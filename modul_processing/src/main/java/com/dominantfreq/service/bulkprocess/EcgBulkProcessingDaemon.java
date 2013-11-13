package com.dominantfreq.service.bulkprocess;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.dominantfreq.model.data.Ecg;
import com.dominantfreq.model.data.EcgAnalysis;
import com.dominantfreq.model.data.EcgSpectrum;
import com.dominantfreq.model.data.RealEcgSpectrum;
import com.dominantfreq.model.dataaccess.CallableEcgLoadingTask;
import com.dominantfreq.service.TransformationPool;
import com.dominantfreq.service.analysis.EcgAnalyser;
import com.dominantfreq.service.fourier.EcgFourier;
import com.dominantfreq.service.postprocess.EcgSpectrumPostProcessor;
import com.dominantfreq.service.preprocess.EcgChannelPreProcessor;

public class EcgBulkProcessingDaemon extends Thread {
	private static final EcgBulkProcessingDaemon INSTANCE = new EcgBulkProcessingDaemon();
	private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
	private static final EcgChannelPreProcessor PREPROCESSOR = EcgChannelPreProcessor.getPreProcessor();
	private static final EcgFourier FOURIER = EcgFourier.getFourier();
	private static final EcgSpectrumPostProcessor POSTPROCESSOR = EcgSpectrumPostProcessor.getPostProcessor();
	private static final EcgAnalyser ANALYSER = EcgAnalyser.getAnalyser();
	private static final EcgProcessingRequest TERMINATION_REQUEST = new EcgProcessingRequest(new File(UUID.randomUUID().toString()), UUID.randomUUID().toString());
	private static final int SLEEPYNESS = 500;
	private static final int ECG_LIMIT = 3;
	private static final int LOAD_PER_CYCLE = 2;

	private static Queue<EcgProcessingRequest> processingRequests;
	private static Queue<Ecg> loadedEcgs;
	private static Queue<EcgAnalysis> results;

	private EcgBulkProcessingDaemon() {
		setDaemon(true);
		processingRequests = new ConcurrentLinkedQueue<EcgProcessingRequest>();
		loadedEcgs = new ConcurrentLinkedQueue<Ecg>();
		results = new ConcurrentLinkedQueue<EcgAnalysis>();
	}

	public static synchronized EcgBulkProcessingDaemon getInstance() {
		if (!INSTANCE.isAlive()) {
			INSTANCE.start();
		}
		return INSTANCE;
	}

	public void sendProcessingRequest(EcgProcessingRequest request) {
		processingRequests.add(request);
	}

	public void sendTerminationRequest() {
		processingRequests.add(TERMINATION_REQUEST);
	}

	public List<EcgAnalysis> pollResults() {
		List<EcgAnalysis> analysises = new ArrayList<EcgAnalysis>();
		while (!results.isEmpty())
			analysises.add(results.poll());
		return analysises;
	}

	@Override
	public void run() {
		while (!terminated()) {
			if (validateProcessingCycle()) {
				processCycle();
			}
		}
		shutdownExecutorsAndCleanQueues();
	}

	private boolean terminated() {
		return processingRequests.contains(TERMINATION_REQUEST);
	}

	private boolean validateProcessingCycle() {
		boolean hasRequest = !processingRequests.isEmpty();
		boolean hasLoadedEcg = !loadedEcgs.isEmpty();
		boolean needed = hasRequest || hasLoadedEcg;
		if (!needed)
			sleepIfNothingToDo();
		return needed;
	}

	private void processCycle() {
		try {
			List<Future<Ecg>> futureEcgs = new ArrayList<Future<Ecg>>();
			futureEcgs.addAll(loadSomeEcgs());
			processCurrentEcg();
			addLoadedEcgsToQueue(futureEcgs);
		} catch (IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	private List<Future<Ecg>> loadSomeEcgs() throws IOException, InterruptedException {
		List<CallableEcgLoadingTask> ecgLoadingTasks = new ArrayList<CallableEcgLoadingTask>();
		if (loadedEcgs.size() < ECG_LIMIT) {
			ecgLoadingTasks.addAll(loadEcgs(LOAD_PER_CYCLE));
		}
		List<Future<Ecg>> tasks = EXECUTOR.invokeAll(ecgLoadingTasks);
		return tasks;
	}

	private void processCurrentEcg() {
		Ecg ecg = loadedEcgs.poll();
		if (ecg != null) {
			ecg = PREPROCESSOR.transform(ecg);
			EcgSpectrum spectrum = FOURIER.transform(ecg);
			RealEcgSpectrum realSpectrum = POSTPROCESSOR.transform(spectrum);
			EcgAnalysis analysis = ANALYSER.transform(realSpectrum);
			results.add(analysis);
		}
	}

	private void addLoadedEcgsToQueue(List<Future<Ecg>> futureEcgs) throws InterruptedException, ExecutionException {
		for (Future<Ecg> futureEcg : futureEcgs) {
			Ecg ecg = futureEcg.get();
			loadedEcgs.add(ecg);
		}
	}

	private List<CallableEcgLoadingTask> loadEcgs(int amount) throws IOException {
		List<CallableEcgLoadingTask> ecgLoadingTasks = new ArrayList<CallableEcgLoadingTask>();
		for (int i = 0; i < amount; i++) {
			EcgProcessingRequest request = processingRequests.poll();
			if (request != null) {
				CallableEcgLoadingTask loadingTask = new CallableEcgLoadingTask(request);
				ecgLoadingTasks.add(loadingTask);
			}
		}
		return ecgLoadingTasks;
	}

	private void sleepIfNothingToDo() {
		try {
			sleep(SLEEPYNESS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void shutdownExecutorsAndCleanQueues() {
		EXECUTOR.shutdownNow();
		TransformationPool.shutdownNow();
		processingRequests.clear();
		loadedEcgs.clear();
		results.clear();
	}

}

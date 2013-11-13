package com.dominantfreq.model.dataaccess;

import java.io.IOException;
import java.util.concurrent.Callable;

import com.dominantfreq.model.data.Ecg;
import com.dominantfreq.service.bulkprocess.EcgProcessingRequest;

public class CallableEcgLoadingTask implements Callable<Ecg> {

	private static final EcgLoader LOADER = EcgLoader.getEcgLoader();
	private EcgProcessingRequest request;

	public CallableEcgLoadingTask(EcgProcessingRequest request) throws IOException {
		this.request = request;
	}

	@Override
	public Ecg call() throws Exception {
		return LOADER.readECG(request.getFolder(), request.getFileName());
	}
}

package com.dominantfreq.model.dataaccess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.dominantfreq.model.Settings;
import com.dominantfreq.model.data.Ecg;
import com.dominantfreq.model.signal.Channel;

public class EcgLoader {

	private static final EcgLoader INSTANCE = new EcgLoader();

	private EcgLoader() { /* prevents initialization */
	}

	public static final EcgLoader getEcgLoader() {
		return INSTANCE;
	}

	public List<String> getDataNames(File folder) {
		File[] listOfFiles = folder.listFiles();
		List<String> ecgNames = new ArrayList<String>();
		for (File file : listOfFiles) {
			String ecgName = extractEcgName(file);
			if (validFileName(ecgName, ecgNames)) {
				ecgNames.add(ecgName);
			}
		}
		return ecgNames;
	}

	private boolean validFileName(String ecgName, List<String> ecgNames) {
		return !ecgNames.contains(ecgName);
	}

	private String extractEcgName(File file) {
		String ecgName = file.getName().split("\\.")[0];
		return ecgName;
	}

	public EcgInfo readEcgInfo(File folder, String name) {
		EcgInfo info = new EcgInfo(folder, name);
		try {
			File infoFile = new File(folder + "/" + name + ".inf");
			populateInfo(info, infoFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}

	private void populateInfo(EcgInfo info, File infoFile) throws IOException {
		List<String> infoLines = readLinesFromFile(infoFile);
		info.setPatient(extractLineValue(infoLines.get(0)));
		info.setDescription(extractLineValue(infoLines.get(1)));
		info.setDate(extractLineValue(infoLines.get(2)));
		info.setNumOfChannel(extractLineValue(infoLines.get(3)));
		info.setNumOfPoints(extractLineValue(infoLines.get(4)));
		info.setSamplingRate(extractLineValue(infoLines.get(5)));
		info.setStartTime(extractLineValue(infoLines.get(6)));
		info.setStopTime(extractLineValue(infoLines.get(7)));
		info.setUnits(infoLines.get(8).split(":")[1].trim());
	}

	private String extractLineValue(String line) {
		String value = line.split("=")[1].trim();
		return value;
	}

	public Ecg readECG(File folder, String name) throws IOException {
		Ecg ecg = new Ecg(name);
		File infoFile = new File(folder + "/" + name + ".inf");
		File dataFile = new File(folder + "/" + name + ".txt");
		populateEcg(ecg, infoFile, dataFile);
		return ecg;
	}

	private void populateEcg(Ecg ecg, File infoFile, File dataFile) throws IOException {
		List<String> infoLines = readLinesFromFile(infoFile);
		List<String> channelNames = extractChannelNames(infoLines);
		int sampleNumber = extractSampleNumber(infoLines);
		int frequency = extractFrequency(infoLines);

		List<String> dataLines = readLinesFromFile(dataFile);
		List<double[]> allChannelsData = extractChannelsData(dataLines, channelNames.size(), sampleNumber);

		for (int i = 0; i < channelNames.size(); i++) {
			String name = channelNames.get(i);
			double[] samples = allChannelsData.get(i);
			Channel channel = new Channel(name, frequency, samples);
			ecg.addChannel(channel);
		}

		Settings.setTimeUpperLimit(sampleNumber / frequency);
	}

	private List<String> readLinesFromFile(File file) throws IOException {
		BufferedReader infReader = null;
		List<String> lines = new ArrayList<String>();
		try {
			infReader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = infReader.readLine()) != null) {
				lines.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			infReader.close();
		}
		return lines;
	}

	private List<String> extractChannelNames(List<String> infoLines) throws IOException {
		List<String> channelNames = new ArrayList<String>();
		List<String> nameSublist = infoLines.subList(10, infoLines.size());
		for (String line : nameSublist) {
			channelNames.add(line.trim().replaceAll("\\s", ""));
		}
		return channelNames;
	}

	private int extractSampleNumber(List<String> infoLines) {
		String sampleNumberLine = infoLines.get(4);
		String sampleNumber = sampleNumberLine.split("=")[1].trim();
		return Integer.parseInt(sampleNumber);
	}

	private int extractFrequency(List<String> infoLines) {
		String frequencyLine = infoLines.get(5);
		return Integer.parseInt(frequencyLine.split("=")[1].split("p")[0].trim());
	}

	private List<double[]> extractChannelsData(List<String> dataLines, int channelNumber, int sampleNumber) throws IOException {
		double[][] data = new double[channelNumber][sampleNumber];
		for (int i = 0; i < sampleNumber; i++) {
			String line = dataLines.get(i).replace(",", ".").trim();
			String[] samples = line.split(" ");
			for (int j = 0; j < channelNumber; j++) {
				data[j][i] = Double.parseDouble(samples[j]);
			}
		}
		List<double[]> channelsData = new ArrayList<double[]>();
		for (int j = 0; j < channelNumber; j++) {
			channelsData.add(data[j]);
		}
		return channelsData;
	}
}

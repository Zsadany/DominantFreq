package com.dominantfreq.model.dataaccess;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.dominantfreq.model.data.Ecg;

public class EcgBuffer {
	private static final int MINIMUM_MEMORY_REQUIRED = 50;
	private static final File FOLDER = new File("./data");
	private static final EcgLoader LOADER = EcgLoader.getEcgLoader();
	private static Map<String, Ecg> ecgMap;
	public static AtomicInteger selectedECG;

	public static void initDataBuffer() throws IOException {
		ecgMap = new ConcurrentHashMap<String, Ecg>();
		selectedECG = new AtomicInteger();
		selectedECG.set(0);
	}

	public static List<String> getEcgNames() {
		return LOADER.getDataNames(FOLDER);
	}

	private static synchronized String getSelectedEcgName() {
		return getEcgNames().get(EcgBuffer.selectedECG.get());
	}

	public static void loadSelectedEcg() throws IOException {
		if (selectedEcgNotInMemory()) {
			freeMemoryIfNecessary();
			Ecg newEcg = LOADER.readECG(FOLDER, getSelectedEcgName());
			putEcg(newEcg);
		}
	}

	private static void freeMemoryIfNecessary() {
		Runtime runtime = Runtime.getRuntime();
		long freeMemory = runtime.freeMemory();
		if (freeMemory / 1024 < MINIMUM_MEMORY_REQUIRED) {
			ecgMap.clear();
		}
	}

	public static boolean selectedEcgNotInMemory() {
		return !ecgMap.containsKey(getSelectedEcgName());
	}

	public static void putEcg(Ecg ecg) {
		ecgMap.put(getSelectedEcgName(), ecg);
	}

	public static Ecg getSelectedEcg() {
		return ecgMap.get(getSelectedEcgName());
	}
}

package com.dominantfreq.model;

import com.dominantfreq.service.fourier.FourierType;
import com.dominantfreq.service.windowfunction.Window;

public class Settings {
	public static int startTime = 0;
	public static int timeInterval = 5;
	public static int timeUpperLimit = 16;
	public static int maxFrequency = 20;

	public static FourierType fourierType = FourierType.DFT;
	public static Window windowFunction = Window.NONE;

	public static boolean ecgOnly = true;
	public static boolean loading = false;

	public static int spectrumSmoothing = 0;
	public static int samplingFilterWidth = 1;
	public static boolean abs = false;
	public static double hzToFlatten = 0.5;

	public static Integer getStartTime() {
		return startTime;
	}

	public static Integer getTimeUpperLimit() {
		return timeUpperLimit;
	}

	public static Integer getTimeInterval() {
		return timeInterval;
	}

	public static Integer getMaxFrequency() {
		return maxFrequency;
	}

	public static void setStartTime(int start) {
		startTime = start;
	}

	public static void setTimeUpperLimit(int limit) {
		timeUpperLimit = limit;
	}

	public static void setTimeInterval(int interval) {
		timeInterval = interval;
	}

	public static void setMaxFrequency(int frequency) {
		maxFrequency = frequency;
	}
}

package com.dominantfreq.model;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.dominantfreq.service.fourier.FourierType;
import com.dominantfreq.service.windowfunction.Window;

public class Settings {
	private static AtomicInteger startTime = new AtomicInteger(0);
	private static AtomicInteger timeInterval = new AtomicInteger(5);
	private static AtomicInteger timeUpperLimit = new AtomicInteger(16);
	private static AtomicInteger maxFrequency = new AtomicInteger(20);

	private static AtomicReference<FourierType> fourierType = new AtomicReference<FourierType>(FourierType.DFT);
	private static AtomicReference<Window> windowFunction = new AtomicReference<Window>(Window.NONE);

	private static AtomicBoolean ecgOnly = new AtomicBoolean(true);
	private static AtomicBoolean loading = new AtomicBoolean(false);

	private static AtomicInteger spectrumSmoothing = new AtomicInteger(0);
	private static AtomicInteger samplingFilterWidth = new AtomicInteger(1);
	private static AtomicBoolean abs = new AtomicBoolean(false);
	private static AtomicBoolean impulsify = new AtomicBoolean(false);
	private static AtomicReference<Double> hzToFlatten = new AtomicReference<Double>(0.5);

	public static Integer getStartTime() {
		return startTime.get();
	}

	public static Integer getTimeUpperLimit() {
		return timeUpperLimit.get();
	}

	public static Integer getTimeInterval() {
		return timeInterval.get();
	}

	public static Integer getMaxFrequency() {
		return maxFrequency.get();
	}

	public static void setStartTime(int start) {
		startTime.set(start);
	}

	public static void setTimeUpperLimit(int limit) {
		timeUpperLimit.set(limit);
	}

	public static void setTimeInterval(int interval) {
		timeInterval.set(interval);
	}

	public static void setMaxFrequency(int frequency) {
		maxFrequency.set(frequency);
	}

	public static boolean isEcgOnly() {
		return ecgOnly.get();
	}

	public static void setEcgOnly(boolean ecgOnly) {
		Settings.ecgOnly.set(ecgOnly);
	}

	public static boolean isLoading() {
		return loading.get();
	}

	public static void setLoading(boolean loading) {
		Settings.loading.set(loading);
	}

	public static int getSpectrumSmoothing() {
		return spectrumSmoothing.get();
	}

	public static void setSpectrumSmoothing(int spectrumSmoothing) {
		Settings.spectrumSmoothing.set(spectrumSmoothing);
	}

	public static int getSamplingFilterWidth() {
		return samplingFilterWidth.get();
	}

	public static void setSamplingFilterWidth(int samplingFilterWidth) {
		Settings.samplingFilterWidth.set(samplingFilterWidth);
	}

	public static boolean getAbs() {
		return abs.get();
	}

	public static void setAbs(boolean abs) {
		Settings.abs.set(abs);
	}

	public static FourierType getFourierType() {
		return fourierType.get();
	}

	public static void setFourierType(FourierType fourierType) {
		Settings.fourierType.set(fourierType);
	}

	public static Window getWindowFunction() {
		return windowFunction.get();
	}

	public static void setWindowFunction(Window windowFunction) {
		Settings.windowFunction.set(windowFunction);
	}

	public static double getHzToFlatten() {
		return hzToFlatten.get();
	}

	public static void setHzToFlatten(double hzToFlatten) {
		Settings.hzToFlatten.set(hzToFlatten);
	}

	public static boolean getImpulsify() {
		return impulsify.get();
	}

	public static void setImpulsify(boolean impulsify) {
		Settings.impulsify.set(impulsify);
	}
}

package com.dominantfreq.model.data;

public class SpectrumAnalysis {

	private String name;
	private double dominantFrequency;
	private int dominantFrequencyIndex;

	// TODO add dominant frequency width and algorithms to compute it

	public SpectrumAnalysis(String name, double dominantFrequency, int dominantFrequencyIndex) {
		this.name = name;
		this.dominantFrequency = dominantFrequency;
		this.dominantFrequencyIndex = dominantFrequencyIndex;
	}

	public String getName() {
		return name;
	}

	public double getDominantFrequency() {
		return dominantFrequency;
	}

	public int getDominantFrequencyIndex() {
		return dominantFrequencyIndex;
	}

}

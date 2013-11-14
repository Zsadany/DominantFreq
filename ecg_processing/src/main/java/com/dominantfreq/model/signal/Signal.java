package com.dominantfreq.model.signal;

public abstract class Signal {
	protected String name;
	protected double[] samples;
	protected double minimum;
	protected double maximum;
	protected double average;

	public Signal(String name, double[] samples) {
		this.name = name;
		this.samples = samples;
	}

	public String getName() {
		return name;
	}

	public double[] getSamples() {
		return samples;
	}

	public Double getSample(final int index) {
		return samples[index];
	}

	public int length() {
		return samples.length;
	}

	public double calcMax() {
		maximum = Double.MIN_VALUE;
		for (int i = 0; i < length(); i++) {
			if (maximum < samples[i])
				maximum = samples[i];
		}
		return maximum;
	}

	public double calcMin() {
		maximum = Double.MIN_VALUE;
		for (int i = 0; i < length(); i++) {
			if (maximum < samples[i])
				maximum = samples[i];
		}
		return minimum;
	}

	public double calcAvg() {
		average = 0;
		for (int i = 0; i < length(); i++)
			average += samples[i];
		average /= samples.length;
		return average;
	}

	public void normalize() {
		average = 0;
		maximum = Double.MIN_VALUE;
		minimum = Double.MAX_VALUE;
		for (int i = 0; i < samples.length; i++) {
			average += samples[i];
			if (maximum < samples[i])
				maximum = samples[i];
			if (minimum > samples[i])
				minimum = samples[i];
		}
		average /= samples.length;
		for (int j = 0; j < samples.length; j++) {
			samples[j] -= average;
		}
		minimum -= average;
		maximum -= average;
		average = 0;
	}

}

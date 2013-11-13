package com.dominantfreq.model.signal;

public class Channel extends Signal {

	private int frequency;

	public Channel(String name, int samplingFrequency, double[] samples) {
		super(name, samples);
		frequency = samplingFrequency;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setSample(int index, double value) {
		samples[index] = value;
	}

	public Channel copy() {
		double[] copySamples = new double[length()];
		for (int i = 0; i < length(); i++) {
			copySamples[i] = samples[i];
		}
		Channel copy = new Channel(name, frequency, copySamples);
		return copy;
	}

}

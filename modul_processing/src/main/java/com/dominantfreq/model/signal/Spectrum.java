package com.dominantfreq.model.signal;

import com.dominantfreq.model.Settings;
import com.dominantfreq.model.data.Complex;

/**
 * Spectrum of an ECG channel.
 * 
 * @author Fulop Zsadany
 * 
 */
public class Spectrum {
	private String name;
	private Complex[] samples;

	public Spectrum(String name, int frequencySampleNumber) {
		this.name = name;
		samples = new Complex[frequencySampleNumber];
	}

	public Spectrum(String name, Complex[] complexes) {
		this.name = name;
		samples = complexes;
	}

	public String getName() {
		return name;
	}

	public Complex getSample(final int index) {
		return samples[index];
	}

	/** Calculates absolute spectrum */
	public double[] getAbsSamples() {
		double[] absSamples = new double[samples.length];
		for (int i = 0; i < samples.length; i++) {
			double abs = samples[i].abs();
			absSamples[i] = abs * abs * Settings.getTimeInterval();
		}
		return absSamples;
	}

	public int length() {
		return samples.length;
	}

	public void setSample(final int index, final Complex value) {
		samples[index] = value;
	}

	public void addToSample(final int index, final Complex value) {
		samples[index] = samples[index].add(value);
	}

	public void divideSample(final int index, final int divider) {
		samples[index] = samples[index].div(divider);
	}

}

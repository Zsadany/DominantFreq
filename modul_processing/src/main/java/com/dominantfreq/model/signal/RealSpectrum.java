package com.dominantfreq.model.signal;

public class RealSpectrum extends Signal {

	public RealSpectrum(Spectrum spectrum) {
		super(spectrum.getName(), spectrum.getAbsSamples());
	}

	public RealSpectrum(String name, double[] samples) {
		super(name, samples);
	}

	public RealSpectrum copy() {
		double[] copySamples = new double[length()];
		for (int i = 0; i < length(); i++) {
			copySamples[i] = samples[i];
		}
		RealSpectrum copy = new RealSpectrum(name, copySamples);
		return copy;
	}

}

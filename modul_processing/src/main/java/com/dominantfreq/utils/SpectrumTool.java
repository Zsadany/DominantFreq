package com.dominantfreq.utils;

import com.dominantfreq.model.signal.RealSpectrum;
import com.dominantfreq.model.signal.Spectrum;

public class SpectrumTool {

	private SpectrumTool() { /* No instantiation. */
	}

	public static RealSpectrum realSpectrumFrom(final Spectrum spectrum) {
		return new RealSpectrum(spectrum);
	}

	public static RealSpectrum applyFilterToSpectrum(final RealSpectrum spectrum) {
		double[] samples = spectrum.getSamples();
		double[] filteredSamples = new double[spectrum.length()];
		for (int i = 0; i < samples.length; i++) {
			filteredSamples[i] = samples[i];
		}
		filteredSamples = Filter.calculateFilteredArrayFrom(filteredSamples);
		return new RealSpectrum(spectrum.getName(), filteredSamples);
	}


	public static RealSpectrum flattenEarlyPeaks(final RealSpectrum spectrum, int numberOfPointsToFlatten) {
		double[] headlessSamples = spectrum.getSamples();
		for (int i = 0; i < numberOfPointsToFlatten; i++) {
			headlessSamples[i] = 0;
		}
		return new RealSpectrum(spectrum.getName(), headlessSamples);
	}

	public static RealSpectrum inflateSpectrumValues(final RealSpectrum spectrum) {
		double[] samples = spectrum.getSamples();
		double[] inflatedSamples = new double[samples.length];
		for (int i = 0; i < samples.length; i++) {
			inflatedSamples[i] = samples[i] * samples[i];
		}
		return new RealSpectrum(spectrum.getName(), inflatedSamples);
	}

}

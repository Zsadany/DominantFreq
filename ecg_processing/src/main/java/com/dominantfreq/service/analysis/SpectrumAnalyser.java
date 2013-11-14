package com.dominantfreq.service.analysis;

import com.dominantfreq.model.Settings;
import com.dominantfreq.model.data.SpectrumAnalysis;
import com.dominantfreq.model.signal.RealSpectrum;
import com.dominantfreq.service.CallableTransformation;
import com.dominantfreq.utils.Filter;

public class SpectrumAnalyser extends CallableTransformation<RealSpectrum, SpectrumAnalysis> {

	private int filterWidth;
	private int filterLowIndex;
	private int filterHighIndex;

	public SpectrumAnalyser(RealSpectrum spectrum) {
		super(spectrum);
	}

	@Override
	public SpectrumAnalysis transform(RealSpectrum spectrum) {
		filterWidth = Settings.getSamplingFilterWidth() * spectrum.length() / Settings.getMaxFrequency();
		filterLowIndex = (filterWidth + 1) / 2;
		filterHighIndex = spectrum.length() - filterLowIndex;
		int dominantFrequencyIndex = 1;
		double dominantFittness = 0;
		double fittness = 0;
		for (int relativeFrequency = filterLowIndex; relativeFrequency < filterHighIndex; relativeFrequency++) {
			fittness = determineLocalFittness(spectrum, relativeFrequency);
			if (fittness > dominantFittness) {
				dominantFittness = fittness;
				dominantFrequencyIndex = relativeFrequency;
			}
		}
		double dominantFrequency = ((double) dominantFrequencyIndex) * (double) Settings.getMaxFrequency() / spectrum.length();
		return new SpectrumAnalysis(spectrum.getName(), dominantFrequency, dominantFrequencyIndex);
	}

	private double determineLocalFittness(RealSpectrum spectrum, int relativeFrequency) {
		double fittness = 0;
		double divider = 0;
		for (int subFrequency = relativeFrequency; subFrequency < filterHighIndex; subFrequency += relativeFrequency) {
			fittness += localFitness(spectrum, subFrequency);
			divider++;
		}
		fittness /= divider;
		fittness *= Math.log(2 + (spectrum.length() / relativeFrequency));
		return fittness;
	}

	private double localFitness(RealSpectrum spectrum, int subFrequency) {
		double[] frequencies = new double[filterWidth];
		for (int index = 0, relative = subFrequency - filterLowIndex; index < filterWidth; index++, relative++) {
			frequencies[index] = spectrum.getSample(relative);
		}
		return Filter.hammingFilter(frequencies);
	}
}

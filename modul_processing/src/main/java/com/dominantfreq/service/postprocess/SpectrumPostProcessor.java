package com.dominantfreq.service.postprocess;

import com.dominantfreq.model.Settings;
import com.dominantfreq.model.signal.RealSpectrum;
import com.dominantfreq.model.signal.Spectrum;
import com.dominantfreq.service.CallableTransformation;
import com.dominantfreq.utils.SpectrumTool;

public class SpectrumPostProcessor extends CallableTransformation<Spectrum, RealSpectrum> {

	public SpectrumPostProcessor(Spectrum spectrum) {
		super(spectrum);
	}

	@Override
	public RealSpectrum transform(Spectrum spectrum) {
		RealSpectrum realSpectrum = SpectrumTool.realSpectrumFrom(spectrum);
		if (Settings.hzToFlatten > 0.1) {
			int pointsToFlatten = (int) ((realSpectrum.length() / Settings.maxFrequency) * Settings.hzToFlatten);
			realSpectrum = SpectrumTool.flattenEarlyPeaks(realSpectrum, pointsToFlatten);
		}
		if (Settings.spectrumSmoothing > 0) {
			for (int i = 0; i < Settings.spectrumSmoothing; i++) {
				realSpectrum = SpectrumTool.applyFilterToSpectrum(realSpectrum);
				realSpectrum = SpectrumTool.inflateSpectrumValues(realSpectrum);
			}
		}
		realSpectrum.normalize();
		return realSpectrum;
	}

}

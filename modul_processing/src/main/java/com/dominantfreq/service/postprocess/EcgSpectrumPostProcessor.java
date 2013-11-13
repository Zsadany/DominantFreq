package com.dominantfreq.service.postprocess;

import java.util.ArrayList;
import java.util.List;

import com.dominantfreq.model.data.EcgSpectrum;
import com.dominantfreq.model.data.RealEcgSpectrum;
import com.dominantfreq.model.signal.RealSpectrum;
import com.dominantfreq.model.signal.Spectrum;
import com.dominantfreq.service.TransformationPool;
import com.dominantfreq.service.Transformation;
import com.dominantfreq.service.postprocess.SpectrumPostProcessor;

public class EcgSpectrumPostProcessor implements Transformation<EcgSpectrum, RealEcgSpectrum> {

	private static final EcgSpectrumPostProcessor INSTANCE = new EcgSpectrumPostProcessor();

	private EcgSpectrumPostProcessor() {/* prevents initialization */}

	public static EcgSpectrumPostProcessor getPostProcessor() {
		return INSTANCE;
	}

	@Override
	public RealEcgSpectrum transform(EcgSpectrum ecgSpectrum) {
		List<SpectrumPostProcessor> transformations = prepareTransformations(ecgSpectrum);
		List<RealSpectrum> transformedSpectrums = TransformationPool.execute(transformations);
		return assemblePreprocessedEcg(ecgSpectrum.getName(), transformedSpectrums);
	}

	private List<SpectrumPostProcessor> prepareTransformations(EcgSpectrum ecgSpectrum) {
		List<SpectrumPostProcessor> transformations = new ArrayList<SpectrumPostProcessor>();
		for (Spectrum spectrum : ecgSpectrum.getChannelSpectrums()) {
			SpectrumPostProcessor spectrumPostProcessor = new SpectrumPostProcessor(spectrum);
			transformations.add(spectrumPostProcessor);
		}
		return transformations;
	}

	private RealEcgSpectrum assemblePreprocessedEcg(String name, List<RealSpectrum> spectrums) {
		RealEcgSpectrum realEcg = new RealEcgSpectrum(name);
		for (RealSpectrum spectrum : spectrums) {
			realEcg.addChannelSpectrum(spectrum);
		}
		return realEcg;
	}

}

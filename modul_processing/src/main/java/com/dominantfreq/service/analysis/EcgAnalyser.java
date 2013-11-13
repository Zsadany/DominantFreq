package com.dominantfreq.service.analysis;

import java.util.ArrayList;
import java.util.List;

import com.dominantfreq.model.data.EcgAnalysis;
import com.dominantfreq.model.data.RealEcgSpectrum;
import com.dominantfreq.model.data.SpectrumAnalysis;
import com.dominantfreq.model.signal.RealSpectrum;
import com.dominantfreq.service.Transformation;
import com.dominantfreq.service.TransformationPool;

public class EcgAnalyser implements Transformation<RealEcgSpectrum, EcgAnalysis> {
	
	private static final EcgAnalyser INSTANCE = new EcgAnalyser();
	
	private EcgAnalyser() {/* prevents initialization */}

	public static EcgAnalyser getInstance() {
		return INSTANCE;
	}
	
	@Override
	public EcgAnalysis transform(RealEcgSpectrum ecgSpectrum) {
		List<SpectrumAnalyser> transformations = prepareTransformations(ecgSpectrum);
		List<SpectrumAnalysis> analysisList = TransformationPool.execute(transformations);
		return assembleEcgAnalysis(ecgSpectrum.getName(), analysisList);
	}

	private List<SpectrumAnalyser> prepareTransformations(RealEcgSpectrum ecgSpectrum) {
		List<SpectrumAnalyser> transformations = new ArrayList<SpectrumAnalyser>();
		for (RealSpectrum spectrum : ecgSpectrum.getChannelSpectrums()) {
			SpectrumAnalyser analyser = new SpectrumAnalyser(spectrum);
			transformations.add(analyser);
		}
		return transformations;
	}

	private EcgAnalysis assembleEcgAnalysis(String name, List<SpectrumAnalysis> analysises) {
		EcgAnalysis ecgAnalysis = new EcgAnalysis(name);
		for (SpectrumAnalysis analysis : analysises) {
			ecgAnalysis.addSpectrumAnalysis(analysis);
		}
		return ecgAnalysis;
	}

}

package com.dominantfreq.display.configuration;

import java.io.IOException;

import com.dominantfreq.display.controller.DisplayControl;
import com.dominantfreq.model.Settings;
import com.dominantfreq.model.data.Ecg;
import com.dominantfreq.model.data.EcgAnalysis;
import com.dominantfreq.model.data.EcgSpectrum;
import com.dominantfreq.model.data.RealEcgSpectrum;
import com.dominantfreq.model.dataaccess.EcgBuffer;
import com.dominantfreq.model.dataaccess.ResultBuffer;
import com.dominantfreq.service.analysis.EcgAnalyser;
import com.dominantfreq.service.fourier.EcgFourier;
import com.dominantfreq.service.postprocess.EcgSpectrumPostProcessor;
import com.dominantfreq.service.preprocess.EcgChannelPreProcessor;

class EcgProcessorConfig extends ConfigSkeleton {
	private static final Config INSTANCE = new EcgProcessorConfig();

	private static final EcgChannelPreProcessor PREPROCESSOR = EcgChannelPreProcessor.getInstance();
	private static final EcgFourier FOURIER = EcgFourier.getInstance();
	private static final EcgSpectrumPostProcessor POSTPROCESSOR = EcgSpectrumPostProcessor.getInstance();
	private static final EcgAnalyser ANALYSER = EcgAnalyser.getInstance();

	private EcgProcessorConfig() {// Singleton
	}

	public static Config getIntance() {
		return INSTANCE;
	}

	@Override
	protected void executeConfig() throws IOException {
		EcgBuffer.loadSelectedEcg();
		Ecg ecg = PREPROCESSOR.transform(EcgBuffer.getSelectedEcg());
		EcgSpectrum ecgSpectrum = FOURIER.transform(ecg);
		RealEcgSpectrum realEcgSpectrum = POSTPROCESSOR.transform(ecgSpectrum);
		EcgAnalysis analysis = ANALYSER.transform(realEcgSpectrum);
		storeResults(ecg, realEcgSpectrum, analysis);
		DisplayControl.initTabs();
		Settings.setLoading(false);
	}

	private void storeResults(Ecg ecg, RealEcgSpectrum realEcgSpectrum, EcgAnalysis analysis) {
		ResultBuffer.setEcgToDraw(ecg);
		ResultBuffer.setEcgSpectrum(realEcgSpectrum);
		ResultBuffer.setEcgAnalysis(analysis);
	}

}

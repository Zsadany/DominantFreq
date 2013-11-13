package com.dominantfreq.display.service;

import java.io.IOException;

import com.dominantfreq.display.model.DisplayMode;
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

/**
 * Class managing the display , run on a separate thread to avoid
 * unresponsiveness during calculations
 * 
 * @author Fulop Zsadany
 */
public class DisplayConfiguration extends Thread {

	/**
	 * The mode of the current DM thread
	 */
	private DisplayMode displayMode;
	private static final EcgChannelPreProcessor PREPROCESSOR = EcgChannelPreProcessor.getPreProcessor();
	private static final EcgFourier FOURIER = EcgFourier.getFourier();
	private static final EcgSpectrumPostProcessor POSTPROCESSOR = EcgSpectrumPostProcessor.getPostProcessor();
	private static final EcgAnalyser ANALYSER = EcgAnalyser.getAnalyser();

	public DisplayConfiguration(DisplayMode mode) {
		displayMode = mode;
	}

	public synchronized void run() {
		try {
			DisplayContentSetter.fitWindowToScreen();
			switch (displayMode) {
			case FULL_ECG_VIEW:
				drawEcgOnly();
				break;
			case NEW_PROCESSING_AND_DISPLAY:
				processConfiguration();
				break;
			case PRELOAD_ECG:
				EcgBuffer.loadSelectedEcg();
				break;
			case REPAINT:
				break;
			}
			DisplayContentSetter.setDisplay();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void drawEcgOnly() throws IOException {
		EcgBuffer.loadSelectedEcg();
		DisplayContentSetter.initEcgOnlyTabs();
		Settings.loading = false;
	}

	private void processConfiguration() throws IOException {
		EcgBuffer.loadSelectedEcg();
		Ecg ecg = PREPROCESSOR.transform(EcgBuffer.getSelectedEcg());
		EcgSpectrum ecgSpectrum = FOURIER.transform(ecg);
		RealEcgSpectrum realEcgSpectrum = POSTPROCESSOR.transform(ecgSpectrum);
		EcgAnalysis analysis = ANALYSER.transform(realEcgSpectrum);
		storeResults(ecg, realEcgSpectrum, analysis);
		DisplayContentSetter.initTabs();
		Settings.loading = false;
	}

	private void storeResults(Ecg ecg, RealEcgSpectrum realEcgSpectrum, EcgAnalysis analysis) {
		ResultBuffer.setEcgToDraw(ecg);
		ResultBuffer.setEcgSpectrum(realEcgSpectrum);
		ResultBuffer.setEcgAnalysis(analysis);
	}
}

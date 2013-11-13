package com.dominantfreq.model.dataaccess;

import java.util.concurrent.atomic.AtomicReference;

import com.dominantfreq.model.data.Ecg;
import com.dominantfreq.model.data.EcgAnalysis;
import com.dominantfreq.model.data.RealEcgSpectrum;

public class ResultBuffer {
	private static AtomicReference<Ecg> ecgPartToDraw = new AtomicReference<Ecg>();
	private static AtomicReference<RealEcgSpectrum> ecgSpectrum = new AtomicReference<RealEcgSpectrum>();
	private static AtomicReference<EcgAnalysis> ecgAnalysis = new AtomicReference<EcgAnalysis>();

	public static Ecg getEcgToDraw() {
		return ecgPartToDraw.get();
	}

	public static void setEcgToDraw(Ecg ecgPartToDraw) {
		ResultBuffer.ecgPartToDraw.set(ecgPartToDraw);
	}

	public static RealEcgSpectrum getEcgSpectrum() {
		return ecgSpectrum.get();
	}

	public static void setEcgSpectrum(RealEcgSpectrum ecgSpectrum) {
		ResultBuffer.ecgSpectrum.set(ecgSpectrum);
	}

	public static void setEcgAnalysis(EcgAnalysis analysis) {
		ecgAnalysis.set(analysis);
	}

	public static EcgAnalysis getEcgAnalysis() {
		return ecgAnalysis.get();
	}

}

package com.dominantfreq.service.fourier;

import com.dominantfreq.model.data.Ecg;
import com.dominantfreq.model.data.EcgSpectrum;
import com.dominantfreq.service.Transformation;
import com.dominantfreq.service.fourier.transformation.EcgDftTransformation;

public class EcgFourier implements Transformation<Ecg, EcgSpectrum> {

	private static final EcgFourier INSTANCE = new EcgFourier();

	private EcgFourier() {/* prevents initialization */}

	public static EcgFourier getFourier() {
		return INSTANCE;
	}

	@Override
	public EcgSpectrum transform(Ecg ecg) {
		Transformation<Ecg, EcgSpectrum> transformation = selectTransformation();
		return transformation.transform(ecg);
	}

	private Transformation<Ecg, EcgSpectrum> selectTransformation() {
		return new EcgDftTransformation();
	}

}

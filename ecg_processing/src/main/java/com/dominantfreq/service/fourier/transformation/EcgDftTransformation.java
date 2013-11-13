package com.dominantfreq.service.fourier.transformation;

import java.util.ArrayList;
import java.util.List;

import com.dominantfreq.model.data.Ecg;
import com.dominantfreq.model.data.EcgSpectrum;
import com.dominantfreq.model.signal.Channel;
import com.dominantfreq.model.signal.Spectrum;
import com.dominantfreq.service.Transformation;
import com.dominantfreq.service.TransformationPool;

public class EcgDftTransformation implements Transformation<Ecg, EcgSpectrum> {

	@Override
	public EcgSpectrum transform(Ecg ecg) {
		List<DFT> transformations = prepareTransformations(ecg);
		List<Spectrum> channelSpectrums = TransformationPool.execute(transformations);
		return assembleEcgSpectrum(ecg.getName(), channelSpectrums);
	}

	private List<DFT> prepareTransformations(Ecg ecg) {
		List<DFT> transformations = new ArrayList<DFT>();
		for (Channel channel : ecg.getChannels()) {
			DFT dft = new DFT(channel);
			transformations.add(dft);
		}
		return transformations;
	}

	private EcgSpectrum assembleEcgSpectrum(String name, List<Spectrum> channelSpectrums) {
		EcgSpectrum ecgSpectrum = new EcgSpectrum(name);
		for (Spectrum spectrum : channelSpectrums) {
			ecgSpectrum.addChannelSpectrum(spectrum);
		}
		return ecgSpectrum;
	}

}

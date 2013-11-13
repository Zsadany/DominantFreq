package com.dominantfreq.service.fourier.transformation;

import com.dominantfreq.model.Settings;
import com.dominantfreq.model.data.Complex;
import com.dominantfreq.model.signal.Channel;
import com.dominantfreq.model.signal.Spectrum;
import com.dominantfreq.service.CallableTransformation;

public class DFT extends CallableTransformation<Channel, Spectrum> {

	public DFT(final Channel channel) {
		super(channel);
	}

	@Override
	public Spectrum transform(final Channel channel) {
		int f = channel.getFrequency();
		int N = channel.length();
		int fN = (Settings.getMaxFrequency() * N) / f;
		Spectrum spectrum = new Spectrum(channel.getName(), fN);
		spectrum.setSample(0, Complex.ZERO);
		final Complex Minus2PiIPerN = Complex.I.times((-1 * 2.0 * Math.PI) / N);
		for (int k = 0; k < fN; k++) {
			spectrum.setSample(k, Complex.ZERO);
			for (int n = 0; n < N; n++) {
				int nk = k * n;
				Complex e = Complex.exp(Minus2PiIPerN.times(nk));
				Double xn = channel.getSample(n);
				spectrum.addToSample(k, e.times(xn));
			}
		}
		return spectrum;
	}
}
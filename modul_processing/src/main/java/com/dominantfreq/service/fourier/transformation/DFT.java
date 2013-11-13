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
		Complex Minus2PiIPerN = Complex.I.mul((2.0 * Math.PI) / N).mul(-1);
		for (int k = 0; k < fN; k++) {
			spectrum.setSample(k, Complex.ZERO);
			for (int n = 0; n < N; n++) {
				int kn = k * n;
				Complex e = Complex.exp(Minus2PiIPerN.mul(kn));
				Double xn = channel.getSample(n);
				spectrum.addToSample(k, e.mul(xn));
			}
		}
		return spectrum;
	}
}
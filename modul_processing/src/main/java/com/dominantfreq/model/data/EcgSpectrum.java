package com.dominantfreq.model.data;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.dominantfreq.model.signal.Spectrum;

public class EcgSpectrum {
	private String ecgSpectrumName;
	private Map<String, Spectrum> spectrums;

	public EcgSpectrum(String ecgSpectrumName) {
		this.ecgSpectrumName = ecgSpectrumName;
		spectrums = new ConcurrentHashMap<String, Spectrum>();
	}

	public String getName() {
		return ecgSpectrumName;
	}

	public void addChannelSpectrum(Spectrum spectrum) {
		spectrums.put(spectrum.getName(), spectrum);
	}

	public Spectrum getChannelSpectrum(String channelName) {
		return spectrums.get(channelName);
	}

	public Collection<Spectrum> getChannelSpectrums() {
		return spectrums.values();
	}
}

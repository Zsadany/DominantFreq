package com.dominantfreq.model.data;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.dominantfreq.model.signal.RealSpectrum;

public class RealEcgSpectrum {
	private String ecgSpectrumName;
	private Map<String, RealSpectrum> spectrums;

	public RealEcgSpectrum(String ecgSpectrumName) {
		this.ecgSpectrumName = ecgSpectrumName;
		spectrums = new ConcurrentHashMap<String, RealSpectrum>();
	}

	public String getName() {
		return ecgSpectrumName;
	}

	public void addChannelSpectrum(RealSpectrum spectrum) {
		spectrums.put(spectrum.getName(), spectrum);
	}

	public RealSpectrum getChannelSpectrum(String channelName) {
		return spectrums.get(channelName);
	}

	public Collection<RealSpectrum> getChannelSpectrums() {
		return spectrums.values();
	}
}

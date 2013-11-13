package com.dominantfreq.service.fourier;

import java.util.ArrayList;
import java.util.List;

public enum FourierType {
	DFT("DFT"), FFT("FFT");

	private String value;

	FourierType(String fourierType) {
		value = fourierType;
	}

	@Override
	public String toString() {
		return value;
	}

	public static List<String> list() {
		List<String> list = new ArrayList<>();
		for (FourierType type : values()) {
			list.add(type.toString());
		}
		return list;
	}
}

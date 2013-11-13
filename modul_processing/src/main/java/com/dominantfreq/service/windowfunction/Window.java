package com.dominantfreq.service.windowfunction;

import java.util.ArrayList;
import java.util.List;

public enum Window {
	NONE("None"), HAMMING("Hamming"), GAUSSIAN("Gaussian"), TRIANGULAR("Triangular"), EXPONENTIAL("Exponential");

	private String value;

	Window(String window) {
		value = window;
	}

	@Override
	public String toString() {
		return value;
	}

	public static List<String> list() {
		List<String> list = new ArrayList<>();
		for (Window window : values()) {
			list.add(window.toString());
		}
		return list;
	}
}

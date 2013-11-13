package com.dominantfreq.service.windowfunction;

class HammingWindowFunction extends WindowFunctionSkeleton {

	private static final WindowFunction INSTANCE = new HammingWindowFunction();

	private static final double alfa = 0.54;
	private static final double beta = 0.46;
	private static final double TWO_PI = 2.0 * Math.PI;

	private HammingWindowFunction() {/* prevent external instantiation */
	}

	static WindowFunction getInstance() {
		return INSTANCE;
	}

	/** Hamming function */
	public double windowValueAt(final int index, final int length) {
		return alfa - beta * Math.cos(TWO_PI * index / (length - 1));
	}
}

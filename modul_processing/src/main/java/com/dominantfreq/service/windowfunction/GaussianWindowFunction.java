package com.dominantfreq.service.windowfunction;

class GaussianWindowFunction extends WindowFunctionSkeleton {

	private static final WindowFunction INSTANCE = new GaussianWindowFunction();

	private static final double sigma = 0.4;

	private GaussianWindowFunction() { /* No external instantiation. */
	}

	static WindowFunction getInstance() {
		return INSTANCE;
	}

	@Override
	public double windowValueAt(final int index, final int length) {
		return Math.exp(-Math.pow((index - (length - 1.0) / 2.0) / (sigma * (length - 1.0) / 2.0), 2) / 2.0);
	}

}

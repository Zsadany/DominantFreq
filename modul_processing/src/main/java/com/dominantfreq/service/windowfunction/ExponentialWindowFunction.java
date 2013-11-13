package com.dominantfreq.service.windowfunction;

class ExponentialWindowFunction extends WindowFunctionSkeleton {

	private static final WindowFunction INSTANCE = new ExponentialWindowFunction();

	private ExponentialWindowFunction() { /* No external instantiation. */
	}

	static WindowFunction getInstance() {
		return INSTANCE;
	}

	@Override
	public double windowValueAt(final int index, final int length) {
		return Math.exp((-Math.abs(index - (length - 1) / 2)) * (14 / length));
	}

}

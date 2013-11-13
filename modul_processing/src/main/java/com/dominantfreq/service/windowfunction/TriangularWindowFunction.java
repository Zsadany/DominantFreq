package com.dominantfreq.service.windowfunction;

class TriangularWindowFunction extends WindowFunctionSkeleton {
	private static final WindowFunction INSTANCE = new TriangularWindowFunction();

	private TriangularWindowFunction() { /* No external instantiation. */
	}

	static WindowFunction getInstance() {
		return INSTANCE;
	}

	@Override
	public double windowValueAt(final int index, final int length) {
		return 1.0 + Math.abs(index - (((length - 1.0) / 2.0) / ((length + 1.0) / 2.0)));
	}

}

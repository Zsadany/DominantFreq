package com.dominantfreq.service.windowfunction;

class DummyWindowFunction extends WindowFunctionSkeleton {

	private static final WindowFunction INSTANCE = new DummyWindowFunction();

	private DummyWindowFunction() { /* No instantiation externally. */
	}

	static final WindowFunction getInstance() {
		return INSTANCE;
	}

	@Override
	public double windowValueAt(int index, int length) {
		return 1;
	}

}

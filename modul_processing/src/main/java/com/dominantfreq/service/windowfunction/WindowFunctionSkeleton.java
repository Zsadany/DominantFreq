package com.dominantfreq.service.windowfunction;

abstract class WindowFunctionSkeleton implements WindowFunction {

	public double[] createWindowOf(int length) {
		double[] window = new double[length];
		for (int i = 0; i < length; i++) {
			window[i] = windowValueAt(i, length);
		}
		return window;
	}
}

package com.dominantfreq.service.windowfunction;

public class WindowFunctionFactory {

	private WindowFunctionFactory() { /* No instantiation. */
	}

	public static WindowFunction getInstanceOfType(Window window) {
		switch (window) {
		case NONE:
			return getDummyWindowFunction();
		case HAMMING:
			return getHammingWindowFuntion();
		case EXPONENTIAL:
			return getExponentialWindowFuntion();
		case GAUSSIAN:
			return getGaussianWindowFunction();
		case TRIANGULAR:
			return getTriangularWindowFunction();
		default:
			return getDummyWindowFunction();
		}
	}

	public static WindowFunction getHammingWindowFuntion() {
		return HammingWindowFunction.getInstance();
	}

	public static WindowFunction getExponentialWindowFuntion() {
		return ExponentialWindowFunction.getInstance();
	}

	public static WindowFunction getGaussianWindowFunction() {
		return GaussianWindowFunction.getInstance();
	}

	public static WindowFunction getTriangularWindowFunction() {
		return TriangularWindowFunction.getInstance();
	}

	public static WindowFunction getDummyWindowFunction() {
		return DummyWindowFunction.getInstance();
	}

}

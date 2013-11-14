package com.dominantfreq.utils;

import com.dominantfreq.service.windowfunction.Window;
import com.dominantfreq.service.windowfunction.WindowFunction;
import com.dominantfreq.service.windowfunction.WindowFunctionFactory;

public class Filter {

	private Filter() { /* No instantiation. */
	}

	public static double median(double... values) {
		double median = 0;
		for (double value : values) {
			median += value;
		}
		median /= values.length;
		return median;
	}

	public static double max(double... values) {
		double max = Double.MIN_VALUE;
		for (double value : values) {
			if (value > max) {
				max = value;
			}
		}
		return max;
	}

	public static double min(double... values) {
		double min = Double.MAX_VALUE;
		for (double value : values) {
			if (value < min) {
				min = value;
			}
		}
		return min;
	}

	public static double[] calculateFilteredArrayFrom(double[] array) {
		double[] result = new double[array.length];
		result[0] = 0;
		result[1] = 0;
		result[array.length - 1] = 0;
		result[array.length - 2] = 0;
		for (int index = 2; index < array.length - 2; index++) {
			result[index] = Filter.hammingFilter(array[index - 2], array[index - 1], array[index], array[index + 1], array[index + 2]);
		}
		return result;
	}

	public static double hammingFilter(double... values) {
		double hamming = 0;
		WindowFunction windowFunction = WindowFunctionFactory.getInstanceOfType(Window.HAMMING);
		double[] window = windowFunction.createWindowOf(values.length);
		double windowSum = 0;
		for (int index = 0; index < window.length; index++) {
			windowSum += window[index];
		}
		for (int index = 0; index < values.length; index++) {
			hamming += values[index] * window[index];
		}
		hamming /= windowSum;
		return hamming;
	}

}

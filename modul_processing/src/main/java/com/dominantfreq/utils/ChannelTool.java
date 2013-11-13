package com.dominantfreq.utils;

import com.dominantfreq.model.signal.Channel;
import com.dominantfreq.service.windowfunction.Window;
import com.dominantfreq.service.windowfunction.WindowFunction;
import com.dominantfreq.service.windowfunction.WindowFunctionFactory;

public class ChannelTool {

	private ChannelTool() { /* No instantiation. */
	}

	public static Channel applyWindowToChannel(final Channel channel, final Window window) {
		String name = channel.getName();
		int frequency = channel.getFrequency();
		int numberOfSamples = channel.length();
		double[] samples = new double[numberOfSamples];
		WindowFunction windowFunc = WindowFunctionFactory.getInstanceOfType(window);
		for (int index = 0; index < channel.length(); index++) {
			samples[index] = applyWindowToValue(channel, windowFunc, index);
		}
		return new Channel(name, frequency, samples);
	}

	private static double applyWindowToValue(final Channel channel, final WindowFunction windowFunc, final int index) {
		return channel.getSample(index) * windowFunc.windowValueAt(index, channel.length());
	}

	public static Channel extractTimeIntervalFromChannel(final Channel channel, final int fromTime, final int toTime) {
		int fromIndex = channel.getFrequency() * fromTime;
		int toIndex = channel.getFrequency() * toTime;
		return extractIntervalFromChannel(channel, fromIndex, toIndex);
	}

	public static Channel extractIntervalFromChannel(final Channel channel, final int fromIndex, final int toIndex) {
		String name = channel.getName();
		int frequency = channel.getFrequency();
		int numberOfSamples = toIndex - fromIndex;
		double[] samples = new double[numberOfSamples];
		for (int i = 0, j = fromIndex; j < toIndex; j++, i++) {
			samples[i] = channel.getSample(j);
		}
		return new Channel(name, frequency, samples);
	}

	public static Channel absChannel(final Channel channel) {
		String name = channel.getName();
		int frequency = channel.getFrequency();
		double[] samples = new double[channel.length()];
		for (int i = 0; i < channel.length(); i++) {
			samples[i] = Math.abs(channel.getSample(i));
		}
		return new Channel(name, frequency, samples);
	}

	public static Channel createArtificialChannel(final Channel channel) {
		String name = channel.getName();
		int frequency = channel.getFrequency();
		Channel original = channel.copy();
		original.normalize();
		Channel diffChannel = absChannel(original);
		double maxDiff = diffChannel.getMax() - diffChannel.getAvg();
		double minDiff = diffChannel.getAvg() - diffChannel.getMin();
		double treshold = Math.abs(maxDiff + minDiff) * 3 / 8;
		double[] samples = new double[channel.length()];
		for (int index = 2; index < channel.length() - 2; index++) {
			double localFittness = Filter.smoothing(diffChannel.getSample(index));
			if (localFittness > treshold) {
				samples[index] = 1;
			} else {
				samples[index] = 0;
			}
		}
		return new Channel(name, frequency, samples);
	}

}

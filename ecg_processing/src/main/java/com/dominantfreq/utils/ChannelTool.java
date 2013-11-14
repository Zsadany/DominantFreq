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
		WindowFunction windowFunction = WindowFunctionFactory.getInstanceOfType(window);
		for (int index = 0; index < channel.length(); index++) {
			samples[index] = applyWindowToValue(channel, windowFunction, index);
		}
		return new Channel(name, frequency, samples);
	}

	private static double applyWindowToValue(final Channel channel, final WindowFunction windowFunction, final int index) {
		return channel.getSample(index) * windowFunction.windowValueAt(index, channel.length());
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

	public static Channel applyFilterToChannel(final Channel channel) {
		final double[] samples = channel.getSamples();
		double[] filteredSamples = new double[channel.length()];
		for (int i = 0; i < samples.length; i++) {
			filteredSamples[i] = samples[i];
		}
		filteredSamples = Filter.calculateFilteredArrayFrom(filteredSamples);
		return new Channel(channel.getName(), channel.getFrequency(), filteredSamples);
	}

	public static Channel inflateChannelValues(final Channel channel) {
		final double[] samples = channel.getSamples();
		double[] inflatedSamples = new double[samples.length];
		for (int i = 0; i < samples.length; i++) {
			inflatedSamples[i] = Math.signum(samples[i]) * samples[i] * samples[i];
		}
		return new Channel(channel.getName(), channel.getFrequency(), inflatedSamples);
	}

	public static Channel impulsify(final Channel channel) {
		String name = channel.getName();
		int frequency = channel.getFrequency();
		double[] impulseSamples = new double[channel.length()];
		for (int i = 0; i < channel.length(); i++) {
			boolean isImpulse = channel.calcAvg() / 10 < channel.getSample(i);
			impulseSamples[i] = isImpulse ? 1 : 0;
		}
		return new Channel(name, frequency, impulseSamples);
	}

	public static Channel narrowImpulsesToOneSample(final Channel channel) {
		String name = channel.getName();
		int frequency = channel.getFrequency();
		double[] impulseSamples = new double[channel.length()];

		boolean previousWasImpulse = false;
		for (int i = 0; i < channel.length(); i++) {
			boolean impulse = channel.getSample(i) == 1;
			if (previousWasImpulse) {
				impulseSamples[i] = 0;
				if (!impulse)
					previousWasImpulse = false;
			} else {
				if (impulse) {
					impulseSamples[i] = 1;
					previousWasImpulse = true;
				} else {
					impulseSamples[i] = 0;
				}
			}
		}
		return new Channel(name, frequency, impulseSamples);
	}

	public static Channel mergeCloseImpulses(final Channel channel, double mergeTimeDistance) {
		final int indexTolerance = (int) (mergeTimeDistance * channel.getFrequency());
		double[] impulses = new double[channel.length()];
		for (int i = 0; i < channel.length(); i++)
			impulses[i] = 0;
		for (int i = 0; i < channel.length(); i++) {
			if (channel.getSample(i) > 0) {
				int[] merge = mergeToCloseEnough(i, indexTolerance, channel);
				impulses[merge[0]] = 1;
				i = merge[1];
			}
		}
		return new Channel(channel.getName(), channel.getFrequency(), impulses);
	}

	private static int[] mergeToCloseEnough(int startIndex, int indexTolerance, Channel channel) {
		int merge[] = { 0, 0 };
		int count = 0;
		for (int i = startIndex; i < startIndex + indexTolerance && i < channel.length(); i++) {
			if (channel.getSample(i) > 0) {
				merge[0] += i;
				merge[1] = i;
				count++;
			}
		}
		merge[0] /= count;
		return merge;
	}
}

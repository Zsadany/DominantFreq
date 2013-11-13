package com.dominantfreq.service.preprocess;

import com.dominantfreq.model.Settings;
import com.dominantfreq.model.signal.Channel;
import com.dominantfreq.service.CallableTransformation;
import com.dominantfreq.service.windowfunction.Window;
import com.dominantfreq.utils.ChannelTool;

public class ChannelPreProcessor extends CallableTransformation<Channel, Channel> {

	public ChannelPreProcessor(Channel channel) {
		super(channel);
	}

	@Override
	public Channel transform(Channel channel) {
		Integer startTime = Settings.getStartTime();
		Integer endTime = startTime + Settings.getTimeInterval();
		Channel transformedChannel = ChannelTool.extractTimeIntervalFromChannel(channel, startTime, endTime);
		Window windowFunction = Settings.getWindowFunction();
		if (true) {
			transformedChannel = ChannelTool.applyFilterToChannel(transformedChannel);
			transformedChannel = ChannelTool.inflateChannelValues(transformedChannel);
			transformedChannel = ChannelTool.applyFilterToChannel(transformedChannel);
			transformedChannel = ChannelTool.inflateChannelValues(transformedChannel);
			transformedChannel = ChannelTool.applyFilterToChannel(transformedChannel);
			transformedChannel = ChannelTool.inflateChannelValues(transformedChannel);
			transformedChannel = ChannelTool.absChannel(transformedChannel);
			transformedChannel = ChannelTool.impulsify(transformedChannel);
			transformedChannel = ChannelTool.applyFilterToChannel(transformedChannel);
			transformedChannel = ChannelTool.inflateChannelValues(transformedChannel);
			transformedChannel = ChannelTool.impulsify(transformedChannel);
		}
		if (windowFunction != Window.NONE) {
			transformedChannel.normalize();
			transformedChannel = ChannelTool.applyWindowToChannel(transformedChannel, windowFunction);
		}
		if (Settings.getAbs() == true) {
			transformedChannel = ChannelTool.absChannel(transformedChannel);
		}
		transformedChannel.normalize();
		return transformedChannel;
	}
}

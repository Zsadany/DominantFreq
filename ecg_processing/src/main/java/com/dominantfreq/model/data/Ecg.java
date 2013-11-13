package com.dominantfreq.model.data;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.dominantfreq.model.signal.Channel;

public class Ecg {

	private String ecgName;
	private Map<String, Channel> channels;

	public Ecg(String ecgName) {
		this.ecgName = ecgName;
		channels = new ConcurrentHashMap<String, Channel>();
	}

	public void addChannel(Channel channel) {
		channels.put(channel.getName(), channel);
	}

	public void normalizeChannels() {
		for (Channel channel : channels.values()) {
			channel.normalize();
		}
	}

	public String getName() {
		return ecgName;
	}

	public Channel getChannel(String channelName) {
		return channels.get(channelName);
	}

	public Collection<Channel> getChannels() {
		return channels.values();
	}
}

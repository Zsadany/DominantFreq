package com.dominantfreq.service.preprocess;

import java.util.ArrayList;
import java.util.List;

import com.dominantfreq.model.data.Ecg;
import com.dominantfreq.model.signal.Channel;
import com.dominantfreq.service.Transformation;
import com.dominantfreq.service.TransformationPool;

public class EcgChannelPreProcessor implements Transformation<Ecg, Ecg> {

	private static final EcgChannelPreProcessor INSTANCE = new EcgChannelPreProcessor();

	private EcgChannelPreProcessor() {/* prevents instantiation */}

	public static EcgChannelPreProcessor getInstance() {
		return INSTANCE;
	}

	@Override
	public Ecg transform(Ecg ecg) {
		List<ChannelPreProcessor> transformations = prepareTransformations(ecg);
		List<Channel> transformedChannels = TransformationPool.execute(transformations);
		return assemblePreprocessedEcg(ecg.getName(), transformedChannels);
	}

	private List<ChannelPreProcessor> prepareTransformations(Ecg ecg) {
		List<ChannelPreProcessor> transformations = new ArrayList<ChannelPreProcessor>();
		for (Channel channel : ecg.getChannels()) {
			ChannelPreProcessor channelPreProcessor = new ChannelPreProcessor(channel);
			transformations.add(channelPreProcessor);
		}
		return transformations;
	}

	private Ecg assemblePreprocessedEcg(String name, List<Channel> channels) {
		Ecg ecg = new Ecg(name);
		for (Channel channel : channels) {
			ecg.addChannel(channel);
		}
		return ecg;
	}

}

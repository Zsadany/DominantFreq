package com.dominantfreq.display.configuration;

import java.io.IOException;

import com.dominantfreq.model.dataaccess.EcgBuffer;

public class PreloadEcgConfig extends ConfigSkeleton {
	private static final Config INSTANCE = new PreloadEcgConfig();
	
	private PreloadEcgConfig() {
	}

	public static Config getIntance() {
		return INSTANCE;
	}
	
	@Override
	protected void executeConfig() throws IOException {
		EcgBuffer.loadSelectedEcg();
	}
}

package com.dominantfreq.display.configuration;

import java.io.IOException;

class RefreshConfig extends ConfigSkeleton {
	private static final Config INSTANCE = new RefreshConfig();

	private RefreshConfig() { // Singleton
	}

	public static Config getIntance() {
		return INSTANCE;
	}

	@Override
	protected void executeConfig() throws IOException {
		// Nothing to do , Skeleton does the job
	}
}

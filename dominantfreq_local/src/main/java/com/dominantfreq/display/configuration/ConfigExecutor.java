package com.dominantfreq.display.configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Fulop Zsadany
 */
public class ConfigExecutor {
	private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();

	public static void executeEcgOnlyView() {
		EXECUTOR.execute(EcgOnlyConfig.getIntance());
	}

	public static void executeEcgProcessor() {
		EXECUTOR.execute(EcgProcessorConfig.getIntance());
	}

	public static void executeEcgPreload() {
		EXECUTOR.execute(PreloadEcgConfig.getIntance());
	}

	public static void executeRefresh() {
		EXECUTOR.execute(RefreshConfig.getIntance());
	}
}

package com.dominantfreq.display.configuration;

import java.io.IOException;

import com.dominantfreq.display.controller.DisplayControl;
import com.dominantfreq.model.Settings;
import com.dominantfreq.model.data.Ecg;
import com.dominantfreq.model.dataaccess.EcgBuffer;
import com.dominantfreq.model.dataaccess.ResultBuffer;

class EcgOnlyConfig extends ConfigSkeleton {
	private static final EcgOnlyConfig INSTANCE = new EcgOnlyConfig();

	private EcgOnlyConfig() { // Singleton
	}

	public static Config getIntance() {
		return INSTANCE;
	}

	@Override
	protected void executeConfig() throws IOException {
		EcgBuffer.loadSelectedEcg();
		Ecg selectedEcg = EcgBuffer.getSelectedEcg();
		selectedEcg.normalizeChannels();
		ResultBuffer.setEcgToDraw(selectedEcg);
		DisplayControl.initEcgOnlyTabs();
		Settings.setLoading(false);
	}

}

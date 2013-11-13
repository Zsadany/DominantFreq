package com.dominantfreq.display.configuration;

import java.io.IOException;

import com.dominantfreq.display.controller.DisplayControl;

abstract class ConfigSkeleton implements Config {

	protected abstract void executeConfig() throws IOException;
	
	@Override
	public void run() {
		try {
			DisplayControl.fitWindowToScreen();
			executeConfig();
			DisplayControl.setDisplay();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

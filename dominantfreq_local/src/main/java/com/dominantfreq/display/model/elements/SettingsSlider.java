package com.dominantfreq.display.model.elements;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.dominantfreq.display.controller.DisplayController;
import com.dominantfreq.model.Settings;

public class SettingsSlider extends JSlider implements ChangeListener {
	private static final long serialVersionUID = -6334875097388937346L;
	public static final int START_TIME = 0;
	public static final int TIME_INTERVAL = 1;
	public static final int MAX_FREQUENCY = 2;
	public static final int FILTER_WIDTH = 3;

	private final int mode;

	public SettingsSlider(final String name, final int mode, final int start, final int end, final int initial) {
		super(JSlider.HORIZONTAL, start, end, initial);
		this.mode = mode;
		setName(name);
		setMajorTickSpacing(5);
		setMinorTickSpacing(1);
		setPaintTicks(true);
		setPaintLabels(true);
		addChangeListener(this);
	}

	@Override
	public void stateChanged(final ChangeEvent event) {
		if (!this.getValueIsAdjusting()) {
			if (mode == START_TIME) {
				Settings.setStartTime((int) this.getValue());
			}
			if (mode == TIME_INTERVAL) {
				Settings.setTimeInterval((int) this.getValue());
			}
			if (mode == MAX_FREQUENCY) {
				Settings.setMaxFrequency((int) this.getValue());
			}
			if (mode == FILTER_WIDTH) {
				Settings.setSamplingFilterWidth((int) this.getValue());
			}
			DisplayController.refresh();
		}

	}

}

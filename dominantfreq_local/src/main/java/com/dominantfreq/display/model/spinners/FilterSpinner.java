package com.dominantfreq.display.model.spinners;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.dominantfreq.model.Settings;

public class FilterSpinner extends JSpinner implements ChangeListener {
	private static final long serialVersionUID = -813590243931470392L;
	private static final int MAX = 3;

	public FilterSpinner() {
		addChangeListener(this);
	}

	@Override
	public void stateChanged(ChangeEvent ch) {
		int value = (int) getValue();
		if (value >= 0 && value < MAX) {
			Settings.setSpectrumSmoothing(value);
		} else if (value >= MAX) {
			Settings.setSpectrumSmoothing(MAX);
			setValue(MAX);
		} else {
			setValue(0);
		}
	}
}

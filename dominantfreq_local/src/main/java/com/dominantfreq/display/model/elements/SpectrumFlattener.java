package com.dominantfreq.display.model.elements;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.dominantfreq.model.Settings;

public class SpectrumFlattener extends JSlider implements ChangeListener {
	private static final long serialVersionUID = -5752155027720123815L;
	private static final int MAX = 3;

	public SpectrumFlattener() {
		addChangeListener(this);
	}

	@Override
	public void stateChanged(ChangeEvent ch) {
		int value = getValue();
		if (value >= 0 && value < MAX) {
			Settings.spectrumSmoothing = value;
		} else if (value >= MAX) {
			Settings.spectrumSmoothing = MAX;
			setValue(MAX);
		} else {
			setValue(0);
		}
	}

}

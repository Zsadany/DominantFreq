package com.dominantfreq.display.model.elements;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;

import com.dominantfreq.model.Settings;

public class SpectrumFlattener extends Slider {
	private static final int MIN = 0;
	private static final int MAX = 3;

	public SpectrumFlattener() {
		setMin(MIN);
		setMax(MAX);
		setMajorTickUnit(1);
		setShowTickMarks(true);
		setShowTickLabels(true);
		setBlockIncrement(0.1);
		valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
				Settings.setHzToFlatten(newValue.doubleValue());
			}
		});
	}
}

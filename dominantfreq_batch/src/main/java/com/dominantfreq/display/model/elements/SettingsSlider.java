package com.dominantfreq.display.model.elements;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Slider;

import com.dominantfreq.model.Settings;

public class SettingsSlider extends Slider {
	public static final int START_TIME = 0;
	public static final int TIME_INTERVAL = 1;
	public static final int MAX_FREQUENCY = 2;
	public static final int FILTER_WIDTH = 3;

	private final int mode;

	public SettingsSlider(final int mode, final int start, final int end, final int initial) {
		this.mode = mode;
		setMin(start);
		setMax(end);
		setMajorTickUnit(5);
		setMinorTickCount(4);
		setShowTickMarks(true);
		setShowTickLabels(true);
		setBlockIncrement(1);
		setSnapToTicks(true);
		setValue(initial);

		valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
				if (mode == START_TIME) {
					Settings.setStartTime(newValue.intValue());
				}
				if (mode == TIME_INTERVAL) {
					Settings.setTimeInterval(newValue.intValue());
				}
				if (mode == MAX_FREQUENCY) {
					Settings.setMaxFrequency(newValue.intValue());
				}
				if (mode == FILTER_WIDTH) {
					Settings.samplingFilterWidth = newValue.intValue();
				}
				ObservableList<Node> children = getParent().getChildrenUnmodifiable();
				for (Node child : children) {
					if (child instanceof SettingsSlider) {
						((SettingsSlider) child).refresh();
					}
				}
			}
		});
	}

	public void refresh() {
		if (mode == START_TIME) {
			setMax(Settings.getTimeUpperLimit() - Settings.getTimeInterval());
			setValue(Settings.getStartTime());
		}
		if (mode == TIME_INTERVAL) {
			setMax(Settings.getTimeUpperLimit() - Settings.getStartTime());
			setValue(Settings.getTimeInterval());
		}

	}
}

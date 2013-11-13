package com.dominantfreq.display.model.elements;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

import com.dominantfreq.model.Settings;

public class FilterBox extends ComboBox<Integer> {

	public FilterBox() {
		super(FXCollections.observableArrayList(0, 1, 2));
		setValue(0);
		getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> value, Integer oldValue, Integer newValue) {
				Settings.setSpectrumSmoothing(newValue);
			}
		});
	}
}

package com.dominantfreq.display.model.elements;

import com.dominantfreq.model.Settings;
import com.dominantfreq.service.windowfunction.Window;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

public class WindowFunctionSelector extends ComboBox<Window> {

	public WindowFunctionSelector() {
		super(FXCollections.observableArrayList(Window.values()));
		getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Window>() {
			@Override
			public void changed(ObservableValue<? extends Window> value, Window oldValue, Window newValue) {
				Settings.setWindowFunction(newValue);
			}
		});
	}

}

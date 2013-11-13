package com.dominantfreq.display.model.elements;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;

import com.dominantfreq.model.Settings;

public class AbsCheck extends CheckBox {

	public AbsCheck() {
		super();
		this.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				Settings.abs = !Settings.abs;
			}
		});
	}
}

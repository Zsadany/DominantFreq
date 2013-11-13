package com.dominantfreq.display.model;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import com.dominantfreq.display.model.elements.AbsCheck;
import com.dominantfreq.display.model.elements.FilterBox;
import com.dominantfreq.display.model.elements.SettingsSlider;
import com.dominantfreq.display.model.elements.WindowFunctionSelector;
import com.dominantfreq.model.Settings;

/**
 * A settings panel to provide a GUI interface to users to set the desired config.
 * 
 * @author Fulop Zsadany
 * 
 */
public class BulkSettingsPanel extends VBox {

	private static final String PANEL_STYLE = "-fx-background-color: #090a0c, linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%), linear-gradient(#20262b, #191d22),"
			+ "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0)); -fx-background-radius: 5,4,3,5;"
			+ "-fx-background-insets: 0,1,2,0; -fx-text-fill: white; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );";
	private static final String LABEL_STYLE = "-fx-font-family: \"Arial\"; -fx-text-fill: linear-gradient(white, #d0d0d0); -fx-font-size: 12px;";

	private WindowFunctionSelector windowFunctionSelector;
	private SettingsSlider startTime;
	private SettingsSlider timeWidth;
	private SettingsSlider frequencyWidth;
	private SettingsSlider filterWidth;

	public BulkSettingsPanel(List<String> files, boolean loading) {
		setStyle(PANEL_STYLE);

		addSeparator();
		addStartTimeSlider(loading);
		addSeparator();
		addTimeIntervalSlider(loading);
		addSeparator();
		addMaxFrequencySlider(loading);
		addSeparator();
		addFrequencyFilterWidthSlider(loading);
		addSeparator();
		addWindowFunctionSelector(loading);
		addSeparator();
		addFilterBox(loading);
		addSeparator();
		addAbsCheckbox(loading);
		addSeparator();

		addSlideEventHandlers();
	}

	private void addSeparator() {
		Separator separator = new Separator();
		separator.setPrefHeight(10);
		add(separator);
	}

	private void addStartTimeSlider(boolean loading) {
		VBox startTimeSliderBox = new VBox();
		startTimeSliderBox.getChildren().add(createStylishLabel("Start time [s]"));
		startTime = new SettingsSlider(SettingsSlider.START_TIME, 0, Settings.getTimeUpperLimit() - Settings.getTimeInterval(),
				Settings.getStartTime());
		startTime.setDisable(loading);
		startTimeSliderBox.getChildren().add(startTime);
		add(startTimeSliderBox);

	}

	private void addFrequencyFilterWidthSlider(boolean loading) {
		VBox sliderBox = new VBox();
		Label label = new Label("Spectrum Sampling Width [Hz]");
		label.setStyle(LABEL_STYLE);
		sliderBox.getChildren().add(label);
		filterWidth = new SettingsSlider(SettingsSlider.FILTER_WIDTH, 0, 5, Settings.samplingFilterWidth);
		filterWidth.setDisable(loading);
		sliderBox.getChildren().add(filterWidth);
		add(sliderBox);
	}

	private void addTimeIntervalSlider(boolean loading) {
		VBox sliderBox = new VBox();
		sliderBox.getChildren().add(createStylishLabel("Time Interval Width [s]"));
		timeWidth = new SettingsSlider(SettingsSlider.TIME_INTERVAL, 0, Settings.getTimeUpperLimit() - Settings.getStartTime(),
				Settings.getTimeInterval());
		timeWidth.setDisable(loading);
		sliderBox.getChildren().add(timeWidth);
		add(sliderBox);
	}

	private void addMaxFrequencySlider(boolean loading) {
		VBox sliderBox = new VBox();
		sliderBox.getChildren().add(createStylishLabel("Max Frequency [Hz]"));
		frequencyWidth = new SettingsSlider(SettingsSlider.MAX_FREQUENCY, 0, 30, Settings.getMaxFrequency());
		frequencyWidth.setDisable(loading);
		sliderBox.getChildren().add(frequencyWidth);
		add(sliderBox);
	}

	private void addWindowFunctionSelector(boolean loading) {
		HBox windowSelectorBox = new HBox();
		Label label = new Label("Window Function: ");
		windowSelectorBox.getChildren().add(label);
		label.setStyle(LABEL_STYLE);
		windowFunctionSelector = new WindowFunctionSelector();
		windowFunctionSelector.setValue(Settings.windowFunction);
		windowFunctionSelector.setDisable(loading);
		windowSelectorBox.getChildren().add(windowFunctionSelector);
		add(windowSelectorBox);
	}

	private void addFilterBox(boolean loading) {
		HBox checkBoxBox = new HBox();
		Label label = new Label("Spectrum Filter Level: ");
		label.setStyle(LABEL_STYLE);
		checkBoxBox.getChildren().add(label);
		FilterBox fs = new FilterBox();
		fs.setValue(Settings.spectrumSmoothing);
		fs.setDisable(loading);
		checkBoxBox.getChildren().add(fs);
		add(checkBoxBox);
	}

	private void addAbsCheckbox(boolean loading) {
		HBox checkBoxBox = new HBox();
		Label label = new Label("Abs. Values: ");
		label.setStyle(LABEL_STYLE);
		checkBoxBox.getChildren().add(label);
		AbsCheck check = new AbsCheck();
		check.setSelected(Settings.abs);
		check.setDisable(loading);
		checkBoxBox.getChildren().add(check);
		add(checkBoxBox);
	}

	private void addSlideEventHandlers() {
		startTime.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> changing, Boolean wasChanging, Boolean willChange) {
				if (wasChanging && !willChange) {
					timeWidth.refresh();
				}
			}
		});
		timeWidth.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> changing, Boolean wasChanging, Boolean willChange) {
				if (wasChanging && !willChange) {
					startTime.refresh();
				}
			}
		});
	}

	private Label createStylishLabel(String text) {
		Label label = new Label(text);
		label.setStyle(LABEL_STYLE);
		return label;
	}

	private void add(Node node) {
		getChildren().add(node);
	}
}

package com.dominantfreq.display.model;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javafx.embed.swing.JFXPanel;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;

import com.dominantfreq.display.model.elements.AbsCheck;
import com.dominantfreq.display.model.elements.DrawButton;
import com.dominantfreq.display.model.elements.FilterSpinner;
import com.dominantfreq.display.model.elements.SelectorBox;
import com.dominantfreq.display.model.elements.SettingsSlider;
import com.dominantfreq.model.Settings;
import com.dominantfreq.model.dataaccess.EcgBuffer;
import com.dominantfreq.service.windowfunction.Window;

/**
 * A settings panel to provide a GUI interface to users to set the desired
 * config.
 * 
 * @author Fulop Zsadany
 * 
 */
public class SettingsPanel extends JFXPanel {
	private static final long serialVersionUID = 1L;

	private SelectorBox ecgSelector;
	private SelectorBox windowFunctionSelector;
	private JProgressBar progressBar;
	private JLabel label;
	private SettingsSlider startTime;
	private SettingsSlider timeIntervalWidth;
	private SettingsSlider frequencyWidth;
	private SettingsSlider filterWidth;

	public SettingsPanel(List<String> files, boolean loading) {
		GridBagConstraints constraints = initLayout();
		int position = 0;
		position = addEcgSelector(loading, files, constraints, position);
		position = addDrawEcgButton(loading, constraints, position);
		position = addStartTimeSlider(loading, constraints, position);
		position = addIntervalSliders(loading, constraints, position);
		position = addFrequencyFilterWidthSlider(loading, constraints, position);
		position = addWindowFunctionSelector(loading, constraints, position);
		position = addFilterSpinner(loading, constraints, position);
		position = addAbsCheckbox(loading, constraints, position);
		position = addProcessButton(loading, constraints, position);
		addProgressBar(loading, constraints, position);
	}

	private GridBagConstraints initLayout() {
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(layout);
		c.fill = GridBagConstraints.HORIZONTAL;
		return c;
	}

	private int addEcgSelector(Boolean loading, List<String> files, GridBagConstraints constraints, int index) {
		label = new JLabel("Select ECG: ");
		constraints.gridx = 0;
		constraints.gridy = index;
		constraints.gridwidth = 1;
		this.add(label, constraints);
		ecgSelector = new SelectorBox(files);
		ecgSelector.setFunction(0);
		ecgSelector.setSelectedIndex(EcgBuffer.selectedECG.get());
		ecgSelector.addActionListener(ecgSelector);
		ecgSelector.setEnabled(!loading);
		constraints.gridx = 1;
		constraints.gridy = index;
		constraints.gridwidth = 2;
		this.add(ecgSelector, constraints);
		index++;
		constraints.insets = new Insets(5, 0, 0, 0);
		constraints.gridwidth = 3;
		constraints.ipady = 10;
		constraints.gridx = 0;
		constraints.gridy = index;
		this.add(new JSeparator(), constraints);
		index++;
		constraints.insets = new Insets(0, 0, 5, 0);
		return index;
	}

	private int addStartTimeSlider(boolean loading, GridBagConstraints constraints, int index) {
		constraints.ipady = 10;
		constraints.gridx = 0;
		constraints.gridy = index;
		this.add(new JLabel("Start time [s]"), constraints);
		index++;
		startTime = new SettingsSlider("Start time", SettingsSlider.START_TIME, 0, Settings.getTimeUpperLimit() - Settings.getTimeInterval(),
				Settings.getStartTime());
		startTime.setEnabled(!loading);
		constraints.gridx = 0;
		constraints.gridy = index;
		this.add(startTime, constraints);
		index++;
		constraints.gridx = 0;
		constraints.gridy = index;
		this.add(new JSeparator(), constraints);
		index++;
		constraints.gridx = 0;
		constraints.gridy = index;
		constraints.ipady = 0;
		return index;
	}

	private int addFrequencyFilterWidthSlider(boolean loading, GridBagConstraints constraints, int index) {
		constraints.ipady = 10;
		constraints.gridx = 0;
		constraints.gridy = index;
		this.add(new JLabel("Spectrum Sampling Width [Hz]"), constraints);
		index++;
		filterWidth = new SettingsSlider("Spectrum Sampling Filter Width", SettingsSlider.FILTER_WIDTH, 0, 5, Settings.samplingFilterWidth);
		filterWidth.setEnabled(!loading);
		constraints.gridx = 0;
		constraints.gridy = index;
		this.add(filterWidth, constraints);
		index++;
		constraints.gridx = 0;
		constraints.gridy = index;
		this.add(new JSeparator(), constraints);
		constraints.ipady = 0;
		index++;
		return index;
	}

	private int addIntervalSliders(boolean loading, GridBagConstraints constraints, int index) {
		constraints.ipady = 10;
		constraints.gridx = 0;
		constraints.gridy = index;
		this.add(new JLabel("Time Interval Width [s]"), constraints);
		index++;
		timeIntervalWidth = new SettingsSlider("Time Interval Width", SettingsSlider.TIME_INTERVAL, 0, Settings.getTimeUpperLimit() - Settings.getStartTime(),
				Settings.getTimeInterval());
		timeIntervalWidth.setEnabled(!loading);
		constraints.gridx = 0;
		constraints.gridy = index;
		this.add(timeIntervalWidth, constraints);
		index++;
		constraints.gridx = 0;
		constraints.gridy = index;
		this.add(new JSeparator(), constraints);
		index++;
		constraints.gridx = 0;
		constraints.gridy = index;
		this.add(new JLabel("Max Frequency [Hz]"), constraints);
		index++;
		frequencyWidth = new SettingsSlider("Max Frequency", SettingsSlider.MAX_FREQUENCY, 0, 30, Settings.getMaxFrequency());
		frequencyWidth.setEnabled(!loading);
		constraints.gridx = 0;
		constraints.gridy = index;
		this.add(frequencyWidth, constraints);
		index++;
		constraints.gridx = 0;
		constraints.gridy = index;
		this.add(new JSeparator(), constraints);
		constraints.ipady = 0;
		index++;
		return index;
	}

	private int addWindowFunctionSelector(boolean loading, GridBagConstraints constraints, int index) {
		label = new JLabel("Window Function: ");
		constraints.gridx = 0;
		constraints.gridy = index;
		constraints.gridwidth = 1;
		this.add(label, constraints);
		windowFunctionSelector = new SelectorBox(Window.list());
		windowFunctionSelector.setSelectedIndex(Settings.windowFunction);
		windowFunctionSelector.setFunction(1);
		windowFunctionSelector.addActionListener(windowFunctionSelector);
		windowFunctionSelector.setEnabled(!loading);
		constraints.gridx = 1;
		constraints.gridy = index;
		constraints.gridwidth = 2;
		this.add(windowFunctionSelector, constraints);
		index++;
		return index;
	}

	private int addFilterSpinner(boolean loading, GridBagConstraints constraints, int index) {
		constraints.ipady = 10;
		label = new JLabel("Spectrum Filter Level: ");
		constraints.gridx = 0;
		constraints.gridy = index;
		constraints.gridwidth = 1;
		this.add(label, constraints);
		FilterSpinner fs = new FilterSpinner();
		fs.setValue(Settings.spectrumSmoothing);
		fs.setEnabled(!loading);
		constraints.gridx = 1;
		constraints.gridy = index;
		constraints.gridwidth = 1;
		int anchor = constraints.anchor;
		constraints.anchor = GridBagConstraints.FIRST_LINE_END;
		this.add(fs, constraints);
		constraints.anchor = anchor;
		index++;
		return index;
	}

	private int addAbsCheckbox(boolean loading, GridBagConstraints constraints, int index) {
		constraints.ipady = 10;
		label = new JLabel("Abs. Values: ");
		constraints.gridx = 0;
		constraints.gridy = index;
		constraints.gridwidth = 1;
		this.add(label, constraints);
		AbsCheck af = new AbsCheck();
		af.setSelected(Settings.abs);
		af.addActionListener(af);
		af.setEnabled(!loading);
		constraints.gridx = 1;
		constraints.gridy = index;
		constraints.gridwidth = 2;
		this.add(af, constraints);
		index++;
		return index;
	}

	private void addProgressBar(boolean loading, GridBagConstraints constraints, int index) {
		constraints.ipady = 0;
		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setVisible(loading);
		constraints.gridx = 0;
		constraints.gridy = index;
		constraints.gridwidth = 3;
		this.add(progressBar, constraints);
		index++;
	}

	private int addProcessButton(boolean loading, GridBagConstraints constraints, int index) {
		DrawButton draw = new DrawButton("Process and Draw Config", false);
		draw.addActionListener(draw);
		draw.setEnabled(!loading);
		constraints.gridx = 0;
		constraints.gridy = index;
		constraints.gridwidth = 3;
		this.add(draw, constraints);
		index++;
		return index;
	}

	private int addDrawEcgButton(boolean loading, GridBagConstraints constraints, int index) {
		DrawButton draw = new DrawButton("Draw Ecg Only", true);
		draw.addActionListener(draw);
		draw.setEnabled(!loading);
		constraints.gridx = 0;
		constraints.gridy = index;
		constraints.gridwidth = 3;
		this.add(draw, constraints);
		index++;
		return index;
	}
}

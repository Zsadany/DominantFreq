package com.dominantfreq.model.dataaccess;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class EcgInfo {

	private File directory;
	private String ecgName;

	private String patient;
	private String date;
	private String startTime;
	private String stopTime;
	private String description;
	private String numOfChannels;
	private String numOfPoints;
	private String samplingRate;
	private String units;

	public EcgInfo(File directory, String ecgName) {
		this.directory = directory;
		this.ecgName = ecgName;
	}

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getNumOfChannel() {
		return numOfChannels;
	}

	public void setNumOfChannel(String numOfChannel) {
		this.numOfChannels = numOfChannel;
	}

	public String getNumOfPoints() {
		return numOfPoints;
	}

	public void setNumOfPoints(String pointsN) {
		this.numOfPoints = pointsN;
	}

	public String getSamplingRate() {
		return samplingRate;
	}

	public void setSamplingRate(String samplingRate) {
		this.samplingRate = samplingRate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStopTime() {
		return stopTime;
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public File getDirectory() {
		return directory;
	}

	public String getEcgName() {
		return ecgName;
	}

	// TODO migrate to UI utility class
	public GridPane renderToGridPane() {
		GridPane pane = new GridPane();
		pane.setHgap(5);
		pane.setVgap(5);
		pane.setPadding(new Insets(0, 10, 0, 10));

		List<List<String>> labels = Arrays.asList(Arrays.asList("Patient:", "desc.:", "units"),
				Arrays.asList("  Date:", "  start:", "  stop:"), Arrays.asList("  channels:", "  samples/channel:", "  sampling rate:"));
		List<List<String>> values = Arrays.asList(Arrays.asList(patient, description, units), Arrays.asList(date, startTime, stopTime),
				Arrays.asList(numOfChannels, numOfPoints, samplingRate));
		Font boldFont = Font.font(null, FontWeight.BOLD, 12);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Label label = new Label(labels.get(i).get(j));
				label.setFont(boldFont);
				pane.add(label, 2 * i, j);
				Label value = new Label(values.get(i).get(j));
				pane.add(value, 2 * i + 1, j);
			}
		}

		return pane;
	}
}

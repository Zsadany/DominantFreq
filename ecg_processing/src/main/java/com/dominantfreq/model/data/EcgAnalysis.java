package com.dominantfreq.model.data;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class EcgAnalysis {
	private String name;
	private Map<String, SpectrumAnalysis> analysises;

	public EcgAnalysis(String name) {
		this.name = name;
		analysises = new ConcurrentHashMap<String, SpectrumAnalysis>();
	}

	public String getName() {
		return name;
	}

	public void addSpectrumAnalysis(SpectrumAnalysis analysis) {
		analysises.put(analysis.getName(), analysis);
	}

	public Collection<SpectrumAnalysis> getAnalysises() {
		return analysises.values();
	}

	public SpectrumAnalysis getAnalysis(String name) {
		return analysises.get(name);
	}

	// TODO migrate to UI utility class
	public GridPane renderToGridPane() {
		GridPane pane = new GridPane();
		pane.setVgap(5);
		pane.setPadding(new Insets(0, 10, 0, 10));
		Font boldFont = Font.font(null, FontWeight.BOLD, 12);
		int n = 0;
		int col = 0;
		for (SpectrumAnalysis analysis : analysises.values()) {
			int row = n % 3;
			Label label = new Label("  " + analysis.getName() + " : ");
			label.setFont(boldFont);
			pane.add(label, col, row);
			Label value = new Label("" + analysis.getDominantFrequency());
			pane.add(value, col + 1, row);
			n++;
			col = row == 2 ? col + 2 : col;
		}
		return pane;
	}

}

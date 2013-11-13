package com.dominantfreq.display.model.signals;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import com.dominantfreq.display.Display;
import com.dominantfreq.model.Settings;
import com.dominantfreq.model.data.SpectrumAnalysis;
import com.dominantfreq.model.signal.RealSpectrum;
import com.dominantfreq.utils.Filter;

public class SpectrumDisplayPanel extends SignalDisplay {
	private static final long serialVersionUID = 1L;
	int hDivBy;
	int hertz;

	String dominantFrequency;
	Point2D dominantFrequencyLocation;

	private SpectrumDisplayPanel() {
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public SpectrumDisplayPanel(RealSpectrum spectrum, SpectrumAnalysis analysis) {
		this();
		height = Display.getHeight();
		width = Display.getWidth();
		xUnit = " Hz";
		hDivBy = 40 / Settings.getMaxFrequency();
		name = "Spectrum";
		initScale(width);
		double max = spectrum.getMax();
		double min = spectrum.getMin();
		double signalHeight = max - min;
		double baseline = (height / 2) + (min * (height / 3) / signalHeight) - 70;
		int numnberOfPointsToDraw = spectrum.length();
		points = new Point2D[numnberOfPointsToDraw];
		for (int i = 0; i < numnberOfPointsToDraw; i++) {
			points[i] = new Point2D.Double(10.0 + ((double) (width - settingsPanelWidth)) * (i / (double) numnberOfPointsToDraw), -15
					+ baseline - (spectrum.getSample(i) * (height / 3) / signalHeight));
		}
		initAnalysisDisplay(spectrum, analysis, width);
		dx = Settings.getMaxFrequency() * (1.0 / (width - settingsPanelWidth));
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = initRendering(g);
		drawAxes(g2);
		drawSignal(g2);
		if (showMouseCoordinates)
			drawMouseCoordinates(g2);
		drawFrequencies(g2);
	}

	private Graphics2D initRendering(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		height = getHeight();
		width = getWidth();
		return g2;
	}

	private void initScale(int width) {
		hertz = Settings.getMaxFrequency();
		scale = new double[hertz * hDivBy];
		scaleStr = new String[(hertz * hDivBy) + 1];
		for (int i = 0; i < hertz; i++) {
			for (int j = 0; j < hDivBy; j++) {
				scale[i * hDivBy + j] = 10.0 + ((width - settingsPanelWidth) * (i * hDivBy + j + 1)) / (double) (hertz * hDivBy);
				scaleStr[i * hDivBy + j] = (i + j / hDivBy) + "," + (10 / hDivBy) * (j % hDivBy);
			}
		}
		scaleStr[hertz * hDivBy] = hertz + ",0";
	}

	private void initAnalysisDisplay(RealSpectrum spec, SpectrumAnalysis analysis, int width) {
		int dominantFrequencyIndex = analysis.getDominantFrequencyIndex();
		dominantFrequency = String.valueOf(Math.round(analysis.getDominantFrequency() * 100.0) / 100.0) + " Hz";
		double freqX = ((double) (width - settingsPanelWidth)) * (dominantFrequencyIndex / (double) spec.length());
		double freqY = calcDominantFrequencyAmplitude(dominantFrequencyIndex) - 10;
		dominantFrequencyLocation = new Point2D.Double(freqX, freqY);
	}

	private void drawFrequencies(Graphics2D g2) {
		g2.setColor(new Color(0, 150, 0));
		g2.drawString(dominantFrequency, (int) dominantFrequencyLocation.getX(), (int) dominantFrequencyLocation.getY());
		g2.setColor(new Color(0, 0, 0));
	}

	private double calcDominantFrequencyAmplitude(int dominantFrequencyIndex) {
		if (dominantFrequencyIndex < 2) {
			dominantFrequencyIndex = 2;
		}
		return Filter
				.min(points[dominantFrequencyIndex - 2].getY(), points[dominantFrequencyIndex - 1].getY(),
						points[dominantFrequencyIndex].getY(), points[dominantFrequencyIndex + 1].getY(),
						points[dominantFrequencyIndex + 2].getY());
	}
}
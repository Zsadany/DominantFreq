package com.dominantfreq.display.model.signals;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import com.dominantfreq.display.controller.DisplayController;
import com.dominantfreq.model.Settings;
import com.dominantfreq.model.signal.Channel;

public class ChannelDisplayPanel extends SignalDisplay {
	private static final long serialVersionUID = 1L;
	private int sDivBy;
	private int startTime;
	private int seconds;
	int numberOfPointsToDraw;
	int startingIndex;

	private ChannelDisplayPanel() {
		addMouseListener(this);
		addMouseMotionListener(this);
		xUnit = " sec";
		name = "ECG channel";
		height = DisplayController.getHeight();
		width = DisplayController.getWidth();
	}

	public ChannelDisplayPanel(Channel channel) {
		this();
		double signalHeight = channel.getMax() - channel.getMin();
		double baseline = baseline(channel, height);
		numberOfPointsToDraw = channel.length();
		startTime = 0;
		startingIndex = 0;
		seconds = channel.length() / channel.getFrequency();
		if (!Settings.isEcgOnly()) {
			startTime = Settings.getStartTime();
			startingIndex = Settings.getStartTime() * channel.getFrequency();
//			seconds = Settings.getTimeInterval();
		}
		initScale(width);
		points = new Point2D[numberOfPointsToDraw];
		for (int i = 0; i < numberOfPointsToDraw; i++) {
			double iX = 10.0 + ((double) (width - settingsPanelWidth)) * ((double) i / (double) numberOfPointsToDraw);
			double iY = -10 + baseline - (channel.getSample(i) * (height / 3) / signalHeight);
			points[i] = new Point2D.Double(iX, iY);
		}
		dx = seconds * (1.0 / (width - settingsPanelWidth));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		height = getHeight();
		width = getWidth();
		drawAxes(g2);
		if (showMouseCoordinates) {
			drawMouseCoordinates(g2);
		}
		drawSignal(g2);
	}

	private double baseline(Channel channel, double height) {
		double signalHeight = channel.getMax() - channel.getMin();
		return (height / 2) + (channel.getMin() * (height / 3) / signalHeight) - 70;
	}

	private void initScale(int width) {
		if (seconds <= 4)
			sDivBy = 10;
		else if (seconds > 4) {
			sDivBy = 5;
			if (seconds >= 10) {
				sDivBy = 2;
				if (seconds >= 15) {
					sDivBy = 1;
				}
			}
		}
		scale = new double[seconds * sDivBy];
		scaleStr = new String[(seconds * sDivBy) + 1];
		for (int i = 0; i < seconds; i++) {
			for (int j = 0; j < sDivBy; j++) {
				scale[i * sDivBy + j] = 10.0 + ((width - settingsPanelWidth) * (i * sDivBy + j + 1)) / (seconds * sDivBy);
				scaleStr[i * sDivBy + j] = (startTime + i + j / sDivBy) + "," + ((10 * (j % sDivBy)) / sDivBy);
			}
		}
		scaleStr[seconds * sDivBy] = (startTime + seconds) + ",0";
	}
}

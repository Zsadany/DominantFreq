package com.dominantfreq.display.model.signals;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javafx.embed.swing.JFXPanel;

import javax.swing.event.MouseInputListener;

import com.dominantfreq.display.controller.DisplayController;

/**
 * Displays ECG channels and spectrums in an orthogonal system.
 * 
 * @author Fulop Zsadany
 * 
 */
public abstract class SignalDisplay extends JFXPanel implements MouseInputListener {
	private static final long serialVersionUID = 1L;
	Point2D[] points;

	double[] scale;
	String[] scaleStr;
	String name;
	int height;
	int width;
	int settingsPanelWidth = 240;

	/** Show mouse coordinates ? or not ? */
	boolean showMouseCoordinates = false;
	Double mouseX;
	Double mouseY;

	Double dx;
	String xUnit;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	protected void drawSignal(Graphics2D g2) {
		g2.drawString(name, 30, 30);
		g2.drawString(scaleStr[0], 5, height - 20);
		for (int i = 0; i < scale.length; i++) {
			g2.draw(new Line2D.Double(scale[i], height - 15, scale[i], height - 5));
			g2.drawString(scaleStr[i + 1], (int) scale[i] - 5, height - 20);
		}
		for (int i = 0; i < points.length - 1; i++) {
			g2.draw(new Line2D.Double(points[i], points[i + 1]));
		}
	}

	protected void drawAxes(Graphics2D g2) {
		g2.draw(new Line2D.Double(0, height - 10, width, height - 10));
		g2.draw(new Line2D.Double(10, height, 10, 0));
	}

	protected void drawMouseCoordinates(Graphics2D g2) {
		g2.setColor(new Color(0, 0, 250));
		g2.draw(new Line2D.Double(mouseX, (getHeight() - 10.0), mouseX, mouseY));
		g2.setColor(new Color(0, 150, 0));
		g2.drawString(("" + Math.round(100.0 * (dx * (mouseX - 10.0))) / 100.0 + "" + xUnit), (int) Math.round(mouseX) - 20, (int) Math.round(mouseY) - 20);
		g2.setColor(new Color(0, 0, 0));
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		mouseX = (double) event.getX();
		mouseY = (double) event.getY();
		DisplayController.getContentPane().repaint();
	}

	@Override
	public void mousePressed(MouseEvent event) {
		showMouseCoordinates = true;
		mouseX = (double) event.getX();
		mouseY = (double) event.getY();
		DisplayController.getContentPane().repaint();
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		showMouseCoordinates = false;
		DisplayController.getContentPane().repaint();

	}

	@Override
	public void mouseMoved(MouseEvent event) {
	}

	@Override
	public void mouseClicked(MouseEvent event) {
	}

	@Override
	public void mouseEntered(MouseEvent event) {
	}

	@Override
	public void mouseExited(MouseEvent event) {
	}

}
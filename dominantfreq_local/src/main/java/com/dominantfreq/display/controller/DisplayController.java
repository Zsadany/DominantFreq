package com.dominantfreq.display.controller;

import java.awt.Container;
import java.awt.Dimension;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.dominantfreq.display.model.DisplayMode;
import com.dominantfreq.display.model.SettingsPanel;
import com.dominantfreq.display.service.DisplayConfiguration;
import com.dominantfreq.model.Settings;
import com.dominantfreq.model.dataaccess.EcgBuffer;

public class DisplayController {

	private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();

	private static int height = 0;
	private static int width = 0;

	private static JFrame frame;
	private static Container contentPane;
	private static SpringLayout layout;

	private static SettingsPanel settingsPanel;
	private static JTabbedPane tabbedPane;

	public static void init() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		if (DisplayController.frame == null)
			DisplayController.frame = new JFrame();
		if (DisplayController.settingsPanel == null)
			DisplayController.settingsPanel = new SettingsPanel(EcgBuffer.getEcgNames(), Settings.isLoading());
		if (DisplayController.tabbedPane == null)
			DisplayController.tabbedPane = new JTabbedPane();
		if (DisplayController.contentPane == null)
			DisplayController.contentPane = DisplayController.frame.getContentPane();
		DisplayController.frame.setTitle("Szoftverfejlesztes Pitvarfibrillacios EKG Jelek Spektralis Analizisere");
		Dimension ss = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		height = ss.height - 5;
		width = ss.width + 15;
		contentPane.removeAll();
		draw();
	}

	public static void ecgOnlyDisplay() {
		EXECUTOR.execute(new DisplayConfiguration(DisplayMode.FULL_ECG_VIEW));
	}

	public static void processedEcgDisplay() {
		EXECUTOR.execute(new DisplayConfiguration(DisplayMode.NEW_PROCESSING_AND_DISPLAY));
	}

	public static void preReadECG() {
		EXECUTOR.execute(new DisplayConfiguration(DisplayMode.PRELOAD_ECG));
	}

	public static void draw() {
		EXECUTOR.execute(new DisplayConfiguration(DisplayMode.REPAINT));
	}

	public static void refresh() {
		EXECUTOR.execute(new DisplayConfiguration(DisplayMode.REPAINT));
	}

	public static int getHeight() {
		return height;
	}

	public static void setHeight(int height) {
		DisplayController.height = height;
	}

	public static int getWidth() {
		return width;
	}

	public static void setWidth(int width) {
		DisplayController.width = width;
	}

	public static JFrame getFrame() {
		return frame;
	}

	public static void setF(JFrame frame) {
		DisplayController.frame = frame;
	}

	public static Container getContentPane() {
		return contentPane;
	}

	public static void setContentPane(Container contentPane) {
		DisplayController.contentPane = contentPane;
	}

	public static SpringLayout getLayout() {
		return layout;
	}

	public static void setLayout(SpringLayout layout) {
		DisplayController.layout = layout;
	}

	public static SettingsPanel getSettingsPanel() {
		return settingsPanel;
	}

	public static void setSettingsPanel(SettingsPanel settingsPanel) {
		DisplayController.settingsPanel = settingsPanel;
	}

	public static JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public static void setTabbedPane(JTabbedPane tabbedPane) {
		DisplayController.tabbedPane = tabbedPane;
	}

}

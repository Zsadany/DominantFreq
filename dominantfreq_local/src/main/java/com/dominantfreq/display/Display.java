package com.dominantfreq.display;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.dominantfreq.display.configuration.ConfigExecutor;
import com.dominantfreq.display.model.SettingsPanel;
import com.dominantfreq.model.Settings;
import com.dominantfreq.model.dataaccess.EcgBuffer;

public class Display {
	private static int height = 0;
	private static int width = 0;

	private static JFrame frame;
	private static Container contentPane;
	private static SpringLayout layout;

	private static SettingsPanel settingsPanel;
	private static JTabbedPane tabbedPane;

	public static void init() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		if (Display.frame == null)
			Display.frame = new JFrame();
		if (Display.settingsPanel == null)
			Display.settingsPanel = new SettingsPanel(EcgBuffer.getEcgNames(), Settings.isLoading());
		if (Display.tabbedPane == null)
			Display.tabbedPane = new JTabbedPane();
		if (Display.contentPane == null)
			Display.contentPane = Display.frame.getContentPane();
		Display.frame.setTitle("Szoftverfejlesztes Pitvarfibrillacios EKG Jelek Spektralis Analizisere");
		Dimension ss = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		height = ss.height - 5;
		width = ss.width + 15;
		contentPane.removeAll();
		refresh();
	}

	public static void ecgOnlyDisplay() {
		ConfigExecutor.executeEcgOnlyView();
	}

	public static void processedEcgDisplay() {
		ConfigExecutor.executeEcgProcessor();
	}

	public static void preLoadECG() {
		ConfigExecutor.executeEcgPreload();
	}

	public static void refresh() {
		ConfigExecutor.executeRefresh();
	}

	public static int getHeight() {
		return height;
	}

	public static void setHeight(int height) {
		Display.height = height;
	}

	public static int getWidth() {
		return width;
	}

	public static void setWidth(int width) {
		Display.width = width;
	}

	public static JFrame getFrame() {
		return frame;
	}

	public static void setF(JFrame frame) {
		Display.frame = frame;
	}

	public static Container getContentPane() {
		return contentPane;
	}

	public static void setContentPane(Container contentPane) {
		Display.contentPane = contentPane;
	}

	public static SpringLayout getLayout() {
		return layout;
	}

	public static void setLayout(SpringLayout layout) {
		Display.layout = layout;
	}

	public static SettingsPanel getSettingsPanel() {
		return settingsPanel;
	}

	public static void setSettingsPanel(SettingsPanel settingsPanel) {
		Display.settingsPanel = settingsPanel;
	}

	public static JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public static void setTabbedPane(JTabbedPane tabbedPane) {
		Display.tabbedPane = tabbedPane;
	}

}

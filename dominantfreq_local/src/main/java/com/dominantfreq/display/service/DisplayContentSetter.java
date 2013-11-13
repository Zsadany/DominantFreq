package com.dominantfreq.display.service;

import java.awt.Container;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SpringLayout;

import com.dominantfreq.display.controller.DisplayController;
import com.dominantfreq.display.model.SettingsPanel;
import com.dominantfreq.display.model.signals.ChannelDisplayPanel;
import com.dominantfreq.display.model.signals.SignalDisplay;
import com.dominantfreq.display.model.signals.SpectrumDisplayPanel;
import com.dominantfreq.model.Settings;
import com.dominantfreq.model.data.Ecg;
import com.dominantfreq.model.data.EcgAnalysis;
import com.dominantfreq.model.data.RealEcgSpectrum;
import com.dominantfreq.model.data.SpectrumAnalysis;
import com.dominantfreq.model.dataaccess.EcgBuffer;
import com.dominantfreq.model.dataaccess.ResultBuffer;
import com.dominantfreq.model.signal.Channel;
import com.dominantfreq.model.signal.RealSpectrum;

public class DisplayContentSetter {

	public static synchronized void setDisplay() throws IOException {
		initSettingsPanel();
		cleanDisplay();
		initLayout();
		addContentToFrame();
		redrawDisplay();
	}

	private static synchronized void initSettingsPanel() throws IOException {
		DisplayController.setSettingsPanel(new SettingsPanel(EcgBuffer.getEcgNames(), Settings.isLoading()));
	}

	private static synchronized void cleanDisplay() {
		JFrame frame = DisplayController.getFrame();
		int state = frame.getExtendedState() | JFrame.MAXIMIZED_BOTH;
		frame.setExtendedState(state);
		DisplayController.getContentPane().removeAll();
	}

	private static synchronized void initLayout() {
		DisplayController.setLayout(new SpringLayout());
		SpringLayout layout = DisplayController.getLayout();
		Container contentPane = DisplayController.getContentPane();
		JTabbedPane tabbedPane = DisplayController.getTabbedPane();
		SettingsPanel settingsPanel = DisplayController.getSettingsPanel();
		contentPane.setLayout(layout);
		layout.putConstraint(SpringLayout.NORTH, settingsPanel, 5, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, settingsPanel, 5, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.WEST, tabbedPane, 5, SpringLayout.EAST, settingsPanel);
		layout.putConstraint(SpringLayout.EAST, tabbedPane, 5, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, tabbedPane, 5, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.SOUTH, tabbedPane, 5, SpringLayout.SOUTH, contentPane);
	}

	private static synchronized void addContentToFrame() {
		JFrame frame = DisplayController.getFrame();
		frame.add(DisplayController.getSettingsPanel());
		frame.add(DisplayController.getTabbedPane());
	}

	private static synchronized void redrawDisplay() {
		DisplayController.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DisplayController.getFrame().setVisible(true);
		DisplayController.getContentPane().revalidate();
		DisplayController.getContentPane().repaint();
	}

	public static void initTabs() {
		addDetailedTabs();
	}

	private static void addDetailedTabs() {
		int selectedTab = DisplayController.getTabbedPane().getSelectedIndex();
		DisplayController.setTabbedPane(new JTabbedPane());
		Ecg ecgToDraw = ResultBuffer.getEcgToDraw();
		RealEcgSpectrum ecgSpectrum = ResultBuffer.getEcgSpectrum();
		EcgAnalysis ecgAnalysis = ResultBuffer.getEcgAnalysis();
		for (Channel channel : ecgToDraw.getChannels()) {
			RealSpectrum spectrum = ecgSpectrum.getChannelSpectrum(channel.getName());
			SpectrumAnalysis analysis = ecgAnalysis.getAnalysis(channel.getName());
			addDetailedTab(channel, spectrum, analysis);
		}
		selectTab(selectedTab);
	}

	public static void initEcgOnlyTabs() throws IOException {
		int selectedTab = DisplayController.getTabbedPane().getSelectedIndex();
		DisplayController.setTabbedPane(new JTabbedPane());
		Ecg ecg = EcgBuffer.getSelectedEcg();
		for (Channel channel : ecg.getChannels()) {
			JPanel tab = new JPanel();
			tab.setLayout(new BoxLayout(tab, BoxLayout.PAGE_AXIS));
			SignalDisplay channelSignal = new ChannelDisplayPanel(channel);
			tab.add(channelSignal);
			DisplayController.getTabbedPane().addTab(channel.getName(), tab);
		}
		selectTab(selectedTab);
	}

	private static void addDetailedTab(Channel channel, RealSpectrum spectrum, SpectrumAnalysis analysis) {
		JPanel tab = new JPanel();
		tab.setLayout(new BoxLayout(tab, BoxLayout.PAGE_AXIS));
		SignalDisplay channelSignal = new ChannelDisplayPanel(channel);
		tab.add(channelSignal);
		SignalDisplay spectrumSignal = new SpectrumDisplayPanel(spectrum, analysis);
		tab.add(spectrumSignal);
		DisplayController.getTabbedPane().addTab(channel.getName(), tab);
	}

	private static void selectTab(int selectedTab) {
		JTabbedPane tabbedPane = DisplayController.getTabbedPane();
		int tabCount = tabbedPane.getTabCount();
		if (tabCount >= 1) {
			if (selectedTab == -1)
				tabbedPane.setSelectedIndex(0);
			else
				tabbedPane.setSelectedIndex(selectedTab);
		}
	}

	public static void fitWindowToScreen() {
		Dimension ss = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		DisplayController.setHeight(ss.height - 5);
		DisplayController.setWidth(ss.width + 15);
	}
}

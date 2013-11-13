package com.dominantfreq.display.model.elements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.dominantfreq.display.controller.DisplayController;
import com.dominantfreq.model.Settings;

public class DrawButton extends JButton implements ActionListener {
	private static final long serialVersionUID = 7013305622329391154L;

	boolean ecgOnly;

	public DrawButton(String str, boolean ecgOnly) {
		super(str);
		this.ecgOnly = ecgOnly;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Settings.setLoading(true);
		DisplayController.refresh();
		if (ecgOnly) {
			Settings.setEcgOnly(true);
			DisplayController.ecgOnlyDisplay();
		} else {
			Settings.setEcgOnly(false);
			DisplayController.processedEcgDisplay();
		}
	}

}

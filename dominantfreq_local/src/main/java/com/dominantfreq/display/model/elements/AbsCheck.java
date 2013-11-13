package com.dominantfreq.display.model.elements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import com.dominantfreq.model.Settings;

public class AbsCheck extends JCheckBox implements ActionListener {

	private static final long serialVersionUID = 6268136321997934867L;

	public AbsCheck() {
		super();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Settings.abs = !Settings.abs;
	}
}

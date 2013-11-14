package com.dominantfreq.display.model.checkboxes;

import javax.swing.JCheckBox;

import com.dominantfreq.display.model.actionlisteners.CheckActionListener;

public class AbsCheck extends JCheckBox {
	private static final long serialVersionUID = 3374910407143663110L;

	public AbsCheck() {
		super();
		addActionListener(CheckActionListener.getInstance());
	}
}

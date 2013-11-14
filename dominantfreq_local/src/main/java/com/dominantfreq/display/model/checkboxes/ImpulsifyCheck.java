package com.dominantfreq.display.model.checkboxes;

import javax.swing.JCheckBox;

import com.dominantfreq.display.model.actionlisteners.CheckActionListener;

public class ImpulsifyCheck extends JCheckBox {
	private static final long serialVersionUID = -207010000080326232L;

	public ImpulsifyCheck() {
		super();
		addActionListener(CheckActionListener.getInstance());
	}
}

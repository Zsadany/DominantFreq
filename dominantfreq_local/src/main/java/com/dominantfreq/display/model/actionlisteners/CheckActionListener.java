package com.dominantfreq.display.model.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.dominantfreq.display.Display;
import com.dominantfreq.display.model.checkboxes.AbsCheck;
import com.dominantfreq.display.model.checkboxes.ImpulsifyCheck;
import com.dominantfreq.model.Settings;

public class CheckActionListener implements ActionListener {

	private static final CheckActionListener INSTANCE = new CheckActionListener();

	private CheckActionListener() {
	}

	public static ActionListener getInstance() {
		return INSTANCE;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof AbsCheck) {
			Settings.setAbs(!Settings.getAbs());
			if (!Settings.getAbs())
				Settings.setImpulsify(false);
		} else if (e.getSource() instanceof ImpulsifyCheck) {
			Settings.setImpulsify(!Settings.getImpulsify());
			if (Settings.getImpulsify())
				Settings.setAbs(true);
		}
		Display.refresh();
	}
}

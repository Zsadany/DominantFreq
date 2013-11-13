package com.dominantfreq.display.model.elements;

import javax.swing.JComboBox;

import com.dominantfreq.display.model.DisplayMode;
import com.dominantfreq.display.service.DisplayConfiguration;
import com.dominantfreq.model.Settings;
import com.dominantfreq.model.dataaccess.EcgBuffer;
import com.dominantfreq.service.windowfunction.Window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SelectorBox extends JComboBox<Object> implements ActionListener {

	private static final long serialVersionUID = 7437322222497566334L;

	int func;

	public SelectorBox(List<String> str) {
		super(str.toArray());
	}

	public SelectorBox(String[] str) {
		super(str);
	}

	/**
	 * Sets the function of the ComboBox (0- ECG selector , 1 - Window Function
	 * selector, 2 - Fourier selector )
	 */
	public void setFunction(int i) {
		func = i;
	}

	public void setSelectedIndex(int i) {
		super.setSelectedIndex(i);
	}

	public void setSelectedIndex(Enum<?> value) {
		int i = 0;
		for (; i < getItemCount(); i++) {
			if (value.toString().equals(getItemAt(i)))
				break;
		}
		setSelectedIndex(i);
	}

	public void actionPerformed(ActionEvent event) {

		if (this.func == 0) {
			Object selected = getSelectedItem();
			int i = 0;
			for (; i < getItemCount(); i++) {
				if (selected.toString().equals(getItemAt(i)))
					break;
			}
			EcgBuffer.selectedECG.set(i);
			new DisplayConfiguration(DisplayMode.PRELOAD_ECG);

		}

		else if (this.func == 1) {
			Object selected = getSelectedItem();
			Settings.windowFunction = Window.valueOf(((String) selected).toUpperCase());
		}

	}
}

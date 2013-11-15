package com.dominantfreq.display;

import java.io.IOException;

import javax.swing.UnsupportedLookAndFeelException;

import com.dominantfreq.model.dataaccess.EcgBuffer;

/**
 * Main class of ECG signal processing
 * 
 * @author Fulop Zsadany
 */
public class Main {

	public static void main(String[] args) {
		try {
			run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void run() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		EcgBuffer.initDataBuffer();
		Display.init();
		Display.preLoadECG();
	}
}

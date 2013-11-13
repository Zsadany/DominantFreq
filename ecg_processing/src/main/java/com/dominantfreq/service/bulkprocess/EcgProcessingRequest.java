package com.dominantfreq.service.bulkprocess;

import java.io.File;

public class EcgProcessingRequest {

	private File folder;
	private String fileName;

	public EcgProcessingRequest(File folder, String fileName) {
		this.folder = folder;
		this.fileName = fileName;
	}

	public File getFolder() {
		return folder;
	}

	public String getFileName() {
		return fileName;
	}

}

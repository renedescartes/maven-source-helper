package com.ekanathk.maven.gui;

import javax.swing.JTextArea;

import com.ekanathk.maven.core.Artifact;
import com.ekanathk.maven.core.SourceDownload;

class SourceDownloadRunnable implements Runnable {
	private Artifact artifact;
	private SourceDownload sourceDownload;
	private LogWindow logWindow;
	
	public SourceDownloadRunnable(SourceDownload sourceDownload, Artifact artifact, JTextArea textArea) {
		this.sourceDownload = sourceDownload;
		this.artifact = artifact;
		this.logWindow = new LogWindow(textArea);
	}

	public void run() {
		SourceDownload.getLog().addHandler(logWindow);
		try {
			sourceDownload.attemptDownload(artifact);
		} catch(Exception ex) {
			logWindow.showInfo("Error while downloading/copying - " + ex.getMessage());
			throw new RuntimeException(ex);
		}
		SourceDownload.getLog().removeHandler(logWindow);
	}
}

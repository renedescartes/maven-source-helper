package com.ekanathk.maven.gui;

import com.ekanathk.maven.core.Artifact;
import com.ekanathk.maven.core.SourceDownload;

class SourceDownloadRunnable implements Runnable {
	private Artifact artifact;
	private SourceDownload sourceDownload;
	private LogWindow logWindow;
	
	public SourceDownloadRunnable(SourceDownload sourceDownload, Artifact artifact) {
		this.sourceDownload = sourceDownload;
		this.artifact = artifact;
		this.logWindow = new LogWindow("Source Download Progress", 500, 200);
	}

	public void run() {
		SourceDownload.getLog().addHandler(logWindow.getHandler());
		sourceDownload.attemptDownload(artifact);
		SourceDownload.getLog().removeHandler(logWindow.getHandler());
	}
}

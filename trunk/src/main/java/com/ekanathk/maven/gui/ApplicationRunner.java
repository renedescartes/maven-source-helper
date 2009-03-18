package com.ekanathk.maven.gui;

import javax.swing.UIManager;

public class ApplicationRunner {

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new DownloadFrame();
	}
}

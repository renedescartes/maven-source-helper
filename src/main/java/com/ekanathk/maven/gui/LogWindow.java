package com.ekanathk.maven.gui;

import java.util.logging.ErrorManager;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class LogWindow extends Handler {
	private JTextArea textArea = null;

	private static String LINE_SEPERATOR = System.getProperty("line.separator");
	
	public LogWindow(JTextArea textArea) {
		this.textArea = textArea;
		this.textArea.setText("");// blankify
	}

	public void showInfo(final String data) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				textArea.append(data + LINE_SEPERATOR + LINE_SEPERATOR);
				textArea.repaint();
			}
		});
	}
	
	public synchronized void publish(LogRecord record) {
		if (!isLoggable(record))
			return;
		try {
			showInfo(record.getMessage());
		} catch (Exception ex) {
			reportError(null, ex, ErrorManager.WRITE_FAILURE);
		}
	}

	public void close() {
	}

	public void flush() {
	}
}

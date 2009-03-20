package com.ekanathk.maven.gui;

import static com.ekanathk.maven.gui.SwingUtil.centerComponentOnScreen;

import java.util.logging.ErrorManager;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LogWindow extends JFrame {
	private JTextArea textArea = null;

	private JScrollPane pane = null;

	private WindowHandler handler;

	private static String LINE_SEPERATOR = System.getProperty("line.separator");
	
	public LogWindow(String title, int width, int height) {
		super(title);
		setSize(width, height);
		textArea = new JTextArea();
		pane = new JScrollPane(textArea);
		getContentPane().add(pane);
		setVisible(true);
		handler = new WindowHandler(this);
		centerComponentOnScreen(this);
	}

	public Handler getHandler() {
		return handler;
	}

	public void showInfo(String data) {
		textArea.append(data + LINE_SEPERATOR);
		this.getContentPane().validate();
	}
}

class WindowHandler extends Handler {
	// the window to which the logging is done
	private LogWindow window = null;

	public WindowHandler(LogWindow window) {
		this.window = window;
	}

	public synchronized void publish(LogRecord record) {
		if (!isLoggable(record))
			return;
		try {
			window.showInfo(record.getMessage());
		} catch (Exception ex) {
			reportError(null, ex, ErrorManager.WRITE_FAILURE);
		}

	}

	public void close() {
	}

	public void flush() {
	}
}

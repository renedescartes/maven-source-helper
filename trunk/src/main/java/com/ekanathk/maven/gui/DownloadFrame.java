package com.ekanathk.maven.gui;

import static com.ekanathk.maven.gui.SwingUtil.centerComponentOnScreen;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.ekanathk.maven.core.SourceDownload;

public class DownloadFrame extends JFrame implements ActionListener {

	private static final Logger logger = Logger.getLogger(DownloadFrame.class.getName());
	
	private JButton download = new JButton("Download");
	private ArtifactDialog artifactDialog;
	
	public DownloadFrame() {
		super("Maven source downloader application");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 600);
        artifactDialog = new ArtifactDialog();
		this.add(artifactDialog, BorderLayout.CENTER);
        this.add(download, BorderLayout.SOUTH);
        download.addActionListener(this);
        this.pack();
        this.setSize(900, 600);
        this.setVisible(true);
        centerComponentOnScreen(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		SourceDownload s = new SourceDownload();
		LogWindow window = new LogWindow("Source Download Progress", 500, 200);
		SourceDownload.getLog().addHandler(window.getHandler());
		try {
			s.attemptDownload(artifactDialog.getArtifact());
		} catch (IOException ex) {
			logger.log(Level.SEVERE, "Cannot download artifact", ex);
		}
		SourceDownload.getLog().addHandler(window.getHandler());
	}
}

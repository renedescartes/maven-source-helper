package com.ekanathk.maven.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.ekanathk.maven.core.Artifact;
import com.ekanathk.maven.core.SourceDownload;

public class DownloadFrame extends JFrame implements ActionListener {

	private static final Logger logger = Logger.getLogger(DownloadFrame.class.getName());
	
	private JTextField groupId = new JTextField(20);
	private JTextField artifactId = new JTextField(20);
	private JTextField version = new JTextField(20);
	private JButton download = new JButton("Download");
	
	public DownloadFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 600);
        this.add(new ArtifactDialog(), BorderLayout.CENTER);
        this.add(download, BorderLayout.SOUTH);
        download.addActionListener(this);
        this.pack();
        this.setSize(900, 600);
        this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		SourceDownload s = new SourceDownload();
		try {
			s.attemptDownload(new Artifact(groupId.getText(), artifactId.getText(), version.getText()));
		} catch (IOException ex) {
			logger.log(Level.SEVERE, "Cannot download artifact", ex);
		}
	}
}

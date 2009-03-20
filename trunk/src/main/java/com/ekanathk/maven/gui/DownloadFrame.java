package com.ekanathk.maven.gui;

import static com.ekanathk.maven.gui.SwingUtil.centerComponentOnScreen;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.ekanathk.maven.core.MavenDefaults;
import com.ekanathk.maven.core.SourceDownload;

public class DownloadFrame extends JFrame implements ActionListener {

	private JButton download = new JButton("Download");
	private ArtifactDialog artifactDialog = new ArtifactDialog();
	private JTextField localRepoField = new JTextField(mavenDefaults.getStandardLocalRepositoryPath());
	private static MavenDefaults mavenDefaults = new MavenDefaults();
	
	public DownloadFrame() {
		super("Maven source downloader application");
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 600);
		this.add(artifactDialog);
		this.add(Box.createVerticalStrut(15));
		addLocalRepoPanel();
        this.add(download);
        download.addActionListener(this);
        this.pack();
        this.setVisible(true);
        centerComponentOnScreen(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		SourceDownload s = new SourceDownload();
		SourceDownloadRunnable t = new SourceDownloadRunnable(s, artifactDialog.getArtifact());
		new Thread(t).start();
	}
	
	private final void addLocalRepoPanel() {
		JPanel localRepoPanel = new JPanel(new FlowLayout());
		localRepoPanel.add(new JLabel("Local Repository Path"));
		localRepoPanel.add(localRepoField);
		localRepoPanel.setBorder(BorderFactory.createTitledBorder("Local Repo Settings"));
		this.add(localRepoPanel);
	}
}

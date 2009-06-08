package com.ekanathk.maven.gui;

import static com.ekanathk.maven.gui.SwingUtil.centerComponentOnScreen;
import static com.ekanathk.maven.gui.SwingUtil.handleException;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.ekanathk.maven.core.MavenSettings;
import com.ekanathk.maven.core.SourceDownload;

public class DownloadFrame extends JFrame implements ActionListener {

	private JButton download = new JButton("Download");
	private ArtifactDialog artifactDialog = new ArtifactDialog();
	private JTextField localRepoField;
	private RepoListDialog repoListDialog;
	private MavenSettings mavenSettings;
	private JTextArea progressArea = new JTextArea(15, 80);
	
	public DownloadFrame() {
		super("Maven source downloader application");
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900, 600);
		initFields();
		this.add(artifactDialog);
		this.add(Box.createVerticalStrut(15));
		addLocalRepoPanel();
		addRemoteRepoList();
		addProgressDisplayArea();
		addDownloadButton();
		this.pack();
		this.setVisible(true);
		centerComponentOnScreen(this);
	}

	public void actionPerformed(ActionEvent e) {
		try {
			mavenSettings.setLocalRepositoryPath(localRepoField.getText());
			mavenSettings.setRepositoryList(repoListDialog.getListData());
			SourceDownload s = new SourceDownload(mavenSettings);
			SourceDownloadRunnable t = new SourceDownloadRunnable(s,
					artifactDialog.getArtifact(), progressArea);
			mavenSettings.saveConfig();
			new Thread(t).start();
		} catch (Exception ex) {
			handleException(ex.getMessage());
		}
	}

	private final void initFields() {
		try {
			mavenSettings = MavenSettings.readConfig();
		} catch(Exception e) {
			mavenSettings = new MavenSettings();
		}
		localRepoField = new JTextField(mavenSettings.getLocalRepositoryPath(), 40);
		repoListDialog = new RepoListDialog(mavenSettings.getRepositoryList());
	}
	
	private final void addLocalRepoPanel() {
		JPanel localRepoPanel = new JPanel(new FlowLayout());
		localRepoPanel.add(new JLabel("Local Repository Path"));
		localRepoPanel.add(localRepoField);
		JButton dirChooseButton = new JButton("Browse..");
		dirChooseButton.addActionListener(new DirChooser(localRepoField));
		localRepoPanel.add(dirChooseButton);
		localRepoPanel.setBorder(BorderFactory
				.createTitledBorder("Local Repo Settings"));
		this.add(localRepoPanel);
		this.add(Box.createVerticalStrut(15));
	}

	private final void addRemoteRepoList() {
		this.add(repoListDialog);
		this.add(Box.createVerticalStrut(15));
	}
	
	private void addProgressDisplayArea() {
		JPanel logPanel = new JPanel(new FlowLayout());
		this.progressArea.setLineWrap(true);
		this.progressArea.setEditable(false);
		logPanel.setBorder(BorderFactory.createTitledBorder("Maven source helper progress information"));
		logPanel.add(new JScrollPane(this.progressArea));
		this.add(logPanel);
		this.add(Box.createVerticalStrut(15));
	}
	
	private void addDownloadButton() {
		this.add(download);
		this.add(Box.createVerticalStrut(15));
		download.addActionListener(this);
	}
	
}

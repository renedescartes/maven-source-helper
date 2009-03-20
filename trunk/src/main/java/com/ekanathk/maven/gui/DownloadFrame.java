package com.ekanathk.maven.gui;

import static com.ekanathk.maven.gui.SwingUtil.centerComponentOnScreen;
import static com.ekanathk.maven.gui.SwingUtil.handleException;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.ekanathk.maven.core.MavenSettings;
import com.ekanathk.maven.core.SavedClass;
import com.ekanathk.maven.core.SourceDownload;

public class DownloadFrame extends JFrame implements ActionListener {

	private JButton download = new JButton("Download");
	private ArtifactDialog artifactDialog = new ArtifactDialog();
	private JTextField localRepoField = new JTextField(mavenSettings
			.getLocalRepositoryPath(), 40);
	private RepoListDialog repoListDialog = new RepoListDialog(mavenSettings
			.getRepositoryList());
	private static SavedClass<MavenSettings> savedClass = new SavedClass<MavenSettings>(MavenSettings.class);
	private static MavenSettings mavenSettings = savedClass.readObject();

	public DownloadFrame() {
		super("Maven source downloader application");
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900, 600);
		this.add(artifactDialog);
		this.add(Box.createVerticalStrut(15));
		addLocalRepoPanel();
		addRemoteRepoList();
		this.add(download);
		download.addActionListener(this);
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
					artifactDialog.getArtifact());
			savedClass.saveObject(mavenSettings);
			new Thread(t).start();
		} catch (Exception ex) {
			handleException(ex.getMessage());
		}
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

}

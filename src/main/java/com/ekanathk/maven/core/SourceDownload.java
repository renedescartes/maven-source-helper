package com.ekanathk.maven.core;

import static com.ekanathk.maven.core.CommonUtil.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class SourceDownload {
	private static final Logger log = Logger.getLogger(SourceDownload.class
			.getName());

	private static MavenDefaults mavenDefaults = new MavenDefaults();

	private List<String> repositoryList;

	private String localRepositoryPath;

	public SourceDownload() {
		this(mavenDefaults.getStandardRepoList());
	}
	
	public SourceDownload(String... repositories) {
		this(Arrays.asList(repositories));
	}

	public SourceDownload(List<String> repositories) {
		this(repositories, mavenDefaults.getStandardLocalRepositoryPath());
	}

	public SourceDownload(List<String> repositories, String localRepositoryPath) {
		assertTrue(!isEmpty(localRepositoryPath),
				"The local repository path cannot be null or empty");
		assertNotNull(repositories, "The repositories cannot be null");
		assertTrue(!repositories.isEmpty(),
				"The repository list cannot be empty");
		for (String s : repositories) {
			assertTrue(isValidUrl(s), "One of the repositories at [" + s
					+ "] is not a valid url");
		}
		assertTrue(isValidDirectory(localRepositoryPath),
				"The local repository path at [" + localRepositoryPath
						+ "] is not a valid directory");
		this.repositoryList = Collections.unmodifiableList(repositories);
		this.localRepositoryPath = localRepositoryPath;
	}

	public boolean attemptDownload(Artifact artifact) throws IOException {
		assertTrue(!isEmpty(artifact.getGroupId()), "The group id cannot be empty");
		assertTrue(!isEmpty(artifact.getArtifactId()), "The artifact id cannot be empty");
		assertTrue(!isEmpty(artifact.getVersion()), "The version cannot be empty");
		String groupId = artifact.getGroupId().replace('.', '/');
		String fileName = MessageFormat.format(
				"/{0}/{1}/{2}/{1}-{2}-sources.jar", groupId, artifact
						.getArtifactId(), artifact.getVersion());
		for (String repo : repositoryList) {
			String jarUrl = repo + fileName;
			log.info("Attempting to download [" + jarUrl + "]");
			if (isValidUrl(jarUrl)) {
				String localUrl = localRepositoryPath + fileName;
				File file = new File(localUrl);
				if (file.exists()) {
					file.delete();
				}
				file.createNewFile();
				copyPaths(jarUrl, localUrl);
				return true;
			}
			log.info("The source jar is not found at [" + jarUrl + "]");
		}
		log.info("None of the repositories " + repositoryList + "contains a source artifact for [" + artifact + "]");
		return false;
	}

	private void copyPaths(String jarUrl, String localUrl)
			throws MalformedURLException, IOException {
		log.info("Copying from [" + jarUrl + "]  to [" + localUrl + "]");
		InputStream is = new URL(jarUrl).openStream();
		OutputStream os = new FileOutputStream(new File(localUrl));
		copyStreams(is, os);
		closeQuietly(is, os);
		log.info("Copied successfully");
	}
}

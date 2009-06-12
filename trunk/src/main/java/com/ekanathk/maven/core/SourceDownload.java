package com.ekanathk.maven.core;

import static com.ekanathk.maven.core.CommonUtil.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.logging.Logger;

public class SourceDownload {
	private static final Logger log = Logger.getLogger(SourceDownload.class
			.getName());

	private MavenSettings mavenSettings;

	public SourceDownload(MavenSettings mavenSettings) {
		String localRepositoryPath = mavenSettings.getLocalRepositoryPath();
		String[] repositoryList = mavenSettings.getRepositoryList();
		assertTrue(!isEmpty(localRepositoryPath),
				"The local repository path cannot be null or empty");
		assertNotNull(repositoryList, "The repositories cannot be null");
		assertTrue(repositoryList.length > 0,
				"The repository list cannot be empty");
		for (String s : repositoryList) {
			assertTrue(isValidUrl(s), "One of the repositories at [" + s
					+ "] is not a valid url");
		}
		assertTrue(isValidDirectory(localRepositoryPath),
				"The local repository path at [" + localRepositoryPath
						+ "] is not a valid directory");
		this.mavenSettings = mavenSettings;
	}

	public boolean attemptDownload(Artifact artifact) {
		assertTrue(!isEmpty(artifact.getGroupId()), "The group id cannot be empty");
		assertTrue(!isEmpty(artifact.getArtifactId()), "The artifact id cannot be empty");
		assertTrue(!isEmpty(artifact.getVersion()), "The version cannot be empty");
		String groupId = artifact.getGroupId().replace('.', '/');
		String fileName = MessageFormat.format(
				"/{0}/{1}/{2}/{1}-{2}-sources.jar", groupId, artifact
						.getArtifactId(), artifact.getVersion());
		for (String repo : mavenSettings.getRepositoryList()) {
			String jarUrl = repo + fileName;
			log.info("Attempting to download [" + jarUrl + "]");
			if (isValidUrl(jarUrl)) {
				String localUrl = mavenSettings.getLocalRepositoryPath() + fileName;
				File file = new File(localUrl);
				if (file.exists()) {
					file.delete();
				}
				file.getParentFile().mkdirs();
				try {
					copyPaths(jarUrl, localUrl);
				} catch (IOException e) {
					throw new DownloadException("Could not copy [" + jarUrl + "] to [" + localUrl + "]");
				}
				return true;
			}
			log.info("The source jar is not found at [" + jarUrl + "]");
		}
		log.info("None of the repositories " + Arrays.toString(mavenSettings.getRepositoryList()) 
				+ "contains a source artifact for [" + artifact + "]");
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

	public static Logger getLog() {
		return log;
	}
}

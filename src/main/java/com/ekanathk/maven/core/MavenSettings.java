package com.ekanathk.maven.core;

import java.text.MessageFormat;
import java.util.Arrays;

import static com.ekanathk.maven.core.IOUtil.*;

public class MavenSettings {

	private static final String STANDARD_MAVEN_REPO_PATTERN = "C:\\Documents and Settings\\{0}\\.m2\\repository\\";

	private static final String STANDARD_MAVEN_REPO = MessageFormat.format(
			STANDARD_MAVEN_REPO_PATTERN, System.getProperty("user.name"));
	
	private static final String[] STANDARD_REPO_LIST = new String[] {
			"http://repo1.maven.org/maven2",
			"http://static.appfuse.org/repository",
			"http://nexus.openqa.org/content/repositories/releases"};

	private String localRepositoryPath;
	private String[] repositoryList;
	private static final String STRING_FORMAT = "localRepositoryPath~[{0}]~repositoryList~{1}";
	public static final String CONFIG_FILE_NAME = "MavenSettings.xml";
	
	public MavenSettings() {
		this(STANDARD_MAVEN_REPO, STANDARD_REPO_LIST);
	}
	public MavenSettings(String localRepositoryPath, String[] repositoryList) {
		this.localRepositoryPath = localRepositoryPath;
		this.repositoryList = repositoryList;
	}
	public String getLocalRepositoryPath() {
		return localRepositoryPath;
	}
	public void setLocalRepositoryPath(String localRepositoryPath) {
		this.localRepositoryPath = localRepositoryPath;
	}
	public String[] getRepositoryList() {
		return repositoryList;
	}
	public void setRepositoryList(String[] repositoryList) {
		this.repositoryList = repositoryList;
	}
	public synchronized void saveConfig() {
		writeToFile(MessageFormat.format(STRING_FORMAT, localRepositoryPath, Arrays.toString(repositoryList)), CONFIG_FILE_NAME);
	}
	public static synchronized MavenSettings readConfig() {
		String[] content = readFromFile(CONFIG_FILE_NAME).split("~");
		String[] repoList = content[3].split(",");
		return new MavenSettings(content[1], repoList);
	}
}

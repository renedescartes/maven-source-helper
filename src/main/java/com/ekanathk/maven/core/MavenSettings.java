package com.ekanathk.maven.core;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

public class MavenSettings {

	private static final String STANDARD_MAVEN_REPO_PATTERN = "C:\\Documents and Settings\\{0}\\.m2\\repository\\";

	private static final String STANDARD_MAVEN_REPO = MessageFormat.format(
			STANDARD_MAVEN_REPO_PATTERN, System.getProperty("user.name"));
	
	private static final List<String> STANDARD_REPO_LIST = Arrays.asList(
			"http://repo1.maven.org/maven2",
			"http://static.appfuse.org/repository",
			"http://nexus.openqa.org/content/repositories/releases");

	private String localRepositoryPath;
	private List<String> repositoryList;
	
	public MavenSettings() {
		this(STANDARD_MAVEN_REPO, STANDARD_REPO_LIST);
	}
	public MavenSettings(String localRepositoryPath, List<String> repositoryList) {
		this.localRepositoryPath = localRepositoryPath;
		this.repositoryList = repositoryList;
	}
	public String getLocalRepositoryPath() {
		return localRepositoryPath;
	}
	public void setLocalRepositoryPath(String localRepositoryPath) {
		this.localRepositoryPath = localRepositoryPath;
	}
	public List<String> getRepositoryList() {
		return repositoryList;
	}
	public void setRepositoryList(List<String> repositoryList) {
		this.repositoryList = repositoryList;
	}
}

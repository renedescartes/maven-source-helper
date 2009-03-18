package com.ekanathk.maven.core;

import static com.ekanathk.maven.core.CommonUtil.assertTrue;
import static com.ekanathk.maven.core.CommonUtil.isEmpty;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

public class MavenDefaults {

	private static final String STANDARD_MAVEN_REPO_PATH = "C:\\Documents and Settings\\{0}\\.m2\\repository\\";

	private static final List<String> STANDARD_REPO_LIST = Arrays.asList(
			"http://repo1.maven.org/maven2");

	public String getStandardLocalRepositoryPath() {
		String detectedUserName = System.getProperty("user.name");
		assertTrue(
				!isEmpty(detectedUserName),
				"Could not detect username from the os. This is usually the Java System property [user.name]");
		return getStandardLocalRepositoryPath(detectedUserName);
	}

	public String getStandardLocalRepositoryPath(String userName) {
		return MessageFormat.format(STANDARD_MAVEN_REPO_PATH, userName);
	}

	public List<String> getStandardRepoList() {
		return STANDARD_REPO_LIST;
	}
}

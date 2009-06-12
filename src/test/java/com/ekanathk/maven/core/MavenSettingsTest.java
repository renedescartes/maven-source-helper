package com.ekanathk.maven.core;

import junit.framework.TestCase;

public class MavenSettingsTest extends TestCase {

	public void testSimple() {
		String repo = "C:\\Documents and Settings\\ekanathk\\.m2\\repository\\";
		String str = "localRepositoryPath~[" + repo + "]~repositoryList~[http://repo1.maven.org/maven2, http://static.appfuse.org/repository, http://nexus.openqa.org/content/repositories/releases]";
		MavenSettings settings = MavenSettings.readFromString(str);
		assertEquals(repo, settings.getLocalRepositoryPath());
		assertEquals("http://repo1.maven.org/maven2", settings.getRepositoryList()[0]);
		assertEquals("http://static.appfuse.org/repository", settings.getRepositoryList()[1]);
		assertEquals("http://nexus.openqa.org/content/repositories/releases", settings.getRepositoryList()[2]);
	}
}

package com.ekanathk.maven.core;

import junit.framework.TestCase;

public class CommonUtilTest extends TestCase {

	public void testStringTrim() {
		assertEquals("xxx", CommonUtil.trimBraces("[xxx]"));
		assertEquals("xxxqwer", CommonUtil.trimBraces("[xxxqwer]"));
		try {
			CommonUtil.trimBraces("");
			fail("No exception");
		} catch(DownloadException e) {
			//pass
		}
		try {
			CommonUtil.trimBraces(null);
			fail("No exception");
		} catch(DownloadException e) {
			//pass
		}
	}
}

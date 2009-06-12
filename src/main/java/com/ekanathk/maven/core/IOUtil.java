package com.ekanathk.maven.core;

import java.io.*;

public class IOUtil {

	public static void writeToFile(String content, String fileName) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(
					fileName));
			out.write(content);
			out.close();
		} catch (IOException e) {
			throw new RuntimeException("Could not save to file [" + fileName + "]");
		}
	}
	
	public static String readFromFile(String fileName) {
		try {
	        BufferedReader in = new BufferedReader(new FileReader(fileName));
	        StringBuffer buffer = new StringBuffer();
	        String str = null;
	        while ((str = in.readLine()) != null) {
	            buffer.append(str);
	        }
	        in.close();
	        return buffer.toString();
	    } catch (IOException e) {
	    	throw new RuntimeException("Could not read from file [" + fileName + "]");
	    }
	}
}

package com.ekanathk.maven.core;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An utility class which has common methods needed by the application involving
 * Strings, Arrays and Collections.
 *
 * @author Kannan
 * @version 1.0
 */
public abstract class CommonUtil {

	private static final Logger logger = Logger.getLogger(CommonUtil.class.getName());

	public static String toStringMaker(Object o, Object... fieldsAndValues) {
        assertTrue(
                fieldsAndValues.length % 2 == 0,
                "Please pass even number of values of the form field1, value1, field2, value2 etc");
        StringBuffer sb = new StringBuffer(o.getClass().getName() + "@"
                + Integer.toHexString(o.hashCode()));
        for (int i = 0; i < fieldsAndValues.length; i += 2) {
            sb.append(", ").append(fieldsAndValues[i]).append("=");
            sb.append("[").append(fieldsAndValues[i + 1]).append("]");
        }
        return sb.toString();
    }

    public static boolean isEmpty(String string) {
        return string == null || "".equals(string.trim());
    }

    public static void assertNotNull(Object o, String message) {
        assertTrue(o != null, message);
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static boolean isValidUrl(String location) {
		InputStream stream = null;
		try {
			URL url = new URL(location);
			stream = url.openStream();
			stream.read();
			return true;
		} catch (MalformedURLException e) {
			logger.log(Level.SEVERE, "Bad url [" + location + "]", e);
			return false;
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Bad url [" + location + "]", e);
			return false;
		} finally {
			closeQuietly(stream);
		}
	}

    public static boolean isValidDirectory(String location) {
    	try {
    		return new File(location).isDirectory();
    	} catch (Exception e) {
    		return false;
    	}
    }
    
	public static void closeQuietly(Closeable... streams) {
		try {
			for(Closeable c : streams) {
				if(c != null) {
					c.close();
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Cannot close one of the streams", e);
		}
	}
	
	public static void copyStreams(InputStream in, OutputStream out) throws IOException {
        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
    }
}
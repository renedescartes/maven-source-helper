package com.ekanathk.maven.core;

import static com.ekanathk.maven.core.CommonUtil.assertNotNull;
import static com.ekanathk.maven.core.CommonUtil.assertTrue;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

public class SavedClass<T> {

	Class<?> clazz;
	
	public SavedClass(Class<?> clazz) {
		this.clazz = clazz;
	}

	public void saveObject(T o) {
		assertNotNull(o, "The object to be saved cannot be null");
		assertTrue(o.getClass().equals(clazz), "The object [" + o + "] is not an instance of [" + clazz + "]");
		try {
			XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(
			        new FileOutputStream(getFileName())));
			encoder.writeObject(o);
		    encoder.close();
		} catch (FileNotFoundException e) {
			throw new DownloadException("Could not create file [" + getFileName() + "]");
		}
	}

	@SuppressWarnings("unchecked")
	public T readObject() {
		try {
			if(!new File(getFileName()).exists()) {
				return (T) clazz.newInstance();
			}
	        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(
	            new FileInputStream(getFileName())));
	        Object o = decoder.readObject();
	        decoder.close();
	        return (T) o;
	    } catch (FileNotFoundException e) {
	    	//wont happen
	    	throw new DownloadException("Could not read from [" + getFileName() + "]");
	    } catch (InstantiationException e) {
	    	throw new DownloadException("Could not read from [" + getFileName() + "]");
		} catch (IllegalAccessException e) {
			throw new DownloadException("Could not read from [" + getFileName() + "]");
		}
	}
	
	private String getFileName() {
		return clazz.getName().substring(clazz.getName().lastIndexOf('.') + 1) + ".xml";
	}
}

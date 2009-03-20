package com.ekanathk.maven.core;

public class DownloadException extends RuntimeException {

	public DownloadException() {
		super();
	}

	public DownloadException(String message, Throwable cause) {
		super(message, cause);
	}

	public DownloadException(String message) {
		super(message);
	}

	public DownloadException(Throwable cause) {
		super(cause);
	}

}

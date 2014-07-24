package com.proquest.mtg.dismetadataservice.media;

@SuppressWarnings("serial")
public class MediaDownloadException extends Exception {
	public MediaDownloadException(String message) {
		super(message);
	}
	
	public MediaDownloadException(String message, Throwable cause) {
		super(message, cause);
	}
}

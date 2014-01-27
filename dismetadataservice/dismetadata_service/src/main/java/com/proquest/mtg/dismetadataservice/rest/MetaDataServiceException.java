package com.proquest.mtg.dismetadataservice.rest;

@SuppressWarnings("serial")
public class MetaDataServiceException extends Exception {

	public MetaDataServiceException(Throwable cause) {
		super(cause);
	}
	
	public MetaDataServiceException(String message) {
		super(message);
	}
	
	public MetaDataServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}

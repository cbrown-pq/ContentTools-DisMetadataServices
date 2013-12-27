package com.proquest.mtg.dismetadataservice.marc;

@SuppressWarnings("serial")
public class MarcParserException extends Exception {
	
	public MarcParserException(String message) {
		super(message);
	}
	
	public MarcParserException(String message, Throwable cause) {
		super(message, cause);
	}

}

package com.proquest.mtg.dismetadataservice.exodus;

public class LocReportPubMetaData {

	private final String pubNumber;
    private final String authorFullName;
    
    public LocReportPubMetaData(String pubNumber, String authorFullName) {
    	this.pubNumber = pubNumber;
    	this.authorFullName = authorFullName;    	
    }
	public String getPubNumber() {
		return pubNumber;
	}
	
	public String getAuthorFullName() {
		return authorFullName;
	}
}

package com.proquest.mtg.dismetadataservice.loc;

import org.codehaus.jackson.annotate.JsonProperty;

// The purpose of this class is to assist in the transformation from XML to JSON
public class LOCReportPubJson {
	@JsonProperty("pubNumber")
	private Integer pubNumber;
	@JsonProperty("authorFullName")
	private String authorFullName;
	
	public LOCReportPubJson() {
		super();
	}

	public LOCReportPubJson(Integer pubNumber, String authorFullName) {
		this.pubNumber = pubNumber;
		this.authorFullName = authorFullName;
	}

	public Integer getPubNumber() {
		return pubNumber;
	}
	
	public void setPubNumber(Integer pubId) {
		this.pubNumber = pubId;
	}
	
	public String getAuthorFullName() {
		return authorFullName;
	}
	
	public void setAuthorFullName(String author) {
		this.authorFullName = author;
	}
	
}

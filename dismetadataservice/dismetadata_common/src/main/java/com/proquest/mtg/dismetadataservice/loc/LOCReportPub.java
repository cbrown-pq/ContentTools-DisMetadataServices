package com.proquest.mtg.dismetadataservice.loc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Publication")
@XmlAccessorType(XmlAccessType.FIELD)
public class LOCReportPub {

	@XmlElement(name = "PubID")
	private Integer pubNumber;
	@XmlElement(name = "Author")
	private String authorFullName;
	
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

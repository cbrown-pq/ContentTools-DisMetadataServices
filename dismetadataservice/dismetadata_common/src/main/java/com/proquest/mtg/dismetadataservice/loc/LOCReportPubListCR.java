package com.proquest.mtg.dismetadataservice.loc;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// used for copyright reports
@XmlRootElement(name = "CopyrightEligiblePubList")
@XmlAccessorType(XmlAccessType.FIELD)
public class LOCReportPubListCR {
	
	@XmlElement(name = "Publication")
	private List<LOCReportPub> pubs;

	public List<LOCReportPub> getPubs() {
		return pubs;
	}

	public void setPubs(List<LOCReportPub> pubs) {
		this.pubs = pubs;
	}
	
	public List<LOCReportPubJson> makeJsonList() {
		List<LOCReportPubJson> newList = new ArrayList<>();
		for (LOCReportPub pub : this.pubs) {
			newList.add(new LOCReportPubJson(pub.getPubNumber(), pub.getAuthorFullName()));
		}
		return newList;
	}
	
}

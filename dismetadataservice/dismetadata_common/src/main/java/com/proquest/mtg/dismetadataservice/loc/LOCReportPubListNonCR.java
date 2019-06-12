package com.proquest.mtg.dismetadataservice.loc;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// used for non-copyright reports
@XmlRootElement(name = "NonCopyrightEligiblePubList")
@XmlAccessorType(XmlAccessType.FIELD)
public class LOCReportPubListNonCR {
	
	@XmlElement(name = "Publication")
	private List<LOCReportPub> pubs;

	public List<LOCReportPub> getPubs() {
		if (pubs == null)
			pubs = new ArrayList<>();
		return pubs;
	}

	public void setPubs(List<LOCReportPub> pubs) {
		this.pubs = pubs;
	}
	
	public List<LOCReportPubJson> makeJsonList() {
		List<LOCReportPubJson> newList = new ArrayList<>();
		for (LOCReportPub pub : getPubs()) {
			newList.add(new LOCReportPubJson(pub.getPubNumber(), pub.getAuthorFullName()));
		}
		return newList;
	}
	
}

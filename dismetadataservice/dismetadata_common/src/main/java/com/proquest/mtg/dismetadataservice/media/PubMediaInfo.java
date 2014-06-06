package com.proquest.mtg.dismetadataservice.media;

import java.util.List;

import com.google.common.collect.Lists;

public class PubMediaInfo {

	private final String pubId;
	private final List<GoidMediaInfo> goidMediaInfo;
	
	public PubMediaInfo(String pubId, Iterable<GoidMediaInfo> goidMediaInfo) {
		this.pubId = pubId;
		this.goidMediaInfo = Lists.newArrayList(goidMediaInfo);
	}

	public String getPubId() {
		return pubId;
	}

	public List<GoidMediaInfo> getGoidMediaInfo() {
		return goidMediaInfo;
	}

	public boolean hasPdf() {
		boolean result = false;
		for (GoidMediaInfo curGoidMediaInfo : getGoidMediaInfo()) {
			if (curGoidMediaInfo.hasPdf()) {
				result = true;
			}
		}
		return result;
	}
			
	public PdfMediaInfo getPreferredPdf(Iterable<PdfType> pdfPriorityOrder) {
		for (PdfType curPdfType : pdfPriorityOrder) {
			PdfMediaInfo found = getFirstKindOfPdf(curPdfType);
			if (null != found) {
				return found;
			}
		}
		return null; // No PDF found
	}
	
	private PdfMediaInfo getFirstKindOfPdf(PdfType targetPdfType) {
		for (GoidMediaInfo curGoidMediaInfo : getGoidMediaInfo()) {
			PdfMediaInfo found = curGoidMediaInfo.getFirstKindOfPdf(targetPdfType);
			if (null != found) {
				return found;
			}
		}
		return null;
	}
	
		
	@Override
	public String toString() {
		return "PubMediaInfo [pubId=" + pubId + ", goidMediaInfo=" + goidMediaInfo
				+ "]";
	}
}

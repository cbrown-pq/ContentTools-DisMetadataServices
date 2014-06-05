package com.proquest.mtg.dismetadataservice.media;

import java.util.List;

import com.google.common.collect.Lists;

public class GoidMediaInfo {
	private final String goid;
	private final List<PdfMediaInfo> pdfMediaInfo;
	
	public GoidMediaInfo(
			String goid, 
			Iterable<PdfMediaInfo> pdfMediaInfo) {
		this.goid = goid;
		this.pdfMediaInfo = Lists.newArrayList(pdfMediaInfo);
	
	}

	public String getGoid() {
		return goid;
	}

	public List<PdfMediaInfo> getPdfMediaInfo() {
		return pdfMediaInfo;
	}
	
	public boolean hasPdf() {
		return !getPdfMediaInfo().isEmpty();
	}

	public PdfMediaInfo getFirstKindOfPdf(PdfType targetPdfType) {
		for (PdfMediaInfo curPdfMediaInfo : getPdfMediaInfo()) {
			if (targetPdfType == curPdfMediaInfo.getPdfType()) {
				return curPdfMediaInfo;
			}
		}
		return null;
	}
		
	@Override
	public String toString() {
		return "GoidMediaInfo [goid=" + goid + ", pdfMediaInfo=" + pdfMediaInfo
				+ "]";
	}
}

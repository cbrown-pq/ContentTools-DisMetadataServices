package com.proquest.mtg.dismetadataservice.pdf;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.proquest.mtg.dismetadataservice.media.PdfType;

public class PDFDownloadOptions {
	private Set<PdfType> pdfPriorityOrder;
	private final String pdfType;
	private final int excludeCopyright;
	private final int restrictionIncluded;

	public PDFDownloadOptions(String pdfType, int excludeCopyright, int restrictionIncluded) {
		this.pdfType = pdfType;
		this.excludeCopyright = excludeCopyright;
		this.restrictionIncluded = restrictionIncluded;
		setPdfPriorityOrder(getPdfType());
	}
	
	private void setPdfPriorityOrder(String pdfType) {
		if (pdfType.equalsIgnoreCase(SupportedPdfTypes.kPODPdfType)) {
			this.pdfPriorityOrder = PdfType.kPriorityOrderForDistribution;
		} else if (pdfType.equalsIgnoreCase(SupportedPdfTypes.kPrintPdfType)) {
			this.pdfPriorityOrder = PdfType.kPriorityOrderForPrint;
		} else if (pdfType.equalsIgnoreCase(SupportedPdfTypes.k300PdfType)) {
			this.pdfPriorityOrder = PdfType.kPriorityOrderFor300PDF;
		} else {
			this.pdfPriorityOrder = PdfType.kPriorityOrderForSPDF;
		} 		
	}

	public ImmutableSet<PdfType> getPdfPriorityOrder() {
		return ImmutableSet.copyOf(pdfPriorityOrder);
	}
	
	public boolean isCopyrightMsgRequired() {
		return (excludeCopyright == 0) ? true : false;
	}
	
	public boolean isRestrictionIncluded() {
		return (restrictionIncluded == 0) ? false : true;
	}
	
	public String getPdfType() {
		return pdfType;
	}	
}

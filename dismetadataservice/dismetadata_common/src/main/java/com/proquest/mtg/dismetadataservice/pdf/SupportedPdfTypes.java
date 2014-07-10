package com.proquest.mtg.dismetadataservice.pdf;

import com.google.common.collect.ImmutableList;

public class SupportedPdfTypes {
	
	public static final String kPODPdfType = "POD";
	public static final String kPrintPdfType = "PRINT";
	public static final String k300PdfType = "300PDF";
	public static final String kSearchablePdfType = "SPDF";
	
	public static final ImmutableList<String> kSupportedPdfTypes = ImmutableList.of(
			SupportedPdfTypes.kPODPdfType,
			SupportedPdfTypes.kPrintPdfType,
			SupportedPdfTypes.k300PdfType,
			SupportedPdfTypes.kPrintPdfType);
	
}

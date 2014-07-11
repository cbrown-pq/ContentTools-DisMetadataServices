package com.proquest.mtg.dismetadataservice.pdf;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.proquest.mtg.dismetadataservice.media.PdfType;

public class PDFDownloadOptions_Tests {
	private String pdfType;
	private int excludeCopyright;
	private int restrictionIncluded;
	PDFDownloadOptions target;
	 
	@Test
	public void hasCorrect_PdfTypeAndPriorityList_ForPOD() {
		pdfType = "POD";
		excludeCopyright = 1;
		restrictionIncluded = 1;
		target = new PDFDownloadOptions(pdfType, excludeCopyright, restrictionIncluded);
		
		assertThat(target.getPdfType(), is(pdfType));
		assertThat(target.getPdfPriorityOrder(), is(PdfType.kPriorityOrderForDistribution));
	}
	
	@Test
	public void hasCorrect_PdfTypeAndPriorityList_ForSPDF() {
		pdfType = "SPDF";
		excludeCopyright = 1;
		restrictionIncluded = 1;
		target = new PDFDownloadOptions(pdfType, excludeCopyright, restrictionIncluded);
		
		assertThat(target.getPdfType(), is(pdfType));
		assertThat(target.getPdfPriorityOrder(), is(PdfType.kPriorityOrderForSPDF));
	}
	
	@Test
	public void hasCorrect_PdfTypeAndPriorityList_ForPrint() {
		pdfType = "PRINT";
		excludeCopyright = 1;
		restrictionIncluded = 1;
		target = new PDFDownloadOptions(pdfType, excludeCopyright, restrictionIncluded);
		
		assertThat(target.getPdfType(), is(pdfType));
		assertThat(target.getPdfPriorityOrder(), is(PdfType.kPriorityOrderForPrint));
	}
	
	@Test
	public void canEnable_CopyrightAndRestriction() {
		pdfType = "POD";
		excludeCopyright = 0;
		restrictionIncluded = 1;
		target = new PDFDownloadOptions(pdfType, excludeCopyright, restrictionIncluded);
		
		assertThat(target.isCopyrightMsgRequired(), is(true));
		assertThat(target.isRestrictionIncluded(), is(true));
	}
	
	@Test
	public void canDisable_CopyrightAndRestriction() {
		pdfType = "POD";
		excludeCopyright = 1;
		restrictionIncluded = 0;
		target = new PDFDownloadOptions(pdfType, excludeCopyright, restrictionIncluded);
		
		assertThat(target.isCopyrightMsgRequired(), is(false));
		assertThat(target.isRestrictionIncluded(), is(false));
	}
}

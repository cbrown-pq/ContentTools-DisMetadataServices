package com.proquest.mtg.dismetadataservice.media;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class PdfMediaInfo_Tests {
	PdfType pdfType;
	String url;
	PdfMediaInfo target;
	
	@Before
	public void setUp() throws Exception {
		pdfType = PdfType.SEARCHABLE;
		url = "http://services.aa.pqe/media";
		target = new PdfMediaInfo(pdfType, url);
	}
	
	@Test
	public void hasCorrect_PdfType() throws Exception {
		assertThat(target.getPdfType(), sameInstance(pdfType));
	}
	
	@Test
	public void hasCorrect_Url() throws Exception {
		assertThat(target.getUrlSpec(), is(url));
	}
	
	@Test
	public void coverageTests() throws Exception {
		assertThat(target.toString(), notNullValue());
	}
}

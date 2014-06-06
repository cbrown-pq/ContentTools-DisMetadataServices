package com.proquest.mtg.dismetadataservice.media;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class GoidMediaInfo_Tests {
	String goid;
	PdfMediaInfo pdfInfo1;
	PdfMediaInfo pdfInfo2;
	GoidMediaInfo target;
	
	@Before
	public void setUp() throws Exception {
		goid = "1220487081";
		pdfInfo1 = EasyMock.createMock(PdfMediaInfo.class);
		pdfInfo2 = EasyMock.createMock(PdfMediaInfo.class);
		
		target = new GoidMediaInfo(goid, 
					Lists.newArrayList(pdfInfo1, pdfInfo2));
	}
	
	@Test
	public void hasCorrect_Goid() throws Exception {
		assertThat(target.getGoid(), is(goid));
	}
	
	@Test
	public void hasCorrect_PdfInfo() throws Exception {
		assertThat(target.getPdfMediaInfo().size(), is(2));
		assertThat(target.getPdfMediaInfo(), hasItem(pdfInfo1));
		assertThat(target.getPdfMediaInfo(), hasItem(pdfInfo2));
	}
	
	@Test
	public void coverageTest() throws Exception {
		assertThat(target.toString(), notNullValue());
	}
}

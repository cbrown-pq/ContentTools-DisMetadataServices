package com.proquest.mtg.dismetadataservice.media;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class PubMediaInfo_GetPreferredPdfForDistribution_Tests extends EasyMockSupport {
	static final String pubId = "1234567";
	
	PdfMediaInfo searchablePdf1;
	PdfMediaInfo searchablePdf2;
	
	PdfMediaInfo imagePdf1;
	PdfMediaInfo imagePdf2;
	
	GoidMediaInfo goidMedaiInfo1;
	GoidMediaInfo goidMedaiInfo2;
	GoidMediaInfo goidMedaiInfo3;
	List<GoidMediaInfo> goidMediaInfoList;
	
	PubMediaInfo target;
	
	@Before
	public void setUp() throws Exception {
		searchablePdf1 = new PdfMediaInfo(PdfType.SEARCHABLE, "Searchable 1");
		searchablePdf2 = new PdfMediaInfo(PdfType.SEARCHABLE, "Searchable 2");
		
		imagePdf1 = new PdfMediaInfo(PdfType.IMAGE_300DPI, "Image 1");
		imagePdf2 = new PdfMediaInfo(PdfType.IMAGE_300DPI, "Image 2");
		
		goidMedaiInfo1 = createMock(GoidMediaInfo.class);
		goidMedaiInfo2 = createMock(GoidMediaInfo.class);
		goidMedaiInfo3 = createMock(GoidMediaInfo.class);
	}
	
	public PdfMediaInfo getPreferredPdfForDistribution() {
		return target.getPreferredPdf(PdfType.kPriorityOrderForDistribution);
	}
	
	@Test
	public void with_NoGoidMediaInfo_ReturnsNull() throws Exception {
		goidMediaInfoList = Lists.newArrayList();
		target = new PubMediaInfo(pubId, goidMediaInfoList);
		assertThat(getPreferredPdfForDistribution(), nullValue());
	}
	
	@Test
	public void with_SeveralMediaInfo_NoPdfs_ReturnsNull() throws Exception {
		expect(goidMedaiInfo1.hasPdf()).andStubReturn(false);
		expect(goidMedaiInfo1.getFirstKindOfPdf(PdfType.SEARCHABLE)).andStubReturn(null);
		expect(goidMedaiInfo1.getFirstKindOfPdf(PdfType.IMAGE_300DPI)).andStubReturn(null);
		expect(goidMedaiInfo1.getFirstKindOfPdf(PdfType.IMAGE_600DPI)).andStubReturn(null);
		
		expect(goidMedaiInfo2.hasPdf()).andStubReturn(false);
		expect(goidMedaiInfo2.getFirstKindOfPdf(PdfType.SEARCHABLE)).andStubReturn(null);
		expect(goidMedaiInfo2.getFirstKindOfPdf(PdfType.IMAGE_300DPI)).andStubReturn(null);
		expect(goidMedaiInfo2.getFirstKindOfPdf(PdfType.IMAGE_600DPI)).andStubReturn(null);
		
		expect(goidMedaiInfo3.hasPdf()).andStubReturn(false);
		expect(goidMedaiInfo3.getFirstKindOfPdf(PdfType.SEARCHABLE)).andStubReturn(null);
		expect(goidMedaiInfo3.getFirstKindOfPdf(PdfType.IMAGE_300DPI)).andStubReturn(null);
		expect(goidMedaiInfo3.getFirstKindOfPdf(PdfType.IMAGE_600DPI)).andStubReturn(null);
		
		goidMediaInfoList = Lists.newArrayList(goidMedaiInfo1, goidMedaiInfo2, goidMedaiInfo3);
		
		replayAll();
		
		target = new PubMediaInfo(pubId, goidMediaInfoList);
		assertThat(getPreferredPdfForDistribution(), nullValue());
		
		verifyAll();
	}
	
	@Test
	public void with_SeveralMediaInfo_OnlyImage_ReturnsFirstImage() throws Exception {
		expect(goidMedaiInfo1.hasPdf()).andStubReturn(false);
		expect(goidMedaiInfo1.getFirstKindOfPdf(PdfType.SEARCHABLE)).andStubReturn(null);
		expect(goidMedaiInfo1.getFirstKindOfPdf(PdfType.IMAGE_300DPI)).andStubReturn(null);
		expect(goidMedaiInfo1.getFirstKindOfPdf(PdfType.IMAGE_600DPI)).andStubReturn(null);
		
		expect(goidMedaiInfo2.hasPdf()).andStubReturn(true);
		expect(goidMedaiInfo2.getFirstKindOfPdf(PdfType.SEARCHABLE)).andStubReturn(null);
		expect(goidMedaiInfo2.getFirstKindOfPdf(PdfType.IMAGE_300DPI)).andStubReturn(imagePdf1);
		expect(goidMedaiInfo2.getFirstKindOfPdf(PdfType.IMAGE_600DPI)).andStubReturn(null);

		expect(goidMedaiInfo3.hasPdf()).andStubReturn(true);
		expect(goidMedaiInfo3.getFirstKindOfPdf(PdfType.SEARCHABLE)).andStubReturn(null);
		expect(goidMedaiInfo3.getFirstKindOfPdf(PdfType.IMAGE_300DPI)).andStubReturn(imagePdf2);
		expect(goidMedaiInfo2.getFirstKindOfPdf(PdfType.IMAGE_600DPI)).andStubReturn(null);
		
		goidMediaInfoList = Lists.newArrayList(goidMedaiInfo1, goidMedaiInfo2, goidMedaiInfo3);
		
		replayAll();
		
		target = new PubMediaInfo(pubId, goidMediaInfoList);
		assertThat(getPreferredPdfForDistribution(), sameInstance(imagePdf1));
		
		verifyAll();
	}
}

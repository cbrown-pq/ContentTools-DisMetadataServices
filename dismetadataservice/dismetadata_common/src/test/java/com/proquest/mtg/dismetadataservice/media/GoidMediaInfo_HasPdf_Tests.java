package com.proquest.mtg.dismetadataservice.media;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class GoidMediaInfo_HasPdf_Tests {
	static final String goid = "1234567890";
	static final PdfMediaInfo pdfInfo1 = new PdfMediaInfo(PdfType.SEARCHABLE, "");
	static final PdfMediaInfo pdfInfo2 = new PdfMediaInfo(PdfType.IMAGE_300DPI, "");
	List<PdfMediaInfo> pdfInfoList;
	GoidMediaInfo target;
	
	@Test
	public void with_EmptyPdfInfoList_ReturnsFalse() throws Exception {
		pdfInfoList = Lists.newArrayList();
		target = new GoidMediaInfo(goid, pdfInfoList);
		assertThat(target.hasPdf(), is(false));
	}
	
	@Test
	public void with_OnePdfInfo_ReturnsTrue() throws Exception {
		pdfInfoList = Lists.newArrayList(pdfInfo1);
		target = new GoidMediaInfo(goid, pdfInfoList);
		assertThat(target.hasPdf(), is(true));
	}
	
	@Test
	public void with_SeveralPdfInfo_ReturnsTrue() throws Exception {
		pdfInfoList = Lists.newArrayList(pdfInfo1, pdfInfo2);
		target = new GoidMediaInfo(goid, pdfInfoList);
		assertThat(target.hasPdf(), is(true));
	}
}

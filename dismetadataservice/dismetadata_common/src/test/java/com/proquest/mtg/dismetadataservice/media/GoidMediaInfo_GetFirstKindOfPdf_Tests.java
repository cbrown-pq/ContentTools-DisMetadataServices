package com.proquest.mtg.dismetadataservice.media;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class GoidMediaInfo_GetFirstKindOfPdf_Tests {
	static final String goid = "1234567890";
	static final PdfMediaInfo pdfInfoSearchable = new PdfMediaInfo(PdfType.SEARCHABLE, "");
	static final PdfMediaInfo pdfInfoImage300 = new PdfMediaInfo(PdfType.IMAGE_300DPI, "");
	
	List<PdfMediaInfo> pdfInfoList;
	GoidMediaInfo target;
	
	@Test
	public void with_EmptyPdfInfoList_ReturnsNull() throws Exception {
		pdfInfoList = Lists.newArrayList();
		target = new GoidMediaInfo(goid, pdfInfoList);
		assertThat(target.getFirstKindOfPdf(PdfType.SEARCHABLE), nullValue());
		assertThat(target.getFirstKindOfPdf(PdfType.IMAGE_300DPI), nullValue());
	}
	
	@Test
	public void with_One_Searchable() throws Exception {
		pdfInfoList = Lists.newArrayList(pdfInfoSearchable);
		target = new GoidMediaInfo(goid, pdfInfoList);
		assertThat(target.getFirstKindOfPdf(PdfType.SEARCHABLE), sameInstance(pdfInfoSearchable));
		assertThat(target.getFirstKindOfPdf(PdfType.IMAGE_300DPI), nullValue());
	}
	
	@Test
	public void with_One_Image() throws Exception {
		pdfInfoList = Lists.newArrayList(pdfInfoImage300);
		target = new GoidMediaInfo(goid, pdfInfoList);
		assertThat(target.getFirstKindOfPdf(PdfType.SEARCHABLE), nullValue());
		assertThat(target.getFirstKindOfPdf(PdfType.IMAGE_300DPI), sameInstance(pdfInfoImage300));
	}
	
	@Test
	public void with_All() throws Exception {
		pdfInfoList = Lists.newArrayList(pdfInfoSearchable, pdfInfoImage300);
		target = new GoidMediaInfo(goid, pdfInfoList);
		assertThat(target.getFirstKindOfPdf(PdfType.SEARCHABLE), sameInstance(pdfInfoSearchable));
		assertThat(target.getFirstKindOfPdf(PdfType.IMAGE_300DPI), sameInstance(pdfInfoImage300));
	}
}

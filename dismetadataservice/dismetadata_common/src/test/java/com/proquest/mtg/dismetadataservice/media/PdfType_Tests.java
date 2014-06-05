package com.proquest.mtg.dismetadataservice.media;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class PdfType_Tests {

	@Test
	public void searchable_enum() throws Exception {
		String value = "SPDF";
		PdfType target = PdfType.fromString(value);
		assertThat(target, is(PdfType.SEARCHABLE));
		assertThat(target.getValue(), is(value));
		assertThat(target.toString(), is(value));
	}
	
	@Test
	public void native_enum() throws Exception {
		String value = "NPDF";
		PdfType target = PdfType.fromString(value);
		assertThat(target, is(PdfType.NATIVE));
		assertThat(target.getValue(), is(value));
		assertThat(target.toString(), is(value));
	}
	
	@Test
	public void image300Dpi_enum() throws Exception {
		String value = "300PDF";
		PdfType target = PdfType.fromString(value);
		assertThat(target, is(PdfType.IMAGE_300DPI));
		assertThat(target.getValue(), is(value));
		assertThat(target.toString(), is(value));
	}
	
	@Test
	public void image600Dpi_enum() throws Exception {
		String value = "600PDF";
		PdfType target = PdfType.fromString(value);
		assertThat(target, is(PdfType.IMAGE_600DPI));
		assertThat(target.getValue(), is(value));
		assertThat(target.toString(), is(value));
	}
}

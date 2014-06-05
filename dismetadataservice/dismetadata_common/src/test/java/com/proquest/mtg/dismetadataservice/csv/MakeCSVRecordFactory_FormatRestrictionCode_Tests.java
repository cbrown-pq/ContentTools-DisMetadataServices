package com.proquest.mtg.dismetadataservice.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.FormatRestriction;
import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;

public class MakeCSVRecordFactory_FormatRestrictionCode_Tests extends EasyMockSupport {
	CSVRecordFactory factory;
	String header = "";
	DisPubMetaData metadata;
	List<FormatRestriction> formatRestrictions;
	PDFVaultAvailableStatusProvider pdfVaultAvailableStatus;

	@Before
	public void setUp() throws Exception {
		pdfVaultAvailableStatus  =  createMock(PDFVaultAvailableStatusProvider.class);
		factory = new CSVRecordFactory(pdfVaultAvailableStatus);
		metadata = new DisPubMetaData();
		metadata = new DisPubMetaData();
		for (String curheader : factory.getHeaders()) {
			header += curheader + ",";
		}
		formatRestrictions = new ArrayList<FormatRestriction>();
	}

	@Test
	public void makeWithEmptyFormatRestrictionCode() throws Exception {
		FormatRestriction formatRestriction = new FormatRestriction();
		formatRestrictions.add(formatRestriction);
		metadata.setFormatRestrictions(formatRestrictions);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,\"NONE\",\"NONE\",\"NONE\",\"NONE\",,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyFormatRestrictionCode() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		List<FormatRestriction> formatRestrictions = new ArrayList<FormatRestriction>();
		FormatRestriction formatRestriction1 = new FormatRestriction();
		formatRestriction1.setCode("C");
		FormatRestriction formatRestriction2 = new FormatRestriction();
		formatRestriction2.setCode("CE");
		formatRestrictions.add(formatRestriction1);
		formatRestrictions.add(formatRestriction2);
		metadata.setFormatRestrictions(formatRestrictions);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"N\"" + ",,,,,,,,,,,,," + ",\"N\"" + ",,\"C|CE\",\"NONE|NONE\",\"NONE|NONE\",\"NONE|NONE\",,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

}

package com.proquest.mtg.dismetadataservice.csv;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;

public class MakeCSVRecordFactory_BritishLocation_Tests extends EasyMockSupport {
	CSVRecordFactory factory;
	DisPubMetaData metadata;
	PDFVaultAvailableStatusProvider pdfVaultAvailableStatus;

	@Before
	public void setUp() throws Exception {
		pdfVaultAvailableStatus  =  createMock(PDFVaultAvailableStatusProvider.class);
		factory = new CSVRecordFactory(pdfVaultAvailableStatus,0,0);
	}

	@Test
	public void makeWithEmptyBritishLocation() throws Exception {
		metadata = new DisPubMetaData();
		String blNumber = null;
		metadata.setBLNumber(blNumber);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kBritishLibraryNumber, null);
	}

	@Test
	public void withOnlyBritishLocation() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		metadata.setBLNumber("BLNumber");
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kBritishLibraryNumber, "BLNumber");
	}
}

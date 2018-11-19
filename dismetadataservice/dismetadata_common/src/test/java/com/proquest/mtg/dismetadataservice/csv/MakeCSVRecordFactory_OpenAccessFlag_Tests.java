package com.proquest.mtg.dismetadataservice.csv;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;

public class MakeCSVRecordFactory_OpenAccessFlag_Tests extends EasyMockSupport  {
	CSVRecordFactory factory;
	DisPubMetaData metadata;
	PDFVaultAvailableStatusProvider pdfVaultAvailableStatus;

	@Before
	public void setUp() throws Exception {
		pdfVaultAvailableStatus  =  createMock(PDFVaultAvailableStatusProvider.class);
		factory = new CSVRecordFactory(pdfVaultAvailableStatus,0,0);
		metadata = new DisPubMetaData();
	}

	@Test
	public void makeWithEmptyOpenAccessFlag() throws Exception {
		String openAccessFlag = null;
		metadata.setOpenAccessFlag(openAccessFlag);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kOpenAccessFlag, null);
	}

	@Test
	public void withOnlyOpenAccessFlag() throws Exception {
		String openAccessFlag = "TESTOAFLAG";
		metadata.setOpenAccessFlag(openAccessFlag);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kOpenAccessFlag, openAccessFlag);
	}
}

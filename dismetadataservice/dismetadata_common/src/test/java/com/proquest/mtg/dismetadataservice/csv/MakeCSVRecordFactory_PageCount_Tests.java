package com.proquest.mtg.dismetadataservice.csv;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;

public class MakeCSVRecordFactory_PageCount_Tests extends EasyMockSupport {
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
	public void makeWithEmptyPageCount() throws Exception {
		String pageCount = null;
		metadata.setPageCount(pageCount);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kPageCount, null);
	}

	@Test
	public void withOnlyPubPageCount() throws Exception {
		metadata.setPageCount("128");
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kPageCount, "128");
	}
}

package com.proquest.mtg.dismetadataservice.csv;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;

public class MakeCSVRecordFactory_PubNumber_Tests extends EasyMockSupport {
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
	public void makeWithEmpty() throws Exception {
		String pubNumber = null;
		metadata.setPubNumber(pubNumber);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kPubNumber, null);
	}

	@Test
	public void makeWithPubNumber() throws Exception {
		metadata.setPubNumber("3569004");
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kPubNumber, "3569004");
	}

}

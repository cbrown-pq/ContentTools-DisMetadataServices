package com.proquest.mtg.dismetadataservice.csv;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;

public class MakeCSVRecordFactory_ReferenceLocation_Tests extends EasyMockSupport {
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
	public void makeWithEmptReferenceLocation() throws Exception {
		String refLocation = null;
		metadata.setReferenceLocation(refLocation);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kReferenceLocation, refLocation);
	}

	@Test
	public void withOnlyReferenceLocation() throws Exception {
		String refLocation = "DEPT. OF PHARMACOLOGY, KAROLINSKA INSTITUTE, BOX 60400, S-104 01         STOCKHOLM, SWEDEN";
		metadata.setReferenceLocation(refLocation);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kReferenceLocation, refLocation);
	}
}

package com.proquest.mtg.dismetadataservice.csv;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Batch;
import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;

public class MakeCSVRecordFactory_DissertationCode_Tests extends EasyMockSupport {
	CSVRecordFactory factory;
	DisPubMetaData metadata;
	Batch batch;
	PDFVaultAvailableStatusProvider pdfVaultAvailableStatus;
	
	@Before
	public void setUp() throws Exception {
		pdfVaultAvailableStatus  =  createMock(PDFVaultAvailableStatusProvider.class);
		factory = new CSVRecordFactory(pdfVaultAvailableStatus,0,0);
		metadata = new DisPubMetaData();
		batch = new Batch();
	}

	@Test
	public void makeWithEmptyBatch() throws Exception {

		metadata.setBatch(batch);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kDissertationTypeCode, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kDissertationCode, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kDAISectionCode, null);
	}

	@Test
	public void withOnlyDissertationTypeCode() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		Batch batch = new Batch();
		batch.setDBTypeCode("DAC");
		metadata.setBatch(batch);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kDissertationTypeCode, "DAC");
	}

	@Test
	public void withOnlyDissertationCode() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		Batch batch = new Batch();
		batch.setDBTypeDesc("Dissertation Abstracts International");
		metadata.setBatch(batch);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kDissertationCode, "Dissertation Abstracts International");
	}

	@Test
	public void withOnlyDissertationDAICode() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		Batch batch = new Batch();
		batch.setDAISectionCode("B");
		metadata.setBatch(batch);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kDAISectionCode, "B");
	}

}

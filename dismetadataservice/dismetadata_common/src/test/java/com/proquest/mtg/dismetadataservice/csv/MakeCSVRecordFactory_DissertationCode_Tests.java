package com.proquest.mtg.dismetadataservice.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Batch;
import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;

public class MakeCSVRecordFactory_DissertationCode_Tests extends EasyMockSupport {
	CSVRecordFactory factory;
	String header = "";
	DisPubMetaData metadata;
	Batch batch;
	PDFVaultAvailableStatusProvider pdfVaultAvailableStatus;
	
	@Before
	public void setUp() throws Exception {
		pdfVaultAvailableStatus  =  createMock(PDFVaultAvailableStatusProvider.class);
		factory = new CSVRecordFactory(pdfVaultAvailableStatus,0,0);
		metadata = new DisPubMetaData();
		for (String curheader : factory.getHeaders()) {
			header += curheader + ",";
		}
		batch = new Batch();
	}

	@Test
	public void makeWithEmptyBatch() throws Exception {

		metadata.setBatch(batch);
		String expectedCSVData = header
				+ "\r\n,,,\"N\",,\"N\",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyDissertationTypeCode() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		Batch batch = new Batch();
		batch.setDBTypeCode("DAC");
		metadata.setBatch(batch);
		String expectedCSVData = header + "\r\n,,,\"N\",,\"N\",,,,,,,,,\"DAC\",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyDissertationCode() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		Batch batch = new Batch();
		batch.setDBTypeDesc("Dissertation Abstracts International");
		metadata.setBatch(batch);
		String expectedCSVData = header + "\r\n,,,\"N\",,\"N\",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"Dissertation Abstracts International\",,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyDissertationDAICode() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		Batch batch = new Batch();
		batch.setDAISectionCode("B");
		metadata.setBatch(batch);
		String expectedCSVData = header + "\r\n,,,\"N\",,\"N\",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"B\",,,,,,,,,,,,,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

}

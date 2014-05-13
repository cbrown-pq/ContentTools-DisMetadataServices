package com.proquest.mtg.dismetadataservice.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Batch;

public class MakeCSVRecordFactory_DissertationCode_Tests {
	CSVRecordFactory factory;
	String header = "";
	DisPubMetaData metadata;
	Batch batch;

	@Before
	public void setUp() throws Exception {
		factory = new CSVRecordFactory();
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
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyDissertationTypeCode() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		Batch batch = new Batch();
		batch.setDBTypeCode("DAC");
		metadata.setBatch(batch);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"N\"" + ",,,,,,,,,," + ",\"" + "DAC" + "\""
				+ ",,,\"N\",,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyDissertationCode() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		Batch batch = new Batch();
		batch.setDBTypeDesc("Dissertation Abstracts International");
		metadata.setBatch(batch);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"N\"" + ",,,,,,,,,,," + ",\""
				+ "Dissertation Abstracts International" + "\"" + ",,\"N\",,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyDissertationDAICode() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		Batch batch = new Batch();
		batch.setDAISectionCode("B");
		metadata.setBatch(batch);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"N\"" + ",,,,,,,,,,,," + ",\"" + "B" + "\"" + ",\"N\",,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

}

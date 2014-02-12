package com.proquest.mtg.dismetadataservice.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Batch;

public class MakeCSVRecordFactory_VolumeIssue_Tests {
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
	public void makeWithEmptyVolumeIssue() throws Exception {
		String volumeIssue = null;
		metadata.setPubNumber(volumeIssue);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyVolumeIssue() throws Exception {
		batch.setVolumeIssue("74-08(E)");
		metadata.setBatch(batch);
		String expectedCSVData = header
				+ "\r\n,\"74-08(E)\",,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

}

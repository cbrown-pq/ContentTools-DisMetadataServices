package com.proquest.mtg.dismetadataservice.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;

public class MakeCSVRecordFactory_Department_Tests {
	CSVRecordFactory factory;
	String header = "";
	DisPubMetaData metadata;
	List<String> deparments;

	@Before
	public void setUp() throws Exception {
		factory = new CSVRecordFactory();
		for (String curheader : factory.getHeaders()) {
			header += curheader + ",";
		}
	}

	@Test
	public void makeWithEmptyDepartments() throws Exception {
		metadata = new DisPubMetaData();
		deparments = Lists.newArrayList();
		metadata.setDepartments(deparments);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyDepartments() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		List<String> deparments = Lists.newArrayList(
				"Applied Behavioral Science", "Communication Studies");
		metadata.setDepartments(deparments);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"N\"" + ",,," + ",\""
				+ "Applied Behavioral Science|Communication Studies" + "\""
				+ ",,,,,,,,,,\"N\",,,,,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

}

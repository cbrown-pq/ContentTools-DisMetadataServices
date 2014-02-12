package com.proquest.mtg.dismetadataservice.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Keyword;

public class MakeCSVRecordFactory_Keyword_Tests {
	CSVRecordFactory factory;
	String header = "";
	DisPubMetaData metadata;
	List<Keyword> keywords;

	@Before
	public void setUp() throws Exception {
		factory = new CSVRecordFactory();
		for (String curheader : factory.getHeaders()) {
			header += curheader + ",";
		}
	}

	@Test
	public void makeWithEmptyKeywords() throws Exception {
		metadata = new DisPubMetaData();
		keywords = Lists.newArrayList();
		metadata.setKeywords(keywords);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void makeWithEmptyKeywordSource() throws Exception {
		metadata = new DisPubMetaData();
		keywords = Lists.newArrayList();
		Keyword keyword = new Keyword();
		keyword.setSource("KeywordSource");
		keywords.add(keyword);
		metadata.setKeywords(keywords);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"N\"" + ",,,," + ",,\"" + "KeywordSource" + "\""
				+ ",,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void makeWithEmptyKeywordValue() throws Exception {
		metadata = new DisPubMetaData();
		keywords = Lists.newArrayList();
		Keyword keyword = new Keyword();
		keyword.setValue("KeywordValue");
		keywords.add(keyword);
		metadata.setKeywords(keywords);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"N\"" + ",,,," + ",\"" + "KeywordValue" + "\""
				+ ",,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withOnlyKeywords() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		Keyword keyword1 = new Keyword();
		keyword1.setValue("By Author");
		keyword1.setSource("low temperature adsorption drying");
		Keyword keyword2 = new Keyword();
		keyword2.setValue("For Datrix");
		keyword2.setSource("	  免疫");
		List<Keyword> keywords = Lists.newArrayList(keyword1, keyword2);
		metadata.setKeywords(keywords);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"N\"" + ",,,," + ",\"" + "By Author|For Datrix" + "\""
				+ ",\"" + "low temperature adsorption drying|	  免疫" + "\""
				+ ",,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

}

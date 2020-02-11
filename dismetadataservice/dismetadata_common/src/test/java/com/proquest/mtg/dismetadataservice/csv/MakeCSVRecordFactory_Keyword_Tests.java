package com.proquest.mtg.dismetadataservice.csv;

import java.util.List;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.Keyword;
import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;

public class MakeCSVRecordFactory_Keyword_Tests extends EasyMockSupport {
	CSVRecordFactory factory;
	DisPubMetaData metadata;
	List<Keyword> keywords;
	PDFVaultAvailableStatusProvider pdfVaultAvailableStatus;
	
	@Before
	public void setUp() throws Exception {
		pdfVaultAvailableStatus  =  createMock(PDFVaultAvailableStatusProvider.class);
		factory = new CSVRecordFactory(pdfVaultAvailableStatus,0,0);
	}

	@Test
	public void makeWithEmptyKeywords() throws Exception {
		metadata = new DisPubMetaData();
		keywords = Lists.newArrayList();
		metadata.setKeywords(keywords);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kKeyword, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kKeywordSource, null);
	}

	@Test
	public void makeWithEmptyKeywordSource() throws Exception {
		metadata = new DisPubMetaData();
		keywords = Lists.newArrayList();
		Keyword keyword = new Keyword();
		keyword.setSource("KeywordSource");
		keywords.add(keyword);
		metadata.setKeywords(keywords);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kKeyword, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kKeywordSource, "KeywordSource");
	}

	@Test
	public void makeWithEmptyKeywordValue() throws Exception {
		metadata = new DisPubMetaData();
		keywords = Lists.newArrayList();
		Keyword keyword = new Keyword();
		keyword.setValue("KeywordValue");
		keywords.add(keyword);
		metadata.setKeywords(keywords);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kKeyword, "KeywordValue");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kKeywordSource, null);
	}

	@Test
	public void withOnlyKeywords() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		Keyword keyword1 = new Keyword();
		keyword1.setValue("By Author");
		keyword1.setSource("low temperature adsorption drying");
		Keyword keyword2 = new Keyword();
		keyword2.setValue("For Datrix");
		keyword2.setSource("	  å…�ç–«");
		List<Keyword> keywords = Lists.newArrayList(keyword1, keyword2);
		metadata.setKeywords(keywords);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kKeyword, "By Author|For Datrix");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kKeywordSource, "low temperature adsorption drying|	  å…�ç–«");
	}

}

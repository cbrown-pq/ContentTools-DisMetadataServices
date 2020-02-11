package com.proquest.mtg.dismetadataservice.csv;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.DissLanguage;
import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;

public class MakeCSVRecordFactory_DissLanguages_Tests extends EasyMockSupport {
	CSVRecordFactory factory;
	DisPubMetaData metadata;
	List<DissLanguage> languages;
	PDFVaultAvailableStatusProvider pdfVaultAvailableStatus;
	
	@Before
	public void setUp() throws Exception {
		pdfVaultAvailableStatus  =  createMock(PDFVaultAvailableStatusProvider.class);
		factory = new CSVRecordFactory(pdfVaultAvailableStatus,0,0);
		metadata = new DisPubMetaData();
		languages = new ArrayList<DissLanguage>();
	}

	@Test
	public void makeWithEmpty() throws Exception {
		metadata.setDissLanguages(languages);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kDissLangDesc, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kDissLangCode, null);
	}

	@Test
	public void withOnlyDissLanguages() throws Exception {
		DissLanguage lang1 = new DissLanguage("English", "EN");
		DissLanguage lang2 = new DissLanguage("French", "FR");
		languages.add(lang1);
		languages.add(lang2);
		metadata.setDissLanguages(languages);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kDissLangDesc, "English|French");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kDissLangCode, "EN|FR");
	}

}

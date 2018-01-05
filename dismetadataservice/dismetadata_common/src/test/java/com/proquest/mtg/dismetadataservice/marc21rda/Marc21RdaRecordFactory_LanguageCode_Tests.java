package com.proquest.mtg.dismetadataservice.marc21rda;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.DissLOCLanguage;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.DissLanguage;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcField;
import com.proquest.mtg.dismetadataservice.marc.MarcRecord;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;

public class Marc21RdaRecordFactory_LanguageCode_Tests extends
		Marc21RdaRecordFactory_Test_Helper {
	String tag = MarcTags.kLanguageCode;
	DisPubMetaData metaData;

	@Test
	public void withNull() throws Exception {
		DisPubMetaData metaData = new DisPubMetaData();
		DissLanguage language = new DissLanguage(null, null);
		metaData.setDissLanguages(Lists.newArrayList(language));
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(0));
	}
	
	@Test
	public void withEmpty() throws Exception {
		DisPubMetaData metaData = new DisPubMetaData();
		DissLanguage language = new DissLanguage("", "");
		metaData.setDissLanguages(Lists.newArrayList(language));
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(0));
	}



	@Test
	public void withOneLanguage() throws Exception {
		DissLanguage language = new DissLanguage("English", "EN");
		metaData = new DisPubMetaData();
		metaData.setDissLanguages(Lists.newArrayList(language));
		MarcRecord marc = factory.makeFrom(metaData);
		System.out.println(marc.getAllTags());
		System.out.println(marc.getAllFields());
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(0));
	}

	@Test
	public void withMultipleLanguages() throws Exception {
		DissLOCLanguage language1 = new DissLOCLanguage("English", "EN");
		DissLOCLanguage language2 = new DissLOCLanguage("Greek and English", "EG");
		DissLOCLanguage language3 = new DissLOCLanguage(
				"Estonian, Russian, and English.", "EZ");
		DissLanguage language4 = new DissLanguage("English", "EN");
		DissLanguage language5 = new DissLanguage("Greek and English", "EG");
		DissLanguage language6 = new DissLanguage(
				"Estonian, Russian, and English.", "EZ");
		metaData = new DisPubMetaData();
		metaData.setDissLOCLanguages(Lists.newArrayList(language1, language2,
				language3));
		metaData.setDissLanguages(Lists.newArrayList(language4, language5, language6));
		String expectedMarcFieldData2 = "0 " 
				+ MarcCharSet.kSubFieldIndicator
				+ "a" + "gre"
				+ MarcCharSet.kSubFieldIndicator
				+ "a" + "eng";
		String expectedMarcFieldData3 = "0 "
				+ MarcCharSet.kSubFieldIndicator
				+ "a" + "est"
				+ MarcCharSet.kSubFieldIndicator
				+ "a" + "rus"
				+ MarcCharSet.kSubFieldIndicator
				+ "a" + "eng";
		MarcRecord marc = factory.makeFrom(metaData);
		/* 041 tag */
		List<MarcField> languageCodeFields = marc.getFieldsMatchingTag(tag);
		assertThat(languageCodeFields.size(), is(0));
		/*Temporarily turned off*/
		/*assertThat(languageCodeFields.get(0).getData(),
				is(expectedMarcFieldData2));
		assertThat(languageCodeFields.get(1).getData(),
				is(expectedMarcFieldData3));*/
	}
}

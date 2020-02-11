package com.proquest.mtg.dismetadataservice.marc21rda;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.DissLanguage;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcField;
import com.proquest.mtg.dismetadataservice.marc.MarcRecord;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;

public class Marc21RdaRecordFactory_LanguageNote_Tests extends
		Marc21RdaRecordFactory_Test_Helper {
	String tag = MarcTags.kLanguageNote;
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
		String expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator
				+ "a" + "English";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 1);
	}

	@Test
	public void withMultipleLanguages() throws Exception {
		DissLanguage language1 = new DissLanguage("English", "EN");
		DissLanguage language2 = new DissLanguage("German and English", "GE");
		DissLanguage language3 = new DissLanguage(
				"Estonian, Russian, and English.", "ER");
		metaData = new DisPubMetaData();
		metaData.setDissLanguages(Lists.newArrayList(language1, language2,
				language3));
		String expectedMarcFieldData1 = "  " + MarcCharSet.kSubFieldIndicator
				+ "a" + "English";
		String expectedMarcFieldData2 = "  " + MarcCharSet.kSubFieldIndicator
				+ "a" + "German and English";
		String expectedMarcFieldData3 = "  " + MarcCharSet.kSubFieldIndicator
				+ "a" + "Estonian, Russian, and English.";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> subjectTermFields = marc.getFieldsMatchingTag(tag);
		assertThat(subjectTermFields.size(), is(3));
		assertThat(subjectTermFields.get(0).getData(),
				is(expectedMarcFieldData1));
		assertThat(subjectTermFields.get(1).getData(),
				is(expectedMarcFieldData2));
		assertThat(subjectTermFields.get(2).getData(),
				is(expectedMarcFieldData3));
	}
}

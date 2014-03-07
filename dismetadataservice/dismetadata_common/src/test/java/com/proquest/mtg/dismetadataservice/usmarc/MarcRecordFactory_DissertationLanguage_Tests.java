package com.proquest.mtg.dismetadataservice.usmarc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.DissLanguage;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcField;
import com.proquest.mtg.dismetadataservice.marc.MarcRecord;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;

public class MarcRecordFactory_DissertationLanguage_Tests extends
		UsMarcRecordFactoryBase_Test_Helper {
	String tag = MarcTags.kDissertationLanguage;
	String expectedMarcFieldData;
	DisPubMetaData metaData;

	@Test
	public void withNullURL() {
		metaData = new DisPubMetaData();
		metaData.setExternalURL(null);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withEmptyURL() {
		metaData = new DisPubMetaData();
		metaData.setExternalURL("");
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withOnlyLanguage() {
		metaData = new DisPubMetaData();
		List<DissLanguage> languages = new ArrayList<DissLanguage>();
		DissLanguage language = new DissLanguage("Description", "Code");
		languages.add(language);
		metaData.setDissLanguages(languages);
		expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "a"
				+ "Description";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(),
				is(expectedMarcFieldData));

	}

	@Test
	public void withMultipleLanguages() {
		metaData = new DisPubMetaData();
		List<DissLanguage> languages = new ArrayList<DissLanguage>();
		DissLanguage language1 = new DissLanguage("Description1", "Code1");
		languages.add(language1);
		DissLanguage language2 = new DissLanguage("Description2", "Code2");
		languages.add(language2);
		metaData.setDissLanguages(languages);
		expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "a"
				+ "Description1;Description2";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(),
				is(expectedMarcFieldData));

	}

}

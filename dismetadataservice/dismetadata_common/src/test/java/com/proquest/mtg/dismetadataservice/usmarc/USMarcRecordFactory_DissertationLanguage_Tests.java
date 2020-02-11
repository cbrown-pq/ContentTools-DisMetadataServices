package com.proquest.mtg.dismetadataservice.usmarc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.DissLOCLanguage;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.DissLanguage;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcField;
import com.proquest.mtg.dismetadataservice.marc.MarcRecord;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;

public class USMarcRecordFactory_DissertationLanguage_Tests extends
		USMarcRecordFactoryBase_Test_Helper {
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
		List<DissLOCLanguage> dissLOCLanguages = new ArrayList<DissLOCLanguage>();
		DissLOCLanguage LOClanguage = new DissLOCLanguage("Description", "Code");
		dissLOCLanguages.add(LOClanguage);
		metaData.setDissLOCLanguages(dissLOCLanguages);
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
		List<DissLOCLanguage> dissLOCLanguages = new ArrayList<DissLOCLanguage>();
		DissLOCLanguage language1 = new DissLOCLanguage("Description1", "Code1");
		dissLOCLanguages.add(language1);
		DissLOCLanguage language2 = new DissLOCLanguage("Description2", "Code2");
		dissLOCLanguages.add(language2);
		metaData.setDissLOCLanguages(dissLOCLanguages);
		expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "a"
				+ "Description1;Description2";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(),
				is(expectedMarcFieldData));

	}

}

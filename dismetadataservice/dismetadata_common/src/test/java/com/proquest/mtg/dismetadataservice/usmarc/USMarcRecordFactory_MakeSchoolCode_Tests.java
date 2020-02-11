package com.proquest.mtg.dismetadataservice.usmarc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.School;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcField;
import com.proquest.mtg.dismetadataservice.marc.MarcRecord;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;

public class USMarcRecordFactory_MakeSchoolCode_Tests extends
		USMarcRecordFactoryBase_Test_Helper {
	String tag1 = MarcTags.kLocalAddedEntry;
	String tag2 = MarcTags.kLocalNoteSchoolCode;

	String expectedMarcFieldData1, expectedMarcFieldData2;
	DisPubMetaData metaData;
	School school;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		school = new School();
		metaData = new DisPubMetaData();
	}

	@Test
	public void withNullSchoolCode() {
		school.setSchoolCode(null);
		metaData.setSchool(school);
		verifyMarcRecordHasEmptyField(metaData, tag1);
		verifyMarcRecordHasEmptyField(metaData, tag2);
	}

	@Test
	public void withEmptySchoolCode() {
		school.setSchoolCode("");
		metaData.setSchool(school);
		verifyMarcRecordHasEmptyField(metaData, tag1);
		verifyMarcRecordHasEmptyField(metaData, tag2);
	}

	@Test
	public void withNoSchool() {
		metaData.setSchool(school);
		verifyMarcRecordHasEmptyField(metaData, tag1);
		verifyMarcRecordHasEmptyField(metaData, tag2);
	}

	@Test
	public void withSchoolCode() {
		school.setSchoolCode("schoolcode");
		metaData.setSchool(school);
		expectedMarcFieldData1 = "  " + MarcCharSet.kSubFieldIndicator + "a"
				+ "schoolcode";
		expectedMarcFieldData2 = "  " + MarcCharSet.kSubFieldIndicator + "a"
				+ "School code: " + "schoolcode.";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag1 = marc.getFieldsMatchingTag(tag1);
		assertThat(fieldsMatchingTag1.size(), is(1));
		assertThat(fieldsMatchingTag1.get(0).getData(),
				is(expectedMarcFieldData1));
		List<MarcField> fieldsMatchingTag2 = marc.getFieldsMatchingTag(tag2);
		assertThat(fieldsMatchingTag2.size(), is(1));
		assertThat(fieldsMatchingTag2.get(0).getData(),
				is(expectedMarcFieldData2));
	}
}

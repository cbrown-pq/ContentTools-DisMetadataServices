package com.proquest.mtg.dismetadataservice.marc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.School;

public class MarcRecordFactory_MakeCorporateEntry_Tests extends
		MarcRecordFactoryBase_Tests {

	String tag = MarcTags.kCorporatename;

	String expectedMarcFieldData;
	DisPubMetaData metaData;
	List<String> departments;
	String corpName;
	School school;
	String schoolName;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		departments = new ArrayList<String>();
		school = new School();
		metaData = new DisPubMetaData();
	}

	@Test
	public void withNullCorporateName() {
		corpName = null;
		departments.add(corpName);
		metaData.setDepartments(departments);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withEmptyCorporateName() {
		corpName = "";
		departments.add(corpName);
		metaData.setDepartments(departments);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withNoCorporateName() {
		metaData.setDepartments(departments);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withNullSchoolName() {
		school.setSchoolName("");
		metaData.setSchool(school);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withEmptySchoolName() {
		school.setSchoolName("");
		metaData.setSchool(school);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withNoSchoolName() {
		metaData.setSchool(school);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withCorporateNamesAndWithSchool() {
		corpName = "CorporateName";
		departments.add(corpName);
		schoolName = "SchoolName";
		school.setSchoolName(schoolName);
		metaData.setDepartments(departments);
		metaData.setSchool(school);
		expectedMarcFieldData = "20" + MarcCharSet.kSubFieldIndicator + "a"
				+ schoolName + "." + MarcCharSet.kSubFieldIndicator + "b"
				+ corpName + ".";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag1 = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag1.size(), is(1));
		assertThat(fieldsMatchingTag1.get(0).getData(),
				is(expectedMarcFieldData));
	}
	
	@Test
	public void withMultipleCorporateNamesAndWithSchool() {
		corpName = "CorporateName1";
		departments.add(corpName);
		departments.add("CorporateName2");
		schoolName = "SchoolName";
		school.setSchoolName(schoolName);
		metaData.setDepartments(departments);
		metaData.setSchool(school);
		expectedMarcFieldData = "20" + MarcCharSet.kSubFieldIndicator + "a"
				+ schoolName + "." + MarcCharSet.kSubFieldIndicator + "b"
				+ corpName + "." + "CorporateName2" + ".";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag1 = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag1.size(), is(1));
		assertThat(fieldsMatchingTag1.get(0).getData(),
				is(expectedMarcFieldData));
	}

	@Test
	public void withCorporateNameAndNoSchoolHasNoTag() {
		corpName = "CorporateName";
		departments.add(corpName);
		metaData.setDepartments(departments);
		expectedMarcFieldData = "20" + MarcCharSet.kSubFieldIndicator + "a"
				+ schoolName + ".";
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withNoCorporateNameAndWithSchool() {
		schoolName = "SchoolName";
		school.setSchoolName(schoolName);
		metaData.setSchool(school);
		expectedMarcFieldData = "20" + MarcCharSet.kSubFieldIndicator + "a"
				+ schoolName + ".";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag1 = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag1.size(), is(1));
		assertThat(fieldsMatchingTag1.get(0).getData(),
				is(expectedMarcFieldData));
	}
}

package com.proquest.mtg.dismetadataservice.marc21rda;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.School;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcField;
import com.proquest.mtg.dismetadataservice.marc.MarcRecord;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;

public class Marc21RdaRecordFactory_MakeCorporateEntry_Tests extends
				Marc21RdaRecordFactory_Test_Helper {

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
		expectedMarcFieldData = "2 " + MarcCharSet.kSubFieldIndicator + "a"
				+ schoolName + "." + MarcCharSet.kSubFieldIndicator + "b"
				+ corpName + "." +  MarcCharSet.kSubFieldIndicator + "edegree granting institution" + ".";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag1 = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag1.size(), is(1));
		assertThat(fieldsMatchingTag1.get(0).getData(),
				is(expectedMarcFieldData));
	}
	
	@Test
	public void withMultipleCorporateNamesAndWithSchool() {
		departments.add("CorporateName1");
		departments.add("CorporateName2");
		schoolName = "SchoolName";
		school.setSchoolName(schoolName);
		metaData.setDepartments(departments);
		metaData.setSchool(school);
		String expectedMarcFieldData1 = "2 " + MarcCharSet.kSubFieldIndicator + "a"
				+ schoolName + "." + MarcCharSet.kSubFieldIndicator + "b"
				+ "CorporateName1" + "." + MarcCharSet.kSubFieldIndicator + "edegree granting institution" + ".";
		String expectedMarcFieldData2 = "2 " + MarcCharSet.kSubFieldIndicator + "a"
				+ schoolName + "." + MarcCharSet.kSubFieldIndicator + "b"
				+ "CorporateName2" + "." + MarcCharSet.kSubFieldIndicator + "edegree granting institution" + ".";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag1 = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag1.size(), is(2));
		assertThat(fieldsMatchingTag1.get(0).getData(),
				is(expectedMarcFieldData1));
		assertThat(fieldsMatchingTag1.get(1).getData(),
				is(expectedMarcFieldData2));
	}

	@Test
	public void withCorporateNameAndNoSchool() {
		corpName = "CorporateName";
		departments.add(corpName);
		metaData.setDepartments(departments);
		expectedMarcFieldData = "2 " + MarcCharSet.kSubFieldIndicator + "b"
				+ corpName + "."
				+ MarcCharSet.kSubFieldIndicator 
				+ "edegree granting institution"
				+ ".";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag1 = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag1.size(), is(1));
		assertThat(fieldsMatchingTag1.get(0).getData(),
				is(expectedMarcFieldData));
	}
	
	@Test
	public void withCorporateNamesAndNoSchool() {
		String corpName1 = "CorporateName1";
		String corpName2 = "CorporateName2";
		departments.add(corpName1);
		departments.add(corpName2);
		metaData.setDepartments(departments);
		String expectedMarcFieldData1 = "2 " + MarcCharSet.kSubFieldIndicator + "b"
				+ corpName1 + "."
				+ MarcCharSet.kSubFieldIndicator 
				+ "edegree granting institution"
				+ ".";
		String expectedMarcFieldData2 = "2 " + MarcCharSet.kSubFieldIndicator + "b"
				+ corpName2 + "."
				+ MarcCharSet.kSubFieldIndicator 
				+ "edegree granting institution"
				+ ".";
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag1 = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag1.size(), is(2));
		assertThat(fieldsMatchingTag1.get(0).getData(),
				is(expectedMarcFieldData1));
		assertThat(fieldsMatchingTag1.get(1).getData(),
				is(expectedMarcFieldData2));
	}

	@Test
	public void withNoCorporateNameAndWithSchool_Results() {
		schoolName = "SchoolName";
		school.setSchoolName(schoolName);
		metaData.setSchool(school);
		String expectedMarcFieldData = "2 " + MarcCharSet.kSubFieldIndicator 
										+ "a" + schoolName 
										+ "." + MarcCharSet.kSubFieldIndicator 
										+ "edegree granting institution" + ".";		
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(),
				is(expectedMarcFieldData));
	}
	

}

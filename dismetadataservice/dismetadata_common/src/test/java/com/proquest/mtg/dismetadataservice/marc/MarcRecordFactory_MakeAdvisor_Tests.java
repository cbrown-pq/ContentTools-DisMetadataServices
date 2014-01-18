package com.proquest.mtg.dismetadataservice.marc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisor;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisors;

public class MarcRecordFactory_MakeAdvisor_Tests extends
		MarcRecordFactoryBase_Tests {

	String tag = MarcTags.kAdvisorname;
	String expectedMarcFieldData1;
	DisPubMetaData metaData;
	Advisor advisor;
	Advisors advisors;
	List<Advisor> advisorList;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		advisor = new Advisor();
		advisors = new Advisors();
		advisorList = new ArrayList<Advisor>();
		metaData = new DisPubMetaData();
	}

	@Test
	public void withNullAdvisor() {
		metaData.setAdvisors(advisors);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withEmptyAdvisor() {
		advisor.setAdvisorFullName("");
		advisorList.add(advisor);
		metaData.setAdvisors(advisors);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withAdvisorFullNameAndMiddleInitial() {
		advisor.setAdvisorFullName("John J. King");
		advisorList.add(advisor);
		advisors.setAdvisor(advisorList);
		metaData.setAdvisors(advisors);
		MarcRecord marc = factory.makeFrom(metaData);
		String expectedData = "10" + MarcCharSet.kSubFieldIndicator + "a"
				+ "King, John J.," + MarcCharSet.kSubFieldIndicator
				+ "eadvisor";
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}

	@Test
	public void withAdvisorAndMiddleInitialAndGenTitle() {
		advisor.setAdvisorFullName("John J. King Jr.");
		advisorList.add(advisor);
		advisors.setAdvisor(advisorList);
		metaData.setAdvisors(advisors);
		MarcRecord marc = factory.makeFrom(metaData);
		String expectedData = "10" + MarcCharSet.kSubFieldIndicator + "a"
				+ "King, John J.,Jr.," + MarcCharSet.kSubFieldIndicator
				+ "eadvisor";
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}

	@Test
	public void withAdvisorAndNoMiddleInitialAndGenTitle() {
		advisor.setAdvisorFullName("John King Jr.");
		advisorList.add(advisor);
		advisors.setAdvisor(advisorList);
		metaData.setAdvisors(advisors);
		MarcRecord marc = factory.makeFrom(metaData);
		String expectedData = "10" + MarcCharSet.kSubFieldIndicator + "a"
				+ "King, John,Jr.," + MarcCharSet.kSubFieldIndicator
				+ "eadvisor";
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}

	@Test
	public void withAdvisorAndNoMiddleInitialAndGenTitle2() {
		advisor.setAdvisorFullName("John King III");
		advisorList.add(advisor);
		advisors.setAdvisor(advisorList);
		metaData.setAdvisors(advisors);
		MarcRecord marc = factory.makeFrom(metaData);
		String expectedData = "10" + MarcCharSet.kSubFieldIndicator + "a"
				+ "King, John,III," + MarcCharSet.kSubFieldIndicator
				+ "eadvisor";
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}

	@Test
	public void withAdvisorAndNoMiddleInitialAndGenTitle3() {
		advisor.setAdvisorFullName("John R. King III");
		advisorList.add(advisor);
		advisors.setAdvisor(advisorList);
		metaData.setAdvisors(advisors);
		MarcRecord marc = factory.makeFrom(metaData);
		String expectedData = "10" + MarcCharSet.kSubFieldIndicator + "a"
				+ "King, John R.,III," + MarcCharSet.kSubFieldIndicator
				+ "eadvisor";
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}

	@Test
	public void withAdvisorAndNoMiddleInitialAndGenTitle4() {
		advisor.setAdvisorFullName("John R. King, III");
		advisorList.add(advisor);
		advisors.setAdvisor(advisorList);
		metaData.setAdvisors(advisors);
		MarcRecord marc = factory.makeFrom(metaData);
		String expectedData = "10" + MarcCharSet.kSubFieldIndicator + "a"
				+ "King, John R.,III," + MarcCharSet.kSubFieldIndicator
				+ "eadvisor";
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}

	@Test
	public void withAdvisorAndNoMiddleInitialAndGenTitle5() {
		advisor.setAdvisorFullName("John R. King, IX");
		advisorList.add(advisor);
		advisors.setAdvisor(advisorList);
		metaData.setAdvisors(advisors);
		MarcRecord marc = factory.makeFrom(metaData);
		String expectedData = "10" + MarcCharSet.kSubFieldIndicator + "a"
				+ "King, John R.,IX," + MarcCharSet.kSubFieldIndicator
				+ "eadvisor";
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}

	@Test
	public void withAdvisorAndNoMiddleInitialAndGenTitle6() {
		advisor.setAdvisorFullName(" R. John King III");
		advisorList.add(advisor);
		advisors.setAdvisor(advisorList);
		metaData.setAdvisors(advisors);
		MarcRecord marc = factory.makeFrom(metaData);
		String expectedData = "10" + MarcCharSet.kSubFieldIndicator + "a"
				+ "King, R. John,III," + MarcCharSet.kSubFieldIndicator
				+ "eadvisor";
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}

	@Test
	public void withAdvisorAndSurNameOnly() {
		advisor.setAdvisorFullName("John Van@de@Voorde");
		advisorList.add(advisor);
		advisors.setAdvisor(advisorList);
		metaData.setAdvisors(advisors);
		MarcRecord marc = factory.makeFrom(metaData);
		String expectedData = "10" + MarcCharSet.kSubFieldIndicator + "a"
				+ "Van de Voorde, John," + MarcCharSet.kSubFieldIndicator
				+ "eadvisor";
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}

	@Test
	public void withAdvisorFirstAndLastNameOnly() {
		advisor.setAdvisorFullName("John King");
		advisorList.add(advisor);
		advisors.setAdvisor(advisorList);
		metaData.setAdvisors(advisors);
		MarcRecord marc = factory.makeFrom(metaData);
		String expectedData = "10" + MarcCharSet.kSubFieldIndicator + "a"
				+ "King, John," + MarcCharSet.kSubFieldIndicator + "eadvisor";
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}

	@Test
	public void withAdvisorInvalidFormat1() {
		advisor.setAdvisorFullName("A. John King");
		advisorList.add(advisor);
		advisors.setAdvisor(advisorList);
		metaData.setAdvisors(advisors);
		MarcRecord marc = factory.makeFrom(metaData);
		String expectedData = "10" + MarcCharSet.kSubFieldIndicator + "a"
				+ "A. John King," + MarcCharSet.kSubFieldIndicator + "eadvisor";
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag);
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedData));
	}
}

package com.proquest.mtg.dismetadataservice.marc21rda;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisor;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisors;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcField;
import com.proquest.mtg.dismetadataservice.marc.MarcRecord;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;

public class Marc21RdaRecordFactory_UncontrolledName_Tests extends
		Marc21RdaRecordFactory_Test_Helper {

	String tag = MarcTags.kUncontrolledName;
	DisPubMetaData metaData;
	Advisors advisors;
	Advisor advisor;

	@Before
	public void setup() {
		advisors = new Advisors();
		metaData = new DisPubMetaData();
	}

	@Test
	public void withNoAdvisorsAndCmteMembers() {
		metaData.setAdvisors(advisors);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withAdvisor() {
		String advisorString = "Advisers: Ou Li";
		advisors.setAdvisorsExodusStr(advisorString);
		metaData.setAdvisors(advisors);
		String expectedMarcFieldData = "1 " + MarcCharSet.kSubFieldIndicator
				+ "a" + "Ou Li" 
				+ MarcCharSet.kSubFieldIndicator 
				+ "edegree supervisor" 
				+ ".";
		MarcRecord marc = factory.makeFrom(metaData);

		List<MarcField> advisorTermFields = marc.getFieldsMatchingTag(tag);
		assertThat(advisorTermFields.size(), is(1));
		assertThat(advisorTermFields.get(0).getData(),
				is(expectedMarcFieldData));
	}

	@Test
	public void withAdvisorsNames() {
		String advisorString = "Advisers: JoAnn Jodi Crandall; Beverly Bickel";
		advisors.setAdvisorsExodusStr(advisorString);
		metaData.setAdvisors(advisors);
		String expectedMarcFieldData1 = "1 " + MarcCharSet.kSubFieldIndicator
				+ "a" 
				+ "JoAnn Jodi Crandall" 
				+ MarcCharSet.kSubFieldIndicator 
				+ "edegree supervisor" 
				+ ".";
		String expectedMarcFieldData2 = "1 " + MarcCharSet.kSubFieldIndicator
				+ "a" + "Beverly Bickel" 
				+ MarcCharSet.kSubFieldIndicator 
				+ "edegree supervisor" 
				+ ".";
		MarcRecord marc = factory.makeFrom(metaData);

		List<MarcField> advisorTermFields = marc.getFieldsMatchingTag(tag);
		assertThat(advisorTermFields.size(), is(2));
		assertThat(advisorTermFields.get(0).getData(),
				is(expectedMarcFieldData1));
		assertThat(advisorTermFields.get(1).getData(),
				is(expectedMarcFieldData2));
	}

}

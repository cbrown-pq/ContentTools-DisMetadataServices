package com.proquest.mtg.dismetadataservice.marc21rda;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.Advisor;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.Advisors;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.CmteMember;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;

public class Marc21RdaRecordFactory_SourceAdvisor_Tests extends
		Marc21RdaRecordFactory_Test_Helper {

	String tag = MarcTags.kGeneralNote;
	String expectedMarcFieldData;
	DisPubMetaData metaData;
	Advisors advisors;
	Advisor advisor;
	List<CmteMember> cmteMembers;
	CmteMember cmteMember;

	@Before
	public void setup() {
		advisors = new Advisors();
		cmteMember = new CmteMember();
		cmteMembers = new ArrayList<CmteMember>();
		metaData = new DisPubMetaData();
	}

	@Test
	public void withNoAdvisorsAndCmteMembers() {
		metaData.setAdvisors(advisors);
		metaData.setCmteMembers(cmteMembers);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withAdvisors_NullCmteMembers() {
		String advisorString = "Advisors: JoAnn Jodi Crandall;Beverly Bickel";
		advisors.setAdvisorsECMSStr(advisorString);
		metaData.setAdvisors(advisors);
		metaData.setCmteMembers(cmteMembers);
		expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "a"
				+ "Advisors: JoAnn Jodi Crandall; Beverly Bickel" + ".";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 1);

	}

	@Test
	public void withAdvisorsAndCmteMembers() {
		String advisorString = "Advisors: JoAnn Jodi Crandall; Beverly Bickel";
		advisors.setAdvisorsECMSStr(advisorString);
		metaData.setAdvisors(advisors);
		cmteMember.setFirstName("William");
		cmteMember.setLastName("Dalton");
		cmteMember.setMiddleName("T.");
		cmteMember.setSuffix("III");
		cmteMembers.add(cmteMember);
		metaData.setCmteMembers(cmteMembers);
		expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "a"
				+ advisorString + "  Committee members: "
				+ "William T. Dalton, III" + ".";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 1);

	}
	
	@Test
	public void withAdvisorsAndMultipleCmteMembers() {
		String advisorString = "Advisors: JoAnn Jodi Crandall;Beverly Bickel";
		advisors.setAdvisorsECMSStr(advisorString);
		metaData.setAdvisors(advisors);
		cmteMember.setFirstName("William");
		cmteMember.setLastName("Dalton");
		cmteMember.setMiddleName("T.");
		cmteMember.setSuffix("III");
		CmteMember cmteMember2 = new CmteMember();
		cmteMember2.setFirstName("Rodney");
		cmteMember2.setLastName("Beaulieu");
		cmteMember2.setMiddleName("J.");
		cmteMembers.add(cmteMember);
		cmteMembers.add(cmteMember2);
		metaData.setCmteMembers(cmteMembers);
		expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "a"
				+ "Advisors: JoAnn Jodi Crandall; Beverly Bickel" + "  Committee members: "
				+ "William T. Dalton, III; Rodney J. Beaulieu" + ".";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 1);

	}

}

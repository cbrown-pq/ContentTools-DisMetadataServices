package com.proquest.mtg.dismetadataservice.metadata;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisor;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.AlternateTitle;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.CmteMember;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.DissLanguage;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.FormatRestriction;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Keyword;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SalesRestriction;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Subject;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SuppFile;
import com.proquest.mtg.dismetadataservice.metadata.Author.Degree;

public class DissertationMatcher extends TypeSafeMatcher<DisPubMetaData> {
	private final DisPubMetaData expected;
	private List<String> errorDescriptions;
	
	public DissertationMatcher(DisPubMetaData expected) {
		this.expected = expected;
	}
	
	@Override
	protected boolean matchesSafely(DisPubMetaData actual) {
		resetErrors();
		
		verify("PubNumber", expected.getPubNumber(), actual.getPubNumber());
		verify("ISBN", expected.getISBN(), actual.getISBN());
		verify("BLNumber", expected.getBLNumber(), actual.getBLNumber());
		verify("PageCount", expected.getPageCount(), actual.getPageCount());
		verify("PubPageNum", expected.getPubPageNum(), actual.getPubPageNum());
		verify("Publisher", expected.getPublisher(), actual.getPublisher());
		verify("ReferenceLocation", expected.getReferenceLocation(), actual.getReferenceLocation());
		verify("ExternalURL", expected.getExternalURL(), actual.getExternalURL());
		verify("PqOpenUrl", expected.getPqOpenURL(), actual.getPqOpenURL());
		verifyTitle(actual);
		verifyAbstract(actual);
		verifyBatch(actual);
		verifySchool(actual);
		verifySubjects(actual);
		verifyAlternateTitles(actual);
		verifyDissLanguages(actual);
		verifyDepartments(actual);
		verifyKeywords(actual);
		verifySalesRestriction(actual);
		verifyFormatRestriction(actual);
		verifySupplementalFiles(actual);
		verifyCommittee(actual);
		verifyAdvisors(actual);
		verifyAuthors(actual);
		verifyPdfStatus(actual);
		
		return hasErrors();
	}

	private void resetErrors() {
		errorDescriptions = Lists.newArrayList();
	}

	private boolean hasErrors() {
		return errorDescriptions.isEmpty();
	}

	private void verifyTitle(DisPubMetaData actual) {
		if (null != expected.getTitle()) {
			if (verifyNotNullValue("Title", actual.getTitle())) {
				verify("Electronic Title",  expected.getTitle().getElectronicTitle(), 
						actual.getTitle().getElectronicTitle());
				verify("Master Title",  expected.getTitle().getMasterTitle(), 
						actual.getTitle().getMasterTitle());
				verify("English Overwrite Title",  expected.getTitle().getEnglishOverwriteTitle(), 
						actual.getTitle().getEnglishOverwriteTitle());
				verify("Foreign Title",  expected.getTitle().getForeignTitle(), 
						actual.getTitle().getForeignTitle());
			}
		}
		else {
			verifyNullValue("Title", actual.getTitle());
		}
	}

	private void verifyAbstract(DisPubMetaData actual) {
		if (null != expected.getAbstract()) {
			if (verifyNotNullValue("Abstract", actual.getAbstract())) {
				verify("Abstract Value",  expected.getAbstract(), actual.getAbstract());
			}
		}
		else {
			verifyNullValue("Abstract", actual.getAbstract());
		}
	}

	private void verifyBatch(DisPubMetaData actual) {
		if (null != expected.getBatch()) {
			if (verifyNotNullValue("Batch", actual.getBatch())) {
				verify("Batch.DAISectionCode", expected.getBatch().getDAISectionCode(), actual.getBatch().getDAISectionCode());
				verify("Batch.DBTypeCode", expected.getBatch().getDBTypeCode(), actual.getBatch().getDBTypeCode());
				verify("Batch.DBTypeDesc(", expected.getBatch().getDBTypeDesc(), actual.getBatch().getDBTypeDesc());
				verify("Batch.VolumeIssue", expected.getBatch().getVolumeIssue(), actual.getBatch().getVolumeIssue());
			}
		}
		else {
			verifyNullValue("Batch", actual.getBatch());
		}
	}

	private void verifySchool(DisPubMetaData actual) {
		if (null != expected.getSchool()) {
			if (verifyNotNullValue("School", actual.getSchool())) {
				verify("Schoool.Code", expected.getSchool().getSchoolCode(), actual.getSchool().getSchoolCode());
				verify("Schoool.Name", expected.getSchool().getSchoolName(), actual.getSchool().getSchoolName());
				verify("Schoool.Country", expected.getSchool().getSchoolCountry(), actual.getSchool().getSchoolCountry());
				verify("Schoool.State", expected.getSchool().getSchoolState(), actual.getSchool().getSchoolState());
			}
		}
		else {
			verifyNullValue("School", actual.getSchool());
		}
	}

	private void verifySubjects(DisPubMetaData actual) {
		if (null != expected.getSubjects()) {
			if (verifyNotNullValue("Subjects", actual.getSubjects())) {
				int expectedCount = expected.getSubjects().size();
				int actualCount = actual.getSubjects().size();
				verify("Subjects Count", expectedCount, actualCount);
				if (expectedCount == actualCount) {
					for (int i=0; i<expectedCount; ++i) {
						Subject expectedItem = expected.getSubjects().get(i);
						Subject actualItem = actual.getSubjects().get(i);
						verify("Subject Group " + i, expectedItem.getSubjectGroupDesc(), actualItem.getSubjectGroupDesc());
						verify("Subject Code " + i, expectedItem.getSubjectCode(), actualItem.getSubjectCode());
						verify("Subject Desc " + i, expectedItem.getSubjectDesc(), actualItem.getSubjectDesc());
						verify("Subject Sequence Number " + i, expectedItem.getSequenceNumber(), actualItem.getSequenceNumber());
					}
				}
			}
		}
		else {
			verifyNullValue("Subjects", actual.getSubjects());
		}
	}
	
	private void verifyAlternateTitles(DisPubMetaData actual) {
		if (null != expected.getAlternateTitles()) {
			if (verifyNotNullValue("AlternateTitles", actual.getAlternateTitles())) {
				int expectedCount = expected.getAlternateTitles().size();
				int actualCount = actual.getAlternateTitles().size();
				verify("AlternateTitles Count", expectedCount, actualCount);
				if (expectedCount == actualCount) {
					for (int i=0; i<expectedCount; ++i) {
						AlternateTitle expectedItem = expected.getAlternateTitles().get(i);
						AlternateTitle actualItem = actual.getAlternateTitles().get(i);
						verify("AlternateTitle Value " + i, expectedItem.getAltTitle(), actualItem.getAltTitle());
						verify("AlternateTitle Language " + i, expectedItem.getLanguage(), actualItem.getLanguage());
					}
				}
			}
		}
		else {
			verifyNullValue("AlternateTitles", actual.getAlternateTitles());
		}
	}
	
	private void verifyKeywords(DisPubMetaData actual) {
		if (null != expected.getKeywords()) {
			if (verifyNotNullValue("Keywords", actual.getKeywords())) {
				int expectedCount = expected.getKeywords().size();
				int actualCount = actual.getKeywords().size();
				verify("Keywords Count", expectedCount, actualCount);
				if (expectedCount == actualCount) {
					for (int i=0; i<expectedCount; ++i) {
						Keyword expectedItem = expected.getKeywords().get(i);
						Keyword actualItem = actual.getKeywords().get(i);
						verify("Keyword Value " + i, expectedItem.getValue(), actualItem.getValue());
						verify("Keywords Source " + i, expectedItem.getSource(), actualItem.getSource());
					}
				}
			}
		}
		else {
			verifyNullValue("Keywords", actual.getKeywords());
		}
	}
	
	private void verifySalesRestriction(DisPubMetaData actual) {
		if (null != expected.getSalesRestrictions()) {
			if (verifyNotNullValue("Sales Restriction", actual.getSalesRestrictions())) {
				int expectedCount = expected.getSalesRestrictions().size();
				int actualCount = actual.getSalesRestrictions().size();
				verify("Sales Restriction Count", expectedCount, actualCount);
				if (expectedCount == actualCount) {
					for (int i=0; i<expectedCount; ++i) {
						SalesRestriction expectedItem = expected.getSalesRestrictions().get(i);
						SalesRestriction actualItem = actual.getSalesRestrictions().get(i);
						verify("Sales Restriction Code " + i, expectedItem.getCode(), actualItem.getCode());
						verify("Sales Restriction Start date " + i, expectedItem.getRestrictionStartDate(), 
								actualItem.getRestrictionStartDate());
						verify("Sales Restriction End date " + i, expectedItem.getRestrictionEndDate(), 
								actualItem.getRestrictionEndDate());
						verify("Sales Restriction Description " + i, expectedItem.getDescription(), 
								actualItem.getDescription());
					}
				}
			}
		}
		else {
			verifyNullValue("Sales Restriction", actual.getSalesRestrictions());
		}
	}
	
	private void verifyFormatRestriction(DisPubMetaData actual) {
		if (null != expected.getFormatRestrictions()) {
			if (verifyNotNullValue("Format Restriction", actual.getFormatRestrictions())) {
				int expectedCount = expected.getFormatRestrictions().size();
				int actualCount = actual.getFormatRestrictions().size();
				verify("Format Restriction Count", expectedCount, actualCount);
				if (expectedCount == actualCount) {
					for (int i=0; i<expectedCount; ++i) {
						FormatRestriction expectedItem = expected.getFormatRestrictions().get(i);
						FormatRestriction actualItem = actual.getFormatRestrictions().get(i);
						verify("Format Restriction Code " + i, expectedItem.getCode(), actualItem.getCode());
					}
				}
			}
		}
		else {
			verifyNullValue("Format Restriction", actual.getFormatRestrictions());
		}
	}
	
	private void verifySupplementalFiles(DisPubMetaData actual) {
		if (null != expected.getSuppFiles()) {
			if (verifyNotNullValue("SuppFiles", actual.getSuppFiles())) {
				int expectedCount = expected.getSuppFiles().size();
				int actualCount = actual.getSuppFiles().size();
				verify("SuppFiles Count", expectedCount, actualCount);
				if (expectedCount == actualCount) {
					for (int i=0; i<expectedCount; ++i) {
						SuppFile expectedItem = expected.getSuppFiles().get(i);
						SuppFile actualItem = actual.getSuppFiles().get(i);
						verify("SuppFile FileName " + i, expectedItem.getSuppFilename(), actualItem.getSuppFilename());
						verify("SuppFile Description " + i, expectedItem.getSuppFileDesc(), actualItem.getSuppFileDesc());
						verify("SuppFile Category " + i, expectedItem.getSuppFileCategory(), actualItem.getSuppFileCategory());
					}
				}
			}
		}
		else {
			verifyNullValue("SuppFiles", actual.getSuppFiles());
		}
	}
	
	private void verifyCommittee(DisPubMetaData actual) {
		if (null != expected.getCmteMembers()) {
			if (verifyNotNullValue("CmteMembers", actual.getCmteMembers())) {
				int expectedCount = expected.getCmteMembers().size();
				int actualCount = actual.getCmteMembers().size();
				verify("CmteMemberS Count", expectedCount, actualCount);
				if (expectedCount == actualCount) {
					for (int i=0; i<expectedCount; ++i) {
						CmteMember expectedItem = expected.getCmteMembers().get(i);
						CmteMember actualItem = actual.getCmteMembers().get(i);
						verify("CmteMember FirstName " + i, expectedItem.getFirstName(), actualItem.getFirstName());
						verify("CmteMember MiddleName " + i, expectedItem.getMiddleName(), actualItem.getMiddleName());
						verify("CmteMember LastName " + i, expectedItem.getLastName(), actualItem.getLastName());
						verify("CmteMember Suffix " + i, expectedItem.getSuffix(), actualItem.getSuffix());
					}
				}
			}
		}
		else {
			verifyNullValue("CmteMembers", actual.getCmteMembers());
		}
	}
	
	private void verifyAdvisors(DisPubMetaData actual) {
		if (null != expected.getAdvisors()) {
			if (verifyNotNullValue("Advisors", actual.getAdvisors())) {
				verify("Advisor Exodus String", 
						expected.getAdvisors().getAdvisorsExodusStr(),
						actual.getAdvisors().getAdvisorsExodusStr());
				int expectedCount = expected.getAdvisors().getAdvisor().size();
				int actualCount = actual.getAdvisors().getAdvisor().size();
				verify("Advisors Count", expectedCount, actualCount);
				if (expectedCount == actualCount) {
					for (int i=0; i<expectedCount; ++i) {
						Advisor expectedItem = expected.getAdvisors().getAdvisor().get(i);
						Advisor actualItem = actual.getAdvisors().getAdvisor().get(i);
						verify("Advisor FullName " + i, expectedItem.getAdvisorFullName(), actualItem.getAdvisorFullName());
						if (null != expectedItem.getAltAdvisorFullName()) {
							if (verifyNotNullValue("Advisor AltAdvisorFullName" + i, actualItem.getAltAdvisorFullName())) {
								verify("Advisor AltAdvisorFullName Value" + i, 
										expectedItem.getAltAdvisorFullName(),
										actualItem.getAltAdvisorFullName());
							}
						}
						else {
							verifyNullValue("Advisor AltAdvisorFullName" + i, actualItem.getAltAdvisorFullName());
						}
					}
				}
			}
		}
		else {
			verifyNullValue("Advisors", actual.getAdvisors());
		}
	}
	
	private void verifyAuthors(DisPubMetaData actual) {
		if (null != expected.getAuthors()) {
			if (verifyNotNullValue("Authors", actual.getAuthors())) {
				int expectedCount = expected.getAuthors().size();
				int actualCount = actual.getAuthors().size();
				verify("Authors Count", expectedCount, actualCount);
				if (expectedCount == actualCount) {
					for (int i=0; i<expectedCount; ++i) {
						Author expectedItem = expected.getAuthors().get(i);
						Author actualItem = actual.getAuthors().get(i);
						verify("Authors FullName " + i, expectedItem.getAuthorFullName(), actualItem.getAuthorFullName());
						verify("Authors FirstName " + i, expectedItem.getFirstName(), actualItem.getFirstName());
						verify("Authors MiddleName " + i, expectedItem.getMiddleName(), actualItem.getMiddleName());
						verify("Authors SequenceNumber " + i, expectedItem.getSequenceNumber(), actualItem.getSequenceNumber());
						if (null != expectedItem.getAltAuthorFullName()) {
							if (verifyNotNullValue("Authors AltAuthorsFullName " + i, actualItem.getAltAuthorFullName())) {
								verify("Authors AltAuthorsFullName Value " + i, 
										expectedItem.getAltAuthorFullName(),
										actualItem.getAltAuthorFullName());
							}
						}
						else {
							verifyNullValue("Authors AltAuthorFullName" + i, actualItem.getAltAuthorFullName());
						}
						if (null != expectedItem.getDegrees()) {
							if (verifyNotNullValue("Authors Degrees " + i, actualItem.getDegrees())) {
									verifyAuthorDegrees("Authors Degrees " + i, expectedItem.getDegrees(), 
											actualItem.getDegrees());
							}
						}
						else {
							verifyNullValue("Authors Degrees" + i, actualItem.getDegrees());
						}
					}
				}
			}
		}
		else {
			verifyNullValue("Authors", actual.getAuthors());
		}
	}
	
	private void verifyAuthorDegrees(String context, List<Degree> expected, List<Degree> actual) {
		int expectedCount = expected.size();
		int actualCount = actual.size();
		verify(context + " Count", expectedCount, actualCount);
		if (expectedCount == actualCount) {
			for (int i=0; i<expectedCount; ++i) {
				Degree expectedItem = expected.get(i);
				Degree actualItem = actual.get(i);
				verify(context + " Degree Code " + i, expectedItem.getDegreeCode(), actualItem.getDegreeCode());
				verify(context + " Degree Description" + i, expectedItem.getDegreeDescription(), actualItem.getDegreeDescription());
				verify(context + " Degree Year" + i, expectedItem.getDegreeYear(), actualItem.getDegreeYear());
				verify(context + " Degree Sequence Number" + i, expectedItem.getSequenceNumber(), actualItem.getSequenceNumber());
			}
		}
	}
	
	private void verifyDissLanguages(DisPubMetaData actual) {
		if (null != expected.getDissLanguages()) {
			if (verifyNotNullValue("DissLanguages", actual.getDissLanguages())) {
				int expectedCount = expected.getDissLanguages().size();
				int actualCount = actual.getDissLanguages().size();
				verify("Count", expectedCount, actualCount);
				if (expectedCount == actualCount) {
					for (int i=0; i<expectedCount; ++i) {
						DissLanguage expectedItem = expected.getDissLanguages().get(i);
						DissLanguage actualItem = actual.getDissLanguages().get(i);
						verify("Language Code " + i, expectedItem.getLanguageCode(), actualItem.getLanguageCode());
						verify("Language Description" + i, expectedItem.getLanguageDescription(), 
								actualItem.getLanguageDescription());
					}
				}
			}
		}
		else {
			verifyNullValue("DissLanguages", actual.getDissLanguages());
		}
	}
	
	private void verifyDepartments(DisPubMetaData actual) {
		if (null != expected.getDepartments()) {
			if (verifyNotNullValue("Departements", actual.getDepartments())) {
				verifyList("Departments", 
					expected.getDepartments(),
					actual.getDepartments());
			}
		}
		else {
			verifyNullValue("Departements", actual.getDepartments());
		}
	}
	
	private void verifyPdfStatus(DisPubMetaData actual) {
		if (null != expected.getPdfStatus()) {
			if (verifyNotNullValue("Pdf Status", actual.getPdfStatus())) {
				verify("PdfStatus.AvailableDate", expected.getPdfStatus().getPdfAvailableDate(), 
						actual.getPdfStatus().getPdfAvailableDate());
			}
		}
		else {
			verifyNullValue("Pdf Status", actual.getPdfStatus());
		}
	}

	private <T> void verifyList(String reason, List<T> expected, List<T> actual) {
		int expectedCount = expected.size();
		int actualCount = actual.size();
		verify(reason + " Count", expected.size(), actual.size());
		if (expectedCount == actualCount) {
			for (int i=0; i<expected.size(); ++i) {
				T expectedItem = expected.get(i);
				T actualItem = actual.get(i);
				verify(reason + " " + i, expectedItem, actualItem);
			}
		}
	}
	
	private <T> void verify(String reason, T expected, T actual) {
		try {
			assertThat(reason, actual, is(expected));
		} catch (AssertionError e) {
			errorDescriptions.add(e.getMessage());
		}
	}
	
	private <T> boolean verifyNotNullValue(String reason, T actual) {
		try {
			assertThat(reason, actual, notNullValue());
		} catch (AssertionError e) {
			errorDescriptions.add(e.getMessage());
			return false;
		}
		return true;
	}
	private <T> void verifyNullValue(String reason, T actual) {
		try {
			assertThat(reason, actual, nullValue());
		} catch (AssertionError e) {
			errorDescriptions.add(e.getMessage());
		}
	}

	@Override
	public void describeTo(Description description) {
	}
	
	protected void describeMismatchSafely(DisPubMetaData item, Description mismatchDescription) {
		for (String e : errorDescriptions) {
			mismatchDescription.appendText("\n\n" + e);
		}
	}

	@Factory
    public static DissertationMatcher dissertationEqualTo(DisPubMetaData expected) {
        return new DissertationMatcher(expected);
    }

}

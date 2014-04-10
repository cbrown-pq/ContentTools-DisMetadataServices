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
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.AddressType;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.AddressType.AddressUses;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.AddressUseType.SchoolContacts;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.AddressUseType;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.ContactType;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.NameType;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.PersonType;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.Schools.School;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.Schools.School.Addresses;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.Schools.School.SchoolPersons;

public class SchoolMetadataMatcher extends TypeSafeMatcher<School> {
	private final School expected;
	private List<String> errorDescriptions;
	
	public SchoolMetadataMatcher(School expected) {
		this.expected = expected;
	}
	
	@Override
	protected boolean matchesSafely(School actual) {
		resetErrors();
		verify("School Name",expected.getName(),actual.getName());
		verify("School State",expected.getState(),actual.getState());
		verify("School Code",expected.getCode(),actual.getCode());
		verify("School Country",expected.getCountry(),actual.getCountry());
		verify("School Dashboard Classification Code",expected.getDashboardClassificationCode(),actual.getDashboardClassificationCode());
		verify("School Dashboard Classification Source",expected.getDashboardClassificationSource(),actual.getDashboardClassificationSource());
		verify("School Dashboard Eligibility Code",expected.getDashboardEligibilityCode(),actual.getDashboardEligibilityCode());
		verify("School Dashboard Eligibility Description",expected.getDashboardEligibilityDescription(),actual.getDashboardEligibilityDescription());
		verifyAddresses(actual);
		verifyPersonTypes(actual);
		return hasErrors();
	}


	private void resetErrors() {
		errorDescriptions = Lists.newArrayList();
	}

	private boolean hasErrors() {
		return errorDescriptions.isEmpty();
	}

	private void verifyAddresses(School actual) {
		Addresses expectedAddresses = expected.getAddresses(); 
		Addresses actualAddresses = actual.getAddresses();
		if (null != expectedAddresses) {
			if (verifyNotNullValue("Addresses", actualAddresses)) {
				int expectedCount = expectedAddresses.getAddress().size();
				int actualCount = actualAddresses.getAddress().size();
				verify("Count", expectedCount, actualCount);
				if (expectedCount == actualCount) {
					for (int i = 0; i < expectedCount; ++i) {
						AddressType expectedItem = expectedAddresses.getAddress().get(i);
						AddressType actualItem = actualAddresses.getAddress().get(i);
						verify("Address Name " + i, 
								expectedItem.getName(), actualItem.getName());
						verify("Address Line 1" + i, 
								expectedItem.getLine1(), actualItem.getLine1());
						verify("Address Line 2" + i, 
								expectedItem.getLine2(), actualItem.getLine2());
						verify("Address Line 3" + i, 
								expectedItem.getLine3(), actualItem.getLine3());
						verify("Address City" + i, 
								expectedItem.getCity(), actualItem.getCity());
						verify("Address Zip" + i, 
								expectedItem.getZip(), actualItem.getZip());
						verify("Address Effective Date" + i, 
								expectedItem.getEffectiveDate(), actualItem.getEffectiveDate());
						verify("Address PostalCode" + i, 
								expectedItem.getPostalCode(), actualItem.getPostalCode());
						verify("Address StateProvince" + i, 
								expectedItem.getStateProvince(), actualItem.getStateProvince());
						verify("Address Country" + i, 
								expectedItem.getCountry(), actualItem.getCountry());
						verify("Address ActiveFlag " + i, 
								expectedItem.getActiveFlag(), actualItem.getActiveFlag());
						
						verifyAddressUses(expectedItem.getAddressUses(), actualItem.getAddressUses());
					}
				}
			}
			else {
				verifyNullValue("Addresses", actual.getAddresses());
			}
		}
	}

	private void verifyAddressUses(AddressUses expectedAddressUses, 
			AddressUses actualAddressUses ) {
		int expectedAddressUseCount = expectedAddressUses.getAddressUse().size();
		int actualAddressUseCount = actualAddressUses.getAddressUse().size();
		
		if (expectedAddressUseCount == actualAddressUseCount) {
			for (int j = 0; j < expectedAddressUseCount; ++j) {
				AddressUseType expectAddressUseItem = expectedAddressUses.getAddressUse().get(j);
				AddressUseType actualAddressUseItem = actualAddressUses.getAddressUse().get(j);
				
				verify("Address Uses " + j, expectAddressUseItem.getType(), 
						actualAddressUseItem.getType());
				verify("Address Uses " + j, expectAddressUseItem.getEBSAccount(), 
						actualAddressUseItem.getEBSAccount());
				verify("Address Uses " + j, expectAddressUseItem.getDeliveryDate(), 
						actualAddressUseItem.getDeliveryDate());
				verify("Address Uses " + j, expectAddressUseItem.getDateCreated(), 
						actualAddressUseItem.getDateCreated());
				verify("Address Uses " + j, expectAddressUseItem.getDateModified(), 
						actualAddressUseItem.getDateModified());
				
				verifyAddressSchoolContacts(expectAddressUseItem.getSchoolContacts(), 
						actualAddressUseItem.getSchoolContacts());
			}
		}
		
	}
	
	private void verifyAddressSchoolContacts(SchoolContacts expectedSchoolContacts,
			SchoolContacts actualSchoolContacts) {
		int expectedSchoolContactsCount = expectedSchoolContacts.getSchoolContact().size();
		int actualSchoolContactseCount = actualSchoolContacts.getSchoolContact().size();
		if (expectedSchoolContactsCount == actualSchoolContactseCount) {
			for (int k = 0; k < expectedSchoolContactsCount; ++k) {
				ContactType expectedSchoolContact = expectedSchoolContacts.getSchoolContact().get(k);
				ContactType actualSchoolContact = actualSchoolContacts.getSchoolContact().get(k);
				verify("School Contacts " + k, expectedSchoolContact.getType(),
						actualSchoolContact.getType());
				verify("School Contacts " + k, expectedSchoolContact.getName(), 
						actualSchoolContact.getName());
				verify("School Contacts " + k, expectedSchoolContact.getEffectiveDate(), 
						actualSchoolContact.getEffectiveDate());
				verify("School Contacts " + k, expectedSchoolContact.getDateCreated(), 
						actualSchoolContact.getDateCreated());
				verify("School Contacts " + k, expectedSchoolContact.getDateModified(), 
						actualSchoolContact.getDateModified());
			}
		}
	}
	
	private void verifyPersonTypes(School actual) {
		SchoolPersons expectedPersons = expected.getSchoolPersons(); 
		SchoolPersons actualPersons = actual.getSchoolPersons();
		verifyNotNullValue("Persons type: ", expectedPersons);
		if (null != expectedPersons.getSchoolPerson() 
				&& ! expectedPersons.getSchoolPerson().isEmpty()) {
			int expectedCount = expectedPersons.getSchoolPerson().size();
			int actualCount = actualPersons.getSchoolPerson().size();
			verify("Person type Count", expectedCount, actualCount);
			if (expectedCount == actualCount) {
				for (int i = 0; i < expectedCount; ++i) {
					PersonType expectedItem = expectedPersons.getSchoolPerson().get(i);
					PersonType actualItem = actualPersons.getSchoolPerson().get(i);
					verify("Title" + i, expectedItem.getTitle(), actualItem.getTitle());
					verify("Category " + i, expectedItem.getTitleCategory(), 
							actualItem.getTitleCategory());
					verify("Department " + i, expectedItem.getDepartment(), 
							actualItem.getDepartment());
					verify("Status" + i, expectedItem.getStatus(), 
							actualItem.getStatus());
					verify("Email Address" + i, expectedItem.getEmailAddress(), 
							actualItem.getEmailAddress());
					verify("Start Date" + i, expectedItem.getStartDate(), 
							actualItem.getStartDate());
					verify("End Date" + i, expectedItem.getEndDate(), 
							actualItem.getEndDate());
					
					varifyName(expectedItem.getName(), actualItem.getName());
				}
			}
		}
	}
	
	private void varifyName(NameType expectedName, NameType actualName) {
		verify("Name Type, First Name ", expectedName.getFirstName(), actualName.getFirstName());
		verify("Name Type, Middle Name", expectedName.getMiddleName(), actualName.getMiddleName());
		verify("Name Type, Last Name ", expectedName.getLastName(), actualName.getLastName());
		verify("Name Type, Status ",  expectedName.getStatus(), actualName.getStatus());
		verify("Name Type, Status Date ",  expectedName.getStatusDate(), actualName.getStatusDate());
		verify("Name Type, Date Created", expectedName.getDateCreated(), actualName.getDateCreated());
		verify("Name Type, Date Modified" , expectedName.getDateModified(), actualName.getDateModified());
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
	
	protected void describeMismatchSafely(School item, Description mismatchDescription) {
		for (String e : errorDescriptions) {
			mismatchDescription.appendText("\n\n" + e);
		}
	}

	@Factory
    public static SchoolMetadataMatcher schoolEqualTo(School expected) {
        return new SchoolMetadataMatcher(expected);
    }
	
}

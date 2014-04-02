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
import com.proquest.mtg.dismetadataservice.metadata.school.Address;
import com.proquest.mtg.dismetadataservice.metadata.school.PersonType;
import com.proquest.mtg.dismetadataservice.metadata.school.School;

public class SchoolMetadataMatcher extends TypeSafeMatcher<School> {
	private final School expected;
	private List<String> errorDescriptions;
	
	public SchoolMetadataMatcher(School expected) {
		this.expected = expected;
	}
	
	@Override
	protected boolean matchesSafely(School actual) {
		resetErrors();
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
		if (null != expected.getAddresses() && !expected.getAddresses().isEmpty()) {
			if (verifyNotNullValue("Addresses", actual.getAddresses())) {
				int expectedCount = expected.getAddresses().size();
				int actualCount = actual.getAddresses().size();
				verify("Count", expectedCount, actualCount);
				if (expectedCount == actualCount) {
					for (int i=0; i<expectedCount; ++i) {
						Address expectedItem = expected.getAddresses().get(i);
						Address actualItem = actual.getAddresses().get(i);
						verify("Address Id " + i, expectedItem.getAddressId(), actualItem.getAddressId());
						verify("Address Name " + i, expectedItem.getAddressName(), actualItem.getAddressName());
						verify("Address ActiveFlag " + i, expectedItem.getActiveFlag(), actualItem.getActiveFlag());
						verify("Address City" + i, expectedItem.getCity(), actualItem.getCity());
						verify("Address Country" + i, expectedItem.getCountry(), actualItem.getCountry());
						verify("Address Effective Date" + i, expectedItem.getEffectiveDate(), actualItem.getEffectiveDate());
						verify("Address Fourdigit Zip" + i, expectedItem.getFourdigitzip(), actualItem.getFourdigitzip());
						verify("Address Line 1" + i, expectedItem.getLine1(), actualItem.getLine1());
						verify("Address Line 2" + i, expectedItem.getLine2(), actualItem.getLine2());
						verify("Address Line 3" + i, expectedItem.getLine3(), actualItem.getLine3());
						verify("Address PostalCode" + i, expectedItem.getPostalCode(), actualItem.getPostalCode());
						verify("Address StateProvince" + i, expectedItem.getStateProvince(), actualItem.getStateProvince());
						verify("Address Zip" + i, expectedItem.getZip(), actualItem.getZip());
						for (int j=0; j<expectedItem.getAddressUses().size(); ++j) {
							verify("Address Uses " + j, expectedItem.getAddressUses().get(j).getDateCreated(), actualItem.getAddressUses().get(j).getDateCreated());
							verify("Address Uses " + j, expectedItem.getAddressUses().get(j).getDateModified(), actualItem.getAddressUses().get(j).getDateModified());
							verify("Address Uses " + j, expectedItem.getAddressUses().get(j).getDeliveryDate(), actualItem.getAddressUses().get(j).getDeliveryDate());
							verify("Address Uses " + j, expectedItem.getAddressUses().get(j).geteBSAccount(), actualItem.getAddressUses().get(j).geteBSAccount());
							verify("Address Uses " + j, expectedItem.getAddressUses().get(j).getType(), actualItem.getAddressUses().get(j).getType());
							if(null != expectedItem.getAddressUses().get(j).getSchoolContacts() && !expectedItem.getAddressUses().get(j).getSchoolContacts().isEmpty())
							{
							for (int k=0; k<expectedItem.getAddressUses().get(j).getSchoolContacts().size(); ++k) {
								verify("School Contacts " + k, expectedItem.getAddressUses().get(j).getSchoolContacts().get(k).getDateCreated(), actualItem.getAddressUses().get(j).getSchoolContacts().get(k).getDateCreated());
								verify("School Contacts " + k, expectedItem.getAddressUses().get(j).getSchoolContacts().get(k).getDateModified(), actualItem.getAddressUses().get(j).getSchoolContacts().get(k).getDateModified());
								verify("School Contacts " + k, expectedItem.getAddressUses().get(j).getSchoolContacts().get(k).getEffectiveDate(), actualItem.getAddressUses().get(j).getSchoolContacts().get(k).getEffectiveDate());
								verify("School Contacts " + k, expectedItem.getAddressUses().get(j).getSchoolContacts().get(k).getName(), actualItem.getAddressUses().get(j).getSchoolContacts().get(k).getName());
								verify("School Contacts " + k, expectedItem.getAddressUses().get(j).getSchoolContacts().get(k).getType(), actualItem.getAddressUses().get(j).getSchoolContacts().get(k).getType());
							}
							}
						}
					}
				}
			}
			else {
				verifyNullValue("Addresses", actual.getAddresses());
			}
		}
	}

	private void verifyPersonTypes(School actual) {
		if (null != expected.getPersonTypes() && !expected.getPersonTypes().isEmpty()) {
			int expectedCount = expected.getPersonTypes().size();
			int actualCount = actual.getPersonTypes().size();
			verify("Person type Count", expectedCount, actualCount);
			if (expectedCount == actualCount) {
				for (int i=0; i<expectedCount; ++i) {
						PersonType expectedItem = expected.getPersonTypes().get(i);
						PersonType actualItem = actual.getPersonTypes().get(i);
						verify("Category " + i, expectedItem.getCategory(), actualItem.getCategory());
						verify("Department " + i, expectedItem.getDepartment(), actualItem.getDepartment());
						verify("Email " + i, expectedItem.getEmail(), actualItem.getEmail());
						verify("Name Id" + i, expectedItem.getNameId(), actualItem.getNameId());
						verify("Name Type" + i, expectedItem.getNameType().getDateCreated(), actualItem.getNameType().getDateCreated());
						verify("Name Type" + i, expectedItem.getNameType().getDateModified(), actualItem.getNameType().getDateModified());
						verify("Name Type" + i, expectedItem.getNameType().getFirstName(), actualItem.getNameType().getFirstName());
						verify("Name Type" + i, expectedItem.getNameType().getLastName(), actualItem.getNameType().getLastName());
						verify("Name Type" + i, expectedItem.getNameType().getMiddleName(), actualItem.getNameType().getMiddleName());
						verify("Phone Number" + i, expectedItem.getPhoneNumber(), actualItem.getPhoneNumber());
						verify("Start Date" + i, expectedItem.getStartDate(), actualItem.getStartDate());
						verify("End Date" + i, expectedItem.getEndDate(), actualItem.getEndDate());
						verify("Status" + i, expectedItem.getStatus(), actualItem.getStatus());
						verify("Title" + i, expectedItem.getTitle(), actualItem.getTitle());
					}
				}
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

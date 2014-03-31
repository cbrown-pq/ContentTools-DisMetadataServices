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
import com.proquest.mtg.dismetadataservice.exodus.DisSchoolMetaData;

public class SchoolMetadataMatcher extends TypeSafeMatcher<DisSchoolMetaData> {
	private final DisSchoolMetaData expected;
	private List<String> errorDescriptions;
	
	public SchoolMetadataMatcher(DisSchoolMetaData expected) {
		this.expected = expected;
	}
	
	@Override
	protected boolean matchesSafely(DisSchoolMetaData actual) {
		resetErrors();
		return hasErrors();
		}

	private void resetErrors() {
		errorDescriptions = Lists.newArrayList();
	}

	private boolean hasErrors() {
		return errorDescriptions.isEmpty();
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
	
	protected void describeMismatchSafely(DisSchoolMetaData item, Description mismatchDescription) {
		for (String e : errorDescriptions) {
			mismatchDescription.appendText("\n\n" + e);
		}
	}

	@Factory
    public static SchoolMetadataMatcher schoolEqualTo(DisSchoolMetaData expected) {
        return new SchoolMetadataMatcher(expected);
    }
	
}

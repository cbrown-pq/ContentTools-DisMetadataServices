package com.proquest.mtg.dismetadataservice.marc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class UnknownTags_Tests {
	@Test
	public void knownTags() throws Exception {
		List<String> tags = Lists.newArrayList("001", "020", "500");
		for (String tag : tags) {
			assertThat("Tag: " + tag, MarcTags.kUnknownTagPredicate.apply(tag), is(false));
		}
	}
	
	@Test
	public void uknownTags() throws Exception {
		List<String> tags = Lists.newArrayList("000", "123", "555");
		for (String tag : tags) {
			assertThat("Tag: " + tag, MarcTags.kUnknownTagPredicate.apply(tag), is(true));
		}
	}
	
	@Test
	public void allTags_Areknown() throws Exception {
		for (String tag : MarcTags.kAllTags) {
			assertThat("Tag: " + tag, MarcTags.kUnknownTagPredicate.apply(tag), is(false));
		}
	}

}

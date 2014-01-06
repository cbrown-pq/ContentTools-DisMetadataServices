package com.proquest.mtg.dismetadataservice.metadata;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class SGMLEntitySubstitution_Tests {
	
	@Test
	public void applyAll_withEmptyString_DoesNothing() {
		String source = "";
		String expected = "";
		assertThat(SGMLEntitySubstitution.applyAllTo(source), is(expected));
	}
	
	@Test
	public void applyAll_withoutEntities_DoesNothing() {
		String source = "Catch Phrase";
		String expected = "Catch Phrase";
		assertThat(SGMLEntitySubstitution.applyAllTo(source), is(expected));
	}
	
	@Test
	public void eAcuteTest() throws Exception {
		String source = "Les aspects &eacute;thiques de l'utilisation d";
		String expected = "Les aspects ethiques de l'utilisation d";
		String actual = SGMLEntitySubstitution.applyAllTo(source);
		assertThat(actual, is(expected));
	}

}

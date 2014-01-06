package com.proquest.mtg.dismetadataservice.metadata;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class SGMLAngluarBracesEntitySubstitution_Tests {
	@Test
	public void applyAll_withEmptyString_DoesNothing() {
		String source = "";
		String expected = "";
		assertThat(SGMLAngluarBracesEntitySubstitution.applyAllTo(source), is(expected));
	}
	
	@Test
	public void applyAll_withoutEntities_DoesNothing() {
		String source = "Catch Phrase";
		String expected = "Catch Phrase";
		assertThat(SGMLAngluarBracesEntitySubstitution.applyAllTo(source), is(expected));
	}
	
	@Test
	public void lessGreaterThan() throws Exception {
		String source = "This is a &lt;p&gt;&lt;b&gt;Bold Paragraph&lt;/b&gt;&lt;/p&gt;";
		String expected = "This is a <p><b>Bold Paragraph</b></p>";
		String actual = SGMLAngluarBracesEntitySubstitution.applyAllTo(source);
		assertThat(actual, is(expected));
	}

}

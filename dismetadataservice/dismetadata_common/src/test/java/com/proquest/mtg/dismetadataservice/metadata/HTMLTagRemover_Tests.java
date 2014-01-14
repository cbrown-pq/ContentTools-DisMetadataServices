package com.proquest.mtg.dismetadataservice.metadata;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;


public class HTMLTagRemover_Tests {
	
HTMLTagRemover target;
	
	@Before
	public void setup() {
		target = new HTMLTagRemover();
	}
	
	@Test
	public void hasDefault_MaxTagLength() {
		assertThat(target.getMaxagLength(), is(HTMLTagRemover.kDefaultMaxTagLength));
	}
	
	@Test
	public void canSet_MaxTagLength() throws Exception {
		int value = 25;
		target.setMaxTagLength(value);
		assertThat(target.getMaxagLength(), is(value));
	}
	
	@Test
	public void withNullString() {
		assertThat(target.applyTo(null), nullValue());
	}
	
	@Test
	public void withEmptyString() {
		String emptyString = "";
		assertThat(target.applyTo(emptyString), is(emptyString));
	}
	
	@Test
	public void withSingleHTMLTag() {
		String source = "foo <b> bar";
		String expected = "foo  bar"; 
		assertThat(target.applyTo(source), is(expected));
	}
	
	@Test
	public void withMultipleHTMLTags() {
		String source = "foo <b> bar <i> car";
		String expected = "foo  bar  car"; 
		assertThat(target.applyTo(source), is(expected));
	}
		
	@Test
	public void withStartAndCloseHTMLTags() {
		String source = "foo <b> bar <i> car </i>";
		String expected = "foo  bar  car "; 
		assertThat(target.applyTo(source), is(expected));
	}
	
	@Test
	public void withHTMLTagAttribute() {
		String source = "foo <form name=\"abc\"> bar <i> car </i></form>";
		String expected = "foo  bar  car "; 
		assertThat(target.applyTo(source), is(expected));
	}
	
	@Test
	public void withConsecutiveHTMLTags() {
		String source = "foo <form name=\"abc\"><b> bar <i> car </i></form>";
		String expected = "foo  bar  car "; 
		assertThat(target.applyTo(source), is(expected));
	}
	
	@Test
	public void withHTMLTagLen_More_Than_MaxLength() {
		String source = "foo <form name=\"abc1234\"> bar <i> car </i></form>";
		String expected = "foo <form name=\"abc1234\"> bar  car ";
		target.setMaxTagLength(10);
		assertThat(target.applyTo(source), is(expected));
	}
	
	@Test
	public void withNonHTMLTag_1() {
		String source = "foo 2 < 5 < 10 > 3 bar <i> car";
		String expected = "foo 2 < 5  3 bar  car"; 
		assertThat(target.applyTo(source), is(expected));
	}
	
	@Test
	public void withNonHTMLTag_2() {
		String source = "foo bar <i> car 2 > 3";
		String expected = "foo bar  car 2 > 3"; 
		assertThat(target.applyTo(source), is(expected));
	}
	
	@Test
	public void withOnlySGMLEntity() throws Exception {
		String source = "This is a &lt;p&gt;&lt;b&gt;Bold Paragraph&lt;/b&gt;&lt;/p&gt;";
		String expected = "This is a Bold Paragraph";
		String actual = target.applyTo(source);
		assertThat(actual, is(expected));
	}
	
	@Test
	public void withSGMLEntityAndHTMLTag() throws Exception {
		String source = "This <i>is a &lt;p&gt;&lt;b&gt;Bold Paragraph&lt;/b&gt;&lt;/p&gt;</i>";
		String expected = "This is a Bold Paragraph";
		String actual = target.applyTo(source);
		assertThat(actual, is(expected));
	}


}

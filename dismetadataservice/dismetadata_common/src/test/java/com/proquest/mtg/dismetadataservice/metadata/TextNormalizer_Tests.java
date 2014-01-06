package com.proquest.mtg.dismetadataservice.metadata;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class TextNormalizer_Tests {
	
TextNormalizer target;
	
	@Before
	public void setUp() throws Exception {
		target = new TextNormalizer();
	}
	
	@Test
	public void withNull_ReturnsNull() throws Exception {
		assertThat(target.applyTo(null), nullValue());
	}
	
	@Test
	public void withEmptyString() throws Exception {
		assertThat(target.applyTo(""), is(""));
	}
	
	@Test
	public void withPlainText() throws Exception {
		String source = "This is my research";
		String expected = source;
		
		assertThat(target.applyTo(source), is(expected));
	}
	
	@Test
	public void trimsWhiteSpace() throws Exception {
		String source = " \n \t This is my research ";
		String expected = "This is my research";
		
		assertThat(target.applyTo(source), is(expected));
	}
	
	@Test
	public void withCarrots() throws Exception {
		String source = "This.^ Is my^Research?^";
		String expected = "This.<p> Is my<p>Research?<p>";
		
		assertThat(target.applyTo(source), is(expected));
	}
	
	@Test
	public void withLineSeparators() throws Exception {
		String source = "This.\nIs my\nResearch!\n";
		String expected = "This.<p>Is my<p>Research!";
		
		assertThat(target.applyTo(source), is(expected));
	}
	
	@Test
	public void withLessThanAndGreaterThanSgmlEntityTags() throws Exception {
		String source = "&lt;p&gt;This.&lt;/p&gt;&lt;p&gt;Is my&lt;p&gt;Research!&lt;/p&gt;";
		String expected = "<p>This.</p><p>Is my<p>Research!</p>";
		
		assertThat(target.applyTo(source), is(expected));
	}
	
	@Test
	public void withAdeptTag() throws Exception {
		String source = "<?Pub Inc> This is my research";
		String expected = "This is my research";
		
		assertThat(target.applyTo(source), is(expected));
	}
	
	@Test
	public void withCDataTag() throws Exception {
		String source = "<![CDATA[This is my research ]]>";
		String expected = "This is my research";
		
		assertThat(target.applyTo(source), is(expected));
	}

}

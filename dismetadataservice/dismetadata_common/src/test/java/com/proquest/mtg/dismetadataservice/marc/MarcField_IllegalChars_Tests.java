package com.proquest.mtg.dismetadataservice.marc;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.CharMatcher;

public class MarcField_IllegalChars_Tests extends EasyMockSupport {
	String tag;
	String fieldData;
	MarcField field;
	CharMatcher mockCharMatcher;
	
	@Before
	public void setUp() throws Exception {
		tag = MarcTags.kAbstract;
		fieldData = "My Abstract";
		field = new MarcField(tag, fieldData);
		
		mockCharMatcher = createMock(CharMatcher.class);
	}
	
	@Test
	public void whenFieldData_Matches_IsLegal() throws Exception {
		
		expect(mockCharMatcher.matchesAllOf(field.getDataWithTerminator())).andReturn(true);
		
		replayAll();
		
		assertThat(field.hasIllegalChars(mockCharMatcher), is(false));
		
		verifyAll();
	}
	
	@Test
	public void whenFieldData_DoesNotMatch_IsIllegal() throws Exception {
		
		expect(mockCharMatcher.matchesAllOf(field.getDataWithTerminator())).andReturn(false);
		
		replayAll();
		
		assertThat(field.hasIllegalChars(mockCharMatcher), is(true));
		
		verifyAll();
	}

}

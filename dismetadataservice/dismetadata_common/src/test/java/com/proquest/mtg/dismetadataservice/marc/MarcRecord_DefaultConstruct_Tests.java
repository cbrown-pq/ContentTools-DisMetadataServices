package com.proquest.mtg.dismetadataservice.marc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class MarcRecord_DefaultConstruct_Tests {
	
MarcRecord target;
	
	@Before
	public void setUp() throws Exception {
		char type = 'a';
		target = new MarcRecord(type);
	}
	
	@Test
	public void hasCorrect_Status() throws Exception {
		assertThat(target.getStatus(), is('n'));
	}
	
	@Test
	public void hasCorrect_Type() throws Exception {
		assertThat(target.getType(), is('a'));
	}
	
	@Test
	public void hasCorrect_Level() throws Exception {
		assertThat(target.getLevel(), is('m'));
	}
	
	@Test
	public void fieldsAreEmpty() throws Exception {
		assertThat(target.getFieldCount(), is(0));
	}

}

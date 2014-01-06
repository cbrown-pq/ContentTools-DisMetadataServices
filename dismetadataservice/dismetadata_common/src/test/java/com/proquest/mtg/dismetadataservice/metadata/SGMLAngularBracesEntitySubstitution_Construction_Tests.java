package com.proquest.mtg.dismetadataservice.metadata;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class SGMLAngularBracesEntitySubstitution_Construction_Tests {
	
	SGMLAngluarBracesEntitySubstitution target;
	String entity;
	String substitution;
	
	@Before
	public void setUp(){
		entity = "&aacute;";
		substitution = "a";
		target = new SGMLAngluarBracesEntitySubstitution(entity, substitution);
	}
	
	@Test
	public void hasCorrect_Entity(){
		assertThat(target.getEntity(), is(entity));
	}
	
	@Test
	public void hasCorrect_Substitution(){
		assertThat(target.getSubstitution(), is(substitution));
	}
	
	@Test
	public void applyTo_ReplacesEntitiesInSourceString() {
		String source = "Foo &aacute; bar";
		String expected = "Foo a bar";
		assertThat(target.applyTo(source), is(expected));
	}

}

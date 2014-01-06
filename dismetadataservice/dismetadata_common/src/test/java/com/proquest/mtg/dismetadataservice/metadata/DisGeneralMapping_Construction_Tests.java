package com.proquest.mtg.dismetadataservice.metadata;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class DisGeneralMapping_Construction_Tests {
	
	String degreeValue1;
	String degreeValue2;
	
	DisGeneralMapping target;
	
	@Before
	public void setUp(){
		degreeValue1 = "%Ae%";
		degreeValue2 = "2";
		target = new DisGeneralMapping(degreeValue1, degreeValue2);
	}
	
	@Test
	public void hasCorrect_DegreeValue1() {
		assertThat(target.getDegreevalue1(), is(degreeValue1));
	}
	
	@Test
	public void hasCorrect_DegreeValue2() {
		assertThat(target.getDegreeValue2(), is(degreeValue2));
	}
	

}

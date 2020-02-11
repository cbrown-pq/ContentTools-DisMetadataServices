package com.proquest.mtg.dismetadataservice.datasource;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;


public class AddressMetaData_Construct_Tests {
	
	String name;
	String line1;
	String line2;
	String line3;
	String city;
	String zip;
	String postalCode;
	String stateProvince;
	String country;
	
	public AddressMetaData target;
	
	@Before
	public void setup() {
		target = new AddressMetaData();
		target.setName("TestName");
		target.setLine1("TestLine1");
		target.setLine2("TestLine2");
		target.setLine3("TestLine3");
		target.setCity("TestCity");
		target.setFourDigitzip("4digit");
		target.setFiveDigitzip("5digit");
		target.setPostalCode("TestPostalCode");
		target.setStateProvince("TestStateProvince");
		target.setCountry("TestCountry");
	}
	
	@Test
	public void hasCorrectFields() {
		assertThat(target.getName(), is("TestName"));
		assertThat(target.getLine1(), is("TestLine1"));
		assertThat(target.getLine2(), is("TestLine2"));
		assertThat(target.getLine3(), is("TestLine3"));
		assertThat(target.getCity(), is("TestCity"));
		assertThat(target.getFourDigitZip(),is("4digit"));
		assertThat(target.getFiveDigitZip(),is("5digit"));
		assertThat(target.getPostalCode(), is("TestPostalCode"));
		assertThat(target.getStateProvince(), is("TestStateProvince"));
		assertThat(target.getCountry(), is("TestCountry"));
		
	}
	 

}

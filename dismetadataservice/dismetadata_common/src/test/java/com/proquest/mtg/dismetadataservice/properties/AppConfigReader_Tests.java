package com.proquest.mtg.dismetadataservice.properties;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class AppConfigReader_Tests {
	
	String propertyFile = "test.properties";
	Properties props;
	AppConfigReader target;
	
	@Before
	public void setUp() {
		
		initProperties();
		
		target = new AppConfigReader(propertyFile);
		
	}
	
	private void initProperties() {
		props = new Properties();
		props.setProperty("Key1", "Value1");
		props.setProperty("Key2", "Value2");
		props.setProperty("Key3", "Value3");
	}
	
	@Test
	public void has_Correct_propertyFile() {
		assertThat(target.getPropertyFileName(), is(propertyFile));
	}
	
	@Test
	public void hasCorrectProperties() throws Exception {
		Properties copy = target.getAllProperties();
		assertThat(copy, is(props));
	}

}

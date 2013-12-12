package com.proquest.mtg.dismetadataservice.guice;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.proquest.mtg.dismetadataservice.properties.DisMetadataProperties;
import com.proquest.mtg.dismetadataservice.properties.DisMetadataProperties_Tests;
import com.proquest.mtg.dismetadataservice.properties.IAppConfigReader;

public class DisMetadataServiceGuiceModule_Tests {
	
	DisMetadataProperties disMetadataProps;
	DisMetadataServiceGuiceModule guiceModule;
	Injector injector;
	
	@Before
	public void setUp() throws Exception {
		Properties props = DisMetadataProperties_Tests.makePropertyMapForTesting();
		resetInjector(props);
	}

	private void resetInjector(Properties props) throws Exception {
		
		
		disMetadataProps = new DisMetadataProperties(props);
		
		String unitTestPropertyFile = "dismetadata.local.properties";
		guiceModule = new DisMetadataServiceGuiceModule(unitTestPropertyFile);
		injector = Guice.createInjector(guiceModule);
	}
	
	@Test
	public void hasCorrect_AppConfigReader() throws Exception {
		IAppConfigReader object = injector.getInstance(IAppConfigReader.class);
		assertThat(object, notNullValue());
		assertThat(object, instanceOf(IAppConfigReader.class));
	}
	
	
	@Test
	public void hasCorrect_DisMetadataProperties() throws Exception {
		DisMetadataProperties object = injector.getInstance(DisMetadataProperties.class);
		assertThat(object, notNullValue());
		assertThat(object, instanceOf(DisMetadataProperties.class));
	}

}

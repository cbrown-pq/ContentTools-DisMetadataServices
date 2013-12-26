package com.proquest.mtg.dismetadataservice.rest.helper;

import java.io.IOException;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.proquest.mtg.dismetadataservice.format.MetaDataFormatFactory;

public class MockServiceTestHelper extends ServiceTestHelperBase {
	
	public MockServiceTestHelper(final MetaDataFormatFactory metaDataFormatFactory) 
			throws IllegalArgumentException, IOException {
		
		Injector injector = Guice.createInjector( new AbstractModule() {
									 @Override
									 protected void configure() {
										 bind(MetaDataFormatFactory.class).toInstance(metaDataFormatFactory);
									 }
		 	 					});
		
		createHTTPServer(injector);
		
	}
	
}

package com.proquest.mtg.dismetadataservice.rest.helper;

import java.io.IOException;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.proquest.mtg.dismetadataservice.exodus.PubMetaDataProvider;

public class MockServiceTestHelper extends ServiceTestHelperBase {
	
	public MockServiceTestHelper(final PubMetaDataProvider metadataProvider) 
			throws IllegalArgumentException, IOException {
		
		Injector injector = Guice.createInjector( new AbstractModule() {
									 @Override
									 protected void configure() {
										 bind( PubMetaDataProvider.class ).toInstance(metadataProvider);
									 }
		 	 					});
		
		createHTTPServer(injector);
		
	}
	
}

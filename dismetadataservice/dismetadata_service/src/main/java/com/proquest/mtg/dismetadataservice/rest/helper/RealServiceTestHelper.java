package com.proquest.mtg.dismetadataservice.rest.helper;

import java.io.IOException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.proquest.mtg.dismetadataservice.guice.DisMetadataServiceGuiceModule;

public class RealServiceTestHelper extends ServiceTestHelperBase {
	
	static final String kPropertyFileName = "dismetadata.local.properties";
	
	DisMetadataServiceGuiceModule disMetadataGuiceModule;
	
	public RealServiceTestHelper() throws IllegalArgumentException, IOException {
		disMetadataGuiceModule = new DisMetadataServiceGuiceModule(kPropertyFileName);
	 	Injector injector = Guice.createInjector(disMetadataGuiceModule );
	 	createHTTPServer(injector);
	}
	
	public void stopHTTPServer() {
		disMetadataGuiceModule.shutdown();
		super.stopHTTPServer();
 }

}

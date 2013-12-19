package com.proquest.mtg.dismetadataservice.rest.helper;

import java.io.IOException;

import com.google.inject.Injector;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.core.spi.component.ioc.IoCComponentProviderFactory;
import com.sun.jersey.guice.spi.container.GuiceComponentProviderFactory;
import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public abstract class ServiceTestHelperBase {
	
	static final String kBaseURI = "http://localhost:9999/dismetadata_service/";
	HttpServer server;
	
	
	public void createHTTPServer(Injector injector) throws IllegalArgumentException, IOException {
		ResourceConfig rc = new PackagesResourceConfig("com.proquest.mtg.dismetadataservice.rest");
		IoCComponentProviderFactory ioc = new GuiceComponentProviderFactory( rc, injector );
		server = HttpServerFactory.create(kBaseURI, rc, ioc);
		
	}
	
	public void startHTTPServer() {
		    server.start();
	}
	
	public void stopHTTPServer() {
           server.stop(0);
    }
	

}

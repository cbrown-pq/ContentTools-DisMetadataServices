package com.proquest.mtg.dismetadataservice.rest.helper;

import java.io.IOException;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class MetadataServiceProviderTestHelper {

	static final String kBaseURI = "http://localhost:9999/dismetadata_service/";
	
	HttpServer server;
	
	public MetadataServiceProviderTestHelper() throws IllegalArgumentException, IOException {
		ResourceConfig rc = new PackagesResourceConfig("com.proquest.mtg.dismetadataservice.rest");
		server = HttpServerFactory.create(kBaseURI, rc);
	}
	
	public void startHTTPServer() {
		    server.start();
	}
	
	public void stopHTTPServer() {
           server.stop(0);
    }
}

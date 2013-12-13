package com.proquest.mtg.dismetadataservice.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.rest.helper.MetadataServiceProviderTestHelper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class DisMetadataServiceProvider_Tests {
	
	static String kBASE_URI = "http://localhost:9999/dismetadata_service/";
	
	MetadataServiceProviderTestHelper serviceTestHelper;
	WebResource service;
 
    @Before
    public void setUp() throws Exception {
    	serviceTestHelper = new MetadataServiceProviderTestHelper();
    	serviceTestHelper.startHTTPServer();
    	Client client = Client.create( new DefaultClientConfig() );
    	service = client.resource( kBASE_URI );
    }
 
    @After
    public void tearDown() throws Exception {
    	serviceTestHelper.stopHTTPServer();
    }
 
    @Test
    public void testResponseOK() {
    	ClientResponse resp = service.path("dismetadata").path("test/123").get(ClientResponse.class);
        String responseMsg = resp.getEntity(String.class);
        
        assertEquals("test-123", responseMsg);
        assertEquals( 200, resp.getStatus());
    }

}

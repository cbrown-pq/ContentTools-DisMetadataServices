package com.proquest.mtg.dismetadataservice.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.rest.helper.RealServiceTestHelper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class MStarXmlMetadataServiceProvider_Tests {

	static String kBASE_URI = "http://localhost:9999/dismetadata_service/";

	RealServiceTestHelper serviceTestHelper;
	WebResource service;

	@Before
	public void setUp() throws Exception {
		serviceTestHelper = new RealServiceTestHelper();
		serviceTestHelper.startHTTPServer();
		Client client = Client.create(new DefaultClientConfig());
		service = client.resource(kBASE_URI);
	}

	@After
	public void tearDown() throws Exception {
		serviceTestHelper.stopHTTPServer();
	}

	@Test
	public void whenValidPubExist() {
		ClientResponse resp = service.path("mstar")
				.path("dissinfo/U598835").get(ClientResponse.class);
		assertThat(resp.getStatus(), is(200));		
	}
	
	@Test
	public void whenInvalidPub() {
		ClientResponse resp = service.path("mstar")
				.path("dissinfo/U59").get(ClientResponse.class);
		assertThat(resp.getStatus(), is(500));		
	}
	
	@Test
	public void withMissingPubNums() {
		ClientResponse resp = service.path("mstar")
				.path("dissinfo").get(ClientResponse.class);
		assertThat(resp.getStatus(), is(404));		
	}	

}
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

public class ExternalUrlXmlServiceProvider_Tests {

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
	public void whenUrlExist() {
		ClientResponse resp = service.path("externalurl")
				.path("getList").get(ClientResponse.class);
		assertThat(resp.getStatus(), is(200));		
	}
	
	@Test
	public void withInvalidUrl() {
		ClientResponse resp = service.path("externalurl")
				.path("badurl").get(ClientResponse.class);
		assertThat(resp.getStatus(), is(404));		
	}	

}
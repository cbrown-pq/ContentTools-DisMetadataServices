package com.proquest.mtg.dismetadataservice.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

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
		Client client = Client.create(new DefaultClientConfig());
		service = client.resource(kBASE_URI);
	}

	@After
	public void tearDown() throws Exception {
		serviceTestHelper.stopHTTPServer();
	}

	@Test
	public void testResponseOK() {
		ClientResponse resp = service.path("dispubmetadata")
				.path("123456/test").get(ClientResponse.class);
		String responseMsg = resp.getEntity(String.class);

		assertThat(responseMsg, not(nullValue()));
		assertThat(resp.getStatus(), is(200));
	}

}

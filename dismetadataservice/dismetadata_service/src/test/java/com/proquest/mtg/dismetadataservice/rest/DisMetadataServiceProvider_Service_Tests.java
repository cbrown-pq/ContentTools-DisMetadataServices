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

public class DisMetadataServiceProvider_Service_Tests {

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
	public void whenPubDoesNotExist() {
		ClientResponse resp = service.path("metadata")
				.path("testpub/USMARC").get(ClientResponse.class);
		assertThat(resp.getStatus(), is(404));		
	}
	
	@Test
	public void withInvalidFormatType() {
		ClientResponse resp = service.path("metadata")
				.path("12345/test").get(ClientResponse.class);
		assertThat(resp.getStatus(), is(500));		
	}
	
//	@Test
//	public void whenPubExist() {
//		ClientResponse resp = service.path("dispubmetadata")
//				.path("3062346/test").get(ClientResponse.class);
//		String responseMsg = resp.getEntity(String.class);
//
//		assertThat(resp.getStatus(), is(200));
//		assertThat( responseMsg, is("Pub Number: 3062346\nISBN: 978-0-493-78200-3") );
//	}
	

	

}
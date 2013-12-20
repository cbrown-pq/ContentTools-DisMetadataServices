package com.proquest.mtg.dismetadataservice.rest;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.PubMetaDataProvider;
import com.proquest.mtg.dismetadataservice.rest.helper.MockServiceTestHelper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class DisMetadataServiceProvider_Tests extends EasyMockSupport {

	static String kBASE_URI = "http://localhost:9999/dismetadata_service/";
	PubMetaDataProvider metadataProvider;
	DisPubMetaData disPubMetadata;
	MockServiceTestHelper serviceTestHelper;
	WebResource service;
	String result;
	String out;

	@Before
	public void setUp() throws Exception {
		result = "Pub Number: FakePub" + "\n" 
				  + "ISBN: FakeISBN";

		metadataProvider = createMock(PubMetaDataProvider.class);
		disPubMetadata = createMock(DisPubMetaData.class);

		serviceTestHelper = new MockServiceTestHelper(metadataProvider);
		serviceTestHelper.startHTTPServer();
		Client client = Client.create(new DefaultClientConfig());
		service = client.resource(kBASE_URI);
		expect(metadataProvider.getPubMetaDataFor("7011730")).andReturn(
				disPubMetadata);
		expect(disPubMetadata.getPubNumber()).andReturn("FakePub");
		expect(disPubMetadata.getISBN()).andReturn("FakeISBN");

		replayAll();

	}

	@After
	public void tearDown() throws Exception {
		verifyAll();
		serviceTestHelper.stopHTTPServer();
	}

	@Test
	public void acceptance() throws Exception {
		ClientResponse resp = service.path("dispubmetadata")
				.path("7011730/MARC").get(ClientResponse.class);
		String responseMsg = resp.getEntity(String.class);

		assertThat(responseMsg, not(nullValue()));
		assertThat(responseMsg, is(result));
		assertThat(resp.getStatus(), is(200));
	}

}

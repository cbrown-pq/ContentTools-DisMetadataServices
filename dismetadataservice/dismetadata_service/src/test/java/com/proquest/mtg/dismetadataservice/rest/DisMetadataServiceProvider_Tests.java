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

import com.proquest.mtg.dismetadataservice.format.IMetaDataFormats;
import com.proquest.mtg.dismetadataservice.format.MetaDataFormatFactory;
import com.proquest.mtg.dismetadataservice.rest.helper.MockServiceTestHelper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class DisMetadataServiceProvider_Tests extends EasyMockSupport {

	static String kBASE_URI = "http://localhost:9999/dismetadata_service/";
	MetaDataFormatFactory metaDataFormatFactory;
	SchoolMetadataServiceProvider schoolMetadata;
	SubjectsMetadataServiceProvider subjectsMetadata;
	LocMetaDataServiceProvider locMetadata;
	PdfDownloadServiceProvider pdfDownload;
	LocReportServiceProvider locReport;
	IMetaDataFormats metaDataFormats;
	MockServiceTestHelper serviceTestHelper;
	WebResource service;
	String result;
	String out;

	@Before
	public void setUp() throws Exception {
		result = "MARC Test output";

		metaDataFormatFactory = createMock(MetaDataFormatFactory.class);
		metaDataFormats = createMock(IMetaDataFormats.class);
		
		schoolMetadata = createMock(SchoolMetadataServiceProvider.class);
		subjectsMetadata = createMock(SubjectsMetadataServiceProvider.class);
		locMetadata = createMock(LocMetaDataServiceProvider.class);
		locReport = createMock(LocReportServiceProvider.class);
		pdfDownload = createMock(PdfDownloadServiceProvider.class);

		serviceTestHelper = new MockServiceTestHelper(metaDataFormatFactory, 
				schoolMetadata, subjectsMetadata, locMetadata, locReport, pdfDownload);
		serviceTestHelper.startHTTPServer();
		Client client = Client.create(new DefaultClientConfig());
		service = client.resource(kBASE_URI);
		expect(metaDataFormatFactory.getFor("MARC")).andReturn(metaDataFormats);
		expect(metaDataFormats.makeFor("TESTPUB")).andReturn("MARC Test output");
		replayAll();

	}

	@After
	public void tearDown() throws Exception {
		verifyAll();
		serviceTestHelper.stopHTTPServer();
	}

	@Test
	public void acceptance() throws Exception {
		ClientResponse resp = service.path("metadata")
				.path("TESTPUB/MARC").get(ClientResponse.class);
		String responseMsg = resp.getEntity(String.class);

		assertThat(responseMsg, not(nullValue()));
		assertThat(responseMsg, is(result));
		assertThat(resp.getStatus(), is(200));
	}

}

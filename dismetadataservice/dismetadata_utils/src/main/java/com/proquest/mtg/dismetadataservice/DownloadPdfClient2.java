package com.proquest.mtg.dismetadataservice;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.io.FileUtils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class DownloadPdfClient2 {
	static final String outDirPath = "C:\\temp\\Pdf";
	static final String kBaseUrl = "http://localhost:8080/";
	//static final String kBaseUrl = "http://mfg.preprodservices.aa1.pqe";
	static final int timeoutMs = 1200000;
	static final String userAgent = "DisOut";
	public static final String kHttpHeaderUserAgentPropertyName = "User-Agent";
	static final int EXCLUDE_COPYRIGHT_TEXT = 1;
	static final int INCLUDE_COPYRIGHT_TEXT = 0;
	static final int EXCLUDE_RESTRICTED_PUBS = 0;
	static final int INCLUDE_RESTRICTED_PUBS = 1;
	static WebResource service;
	
		
	public static void main(String[] args) throws Exception {
		String pubId = "8803094";
		String pdfType = "POD";
		String url = prepareUrlFor(pubId, pdfType, EXCLUDE_COPYRIGHT_TEXT, INCLUDE_RESTRICTED_PUBS);
		initClient();
		System.out.println("URL : " + url);
		int statusCode = doDownload(url, new File(outDirPath, pubId + ".pdf"));
		System.out.println("Return Status code: " + statusCode);
	}
	
	private static void initClient() {
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, 300000);
		clientConfig.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, true);
		Client client = Client.create(clientConfig);
		service = client.resource(kBaseUrl);		
	}
	
	private static String prepareUrlFor(String pubId, String pdfType, int ec, int ri) {
		String url;
		url = "dismetadata_service/disout/getPdf" + "/" + pubId + "/" + pdfType;
		/*if (ec == 0 && ri == 0) {
			url = "dismetadata_service/disout/getPdf" + "/" + pubId + "/" + pdfType;
		} else if (ec == 1 && ri == 1) {
			url = "dismetadata_service/disout/getPdf" + "/" + pubId + "/" + pdfType + "?ec=1&ri=1"  ; 
		} else {
			url = "dismetadata_service/disout/getPdf" + "/" + pubId + "/" + pdfType + "?ec=" + ec + "&ri=" + ri;
		}*/
		return url;		
	}
	
	private static int doDownload(String url, File destination) throws IOException {
		int statusCode = 0;;
		try {
			MultivaluedMap<String, String> params = new MultivaluedMapImpl();
			params.add("ec", "1");
			params.add("ri", "1");
			ClientResponse result = 
					service.path(url).queryParams(params).get(ClientResponse.class);
			statusCode = result.getStatus();
			String response;
	   
		    if(statusCode == HttpURLConnection.HTTP_OK) {
		    	InputStream input = result.getEntityInputStream();
				FileUtils.copyInputStreamToFile(input, destination);
				input.close();
				result.close();
		    } else {
		    	response = result.getEntity(String.class);
		    	System.out.println("response : " + response);
		    }
		} catch (Exception e) {
			System.out.println("Exception happened : " + e.getMessage());
		}
		return statusCode;
	}
}

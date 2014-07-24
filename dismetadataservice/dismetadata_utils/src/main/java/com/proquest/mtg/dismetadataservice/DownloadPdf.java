package com.proquest.mtg.dismetadataservice;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.FileUtils;

public class DownloadPdf {
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
	
		
	public static void main(String[] args) throws Exception {
		String pubId = "8803094";
		String pdfType = "POD";
		String url = prepareUrlFor(pubId, pdfType, EXCLUDE_COPYRIGHT_TEXT, INCLUDE_RESTRICTED_PUBS);
		System.out.println("URL : " + url);
		int statusCode = doDownload(new URL(url), new File(outDirPath, pubId + ".pdf"));
		System.out.println("Return Status code: " + statusCode);
	}
	
	private static String prepareUrlFor(String pubId, String pdfType, int ec, int ri) {
		String url;
		if (ec == 0 && ri == 0) {
			url = kBaseUrl + "dismetadata_service/disout/getPdf" + "/" + pubId + "/" + pdfType;
		} else if (ec == 1 && ri == 1) {
			url = kBaseUrl + "dismetadata_service/disout/getPdf" + "/" + pubId + "/" + pdfType + "?ec=1&ri=1"  ; 
		} else {
			url = kBaseUrl + "dismetadata_service/disout/getPdf" + "/" + pubId + "/" + pdfType + "?ec=" + ec + "&ri=" + ri;
		}
		return url;		
	}
	
	private static int doDownload(URL url, File destination) throws IOException {
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)url.openConnection();
			connection.setInstanceFollowRedirects(true);
			connection.setConnectTimeout(timeoutMs);
			connection.setReadTimeout(timeoutMs);
			connection.setRequestProperty(kHttpHeaderUserAgentPropertyName, userAgent);
			connection.connect();
			int statusCode = connection.getResponseCode();
			if (HttpURLConnection.HTTP_OK == statusCode) {
				InputStream input = connection.getInputStream();
				FileUtils.copyInputStreamToFile(input, destination);
			}
			return statusCode;
		} finally {
			if (null != connection) {
				connection.disconnect();
			}
		}
	}
}

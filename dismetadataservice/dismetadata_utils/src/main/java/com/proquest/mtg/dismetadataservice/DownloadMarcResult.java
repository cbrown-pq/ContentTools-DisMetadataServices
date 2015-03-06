package com.proquest.mtg.dismetadataservice;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.io.FileUtils;

public class DownloadMarcResult {
	static final String outDirPath = "C:\\temp\\Marc";
	static final String kBaseUrl = "http://localhost:8081/";
	//static final String kBaseUrl = "http://mfg.preprodservices.aa1.pqe";
	static final int timeoutMs = 1200000;
	static final String userAgent = "DisOut";
	public static final String kHttpHeaderUserAgentPropertyName = "User-Agent";
		
	public static void main(String[] args) throws Exception {
		String pubId = "H006920";
		String format = "MARCXML";
		String url = kBaseUrl + "dismetadata_service/disout/metadata" + "/" + pubId + "/" + format;
		int statusCode = doDownload(new URL(url), new File(outDirPath, pubId + ".mrc"));
		System.out.println("Return Status code: " + statusCode);
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

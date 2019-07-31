package com.proquest.mtg.dismetadataservice.media;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;

import com.google.inject.name.Named;
import com.proquest.mtg.dismetadataservice.properties.DisMetadataProperties;

public class MediaDownloader implements IMediaDownloader {

	public static final String kHttpHeaderUserAgentPropertyName = "User-Agent";
	
	
	private final int timeoutMs;
	private final String pqServiceUserAgent;
	
	@Inject
	public MediaDownloader(
		@Named(DisMetadataProperties.PQ_SERVICE_TIMEOUT_MS) int timeoutMs,
		@Named(DisMetadataProperties.PQ_SERVICE_USER_AGENT) String userAgent) {
		this.timeoutMs = timeoutMs;
		this.pqServiceUserAgent = userAgent;
	}

	public int getTimeoutMs() {
		return timeoutMs;
	}
	
	public String getPqServiceUserAgent() {
		return pqServiceUserAgent;
	}

	@Override
	public byte[] download(URL url) throws Exception {
		byte[] content = null;
		for (int i = 1; i <= 5; i++) {
			try {
				content = doDownload(url);
			} catch (IOException | MediaDownloadException e1) {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}
			break;
		}
		if (content == null) {
			throw new MediaDownloadException("Failed to download the PDF.");
		}
		return content;
	}
	
	private byte[] doDownload(URL url) throws IOException, 
								MediaDownloadException {
		HttpURLConnection connection = null;
		byte[] content = null;
		boolean redirect = false;
		try {
			
			System.out.println("Redirect to URL :11");
			connection = (HttpURLConnection)url.openConnection();
			System.out.println("Redirect to URL :2");
			connection.setInstanceFollowRedirects(true);
			System.out.println("Redirect to URL :3");
			connection.setConnectTimeout(getTimeoutMs());
			System.out.println("Redirect to URL :4");
			connection.setReadTimeout(getTimeoutMs());
			System.out.println("Redirect to URL :5");
			connection.setRequestProperty(kHttpHeaderUserAgentPropertyName, getPqServiceUserAgent());
			System.out.println("Redirect to URL :6");
			connection.connect();
			System.out.println("Redirect to URL :7");
			int statusCode = connection.getResponseCode();
			System.out.println("Redirect to URL statusCode:" +statusCode);
			if (statusCode != HttpURLConnection.HTTP_OK) {
				redirect = connection.getFollowRedirects();
			}
			System.out.println("redirect:" +redirect);
			if (redirect) {
				System.out.println("Redirect to URL :8");
				// get redirect url from "location" header field
				String newUrl = connection.getHeaderField("Location");


				// open the new connnection again
				connection = (HttpURLConnection)new URL(newUrl).openConnection();
				connection.setInstanceFollowRedirects(true);
				connection.setConnectTimeout(getTimeoutMs());
				connection.setReadTimeout(getTimeoutMs());
				connection.setRequestProperty(kHttpHeaderUserAgentPropertyName, getPqServiceUserAgent());
				connection.connect();
										
				System.out.println("Redirect to URL : " + newUrl);

			}
			System.out.println("redirect8:" +redirect);
			if (HttpURLConnection.HTTP_OK == statusCode || redirect == true) {
				System.out.println("redirect9:" +redirect);
				content = IOUtils.toByteArray(connection.getInputStream());
				System.out.println("redirect10:" +content.length);
			} else {
				System.out.println("redirect11:" +redirect);
				throw new MediaDownloadException("Failed to down load the PDF.");
			}
			return content;
		} finally {
			if (null != connection) {
				connection.disconnect();
			}
		}
	}
}
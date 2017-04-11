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
		try {
			connection = (HttpURLConnection)url.openConnection();
			connection.setInstanceFollowRedirects(true);
			connection.setConnectTimeout(getTimeoutMs());
			connection.setReadTimeout(getTimeoutMs());
			connection.setRequestProperty(kHttpHeaderUserAgentPropertyName, getPqServiceUserAgent());
			connection.connect();
			int statusCode = connection.getResponseCode();
			if (HttpURLConnection.HTTP_OK == statusCode) {
				
				content = IOUtils.toByteArray(connection.getInputStream());
				
			} else {
				throw new MediaDownloadException("Failed to download the PDF.");
			}
			return content;
		} finally {
			if (null != connection) {
				connection.disconnect();
			}
		}
	}
}

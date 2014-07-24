package com.proquest.mtg.dismetadataservice.media;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

public class MediaDownloader_Tests {

	MediaDownloader target;
	int timeoutMs;
	String userAgent;
	
	@Before
	public void setUp() throws Exception {
		timeoutMs = 1200000;
		userAgent = "DisOut";
		target = new MediaDownloader(timeoutMs, userAgent);
	}
	
	@Test
	public void hasCorrect_TimeoutMs() throws Exception {
		assertThat(target.getTimeoutMs(), is(timeoutMs));
	}
	
	@Test
	public void hasCorrect_UserAgent() throws Exception {
		assertThat(target.getPqServiceUserAgent(), is(userAgent));
	}
	
	@Test(expected=MediaDownloadException.class)
	public void download_WhenNotFound_Throw_Exception() throws Exception {
		URL url = new URL("http://mfg.preprodservices.aa1.pqe/relatedids/goid/dissnum");
		target.download(url);
	}
}

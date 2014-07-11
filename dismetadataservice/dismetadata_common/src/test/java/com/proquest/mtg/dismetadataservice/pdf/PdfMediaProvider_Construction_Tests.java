package com.proquest.mtg.dismetadataservice.pdf;

import org.junit.Before;
import org.junit.Test;
import org.easymock.EasyMockSupport;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import com.proquest.mtg.dismetadataservice.media.IMediaDownloader;
import com.proquest.mtg.dismetadataservice.media.MediaDownloader;
import com.proquest.mtg.dismetadataservice.media.PubMediaInfoProviderFactory;

public class PdfMediaProvider_Construction_Tests extends EasyMockSupport {
	PubMediaInfoProviderFactory pubMediaProviderFactory;
	IMediaDownloader mediaDownloader;	
	
	PdfMediaProvider target;
	
	@Before
	public void setUp() throws Exception {
		pubMediaProviderFactory = createMock(PubMediaInfoProviderFactory.class);
		mediaDownloader = createMock(MediaDownloader.class);
				
		target = new PdfMediaProvider(
				pubMediaProviderFactory,
				mediaDownloader);
	}
	
	@Test
	public void hasCorrect_PubMediaInfoProviderFactoryResource() throws Exception {
		assertThat(target.getPubMediaProviderFactory(), sameInstance(pubMediaProviderFactory));
	}
	
	@Test
	public void hasCorrect_MediaDownloaderResource() throws Exception {
		assertThat(target.getMediaDownloader(), sameInstance(mediaDownloader));
	}
}

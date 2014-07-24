package com.proquest.mtg.dismetadataservice.media;

import java.net.URL;

public interface IMediaDownloader {
	public abstract byte[] download(URL url) throws Exception;
}

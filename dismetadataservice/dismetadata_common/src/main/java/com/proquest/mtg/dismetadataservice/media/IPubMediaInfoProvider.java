package com.proquest.mtg.dismetadataservice.media;

public interface IPubMediaInfoProvider {
	PubMediaInfo makeFor(String pubName) throws Exception;
}

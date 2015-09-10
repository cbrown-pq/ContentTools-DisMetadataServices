package com.proquest.mtg.dismetadataservice.exodus;

import java.util.List;

import com.proquest.mtg.dismetadataservice.mrngxml.Dissertation;

public interface IMStarPubMetaDataProvider {
	Dissertation getPubMetaDataFor(String pubId) throws Exception;
}

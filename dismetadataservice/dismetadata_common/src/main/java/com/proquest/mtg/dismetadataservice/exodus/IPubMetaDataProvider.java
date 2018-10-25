package com.proquest.mtg.dismetadataservice.exodus;

public interface IPubMetaDataProvider {
	DisPubMetaData getPubMetaDataFor(String pubId, int excludeRestriction, int excludeAbstract, int excludeAltAbstract) throws Exception;
}

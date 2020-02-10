package com.proquest.mtg.dismetadataservice.datasource;

public interface IPubMetaDataProvider {
	DisPubMetaData getPubMetaDataFor(String pubId, String mr3Data, int excludeRestriction, int excludeAbstract, int excludeAltAbstract) throws Exception;
}

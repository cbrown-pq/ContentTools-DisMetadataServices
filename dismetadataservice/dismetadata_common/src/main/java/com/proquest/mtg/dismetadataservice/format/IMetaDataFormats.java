package com.proquest.mtg.dismetadataservice.format;

public interface IMetaDataFormats {
	
	public String makeFor(String pubId, String mr3Data, int excludeRestriction, int excludeAbstract, int excludeAltAbstract) throws Exception;

}

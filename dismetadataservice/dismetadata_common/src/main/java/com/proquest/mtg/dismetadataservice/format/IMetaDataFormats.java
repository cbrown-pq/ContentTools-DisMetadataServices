package com.proquest.mtg.dismetadataservice.format;

public interface IMetaDataFormats {
	
	public String makeFor(String pubNum, int excludeRestriction, int excludeAbstract, int excludeAltAbstract) throws Exception;

}

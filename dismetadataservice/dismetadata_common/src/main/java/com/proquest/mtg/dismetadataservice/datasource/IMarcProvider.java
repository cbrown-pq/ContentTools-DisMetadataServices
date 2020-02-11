package com.proquest.mtg.dismetadataservice.datasource;

import com.proquest.mtg.dismetadataservice.marc.MarcRecord;

public interface IMarcProvider {
	
	MarcRecord getMarcResultFor(String pubNum, String mr3Data, int excludeRestriction, int excludeAbstract, int excludeAltAbstract) throws Exception;

	MarcRecord getMarc21RDAResultFor(String pubNum, String mr3Data, int excludeRestriction, int excludeAbstract, int excludeAltAbstract) throws Exception;

}

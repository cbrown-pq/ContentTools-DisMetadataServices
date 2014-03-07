package com.proquest.mtg.dismetadataservice.exodus;

import com.proquest.mtg.dismetadataservice.marc.MarcRecord;

public interface IMarcProvider {
	
	MarcRecord getMarcResultFor(String pubNum) throws Exception;

	MarcRecord getMarc21RDAResultFor(String pubNum) throws Exception;

}

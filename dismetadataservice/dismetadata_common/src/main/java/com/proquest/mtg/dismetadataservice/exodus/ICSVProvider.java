package com.proquest.mtg.dismetadataservice.exodus;

public interface ICSVProvider {

	String getCSVResultFor(String pubNum, int excludeRestriction)
			throws Exception;

}

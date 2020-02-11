package com.proquest.mtg.dismetadataservice.exodus;

public interface ICSVProvider {

	String getCSVResultFor(String pubNum, String mr3Data, int excludeRestriction, int excludeAbstract, int excludeAltAbstract)
			throws Exception;

}

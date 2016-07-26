package com.proquest.mtg.dismetadataservice.exodus;

public interface ICSVProvider {

	String getCSVResultFor(String pubNum, int excludeRestriction, int excludeAbstract)
			throws Exception;

}

package com.proquest.mtg.dismetadataservice.exodus;

public interface IMarcProvider {
	
	String getMarcResultFor(Iterable<String> pubs) throws Exception;

}

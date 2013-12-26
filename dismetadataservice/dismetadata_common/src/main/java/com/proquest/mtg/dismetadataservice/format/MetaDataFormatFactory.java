package com.proquest.mtg.dismetadataservice.format;

import java.util.HashMap;


public class MetaDataFormatFactory {
	
private final HashMap<String, IMetaDataFormats> factoryMap;
	
	public MetaDataFormatFactory()  {
		factoryMap = new HashMap<String, IMetaDataFormats>();
	}
	
	public void add(String taskType, IMetaDataFormats factory) {
		factoryMap.put(taskType, factory);
	}
	
	public IMetaDataFormats getFor(String taskType) {
		return factoryMap.get(taskType);
	}
}

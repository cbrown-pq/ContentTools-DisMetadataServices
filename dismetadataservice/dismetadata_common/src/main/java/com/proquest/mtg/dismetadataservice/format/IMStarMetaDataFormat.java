package com.proquest.mtg.dismetadataservice.format;


import com.proquest.mtg.dismetadataservice.mrngxml.Dissertation;

public interface IMStarMetaDataFormat {

	Dissertation makeForDissertation(String pubNums) throws Exception;
}

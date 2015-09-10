package com.proquest.mtg.dismetadataservice.mrngxml;

public class FakeDissertationFactory {

	public Dissertation makeFor(String pubName) {
		Dissertation result = new Dissertation();
		result.setPubNumber(pubName);
		return result;
	}
}

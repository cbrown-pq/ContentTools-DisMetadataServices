package com.proquest.mtg.dismetadataservice.metadata;

import com.google.inject.Inject;

public class PlainTextNormalizer {
	
	private final HTMLTagRemover htmlTagRemover;

	@Inject
	public PlainTextNormalizer(HTMLTagRemover htmlTagRemover) {
		this.htmlTagRemover = htmlTagRemover;
	}

	public HTMLTagRemover getHTMLTagRemover() {
		return htmlTagRemover;
	}

	public String applyTo(String x) {
		return getHTMLTagRemover().applyTo(x);
	}
}

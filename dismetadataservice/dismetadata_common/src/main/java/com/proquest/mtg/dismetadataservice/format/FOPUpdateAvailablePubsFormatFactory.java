package com.proquest.mtg.dismetadataservice.format;

import java.io.IOException;

import org.xml.sax.SAXException;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.exodus.IFopFormatsDataProvider;

public class FOPUpdateAvailablePubsFormatFactory {
	private final IFopFormatsDataProvider fopFormatsDataProvider;

	@Inject
	public FOPUpdateAvailablePubsFormatFactory(
			IFopFormatsDataProvider fopFormatsDataProvider) throws IOException,
			SAXException {
		this.fopFormatsDataProvider = fopFormatsDataProvider;

	}

	public IFopFormatsDataProvider getFopFormatsDataProvider() {
		return fopFormatsDataProvider;
	}

	public String updateFOPFormatsQuery(String pubNumber, String format) throws Exception {
		return getFopFormatsDataProvider().updateFOPFormatsQuery(pubNumber, format);
		
	}

}

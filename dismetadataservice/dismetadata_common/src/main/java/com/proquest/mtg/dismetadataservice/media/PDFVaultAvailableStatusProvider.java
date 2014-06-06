package com.proquest.mtg.dismetadataservice.media;

import javax.inject.Inject;

import com.google.common.collect.ImmutableSet;

public class PDFVaultAvailableStatusProvider {
	private final PubMediaInfoProviderFactory pubMediaProviderFactory;
	
	@Inject
	public PDFVaultAvailableStatusProvider(PubMediaInfoProviderFactory pubMediaProviderFactory) {
		this.pubMediaProviderFactory = pubMediaProviderFactory;
	}
	
	public PubMediaInfoProviderFactory getPubMediaProviderFactory() {
		return pubMediaProviderFactory;
	}
	
	public boolean isPdfAvailableInVaultFor(String pub) throws Exception {
		IPubMediaInfoProvider pubMediaInfoProvider = getPubMediaProviderFactory().create(true);
		PubMediaInfo curPubMediaInfo = pubMediaInfoProvider.makeFor(pub);
		PdfMediaInfo curPdfMediaInfo = curPubMediaInfo.getPreferredPdf(getPdfPriorityOrder());
		if (null != curPdfMediaInfo) {
			return true;
		}
		return false;
	}

	public ImmutableSet<PdfType> getPdfPriorityOrder() {
		return PdfType.kPriorityOrderForDistribution;
	}
}

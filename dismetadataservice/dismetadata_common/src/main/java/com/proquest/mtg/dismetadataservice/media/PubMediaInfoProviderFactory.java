package com.proquest.mtg.dismetadataservice.media;

import javax.inject.Inject;

import com.proquest.mtg.services.gossamer.docfrosting.DocumentfrostingObjectIdResource;
import com.proquest.mtg.services.gossamer.relatedids.RelatedidsGoidIdtypeIdResource;

public class PubMediaInfoProviderFactory {
	private final RelatedidsGoidIdtypeIdResource relatedIdsResource;
	private final DocumentfrostingObjectIdResource docFrostingResource;
	private final PdfMediaInfoFactory pdfMediaInfoFactory;

	@Inject
	public PubMediaInfoProviderFactory(RelatedidsGoidIdtypeIdResource relatedIdsResource,  
			DocumentfrostingObjectIdResource docFrostingResource,
			PdfMediaInfoFactory pdfMediaInfoFactory) {
			this.relatedIdsResource = relatedIdsResource;
			this.docFrostingResource = docFrostingResource;
			this.pdfMediaInfoFactory = pdfMediaInfoFactory;
	}

	public RelatedidsGoidIdtypeIdResource getRelatedIdsResource() {
		return relatedIdsResource;
	}

	public DocumentfrostingObjectIdResource getDocFrostingResource() {
		return docFrostingResource;
	}

	public PdfMediaInfoFactory getPdfMediaInfoFactory() {
		return pdfMediaInfoFactory;
	}

	public PubMediaInfoProvider create(boolean restrictionIncluded) {
		
		return new PubMediaInfoProvider(
				restrictionIncluded,
				getRelatedIdsResource(),
				getDocFrostingResource(),
				getPdfMediaInfoFactory()
				);
	}
}

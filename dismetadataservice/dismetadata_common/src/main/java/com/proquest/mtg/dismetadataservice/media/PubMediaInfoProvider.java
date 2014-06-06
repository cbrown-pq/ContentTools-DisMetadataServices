package com.proquest.mtg.dismetadataservice.media;

import java.util.List;

import com.google.common.collect.Lists;
import com.proquest.gossamer.exceptions.ClientTimeoutException;
import com.proquest.gossamer.exceptions.CommunicationsException;
import com.proquest.gossamer.exceptions.EncodingDecodingException;
import com.proquest.gossamer.exceptions.MethodFault;
import com.proquest.mtg.services.bindings.docfrosting.DocFrosting;
import com.proquest.mtg.services.bindings.relatedids.RelatedId;
import com.proquest.mtg.services.gossamer.docfrosting.DocumentfrostingObjectIdResource;
import com.proquest.mtg.services.gossamer.relatedids.RelatedidsGoidIdtypeIdResource;

public class PubMediaInfoProvider implements IPubMediaInfoProvider {
	public final static String kDissertationRelatedIdType = "DISSNUM";
	
	private final boolean restrictionIncluded;
	private final RelatedidsGoidIdtypeIdResource relatedIdsResource;
	private final DocumentfrostingObjectIdResource docFrostingResource;
	private final PdfMediaInfoFactory pdfMediaInfoFactory;

	
	public PubMediaInfoProvider(
		boolean restrictionIncluded,
		RelatedidsGoidIdtypeIdResource relatedIdsResource,  
		DocumentfrostingObjectIdResource docFrostingResource,
		PdfMediaInfoFactory pdfMediaInfoFactory
		) {
		this.restrictionIncluded = restrictionIncluded; 
		this.relatedIdsResource = relatedIdsResource;
		this.docFrostingResource = docFrostingResource;
		this.pdfMediaInfoFactory = pdfMediaInfoFactory;
	}

	public boolean isRestrictionIncluded() {
		return restrictionIncluded;
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
	

	public PubMediaInfo makeFor(String pubId) 
			throws ClientTimeoutException, MethodFault, EncodingDecodingException, CommunicationsException {
		List<GoidMediaInfo> result = Lists.newArrayList();
		for (String curGoid : getGoidsFor(pubId)) {
			DocFrosting curDocFrosting = getDocFrostingFor(curGoid);
			if (null != curDocFrosting) {
				if (curDocFrosting.getStatus().toString().equals("NORMAL")) {
				result.add(new GoidMediaInfo(
						curGoid, 
						getPdfMediaInfoFactory().makeFrom(curDocFrosting)));
				}
			}
		}
		return new PubMediaInfo(pubId, result);
	}

	private DocFrosting getDocFrostingFor(String curGoid) throws MethodFault,
			EncodingDecodingException, CommunicationsException,
			ClientTimeoutException {
		return getDocFrostingResource().get(curGoid, null, isRestrictionIncluded(), null);
	}

	private List<String> getGoidsFor(String pubId) 
			throws ClientTimeoutException, MethodFault, EncodingDecodingException, CommunicationsException {
		List<String> goids = Lists.newArrayList();
		RelatedId[] relatedIds = 
				getRelatedIdsResource().get(kDissertationRelatedIdType, pubId).getRelatedId();
		for (RelatedId curRelatedId : relatedIds) {
			goids.add(curRelatedId.getContent());
		}
		return goids;
	}


}

package com.proquest.mtg.dismetadataservice.media;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.properties.DisMetadataProperties;
import com.proquest.mtg.services.bindings.docfrosting.Comp;
import com.proquest.mtg.services.bindings.docfrosting.DocFrosting;
import com.proquest.mtg.services.bindings.docfrosting.Mindex;
import com.proquest.mtg.services.bindings.docfrosting.Rep;

public class PdfMediaInfoFactory {
	
	public static final String kFullText = "FT";
	public static final String kPdfFullText = "PFT";
	
	private final URI baseUrl;
	
	@Inject
	public PdfMediaInfoFactory(
			@Named(DisMetadataProperties.PQ_SERVICE_URL_BASE) String baseUrl) 
	throws URISyntaxException {
		this.baseUrl = new URI(baseUrl);
	}
	
	public URI getBaseUrl() {
		return baseUrl;
	}
	
	public List<PdfMediaInfo> makeFrom(DocFrosting docFrosting) {
		List<PdfMediaInfo> result = Lists.newArrayList();
		if (null != docFrosting) {
			Mindex mediaIndex = docFrosting.getMindex();
			if (null != mediaIndex) {
				for (Comp curComponent : mediaIndex.getComp()) {
					if (isFullText(curComponent)) {
					for (Rep curRepresentation :curComponent.getRep()) {
							if (isFullTextPdf(curRepresentation)) {
									result.add(makeFrom(curComponent, curRepresentation));
								}
							}
						}
				}
			}
		}
		return result;
	}
	
	private PdfMediaInfo makeFrom(Comp curComponent, Rep curRepresentation) {
		PdfMediaInfo curPdfInfo = new PdfMediaInfo(
				PdfType.fromString(curRepresentation.getPdfType()),
				makeUrlFrom(curComponent.getBasepath(), curRepresentation.getPath()));
		return curPdfInfo;
	}
	
	private String makeUrlFrom(String componentpath, String representationPath) {
		System.out.println("makeUrlFrom:"+getBaseUrl().resolve(componentpath).resolve(representationPath).toString());
		return getBaseUrl().resolve(componentpath).resolve(representationPath).toString();
	}

	private boolean isFullText(Comp curComponent) {
	   System.out.println(null != curComponent &&
			curComponent.getType().equalsIgnoreCase(kFullText));
		return 
			null != curComponent &&
			curComponent.getType().equalsIgnoreCase(kFullText) ;
	}

	private boolean isFullTextPdf(Rep curRepresentation) {
		System.out.println(null != curRepresentation &&
				curRepresentation.getType().equalsIgnoreCase(kPdfFullText) &&
				null != curRepresentation.getPdfType() &&
				null != curRepresentation.getPath());
		return
			null != curRepresentation &&
			curRepresentation.getType().equalsIgnoreCase(kPdfFullText) &&
			null != curRepresentation.getPdfType() &&
			null != curRepresentation.getPath();
	}
}

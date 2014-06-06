package com.proquest.mtg.dismetadataservice.media;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.services.gossamer.docfrosting.DocumentfrostingObjectIdResource;
import com.proquest.mtg.services.gossamer.relatedids.RelatedidsGoidIdtypeIdResource;

public class PubMediaInfoProviderFactory_Tests extends EasyMockSupport {
	RelatedidsGoidIdtypeIdResource relatedIdsResource;    
	DocumentfrostingObjectIdResource docFrostingResource; 
	PdfMediaInfoFactory pdfMediaInfoFactory;
	
	PubMediaInfoProvider pubMediaInfoProvider;
	
	
	PubMediaInfoProviderFactory target;
	
	@Before
	public void setUp() throws Exception {
		relatedIdsResource = createMock(RelatedidsGoidIdtypeIdResource.class);
		docFrostingResource = createMock(DocumentfrostingObjectIdResource.class);
		pdfMediaInfoFactory = createMock(PdfMediaInfoFactory.class);
		pubMediaInfoProvider = createMock(PubMediaInfoProvider.class);
		
		target = new PubMediaInfoProviderFactory(
				relatedIdsResource,
				docFrostingResource,
				pdfMediaInfoFactory);
	}
	
	@Test
	public void hasCorrect_RelatedIdsResource() throws Exception {
		assertThat(target.getRelatedIdsResource(), sameInstance(relatedIdsResource));
	}
	
	@Test
	public void hasCorrect_DocFrostingResource() throws Exception {
		assertThat(target.getDocFrostingResource(), sameInstance(docFrostingResource));
	}
	
	@Test
	public void hasCorrect_PdfMediaInfoFactory() throws Exception {
		assertThat(target.getPdfMediaInfoFactory(), sameInstance(pdfMediaInfoFactory));
	}
}

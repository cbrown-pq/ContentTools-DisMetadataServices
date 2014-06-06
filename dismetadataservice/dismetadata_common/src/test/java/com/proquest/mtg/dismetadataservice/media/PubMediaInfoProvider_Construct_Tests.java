package com.proquest.mtg.dismetadataservice.media;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.services.gossamer.docfrosting.DocumentfrostingObjectIdResource;
import com.proquest.mtg.services.gossamer.relatedids.RelatedidsGoidIdtypeIdResource;

public class PubMediaInfoProvider_Construct_Tests {
	boolean restrictionIncluded;
	RelatedidsGoidIdtypeIdResource relatedIdsResource;    
	DocumentfrostingObjectIdResource docFrostingResource; 
	PdfMediaInfoFactory pdfMediaInfoFactory;
	
	
	PubMediaInfoProvider target;
	
	@Before
	public void setUp() throws Exception {
		
		restrictionIncluded = true;
		relatedIdsResource = EasyMock.createNiceMock(RelatedidsGoidIdtypeIdResource.class);
		docFrostingResource = EasyMock.createNiceMock(DocumentfrostingObjectIdResource.class);
		pdfMediaInfoFactory = EasyMock.createNiceMock(PdfMediaInfoFactory.class);
		
		target = new PubMediaInfoProvider(
				restrictionIncluded,
				relatedIdsResource,
				docFrostingResource,
				pdfMediaInfoFactory);
	}
	
	@Test
	public void hasCorrect_RestrictionInfo() throws Exception {
		assertThat(target.isRestrictionIncluded(), is(restrictionIncluded));
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

package com.proquest.mtg.dismetadataservice.media;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.proquest.mtg.services.bindings.docfrosting.DocFrosting;
import com.proquest.mtg.services.bindings.relatedids.RelatedId;
import com.proquest.mtg.services.bindings.relatedids.RelatedIds;
import com.proquest.mtg.services.gossamer.docfrosting.DocumentfrostingObjectIdResource;
import com.proquest.mtg.services.gossamer.relatedids.RelatedidsGoidIdtypeIdResource;

public class PubMediaInfoProvider_Acceptance_Tests extends EasyMockSupport {

	RelatedidsGoidIdtypeIdResource relatedIdsResource;    
	DocumentfrostingObjectIdResource docFrostingResource; 
	PdfMediaInfoFactory pdfMediaInfoFactory;              
	
	PubMediaInfoProvider target;
	
	String pubName = "Pub 1";
	
	String goid1 = "goid1A";
	String goid2 = "goid2A";
	String goid3 = "goid3A";
	
	boolean restrictionIncluded;
	
	RelatedId relatedId1;
	RelatedId relatedId2;
	RelatedId relatedId3;
	RelatedIds relatedIds;	
	
	DocFrosting docFrosting1;
	DocFrosting docFrosting2;
	
	PdfMediaInfo pdfMediaInfo1A;
	PdfMediaInfo pdfMediaInfo2A;
	PdfMediaInfo pdfMediaInfo2B;
	
	PubMediaInfo pubMediaInfo;
	
	@Before
	public void setUp() throws Exception {
		restrictionIncluded = true;
		relatedIdsResource = createMock(RelatedidsGoidIdtypeIdResource.class);
		docFrostingResource = createMock(DocumentfrostingObjectIdResource.class);
		pdfMediaInfoFactory = createMock(PdfMediaInfoFactory.class);
		
		relatedId1 = new RelatedId(goid1);
		relatedId2 = new RelatedId(goid2);
		relatedId3 = new RelatedId(goid3);
		
		relatedIds = new RelatedIds();
		relatedIds.setRelatedId(new RelatedId[] { relatedId1, relatedId2 });
		
		docFrosting1 = createMock(DocFrosting.class);
		docFrosting2 = createMock(DocFrosting.class);
		
		pdfMediaInfo1A = createMock(PdfMediaInfo.class);
		pdfMediaInfo2A = createMock(PdfMediaInfo.class);
		pdfMediaInfo2B = createMock(PdfMediaInfo.class);
			
		expect(relatedIdsResource.get(
				PubMediaInfoProvider.kDissertationRelatedIdType,
				pubName)).andReturn(relatedIds).once();
		
		expect(docFrostingResource.get(goid1, null, restrictionIncluded, null, false)).andReturn(docFrosting1).once();
		
		expect(docFrostingResource.get(goid2, null, restrictionIncluded, null, false)).andReturn(docFrosting2).once();
		
		expect(pdfMediaInfoFactory.makeFrom(docFrosting1)).andReturn(
				Lists.newArrayList(pdfMediaInfo1A)).once();
		
		expect(pdfMediaInfoFactory.makeFrom(docFrosting2)).andReturn(
				Lists.newArrayList(pdfMediaInfo2A, pdfMediaInfo2B)).once();

		expect(docFrosting1.getStatus()).andReturn(new String("NORMAL")).once();
		expect(docFrosting2.getStatus()).andReturn(new String("NORMAL")).once();
		replayAll();
		
		target = new PubMediaInfoProvider(
				restrictionIncluded,
				relatedIdsResource,
				docFrostingResource,
				pdfMediaInfoFactory);
		
		pubMediaInfo = target.makeFor(pubName);
		
		verifyAll();
	}
		
	@Test
	public void hasCorrect_GoidCount() throws Exception {
		assertThat(pubMediaInfo.getGoidMediaInfo().size(), is(2));
	}
	
	@Test
	public void goid1_HasCorrect_Goid() throws Exception {
		GoidMediaInfo goidMediaInfo1 = pubMediaInfo.getGoidMediaInfo().get(0);
		assertThat(goidMediaInfo1.getGoid(), is(goid1));
	}
	
	@Test
	public void goid1_HasCorrect_Pdfs() throws Exception {
		GoidMediaInfo goidMediaInfo1 = pubMediaInfo.getGoidMediaInfo().get(0);
		assertThat(goidMediaInfo1.getPdfMediaInfo().size(), is(1));
		assertThat(goidMediaInfo1.getPdfMediaInfo().get(0), sameInstance(pdfMediaInfo1A));
	}
	
	@Test
	public void goid2_HasCorrect_Goid() throws Exception {
		GoidMediaInfo goidMediaInfo2 = pubMediaInfo.getGoidMediaInfo().get(1);
		assertThat(goidMediaInfo2.getGoid(), is(goid2));
	}
	
	@Test
	public void goid2_HasCorrect_Pdfs() throws Exception {
		GoidMediaInfo goidMediaInfo2 = pubMediaInfo.getGoidMediaInfo().get(1);
		assertThat(goidMediaInfo2.getPdfMediaInfo().size(), is(2));
		assertThat(goidMediaInfo2.getPdfMediaInfo().get(0), sameInstance(pdfMediaInfo2A));
		assertThat(goidMediaInfo2.getPdfMediaInfo().get(1), sameInstance(pdfMediaInfo2B));
	}
}

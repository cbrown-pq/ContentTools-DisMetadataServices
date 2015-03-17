package com.proquest.mtg.dismetadataservice.media;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.CoreMatchers.is;
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

public class PubMediaInfoProvider_DocforstingStatus_Tests extends EasyMockSupport {
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
	DocFrosting docFrosting3;
	
	PdfMediaInfo pdfMediaInfo1A;
	PdfMediaInfo pdfMediaInfo2A;
	PdfMediaInfo pdfMediaInfo2B;
	PdfMediaInfo pdfMediaInfo3A;
	
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
		
		docFrosting1 = createMock(DocFrosting.class);
		docFrosting2 = createMock(DocFrosting.class);		
		docFrosting3 = createMock(DocFrosting.class);
		
		pdfMediaInfo1A = createMock(PdfMediaInfo.class);
		pdfMediaInfo2A = createMock(PdfMediaInfo.class);
		pdfMediaInfo2B = createMock(PdfMediaInfo.class);
		pdfMediaInfo3A = createMock(PdfMediaInfo.class);
	}
	
	void prepareDocforstingStatus_OneNormal() throws Exception {
		relatedIds = new RelatedIds();
		relatedIds.setRelatedId(new RelatedId[] { relatedId1 });
		
		expect(relatedIdsResource.get(
				PubMediaInfoProvider.kDissertationRelatedIdType,
				pubName)).andReturn(relatedIds).once();
		
		expect(docFrostingResource.get(goid1, null, restrictionIncluded, null, false)).andReturn(docFrosting1).once();		
		expect(docFrosting1.getStatus()).andReturn(new String("NORMAL")).once();
		expect(pdfMediaInfoFactory.makeFrom(docFrosting1)).andReturn(
				Lists.newArrayList(pdfMediaInfo1A)).once();

		replayAll();
		
		target = new PubMediaInfoProvider(
				restrictionIncluded,
				relatedIdsResource,
				docFrostingResource,
				pdfMediaInfoFactory);
		
		pubMediaInfo = target.makeFor(pubName);
		verifyAll();
	}
	void prepareDocforstingStatus_AllNormal() throws Exception {	
		relatedIds = new RelatedIds();
		relatedIds.setRelatedId(new RelatedId[] { relatedId1, relatedId2 });
		
		expect(relatedIdsResource.get(
				PubMediaInfoProvider.kDissertationRelatedIdType,
				pubName)).andReturn(relatedIds).once();
		
		expect(docFrostingResource.get(goid1, null, restrictionIncluded, null, false)).andReturn(docFrosting1).once();		
		expect(docFrostingResource.get(goid2, null, restrictionIncluded, null, false)).andReturn(docFrosting2).once();

		expect(docFrosting1.getStatus()).andReturn(new String("NORMAL")).once();
		expect(docFrosting2.getStatus()).andReturn(new String("NORMAL")).once();
		
		expect(pdfMediaInfoFactory.makeFrom(docFrosting1)).andReturn(
				Lists.newArrayList(pdfMediaInfo1A)).once();
		
		expect(pdfMediaInfoFactory.makeFrom(docFrosting2)).andReturn(
				Lists.newArrayList(pdfMediaInfo2A, pdfMediaInfo2B)).once();
		
		replayAll();
		
		target = new PubMediaInfoProvider(
				restrictionIncluded,
				relatedIdsResource,
				docFrostingResource,
				pdfMediaInfoFactory);
		
		pubMediaInfo = target.makeFor(pubName);
		verifyAll();
	}
	
	void prepareDocforstingStatus_AllDelete() throws Exception {		
		relatedIds = new RelatedIds();
		relatedIds.setRelatedId(new RelatedId[] { relatedId1, relatedId2 });
		
		expect(relatedIdsResource.get(
				PubMediaInfoProvider.kDissertationRelatedIdType,
				pubName)).andReturn(relatedIds).once();
		
		expect(docFrostingResource.get(goid1, null, restrictionIncluded, null, false)).andReturn(docFrosting1).once();		
		expect(docFrostingResource.get(goid2, null, restrictionIncluded, null, false)).andReturn(docFrosting2).once();

		expect(docFrosting1.getStatus()).andReturn(new String("DELETE")).atLeastOnce();
		expect(docFrosting2.getStatus()).andReturn(new String("DELETE")).atLeastOnce();
		
		replayAll();
		
		target = new PubMediaInfoProvider(
				restrictionIncluded,
				relatedIdsResource,
				docFrostingResource,
				pdfMediaInfoFactory);
		
		pubMediaInfo = target.makeFor(pubName);
		verifyAll();
	}
	
	void prepareDocforstingStatus_OneNormal_OneDelete() throws Exception {
		relatedIds = new RelatedIds();
		relatedIds.setRelatedId(new RelatedId[] { relatedId1, relatedId2 });
		
		expect(relatedIdsResource.get(
				PubMediaInfoProvider.kDissertationRelatedIdType,
				pubName)).andReturn(relatedIds).once();
		
		expect(docFrostingResource.get(goid1, null, restrictionIncluded, null, false)).andReturn(docFrosting1).once();		
		expect(docFrostingResource.get(goid2, null, restrictionIncluded, null, false)).andReturn(docFrosting2).once();

		expect(docFrosting1.getStatus()).andReturn(new String("DELETE")).atLeastOnce();
		expect(docFrosting2.getStatus()).andReturn(new String("NORMAL")).atLeastOnce();
		
		
		expect(pdfMediaInfoFactory.makeFrom(docFrosting2)).andReturn(
				Lists.newArrayList(pdfMediaInfo2A, pdfMediaInfo2B)).once();
		
		replayAll();
		
		target = new PubMediaInfoProvider(
				restrictionIncluded,
				relatedIdsResource,
				docFrostingResource,
				pdfMediaInfoFactory);
		
		pubMediaInfo = target.makeFor(pubName);
		verifyAll();
	}
	
	void prepareDocforstingStatus_TwoDelete_OneNormal() throws Exception {
		relatedIds = new RelatedIds();
		relatedIds.setRelatedId(new RelatedId[] { relatedId1, relatedId2, relatedId3 });
		
		expect(relatedIdsResource.get(
				PubMediaInfoProvider.kDissertationRelatedIdType,
				pubName)).andReturn(relatedIds).once();
		
		expect(docFrostingResource.get(goid1, null, restrictionIncluded, null, false)).andReturn(docFrosting1).once();		
		expect(docFrostingResource.get(goid2, null, restrictionIncluded, null, false)).andReturn(docFrosting2).once();
		expect(docFrostingResource.get(goid3, null, restrictionIncluded, null, false)).andReturn(docFrosting3).once();

		expect(docFrosting1.getStatus()).andReturn(new String("DELETE")).atLeastOnce();
		expect(docFrosting2.getStatus()).andReturn(new String("DELETE")).atLeastOnce();
		expect(docFrosting3.getStatus()).andReturn(new String("NORMAL")).atLeastOnce();
		
		
		expect(pdfMediaInfoFactory.makeFrom(docFrosting3)).andReturn(
				Lists.newArrayList(pdfMediaInfo3A)).once();
		
		replayAll();
		
		target = new PubMediaInfoProvider(
				restrictionIncluded,
				relatedIdsResource,
				docFrostingResource,
				pdfMediaInfoFactory);
		
		pubMediaInfo = target.makeFor(pubName);
		verifyAll();
	}
	
	void prepareDocforstingStatus_OneDelete_OneHold_OneNormal() throws Exception {
		relatedIds = new RelatedIds();
		relatedIds.setRelatedId(new RelatedId[] { relatedId1, relatedId2, relatedId3 });
		
		expect(relatedIdsResource.get(
				PubMediaInfoProvider.kDissertationRelatedIdType,
				pubName)).andReturn(relatedIds).once();
		
		expect(docFrostingResource.get(goid1, null, restrictionIncluded, null, false)).andReturn(docFrosting1).once();		
		expect(docFrostingResource.get(goid2, null, restrictionIncluded, null, false)).andReturn(docFrosting2).once();
		expect(docFrostingResource.get(goid3, null, restrictionIncluded, null, false)).andReturn(docFrosting3).once();

		expect(docFrosting1.getStatus()).andReturn(new String("DELETE")).once();
		expect(docFrosting2.getStatus()).andReturn(new String("HOLD")).once();
		expect(docFrosting3.getStatus()).andReturn(new String("NORMAL")).once();
		
		
		expect(pdfMediaInfoFactory.makeFrom(docFrosting3)).andReturn(
				Lists.newArrayList(pdfMediaInfo3A)).once();
		
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
	public void hasCorrect_GoidCount_WithOneNormalStatus() throws Exception {
		prepareDocforstingStatus_OneNormal();
		assertThat(pubMediaInfo.getGoidMediaInfo().size(), is(1));
		
		GoidMediaInfo goidMediaInfo1 = pubMediaInfo.getGoidMediaInfo().get(0);
		assertThat(goidMediaInfo1.getGoid(), is(goid1));
	}
	
	@Test
	public void hasCorrect_GoidCount_WithAllNormalStatus() throws Exception {
		prepareDocforstingStatus_AllNormal();
		assertThat(pubMediaInfo.getGoidMediaInfo().size(), is(2));
		
		GoidMediaInfo goidMediaInfo1 = pubMediaInfo.getGoidMediaInfo().get(0);
		assertThat(goidMediaInfo1.getGoid(), is(goid1));
		
		GoidMediaInfo goidMediaInfo2 = pubMediaInfo.getGoidMediaInfo().get(1);
		assertThat(goidMediaInfo2.getGoid(), is(goid2));
		
	}
	
	@Test
	public void hasCorrect_GoidCount_WithAllDeleteStatus() throws Exception {
		prepareDocforstingStatus_AllDelete();
		assertThat(pubMediaInfo.getGoidMediaInfo().size(), is(0));
	}
	
	@Test
	public void hasCorrect_GoidCount_With_OneNormal_OneDelete_Status() throws Exception {
		prepareDocforstingStatus_OneNormal_OneDelete();
		assertThat(pubMediaInfo.getGoidMediaInfo().size(), is(1));
		
		GoidMediaInfo goidMediaInfo2 = pubMediaInfo.getGoidMediaInfo().get(0);
		assertThat(goidMediaInfo2.getGoid(), is(goid2));
	}
	
	@Test
	public void hasCorrect_GoidCount_With_TwoDelete_OneNormal_Status() throws Exception {
		prepareDocforstingStatus_TwoDelete_OneNormal();
		assertThat(pubMediaInfo.getGoidMediaInfo().size(), is(1));
		
		GoidMediaInfo goidMediaInfo = pubMediaInfo.getGoidMediaInfo().get(0);
		assertThat(goidMediaInfo.getGoid(), is(goid3));
	}
	
	@Test
	public void hasCorrect_GoidCount_With_OneDelete_OneHold_OneNormal_Status() throws Exception {
		prepareDocforstingStatus_TwoDelete_OneNormal();
		assertThat(pubMediaInfo.getGoidMediaInfo().size(), is(1));
		
		GoidMediaInfo goidMediaInfo = pubMediaInfo.getGoidMediaInfo().get(0);
		assertThat(goidMediaInfo.getGoid(), is(goid3));
	}
}
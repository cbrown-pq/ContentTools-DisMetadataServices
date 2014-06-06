package com.proquest.mtg.dismetadataservice.media;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class PubMediaInfo_HasPdf_Tests extends EasyMockSupport {
	static final String pubId = "1234567";
	GoidMediaInfo goidMedaiInfo1;
	GoidMediaInfo goidMedaiInfo2;
	GoidMediaInfo goidMedaiInfo3;
	List<GoidMediaInfo> goidMediaInfoList;
	PubMediaInfo target;
	
	@Before
	public void setUp() throws Exception {
		goidMedaiInfo1 = createMock(GoidMediaInfo.class);
		goidMedaiInfo2 = createMock(GoidMediaInfo.class);
		goidMedaiInfo3 = createMock(GoidMediaInfo.class);
	}
	
	@Test
	public void with_NoGoidMediaInfo_ReturnsFalse() throws Exception {
		goidMediaInfoList = Lists.newArrayList();
		target = new PubMediaInfo(pubId, goidMediaInfoList);
		assertThat(target.hasPdf(), is(false));
	}
	
	@Test
	public void with_SeveralGoidMediaInfo_WithNoPdfs_ReturnsFalse() throws Exception {
		expect(goidMedaiInfo1.hasPdf()).andStubReturn(false);
		expect(goidMedaiInfo2.hasPdf()).andStubReturn(false);
		expect(goidMedaiInfo3.hasPdf()).andStubReturn(false);
		
		replayAll();
		
		goidMediaInfoList = Lists.newArrayList(goidMedaiInfo1, goidMedaiInfo2, goidMedaiInfo3);
		target = new PubMediaInfo(pubId, goidMediaInfoList);
		assertThat(target.hasPdf(), is(false));
		
		verifyAll();
	}
	
	@Test
	public void with_SeveralGoidMediaInfo_WithPdfs_ReturnsTrue() throws Exception {
		expect(goidMedaiInfo1.hasPdf()).andStubReturn(false);
		expect(goidMedaiInfo2.hasPdf()).andStubReturn(true);
		expect(goidMedaiInfo3.hasPdf()).andStubReturn(false);
		
		replayAll();
		
		goidMediaInfoList = Lists.newArrayList(goidMedaiInfo1, goidMedaiInfo2, goidMedaiInfo3);
		target = new PubMediaInfo(pubId, goidMediaInfoList);
		assertThat(target.hasPdf(), is(true));
		
		verifyAll();
	}
	
	@Test
	public void with_SeveralGoidMediaInfo_WithAllPdfs_ReturnsTrue() throws Exception {
		expect(goidMedaiInfo1.hasPdf()).andStubReturn(true);
		expect(goidMedaiInfo2.hasPdf()).andStubReturn(true);
		expect(goidMedaiInfo3.hasPdf()).andStubReturn(true);
		
		replayAll();
		
		goidMediaInfoList = Lists.newArrayList(goidMedaiInfo1, goidMedaiInfo2, goidMedaiInfo3);
		target = new PubMediaInfo(pubId, goidMediaInfoList);
		assertThat(target.hasPdf(), is(true));

		verifyAll();
	}
}

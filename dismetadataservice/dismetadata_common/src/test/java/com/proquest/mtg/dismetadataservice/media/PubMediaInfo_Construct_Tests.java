package com.proquest.mtg.dismetadataservice.media;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class PubMediaInfo_Construct_Tests {
	String pubId;
	GoidMediaInfo mediaInfo1;
	GoidMediaInfo mediaInfo2;
	PubMediaInfo target;
	
	@Before
	public void setUp() throws Exception {
		pubId = "1234567";
		mediaInfo1 = EasyMock.createMock(GoidMediaInfo.class);
		mediaInfo2 = EasyMock.createMock(GoidMediaInfo.class);
		target = new PubMediaInfo(
				pubId,
				Lists.newArrayList(
						mediaInfo1,
						mediaInfo2));
	}
	
	@Test
	public void hasCorrect_PubId() throws Exception {
		assertThat(target.getPubId(), is(pubId));
	}
	
	@Test
	public void hasCorrect_MediaInfo() throws Exception {
		assertThat(target.getGoidMediaInfo().size(), is(2));
		assertThat(target.getGoidMediaInfo().get(0), sameInstance(mediaInfo1));
		assertThat(target.getGoidMediaInfo().get(1), sameInstance(mediaInfo2));
	}
	
	@Test
	public void coverageTests() throws Exception {
		assertThat(target.toString(), notNullValue());
	}
}

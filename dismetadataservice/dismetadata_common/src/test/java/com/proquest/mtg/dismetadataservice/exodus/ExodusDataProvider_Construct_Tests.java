package com.proquest.mtg.dismetadataservice.exodus;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

public class ExodusDataProvider_Construct_Tests {
	
	IPubMetaDataProvider pubMetaDataProvider;
	
	ExodusDataProvider target;
	
	@Before
	public void setUp() throws Exception {
		pubMetaDataProvider = EasyMock.createMock(IPubMetaDataProvider.class);
		target = new ExodusDataProvider(pubMetaDataProvider);
	}
	
	@Test
	public void has_Correct_PubMetaDataProvider() {
		assertThat(target.getPubMetaDataProvider(), sameInstance(pubMetaDataProvider));
	}
	

}

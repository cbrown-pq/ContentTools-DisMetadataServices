package com.proquest.mtg.dismetadataservice.exodus;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.metadata.DisGenMappingProvider;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

public class ExodusDataProvider_Construct_Tests {
	
	IPubMetaDataProvider pubMetaDataProvider;
	DisGenMappingProvider disGenMappingProvider;
	
	ExodusDataProvider target;
	
	@Before
	public void setUp() throws Exception {
		pubMetaDataProvider = EasyMock.createMock(IPubMetaDataProvider.class);
		disGenMappingProvider = EasyMock.createMock(DisGenMappingProvider.class);
		target = new ExodusDataProvider(pubMetaDataProvider, disGenMappingProvider);
	}
	
	@Test
	public void has_Correct_PubMetaDataProvider() {
		assertThat(target.getPubMetaDataProvider(), sameInstance(pubMetaDataProvider));
	}
	
	@Test
	public void has_Correct_DisGenMappingProvider() {
		assertThat(target.getDisGenMappingProvider(), sameInstance(disGenMappingProvider));
	}
	

}

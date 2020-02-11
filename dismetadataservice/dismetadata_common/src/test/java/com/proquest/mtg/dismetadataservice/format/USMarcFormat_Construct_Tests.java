package com.proquest.mtg.dismetadataservice.format;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.datasource.IMarcProvider;

public class USMarcFormat_Construct_Tests {
	
	IMarcProvider marcDataProvider;
	USMarcFormat target;
	
	@Before
	public void setUp() throws Exception {
		marcDataProvider = EasyMock.createMock(IMarcProvider.class);
		target = new USMarcFormat(marcDataProvider);
		
	}
	
	@Test
	public void hasCorrect_MarcDataProvider() throws Exception {
		assertThat(target.getMarcDataProvider(), sameInstance(marcDataProvider));
	}
}

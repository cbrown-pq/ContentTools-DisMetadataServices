package com.proquest.mtg.dismetadataservice.format;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.IMarcProvider;

public class MarcXmlFormat_Construction_Tests {
	IMarcProvider marcDataProvider;
	MarcXmlFormat target;
	
	@Before
	public void setUp() throws Exception {
		marcDataProvider = EasyMock.createMock(IMarcProvider.class);
		target = new MarcXmlFormat(marcDataProvider);
		
	}
	
	@Test
	public void hasCorrect_MarcDataProvider() throws Exception {
		assertThat(target.getMarcDataProvider(), sameInstance(marcDataProvider));
	}
}

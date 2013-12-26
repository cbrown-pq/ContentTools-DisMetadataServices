package com.proquest.mtg.dismetadataservice.format;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.IMarcProvider;
import com.proquest.mtg.utils.writer.IWriter;

public class USMarcFormat_Construct_Tests {
	
	IMarcProvider marcDataProvider;
	IWriter marcDataWriter;
	USMarcFormat target;
	
	@Before
	public void setUp() throws Exception {
		marcDataProvider = EasyMock.createMock(IMarcProvider.class);
		marcDataWriter = EasyMock.createMock(IWriter.class);
		target = new USMarcFormat(marcDataProvider, marcDataWriter);
		
	}
	
	@Test
	public void hasCorrect_MarcDataProvider() throws Exception {
		assertThat(target.getMarcDataProvider(), sameInstance(marcDataProvider));
	}
	
	@Test
	public void hasCorrect_MarcDataWriter() throws Exception {
		assertThat(target.getMarcDataWriter(), sameInstance(marcDataWriter));
	}
	

}

package com.proquest.mtg.dismetadataservice.format;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.IMarcProvider;
import com.proquest.mtg.dismetadataservice.utils.MarcXMLSchemaValidator;

public class MarcXmlFormat_Construction_Tests {
	IMarcProvider marcDataProvider;
	MarcXMLSchemaValidator marcXMLSchemaValidator;
	MarcXmlFormat target;
	
	@Before
	public void setUp() throws Exception {
		marcDataProvider = EasyMock.createMock(IMarcProvider.class);
		marcXMLSchemaValidator = EasyMock.createMock(MarcXMLSchemaValidator.class);
		target = new MarcXmlFormat(marcDataProvider, marcXMLSchemaValidator);
		
	}
	
	@Test
	public void hasCorrect_MarcDataProvider() throws Exception {
		assertThat(target.getMarcDataProvider(), sameInstance(marcDataProvider));
	}
	
	@Test
	public void hasCorrect_MarcXmlSchemaValidator() throws Exception {
		assertThat(target.getMarcXMLSchemaValidator(), 
				sameInstance(marcXMLSchemaValidator));
	}
}

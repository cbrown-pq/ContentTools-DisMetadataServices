package com.proquest.mtg.dismetadataservice.exodus;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;
import com.proquest.mtg.dismetadataservice.metadata.DisGenMappingProvider;
import com.proquest.mtg.dismetadataservice.metadata.PlainTextNormalizer;

public class ExodusDataProvider_Construct_Tests extends EasyMockSupport {
	
	IPubMetaDataProvider pubMetaDataProvider;
	DisGenMappingProvider disGenMappingProvider;
	PlainTextNormalizer plainTextNormalizer;
	PDFVaultAvailableStatusProvider pdfVaultAvailableStatusProvider;
	
	ExodusDataProvider target;
	
	@Before
	public void setUp() throws Exception {
		pubMetaDataProvider = createMock(IPubMetaDataProvider.class);
		disGenMappingProvider = createMock(DisGenMappingProvider.class);
		plainTextNormalizer = createMock(PlainTextNormalizer.class);
		pdfVaultAvailableStatusProvider = createMock(PDFVaultAvailableStatusProvider.class);
		target = new ExodusDataProvider(pubMetaDataProvider, disGenMappingProvider, 
								plainTextNormalizer, pdfVaultAvailableStatusProvider);
	}
	
	@Test
	public void has_Correct_PubMetaDataProvider() {
		assertThat(target.getPubMetaDataProvider(), sameInstance(pubMetaDataProvider));
	}
	
	@Test
	public void has_Correct_DisGenMappingProvider() {
		assertThat(target.getDisGenMappingProvider(), sameInstance(disGenMappingProvider));
	}
	
	@Test
	public void has_Correct_PlainTextNormalizer() {
		assertThat(target.getPlainTextNormalizer(), sameInstance(plainTextNormalizer));
	}
	
	@Test
	public void has_Correct_PdfVaultAvailableStatusProvider() {
		assertThat(target.getPDFVaultAvailableStatusProvider(), sameInstance(pdfVaultAvailableStatusProvider));
	}
}

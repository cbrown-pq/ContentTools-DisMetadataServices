package com.proquest.mtg.dismetadataservice.marc21rda;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.easymock.EasyMockSupport;
import org.junit.Before;

import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
//import com.proquest.mtg.dismetadataservice.jdbc.JdbcHelper;
import com.proquest.mtg.dismetadataservice.marc.MarcField;
import com.proquest.mtg.dismetadataservice.marc.MarcRecord;
import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;
import com.proquest.mtg.dismetadataservice.metadata.DisGenMappingProvider;

public class Marc21RdaRecordFactory_Test_Helper extends EasyMockSupport {

	
	static int kDataIndependentFieldCount = 7;
	IJdbcConnectionPool connectionPool;
	DisGenMappingProvider disGenMappingProvider;
	
	static final String kPubForPdfAvailable = "PubHasPdf";
	static final String kPubForPdfNotAvailable = "PubHasNoPdf";
	PDFVaultAvailableStatusProvider pdfVaultAvailableStatusProvider;
	
	Marc21RdaRecordFactory factory;
	
	@Before
	public void setUp() throws Exception {
		//IJdbcConnectionPool connectionPool = JdbcHelper.makePoolForExodusUnitTest();
		DisGenMappingProvider disGenMappingProvider = new DisGenMappingProvider();
		factory = new Marc21RdaRecordFactory(disGenMappingProvider);
		pdfVaultAvailableStatusProvider = createMock(PDFVaultAvailableStatusProvider.class);
	}
	
	public void verifyMarcRecordHasCorrectCount(DisPubMetaData metaData, 
			String tag, String expectedMarcFieldData, int dataSpecificFieldCount) {
		MarcRecord marc = factory.makeFrom(metaData);
		assertThat(marc.getFieldCount(), is(dataSpecificFieldCount + kDataIndependentFieldCount));
	}
	
	public void verifyMarcRecordHasCorrectField(DisPubMetaData metaData, 
			String tag, String expectedMarcFieldData, int dataSpecificFieldCount) {
		
		try {
			expect(pdfVaultAvailableStatusProvider.isPdfAvailableInVaultFor(kPubForPdfAvailable)).andStubReturn(true);
			expect(pdfVaultAvailableStatusProvider.isPdfAvailableInVaultFor(kPubForPdfNotAvailable)).andStubReturn(false);
			expect(pdfVaultAvailableStatusProvider.isPdfAvailableInVaultFor(null)).andStubReturn(false);
			replayAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(1));
		assertThat(fieldsMatchingTag.get(0).getData(), is(expectedMarcFieldData));
	}
	
	public void verifyMarcRecordHasEmptyField(DisPubMetaData metaData, String tag) {
		MarcRecord marc = factory.makeFrom(metaData);
		List<MarcField> fieldsMatchingTag = marc.getFieldsMatchingTag(tag); 
		assertThat(fieldsMatchingTag.size(), is(0));
	}


}

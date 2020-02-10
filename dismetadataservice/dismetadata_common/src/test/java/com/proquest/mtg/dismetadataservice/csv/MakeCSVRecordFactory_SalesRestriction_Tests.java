package com.proquest.mtg.dismetadataservice.csv;

import java.util.List;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.SalesRestriction;
import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;

public class MakeCSVRecordFactory_SalesRestriction_Tests extends EasyMockSupport  {
	CSVRecordFactory factory;
	DisPubMetaData metadata;
	List<SalesRestriction> salesRestrictions;
	PDFVaultAvailableStatusProvider pdfVaultAvailableStatus;

	@Before
	public void setUp() throws Exception {
		pdfVaultAvailableStatus  =  createMock(PDFVaultAvailableStatusProvider.class);
		factory = new CSVRecordFactory(pdfVaultAvailableStatus,0,0);
	}

	@Test
	public void makeWithEmptySalesRestrictions() throws Exception {
		metadata = new DisPubMetaData();
		salesRestrictions = Lists.newArrayList();
		metadata.setSalesRestrictions(salesRestrictions);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSalesRestrictionCode, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSalesRestrictionDesc, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSalesRestrictionStartDate, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSalesRestrictionEndDate, null);
	}

	@Test
	public void makeWithEmptySalesRestrictionDescription() throws Exception {
		metadata = new DisPubMetaData();
		salesRestrictions = Lists.newArrayList();
		SalesRestriction salesRestriction = new SalesRestriction();
		salesRestriction.setCode("SalesRestrictionCode");
		salesRestrictions.add(salesRestriction);
		metadata.setSalesRestrictions(salesRestrictions);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSalesRestrictionCode, "SalesRestrictionCode");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSalesRestrictionDesc, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSalesRestrictionStartDate, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSalesRestrictionEndDate, "NONE");
	}

	@Test
	public void makeWithEmptySalesRestrictionCode() throws Exception {
		metadata = new DisPubMetaData();
		salesRestrictions = Lists.newArrayList();
		SalesRestriction salesRestriction = new SalesRestriction();
		salesRestriction.setDescription("SalesRestrictionDescription");
		salesRestrictions.add(salesRestriction);
		metadata.setSalesRestrictions(salesRestrictions);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSalesRestrictionCode, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSalesRestrictionDesc, "SalesRestrictionDescription");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSalesRestrictionStartDate, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSalesRestrictionEndDate, "NONE");
	}

	@Test
	public void withOnlySalesRestrictions() throws Exception {
		DisPubMetaData metadata = new DisPubMetaData();
		SalesRestriction salesRestriction1 = new SalesRestriction();
		salesRestriction1.setCode("1");
		salesRestriction1.setDescription("Not Available For Sale");
		SalesRestriction salesRestriction2 = new SalesRestriction();
		salesRestriction2.setCode("2");
		salesRestriction2.setDescription("Available");
		List<SalesRestriction> salesRestrictions = Lists.newArrayList(
				salesRestriction1, salesRestriction2);
		metadata.setSalesRestrictions(salesRestrictions);
		String csvData = factory.makeFrom(metadata);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSalesRestrictionCode, "1|2");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSalesRestrictionDesc, "Not Available For Sale|Available");
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSalesRestrictionStartDate, null);
		CSVTestHelper.assertValueForHeader(csvData, CSVHeaders.kSalesRestrictionEndDate, "NONE|NONE");
	}

}

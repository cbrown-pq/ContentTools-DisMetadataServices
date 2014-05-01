package com.proquest.mtg.dismetadataservice.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SalesRestriction;

public class MakeCSVRecordFactory_SalesRestriction_Tests {
	CSVRecordFactory factory;
	String header = "";
	DisPubMetaData metadata;
	List<SalesRestriction> salesRestrictions;

	@Before
	public void setUp() throws Exception {
		factory = new CSVRecordFactory();
		for (String curheader : factory.getHeaders()) {
			header += curheader + ",";
		}
	}

	@Test
	public void makeWithEmptySalesRestrictions() throws Exception {
		metadata = new DisPubMetaData();
		salesRestrictions = Lists.newArrayList();
		metadata.setSalesRestrictions(salesRestrictions);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void makeWithEmptySalesRestrictionDescription() throws Exception {
		metadata = new DisPubMetaData();
		salesRestrictions = Lists.newArrayList();
		SalesRestriction salesRestriction = new SalesRestriction();
		salesRestriction.setCode("SalesRestrictionCode");
		salesRestrictions.add(salesRestriction);
		metadata.setSalesRestrictions(salesRestrictions);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"N\"" + ",,,,,," + ",\"" + "SalesRestrictionCode" + "\""
				+ ",,,,,,,\"N\",,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void makeWithEmptySalesRestrictionCode() throws Exception {
		metadata = new DisPubMetaData();
		salesRestrictions = Lists.newArrayList();
		SalesRestriction salesRestriction = new SalesRestriction();
		salesRestriction.setDescription("SalesRestrictionDescription");
		salesRestrictions.add(salesRestriction);
		metadata.setSalesRestrictions(salesRestrictions);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"N\"" + ",,,,,," + ",,\"" + "SalesRestrictionDescription"
				+ "\"" + ",,,,,,\"N\",,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
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
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,,,,,"
				+ ",\"N\"" + ",,,,,," + ",\"" + "1|2" + "\"" + ",\""
				+ "Not Available For Sale|Available" + "\"" + ",,,,,,\"N\",,,,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

}

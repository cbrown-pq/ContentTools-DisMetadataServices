package com.proquest.mtg.dismetadataservice.csv;

import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;
import com.proquest.mtg.dismetadataservice.jdbc.JdbcHelper;
import static org.hamcrest.CoreMatchers.is;

public class MakeCSVRecordForPubMetaData_Tests {
	static int kDataIndependentFieldCount = 3;
	IJdbcConnectionPool connectionPool;
	CSVRecordFactory factory;

	@Before
	public void setUp() throws Exception {
		IJdbcConnectionPool connectionPool = JdbcHelper.makePoolForExodusProd();
		factory = new CSVRecordFactory();
	}

	@Test(expected = Exception.class)
	public void with_Null_Throws() throws Exception {
		factory.makeFrom(null);
	}

	@Test
	public void with_Emtpy() throws Exception {
		DisPubMetaData empty = new DisPubMetaData();
		String expectedCSVData = "PUB NUMBER,VOLUME ISSUE,ADVISORS,ISBN,PAGE NUMBER,PAGE COUNT,PUBLISHER,REFERENCE,BL NUMBER,EXTERNAL URL,DISS LANGUAGE DESCRIPTION,DISS LANGUAGE CODE,SCHOOL CODE,SCHOOL NAME,SCHOOL COUNTRY,SCHOOL STATE,TITLE,ENGLISH TITLE,VARIANT TITLE,AUTHORS,DEGREE,DEGREE YEAR,DEGREE CODE,ABSTRACT,SUBJ DESC,SUBJ CODE,SUBJ GROUP DESC,HAS SUPP FILES,SUPPLEMENTAL FILE NAMES,SUPPLEMENTAL FILE DESCRIPTION,SUPPLEMENTAL FILE CATEGORY,DEPARTMENT,KEYWORD,KEYWORD SOURCE,SALES RESTRICTION CODE,SALES RESTRICTION DESC,SALES RESTRICTION STARTDT,SALES RESTRICTION ENDDT,DISS TYPE CODE,DISS CODE,DAI SECTION CODE,PDF AVAILABLE,PDF AVAILABLE DATE,FORMAT RESTRICTION CODE,\n,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(empty);
		assertThat(csvData, is(expectedCSVData));
	}
}

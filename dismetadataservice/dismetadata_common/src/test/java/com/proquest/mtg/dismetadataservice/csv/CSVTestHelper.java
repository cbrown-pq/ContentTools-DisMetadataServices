package com.proquest.mtg.dismetadataservice.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

/**
 * Helper class used for the testing of CSV file generation
 * @author JKassal
 */
public class CSVTestHelper {
	private static CSVFormat FORMAT = CSVFormat.DEFAULT.withHeader().withNullString("");
	
	/**
	 * Retrieve the value in the column specified by the given header from
	 * the first non-header record found in the given CSV string.
	 * 
	 * @param csvData The CSV file to check in String form. Assumed to be
	 * in the format specified by CSVFormat.DEFAULT with a header record
	 * @param csvHeader The {@link CSVHeaders} header that defines which column to access
	 * @return Value at the specified column, or null if the value is blank
	 * @throws Exception on failure to parse csvData
	 * @see org.apache.commons.csv.CSVFormat
	 */
	public static String getValueForHeader(@Nonnull String csvData, @Nonnull String csvHeader) throws Exception {
		try (CSVParser parser = CSVParser.parse(csvData, FORMAT)) {
			CSVRecord record = parser.getRecords().get(0);
			return record.get(csvHeader);
		}
	}
	
	/**
	 * Asserts that the value from {@link #getValueForHeader(String, String)} matches testValue
	 * 
	 * @param csvData CSV in String form
	 * @param csvHeader Header in given CSV to check under
	 * @param testValue Value to compare to (null and "" are treated equally)
	 * @throws Exception On failure to parse csvData
	 */
	public static void assertValueForHeader(String csvData, String csvHeader, String testValue) throws Exception {
		String value = getValueForHeader(csvData, csvHeader);
		if (StringUtils.isNotEmpty(testValue))
			assertThat(value, is(testValue));
		else
			assertThat(value, is(nullValue()));
	}
	
	/**
	 * <strong>NOTE:</strong> Only used for <strong>developing</strong> CSV tests.<br><br>
	 * Prints the non-empty header/value pairs in the given CSV and returns the number of said pairs.
	 * 
	 * @param csvData CSV in String form
	 * @return The total count of non-empty header/value pairs
	 * @throws IOException on failure to parse csvData
	 */
	public static int countNonEmpty(String csvData) throws IOException {
		int count = 0;
		try (CSVParser parser = CSVParser.parse(csvData, FORMAT)) {
			CSVRecord record = parser.getRecords().get(0);
			Map<String, String> marp = record.toMap();
			Set<String> headers = marp.keySet();
			for (String header : headers) {
				String val = marp.get(header);
				if (StringUtils.isNotEmpty(val)) {
					count++;
					System.out.println(header + ": " + val);
				}
			}
			
		}
		
		return count;
	}

}

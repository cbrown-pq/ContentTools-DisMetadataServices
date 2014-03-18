package com.proquest.mtg.dismetadataservice.marc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.google.common.collect.Lists;

public class MarcParser {

	public static final String kFieldDataEnCoding = "UTF-8";
	public static final char kMarcRecordDelimChar = (char) 0x19;

	public static final int kLeaderByteCount = 24;
	public static final int kTerminatorByteCount = 1;
	public static final int kDirectoryEntryByteCount = 12;

	public static final int kRecordLengthByteOffset = 0;
	public static final int kRecordLengthByteCount = 5;

	public static final int kStatusByteOffset = 5;
	public static final int kTypeByteOffset = 6;
	public static final int kLevelByteOffset = 7;
	public static final int kCharacterEncodingOffset = 9;

	public static final int kBaseAddressOfFieldDataOffset = 12;
	public static final int kBaseAddressOfFieldDataByteCount = 5;

	public static final int kDirectoryEntryTagByteCount = 3;
	public static final int kDirectoryEntryFieldLengthByteCount = 4;
	public static final int kDirectoryEntryFieldAddressByteCount = 5;

	public static final DecimalFormat k4DigitFormatter = new DecimalFormat(
			"0000");
	public static final DecimalFormat k5DigitFormatter = new DecimalFormat(
			"00000");

	public static final char kLeaderNotUsedFlag = ' ';
	public static final char kLeaderIndicatorCount = '2';
	public static final char kLeaderDelimiterCount = '2';
	public static final int kEncodingLevel = 17;
	public static final int kDescriptiveCataloging = 18;
	public static final String kEntryMap = "4500";

	public MarcRecord read(File marcFile) throws IOException,
			MarcParserException {
		StringWriter dest = new StringWriter();
		IOUtils.copy(new FileInputStream(marcFile), dest);
		return this.read(dest.toString());
	}

	public MarcRecord read(String marcString) throws MarcParserException,
			UnsupportedEncodingException {
		verifyLength(marcString);
		return new MarcRecord(getStatusFrom(marcString),
				getTypeFrom(marcString), getLevelFrom(marcString),
				getCharacterEncodingFrom(marcString),getEncodingLevel(marcString),getDescriptiveCataloging(marcString), getFieldsFrom(marcString));
	}

	
	public String write(MarcRecord marcRecord)
			throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		writeLeader(result, marcRecord);
		writeDirectory(result, marcRecord);
		result.append(MarcCharSet.kFieldTerminator);
		writeFields(result, marcRecord);
		result.append(MarcCharSet.kRecordTerminator);
		result.append(kMarcRecordDelimChar);
		return result.toString();
	}

	private void verifyLength(String marcString) throws MarcParserException,
			UnsupportedEncodingException {
		int lengthInByte = marcString.getBytes(kFieldDataEnCoding).length;
		if (null == marcString || lengthInByte < kLeaderByteCount) {
			throw new IllegalArgumentException("Invalid marc data");
		}
		String lengthStr = marcString.substring(0, kRecordLengthByteCount);
		int recordLength = Integer.parseInt(lengthStr);
		if (lengthInByte < recordLength) {
			throw new MarcParserException("Invalid marc data.  "
					+ "Expected length = " + recordLength + ", "
					+ "Actual Byte length = " + lengthInByte);
		}
	}

	private char getStatusFrom(String marcString) {
		return marcString.charAt(kStatusByteOffset);
	}

	private char getTypeFrom(String marcString) {
		return marcString.charAt(kTypeByteOffset);
	}

	private char getLevelFrom(String marcString) {
		return marcString.charAt(kLevelByteOffset);
	}

	private char getCharacterEncodingFrom(String marcString) {
		return marcString.charAt(kCharacterEncodingOffset);
	}

	private char getEncodingLevel(String marcString) {
		return marcString.charAt(kEncodingLevel);
	}
	
	private char getDescriptiveCataloging(String marcString) {
		return marcString.charAt(kDescriptiveCataloging);
	}
	
	private List<MarcField> getFieldsFrom(String marcString)
			throws NumberFormatException, UnsupportedEncodingException {
		List<MarcField> result = Lists.newArrayList();
		int baseAddressOfFieldData = getBaseAddressOfFieldDataFrom(marcString);
		int curByteOffset = kLeaderByteCount;
		while (marcString.length() >= (curByteOffset + kDirectoryEntryByteCount)
				&& MarcCharSet.kFieldTerminator != marcString
						.charAt(curByteOffset)) {

			int startIndex = curByteOffset;
			String tag = getSubstring(marcString, startIndex,
					kDirectoryEntryTagByteCount);

			startIndex += kDirectoryEntryTagByteCount;
			int fieldLength = Integer.parseInt(getSubstring(marcString,
					startIndex, kDirectoryEntryFieldLengthByteCount));

			startIndex += kDirectoryEntryFieldLengthByteCount;
			int fieldByteOffset = baseAddressOfFieldData
					+ Integer.parseInt(getSubstring(marcString, startIndex,
							kDirectoryEntryFieldAddressByteCount));
			String fieldData = getByteSubstring(marcString, fieldByteOffset,
					fieldLength);
			result.add(new MarcField(tag, fieldData));

			curByteOffset += kDirectoryEntryByteCount;
		}
		return result;
	}

	private int getBaseAddressOfFieldDataFrom(String marcString) {
		return Integer
				.parseInt(getSubstring(marcString,
						kBaseAddressOfFieldDataOffset,
						kBaseAddressOfFieldDataByteCount));
	}

	private void writeLeader(StringBuilder result, MarcRecord record)
			throws UnsupportedEncodingException {
		int directoryByteCount = calculateDirectoryByteCount(record);
		int baseAddressOfData = kLeaderByteCount + directoryByteCount;
		int fieldByteCount = calculateFieldByteCountFrom(record);
		int totalByteCount = kLeaderByteCount + directoryByteCount
				+ fieldByteCount + kTerminatorByteCount;

		result.append(k5DigitFormatter.format(totalByteCount)); // 0 - 4
		result.append(record.getStatus()); // 5
		result.append(record.getType()); // 6
		result.append(record.getLevel()); // 6
		result.append(kLeaderNotUsedFlag); // 8
		result.append(record.getCharacterEncoding()); // 9
		result.append(kLeaderIndicatorCount); // 10
		result.append(kLeaderDelimiterCount); // 11
		result.append(k5DigitFormatter.format(baseAddressOfData)); // 12 - 16
		result.append(record.getEncodingLevel()); // 17
		result.append(record.getDescriptiveCataloging()); // 18
		result.append(kLeaderNotUsedFlag); // 19
		result.append(kEntryMap); // 20 - 23
	}

	private int calculateDirectoryByteCount(MarcRecord record) {
		return kDirectoryEntryByteCount * record.getAllFields().size() + 1;
	}

	private int calculateFieldByteCountFrom(MarcRecord record)
			throws UnsupportedEncodingException {
		int result = 0;
		for (MarcField curField : record.getAllFields()) {
			result += curField.getDataWithTerminator().getBytes(
					kFieldDataEnCoding).length;
		}
		return result;
	}

	private void writeDirectory(StringBuilder result, MarcRecord record)
			throws UnsupportedEncodingException {
		int curByteOffset = 0;
		for (MarcField curField : record.getAllFields()) {
			result.append(curField.getTag());
			int curFieldByteLength = getByteLengthFor(curField
					.getDataWithTerminator());
			result.append(k4DigitFormatter.format(curFieldByteLength));
			result.append(k5DigitFormatter.format(curByteOffset));
			curByteOffset += curFieldByteLength;
		}
	}

	private void writeFields(StringBuilder result, MarcRecord record) {
		for (MarcField curField : record.getAllFields()) {
			result.append(curField.getDataWithTerminator());
		}
	}

	private static String getSubstring(String source, int startIndex, int length) {
		int endIndex = startIndex + length;
		if (endIndex >= source.length()) {
			endIndex = source.length() - 1;
		}
		return source.substring(startIndex, endIndex);
	}

	private static String getByteSubstring(String source, int startIndex,
			int length) throws UnsupportedEncodingException {
		int endIndex = startIndex + length;
		byte[] bytes = source.getBytes(kFieldDataEnCoding);
		String result = new String(Arrays.copyOfRange(bytes, startIndex,
				endIndex), kFieldDataEnCoding);
		return result;
	}

	private int getByteLengthFor(String x) throws UnsupportedEncodingException {
		return x.getBytes(kFieldDataEnCoding).length;
	}

}

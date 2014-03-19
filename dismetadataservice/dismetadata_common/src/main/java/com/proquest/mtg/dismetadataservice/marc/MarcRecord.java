package com.proquest.mtg.dismetadataservice.marc;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.proquest.mtg.dismetadataservice.metadata.PlainTextNormalizer;
import com.proquest.mtg.dismetadataservice.usmarc.USMarcRecordFactory;

public class MarcRecord {
	private final char status;
	private final char type;
	private final char level;
	private final char characterEncoding;
	private final TreeMap<String, List<MarcField>> fields;
	private final char encodingLevel;
	private final char descriptiveCataloging;

	public MarcRecord(char type, char encodingLevel, char descriptiveCataloging) {
		this('n', type, 'm', 'a', encodingLevel, descriptiveCataloging,
				new ArrayList<MarcField>());
	}

	public MarcRecord(Iterable<MarcField> fields) {
		this('n', 'a', 'm', 'a', '3', 'i', fields);
	}

	public MarcRecord(char status, char type, char level,
			char characterEncoding, char encodingLevel,
			char descriptiveCataloging, Iterable<MarcField> fields) {
		this.status = status;
		this.type = type;
		this.level = level;
		this.characterEncoding = characterEncoding;
		this.encodingLevel = encodingLevel;
		this.descriptiveCataloging = descriptiveCataloging;
		this.fields = Maps.newTreeMap();
		addFields(fields);
	}

	public char getStatus() {
		return status;
	}

	public char getType() {
		return type;
	}

	public char getLevel() {
		return level;
	}

	public char getCharacterEncoding() {
		return characterEncoding;
	}

	public char getEncodingLevel() {
		return encodingLevel;
	}

	public char getDescriptiveCataloging() {
		return descriptiveCataloging;
	}

	public int getFieldCount() {
		return getAllFields().size();
	}

	public List<MarcField> getAllFields() {
		List<MarcField> result = Lists.newArrayList();
		for (List<MarcField> curFieldsForTag : fields.values()) {
			result.addAll(curFieldsForTag);
		}
		return result;
	}

	public List<String> getAllTags() {
		return Lists.transform(getAllFields(),
				new Function<MarcField, String>() {
					public String apply(MarcField x) {
						return x.getTag();
					}
				});
	}

	public List<MarcField> getFieldsMatchingTag(final String tag) {
		List<MarcField> result = fields.get(tag);
		if (null == result) {
			result = Lists.newArrayList();
		}
		return result;
	}

	public MarcField getFirstFieldMatchingTag(final String tag) {
		List<MarcField> matchingFields = getFieldsMatchingTag(tag);
		return matchingFields.isEmpty() ? null : matchingFields.get(0);
	}

	public void addField(MarcField fieldToAdd) {
		String tag = fieldToAdd.getTag();
		List<MarcField> fieldsForTag = fields.get(tag);
		if (null == fieldsForTag) {
			fieldsForTag = Lists.newArrayList();
			fields.put(tag, fieldsForTag);
		}
		fieldsForTag.add(fieldToAdd);
	}

	public void addFields(Iterable<MarcField> fieldsToAdd) {
		for (MarcField fieldToAdd : fieldsToAdd) {
			addField(fieldToAdd);
		}
	}

	public void removeFieldsWithTag(String tag) {
		fields.remove(tag);
	}

	public void removeFieldsWithTags(Iterable<String> tags) {
		for (String tag : tags) {
			removeFieldsWithTag(tag);
		}
	}

	public String toMarcString() throws UnsupportedEncodingException {
		return new MarcParser().write(this);
	}

	public String getPubId() {
		String pubId = null;
		MarcField marcField = getFirstFieldMatchingTag(MarcTags.kRecId);
		if (null != marcField) {
			pubId = marcField.getData().trim();
			if (pubId.startsWith(USMarcRecordFactory.kRecordIdPrefix)) {
				pubId = pubId.substring(USMarcRecordFactory.kRecordIdPrefix
						.length());
			}
		}
		return pubId;
	}

	public void overrideWith(MarcRecord other) {
		for (String curOtherTag : other.getAllTags()) {
			this.removeFieldsWithTag(curOtherTag);
			this.addFields(other.getFieldsMatchingTag(curOtherTag));
		}
	}

	public List<MarcField> getFieldsWithIllegalChars(CharMatcher charMatcher) {
		List<MarcField> result = Lists.newArrayList();
		for (MarcField curField : getAllFields()) {
			if (curField.hasIllegalChars(charMatcher)) {
				result.add(curField);
			}
		}
		return result;
	}

	public void apply(PlainTextNormalizer plainTextNormalizer) {
		for (MarcField curField : getAllFields()) {
			curField.setData(plainTextNormalizer.applyTo(curField.getData()));
		}
	}

	// /////////////////////////////////////////////////////

	public static MarcRecord makeFrom(String marcString)
			throws MarcParserException, UnsupportedEncodingException {
		return new MarcParser().read(marcString);
	}

}

package com.proquest.mtg.dismetadataservice.marc;

import java.util.List;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Subject;
import com.proquest.mtg.dismetadataservice.metadata.TextNormalizer;

public class MarcRecordFactory {
	
	public static final String kRecordIdPrefix = "AAI";
	
	private final TextNormalizer abstractNormalizer = new TextNormalizer();
	
	private DisPubMetaData curMetaData = null;
	private MarcRecord curRecord = null;
	
	public MarcRecord makeFrom(DisPubMetaData metaData) {
		if (null == metaData) {
			throw new IllegalArgumentException("metaData is null");
		}
		curMetaData = metaData;
		curRecord = new MarcRecord();
		
		handleRecordId();
		handleAbstract();
		handleLocationOfCopy();
		handleSubjects();
		
		return curRecord;
	}

	private void handleRecordId() {
		String pubId = curMetaData.getPubNumber();
		if (null != pubId && !pubId.isEmpty()) {
			addField(MarcTags.kRecId, 
					kRecordIdPrefix + pubId.trim());
		}
	}
	
	private void handleAbstract() {
		String abstractText = curMetaData.getAbstract();
		if (null != abstractText && !abstractText.isEmpty()) {
			abstractText = abstractNormalizer.applyTo(abstractText);
			for (String curParagraph : makeAbstractParagraphsFrom(abstractText)) {
				curParagraph = endsWithPunctuationMark(curParagraph);
				addField(MarcTags.kAbstract,
						makeFieldDataFrom(' ', ' ', 'a', curParagraph));
			}
		}
	}

	private List<String> makeAbstractParagraphsFrom(String abstractText) {
		List<String> result = Lists.newArrayList();
		for (String curParagraph : abstractText.split("\\^|\n|<[pP]>|</[pP]>|<[pP][aA][rR]>|</[pP][aA][rR]>")) {
			if (null != curParagraph) {
				curParagraph = curParagraph.trim();
				if (!curParagraph.isEmpty()) {
					result.add(curParagraph);
				}
			}
		}
		return result;
	}
	
	private String endsWithPunctuationMark(String x) {
		return x.matches("^.+[\\.,\\?;:!]$") ? x : x + ".";
	}

	private void handleLocationOfCopy() {
		String locationOfCopy = curMetaData.getReferenceLocation();
		if (null != locationOfCopy && !locationOfCopy.isEmpty()) {
			locationOfCopy = endWithPeriod(locationOfCopy.trim());
			addField(MarcTags.kLocationOfCopy,
					makeFieldDataFrom('2', ' ', 'a', locationOfCopy));
		}
	}
	
	private void handleSubjects() {
		List<Subject> subjects = curMetaData.getSubjects(); 
		if (!subjects.isEmpty()) {
			for (Subject curSubject : subjects) {
				addSubjectTerm(curSubject.getSubjectDesc());
				addSubjectCode(curSubject.getSubjectCode());
			}
		}
	}

	private void addSubjectTerm(String term) {
		if (null != term && !term.isEmpty()) {
			term = endWithPeriod(term);
			addField(MarcTags.kSubjectTerm,
					makeFieldDataFrom(' ', '4', 'a', term));
		}
	}
	
	private void addSubjectCode(String code) {
		if (null != code && !code.isEmpty()) {
			addField(MarcTags.kSubjectCode,
					makeFieldDataFrom(' ', ' ', 'a', code));
		}
	}

	private String endWithPeriod(String x) {
		return x.endsWith(".") ? x : x + ".";
	}
	
	private void addField(String tag, String data) {
		curRecord.addField(new MarcField(tag, data));
	}
	
	private String makeFieldDataFrom(
			char dataFieldIndicator1,
			char dataFieldIndicator2,
			char subFieldIndicator,
			String fieldData) {
		StringBuilder builder = new StringBuilder();
		builder.append(dataFieldIndicator1);
		builder.append(dataFieldIndicator2);
		builder.append(MarcCharSet.kSubFieldIndicator);
		builder.append(subFieldIndicator);
		builder.append(fieldData);
		return builder.toString();
	}

}

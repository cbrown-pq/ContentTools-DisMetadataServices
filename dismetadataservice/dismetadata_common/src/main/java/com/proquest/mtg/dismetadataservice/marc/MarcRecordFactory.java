package com.proquest.mtg.dismetadataservice.marc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisor;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.CmteMember;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.DissLanguage;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Subject;
import com.proquest.mtg.dismetadataservice.metadata.Author.Degree;
import com.proquest.mtg.dismetadataservice.metadata.DisGenMappingProvider;
import com.proquest.mtg.dismetadataservice.metadata.DisGeneralMapping;
import com.proquest.mtg.dismetadataservice.metadata.SGMLEntitySubstitution;
import com.proquest.mtg.dismetadataservice.metadata.TextNormalizer;

public class MarcRecordFactory {

	public static final String kRecordIdPrefix = "AAI";
	public static final String kSystemControlNumberPrefix = "(MiAaPQ)AAI";
	public static final String kMarcMapping = "MARC_245_IND";
	public static final int kSingleLineTitleLength = 670;

	private final TextNormalizer abstractNormalizer = new TextNormalizer();

	private DisPubMetaData curMetaData = null;
	private MarcRecord curRecord = null;
	private final DisGenMappingProvider disGenMappingProvider;

	public MarcRecordFactory(DisGenMappingProvider disGenMappingProvider) {
		this.disGenMappingProvider = disGenMappingProvider;
	}

	public MarcRecord makeFrom(DisPubMetaData metaData) {

		curMetaData = metaData;
		curRecord = new MarcRecord();

		handleRecordId();
		handleAbstract();
		handleLocationOfCopy();
		handleSubjects();
		handleTimeStamp();
		handleISBN();
		handleSystemControlNumber();
		handleFixedLengthElements();
		handleTitle(); // 245
		handlePageCount(); // 300
		handleHostItemEntry();
		handleAdvisors();
		handleCommitteeMembers();
		handleSchoolCode();
		handleDegrees();
		handleDisserationLanguage();
		handleUrl();

		return curRecord;
	}

	private void handleRecordId() {
		String pubId = curMetaData.getPubNumber();
		if (null != pubId && !pubId.isEmpty()) {
			addField(MarcTags.kRecId, kRecordIdPrefix + pubId.trim());
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
		for (String curParagraph : abstractText
				.split("\\^|\n|<[pP]>|</[pP]>|<[pP][aA][rR]>|</[pP][aA][rR]>")) {
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

	private void handleFixedLengthElements() {
		Date now = new Date();
		String curTime = new SimpleDateFormat("YYMMdd").format(now);
		String degreeFlag;
		String degreeYear = curMetaData.getAuthors().get(0).getDegrees().get(0)
				.getDegreeYear();
		degreeFlag = (degreeYear == null ? "s" : "n");
		String fixedLengthElement = curTime + degreeFlag;
		if (null != degreeYear) {
			fixedLengthElement += degreeYear;
		}
		fixedLengthElement += "    ||||||||||||||||| ||";
		String LanguageCode = curMetaData.getDissLanguages().get(0)
				.getLanguageCode();
		fixedLengthElement += LanguageCodeToPartialLanguageName
				.getLanguageFor(LanguageCode) + " d";
		addField(MarcTags.kFiexedLengthDataElements, fixedLengthElement);
	}

	private void handleSystemControlNumber() {
		String pubId = curMetaData.getPubNumber();
		if (null != pubId && !pubId.isEmpty()) {
			addField(
					MarcTags.kSystemControlNumber,
					makeFieldDataFrom(' ', ' ', 'a', kSystemControlNumberPrefix
							+ pubId.trim()));

		}

	}

	private void handleISBN() {
		addField(
				MarcTags.kIsbn,
				makeFieldDataFrom(' ', ' ', 'a', curMetaData.getISBN()
						.replaceAll("-", "")));
	}

	private void handleTimeStamp() {
		Date now = new Date();
		String curTime = new SimpleDateFormat("yyyyMMddHHmmss.5").format(now);
		addField(MarcTags.kTransactionTimestamp, curTime);
	}

	private void handlePageCount() {
		addField(
				MarcTags.kPageCount,
				makeFieldDataFrom(' ', ' ', 'a', curMetaData.getPageCount()
						+ " p."));
	}

	private void handleHostItemEntry() {
		addField(
				MarcTags.kHostItemEntry,
				makeHostItemEntryFieldDataFrom('0', ' ', 't', curMetaData.getBatch().getDBTypeDesc(),'g', curMetaData.getBatch().getVolumeIssue(), curMetaData.getBatch().getDAISectionCode()+ "."));
	}

	
	private void handleAdvisors() {
		List<Advisor> dissAdvisors = curMetaData.getAdvisors();
		if (!curMetaData.getAdvisors().isEmpty() && curMetaData.getAdvisors() != null) {
			for (Advisor curAdvisor : dissAdvisors) {
				String adviserString = makeFieldDataFrom(' ', ',', 'e',
						"advisor");
				String adviserFirstName = null, adviserLastName = null;
				int firstBlankIndex = curAdvisor.getAdvisorFullName().indexOf(
						' ');
				if (firstBlankIndex >= 0) {
					adviserFirstName = curAdvisor.getAdvisorFullName()
							.substring(0, firstBlankIndex);
					adviserLastName = curAdvisor.getAdvisorFullName()
							.substring(firstBlankIndex + 1);
				}
				String adviserFullName = adviserLastName + "," + " "
						+ adviserFirstName;
				addField(
						MarcTags.kAdvisorname,
						makeFieldDataFrom('1', '0', 'a', adviserFullName,
								adviserString));
			}
		}
	}

	private void handleCommitteeMembers() {
		List<CmteMember> dissCmteMembers = curMetaData.getCmteMembers();
		if (!dissCmteMembers.isEmpty()) {
			for (CmteMember curCmteMember : dissCmteMembers) {
				String cmteMemberString = makeFieldDataFrom(' ', ',', 'e',
						"committee member");
				String cmteMemberName = curCmteMember.getLastName() + ", "
						+ curCmteMember.getFirstName();
				String cmteMiddleName = curCmteMember.getMiddleName();
				if (null != cmteMiddleName && !cmteMiddleName.isEmpty())
					cmteMemberName = cmteMemberName + " " + cmteMiddleName;
				String cmteSuffix = curCmteMember.getSuffix();
				if (null != cmteSuffix && !cmteSuffix.isEmpty())
					cmteMemberName = cmteMemberName + ", " + cmteSuffix;
				addField(
						MarcTags.kAdvisorname,
						makeFieldDataFrom('1', '0', 'a', cmteMemberName,
								cmteMemberString));
			}
		}
	}

	private void handleSchoolCode() {
		String dissSchoolCode = curMetaData.getSchool().getSchoolCode();
		if (null != dissSchoolCode && !dissSchoolCode.isEmpty()) {
			addField(MarcTags.kAdvisorname,
					makeFieldDataFrom(' ', ' ', 'a', dissSchoolCode));
		}
	}

	private void handleDegrees() {
		List<Degree> degrees = curMetaData.getAuthors().get(0).getDegrees();
		if (!degrees.isEmpty()) {
			for (Degree curDegree : degrees) {
				if (curDegree.getSequenceNumber() == 1) {
					addField(
							MarcTags.kDegreeName,
							makeFieldDataFrom(' ', ' ', 'a',
									curDegree.getDegreeCode()));
					addField(
							MarcTags.kDegreeDate,
							makeFieldDataFrom(' ', ' ', 'a',
									curDegree.getDegreeYear()));
				}
			}
		}

	}

	private void handleDisserationLanguage() {
		List<DissLanguage> dissLanguages = curMetaData.getDissLanguages();
		if (!dissLanguages.isEmpty()) {
			for (DissLanguage curDissLanguage : dissLanguages) {
				addField(
						MarcTags.kDissertationLanguage,
						makeFieldDataFrom(' ', ' ', 'a',
								curDissLanguage.getLanguageDescription()));
			}
		}
	}

	private void handleUrl() {
		String url = curMetaData.getExternalURL();
		String pubId = curMetaData.getPubNumber();
		if (null != url && !url.isEmpty()) {
			addField(MarcTags.kUrl,
					makeFieldDataFrom(' ', ' ', 'u', url, pubId.trim()));
		}

	}

	private String endWithPeriod(String x) {
		return x.endsWith(".") ? x : x + ".";
	}

	private void addField(String tag, String data) {
		curRecord.addField(new MarcField(tag, data));
	}

	private String makeFieldDataFrom(char dataFieldIndicator1,
			char dataFieldIndicator2, char subFieldIndicator, String fieldData) {
		StringBuilder builder = new StringBuilder();
		builder.append(dataFieldIndicator1);
		builder.append(dataFieldIndicator2);
		builder.append(MarcCharSet.kSubFieldIndicator);
		builder.append(subFieldIndicator);
		builder.append(fieldData);
		return builder.toString();
	}

	private String makeFieldDataFrom(char dataFieldIndicator1,
			char dataFieldIndicator2, char subFieldIndicator,
			String fieldData1, String fieldData2) {
		StringBuilder builder = new StringBuilder();
		builder.append(dataFieldIndicator1);
		builder.append(dataFieldIndicator2);
		builder.append(MarcCharSet.kSubFieldIndicator);
		builder.append(subFieldIndicator);
		builder.append(fieldData1);
		builder.append(fieldData2);
		return builder.toString();
	}
	
	private String makeHostItemEntryFieldDataFrom(char dataFieldIndicator1,
			char dataFieldIndicator2, char subFieldIndicator1,
			String fieldData1, char subFieldIndicator2, String fieldData2, String fieldData3) {
		StringBuilder builder = new StringBuilder();
		builder.append(dataFieldIndicator1);
		builder.append(dataFieldIndicator2);
		builder.append(MarcCharSet.kSubFieldIndicator);
		builder.append(subFieldIndicator1);
		builder.append(fieldData1);
		builder.append(MarcCharSet.kSubFieldIndicator);
		builder.append(subFieldIndicator2);
		builder.append(fieldData2);
		builder.append(fieldData3);
		return builder.toString();
	}


	private void handleTitle() {
		String title = getTitleToInclude();
		if (null != title) {
			title = endsWithPunctuationMark(title);
			title = SGMLEntitySubstitution.applyAllTo(title);
			char secondFieldIndicator = getSecondFieldIndicator(title);
			String marcTitle = makeFieldDataFrom('1', secondFieldIndicator,
					'a', title);
			for (String part : splitOnLength(marcTitle, kSingleLineTitleLength)) {
				addField(MarcTags.kTitle, part);
			}
		}
	}

	private String getTitleToInclude() {
		String title = null;
		if (null != curMetaData.getTitle().getEnglishOverwriteTitle()) {
			String cleanedFTitle = verifyTitle(curMetaData.getTitle()
					.getForeignTitle());
			if (null != cleanedFTitle) {
				title = cleanedFTitle;
			} else {
				String masterTitle = verifyTitle(curMetaData.getTitle()
						.getMasterTitle());
				title = masterTitle;
			}
		} else {
			String cleanedElectronicTitle = verifyTitle(curMetaData.getTitle()
					.getElectronicTitle());
			if (null != cleanedElectronicTitle) {
				title = cleanedElectronicTitle;
			} else {
				String masterTitle = verifyTitle(curMetaData.getTitle()
						.getMasterTitle());
				title = masterTitle;
			}
		}
		return title;
	}

	private String verifyTitle(String title) {

		String outTitle = null;
		if (null != title) {
			outTitle = title.trim();
			if (outTitle.endsWith(".")) {
				outTitle = outTitle.substring(0, outTitle.length() - 1);
			}
		}
		return outTitle;
	}

	private char getSecondFieldIndicator(String title) {
		String degreeValue2 = null;
		List<DisGeneralMapping> marcMappings = disGenMappingProvider
				.getDisMapping().get(kMarcMapping);
		for (DisGeneralMapping mapping : marcMappings) {
			if (title.substring(0, Integer.parseInt(mapping.getDegreeValue2()))
					.contentEquals(mapping.getDegreevalue1())) {
				degreeValue2 = mapping.getDegreeValue2();
			}
		}
		char secondIndicator = (degreeValue2 == null) ? '0' : degreeValue2
				.charAt(0);
		return secondIndicator;
	}

	private String[] splitOnLength(String x, int length) {
		Iterable<String> result = Splitter.fixedLength(length).split(x);
		return Iterables.toArray(result, String.class);
	}

}

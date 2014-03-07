package com.proquest.mtg.dismetadataservice.marc21rda;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisors;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Batch;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.DissLanguage;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SalesRestriction;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Subject;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SuppFile;
import com.proquest.mtg.dismetadataservice.marc.LanguageCodeToPartialLanguageName;
import com.proquest.mtg.dismetadataservice.marc.MarcField;
import com.proquest.mtg.dismetadataservice.marc.MarcRecord;
import com.proquest.mtg.dismetadataservice.marc.MarcRecordFactoryBase;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;
import com.proquest.mtg.dismetadataservice.metadata.Author;
import com.proquest.mtg.dismetadataservice.metadata.Author.Degree;
import com.proquest.mtg.dismetadataservice.metadata.DisGenMappingProvider;
import com.proquest.mtg.dismetadataservice.metadata.SGMLEntitySubstitution;
import com.proquest.mtg.dismetadataservice.metadata.TextNormalizer;

public class Marc21RdaRecordFactory extends MarcRecordFactoryBase {

	public static final String kSystemPQPrefix = "MiAaPQD";
	public static final String kSystemRdaString = "rda";
	public static final String kSystemCatalogingSourceLanguage = "eng";

	private final TextNormalizer abstractNormalizer = new TextNormalizer();

	private DisPubMetaData curMetaData = null;
	private MarcRecord curRecord = null;
	private final DisGenMappingProvider disGenMappingProvider;

	public Marc21RdaRecordFactory(DisGenMappingProvider disGenMappingProvider) {
		this.disGenMappingProvider = disGenMappingProvider;
	}

	public MarcRecord makeFrom(DisPubMetaData metaData) {

		if (null == metaData) {
			throw new IllegalArgumentException("metaData is null");
		}

		curMetaData = metaData;
		curRecord = new MarcRecord();

		handleRecordId(); /* 001 */
		handleTimeStamp(); /* 005 */
		handleFixedLengthElements(); /* 008 */
		handleISBN(); /* 020 */
		handleSystemControlNumber(); /* 035 */
		handleCatalogingSource(); /* 040 */
		handleAuthor(); /* 100 */
		handleTitle(); /* 245 */
		handlePublication(); /* 264 */
		handlePageCount(); /* 300 */
		handleGeneralNoteForSource(); /* 500 */
		handleGeneralNoteForPublisher(); /* 500 */
		handleGeneralNoteForSuppFiles(); /* 500 */
		handleGeneralNoteForAdvisor(); /* 500 */
		handleDissertationNote(); /* 502 */
		handleAccessRestrictionNote(); /* 506 */
		handleAbstract(); /* 520 */
		handleLocationOfCopy(); /* 535 */
		handleSubjects(); /* 650 and 690 */
		handleMultipleAuthors(); /* 700 */
		handleCorporateEntry(); /* 710 */
		handleVariantTitle(); /* 740 */
		handleHostItemEntry(); /* 773 */
		handleSchoolCode(); /* 590 and 790 */
		handleDegrees(); /* 791 792 */
		handleDisserationLanguage(); /* 793 */
		handlePqOpenUrl(); /* 856 */
		return curRecord;
	}

	private void handleCatalogingSource() {
		addField(
				MarcTags.kCatalogingSource,
				makeFieldDataFrom(' ', ' ', 'a', kSystemPQPrefix)
						+ makeFieldDataFrom('b',
								kSystemCatalogingSourceLanguage)
						+ makeFieldDataFrom('c', kSystemPQPrefix)
						+ makeFieldDataFrom('e', kSystemRdaString));
	}

	private void handleAuthor() {
		String authorFullname = null;
		List<Author> authors = curMetaData.getAuthors();
		if (null != authors && !authors.isEmpty()) {
			authorFullname = authors.get(0).getAuthorFullName();
			if (null != authorFullname && !authorFullname.isEmpty()) {
				authorFullname = SGMLEntitySubstitution
						.applyAllTo(authorFullname);
				addField(
						MarcTags.kAuthor,
						makeFieldDataFrom('1', ' ', 'a',
								endWithPeriod(authorFullname)));
			}
		}
	}

	private void handleAccessRestrictionNote() {
		Batch batch = curMetaData.getBatch();
		if (null != batch) {
			if (null != batch.getDBTypeCode()
					&& batch.getDBTypeCode().equals("DAC")) {
				if (curMetaData.getPubNumber().substring(0, 1)
						.matches("[01-8]")) {
					addField(MarcTags.kAccessRestrictionNote,
							getSalesRestrictionMarcTag());
				}
			}
		}

	}

	private String getSalesRestrictionMarcTag() {
		String accessrestrictionNote = "";
		List<SalesRestriction> saleRestrictions = curMetaData
				.getSalesRestrictions();
		if (null == saleRestrictions || saleRestrictions.isEmpty()) {
			accessrestrictionNote = makeFieldDataFrom(' ', ' ', 'a',
					"This item is not available from University Microfilms International.");
		} else {
			for (SalesRestriction salesRrestriction : saleRestrictions) {
				String restrictionDesc = salesRrestriction.getDescription()
						.trim();
				if (null != restrictionDesc && !restrictionDesc.isEmpty())
					accessrestrictionNote += makeFieldDataFrom(' ', ' ', 'a',
							restrictionDesc);
			}
		}
		return accessrestrictionNote;
	}

	private void handleDissertationNote() {
		String dadCode = null;
		String degreeYear = null;
		String disNote = null;
		List<Author> authors = curMetaData.getAuthors();
		if (null != authors && !authors.isEmpty()) {
			List<Degree> degrees = authors.get(0).getDegrees();
			if (null != degrees) {
				dadCode = degrees.get(0).getDegreeCode();
				degreeYear = degrees.get(0).getDegreeYear();
			}
		}
		if (null != dadCode) {
			disNote = "(" + dadCode + ")";
		}
		String schoolName = curMetaData.getSchool() == null ? null
				: curMetaData.getSchool().getSchoolName();
		if (null != schoolName) {
			disNote += "--" + schoolName;
		}
		if (null != degreeYear) {
			disNote += ", " + degreeYear;
		}
		if (null != disNote) {
			addField(
					MarcTags.kDissertationNote,
					makeFieldDataFrom(' ', ' ', 'a', "Thesis "
							+ endWithPeriod(disNote)));
		}
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
				curParagraph = SGMLEntitySubstitution.applyAllTo(curParagraph);
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
			locationOfCopy = SGMLEntitySubstitution.applyAllTo(locationOfCopy);
			locationOfCopy = endWithPeriod(locationOfCopy.trim());
			addField(MarcTags.kLocationOfCopy,
					makeFieldDataFrom('2', ' ', 'a', locationOfCopy));
		}
	}

	private void handleSubjects() {
		List<Subject> subjects = curMetaData.getSubjects();
		if (subjects != null && !subjects.isEmpty()) {
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
		String degreeYear = null;

		List<Author> authors = curMetaData.getAuthors();
		if (null != authors && !authors.isEmpty()) {
			List<Degree> degrees = authors.get(0).getDegrees();
			if (null != degrees && !degrees.isEmpty()) {
				degreeYear = degrees.get(0).getDegreeYear();
			}
		}

		degreeFlag = (degreeYear != null && !degreeYear.isEmpty() ? "s" : "n");
		String fixedLengthElement = curTime + degreeFlag;
		if (null != degreeYear) {
			fixedLengthElement += degreeYear;
		}

		fixedLengthElement += "    miu||||||m   |||||||";
		String LanguageCode = null;

		DissLanguage dissLanguage = curMetaData.getDissLanguages() != null ? curMetaData
				.getDissLanguages().get(0) : null;
		if (null != dissLanguage) {

			if (dissLanguage.getLanguageCode() != null
					&& !dissLanguage.getLanguageCode().isEmpty()) {
				LanguageCode = curMetaData.getDissLanguages().get(0)
						.getLanguageCode();
				fixedLengthElement += LanguageCodeToPartialLanguageName
						.getLanguageFor(LanguageCode) + " d";
			}

		}
		addField(MarcTags.kFiexedLengthDataElements, fixedLengthElement);
	}

	private void handleSystemControlNumber() {
		String pubId = curMetaData.getPubNumber();
		if (null != pubId && !pubId.isEmpty()) {
			addField(
					MarcTags.kSystemControlNumber,
					makeFieldDataFrom(' ', ' ', 'a', "(" + kSystemPQPrefix
							+ ")" + kRecordIdPrefix + pubId.trim()));
		}
	}

	private void handleISBN() {
		String isbn = curMetaData.getISBN();
		if (null != isbn && !isbn.isEmpty()) {
			addField(MarcTags.kIsbn,
					makeFieldDataFrom(' ', ' ', 'a', isbn.replaceAll("-", "")));
		}
	}

	private void handleTimeStamp() {
		Date now = new Date();
		String curTime = new SimpleDateFormat("yyyyMMddHHmmss.5").format(now);
		addField(MarcTags.kTransactionTimestamp, curTime);
	}

	private void handleGeneralNoteForSource() {
		String resultPageNumStr = "";
		String sourceGeneralNote = "";

		String pubPageNum = curMetaData.getPubPageNum();
		if (null == pubPageNum || pubPageNum.isEmpty()) {
			resultPageNumStr = "";
		} else {
			resultPageNumStr = ", page: "
					+ StringUtils.rightPad(pubPageNum, 4, '0');
		}

		Batch batch = curMetaData.getBatch();
		if (null == batch) {
			return;
		}

		String dbTypeCode = batch.getDBTypeCode();
		String dbTypeDesc = batch.getDBTypeDesc();
		String volumeIssue = batch.getVolumeIssue();
		String daiSectionCode = batch.getDAISectionCode();

		if (null != dbTypeCode && !dbTypeCode.isEmpty()
				&& dbTypeCode.equals("DAI")) {
			String resultVolumeIssueStr;
			if (null != volumeIssue && !volumeIssue.isEmpty()) {
				resultVolumeIssueStr = ", Volume: " + volumeIssue;
			} else {
				resultVolumeIssueStr = "";
			}

			String resultDaiSectionCodeStr = "";
			if (null != daiSectionCode && !daiSectionCode.isEmpty()) {
				resultDaiSectionCodeStr = ", Section: " + daiSectionCode;
			} else {
				resultDaiSectionCodeStr = "";
			}

			sourceGeneralNote = "Source: " + dbTypeDesc + resultVolumeIssueStr
					+ resultDaiSectionCodeStr + resultPageNumStr + ".";
		} else if (null != dbTypeCode && !dbTypeCode.isEmpty()
				&& dbTypeCode.equals("MAI")) {
			String resultVolumeIssueStr;
			if (null != volumeIssue && !volumeIssue.isEmpty()) {
				resultVolumeIssueStr = ", Volume: "
						+ volumeIssue.substring(0, 5);
			} else {
				resultVolumeIssueStr = "";
			}

			sourceGeneralNote = "Source: " + dbTypeDesc + resultVolumeIssueStr
					+ resultPageNumStr + ".";
		} else if (null != dbTypeCode && !dbTypeCode.isEmpty()) {
			String resultVolumeIssueStr;
			if (null != volumeIssue && !volumeIssue.isEmpty()) {
				if (pubPageNum == null || pubPageNum.isEmpty()) {
					resultVolumeIssueStr = ", Volume: " + volumeIssue;
				} else {
					resultVolumeIssueStr = ", Volume: "
							+ volumeIssue.substring(0, 2) + "-"
							+ volumeIssue.substring(2, 2);
					resultVolumeIssueStr = resultVolumeIssueStr
							+ ", Section: C";
				}
			} else {
				resultVolumeIssueStr = "";
			}

			sourceGeneralNote = "Source: " + dbTypeDesc + resultVolumeIssueStr
					+ resultPageNumStr + ".";
		}

		if (!sourceGeneralNote.isEmpty()) {
			addField(
					MarcTags.kGeneralNote,
					makeFieldDataFrom(' ', ' ', 'a',
							endWithPeriod(sourceGeneralNote)));
		}
	}

	private void handleGeneralNoteForPublisher() {
		String publisher = curMetaData.getPublisher();
		if (null != publisher && !publisher.isEmpty()) {
			publisher = SGMLEntitySubstitution.applyAllTo(publisher);
			addField(
					MarcTags.kGeneralNote,
					makeFieldDataFrom(' ', ' ', 'a', "Publisher info.: "
							+ endWithPeriod(publisher)));
		}
	}

	private void handleGeneralNoteForSuppFiles() {
		List<SuppFile> publisher = curMetaData.getSuppFiles();
		if (null != publisher && !publisher.isEmpty()) {
			addField(
					MarcTags.kGeneralNote,
					makeFieldDataFrom(' ', ' ', 'a',
							"Includes supplementary digital materials."));
		}
	}

	private void handleGeneralNoteForAdvisor() {
		Advisors advisors = curMetaData.getAdvisors();
		if (null != advisors) {
			String advisor = advisors.getAdvisorsExodusStr();
			if (null != advisor && !advisor.isEmpty()) {
				advisor = SGMLEntitySubstitution.applyAllTo(advisor);
				advisor.replaceAll("\\s+$", "");
				if (!advisor.endsWith(".")) {
					advisor = advisor + ".";
				}
			}
			addField(MarcTags.kGeneralNote,
					makeFieldDataFrom(' ', ' ', 'a', advisor));
		}
	}

	private void handlePageCount() {
		if (null != curMetaData.getPageCount()
				&& !curMetaData.getPageCount().isEmpty()) {
			String pageCountString = " 1electronic resource";
			addField(
					MarcTags.kPageCount,
					makeFieldDataFrom(' ', ' ', 'a', pageCountString + " ("
							+ curMetaData.getPageCount() + " pages)"));
		}
	}

	private void handleMultipleAuthors() {
		List<Author> authors = curMetaData.getAuthors();
		if (null != authors && !authors.isEmpty()) {
			for (Author curAuthor : authors) {
				int authorSequence = curAuthor.getSequenceNumber();
				if (authorSequence > 1) {
					addField(
							MarcTags.kMulitpleAuthor,
							makeFieldDataFrom('1', '0', 'a',
									curAuthor.getAuthorFullName())
									+ makeFieldDataFrom('e', "joint author"));
				}
			}
		}
	}

	private void handlePublication() {
		List<Author> authors = curMetaData.getAuthors();
		String degreeYear = "";
		if (null != authors && !authors.isEmpty()) {
			degreeYear = authors.get(0).getDegrees() != null ? authors.get(0)
					.getDegrees().get(0).getDegreeYear() : null;
			if (null != degreeYear && !degreeYear.isEmpty()) {
				addField(
						MarcTags.kPublication,
						makeFieldDataFrom(
								' ',
								'1',
								'a',
								"Ann Arbor : "
										+ endWithComma(makeFieldDataFrom('b',
												"ProQuest Dissertations & Theses"))
										+ ' '
										+ makeFieldDataFrom('c', degreeYear)));
			} else {
				addField(
						MarcTags.kPublication,
						makeFieldDataFrom(
								' ',
								'1',
								'a',
								"Ann Arbor : "
										+ endWithPeriod(makeFieldDataFrom('b',
												"ProQuest Dissertations & Theses"))));
			}
		}
		else {
			addField(
					MarcTags.kPublication,
					makeFieldDataFrom(
							' ',
							'1',
							'a',
							"Ann Arbor : "
									+ endWithPeriod(makeFieldDataFrom('b',
											"ProQuest Dissertations & Theses"))));
		}

	}

	private void handleCorporateEntry() {
		List<String> deptNames = curMetaData.getDepartments();
		String deptName = "";
		if (deptNames != null && !deptNames.isEmpty()) {
			for (String curDeptName : deptNames) {
				if (null != curDeptName && !curDeptName.isEmpty()) {
					deptName = deptName + curDeptName.trim() + ".";
				}
			}
		}
		String schoolName = curMetaData.getSchool() != null ? curMetaData
				.getSchool().getSchoolName() : null;
		if (null != schoolName && !schoolName.isEmpty()) {
			if (null != deptName && !deptName.isEmpty())
				addField(
						MarcTags.kCorporatename,
						makeFieldDataFrom('2', '0', 'a', schoolName + "."
								+ makeFieldDataFrom('b', deptName)));
			else
				addField(MarcTags.kCorporatename,
						makeFieldDataFrom('2', '0', 'a', schoolName + "."));
		}

	}

	private void handleVariantTitle() {
		String variantTitle = curMetaData.getTitle() != null ? curMetaData
				.getTitle().getEnglishOverwriteTitle() : null;
		if (null != variantTitle && !variantTitle.isEmpty()) {
			variantTitle = SGMLEntitySubstitution.applyAllTo(variantTitle);
			addField(MarcTags.kVariantTitle,
					makeFieldDataFrom('0', '0', 'a', variantTitle.trim() + "."));
		}
	}

	private void handleHostItemEntry() {
		String batchTypeCode = curMetaData.getBatch() != null ? curMetaData
				.getBatch().getDBTypeCode() : null;
		String batchTypeDesc = curMetaData.getBatch() != null ? curMetaData
				.getBatch().getDBTypeDesc() : null;
		String batchVolumeIssue = curMetaData.getBatch() != null ? curMetaData
				.getBatch().getVolumeIssue() : null;
		String daiSectionCode = curMetaData.getBatch() != null ? curMetaData
				.getBatch().getDAISectionCode() : null;
		String fieldData = null;

		if (null != batchTypeCode
				&& batchTypeCode.equalsIgnoreCase(kDoctoralPrefix)) {
			if (null != batchVolumeIssue && !batchVolumeIssue.isEmpty()) {

				if (null != daiSectionCode && !daiSectionCode.isEmpty())
					fieldData = endWithPeriod(batchVolumeIssue.substring(0, 5)
							+ daiSectionCode + batchVolumeIssue.substring(5));
				else
					fieldData = endWithPeriod(batchVolumeIssue.substring(0, 5)
							+ batchVolumeIssue.substring(5));

				addField(
						MarcTags.kHostItemEntry,
						makeHostItemEntryFieldDataFrom('0', ' ', 't',
								batchTypeDesc, 'g', fieldData));
			} else
				addField(
						MarcTags.kHostItemEntry,
						makeFieldDataFrom('0', ' ', 't',
								endWithPeriod(batchTypeDesc)));
		} else if (null != batchTypeCode
				&& batchTypeCode.equalsIgnoreCase(kMastersPrefix)) {
			if (batchVolumeIssue.length() == 6
					|| batchVolumeIssue.length() == 9)
				fieldData = endWithPeriod(batchVolumeIssue.substring(0, 5)
						+ batchVolumeIssue.substring(6));
			else
				fieldData = endWithPeriod(batchVolumeIssue);
			if (null != batchVolumeIssue && !batchVolumeIssue.isEmpty())
				addField(
						MarcTags.kHostItemEntry,
						makeHostItemEntryFieldDataFrom('0', ' ', 't',
								batchTypeDesc, 'g', fieldData));
			else
				addField(
						MarcTags.kHostItemEntry,
						makeFieldDataFrom('0', ' ', 't',
								endWithPeriod(batchTypeDesc)));
		} else {
			if (null != batchVolumeIssue && !batchVolumeIssue.isEmpty()) {
				fieldData = endWithPeriod(batchVolumeIssue);
				addField(
						MarcTags.kHostItemEntry,
						makeHostItemEntryFieldDataFrom('0', ' ', 't',
								batchTypeDesc, 'g', fieldData));
			} else if (null != batchTypeDesc && !batchTypeDesc.isEmpty())
				addField(
						MarcTags.kHostItemEntry,
						makeFieldDataFrom('0', ' ', 't',
								endWithPeriod(batchTypeDesc)));
		}
	}

	private void handleSchoolCode() {
		String dissSchoolCode = curMetaData.getSchool() != null ? curMetaData
				.getSchool().getSchoolCode() : null;
		if (null != dissSchoolCode && !dissSchoolCode.isEmpty()) {
			addField(MarcTags.kLocalAddedEntry,
					makeFieldDataFrom(' ', ' ', 'a', dissSchoolCode));
			addField(
					MarcTags.kLocalNoteSchoolCode,
					makeFieldDataFrom(' ', ' ', 'a', "School code: ",
							dissSchoolCode) + ".");
		}
	}

	private void handleDegrees() {
		List<Degree> degrees = curMetaData.getAuthors() != null
				&& !curMetaData.getAuthors().isEmpty() ? curMetaData
				.getAuthors().get(0).getDegrees() : null;
		if (degrees != null && !degrees.isEmpty()) {
			for (Degree curDegree : degrees) {
				if (curDegree.getSequenceNumber() != null
						&& curDegree.getSequenceNumber() == 1) {
					if (null != curDegree.getDegreeCode()
							&& !curDegree.getDegreeCode().isEmpty())
						addField(
								MarcTags.kDegreeName,
								makeFieldDataFrom(' ', ' ', 'a',
										curDegree.getDegreeCode()));
					if (null != curDegree.getDegreeYear()
							&& !curDegree.getDegreeYear().isEmpty())
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
		String languageDescription = "";
		if (dissLanguages != null && !dissLanguages.isEmpty()) {
			for (DissLanguage curDissLanguage : dissLanguages) {
				if (curDissLanguage.getLanguageDescription() != null
						&& !curDissLanguage.getLanguageDescription().isEmpty())
					languageDescription = languageDescription
							+ curDissLanguage.getLanguageDescription() + ";";
				addField(
						MarcTags.kDissertationLanguage,
						makeFieldDataFrom(' ', ' ', 'a', languageDescription
								.substring(0, languageDescription.length() - 1)));
			}
		}
	}

	private void handlePqOpenUrl() {
		String url = curMetaData.getPqOpenURL();
		if (null != url && !url.isEmpty()) {
			addField(MarcTags.kUrl, makeFieldDataFrom(' ', ' ', 'u', url));
		}

	}

	private void addField(String tag, String data) {
		curRecord.addField(new MarcField(tag, data));
	}

	private void handleTitle() {
		String title = getTitleToInclude(curMetaData);
		String additionalAuthors = "";
		if (null != title) {
			title = endsWithPunctuationMark(title);
			title = SGMLEntitySubstitution.applyAllTo(title);
			char secondFieldIndicator = getSecondFieldIndicator(
					disGenMappingProvider, title, kMarcMapping);
			if (null != curMetaData.getAuthors()
					& !curMetaData.getAuthors().isEmpty()) {
				List<Author> authors = curMetaData.getAuthors();
				if (null != authors && !authors.isEmpty()) {
					for (Author curAuthor : authors) {
						if (curAuthor.getSequenceNumber() > 1) {
							additionalAuthors = additionalAuthors
									+ endWithPeriod(curAuthor
											.getAuthorFullName()) + " ; ";
						}
					}
				}
			}
			additionalAuthors = additionalAuthors.substring(0,
					additionalAuthors.length() - 3);
			addField(MarcTags.kTitle,
					makeFieldDataFrom('1', secondFieldIndicator, 'a', title)
							+ makeFieldDataFrom('c', additionalAuthors));
		}
	}

}

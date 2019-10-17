package com.proquest.mtg.dismetadataservice.marc21rda;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisors;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Batch;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.CmteMember;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.DissLOCLanguage;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.DissLanguage;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SalesRestriction;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Subject;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SuppFile;
import com.proquest.mtg.dismetadataservice.exodus.SplitAdvisors;
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
	public static final char kEncodingLevel = '3';
	public static final char kDescriptiveCataloging = 'i';

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
				
		curRecord = new MarcRecord('a', kEncodingLevel, kDescriptiveCataloging);
		
		handleRecordId(); /* 001 */
		handleTimeStamp(); /* 005 */
		handleFixedLengthElements(); /* 008 */
		handleISBN(); /* 020 */
		handleSystemControlNumber(); /* 035 */
		handleUCMercEdNumber(); /* 035 */
		handleCatalogingSource(); /* 040 */
		handleLanguageCode(); /* 041 */
		handleAuthor(); /* 100 */
		handleTitle(); /* 245 */
		handlePublication(); /* 264 */
		handlePageCount(); /* 300 */
		handleContentType(); /* 336 */
		handleMediaType(); /* 337 */
		handleCarrierType(); /* 338 */
		handleGeneralNoteForSource(); /* 500 */
		handleGeneralNoteForPublisher(); /* 500 */
		handleGeneralNoteForSuppFiles(); /* 500 */
		handleGeneralNoteForAdvisor(); /* 500 */
		handleDissertationNote(); /* 502 */
		handleAccessRestrictionNote(); /* 506 */
		handleAbstract(); /* 520 */
		handleLocationOfCopy(); /* 535 */
		handleLanguageNote(); /* 546 */
		handleSubjects(); /* 650 and 690 */
		handleMultipleAuthors(); /* 700 */
		handleCorporateEntry(); /* 710 */
		handleUncontrolledName(); /* 720 */
		handleVariantTitle(); /* 740 */
		handleHostItemEntry(); /* 773 */
		handleSchoolCode(); /* 590 and 790 */
		handleDegrees(); /* 791 792 */
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

	private void handleLanguageCode() {
		List<DissLOCLanguage> LOClanguages = curMetaData.getDissLOCLanguages();
		if (LOClanguages != null && !LOClanguages.isEmpty()) {

			for (DissLOCLanguage curLanguage : LOClanguages) {
				String threeLetterLangCode = "";
				if (null != curLanguage.getLOCLanguageCode()
						&& !curLanguage.getLOCLanguageCode().isEmpty()) {
					List<String> threeLetterLangCodes = SplitLanguageCodes
							.split(curLanguage.getLOCLanguageCode());
					if (threeLetterLangCodes.size() > 1) {
						for (int i = 0; i < threeLetterLangCodes.size(); ++i) {
							{
								threeLetterLangCode = threeLetterLangCode
										+ makeFieldDataFrom('a',
												threeLetterLangCodes.get(i));
							}
						}
						if (null != threeLetterLangCode
								&& !threeLetterLangCode.isEmpty()) {
							addField(
									MarcTags.kLanguageCode,
									makeFieldDataFrom('0', ' ',
											threeLetterLangCode));
						}
					}
				}
			}
		}
	}

	private void handleAuthor() {
		String authorFullname = null;
		String orcID = null;
		List<Author> authors = curMetaData.getAuthors();
		if (null != authors && !authors.isEmpty()) {
			orcID = authors.get(0).getOrcID();
			authorFullname = authors.get(0).getAuthorFullName();
			if (null != authorFullname && !authorFullname.isEmpty()) {
				if (null != orcID && !orcID.isEmpty()){
				authorFullname = SGMLEntitySubstitution
						.applyAllTo(authorFullname);
				addField(
						MarcTags.kAuthor,
						makeFieldDataFrom('1', ' ', 'a',
								endWithPeriod(authorFullname + ","
										+ makeFieldDataFrom('e', "author")))
										+ makeFieldDataFrom('0', "(orcid)" + orcID));
				}else{
					authorFullname = SGMLEntitySubstitution
							.applyAllTo(authorFullname);
					addField(
							MarcTags.kAuthor,
							makeFieldDataFrom('1', ' ', 'a',
									endWithPeriod(authorFullname + ","
											+ makeFieldDataFrom('e', "author"))));
				}
			}
		}
	}
	

	private void handleAccessRestrictionNote() {
		if (null != curMetaData.getPubNumber()) {
			addSalesRestrictionMarcTag();
		}

	}

	private void addSalesRestrictionMarcTag() {
		String accessrestrictionNote = "";
		String restrictionMessageForPQ = "This item is not available from ProQuest Dissertations & Theses";
		String restriction3rdPartyVendors = "This item must not be sold to any third party vendors";
		String restriction3rdPartyIndexing = "This item must not be added to any third party search indexes";
		List<SalesRestriction> saleRestrictions = curMetaData
				.getSalesRestrictions();
		if (null == saleRestrictions || saleRestrictions.isEmpty()) {

		} else {
			for (SalesRestriction salesRrestriction : saleRestrictions) {
				String restrictionCode = salesRrestriction.getCode();
				if (null != restrictionCode && !restrictionCode.isEmpty())
					if (restrictionCode.equals("5"))
						accessrestrictionNote = makeFieldDataFrom(' ', ' ',
								'a', restriction3rdPartyVendors);
					else if (restrictionCode.equals("8"))
						accessrestrictionNote = makeFieldDataFrom(' ', ' ',
								'a', restriction3rdPartyIndexing);
					else
						accessrestrictionNote = makeFieldDataFrom(' ', ' ',
								'a', restrictionMessageForPQ);
				accessrestrictionNote = endWithPeriod(accessrestrictionNote);
				addField(MarcTags.kAccessRestrictionNote, accessrestrictionNote);
			}
		}
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
		if (null != dadCode && !dadCode.isEmpty()) {
			disNote = dadCode;
		}
		String schoolName = curMetaData.getSchool() == null ? null
				: curMetaData.getSchool().getSchoolName();
		if (null != schoolName && !schoolName.isEmpty()) {
			disNote += makeFieldDataFrom('c', schoolName);
		}
		if (null != degreeYear && !degreeYear.isEmpty()) {
			disNote += makeFieldDataFrom('d', degreeYear);
		}
		if (null != disNote && !disNote.isEmpty()) {
			addField(MarcTags.kDissertationNote,
					makeFieldDataFrom(' ', ' ', 'b', endWithPeriod(disNote)));
		}
	}

	private void handleRecordId() {
		String pubId = curMetaData.getPubNumber();
		if (null != pubId && !pubId.isEmpty()) {
			addField(MarcTags.kRecId, kRecordIdPrefix + pubId.trim());
		}
	}
	
	/*  9999 byte limit on abstracts, 775 Words */
	private String handleRecordSize(String x) {
		System.out.println("In Shrink Town");
		if (null != x) {
			if (x.length() < 9999) {
				System.out.println("Short Enough");
				return x;
		}

	      int wordCount = 0;
	      int charMarker = 0;

	      boolean isWord = false;
	      boolean isEnd = false;
	      int endOfLine = x.length() - 1;
	      char[] characters = x.toCharArray();

	      for (int i = 0; i < characters.length; i++) {

	        // if the char is a letter, word = true.
	        if (Character.isLetter(characters[i]) && i != endOfLine && !isEnd) {
	          isWord = true;

	          // if char isn't a letter and there have been letters before,
	          // counter goes up.
	        } else if (!Character.isLetter(characters[i]) && isWord && !isEnd) {
	          wordCount++;
	          //System.out.println("Word Count: " +wordCount);
	          if (wordCount > 771) {
	        	  charMarker = i;
	        	  isEnd = true;
	          }
	          isWord = false;

	          // last word of String; if it doesn't end with a non letter, it
	          // wouldn't count without this.
	        } else if (Character.isLetter(characters[i]) && i == endOfLine && !isEnd) {
	          wordCount++;
	        }
	      }
	      
		if (null != x) {
			if (wordCount > 771) {
				x = x.substring(0,charMarker);
				x = endsWithPunctuationMark(x);
				x = x + " (Abstract shortened by ProQuest)";
		        }
		}
		System.out.println("Word Count of Abstract: "+wordCount);
		}
		return x;
	}
	
	/*  Rearrange authors from lastname-middle-firstname to firstname-middle-lastname */

	private void handleAbstract() {
		String abstractText = curMetaData.getAbstract();
		String alternateAbstractText = null;
		if (curMetaData.getAlternateAbstracts() != null) {
			alternateAbstractText = curMetaData.getAlternateAbstracts().getAbstractText();
		}
		if (null != abstractText && !abstractText.isEmpty() && null != alternateAbstractText && !alternateAbstractText.isEmpty()) {
			abstractText = abstractNormalizer.applyTo(abstractText);
			abstractText = handleRecordSize(abstractText);
			alternateAbstractText = handleRecordSize(alternateAbstractText);
			for (String curParagraph : makeAbstractParagraphsFrom(abstractText)) {
				curParagraph = endsWithPunctuationMark(curParagraph);
				curParagraph = SGMLEntitySubstitution.applyAllTo(curParagraph);
				curParagraph = removeOddQuotes(curParagraph);
				addField(MarcTags.kAbstract,
					makeFieldDataFrom(' ', ' ', 'a', curParagraph));
			}
			for (String curAltParagraph : makeAbstractParagraphsFrom(alternateAbstractText)){
				curAltParagraph = endsWithPunctuationMark(curAltParagraph);
				curAltParagraph = SGMLEntitySubstitution.applyAllTo(curAltParagraph);
				curAltParagraph = removeOddQuotes(curAltParagraph);
				addField(MarcTags.kAbstract,
					makeFieldDataFrom('$','=',curAltParagraph));
			}
		}
		else {
			if (null != abstractText && !abstractText.isEmpty()) {
				abstractText = abstractNormalizer.applyTo(abstractText);
				abstractText = handleRecordSize(abstractText);
				for (String curParagraph : makeAbstractParagraphsFrom(abstractText)) {
					curParagraph = endsWithPunctuationMark(curParagraph);
					curParagraph = SGMLEntitySubstitution.applyAllTo(curParagraph);
					curParagraph = removeOddQuotes(curParagraph);
					addField(MarcTags.kAbstract,
						makeFieldDataFrom(' ', ' ', 'a', curParagraph));
				}
			}
			else
			{
			   if (null != alternateAbstractText && !alternateAbstractText.isEmpty()) {
				   alternateAbstractText = abstractNormalizer.applyTo(alternateAbstractText);
				   alternateAbstractText = handleRecordSize(alternateAbstractText);
				   for (String curAltParagraph : makeAbstractParagraphsFrom(alternateAbstractText)) {
					   curAltParagraph = endsWithPunctuationMark(curAltParagraph);
					   curAltParagraph = SGMLEntitySubstitution.applyAllTo(curAltParagraph);
					   curAltParagraph = removeOddQuotes(curAltParagraph);
					   addField(MarcTags.kAbstract,
						   makeFieldDataFrom(' ', ' ', 'a', curAltParagraph));
				   }
			   }
			}
		}
	}

	
	// Replace left and right quotes with straight quotes
	private String removeOddQuotes(String text) {
		return text.replaceAll("[\\u2018\\u2019]", "'")
		           .replaceAll("[\\u201C\\u201D]", "\"");
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

	private void handleLanguageNote() {
		List<DissLanguage> languages = curMetaData.getDissLanguages();
		if (languages != null && !languages.isEmpty()) {
			for (DissLanguage curLanguage : languages) {
				if (null != curLanguage.getLanguageDescription()
						&& !curLanguage.getLanguageDescription().isEmpty())
					addField(
							MarcTags.kLanguageNote,
							makeFieldDataFrom(' ', ' ', 'a',
									curLanguage.getLanguageDescription()));
			}
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
		/*List<DissLOCLanguage> LOCLanguages = curMetaData.getDissLOCLanguages() != null ? curMetaData
				.getDissLOCLanguages() : null;
				if (LOCLanguages != null && !LOCLanguages.isEmpty()) {
				for (DissLOCLanguage curLanguage : LOCLanguages) {
				List<String> threeLetterLangCodes = SplitLanguageCodes
						.split(curLanguage.getLOCLanguageCode());*/
				fixedLengthElement += kSystemCatalogingSourceLanguage + " d";
				/*}


		}*/
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
	
	private void handleUCMercEdNumber() {
		String externalId = curMetaData.getExternalId();
		String externalIdStr = "";
		if (null != externalId && !externalId.isEmpty()) {
			if (externalId.contains("http")){
			   externalIdStr = externalId.substring(externalId.lastIndexOf("/") +1);
			}
			else{
				externalIdStr = externalId;
			}
			if (null != externalIdStr && !externalIdStr.isEmpty()) {
			addField(
					MarcTags.kSystemControlNumber,
					makeFieldDataFrom(' ', ' ', 'a', "(" +kSystemPQPrefix + ")" 
					       + externalIdStr.trim()));
			}
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
				resultVolumeIssueStr = ", Volume: " + volumeIssue;
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
		String adviserCmteMembers = "";
		if (null != advisors) {
			String advisor = advisors.getAdvisorsExodusStr();
			if (null != advisor && !advisor.isEmpty()) {
				List<String> advisorNames = SplitAdvisors.split(advisor);
				for (String curAdvisor : advisorNames) {
					advisor = SGMLEntitySubstitution.applyAllTo(curAdvisor);
					advisor.replaceAll("\\s+$", "");
					adviserCmteMembers += advisor + "; ";
				}
				adviserCmteMembers = "Advisors: "
						+ adviserCmteMembers.substring(0,
								adviserCmteMembers.length() - 2);
			}
		}
		List<CmteMember> cmteMembers = curMetaData.getCmteMembers();
		if (null != cmteMembers && !cmteMembers.isEmpty()) {
			String cmteMemberString = "";
			for (CmteMember curCmteMember : cmteMembers) {
				if (null != curCmteMember.getFirstName()
						&& !curCmteMember.getFirstName().isEmpty()) {
					cmteMemberString = cmteMemberString
							+ curCmteMember.getFirstName();
				}
				if (null != curCmteMember.getMiddleName()
						&& !curCmteMember.getMiddleName().isEmpty()) {
					cmteMemberString = cmteMemberString + " "
							+ curCmteMember.getMiddleName();
				}
				if (null != curCmteMember.getLastName()
						&& !curCmteMember.getLastName().isEmpty()) {
					cmteMemberString = cmteMemberString + " "
							+ curCmteMember.getLastName();
				}
				if (null != curCmteMember.getSuffix()
						&& !curCmteMember.getSuffix().isEmpty()) {
					cmteMemberString = cmteMemberString + "," + " "
							+ curCmteMember.getSuffix();
				}
				cmteMemberString = cmteMemberString + "; ";
			}
			if (null != cmteMemberString && !cmteMemberString.isEmpty()) {
				cmteMemberString = cmteMemberString.substring(0,
						cmteMemberString.length() - 2);
				cmteMemberString = "  Committee members: " + cmteMemberString;
			}
			adviserCmteMembers = adviserCmteMembers + cmteMemberString;
		}
		if (null != adviserCmteMembers && !adviserCmteMembers.isEmpty())

			addField(
					MarcTags.kGeneralNote,
					makeFieldDataFrom(' ', ' ', 'a',
							endWithPeriod(adviserCmteMembers)));
	}

	private void handlePageCount() {
		if (null != curMetaData.getPageCount()
				&& !curMetaData.getPageCount().isEmpty()) {
			String pageCountString = "1 electronic resource";
			String pagesString;
			if (Integer.valueOf(curMetaData.getPageCount()) > 1)
				pagesString = "pages";
			else
				pagesString = "page";
			addField(
					MarcTags.kPageCount,
					makeFieldDataFrom(' ', ' ', 'a', pageCountString + " ("
							+ curMetaData.getPageCount() + " " + pagesString
							+ ")"));
		}
	}

	private void handleContentType() {
		addField(MarcTags.kContentType,	makeFieldDataFrom(' ', ' ', 'a', "text"
											+ makeFieldDataFrom('b', "txt")
											+ makeFieldDataFrom('2', "rdacontent")));
	}

	private void handleMediaType() {
		addField(MarcTags.kMediaType, makeFieldDataFrom(' ', ' ', 'a',
										"computer" + makeFieldDataFrom('b', "c")
										+ makeFieldDataFrom('2', "rdamedia")));
	}

	private void handleCarrierType() {
		addField(MarcTags.kCarrierType, makeFieldDataFrom(' ', ' ', 'a',
										"online resource" 
											+ makeFieldDataFrom('b', "cr")
											+ makeFieldDataFrom('2', "rdacarrier")));
	}

	private void handleMultipleAuthors() {
		List<Author> authors = curMetaData.getAuthors();
		if (null != authors && !authors.isEmpty()) {
			for (Author curAuthor : authors) {
				int authorSequence = curAuthor.getSequenceNumber();
				if (authorSequence > 1) {
					addField(
							MarcTags.kMulitpleAuthor,
							makeFieldDataFrom('1', ' ', 'a',
									endWithComma(curAuthor.getAuthorFullName()))
									+ endWithPeriod(makeFieldDataFrom('e',
											"author.")));
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

	private void handleCorporateEntry() {
		List<String> deptNames = curMetaData.getDepartments();
		String schoolName = curMetaData.getSchool() != null ? curMetaData.getSchool().getSchoolName() : null;
		if (deptNames != null && !deptNames.isEmpty()) {
			for (String curDeptName : deptNames) {
				if (null != curDeptName && !curDeptName.isEmpty()) {
					String deptName = curDeptName.trim() + ".";
					if (null != schoolName && !schoolName.isEmpty()) {
						if (null != deptName && !deptName.isEmpty()) {
							addField(
									MarcTags.kCorporatename,endWithPeriod(makeFieldDataFrom('2', ' ','a', schoolName))
									+ endWithPeriod(makeFieldDataFrom('b', deptName))
									+ endWithPeriod(makeFieldDataFrom('e',"degree granting institution")));
						}
						else {
							addField(
									MarcTags.kCorporatename,endWithPeriod(makeFieldDataFrom('2', ' ','a', schoolName))
									+ endWithPeriod(makeFieldDataFrom('e',"degree granting institution")));
						}
					} else {
						addField(
								MarcTags.kCorporatename,endWithPeriod(makeFieldDataFrom('2', ' ',makeFieldDataFrom('b', deptName))
								+ endWithPeriod(makeFieldDataFrom('e',"degree granting institution"))));
					}
				}
			}
		}
		else {
			if (null != schoolName && !schoolName.isEmpty()) {
				addField(
						MarcTags.kCorporatename, endWithPeriod(makeFieldDataFrom('2', ' ','a', schoolName))								
						+ endWithPeriod(makeFieldDataFrom('e',"degree granting institution")));
			}
		}

	}

	public void handleUncontrolledName() {

		Advisors advisors = curMetaData.getAdvisors();
		if (null != advisors) {
			String advisor = advisors.getAdvisorsExodusStr();
			if (null != advisor && !advisor.isEmpty()) {
				advisor = SGMLEntitySubstitution.applyAllTo(advisor);
				advisor.replaceAll("\\s+$", "");
				List<String> advisorNames = SplitAdvisors.split(advisor);
				for (String curAdvisor : advisorNames) {
					addField(
							MarcTags.kUncontrolledName,
							makeFieldDataFrom(
									'1',
									' ',
									'a',
									curAdvisor
											+ makeFieldDataFrom('e',
													"degree supervisor.")));
				}

			}
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
				addField(MarcTags.kHostItemEntry,
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
							dissSchoolCode));
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

	private void handlePqOpenUrl() {
		String url = curMetaData.getPqOpenURL();
		if (null != url && !url.isEmpty()) {
			addField(MarcTags.kUrl, makeFieldDataFrom('4', '0', 'u', url));
		}

	}

	private void addField(String tag, String data) {
		curRecord.addField(new MarcField(tag, data));
	}

	private void handleTitle() {
		String title = getTitleToInclude(curMetaData);
		String additionalAuthors = "";
		String alternateTitle = null;
		if ((null != getAlternateTitleToInclude(curMetaData)) && (getAlternateTitleToInclude(curMetaData).size() > 0)) {
			alternateTitle = getAlternateTitleToInclude(curMetaData).get(0).getAltTitle();
		}
		if (null != title && !title.isEmpty()) {
			// title = endsWithPunctuationMark(title);
			title = SGMLEntitySubstitution.applyAllTo(title);
			char secondFieldIndicator = getSecondFieldIndicator(
					disGenMappingProvider, title, kMarcMapping);
			if (null != curMetaData.getAuthors()
					& !curMetaData.getAuthors().isEmpty()) {
				List<Author> authors = curMetaData.getAuthors();
				if (null != authors && !authors.isEmpty()) {
					for (Author curAuthor : authors) {
						additionalAuthors = additionalAuthors
								+ endWithPeriod(curAuthor.getAuthorFullName())
								+ " ; ";
					}
				}
				if (null != alternateTitle) {
					title = title + " ="
							+ makeFieldDataFrom('b', alternateTitle);
				}
			}
			if (null != additionalAuthors && !additionalAuthors.isEmpty()) {
					additionalAuthors = additionalAuthors.substring(0,
							additionalAuthors.length() - 3);
					String authorLastName = null;
					String authorFirstGroup = null;
					String authorMisc = null;
					String allAuthorsCombined = null;
					if (additionalAuthors.contains(";")){
						/* Multiple authors */
						String[] authorArray = additionalAuthors.split(";");
						for (String o : authorArray){
							/*System.out.println("AUTHOR PARTS =" + o);*/
							if (o.contains(",")){
							String[] parts = o.split(",");
							authorLastName = parts[0];
							authorLastName = authorLastName.trim();
							authorFirstGroup = parts[1];
							authorFirstGroup = authorFirstGroup.trim();
							if (authorFirstGroup.endsWith(".")){
								authorFirstGroup = authorFirstGroup.substring(0, authorFirstGroup.length() -1);
							}
		                    int authorLen = parts.length;
		                    /*System.out.println("Array Length = " + authorLen);*/
		                    if (authorLen > 2){
		                    	authorMisc = parts[2];
		                    	additionalAuthors = authorFirstGroup + " " + authorLastName + authorMisc;
		                    	/*System.out.println("AUTHOR PARTS =" + authorFirstGroup + " " + authorLastName + " " + authorMisc);*/
		                    }
		                    else {
		                    	additionalAuthors = authorFirstGroup + " " + authorLastName + ".";
		                    	/*System.out.println("AUTHOR PARTS =" + authorFirstGroup + " " + authorLastName + ".");*/
		                    }
		                    if (additionalAuthors.endsWith(".")){
								additionalAuthors = additionalAuthors.substring(0, additionalAuthors.length() -1);
		                    }
		                    if (null != allAuthorsCombined){
		                       allAuthorsCombined = allAuthorsCombined + ", " + additionalAuthors;
		                    }
		                    else {
		                    	allAuthorsCombined = additionalAuthors;
		                    }
							}
		                    /*System.out.println("AUTHOR STRING: " + allAuthorsCombined);*/
						}
						additionalAuthors = allAuthorsCombined;
						additionalAuthors = additionalAuthors + ".";
					}
					else {
						/* Single author */
						if (additionalAuthors.contains(",")){
						String[] parts = additionalAuthors.split(",");
						authorLastName = parts[0];
						authorLastName = authorLastName.trim();
						authorFirstGroup = parts[1];
						if (authorFirstGroup.endsWith(".")){
							authorFirstGroup = authorFirstGroup.substring(0, authorFirstGroup.length() -1);
						}
	                    int authorLen = parts.length;
	                    /*System.out.println("Array Length = " + authorLen);*/
	                    authorFirstGroup = authorFirstGroup.trim();
	                    if (authorLen > 2){
	                    	authorMisc = parts[2];
	                    	additionalAuthors = authorFirstGroup + " " + authorLastName + authorMisc;
	                    	/*System.out.println("AUTHOR PARTS (single multiparts) =" + authorFirstGroup + " " + authorLastName + " " + authorMisc);*/
	                    }
	                    else {
	                    	/*authorFirstGroup = authorFirstGroup.trim();*/
	                    	additionalAuthors = authorFirstGroup + " " + authorLastName + ".";
	                    	/*System.out.println("AUTHOR PARTS (single onepart) =" + authorFirstGroup + " " + authorLastName + ".");*/
	                    }
						}
					}
			        addField(MarcTags.kTitle,
					        makeFieldDataFrom('1', secondFieldIndicator, 'a', title + " /")
					        + makeFieldDataFrom('c', additionalAuthors));
			}
			else {
				addField(MarcTags.kTitle,
						makeFieldDataFrom('1', secondFieldIndicator, 'a', title));
			}
		}
	}

}

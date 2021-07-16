package com.proquest.mtg.dismetadataservice.csv;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import java.io.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.ImmutableList;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.Advisor;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.AlternateTitle;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.CmteMember;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.DissLanguage;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.FormatRestriction;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.Keyword;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.ManuscriptMedia;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.PdfAvailableDateStatus;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.SalesRestriction;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.School;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.Subject;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.SuppFile;
import com.proquest.mtg.dismetadataservice.media.PDFVaultAvailableStatusProvider;
import com.proquest.mtg.dismetadataservice.metadata.Author;
import com.proquest.mtg.dismetadataservice.metadata.Author.Degree;
import com.proquest.mtg.dismetadataservice.metadata.SGMLEntitySubstitution;
import com.proquest.mtg.dismetadataservice.metadata.TextNormalizer;

@SuppressWarnings("unused")
public class CSVRecordFactory {

	private static final String DELIMITER = "|";
	private DisPubMetaData curMetaData = null;
	private String curRecord = "";
	private int excludeAbstract = 0;
	private int excludeAltAbstract = 0;
	private final TextNormalizer abstractNormalizer = new TextNormalizer();
	private final PDFVaultAvailableStatusProvider pdfAvailableStatus;

	private final LinkedHashMap<String, Method> kAllHeaders = new LinkedHashMap<String, Method>();

	
	public CSVRecordFactory(PDFVaultAvailableStatusProvider pdfVaultAvailableStatus, int excludeAbstract, int excludeAltAbstract) 
			throws NoSuchMethodException, SecurityException, ClassNotFoundException {
		this.excludeAbstract = excludeAbstract;
		this.excludeAltAbstract = excludeAltAbstract;
		initTagMapping();
		this.pdfAvailableStatus = pdfVaultAvailableStatus;
		
	}

	public PDFVaultAvailableStatusProvider getPDFVaultAvailableStatusProvider() {
		return pdfAvailableStatus;
	}

	private void initTagMapping() throws NoSuchMethodException,
			SecurityException, ClassNotFoundException {
		kAllHeaders.put(CSVHeaders.kPubNumber,
				CSVRecordFactory.class.getDeclaredMethod("handlePubNumber"));
		kAllHeaders.put(CSVHeaders.kAuthors, CSVRecordFactory.class
				.getDeclaredMethod("handleMultipleAuthors"));
		kAllHeaders.put(CSVHeaders.kTitle,
				CSVRecordFactory.class.getDeclaredMethod("handleTitle"));
		kAllHeaders.put(CSVHeaders.kDegreeCode, CSVRecordFactory.class
				.getDeclaredMethod("handleDegreeCodeForFirstAuthor"));
		kAllHeaders.put(CSVHeaders.kDegreeYear, CSVRecordFactory.class
				.getDeclaredMethod("handleDegreeYearForFirstAuthor"));
		kAllHeaders.put(CSVHeaders.kSchoolName,
				CSVRecordFactory.class.getDeclaredMethod("handleSchoolName"));
		kAllHeaders.put(CSVHeaders.kHasPDF,
				CSVRecordFactory.class.getDeclaredMethod("hasPDF"));
		kAllHeaders.put(CSVHeaders.kPDFAvailableDate, CSVRecordFactory.class
				.getDeclaredMethod("handlePDFAvailableDate"));
		kAllHeaders.put(CSVHeaders.kHasSupplementalFiles,
				CSVRecordFactory.class.getDeclaredMethod("hasSupplementalFiles"));
		kAllHeaders.put(CSVHeaders.kPubDate, CSVRecordFactory.class
				.getDeclaredMethod("handlePubDate"));
		kAllHeaders.put(CSVHeaders.kDissLangDesc, 
				CSVRecordFactory.class.getDeclaredMethod("handleDissLanguageDesc"));
		kAllHeaders.put(CSVHeaders.kDegreeDesc, CSVRecordFactory.class
				.getDeclaredMethod("handleDegreeDescForFirstAuthor"));
		kAllHeaders.put(CSVHeaders.kIsbn,
				CSVRecordFactory.class.getDeclaredMethod("handleISBN"));
		kAllHeaders.put(CSVHeaders.kOpenAccessFlag, CSVRecordFactory.class
				.getDeclaredMethod("handleOpenAccessFlag"));
		kAllHeaders.put(CSVHeaders.kPageCount,
				CSVRecordFactory.class.getDeclaredMethod("handlePageCount"));
		kAllHeaders.put(CSVHeaders.kpqSubjectDesc,
				CSVRecordFactory.class.getDeclaredMethod("handlepqSubjectDesc"));
		kAllHeaders.put(CSVHeaders.kKeyword,
				CSVRecordFactory.class.getDeclaredMethod("handleKeyWords"));
		kAllHeaders.put(CSVHeaders.kDepartmentName, CSVRecordFactory.class
				.getDeclaredMethod("handleDepartmentName"));
		kAllHeaders.put(CSVHeaders.kAdvisors,
				CSVRecordFactory.class.getDeclaredMethod("handleAdvisors"));
		kAllHeaders.put(CSVHeaders.kCmteMember, CSVRecordFactory.class
				.getDeclaredMethod("handleCmteMember"));
		kAllHeaders.put(CSVHeaders.kExternalId,
				CSVRecordFactory.class.getDeclaredMethod("handleExternalId"));
		kAllHeaders.put(CSVHeaders.kSubjectCode,
				CSVRecordFactory.class.getDeclaredMethod("handleSubjectCode"));
		kAllHeaders.put(CSVHeaders.kSubjectDesc,
				CSVRecordFactory.class.getDeclaredMethod("handleSubjectDesc"));
		kAllHeaders.put(CSVHeaders.kAbstract,
				CSVRecordFactory.class.getDeclaredMethod("handleAbstract"));
		kAllHeaders.put(CSVHeaders.kAbstractLang, 
				CSVRecordFactory.class.getDeclaredMethod("handleDissLanguageDesc"));
		kAllHeaders.put(CSVHeaders.kAltAbstract,
				CSVRecordFactory.class.getDeclaredMethod("handleAltAbstract"));
		kAllHeaders.put(CSVHeaders.kAltAbstractLang, 
				CSVRecordFactory.class.getDeclaredMethod("handleAltAbstractLang"));
		kAllHeaders.put(CSVHeaders.kAltTitle,
				CSVRecordFactory.class.getDeclaredMethod("handleAltTitle"));
		kAllHeaders.put(CSVHeaders.kAltTitleLang,
				CSVRecordFactory.class.getDeclaredMethod("handleAltTitleLang"));
		kAllHeaders.put(CSVHeaders.kDissertationTypeCode,
				CSVRecordFactory.class.getDeclaredMethod("handleDissertationTypeCode"));
		kAllHeaders.put(CSVHeaders.kDAISectionCode, 
				CSVRecordFactory.class.getDeclaredMethod("handleDAISectionCode"));
		kAllHeaders.put(CSVHeaders.kDissertationCode, 
				CSVRecordFactory.class.getDeclaredMethod("handleDissertationCode"));
		kAllHeaders.put(CSVHeaders.kSchoolCode,
				CSVRecordFactory.class.getDeclaredMethod("handleSchoolCode"));
		kAllHeaders.put(CSVHeaders.kSchoolState,
				CSVRecordFactory.class.getDeclaredMethod("handleSchoolState"));
		kAllHeaders.put(CSVHeaders.kSchoolCountry, 
				CSVRecordFactory.class.getDeclaredMethod("handleSchoolCountry"));
		kAllHeaders.put(CSVHeaders.kVolumeIssue,
				CSVRecordFactory.class.getDeclaredMethod("handleVolumeIssue"));
		kAllHeaders.put(CSVHeaders.kActiveSalesRestrictionCode,
				CSVRecordFactory.class.getDeclaredMethod("handleActiveSalesRestrictionCode"));
		kAllHeaders.put(CSVHeaders.kActiveFormatRestrictionCode,
				CSVRecordFactory.class.getDeclaredMethod("handleActiveFormatRestrictionCode"));
		kAllHeaders.put(CSVHeaders.kSalesRestrictionCode,
				CSVRecordFactory.class.getDeclaredMethod("handleSalesRestrictionCode"));
		kAllHeaders.put(CSVHeaders.kSalesRestrictionDesc,
				CSVRecordFactory.class.getDeclaredMethod("handleSalesRestrictionDesc"));
		kAllHeaders.put(CSVHeaders.kSalesRestrictionStartDate,
				CSVRecordFactory.class.getDeclaredMethod("handleSalesRestrictionStartDate"));
		kAllHeaders.put(CSVHeaders.kSalesRestrictionEndDate,
				CSVRecordFactory.class.getDeclaredMethod("handleSalesRestrictionEndDate"));
		kAllHeaders.put(CSVHeaders.kFormatRestrictionCode,
				CSVRecordFactory.class.getDeclaredMethod("handleFormatRestrictionCode"));
		kAllHeaders.put(CSVHeaders.kFormatRestrictionDesc,
				CSVRecordFactory.class.getDeclaredMethod("handleFormatRestrictionDesc"));
		kAllHeaders.put(CSVHeaders.kFormatRestrictionStartDt,
				CSVRecordFactory.class.getDeclaredMethod("handleFormatRestrictionStartDt"));
		kAllHeaders.put(CSVHeaders.kFormatRestrictionEndDt,
				CSVRecordFactory.class.getDeclaredMethod("handleFormatRestrictionEndDt"));
		kAllHeaders.put(CSVHeaders.kManuscriptMediaCode, CSVRecordFactory.class
				.getDeclaredMethod("handleManuscriptMediaCode"));
		kAllHeaders.put(CSVHeaders.kManuscriptMediaDesc, CSVRecordFactory.class
				.getDeclaredMethod("handleManuscriptMediaDesc"));
		kAllHeaders.put(CSVHeaders.kDissLangCode, CSVRecordFactory.class
				.getDeclaredMethod("handleDissLanguageCode"));
		kAllHeaders.put(CSVHeaders.kSuppFileNames, CSVRecordFactory.class
				.getDeclaredMethod("handleSupplementalFileName"));
		kAllHeaders.put(CSVHeaders.kSuppFileDescription, CSVRecordFactory.class
				.getDeclaredMethod("handleSupplementalFileDesc"));
		kAllHeaders.put(CSVHeaders.kSuppFileCategory, CSVRecordFactory.class
				.getDeclaredMethod("handleSupplementalFilesCategory"));
		kAllHeaders.put(CSVHeaders.kExternalUrl,
				CSVRecordFactory.class.getDeclaredMethod("handleExternalUrl"));
		kAllHeaders.put(CSVHeaders.kDOI,
				CSVRecordFactory.class.getDeclaredMethod("handleDOI"));
		kAllHeaders.put(CSVHeaders.kOrcID,
				CSVRecordFactory.class.getDeclaredMethod("handleOrcID"));
		kAllHeaders.put(CSVHeaders.kBritishLibraryNumber,
				CSVRecordFactory.class.getDeclaredMethod("handleBritishLibrary"));
		kAllHeaders.put(CSVHeaders.kPqOpenUrl,
				CSVRecordFactory.class.getDeclaredMethod("handlePqOpenUrl"));
		kAllHeaders.put(CSVHeaders.kDciRefsFlag, CSVRecordFactory.class
				.getDeclaredMethod("handleDCIRefs"));
		kAllHeaders.put(CSVHeaders.kDisValidSource, CSVRecordFactory.class.getDeclaredMethod("handleDisValidSource")); 
		kAllHeaders.put(CSVHeaders.kDisAvailableFormats, CSVRecordFactory.class.getDeclaredMethod("handleDisAvailableFormats")); 
	}

	public LinkedHashMap<String, Method> getTagMappings() {
		return kAllHeaders;
	}

	public ImmutableList<String> getHeaders() {
		return ImmutableList.copyOf(kAllHeaders.keySet());
	}

	public String makeFrom(DisPubMetaData metaData)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		if (null == metaData) {
			throw new IllegalArgumentException("metaData is null");
		}
		createHeader();
		curMetaData = metaData;
		for (String key : getHeaders()) {
			kAllHeaders.get(key).invoke(this);
		}
		return curRecord;
	}

	private void createHeader() {
		for (String key : kAllHeaders.keySet()) {
			curRecord += key + ",";
		}
		curRecord += "\r\n";
	}
	
	private void handleDisValidSource() {
		String disValidSource = "";
		if (null != curMetaData.getDisValidSource()
				&& !curMetaData.getDisValidSource().isEmpty()) {
			disValidSource = curMetaData.getDisValidSource();
		}
		addField(disValidSource);
	}
	
	private void handleDisAvailableFormats() {
		List<String> avlFormats = curMetaData.getDisAvailableFormats();
		String avlFormat = "";
		if (avlFormats != null && !avlFormats.isEmpty()) {
			for (String curAvlFormat : avlFormats) {
				if (null != curAvlFormat && !curAvlFormat.isEmpty()) {
					avlFormat = avlFormat + endWithPipes(curAvlFormat.trim());
				}
			}
			if (avlFormat.endsWith(DELIMITER)) {
				avlFormat = avlFormat.substring(0, avlFormat.length() - 1);
			}
		}
		addField(avlFormat);
	}
	
	private void handleFOPQuantity() {
		List<String> fopQuantities = curMetaData.getFOPQuantity();
		String fopQuantity = "";
		if (fopQuantities != null && !fopQuantities.isEmpty()) {
			for (String curFOPQuantity : fopQuantities) {
				if (null != curFOPQuantity && !curFOPQuantity.isEmpty()) {
					fopQuantity = fopQuantity + endWithPipes(curFOPQuantity.trim());
				}
			}
			if (fopQuantity.endsWith(DELIMITER)) {
				fopQuantity = fopQuantity.substring(0, fopQuantity.length() - 1);
			}
		}
		addField(fopQuantity);
	}

	private void handlePubNumber() {
		String pub = "";
		if (null != curMetaData.getPubNumber()
				&& !curMetaData.getPubNumber().isEmpty()) {
			pub = curMetaData.getPubNumber();
		}
		addField(pub);
	}

	private void handleVolumeIssue() {
		String dissVolume = curMetaData.getBatch() != null ? curMetaData
				.getBatch().getVolumeIssue() : "";
		if (null == dissVolume)
			dissVolume = "";
		addField(dissVolume);
	}

	private void handleAdvisors() {
		List<Advisor> dissAdvisors = curMetaData.getAdvisors() != null ? curMetaData
				.getAdvisors().getAdvisor() : null;
		String adviserName = "";
		if (dissAdvisors != null && !dissAdvisors.isEmpty()) {
			for (Advisor curAdvisor : dissAdvisors) {
				String tempAdviserName = "";
				if (null != curAdvisor.getAdvisorFullName()
						&& !curAdvisor.getAdvisorFullName().isEmpty()) {
					tempAdviserName = SGMLEntitySubstitution
							.applyAllTo(curAdvisor.getAdvisorFullName().trim());
					adviserName += endWithPipes(tempAdviserName);
				}
			}
			if (adviserName.endsWith(DELIMITER)) {
				adviserName = adviserName
						.substring(0, adviserName.length() - 1);
			}
		}
		addField(adviserName);
	}

	private void handleISBN() {
		String isbn = "";
		if (null != curMetaData.getISBN() && !curMetaData.getISBN().isEmpty()) {
			isbn = curMetaData.getISBN();
			isbn = isbn.replaceAll("[\\s\\-()]", "");
		}
		addField(isbn);
	}

	private void handlePageNumber() {
		String pageNumber = "";
		if (null != curMetaData.getPubPageNum()
				&& !curMetaData.getPubPageNum().isEmpty()) {
			pageNumber = curMetaData.getPubPageNum();
		}
		addField(pageNumber);
	}

	private void handlePageCount() {
		String pageCount = "";
		if (null != curMetaData.getPageCount()
				&& !curMetaData.getPageCount().isEmpty()) {
			pageCount = curMetaData.getPageCount();
		}
		addField(pageCount);
	}

	private void handlePublisher() {
		String publisher = "";
		if (null != curMetaData.getPublisher()
				&& !curMetaData.getPublisher().isEmpty()) {
			publisher = curMetaData.getPublisher();
		}
		addField(publisher);

	}

	private void handleReferenceLocation() {
		String locationOfCopy = "";
		if (null != curMetaData.getReferenceLocation()
				&& !curMetaData.getReferenceLocation().isEmpty()) {
			locationOfCopy = SGMLEntitySubstitution.applyAllTo(curMetaData
					.getReferenceLocation());
		}
		addField(locationOfCopy);
	}
	
	private void handleOrcID() {
		String orcid = "";
		if (null != curMetaData.getOrcID()
				&& !curMetaData.getOrcID().isEmpty()) {
			orcid = curMetaData.getOrcID();
		}
		addField(orcid);

	}
	
	private void handleDOI() {
		String doi = "";
		if (null != curMetaData.getDOI()
				&& !curMetaData.getDOI().isEmpty()) {
			doi = curMetaData.getDOI();
		}
		addField(doi);

	}

	private void handleBritishLibrary() {
		String blNumber = "";
		if (null != curMetaData.getBLNumber()
				&& !curMetaData.getBLNumber().isEmpty()) {
			blNumber = curMetaData.getBLNumber();
		}
		addField(blNumber);

	}

	private void handlePqOpenUrl() {
		String source = "";
		if (null != curMetaData.getPqOpenURL()
				&& !curMetaData.getPqOpenURL().isEmpty()) {
			source = curMetaData.getPqOpenURL();
		}
		addField(source);
	}

	private void handleExternalUrl() {
		String source = "";
		if (null != curMetaData.getExternalURL()
				&& !curMetaData.getExternalURL().isEmpty()) {
			source = curMetaData.getExternalURL();
		}
		addField(source);
	}

	private void handleDissLanguageDesc() {
		List<DissLanguage> dissLanguages = curMetaData.getDissLanguages();
		String dissLanguageDesc = "";
		if (dissLanguages != null && !dissLanguages.isEmpty()) {
			for (DissLanguage curDissLanguage : dissLanguages) {
				dissLanguageDesc += endWithPipes(curDissLanguage
						.getLanguageDescription());
			}
			if (dissLanguageDesc.endsWith(DELIMITER)) {
				dissLanguageDesc = dissLanguageDesc.substring(0,
						dissLanguageDesc.length() - 1);
			}
		}
		addField(dissLanguageDesc);
	}

	private void handleDissLanguageCode() {
		List<DissLanguage> dissLanguages = curMetaData.getDissLanguages();
		String dissLanguageCode = "";
		if (dissLanguages != null && !dissLanguages.isEmpty()) {
			for (DissLanguage curDissLanguage : dissLanguages) {
				dissLanguageCode += endWithPipes(curDissLanguage
						.getLanguageCode());
			}

			if (dissLanguageCode.endsWith(DELIMITER)) {
				dissLanguageCode = dissLanguageCode.substring(0,
						dissLanguageCode.length() - 1);
			}
		}
		addField(dissLanguageCode);
	}

	private void handleSchoolName() {
		School school = curMetaData.getSchool();
		String schoolName = "";
		if (null != school) {
			if (null != school.getSchoolName()
					&& !school.getSchoolName().isEmpty())
				schoolName = school.getSchoolName();
		}
		addField(schoolName);
	}

	private void handleSchoolCode() {
		School school = curMetaData.getSchool();
		String schoolCode = "";
		if (null != school) {
			if (null != school.getSchoolCode()
					&& !school.getSchoolCode().isEmpty())
				schoolCode = school.getSchoolCode();
		}
		addField(schoolCode);
	}

	private void handleSchoolCountry() {
		School school = curMetaData.getSchool();
		String schoolCountry = "";
		if (null != school) {
			if (null != school.getSchoolCountry()
					&& !school.getSchoolCountry().isEmpty())
				schoolCountry = school.getSchoolCountry();
		}
		addField(schoolCountry);
	}

	private void handleSchoolState() {
		School school = curMetaData.getSchool();
		String schoolState = "";
		if (null != school) {
			if (null != school.getSchoolState()
					&& !school.getSchoolState().isEmpty())
				schoolState = school.getSchoolState();
		}
		addField(schoolState);
	}

	private void handleEnglishTranslationOfTitle() {
		String title = "";
		String englishOverwriteTitle = (curMetaData.getTitle() == null) ? null
				: curMetaData.getTitle().getEnglishOverwriteTitle();
		if (null != englishOverwriteTitle) {
			String cleanedElectronicTitle = verifyTitle(curMetaData.getTitle()
					.getElectronicTitle());
			if (null != cleanedElectronicTitle) {
				title = cleanedElectronicTitle;
			} else {
				title = curMetaData.getTitle().getEnglishOverwriteTitle();

			}
			if (null != title && !title.isEmpty()) {
				title = SGMLEntitySubstitution.applyAllTo(title).trim();
				title = endsWithPunctuationMark(title);
			}
		}
		addField(title);
	}

	private void handleVariantTitle() {
		String title = "";
		String variantTitle = curMetaData.getTitle() != null ? curMetaData
				.getTitle().getEnglishOverwriteTitle() : null;
		if (null != variantTitle && !variantTitle.isEmpty()) {
			title = SGMLEntitySubstitution.applyAllTo(variantTitle);
			title = endsWithPunctuationMark(title);
		}

		addField(title);
	}
	
	private void handleAltTitle() {
		String title = "";
		List<AlternateTitle> altTitles = curMetaData.getAlternateTitles();
		if (altTitles != null && !altTitles.isEmpty()) {
			StringBuilder altTitleSb = new StringBuilder();
			for (AlternateTitle altTitle : altTitles) {
				altTitleSb.append(altTitle.getAltTitle());
			}
			title = SGMLEntitySubstitution.applyAllTo(altTitleSb.toString());
			title = endsWithPunctuationMark(title);
		}
		
		addField(title);
	}
	
	private void handleAltTitleLang() {
		String lang = "";
		List<AlternateTitle> altTitles = curMetaData.getAlternateTitles();
		if (altTitles != null && !altTitles.isEmpty()
				&& StringUtils.isNotBlank(altTitles.get(0).getLanguage())) {
			lang = altTitles.get(0).getLanguage();
		}
		
		addField(lang);
	}

	private void handleTitle() {
		String title = getTitleToInclude();
		if (null != title && !title.isEmpty()) {
			title = SGMLEntitySubstitution.applyAllTo(title);		
			title = endsWithPunctuationMark(title);
		}
		addField(title);
	}

	private String getTitleToInclude() {
		String title = "";
		String englishOverwriteTitle = (curMetaData.getTitle() == null) ? null
				: curMetaData.getTitle().getEnglishOverwriteTitle();
		if (null != englishOverwriteTitle && !englishOverwriteTitle.isEmpty()) {
			String cleanedFTitle = verifyTitle(curMetaData.getTitle()
					.getForeignTitle());
			if (null != cleanedFTitle && !cleanedFTitle.isEmpty()) {
				title = cleanedFTitle;
			} else {
				String masterTitle = verifyTitle(curMetaData.getTitle()
						.getMasterTitle());
				title = masterTitle;
			}
		} else {
			String cleanedElectronicTitle = curMetaData.getTitle() == null ? null
					: verifyTitle(curMetaData.getTitle().getElectronicTitle());
			if (null != cleanedElectronicTitle
					&& !cleanedElectronicTitle.isEmpty()) {
				title = cleanedElectronicTitle;
			} else {
				String masterTitle = curMetaData.getTitle() == null ? ""
						: verifyTitle(curMetaData.getTitle().getMasterTitle());
				title = masterTitle;
			}
		}
		return title;
	}

	private String verifyTitle(String title) {

		String outTitle = "";
		if (null != title) {
			outTitle = title.trim();
			if (outTitle.endsWith(".")) {
				outTitle = outTitle.substring(0, outTitle.length() - 1);
			}
		}
		return outTitle;
	}

	private void handleMultipleAuthors() {
		List<Author> authors = curMetaData.getAuthors();
		String authorNames = "";

		if (null != authors && !authors.isEmpty()) {
			for (Author curAuthor : authors) {
				if (null != curAuthor.getAuthorFullName()
						&& !curAuthor.getAuthorFullName().isEmpty()) {
					authorNames += endWithPipes(curAuthor.getAuthorFullName());
					authorNames = StringUtils.normalizeSpace(authorNames);
				}
			}
			if (null != authorNames && !authorNames.isEmpty()) {
				authorNames = authorNames
						.substring(0, authorNames.length() - 1);
				authorNames = StringUtils.normalizeSpace(authorNames);
			}
		}
		addField(authorNames);
	}

	private void handleDegreeCodeForFirstAuthor() {
		List<Author> authors = curMetaData.getAuthors();
		List<Degree> degrees = null;
		String degreeCode = "";

		if (null != authors && !authors.isEmpty()) {
			degrees = authors.get(0).getDegrees();
			if (degrees != null && !degrees.isEmpty()) {
				for (Degree curDegree : degrees) {
					if (null != curDegree.getDegreeCode()
							&& !curDegree.getDegreeCode().isEmpty())
						degreeCode += endWithPipes(curDegree.getDegreeCode());
				}
				if (degreeCode.endsWith(DELIMITER)) {
					degreeCode = degreeCode.substring(0,
							degreeCode.length() - 1);
				}
			}
		}
		addField(degreeCode);
	}

	private void handleDegreeDescForFirstAuthor() {
		List<Author> authors = curMetaData.getAuthors();
		List<Degree> degrees = null;
		String degreeDesc = "";

		if (null != authors && !authors.isEmpty()) {
			degrees = authors.get(0).getDegrees();
			if (degrees != null && !degrees.isEmpty()) {
				for (Degree curDegree : degrees) {
					if (null != curDegree.getDegreeDescription()
							&& !curDegree.getDegreeDescription().isEmpty()) {
						degreeDesc += endWithPipes(curDegree
								.getDegreeDescription());
					}
				}
				if (degreeDesc.endsWith(DELIMITER)) {
					degreeDesc = degreeDesc.substring(0,
							degreeDesc.length() - 1);
				}
			}
		}
		addField(degreeDesc);
	}

	private void handleDegreeYearForFirstAuthor() {
		List<Author> authors = curMetaData.getAuthors();
		List<Degree> degrees = null;
		String degreeYear = "";

		if (null != authors && !authors.isEmpty()) {
			degrees = authors.get(0).getDegrees();
			if (degrees != null && !degrees.isEmpty()) {
				for (Degree curDegree : degrees) {
					if (null != curDegree.getDegreeYear())
						degreeYear += endWithPipes(curDegree.getDegreeYear());
				}
				if (degreeYear.endsWith(DELIMITER)) {
					degreeYear = degreeYear.substring(0,
							degreeYear.length() - 1);
				}
			}
		}
		addField(degreeYear);
	}

	private void handleAbstract() {
		String abstractText = "";
		if (this.excludeAbstract == 0 
				&& null != curMetaData.getAbstract()
				&& !curMetaData.getAbstract().isEmpty()) {
			abstractText = abstractNormalizer
					.applyTo(curMetaData.getAbstract());
			abstractText = SGMLEntitySubstitution.applyAllTo(abstractText);			
		}
		addField(abstractText);
	}
	
	private void handleAltAbstract() {
		String altAbstractText = "";
		if (this.excludeAltAbstract == 0
				&& null != curMetaData.getAlternateAbstracts()
				&& StringUtils.isNotEmpty(curMetaData.getAlternateAbstracts().getAbstractText())) {
			altAbstractText = abstractNormalizer
					.applyTo(curMetaData.getAlternateAbstracts().getAbstractText());
			altAbstractText = SGMLEntitySubstitution.applyAllTo(altAbstractText);
		}
		addField(altAbstractText);
	}
	
	private void handleAltAbstractLang() {
		String altAbstractLang = "";
		if (null != curMetaData.getAlternateAbstracts()
				&& StringUtils.isNotEmpty(curMetaData.getAlternateAbstracts().getLanguage())) {
			altAbstractLang = curMetaData.getAlternateAbstracts().getLanguage();
		}
		addField(altAbstractLang);
	}

	private void handleSubjectDesc() {
		List<Subject> subjects = curMetaData.getSubjects();
		String subjDesc = "";

		if (subjects != null && !subjects.isEmpty()) {
			for (Subject curSubject : subjects) {
				if (null != curSubject.getSubjectDesc()
						&& !curSubject.getSubjectDesc().isEmpty()) {
					subjDesc += endWithPipes(curSubject.getSubjectDesc());
				}
			}
			if (subjDesc.endsWith(DELIMITER)) {
				subjDesc = subjDesc.substring(0, subjDesc.length() - 1);
			}
		}
		addField(subjDesc);
	}
	
	private void handlepqSubjectDesc() {
		List<Subject> pqsubjects = curMetaData.getpqSubjects();
		String pqsubjDesc = "";

		if (pqsubjects != null && !pqsubjects.isEmpty()) {
			for (Subject curpqSubject : pqsubjects) {
				if (null != curpqSubject.getSubjectDesc()
						&& !curpqSubject.getSubjectDesc().isEmpty()) {
					pqsubjDesc += endWithPipes(curpqSubject.getSubjectDesc());
				}
			}
			if (pqsubjDesc.endsWith(DELIMITER)) {
				pqsubjDesc = pqsubjDesc.substring(0, pqsubjDesc.length() - 1);
			}
		}
		addField(pqsubjDesc);
	}

	private void handleSubjectCode() {
		List<Subject> subjects = curMetaData.getSubjects();
		String subjCode = "";
		if (subjects != null && !subjects.isEmpty()) {
			for (Subject curSubject : subjects) {
				if (null != curSubject.getSubjectCode()
						&& !curSubject.getSubjectCode().isEmpty()) {
					subjCode += endWithPipes(curSubject.getSubjectCode());
				}
			}
			if (subjCode.endsWith(DELIMITER)) {
				subjCode = subjCode.substring(0, subjCode.length() - 1);
			}
		}
		addField(subjCode);
	}

	private void handleSubjectGroupDesc() {
		List<Subject> subjects = curMetaData.getSubjects();
		String subjGroupDesc = "";
		if (subjects != null && !subjects.isEmpty()) {
			for (Subject curSubject : subjects) {
				if (null != curSubject.getSubjectGroupDesc()
						&& !curSubject.getSubjectGroupDesc().isEmpty()) {
					subjGroupDesc += endWithPipes(curSubject
							.getSubjectGroupDesc());
				}
			}
			if (subjGroupDesc.endsWith(DELIMITER)) {
				subjGroupDesc = subjGroupDesc.substring(0,
						subjGroupDesc.length() - 1);
			}
		}
		addField(subjGroupDesc);
	}

	private void hasSupplementalFiles() {
		String hasSuppFiles = "";
		if (null != curMetaData.getHasSuppFiles()
				&& !curMetaData.getHasSuppFiles().isEmpty()) {
			hasSuppFiles = curMetaData.getHasSuppFiles();
		}
		addField(hasSuppFiles);
	}

	private void handleSupplementalFileName() {
		List<SuppFile> suppFiles = curMetaData.getSuppFiles();
		String suppFileName = "";

		if (null != suppFiles && !suppFiles.isEmpty()) {
			for (SuppFile curSuppFile : suppFiles) {
				if (null != curSuppFile.getSuppFilename()
						&& !curSuppFile.getSuppFilename().isEmpty()) {
					suppFileName += endWithPipes(curSuppFile.getSuppFilename());
				}
			}
			if (suppFileName.endsWith(DELIMITER)) {
				suppFileName = suppFileName.substring(0,
						suppFileName.length() - 1);
			}
		}
		addField(suppFileName);
	}

	private void handleSupplementalFileDesc() {
		List<SuppFile> suppFiles = curMetaData.getSuppFiles();
		String suppFileDescription = "";
		if (null != suppFiles && !suppFiles.isEmpty()) {
			for (SuppFile curSuppFile : suppFiles) {
				if (null != curSuppFile.getSuppFileDesc()
						&& !curSuppFile.getSuppFileDesc().isEmpty()) {
					suppFileDescription += endWithPipes(curSuppFile
							.getSuppFileDesc());
				}
			}
			if (suppFileDescription.endsWith(DELIMITER)) {
				suppFileDescription = suppFileDescription.substring(0,
						suppFileDescription.length() - 1);
			}
		}
		addField(suppFileDescription);
	}

	private void handleSupplementalFilesCategory() {
		List<SuppFile> suppFiles = curMetaData.getSuppFiles();
		String suppFileCategory = "";
		if (null != suppFiles && !curMetaData.getSuppFiles().isEmpty()) {
			for (SuppFile curSuppFile : suppFiles) {
				if (null != curSuppFile.getSuppFileCategory()
						&& !curSuppFile.getSuppFileCategory().isEmpty()) {
					suppFileCategory += endWithPipes(curSuppFile
							.getSuppFileCategory());
				}
			}
			if (suppFileCategory.endsWith(DELIMITER)) {
				suppFileCategory = suppFileCategory.substring(0,
						suppFileCategory.length() - 1);
			}
		}
		addField(suppFileCategory);
	}

	private void handleDepartmentName() {
		List<String> deptNames = curMetaData.getDepartments();
		String deptName = "";
		if (deptNames != null && !deptNames.isEmpty()) {
			for (String curDeptName : deptNames) {
				if (null != curDeptName && !curDeptName.isEmpty()) {
					deptName = deptName + endWithPipes(curDeptName.trim());
				}
			}
			if (deptName.endsWith(DELIMITER)) {
				deptName = deptName.substring(0, deptName.length() - 1);
			}
		}
		addField(deptName);
	}

	private void handleKeyWords() {
		List<Keyword> keywords = curMetaData.getKeywords();
		String keyword = "";

		if (null != keywords && !keywords.isEmpty()) {
			for (Keyword curKeyword : keywords) {
				if (null != curKeyword.getValue()
						&& !curKeyword.getValue().isEmpty())
					keyword += endWithPipes(curKeyword.getValue());
			}
			if (keyword.endsWith(DELIMITER)) {
				keyword = keyword.substring(0, keyword.length() - 1);
			}
		}
		addField(keyword);
	}

	private void handleKeyWordSource() {
		List<Keyword> keywords = curMetaData.getKeywords();
		String keywordSource = "";
		if (null != keywords && !keywords.isEmpty()) {
			for (Keyword curKeyword : keywords) {
				if (null != curKeyword.getSource()
						&& !curKeyword.getSource().isEmpty())
					keywordSource += endWithPipes(curKeyword.getSource());
			}
			if (keywordSource.endsWith(DELIMITER)) {
				keywordSource = keywordSource.substring(0,
						keywordSource.length() - 1);
			}
		}
		addField(keywordSource);
	}

	private void handleActiveSalesRestrictionCode() {
		List<SalesRestriction> salesRestrictions = curMetaData
				.getSalesRestrictions();
		String activeSalesRestrictionCode = "";
		try {
			if (null != salesRestrictions && !salesRestrictions.isEmpty()) {
				for (SalesRestriction currSalesRestriction : salesRestrictions) {
					if (null != currSalesRestriction.getCode()
							&& !currSalesRestriction.getCode().isEmpty()) {
						if(!currSalesRestriction.getCode().equals("5") && !currSalesRestriction.getCode().equals("8")){
							String strtDt = currSalesRestriction.getRestrictionStartDate();
							SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
							String endDt = currSalesRestriction.getRestrictionEndDate();
							Date today = new Date();
							if(strtDt!=null){
								Date strtDate = format.parse(strtDt);
								if(strtDate.before(today)){
									if(endDt!=null){
										Date endDate = format.parse(endDt);
										if(endDate.after(today))
											activeSalesRestrictionCode += endWithPipes(currSalesRestriction
												.getCode());
									}
									else{
										activeSalesRestrictionCode += endWithPipes(currSalesRestriction
												.getCode());
									}
								}
							}
						}
					}
				}
				if (activeSalesRestrictionCode.endsWith(DELIMITER)) {
					activeSalesRestrictionCode = activeSalesRestrictionCode.substring(0,
							activeSalesRestrictionCode.length() - 1);
				}
			}
		} catch (ParseException e) {
			System.out.println("Parse Exception pe="+e);
			e.printStackTrace();
		} catch(Exception e){
			System.out.println("Exception e="+e);
			e.printStackTrace();
		}
		addField(activeSalesRestrictionCode);
	}
	
	private void handleSalesRestrictionCode() {
		List<SalesRestriction> salesRestrictions = curMetaData
				.getSalesRestrictions();
		String salesRestrictionCode = "";

		if (null != salesRestrictions && !salesRestrictions.isEmpty()) {
			for (SalesRestriction currSalesRestriction : salesRestrictions) {
				if (null != currSalesRestriction.getCode()
						&& !currSalesRestriction.getCode().isEmpty()) {
					salesRestrictionCode += endWithPipes(currSalesRestriction
							.getCode());
				}
			}
			if (salesRestrictionCode.endsWith(DELIMITER)) {
				salesRestrictionCode = salesRestrictionCode.substring(0,
						salesRestrictionCode.length() - 1);
			}
		}
		addField(salesRestrictionCode);
	}

	private void handleSalesRestrictionDesc() {
		List<SalesRestriction> salesRestrictions = curMetaData
				.getSalesRestrictions();
		String salesRestrictionDesc = "";
		if (null != salesRestrictions && !salesRestrictions.isEmpty()) {
			for (SalesRestriction currSalesRestriction : salesRestrictions) {
				if (null != currSalesRestriction.getDescription()
						&& !currSalesRestriction.getDescription().isEmpty()) {
					salesRestrictionDesc += endWithPipes(currSalesRestriction
							.getDescription());
				}
			}
			if (salesRestrictionDesc.endsWith(DELIMITER)) {
				salesRestrictionDesc = salesRestrictionDesc.substring(0,
						salesRestrictionDesc.length() - 1);
			}
		}
		addField(salesRestrictionDesc);
	}

	private void handleSalesRestrictionStartDate() {
		List<SalesRestriction> salesRestrictions = curMetaData
				.getSalesRestrictions();
		String salesRestrictionStartDate = "";
		if (null != salesRestrictions && !salesRestrictions.isEmpty()) {
			for (SalesRestriction currSalesRestriction : salesRestrictions) {
				if (null != currSalesRestriction.getRestrictionStartDate()
						&& !currSalesRestriction.getRestrictionStartDate()
								.isEmpty()) {
					salesRestrictionStartDate += endWithPipes(currSalesRestriction
							.getRestrictionStartDate());
				}
			}
			if (salesRestrictionStartDate.endsWith(DELIMITER)) {
				salesRestrictionStartDate = salesRestrictionStartDate
						.substring(0, salesRestrictionStartDate.length() - 1);
			}
		}
		addField(salesRestrictionStartDate);
	}

	private void handleSalesRestrictionEndDate() {
		List<SalesRestriction> salesRestrictions = curMetaData
				.getSalesRestrictions();
		String salesRestrictionEndDate = "";
		if (null != curMetaData.getSalesRestrictions()
				&& !curMetaData.getSalesRestrictions().isEmpty()) {
			for (SalesRestriction currSalesRestriction : salesRestrictions) {
				if (null != currSalesRestriction.getRestrictionEndDate()
						&& !currSalesRestriction.getRestrictionEndDate()
								.isEmpty()) {
					salesRestrictionEndDate += endWithPipes(currSalesRestriction
							.getRestrictionEndDate());
				} else {
					salesRestrictionEndDate += endWithPipes("NONE");
				}
			}
			if (salesRestrictionEndDate.endsWith(DELIMITER)) {
				salesRestrictionEndDate = salesRestrictionEndDate.substring(0,
						salesRestrictionEndDate.length() - 1);
			}
		}
		addField(salesRestrictionEndDate);
	}

	private void handleDissertationTypeCode() {
		String dissTypeCode = curMetaData.getBatch() != null ? curMetaData
				.getBatch().getDBTypeCode() : "";
		if (null == dissTypeCode) {
			dissTypeCode = "";
		}
		addField(dissTypeCode);
	}

	private void handleDissertationCode() {
		String dissTypeDesc = curMetaData.getBatch() != null ? curMetaData
				.getBatch().getDBTypeDesc() : "";

		if (null == dissTypeDesc) {
			dissTypeDesc = "";
		}
		addField(dissTypeDesc);
	}

	private void handleDAISectionCode() {
		String daiSectionCode = curMetaData.getBatch() != null ? curMetaData
				.getBatch().getDAISectionCode() : "";
		if (null == daiSectionCode) {
			daiSectionCode = "";
		}
		addField(daiSectionCode);
	}

	private void hasPDF() {
		String hasPDF = "";
		if (null != curMetaData.getHasPDF()
				&& !curMetaData.getHasPDF().isEmpty()) {
			hasPDF = curMetaData.getHasPDF();
		}
		//String pubNumber = curMetaData.getPubNumber(); 
		//if (null != pubNumber && !pubNumber.isEmpty()) {
		//	try {
		//		if (getPDFVaultAvailableStatusProvider().
		//				isPdfAvailableInVaultFor(pubNumber)) {
		//			hasPDF = "Y";
		//		}
		//	} catch (Exception e) {
		//		hasPDF = "N";
		//	}			
		//}
		addField(hasPDF);
	}

	private void handlePDFAvailableDate() {
		PdfAvailableDateStatus pdfStatus = curMetaData.getPdfStatus();
		String pdfAvailableDate = "";
		if (null != pdfStatus) {
			if (null != pdfStatus.getPdfAvailableDate()
					&& !pdfStatus.getPdfAvailableDate().isEmpty()) {
				pdfAvailableDate = pdfStatus.getPdfAvailableDate();
			}
		}
		addField(pdfAvailableDate);
	}

	private void handleActiveFormatRestrictionCode() {
		String activeFormatRestrictionCode = "";
		try {
			if (null != curMetaData.getFormatRestrictions()
					&& !curMetaData.getFormatRestrictions().isEmpty()) {
				List<FormatRestriction> formatRestrictions = curMetaData
						.getFormatRestrictions();
				for (FormatRestriction curFormatRestriction : formatRestrictions) {
					if (null != curFormatRestriction.getCode()
							&& !curFormatRestriction.getCode().isEmpty()) {
						if(curFormatRestriction.getCode().contains("E")){
							String strtDt = curFormatRestriction.getFormatRestrictionStartDt();
							SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
							String endDt = curFormatRestriction.getFormatRestrictionEndDt();
							Date today = new Date();
							if(strtDt!=null){
								Date strtDate = format.parse(strtDt);
								if(strtDate.before(today)){
									if(endDt!=null){
										Date endDate = format.parse(endDt);
										if(endDate.after(today))
											activeFormatRestrictionCode = activeFormatRestrictionCode
												+ endWithPipes(curFormatRestriction.getCode());
									}
									else{
										activeFormatRestrictionCode += endWithPipes(curFormatRestriction.getCode());
									}
								}
							}
						}
					} else {
						activeFormatRestrictionCode += endWithPipes("NONE");
					}
				}
	
				if (activeFormatRestrictionCode.endsWith(DELIMITER)) {
					activeFormatRestrictionCode = activeFormatRestrictionCode.substring(0,
							activeFormatRestrictionCode.length() - 1);
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("Parse Exception pe="+e);
			e.printStackTrace();
		}catch(Exception e){
			System.out.println("Exception e="+e);
			e.printStackTrace();
		}
		addField(activeFormatRestrictionCode);
	}
	
	private void handleFormatRestrictionCode() {
		String formatRestrictionCode = "";
		if (null != curMetaData.getFormatRestrictions()
				&& !curMetaData.getFormatRestrictions().isEmpty()) {
			List<FormatRestriction> formatRestrictions = curMetaData
					.getFormatRestrictions();
			for (FormatRestriction curFormatRestriction : formatRestrictions) {
				if (null != curFormatRestriction.getCode()
						&& !curFormatRestriction.getCode().isEmpty()) {
					formatRestrictionCode = formatRestrictionCode
							+ endWithPipes(curFormatRestriction.getCode());
				} else {
					formatRestrictionCode += endWithPipes("NONE");
				}
			}

			if (formatRestrictionCode.endsWith(DELIMITER)) {
				formatRestrictionCode = formatRestrictionCode.substring(0,
						formatRestrictionCode.length() - 1);
			}
		}
		addField(formatRestrictionCode);
	}
	
	private void handleFormatRestrictionDesc() {
		String formatRestrictionDesc = "";
		if (null != curMetaData.getFormatRestrictions()
				&& !curMetaData.getFormatRestrictions().isEmpty()) {
			List<FormatRestriction> formatRestrictions = curMetaData
					.getFormatRestrictions();
			for (FormatRestriction curFormatRestriction : formatRestrictions) {
				if (null != curFormatRestriction.getDesc()
						&& !curFormatRestriction.getDesc().isEmpty()) {
					formatRestrictionDesc = formatRestrictionDesc
							+ endWithPipes(curFormatRestriction.getDesc());
				} else {
					formatRestrictionDesc += endWithPipes("NONE");
				}
			}

			if (formatRestrictionDesc.endsWith(DELIMITER)) {
				formatRestrictionDesc = formatRestrictionDesc.substring(0,
						formatRestrictionDesc.length() - 1);
			}
		}
		addField(formatRestrictionDesc);
	}

	private void handleFormatRestrictionStartDt() {
		String formatRestrictionStartDt = "";
		if (null != curMetaData.getFormatRestrictions()
				&& !curMetaData.getFormatRestrictions().isEmpty()) {
			List<FormatRestriction> formatRestrictions = curMetaData
					.getFormatRestrictions();
			for (FormatRestriction curFormatRestriction : formatRestrictions) {

				if (null != curFormatRestriction.getFormatRestrictionStartDt()
						&& !curFormatRestriction.getFormatRestrictionStartDt()
								.isEmpty()) {
					formatRestrictionStartDt = formatRestrictionStartDt
							+ endWithPipes(curFormatRestriction
									.getFormatRestrictionStartDt());
				} else {
					formatRestrictionStartDt += endWithPipes("NONE");
				}

			}
			if (formatRestrictionStartDt.endsWith(DELIMITER)) {
				formatRestrictionStartDt = formatRestrictionStartDt.substring(
						0, formatRestrictionStartDt.length() - 1);
			}
		}
		addField(formatRestrictionStartDt);
	}

	private void handleFormatRestrictionEndDt() {
		String formatRestrictionEndDt = "";
		if (null != curMetaData.getFormatRestrictions()
				&& !curMetaData.getFormatRestrictions().isEmpty()) {
			List<FormatRestriction> formatRestrictions = curMetaData
					.getFormatRestrictions();
			for (FormatRestriction curFormatRestriction : formatRestrictions) {

				if (null != curFormatRestriction.getFormatRestrictionEndDt()
						&& !curFormatRestriction.getFormatRestrictionEndDt()
								.isEmpty()) {
					formatRestrictionEndDt = formatRestrictionEndDt
							+ endWithPipes(curFormatRestriction
									.getFormatRestrictionEndDt());
				} else {
					formatRestrictionEndDt += endWithPipes("NONE");
				}
			}

			if (formatRestrictionEndDt.endsWith(DELIMITER)) {
				formatRestrictionEndDt = formatRestrictionEndDt.substring(0,
						formatRestrictionEndDt.length() - 1);
			}
		}
		addField(formatRestrictionEndDt);
	}

	private void handleExternalId() {
		String externalId = "";

		if (null != curMetaData.getExternalId()
				&& !curMetaData.getExternalId().isEmpty()) {
			externalId = curMetaData.getExternalId();
		}

		addField(externalId);
	}

	private void handleOpenAccessFlag() {
		String openAccessFlag = "";
		if (null != curMetaData.getOpenAccessFlag()
				&& !curMetaData.getOpenAccessFlag().isEmpty()) {
			openAccessFlag = curMetaData.getOpenAccessFlag();
			if (openAccessFlag.equals("TRUE")) {
				openAccessFlag = "Y";
			}
			if (openAccessFlag.equals("FALSE")) {
				openAccessFlag = "N";
			}
		}

		addField(openAccessFlag);
	}

	private void handleAuthorCitizenship() {
		List<Author> authors = curMetaData.getAuthors();
		String authorCitizenship = "";
		if (null != authors && !authors.isEmpty()) {
			for (Author curAuthor : authors) {
				if (null != curAuthor.getAuthorCitizenship()
						&& !curAuthor.getAuthorCitizenship().isEmpty()) {
					authorCitizenship += endWithPipes(curAuthor
							.getAuthorCitizenship());
				} else {
					authorCitizenship += endWithPipes("NONE");
				}
			}
			if (null != authorCitizenship && !authorCitizenship.isEmpty()) {
				authorCitizenship = authorCitizenship.substring(0,
						authorCitizenship.length() - 1);
			}
		}
		addField(authorCitizenship);
	}
	
	private void handlePubDate() {
		String pubDate = "";
		if (null != curMetaData.getPubDate()
				&& !curMetaData.getPubDate().isEmpty()) {
			pubDate = curMetaData.getPubDate();
		}
		addField(pubDate);
	}
	
	private void handleManuscriptMediaCode() {
		ManuscriptMedia manuscriptMedia = curMetaData.getManuscriptMedia();
		String manuscriptMediaCode = "";
		
		if(null != manuscriptMedia) {
			if(null != manuscriptMedia.getManuscriptMediaCode() && !manuscriptMedia.getManuscriptMediaCode().isEmpty()) {
				manuscriptMediaCode = manuscriptMedia.getManuscriptMediaCode();
			}
		}
		addField(manuscriptMediaCode);
	}
	
	private void handleManuscriptMediaDesc() {
		ManuscriptMedia manuscriptMedia = curMetaData.getManuscriptMedia();
		String manuscriptMediaDesc = "";
		
		if(null != manuscriptMedia) {
			if(null != manuscriptMedia.getManuscriptMediaDesc() && !manuscriptMedia.getManuscriptMediaDesc().isEmpty()) {
				manuscriptMediaDesc = manuscriptMedia.getManuscriptMediaDesc();
			}
		}
		addField(manuscriptMediaDesc);
	}
	
	private void handleAuthorLocCitizenship() {
		List<Author> authors = curMetaData.getAuthors();
		String authorLocCitizenship = "";
		if (null != authors && !authors.isEmpty()) {
			for (Author curAuthor : authors) {
				if (null != curAuthor.getAuthorLocCitizenship()
						&& !curAuthor.getAuthorLocCitizenship().isEmpty()) {
					authorLocCitizenship += endWithPipes(curAuthor
							.getAuthorLocCitizenship());
				} else {
					authorLocCitizenship += endWithPipes("NONE");
				}
			}
			if (null != authorLocCitizenship && !authorLocCitizenship.isEmpty()) {
				authorLocCitizenship = authorLocCitizenship.substring(0,
						authorLocCitizenship.length() - 1);
			}
		}
		addField(authorLocCitizenship);
	}
	
	
	private void handleSchoolLocCountry() {
		School school = curMetaData.getSchool();
		String schoolLocCountry = "";
		if (null != school) {
			if (null != school.getSchoolLocCountry()
					&& !school.getSchoolLocCountry().isEmpty())
				schoolLocCountry = school.getSchoolLocCountry();
		}
		addField(schoolLocCountry);
	}
	
	private void handleDCIRefs() {
		String dciRefs = "";
			if(null != curMetaData.getDciRefExistsFlag() && !curMetaData.getDciRefExistsFlag().isEmpty()) {
				dciRefs = curMetaData.getDciRefExistsFlag();
		}
		addField(dciRefs);		
	}

	private void handleCmteMember() {
		List<CmteMember> cmteMembers = curMetaData.getCmteMembers();
		String cmteMember = "";
		if (null != cmteMembers && !cmteMembers.isEmpty()) {
			for (CmteMember curMember : cmteMembers) {
				if (!curMember.getFullName().isEmpty())
				{
						cmteMember += endWithPipes(curMember
							.getFullName());
				}
					else {
						cmteMember += endWithPipes("");
				}
			}
			
			if (null != cmteMember && !cmteMember.isEmpty()) {
				cmteMember = cmteMember.substring(0,
						cmteMember.length() - 1);
			}
		}
		addField(cmteMember);
	}
	
	private void addField(String data) {
		if (data.trim() != ""){
			data = data.replaceAll("\"", "\"\"");
			curRecord = curRecord + "\"" + data + "\"" + ",";
		} else{
			curRecord = curRecord + data + ",";
		}
	}

	private String endWithPipes(String x) {
		return x.endsWith(";") ? x : x + DELIMITER;
	}

	private String endsWithPunctuationMark(String x) {
		return x.matches("^.+[\\.,\\?;:!]$") ? x : x + ".";
	}

}

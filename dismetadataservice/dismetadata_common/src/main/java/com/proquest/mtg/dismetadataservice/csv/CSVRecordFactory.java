package com.proquest.mtg.dismetadataservice.csv;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisor;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.DissLanguage;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.FormatRestriction;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Keyword;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.PdfStatus;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SalesRestriction;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.School;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Subject;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SuppFile;
import com.proquest.mtg.dismetadataservice.metadata.Author;
import com.proquest.mtg.dismetadataservice.metadata.Author.Degree;
import com.proquest.mtg.dismetadataservice.metadata.SGMLEntitySubstitution;
import com.proquest.mtg.dismetadataservice.metadata.TextNormalizer;

public class CSVRecordFactory {

	private static final String DELIMITER = "|";
	private DisPubMetaData curMetaData = null;
	private String curRecord = "";
	private final TextNormalizer abstractNormalizer = new TextNormalizer();
	
	private final LinkedHashMap<String, Method> kAllHeaders = new LinkedHashMap<String, Method>(); 

	public CSVRecordFactory() throws NoSuchMethodException, SecurityException, ClassNotFoundException {
		initTagMapping();
	}
	private void initTagMapping() throws NoSuchMethodException, SecurityException, ClassNotFoundException {
		kAllHeaders.put(CSVHeaders.kPubNumber, 
				CSVRecordFactory.class.getDeclaredMethod("handlePubNumber"));
		kAllHeaders.put(CSVHeaders.kVolumeIssue, 
				CSVRecordFactory.class.getDeclaredMethod("handleVolumneIssue"));
		kAllHeaders.put(CSVHeaders.kAdvisors, 
				CSVRecordFactory.class.getDeclaredMethod("handleAdvisors"));
		kAllHeaders.put(CSVHeaders.kIsbn, 
				CSVRecordFactory.class.getDeclaredMethod("handleISBN"));
		kAllHeaders.put(CSVHeaders.kPageNumber, 
				CSVRecordFactory.class.getDeclaredMethod("handlePageNumber"));
		kAllHeaders.put(CSVHeaders.kPageCount, 
				CSVRecordFactory.class.getDeclaredMethod("handlePageCount"));
		kAllHeaders.put(CSVHeaders.kPublisher, 
				CSVRecordFactory.class.getDeclaredMethod("handlePublisher"));
		kAllHeaders.put(CSVHeaders.kReferenceLocation,
				CSVRecordFactory.class.getDeclaredMethod("handleReferenceLocation"));
		kAllHeaders.put(CSVHeaders.kBritishLibraryNumber,
				CSVRecordFactory.class.getDeclaredMethod("handleBritishLibrary"));
		kAllHeaders.put(CSVHeaders.kExternalUrl, 
				CSVRecordFactory.class.getDeclaredMethod("handleExternalUrl"));
		kAllHeaders.put(CSVHeaders.kDissLangDesc, 
				CSVRecordFactory.class.getDeclaredMethod("handleDissLanguageDesc"));
		kAllHeaders.put(CSVHeaders.kDissLangCode,
				CSVRecordFactory.class.getDeclaredMethod("handleDissLanguageCode"));	
		kAllHeaders.put(CSVHeaders.kSchoolCode, 
				CSVRecordFactory.class.getDeclaredMethod("handleSchoolCode"));
		kAllHeaders.put(CSVHeaders.kSchoolName, 
				CSVRecordFactory.class.getDeclaredMethod("handleSchoolName"));
		kAllHeaders.put(CSVHeaders.kSchoolCountry, 
				CSVRecordFactory.class.getDeclaredMethod("handleSchoolCountry"));
		kAllHeaders.put(CSVHeaders.kSchoolState, 
				CSVRecordFactory.class.getDeclaredMethod("handleSchoolState"));
		kAllHeaders.put(CSVHeaders.kTitle, 
				CSVRecordFactory.class.getDeclaredMethod("handleTitle"));
		kAllHeaders.put(CSVHeaders.kEnglishTranslationOfTitle, 
				CSVRecordFactory.class.getDeclaredMethod("handleEnglishTranslationOfTitle"));
		kAllHeaders.put(CSVHeaders.kVariantTitle, 
				CSVRecordFactory.class.getDeclaredMethod("handleVariantTitle"));
		kAllHeaders.put(CSVHeaders.kAuthors, 
				CSVRecordFactory.class.getDeclaredMethod("handleMultipleAuthors"));
		kAllHeaders.put(CSVHeaders.kDegreeCode, 
				CSVRecordFactory.class.getDeclaredMethod("handleDegreeCodeForFirstAuthor"));
		kAllHeaders.put(CSVHeaders.kDegreeDesc, 
				CSVRecordFactory.class.getDeclaredMethod("handleDegreeDescForFirstAuthor"));
		kAllHeaders.put(CSVHeaders.kDegreeYear, 
				CSVRecordFactory.class.getDeclaredMethod("handleDegreeYearForFirstAuthor"));
		kAllHeaders.put(CSVHeaders.kAbstract, 
				CSVRecordFactory.class.getDeclaredMethod("handleAbstract"));
		kAllHeaders.put(CSVHeaders.kSubjectDesc, 
				CSVRecordFactory.class.getDeclaredMethod("handleSubjectDesc"));
		kAllHeaders.put(CSVHeaders.kSubjectCode, 
				CSVRecordFactory.class.getDeclaredMethod("handleSubjectCode"));
		kAllHeaders.put(CSVHeaders.kSubjectGroupDesc, 
				CSVRecordFactory.class.getDeclaredMethod("handleSubjectGroupDesc"));
		kAllHeaders.put(CSVHeaders.kHasSupplementalFiles, 
				CSVRecordFactory.class.getDeclaredMethod("hasSupplementalFiles"));		
		kAllHeaders.put(CSVHeaders.kSuppFileNames, 
				CSVRecordFactory.class.getDeclaredMethod("handleSupplemetalFileName"));
		kAllHeaders.put(CSVHeaders.kSuppFileDescription, 
				CSVRecordFactory.class.getDeclaredMethod("handleSupplemetalFileDesc"));
		kAllHeaders.put(CSVHeaders.kSuppFileCategory, 
				CSVRecordFactory.class.getDeclaredMethod("handleSupplemetalFilesCategory"));
		kAllHeaders.put(CSVHeaders.kDepartmentName, 
				CSVRecordFactory.class.getDeclaredMethod("handleDepartmentName"));
		kAllHeaders.put(CSVHeaders.kKeyword, 
				CSVRecordFactory.class.getDeclaredMethod("handleKeyWords"));
		kAllHeaders.put(CSVHeaders.kKeywordSource, 
				CSVRecordFactory.class.getDeclaredMethod("handleKeyWordSource"));
		kAllHeaders.put(CSVHeaders.kSalesRestrictionCode, 
				CSVRecordFactory.class.getDeclaredMethod("handleSalesRestrictionCode"));
		kAllHeaders.put(CSVHeaders.kSalesRestrictionDesc, 
				CSVRecordFactory.class.getDeclaredMethod("handleSalesRestrictionDesc"));
		kAllHeaders.put(CSVHeaders.kSalesRestrictionStartDate, 
				CSVRecordFactory.class.getDeclaredMethod("handleSalesRestrictionStartDate"));
		kAllHeaders.put(CSVHeaders.kSalesRestrictionEndDate, 
				CSVRecordFactory.class.getDeclaredMethod("handleSalesRestrictionEndDate"));
		kAllHeaders.put(CSVHeaders.kDissertationTypeCode, 
				CSVRecordFactory.class.getDeclaredMethod("handleDissertationTypeCode"));
		kAllHeaders.put(CSVHeaders.kDissertationCode, 
				CSVRecordFactory.class.getDeclaredMethod("handleDissertationCode"));
		kAllHeaders.put(CSVHeaders.kDAISectionCode, 
				CSVRecordFactory.class.getDeclaredMethod("handleDAISectionCode"));
		kAllHeaders.put(CSVHeaders.kHasPDF, 
				CSVRecordFactory.class.getDeclaredMethod("hasPDF"));
		kAllHeaders.put(CSVHeaders.kPDFAvailableDate, 
				CSVRecordFactory.class.getDeclaredMethod("handlePDFAvailableDate"));
		kAllHeaders.put(CSVHeaders.kFormatRestrictionCode, 
				CSVRecordFactory.class.getDeclaredMethod("handleFormatRestrictionCode"));
	}
	
	public LinkedHashMap<String, Method> getTagMappings() {
		return kAllHeaders;
	}
	public ImmutableList<String> getHeaders() {
		return ImmutableList.copyOf(kAllHeaders.keySet());
	}
	public String makeFrom(DisPubMetaData metaData)	throws IllegalAccessException, 
			IllegalArgumentException, InvocationTargetException   {
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
		String newLine = System.lineSeparator();
		for (String key : kAllHeaders.keySet()) {
			curRecord += key + ",";
		}
		curRecord += newLine;
	}

	private void handlePubNumber() {
		String pub = "";
		if (null != curMetaData.getPubNumber()
				&& !curMetaData.getPubNumber().isEmpty()) {
			pub = curMetaData.getPubNumber();
		}
		addField(pub);
	}

	private void handleVolumneIssue() {
		String dissVolume = curMetaData.getBatch() != null ? curMetaData
				.getBatch().getVolumeIssue() : "";
		if ( null == dissVolume)
			dissVolume = "";
		addField(dissVolume);
	}

	private void handleAdvisors() {
		List<Advisor> dissAdvisors = curMetaData.getAdvisors() != null ? curMetaData
				.getAdvisors().getAdvisor() : null;
		String adviserName = "";
		if (dissAdvisors != null && !dissAdvisors.isEmpty()) {
			for (Advisor curAdvisor : dissAdvisors) {
				String formattedAdviserName = SGMLEntitySubstitution
						.applyAllTo(curAdvisor.getAdvisorFullName().trim());
				adviserName += endWithPipes(formattedAdviserName);
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

	private void handleBritishLibrary() {
		String blNumber = "";
		if (null != curMetaData.getBLNumber()
				&& !curMetaData.getBLNumber().isEmpty()) {
			blNumber = curMetaData.getBLNumber();
		}
		addField(blNumber);

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
			schoolName = school.getSchoolName();
		}
		addField(schoolName);
	}
	
	private void handleSchoolCode() {
		School school = curMetaData.getSchool();
		String schoolCode = "";
		if (null != school) {
			schoolCode = school.getSchoolCode();
		}
		addField(schoolCode);
	}
	
	private void handleSchoolCountry() {
		School school = curMetaData.getSchool();
		String schoolCountry = "";
		if (null != school) {
			schoolCountry = school.getSchoolCountry();
		}
		addField(schoolCountry);
	}
	
	private void handleSchoolState() {
		School school = curMetaData.getSchool();
		String schoolState = "";
		if (null != school) {
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
			title = SGMLEntitySubstitution.applyAllTo(title).trim();
			title = endsWithPunctuationMark(title);
		}
		addField(title);
	}

	private void handleVariantTitle() {
		String title = "";
		String variantTitle = curMetaData.getTitle() != null ? curMetaData
				.getTitle().getEnglishOverwriteTitle() : null;
		if (null != variantTitle && !variantTitle.isEmpty()) {
			title = SGMLEntitySubstitution.applyAllTo(variantTitle);
		}

		addField(title);
	}

	private void handleTitle() {
		String title = getTitleToInclude();
		if (null != title && !title.isEmpty()) {
			title = endsWithPunctuationMark(title);
			title = SGMLEntitySubstitution.applyAllTo(title);
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
				}
			}
			if (null != authorNames && !authorNames.isEmpty()) {
				authorNames = authorNames.substring(0, authorNames.length() - 1);
			}
		}
		addField(authorNames);
	}

	private void handleDegreeCodeForFirstAuthor() {
		List<Author> authors = curMetaData.getAuthors();
		List<Degree> degrees = null;
		String degreeCode = "";
		
		if(null != authors && ! authors.isEmpty()) {
			degrees = authors.get(0).getDegrees(); 
			if (degrees != null && !degrees.isEmpty()) {
				for (Degree curDegree : degrees) {
					degreeCode += endWithPipes(curDegree.getDegreeCode());
				}
				if (degreeCode.endsWith(DELIMITER)) {
					degreeCode = degreeCode.substring(0, degreeCode.length() - 1); 
				}
			}
		}
		addField(degreeCode);
	}
	
	private void handleDegreeDescForFirstAuthor() {
		List<Author> authors = curMetaData.getAuthors();
		List<Degree> degrees = null;
		String degreeDesc = "";
		
		if(null != authors && ! authors.isEmpty()) {
			degrees = authors.get(0).getDegrees(); 
			if (degrees != null && !degrees.isEmpty()) {
				for (Degree curDegree : degrees) {
					if (null != curDegree.getDegreeDescription())
					degreeDesc += endWithPipes(curDegree.getDegreeDescription());
				}
				if (degreeDesc.endsWith(DELIMITER)) {
					degreeDesc = degreeDesc.substring(0, degreeDesc.length() - 1); 
				}
			}
		}
		addField(degreeDesc);
	}
	
	private void handleDegreeYearForFirstAuthor() {
		List<Author> authors = curMetaData.getAuthors();
		List<Degree> degrees = null;
		String degreeYear = "";
		
		if(null != authors && ! authors.isEmpty()) {
			degrees = authors.get(0).getDegrees(); 
			if (degrees != null && !degrees.isEmpty()) {
				for (Degree curDegree : degrees) {
					if (null != curDegree.getDegreeYear())
						degreeYear += endWithPipes(curDegree.getDegreeYear());
				}
				if (degreeYear.endsWith(DELIMITER)) {
					degreeYear = degreeYear.substring(0, degreeYear.length() - 1); 
				}
			}
		}
		addField(degreeYear);		
	}

	private void handleAbstract() {
		String abstractText = "";
		if (null != curMetaData.getAbstract()
				&& !curMetaData.getAbstract().isEmpty()) {
			abstractText = abstractNormalizer.applyTo(curMetaData.getAbstract());
		}
		addField(abstractText);
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
					subjGroupDesc += endWithPipes(curSubject.getSubjectGroupDesc());
				}
			}
			if (subjGroupDesc.endsWith(DELIMITER)) {
				subjGroupDesc = subjGroupDesc.substring(0, subjGroupDesc.length() - 1);
			}
		}
		addField(subjGroupDesc);
	}

	private void hasSupplementalFiles() {
		String hasSuppFiles = "N";
		if (null != curMetaData.getSuppFiles()
				&& !curMetaData.getSuppFiles().isEmpty()) {
			hasSuppFiles = "Y";
		}
		addField(hasSuppFiles);
	}

	private void handleSupplemetalFileName() {
		List<SuppFile> suppFiles = curMetaData.getSuppFiles();
		String suppFileName = "";
	
		if (null != suppFiles && ! suppFiles.isEmpty()) {
			for (SuppFile curSuppFile : suppFiles) {
				suppFileName += endWithPipes(curSuppFile.getSuppFilename());
			}
			if (suppFileName.endsWith(DELIMITER)) {
				suppFileName = suppFileName.substring(0,
						suppFileName.length() - 1);
			}
		}
		addField(suppFileName);
	}
	
	private void handleSupplemetalFileDesc() {
		List<SuppFile> suppFiles = curMetaData.getSuppFiles();
		String suppFileDescription = "";
		if (null != suppFiles && ! suppFiles.isEmpty()) {
			for (SuppFile curSuppFile : suppFiles) {
				suppFileDescription += endWithPipes(curSuppFile.getSuppFileDesc());
			}
			if (suppFileDescription.endsWith(DELIMITER)) {
				suppFileDescription = suppFileDescription.substring(0,
						suppFileDescription.length() - 1);
			}
		}
		addField(suppFileDescription);
	}
	private void handleSupplemetalFilesCategory() {
		List<SuppFile> suppFiles = curMetaData.getSuppFiles();
		String suppFileCategory = "";
		if (null != suppFiles && !curMetaData.getSuppFiles().isEmpty()) {
			for (SuppFile curSuppFile : suppFiles) {
				suppFileCategory += endWithPipes(curSuppFile.getSuppFileCategory());
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
			if (deptName.endsWith(DELIMITER))
				deptName = deptName.substring(0, deptName.length() - 1);
		}
		addField(deptName);
	}

	private void handleKeyWords() {
		List<Keyword> keywords = curMetaData.getKeywords();
		String keyword = "";

		if (null != keywords && ! keywords.isEmpty()) {			
			for (Keyword curKeyword : keywords) {
				keyword += endWithPipes(curKeyword.getValue());
			}
			if (keyword.endsWith(DELIMITER)) {
				keyword = keyword.substring(0, keyword.length() - 1);
			}
		}
		addField(keyword);
	}
	
	private void handleKeyWordSource() {
		List<Keyword> keywords= curMetaData.getKeywords();
		String keywordSource = "";
		if (null != keywords && ! keywords.isEmpty()) {
			for (Keyword curKeyword : keywords) {
				keywordSource += endWithPipes(curKeyword.getSource());
			}
			if (keywordSource.endsWith(DELIMITER)) {
				keywordSource = keywordSource.substring(0, keywordSource.length() - 1);
			}
		}
		addField(keywordSource);
	}

	private void handleSalesRestrictionCode() {
		List<SalesRestriction> salesRestrictions = curMetaData.getSalesRestrictions();
		String salesRestrictionCode = "";
		
		if (null != salesRestrictions && ! salesRestrictions.isEmpty()) {
			for (SalesRestriction currSalesRestriction : salesRestrictions) {
				if (null != currSalesRestriction.getCode()
						&& !currSalesRestriction.getCode().isEmpty()) {
					salesRestrictionCode += endWithPipes(
							currSalesRestriction.getCode());
				}
			}
			if (salesRestrictionCode.endsWith(DELIMITER)) {
				salesRestrictionCode = salesRestrictionCode.substring(
						0, salesRestrictionCode.length() - 1);
			}
		}
		addField(salesRestrictionCode);
	}
	
	private void handleSalesRestrictionDesc() {
		List<SalesRestriction> salesRestrictions = curMetaData.getSalesRestrictions();
		String salesRestrictionDesc = "";
		if (null != salesRestrictions && !salesRestrictions.isEmpty()) {
			for (SalesRestriction currSalesRestriction : salesRestrictions) {
				if (null != currSalesRestriction.getDescription()
						&& !currSalesRestriction.getDescription().isEmpty()) {
					salesRestrictionDesc += endWithPipes(
							currSalesRestriction.getDescription());
				}
			}
			if (salesRestrictionDesc.endsWith(DELIMITER)) {
				salesRestrictionDesc = salesRestrictionDesc.substring(
						0, salesRestrictionDesc.length() - 1);
			}
		}
		addField(salesRestrictionDesc);
	}
	
	private void handleSalesRestrictionStartDate() {
		List<SalesRestriction> salesRestrictions = curMetaData.getSalesRestrictions();
		String salesRestrictionStartDate = "";
		if (null != salesRestrictions
				&& ! salesRestrictions.isEmpty()) {
			for (SalesRestriction currSalesRestriction : salesRestrictions) {
				if (null != currSalesRestriction.getRestrictionStartDate()
						&& !currSalesRestriction.getRestrictionStartDate().isEmpty()) {
					salesRestrictionStartDate += endWithPipes(
							currSalesRestriction.getRestrictionStartDate());
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
		List<SalesRestriction> salesRestrictions = curMetaData.getSalesRestrictions();
		String salesRestrictionEndDate = "";
		if (null != curMetaData.getSalesRestrictions()
				&& !curMetaData.getSalesRestrictions().isEmpty()) {
			for (SalesRestriction currSalesRestriction : salesRestrictions) {
				if (null != currSalesRestriction.getRestrictionEndDate()
						&& !currSalesRestriction.getRestrictionEndDate().isEmpty()) {
					salesRestrictionEndDate += endWithPipes(
							currSalesRestriction.getRestrictionEndDate());
				}
			}
			if (salesRestrictionEndDate.endsWith(DELIMITER)) {
				salesRestrictionEndDate = salesRestrictionEndDate.substring(
						0, salesRestrictionEndDate.length() - 1);
			}
		}
		addField(salesRestrictionEndDate);
	}

	private void handleDissertationTypeCode() {
		String dissTypeCode = curMetaData.getBatch() != null ? curMetaData
				.getBatch().getDBTypeCode() : "";
		if (null == dissTypeCode)
			dissTypeCode = "";
		addField(dissTypeCode);
	}

	private void handleDissertationCode() {
		String dissTypeDesc = curMetaData.getBatch() != null ? curMetaData
				.getBatch().getDBTypeDesc() : "";

		if (null == dissTypeDesc)
			dissTypeDesc = "";
		addField(dissTypeDesc);
	}

	private void handleDAISectionCode() {
		String daiSectionCode = curMetaData.getBatch() != null ? curMetaData
				.getBatch().getDAISectionCode() : "";
		if (null == daiSectionCode)
			daiSectionCode = "";
		addField(daiSectionCode);
	}
	
	private void hasPDF() {
		PdfStatus pdfStatus = curMetaData.getPdfStatus();
		String hasPDF = "N";
		if (null != pdfStatus) {
			if (pdfStatus.isPdfAvailable()) {
				hasPDF = "Y";
			}
		}
		addField(hasPDF);
	}
	private void handlePDFAvailableDate() {
		PdfStatus pdfStatus = curMetaData.getPdfStatus();
		String pdfAvailableDate = "";
		if (null != pdfStatus) {
			if (pdfStatus.isPdfAvailable()) {
				pdfAvailableDate = curMetaData.getPdfStatus().getPdfAvailableDate();
			}
		}
		addField(pdfAvailableDate);
	}

	private void handleFormatRestrictionCode() {
		String formatRestrictionCode = "";

		if (null != curMetaData.getFormatRestrictions()
				&& !curMetaData.getFormatRestrictions().isEmpty()) {
			List<FormatRestriction> formatRestrictions = curMetaData
					.getFormatRestrictions();
			for (FormatRestriction curFormatRestriction : formatRestrictions) {
				if (null != curFormatRestriction.getCode()
						&& !curFormatRestriction.getCode().isEmpty())
					formatRestrictionCode = formatRestrictionCode + endWithPipes(curFormatRestriction.getCode());
			}

			if (formatRestrictionCode.endsWith(DELIMITER))
				formatRestrictionCode = formatRestrictionCode.substring(0,
						formatRestrictionCode.length() - 1);
		}
		addField(formatRestrictionCode);
	}

	private void addField(String data) {
		if (data.trim() != "")
			curRecord = curRecord + "\"" + data + "\"" + ",";
		else
			curRecord = curRecord + data + ",";
	}

	private String endWithPipes(String x) {
		return x.endsWith(";") ? x : x + DELIMITER;
	}

	private String endsWithPunctuationMark(String x) {
		return x.matches("^.+[\\.,\\?;:!]$") ? x : x + ".";
	}

}

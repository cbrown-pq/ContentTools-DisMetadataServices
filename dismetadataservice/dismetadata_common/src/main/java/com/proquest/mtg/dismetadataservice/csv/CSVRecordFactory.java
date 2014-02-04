package com.proquest.mtg.dismetadataservice.csv;

import java.util.List;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisor;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.DissLanguage;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.FormatRestriction;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Keyword;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.PdfStatus;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SalesRestriction;
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

	public String makeFrom(DisPubMetaData metaData) {
		if (null == metaData) {
			throw new IllegalArgumentException("metaData is null");
		}
		createHeader();

		curMetaData = metaData;
		handlePubNumber();
		handleVolumneIssue();
		handleAdvisors();
		handleISBN();
		handlePages();
		handlePublisher();
		handleReferenceLocation();
		handleBritishLibrary();
		handleExternalUrl();
		handleDissLanguages();
		handleSchools();
		handleTitle();
		handleEnglishTranslationOfTitle();
		handleVariantTitle();
		handleMultipleAuthors();
		handleDegrees();
		handleAbstract();
		handleSubjects();
		hasSupplementalFiles();
		handleSupplemetalFiles();
		handleDepartmentName();
		handleKeyWords();
		handleSalesRestrictions();
		handleDissertationTypeCode();
		handleDissertationCode();
		handleDAISectionCode();
		handlePDF();
		handleFormatRestrictionCode();

		return curRecord;
	}

	private void createHeader() {

		for (String header : CSVHeaders.kAllHeaders) {
			curRecord += header + ",";
		}
		curRecord += "\n";
	}

	private void handlePubNumber() {
		String pub = "";
		if (null != curMetaData.getPubNumber()
				&& !curMetaData.getPubNumber().isEmpty())
			pub = curMetaData.getPubNumber();
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
			if (adviserName.endsWith(DELIMITER))
				adviserName = adviserName
						.substring(0, adviserName.length() - 1);
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

	private void handlePages() {
		String pageNumber = "";
		String pageCount = "";
		if (null != curMetaData.getPubPageNum()
				&& !curMetaData.getPubPageNum().isEmpty()) {
			pageNumber = curMetaData.getPubPageNum();
		}
		addField(pageNumber);
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

	private void handleDissLanguages() {
		List<DissLanguage> dissLanguages = curMetaData.getDissLanguages();
		String dissLanguageDesc = "";
		String dissLanguageCode = "";
		if (dissLanguages != null && !dissLanguages.isEmpty()) {
			for (DissLanguage curDissLanguage : dissLanguages) {
				dissLanguageDesc += endWithPipes(curDissLanguage
						.getLanguageDescription());
				dissLanguageCode += endWithPipes(curDissLanguage
						.getLanguageCode());
			}
			if (dissLanguageDesc.endsWith(DELIMITER))
				dissLanguageDesc = dissLanguageDesc.substring(0,
						dissLanguageDesc.length() - 1);
			if (dissLanguageCode.endsWith(DELIMITER))
				dissLanguageCode = dissLanguageCode.substring(0,
						dissLanguageCode.length() - 1);
		}
		addField(dissLanguageDesc);
		addField(dissLanguageCode);
	}

	private void handleSchools() {
		String schoolName = curMetaData.getSchool() != null ? curMetaData
				.getSchool().getSchoolName() : "";
		String schoolCode = curMetaData.getSchool() != null ? curMetaData
				.getSchool().getSchoolCode() : "";
		String schoolCountry = curMetaData.getSchool() != null ? curMetaData
				.getSchool().getSchoolCountry() : "";
		String schoolState = curMetaData.getSchool() != null ? curMetaData
				.getSchool().getSchoolState() : "";
		if (null != schoolName && !schoolName.isEmpty())
			schoolName = curMetaData.getSchool().getSchoolName();
		if (null != schoolCode && !schoolCode.isEmpty())
			schoolCode = curMetaData.getSchool().getSchoolCode();
		if (null != schoolCountry && !schoolCountry.isEmpty())
			schoolCountry = curMetaData.getSchool().getSchoolCountry();
		if (null != schoolState && !schoolState.isEmpty())
			schoolState = curMetaData.getSchool().getSchoolState();

		addField(schoolName);
		addField(schoolCode);
		addField(schoolCountry);
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
						&& !curAuthor.getAuthorFullName().isEmpty())
					authorNames += endWithPipes(curAuthor.getAuthorFullName());
			}
			if (null != authorNames && !authorNames.isEmpty())
				authorNames = authorNames
						.substring(0, authorNames.length() - 1);
		}
		addField(authorNames);
	}

	private void handleDegrees() {
		List<Degree> degrees = curMetaData.getAuthors() != null
				&& !curMetaData.getAuthors().isEmpty() ? curMetaData
				.getAuthors().get(0).getDegrees() : null;
		String degreeDesc = "";
		String degreeCode = "";
		String degreeYear = "";
		if (degrees != null && !degrees.isEmpty()) {
			for (Degree curDegree : degrees) {
				if (null != curDegree.getDegreeDescription()
						&& !curDegree.getDegreeDescription().isEmpty())
					degreeDesc += endWithPipes(curDegree.getDegreeDescription());
				degreeCode += endWithPipes(curDegree.getDegreeCode());
				degreeYear += endWithPipes(curDegree.getDegreeYear());
			}
			if (degreeDesc.endsWith(DELIMITER))
				degreeDesc = degreeDesc.substring(0, degreeDesc.length() - 1);
			if (degreeCode.endsWith(DELIMITER))
				degreeCode = degreeCode.substring(0, degreeCode.length() - 1);
			if (degreeYear.endsWith(DELIMITER))
				degreeYear = degreeYear.substring(0, degreeYear.length() - 1);
		}
		addField(degreeCode);
		addField(degreeDesc);
		addField(degreeYear);
	}

	private void handleAbstract() {
		String abstractText = "";
		if (null != curMetaData.getAbstract()
				&& !curMetaData.getAbstract().isEmpty()) {
			abstractText = abstractNormalizer
					.applyTo(curMetaData.getAbstract());
		}
		addField(abstractText);
	}

	private void handleSubjects() {
		List<Subject> subjects = curMetaData.getSubjects();
		String subjDesc = "";
		String subjCode = "";
		String subjGroupDesc = "";
		if (subjects != null && !subjects.isEmpty()) {
			for (Subject curSubject : subjects) {
				if (null != curSubject.getSubjectDesc()
						&& !curSubject.getSubjectDesc().isEmpty())
					subjDesc += endWithPipes(curSubject.getSubjectDesc());

				if (null != curSubject.getSubjectCode()
						&& !curSubject.getSubjectCode().isEmpty())
					subjCode += endWithPipes(curSubject.getSubjectCode());
				if (null != curSubject.getSubjectGroupDesc()
						&& !curSubject.getSubjectGroupDesc().isEmpty())
					subjGroupDesc += endWithPipes(curSubject
							.getSubjectGroupDesc());
			}
			if (subjDesc.endsWith(DELIMITER))
				subjDesc = subjDesc.substring(0, subjDesc.length() - 1);
			if (subjCode.endsWith(DELIMITER))
				subjCode = subjCode.substring(0, subjCode.length() - 1);
			if (subjGroupDesc.endsWith(DELIMITER))
				subjGroupDesc = subjGroupDesc.substring(0,
						subjGroupDesc.length() - 1);
		}
		addField(subjDesc);
		addField(subjCode);
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

	private void handleSupplemetalFiles() {
		String suppFileName = "";
		String suppFileDescription = "";
		String suppFileCategory = "";
		if (null != curMetaData.getSuppFiles()
				&& !curMetaData.getSuppFiles().isEmpty()) {
			List<SuppFile> suppFiles = curMetaData.getSuppFiles();
			for (SuppFile curSuppFile : suppFiles) {
				suppFileName += endWithPipes(curSuppFile.getSuppFilename());
				suppFileDescription += endWithPipes(curSuppFile
						.getSuppFileDesc());
				suppFileCategory += endWithPipes(curSuppFile
						.getSuppFileCategory());
			}
			if (suppFileName.endsWith(DELIMITER))
				suppFileName = suppFileName.substring(0,
						suppFileName.length() - 1);
			if (suppFileDescription.endsWith(DELIMITER))
				suppFileDescription = suppFileDescription.substring(0,
						suppFileDescription.length() - 1);
			if (suppFileCategory.endsWith(DELIMITER))
				suppFileCategory = suppFileCategory.substring(0,
						suppFileCategory.length() - 1);
		}
		addField(suppFileName);
		addField(suppFileDescription);
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
		String keyword = "";
		String keywordSource = "";

		if (null != curMetaData.getKeywords()
				&& !curMetaData.getKeywords().isEmpty()) {
			List<Keyword> keywords = curMetaData.getKeywords();
			for (Keyword curKeyword : keywords) {
				keyword = keyword + endWithPipes(curKeyword.getValue());
				keywordSource = keywordSource + endWithPipes(curKeyword.getSource());
			}
			if (keyword.endsWith(DELIMITER))
				keyword = keyword.substring(0, keyword.length() - 1);
			if (keywordSource.endsWith(DELIMITER))
				keywordSource = keywordSource.substring(0,
						keywordSource.length() - 1);
		}
		addField(keyword);
		addField(keywordSource);
	}

	private void handleSalesRestrictions() {
		String salesRestrictionCode = "";
		String salesRestrictionDesc = "";
		String salesRestrictionStartDate = "";
		String salesRestrictionEndDate = "";
		if (null != curMetaData.getSalesRestrictions()
				&& !curMetaData.getSalesRestrictions().isEmpty()) {
			List<SalesRestriction> salesRestrictions = curMetaData
					.getSalesRestrictions();
			for (SalesRestriction currSalesRestriction : salesRestrictions) {
				if (null != currSalesRestriction.getCode()
						&& !currSalesRestriction.getCode().isEmpty())
					salesRestrictionCode = salesRestrictionCode + endWithPipes(currSalesRestriction
							.getCode());
				if (null != currSalesRestriction.getDescription()
						&& !currSalesRestriction.getDescription().isEmpty())
					salesRestrictionDesc = salesRestrictionDesc + endWithPipes(currSalesRestriction
							.getDescription());
				if (null != currSalesRestriction.getRestrictionStartDate()
						&& !currSalesRestriction.getRestrictionStartDate()
								.isEmpty())
					salesRestrictionStartDate = salesRestrictionStartDate + endWithPipes(currSalesRestriction
							.getRestrictionStartDate());
				if (null != currSalesRestriction.getRestrictionEndDate()
						&& !currSalesRestriction.getRestrictionEndDate()
								.isEmpty())
					salesRestrictionEndDate = salesRestrictionEndDate + endWithPipes(currSalesRestriction
							.getRestrictionEndDate());
			}
			if (salesRestrictionCode.endsWith(DELIMITER))
				salesRestrictionCode = salesRestrictionCode.substring(0,
						salesRestrictionCode.length() - 1);
			if (salesRestrictionDesc.endsWith(DELIMITER))
				salesRestrictionDesc = salesRestrictionDesc.substring(0,
						salesRestrictionDesc.length() - 1);
			if (salesRestrictionStartDate.endsWith(DELIMITER))
				salesRestrictionStartDate = salesRestrictionStartDate 
						.substring(0, salesRestrictionStartDate.length() - 1);
			if (salesRestrictionEndDate.endsWith(DELIMITER))
				salesRestrictionEndDate = salesRestrictionEndDate.substring(0,
						salesRestrictionEndDate.length() - 1);
		}
		addField(salesRestrictionCode);
		addField(salesRestrictionDesc);
		addField(salesRestrictionStartDate);
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

	private void handlePDF() {
		PdfStatus pdfStatus = curMetaData.getPdfStatus() != null ? curMetaData
				.getPdfStatus() : null;
		String hasPDF = "N";
		String pdfAvailableDate = "";

		if (null != pdfStatus) {
			if (pdfStatus.isPdfAvailable()) {
				hasPDF = "Y";
				pdfAvailableDate = curMetaData.getPdfStatus()
						.getPdfAvailableDate();
			}
		}
		addField(hasPDF);
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
					formatRestrictionCode = curFormatRestriction.getCode();
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

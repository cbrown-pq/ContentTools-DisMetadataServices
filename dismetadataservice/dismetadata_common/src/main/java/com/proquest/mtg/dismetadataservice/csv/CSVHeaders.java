package com.proquest.mtg.dismetadataservice.csv;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

public class CSVHeaders {
	public static final String kPubNumber = "PUB NUMBER";
	public static final String kVolumeIssue = "VOLUME ISSUE";
	public static final String kAdvisors = "ADVISORS";
	public static final String kIsbn = "ISBN";
	public static final String kPageNumber = "PAGE NUMBER";
	public static final String kPageCount = "PAGE COUNT";
	public static final String kPublisher = "PUBLISHER";
	public static final String kReferenceLocation = "REFERENCE";
	public static final String kBritishLibraryNumber = "BL NUMBER";
	public static final String kExternalUrl = "EXTERNAL URL";
	public static final String kDissLangDesc = "DISS LANGUAGE DESCRIPTION";
	public static final String kDissLangCode = "DISS LANGUAGE CODE";
	public static final String kSchoolCode = "SCHOOL CODE";
	public static final String kSchoolName = "SCHOOL NAME";
	public static final String kSchoolCountry = "SCHOOL COUNTRY";
	public static final String kSchoolState = "SCHOOL STATE";
	public static final String kTitle = "TITLE";
	public static final String kEnglishTranslationOfTitle = "ENGLISH TITLE";
	public static final String kVariantTitle = "VARIANT TITLE";
	public static final String kAuthors = "AUTHORS";
	public static final String kDegreeDesc = "DEGREE";
	public static final String kDegreeYear = "DEGREE YEAR";
	public static final String kDegreeCode = "DEGREE CODE";
	public static final String kAbstract = "ABSTRACT";
	public static final String kSubjectDesc = "SUBJ DESC";
	public static final String kSubjectCode = "SUBJ CODE";
	public static final String kSubjectGroupDesc = "SUBJ GROUP DESC";
	public static final String kHasSupplementalFiles = "HAS SUPP FILES";
	public static final String kSuppFileNames = "SUPPLEMENTAL FILE NAMES";
	public static final String kSuppFileDescription = "SUPPLEMENTAL FILE DESCRIPTION";
	public static final String kSuppFileCategory = "SUPPLEMENTAL FILE CATEGORY";
	public static final String kDepartmentName = "DEPARTMENT";
	public static final String kKeyword = "KEYWORD";
	public static final String kKeywordSource = "KEYWORD SOURCE";
	public static final String kSalesRestrictionCode = "SALES RESTRICTION CODE";
	public static final String kSalesRestrictionDesc = "SALES RESTRICTION DESC";
	public static final String kSalesRestrictionStartDate = "SALES RESTRICTION STARTDT";
	public static final String kSalesRestrictionEndDate = "SALES RESTRICTION ENDDT";
	public static final String kDissertationTypeCode = "DISS TYPE CODE";
	public static final String kDissertationCode = "DISS CODE";
	public static final String kDAISectionCode = "DAI SECTION CODE";
	public static final String kHasPDF = "PDF AVAILABLE";
	public static final String kPDFAvailableDate = "PDF AVAILABLE DATE";
	public static final String kFormatRestrictionCode = "FORMAT RESTRICTION CODE";

	public static final Set<String> kAllHeaders = ImmutableSet.of(kPubNumber,
			kVolumeIssue, kAdvisors, kIsbn, kPageNumber, kPageCount,
			kPublisher, kReferenceLocation, kBritishLibraryNumber,
			kExternalUrl, kDissLangDesc, kDissLangCode, kSchoolCode,
			kSchoolName, kSchoolCountry, kSchoolState, kTitle,
			kEnglishTranslationOfTitle, kVariantTitle, kAuthors, kDegreeDesc,
			kDegreeYear, kDegreeCode, kAbstract, kSubjectDesc, kSubjectCode,
			kSubjectGroupDesc, kHasSupplementalFiles, kSuppFileNames,
			kSuppFileDescription, kSuppFileCategory, kDepartmentName, kKeyword,
			kKeywordSource, kSalesRestrictionCode, kSalesRestrictionDesc,
			kSalesRestrictionStartDate, kSalesRestrictionEndDate,
			kDissertationTypeCode, kDissertationCode, kDAISectionCode, kHasPDF,
			kPDFAvailableDate, kFormatRestrictionCode);

}

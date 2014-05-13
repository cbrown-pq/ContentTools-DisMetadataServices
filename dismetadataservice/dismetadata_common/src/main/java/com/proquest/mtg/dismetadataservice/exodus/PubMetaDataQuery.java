package com.proquest.mtg.dismetadataservice.exodus;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisor;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisors;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.AlternateTitle;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Batch;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.CmteMember;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.DissLanguage;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.FormatRestriction;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Keyword;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.PdfStatus;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SalesRestriction;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.School;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Subject;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SuppFile;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Title;
import com.proquest.mtg.dismetadataservice.metadata.Author;
import com.proquest.mtg.dismetadataservice.metadata.Author.Degree;
import com.proquest.mtg.dismetadataservice.metadata.SplitAuthorNames;

public class PubMetaDataQuery {
	public static final String kEmptyValue = "";
	
	private static final SimpleDateFormat kDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private static final String kColumnItemId = "ItemId";
	private static final String kColumnVolumeIssueId = "VolumeIssueId";
	private static final String kColumnPubId = "PubNumber";
	private static final String kColumnAdvisors = "Advisors";
	private static final String kColumnIsbn = "Isbn";
	private static final String kColumnPageNumber = "PageNumber";
	private static final String kColumnPageCount = "PageCount";
	private static final String kColumnPublisher = "Publisher";
	private static final String kColumnReferenceLocation = "ReferenceLocation";
	private static final String kColumnBritishLibraryNumber = "BritishLibraryNumber";
	private static final String kColumnSource = "Source";
	private static final String kColumnExternalUrl = "ExternalUrl";
	private static final String kColumnLanguageDescription = "LanguageDescription";
	private static final String kColumnLanguageCode = "LanguageCode";
	private static final String kColumnMasterTitle = "MasterTitle";
	private static final String kColumnElectronicTitle = "ElectronicTitle";
	private static final String kColumnEngOverwriteTitle = "EnglishOevrwriteTitle";
	private static final String kColumnForeignTitle = "ForeignTitle";
	
	private static final String kColumnAuthorId = "AuthorId"; 
	private static final String kColumnAuthorSequenceNumber = "AuthorSequenceNumber"; 
	private static final String kColumnAuthorFullName = "AuthorFullName";
	private static final String kColumnAuthorAlternateFullName = "AuthorAlternateFullName";
	private static final String kColumnAuthorCitizenship = "AuthorCitizenship"; 
	
	private static final String kColumnDegreeCode = "DegreeCode";
	private static final String kColumnDegreeDescription = "DegreeDescription";
	private static final String kColumnDegreeYear = "DegreeYear";
	private static final String kColumnDegreeSequenceNumber = "DegreeSequenceNumber";
	
	private static final String kColumnAbstract = "Abstract";
	
	private static final String kColumnCommitteeFirstName = "CommitteeFirstName";
	private static final String kColumnCommitteeMiddleName = "CommitteeMiddleName";
	private static final String kColumnCommitteeLastName = "CommitteeLastName";
	private static final String kColumnCommitteeSuffix = "CommitteeSuffix";
	
	private static final String kColumnSubjectGroup = "SubjectGroup"; 
	private static final String kColumnSubjectCode = "SubjectCode";
	private static final String kColumnSubjectDescription = "SubjectDescription";
	private static final String kColumnSubjectSequenceNumber = "SubjectSequenceNumber";
	
	private static final String kColumnSupplementalFileName = "SupplementalFileName";
	private static final String kColumnSupplementalFileDescription = "SupplementalFileDescription";
	private static final String kColumnSupplementalFileCategory = "SupplementalFileCategory";
	private static final String kColumnDepartment = "Department";
	
	private static final String kColumnKeyword = "Keyword";
	private static final String kColumnKeywordSource = "KeywordSource";
	
	private static final String kColumnSalesRestrctionCode = "SalesRestrictionCode";
	private static final String kColumnSalesRestrctionDescription = "SalesRestrictionDescription";
	private static final String kColumnSalesRestrctionStartDate = "SalesRestrictionStartDate";
	private static final String kColumnSalesRestrctionEndDate = "SalesRestrictionEndDate";
	
	private static final String kColumnFormatRestrctionCode = "FormatRestrictionCode";
	
	private static final String kColumnBatchTypeCode = "BatchTypeCode";
	private static final String kColumnBatchDescription = "BatchDescription";
	private static final String kColumnVolumeIssue = "VolumeIssue";
	private static final String kColumnDiaSectionCode = "DiaSectionCode";
	
	private static final String kColumnAlternateTitle = "AlternateTitle";
	private static final String kColumnAlternateTitleLanguage = "AlternateTitleLanguage";
	
	private static final String kColumnAlternateAdvisorName = "AlternateAdvisorName";
	
	private static final String kColumnSchoolId = "SchoolId";
	private static final String kColumnSchoolCode = "SchoolCode";
	private static final String kColumnSchoolName = "SchoolName";
	private static final String kColumnSchoolCountry = "SchoolCountry";
	private static final String kColumnSchoolState = "SchoolState";
	
	private static final String kColumnPdfAvailableDate = "PdfAvailableDate";
	private static final String kColumnExternalId = "ExternalId";
	private static final String kColumnOpenAccessFlag = "OpenAccessFlag";
	
	private static final String kSelectSchoolId = 
			"( " +
				"SELECT dish_id FROM dis_schools WHERE dish_id = ditm.dish_id " +
				"UNION " +
				"SELECT dish_id FROM dis_schools WHERE dish_id = " +
					"( " +
						"SELECT dish_id " +
				        "FROM dis.dis_school_instructions dsin, dis.dis_shipments dshi " +
				        "WHERE dshi.dsin_id = dsin.dsin_id AND dshi.dshi_id = ditm.dshi_id " +
				    ") " +
			") " + kColumnSchoolId + " ";
	
	private static final String kSelectMainPubData =
            "SELECT " +
                  "ditm.ditm_id " + kColumnItemId + ", " +
                  "dvi_id " + kColumnVolumeIssueId + ", " +
                  kSelectSchoolId + ", " +
                  "ditm_pub_number " + kColumnPubId  + ", " + 
                  "ditm_adviser " + kColumnAdvisors + ", " +
                  "ditm_isbn_number " + kColumnIsbn + ", " +
                  "ditm_publication_page_number " + kColumnPageNumber + ", " +
                  "ditm_page_count " + kColumnPageCount + ", " +
                  "ditm_publisher " + kColumnPublisher + ", " +
                  "ditm_reference_location " + kColumnReferenceLocation + ", " +
                  "ditm_bl_no " + kColumnBritishLibraryNumber + ", " +
                  "ditm_source " + kColumnSource + ", " +
                  "ditm_ft_url " + kColumnExternalUrl + ", " +
                  "( SELECT dttl_text FROM dis.dis_titles WHERE dvtl_code = 'M' AND ditm_id = ditm.ditm_id ) " + kColumnMasterTitle + ", " +
                  "( SELECT dttl_text FROM dis.dis_titles WHERE dvtl_code = 'P' AND ditm_id = ditm.ditm_id ) " + kColumnElectronicTitle + ", " +
                  "( SELECT dttl_text FROM dis.dis_titles WHERE dvtl_code = 'E' AND ditm_id = ditm.ditm_id ) " + kColumnEngOverwriteTitle + ", " +
                  "( SELECT dttl_text FROM dis.dis_titles WHERE dvtl_code = 'F' AND ditm_id = ditm.ditm_id ) " + kColumnForeignTitle + ", " +
                  "open_access.flag  " + kColumnOpenAccessFlag + ", " +
                  "ditm_external_id " + kColumnExternalId + " " +
                  "FROM dis.dis_items ditm, " +
                   "( SELECT pub_number,decode(open_access_flag,1,'Y',0,'N','N') flag from dis_vu_pqd_open_access where pub_number = ?) open_access " +
                  "WHERE ditm.ditm_pub_number = ? " +  
                  "and ditm_pub_number = open_access.pub_number(+) " +
                  "AND ditm.dvi_id IS NOT NULL ";
	
	private static final String kSelectLanguage = 
			"SELECT " +
				"dil.dvl_code " + kColumnLanguageCode + "," +
				"dvl.dvl_description " + kColumnLanguageDescription   + " " + 
			"FROM " + 
				"dis_items_languages dil, " +
				"dis_valid_languages dvl " +
			"WHERE " + 
				"dil.ditm_id = ?  AND " + 
				"dil.dvl_code = dvl.dvl_code";

	private static final String kSelectAuthors = 
			"SELECT " +
				"dath.dath_id " + kColumnAuthorId + ", " +
				"dath_sequence_number " + kColumnAuthorSequenceNumber + ", " +
				"dath_fullname " + kColumnAuthorFullName + ", " + 
				"dsa_fullname " + kColumnAuthorAlternateFullName + ", " +
				"dvc_description " + kColumnAuthorCitizenship + " " +
			"FROM " +
				"dis.dis_authors dath, " + 
				"dis.dis_supp_authors dsa, " +
				"dis.dis_valid_countries dvc " +
			"WHERE " +
				"dath.ditm_id = ? AND " +  
				"dath.dath_id = dsa.dath_id(+) AND " + 
				"dath.dath_sequence_number = dsa.dsa_sequence_number(+) AND " +
				"dath.dvc_code = dvc.dvc_code(+) " +
			"ORDER BY dath_sequence_number asc "; 
	
	private static final String kSelectDegree = 
			"SELECT " +
				"dad_code " + kColumnDegreeCode + ", " + 
				"dvde_description " + kColumnDegreeDescription + ", " +
				"TO_CHAR (dad_degree_year, 'YYYY') " + kColumnDegreeYear + ", " +
				"dad_sequence_number " + kColumnDegreeSequenceNumber + " " +
			"FROM " +
				"dis.dis_authors dath, " +
				"dis.dis_author_degrees dad, " +
				"dis.dis_valid_degrees dvde " +
			"WHERE " +
				"dath.dath_id = ? " +
				"AND " +
				"dath.dath_id = dad.dath_id " +
				"AND " +
				"dad.dad_code = dvde.dvde_code " +
			"ORDER BY dad_sequence_number ";
	
	private static final String kSelectAbstract =
			"SELECT " +
				"da_abstract_text " + kColumnAbstract + " " + 
			"FROM " +
				"dis.dis_abstracts " +
			"WHERE " +
				"ditm_id = ? ";
	
	private static final String kSelectSubjects = 
			"SELECT " +
				"dvsg_description " + kColumnSubjectGroup + ", " +
				"dsub.dvsc_code " + kColumnSubjectCode + ", " +
				"dvsc_description " + kColumnSubjectDescription + ", " +
				"dis_sequence_number " + kColumnSubjectSequenceNumber + " " +
			"FROM " +
				"dis.dis_item_subjects dsub, " +
				"dis.dis_valid_subjects dvsc, " +
				"dis.dis_valid_subject_groups dvsg " +
			"WHERE " +
				"dsub.ditm_id = ? " +
				"AND " +
				"dsub.dvsc_code = dvsc.dvsc_code " +
				"AND " +
				"dvsc.dvsg_id = dvsg.dvsg_id " +
			"ORDER BY dis_sequence_number ";
	
	private static final String kSelectCommitteeMembers = 
			"SELECT " +
				"dicm_first_name " + kColumnCommitteeFirstName + ", " + 
				"dicm_middle_name " + kColumnCommitteeMiddleName + ", " +
				"dicm_last_name " + kColumnCommitteeLastName + ", " +
				"dicm_suffix " + kColumnCommitteeSuffix + " " +
			"FROM " +
				"dis.dis_item_cmte_members " +
			"WHERE " +
				"ditm_id = ? " +
			"ORDER BY dicm_last_name, dicm_first_name ";
	
	private static final String kSelectSupplementalFiles = 
			"SELECT " + 
				"disf_filename " + kColumnSupplementalFileName + ", " +  
				"disf_description " + kColumnSupplementalFileDescription + ", " + 
				"vcc_description " + kColumnSupplementalFileCategory + " " +
			"FROM " +
				"dis.dis_item_suppl_files disf, " +
				"dis.valid_content_categories vcc " +
			"WHERE " +
				"disf.vcc_number = vcc.vcc_number AND " +
				"disf.ditm_id = ? " + 
			"ORDER BY disf_filename ";
	
	private static final String kSelectDepartments = 
			"SELECT " +  
				"did_department " + kColumnDepartment + " " +
			"FROM " + 
				"dis.dis_item_departments " +
			"WHERE " +
				"ditm_id = ? " + 
			"ORDER BY did_department ";
	
	private static final String kSelectKeywords = 
			"SELECT " +
				"dik_keyword " + kColumnKeyword + ", " +  
				"rv_abbreviation " + kColumnKeywordSource + " " +
			"FROM " +
				"dis_keywords dik, " +
				"cg_ref_codes rv " +
			"WHERE " +
				"dik.dik_source = rv.rv_low_value(+) AND " +
				"rv.rv_domain(+) = 'KEYWORD SOURCE' AND " +
				"ditm_id = ? " + 
			"ORDER BY dik_keyword ";
	
	private static final String kSelectSalesRestriction = 
			"SELECT " + 
				"dsr.dvsr_code " + kColumnSalesRestrctionCode + ", " +
				"TO_CHAR(dsr.dsr_res_start_date, 'DD-MON-YYYY') " + kColumnSalesRestrctionStartDate + ", " +
				"TO_CHAR(dsr.dsr_res_lift_date, 'DD-MON-YYYY') " + kColumnSalesRestrctionEndDate + ", " +
				"dvsr.dvsr_description " + kColumnSalesRestrctionDescription + " " +
			"FROM " + 
				"dis.dis_sales_restrictions dsr, " +  
				"dis.dis_valid_sales_rstcns dvsr " +
			"WHERE " + 
				"dvsr.dvsr_code = dsr.dvsr_code AND " + 
				"dsr.ditm_id = ? " +
			"ORDER BY dvsr.dvsr_code";
	
	private static final String kSelectFormatRestriction = 
			"SELECT " + 
				"dfr.dvfr_code " + kColumnFormatRestrctionCode + " " +
			"FROM " + 
				"dis.dis_format_restrictions dfr " +  
			"WHERE " + 
				"dfr.ditm_id = ? " +
			"ORDER BY dfr.dvfr_code";
	
	private static final String kSelectBatch = 
			"SELECT " +
				"ddt.ddt_code " + kColumnBatchTypeCode + ", " +
				"ddt.ddt_description " + kColumnBatchDescription + ", " + 
				"dvi.dvi_volume_issue " + kColumnVolumeIssue + ", " + 
				"dvi.dvi_dai_section_code " + kColumnDiaSectionCode + " " +
			"FROM " +
				"dis.dis_volume_issues dvi, " +
				"dis.dis_database_types ddt " +
			"WHERE " +
				"dvi.dvi_id = ? AND " +
				"dvi.ddt_code = ddt.ddt_code";
	
	private static final String kSelectAlternateTitles =
			"SELECT " + 
				"dst.dst_supp_title " + kColumnAlternateTitle + ", " +
				"dvl.dvl_description " + kColumnAlternateTitleLanguage + " " +
			"FROM  " +
				"dis.dis_supp_titles dst, " + 
				"dis.dis_valid_languages dvl " +
			"WHERE " +
				"dst.ditm_id = ? " +
				"AND " +
				"dst.dvl_code = dvl.dvl_code " +
			"ORDER BY dst.dst_supp_title ";	
	
	private static final String kSelectAlternateAdvisors = 
			"SELECT " +
				"dsad_fullname " + kColumnAlternateAdvisorName + " " +
			"FROM " +
				"dis.dis_supp_advisors advis " +
			"WHERE " +
				"ditm_id = ? ";
	
	private static final String kSelectSchool = 
			"SELECT " + 
				"dish_code " + kColumnSchoolCode + ", " + 
				"dish_name " + kColumnSchoolName + ", " + 
				"dvc_description " + kColumnSchoolCountry + ", " + 
				"dsta_name " + kColumnSchoolState + " " +
			"FROM " + 
				"dis.dis_schools dish, " + 
				"dis.dis_valid_countries dvc, " +  
				"dis.dis_states dsta " + 
			"WHERE " + 
				"dish.dish_id = ? " + 
				"AND " + 
				"dish.dvc_code = dvc.dvc_code " +  
				"AND " +  
				"dish.dsta_code = dsta.dsta_code(+) ";
	
	private static final String kSelectPdfStatus = 
			"SELECT " + 
				"TO_CHAR(GREATEST (diaf_date_created, " +
							"NVL (diaf_date_modified, diaf_date_created))," +
							" 'DD-MON-YYYY')  " + kColumnPdfAvailableDate + " " +
			"FROM " + 
				"dis.dis_item_available_formats diaf " + 
			"WHERE " + 
				"diaf.ditm_id = ? " + 
				"AND " + 
				"diaf.dvf_code = 'PDF' ";
	
	
	private PreparedStatement authorsStatement;
	private PreparedStatement mainPubDataStatement;
	private PreparedStatement languageStatement;
	private PreparedStatement degreeStatement;
	private PreparedStatement abstractStatement;
	private PreparedStatement subjectsStatement;
	private PreparedStatement committeeMembersStatement;
	private PreparedStatement supplementalFilesStatement;
	private PreparedStatement departmentsStatement;
	private PreparedStatement keywordsStatement;
	private PreparedStatement salesRestrictionStatement;
	private PreparedStatement formatRestrictionStatement;
	private PreparedStatement batchStatement;
	private PreparedStatement alternateTitlesStatement;
	private PreparedStatement alternateAdvisorsStatement;
	private PreparedStatement schoolStatement;
	private PreparedStatement pdfStatusStatement;
	
	private final String pqOpenUrlBase;
	
	public PubMetaDataQuery(Connection connection, 
			String pqOpenUrlBase) throws SQLException {
		this.pqOpenUrlBase = pqOpenUrlBase;
		
		this.authorsStatement = connection.prepareStatement(kSelectAuthors);
		this.mainPubDataStatement = connection.prepareStatement(kSelectMainPubData);
		this.languageStatement = connection.prepareStatement(kSelectLanguage);
		this.degreeStatement = connection.prepareStatement(kSelectDegree);
		this.abstractStatement = connection.prepareStatement(kSelectAbstract);
		this.subjectsStatement = connection.prepareStatement(kSelectSubjects);
		this.committeeMembersStatement = connection.prepareStatement(kSelectCommitteeMembers);
		this.supplementalFilesStatement = connection.prepareStatement(kSelectSupplementalFiles);
		this.departmentsStatement = connection.prepareStatement(kSelectDepartments);
		this.keywordsStatement = connection.prepareStatement(kSelectKeywords);
		this.salesRestrictionStatement= connection.prepareStatement(kSelectSalesRestriction); 
		this.formatRestrictionStatement= connection.prepareStatement(kSelectFormatRestriction);
		this.batchStatement = connection.prepareStatement(kSelectBatch);
		this.alternateTitlesStatement = connection.prepareStatement(kSelectAlternateTitles);
		this.alternateAdvisorsStatement = connection.prepareStatement(kSelectAlternateAdvisors);
		this.schoolStatement = connection.prepareStatement(kSelectSchool);
		this.pdfStatusStatement = connection.prepareStatement(kSelectPdfStatus);
	}
	
	public String getPqOpenUrlBase() {
		return pqOpenUrlBase;
	}
	
	public String makePqOpenUrlFor(String pubId) {
		return getPqOpenUrlBase() + pubId.trim();
	}
	
	public DisPubMetaData getFor(String pubId) throws SQLException {
		DisPubMetaData result = null;
		ResultSet cursor = null;
		try {
			mainPubDataStatement.setString(1, pubId);
			mainPubDataStatement.setString(2, pubId);
			cursor = mainPubDataStatement.executeQuery();
			if (cursor.next()) {
				result = makeDisPubMetaDataFrom(cursor);
			}
		}
		finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}
		
	private DisPubMetaData makeDisPubMetaDataFrom(ResultSet cursor) throws SQLException {
		DisPubMetaData result = new DisPubMetaData();
		String itemId = cursor.getString(kColumnItemId);
		String pubId = cursor.getString(kColumnPubId);
		result.setPubNumber(required(pubId));
		result.setISBN(trimmed(cursor.getString(kColumnIsbn)));
		result.setPubPageNum(trimmed(cursor.getString(kColumnPageNumber)));
		result.setPageCount(trimmed(cursor.getString(kColumnPageCount)));
		result.setPublisher(trimmed(cursor.getString(kColumnPublisher)));
		result.setBLNumber(trimmed(cursor.getString(kColumnBritishLibraryNumber)));
		result.setReferenceLocation(trimmed(cursor.getString(kColumnReferenceLocation)));
		result.setTitle(makeTitleFrom(cursor));
		String source = trimmed(cursor.getString(kColumnSource));
		if (null != source && source.equalsIgnoreCase("I")) {
			result.setExternalURL(trimmed(cursor.getString(kColumnExternalUrl)));
		}
		if (null != itemId) {
			result.setDissLanguages(getLanguagesFor(itemId));
			result.setSubjects(getSubjectsFor(itemId));
			result.setAbstract(required(getAbstractFor(itemId)));
			result.setDepartments(getDepartmentsFor(itemId));
			result.setKeywords(getKeywordsFor(itemId));
			result.setSalesRestrictions(getSalesRestrictionsFor(itemId));
			result.setFormatRestrictions(getFormatRestrictionsFor(itemId));
			result.setSuppFiles(getSupplementalFilesFor(itemId));
			result.setAuthors(getAuthorsFor(itemId));
			result.setAlternateTitles(getAlternateTitlesFor(itemId));
			result.setCmteMembers(getCommitteeMembersFor(itemId));
			String delimitedAdvisorStr = cursor.getString(kColumnAdvisors);
			result.setAdvisors(getAdvisorsFor(itemId, delimitedAdvisorStr));
			result.setPdfStatus(getPdfStatusFor(itemId));
		}
		String volumeIssueId = cursor.getString(kColumnVolumeIssueId);
		if (null != volumeIssueId) {
			result.setBatch(getBatchFor(volumeIssueId));
		}
		result.setDateOfExtraction(getExtractionDateAsInt());
		result.setSchool(getSchoolFor(cursor.getString(kColumnSchoolId)));
		result.setPqOpenURL(makePqOpenUrlFor(pubId));
		result.setOpenAccessFlag(cursor.getString(kColumnOpenAccessFlag));
		result.setExternalId(cursor.getString(kColumnExternalId));
		return result;
	}
	
	private List<DissLanguage> getLanguagesFor(String itemId) throws SQLException {
		List<DissLanguage> result = Lists.newArrayList();
		ResultSet cursor = null;
		try {
			languageStatement.setString(1, itemId);
			cursor = languageStatement.executeQuery();
			while (cursor.next()) {
				String languageDescription = trimmed(cursor.getString(kColumnLanguageDescription));
				String languageCode = trimmed(cursor.getString(kColumnLanguageCode));
				DissLanguage language = new DissLanguage(required(languageDescription), required(languageCode));
				result.add(language);
			}
		}
		finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	private List<Author> getAuthorsFor(String itemId) throws SQLException {
		List<Author> result = Lists.newArrayList();
		ResultSet cursor = null;
		try {
			authorsStatement.setString(1, itemId);
			cursor = authorsStatement.executeQuery();
			while (cursor.next()) {
				Author author = new Author();
				SplitAuthorNames splitNames = new SplitAuthorNames(cursor.getString(kColumnAuthorFullName));
				author.setAuthorFullName(required(splitNames.getFull()));
				author.setLastName(required(splitNames.getLast()));
				author.setFirstName(splitNames.getFirst());
				author.setMiddleName(splitNames.getMiddle());
				author.setSequenceNumber(cursor.getInt(kColumnAuthorSequenceNumber));
				String alternateFullName = cursor.getString(kColumnAuthorAlternateFullName);
				if (null != alternateFullName) {
					author.setAltAuthorFullName(alternateFullName);
				}
				author.setDegrees(getDegreesFor(cursor.getString(kColumnAuthorId)));
				author.setAuthorCitizenship(cursor.getString(kColumnAuthorCitizenship));
				result.add(author);
			}
		}
		finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	private List<Degree> getDegreesFor(String authorId) throws SQLException {
		List<Degree> result = Lists.newArrayList();
		if (null != authorId) {
			ResultSet cursor = null;
			try {
				degreeStatement.setString(1, authorId);
				cursor = degreeStatement.executeQuery();
				while (cursor.next()) {
					Degree degree = new Degree();
					degree.setDegreeCode(required(cursor.getString(kColumnDegreeCode)));
					degree.setDegreeDescription(required(cursor.getString(kColumnDegreeDescription)));
					degree.setDegreeYear(required(cursor.getString(kColumnDegreeYear)));
					degree.setSequenceNumber(cursor.getInt(kColumnDegreeSequenceNumber));
					result.add(degree);
				}
			}
			finally {
				if (null != cursor) {
					cursor.close();
				}
			}
		}
		return result;
	}

	private String getAbstractFor(String itemId)  throws SQLException {
		String result = null;
		ResultSet cursor = null;
		try {
			abstractStatement.setString(1, itemId);
			cursor = abstractStatement.executeQuery();
			if (cursor.next()) {
				result = cursor.getString(kColumnAbstract);
			}
		}
		finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}
	
	private List<Subject> getSubjectsFor(String itemId) throws SQLException {
		List<Subject> result = Lists.newArrayList();
		ResultSet cursor = null;
		try {
			subjectsStatement.setString(1, itemId);
			cursor = subjectsStatement.executeQuery();
			while (cursor.next()) {
				Subject subject = new Subject();
				subject.setSubjectGroupDesc(required(cursor.getString(kColumnSubjectGroup)));
				subject.setSubjectCode(required(cursor.getString(kColumnSubjectCode)));
				subject.setSubjectDesc(required(cursor.getString(kColumnSubjectDescription)));
				subject.setSequenceNumber(cursor.getInt(kColumnSubjectSequenceNumber));
				result.add(subject);
			}
		}
		finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}
	
	private List<CmteMember> getCommitteeMembersFor(String itemId) throws SQLException {
		List<CmteMember> result = null;
		ResultSet cursor = null;
		try {
			committeeMembersStatement.setString(1, itemId);
			cursor = committeeMembersStatement.executeQuery();
			while (cursor.next()) {
				if (null == result) {
					result = Lists.newArrayList();
				}
				CmteMember item = new CmteMember();
				item.setFirstName(required(cursor.getString(kColumnCommitteeFirstName)));
				item.setMiddleName(trimmed(cursor.getString(kColumnCommitteeMiddleName)));
				item.setLastName(required(cursor.getString(kColumnCommitteeLastName)));
				item.setSuffix(trimmed(cursor.getString(kColumnCommitteeSuffix)));
				result.add(item);
			}
		}
		finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}
	
	private List<SuppFile> getSupplementalFilesFor(String itemId) throws SQLException {
		List<SuppFile> result = null;
		ResultSet cursor = null;
		try {
			supplementalFilesStatement.setString(1, itemId);
			cursor = supplementalFilesStatement.executeQuery();
			while (cursor.next()) {
				if (null == result) {
					result = Lists.newArrayList();
				}
				SuppFile item = new SuppFile();
				item.setSuppFilename(required(cursor.getString(kColumnSupplementalFileName)));
				item.setSuppFileDesc(required(cursor.getString(kColumnSupplementalFileDescription)));
				item.setSuppFileCategory(required(cursor.getString(kColumnSupplementalFileCategory)));
				result.add(item);
			}
		}
		finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}
	
	private List<String> getDepartmentsFor(String itemId) throws SQLException {
		List<String> result = null;
		ResultSet cursor = null;
		try {
			departmentsStatement.setString(1, itemId);
			cursor = departmentsStatement.executeQuery();
			while (cursor.next()) {
				if (null == result) {
					result = Lists.newArrayList();
				}
				result.add(trimmed(cursor.getString(kColumnDepartment)));
			}
		}
		finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}
	
	private List<Keyword> getKeywordsFor(String itemId) throws SQLException {
		List<Keyword> result = null;
		ResultSet cursor = null;
		try {
			keywordsStatement.setString(1, itemId);
			cursor = keywordsStatement.executeQuery();
			while (cursor.next()) {
				if (null == result) {
					result = Lists.newArrayList();
				}
				Keyword item = new Keyword();
				item.setValue(trimmed(cursor.getString(kColumnKeyword)));
				item.setSource(trimmed(cursor.getString(kColumnKeywordSource)));
				result.add(item);
			}
		}
		finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	private List<SalesRestriction> getSalesRestrictionsFor(String itemId) throws SQLException {
		List<SalesRestriction> result = null;
		ResultSet cursor = null;
		try {
			salesRestrictionStatement.setString(1, itemId);
			cursor = salesRestrictionStatement.executeQuery();
			while (cursor.next()) {
				if (null == result) {
					result = Lists.newArrayList();
				}
				SalesRestriction item = new SalesRestriction();
				item.setCode(trimmed(cursor.getString(kColumnSalesRestrctionCode)));
				item.setRestrictionStartDate(cursor.getString(kColumnSalesRestrctionStartDate));
				item.setRestrictionEndDate(cursor.getString(kColumnSalesRestrctionEndDate));
				item.setDescription(trimmed(cursor.getString(kColumnSalesRestrctionDescription)));
				result.add(item);
			}
		}
		finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	} 
	
	private List<FormatRestriction> getFormatRestrictionsFor(String itemId) throws SQLException {
		List<FormatRestriction> result = null;
		ResultSet cursor = null;
		try {
			formatRestrictionStatement.setString(1, itemId);
			cursor = formatRestrictionStatement.executeQuery();
			while (cursor.next()) {
				if (null == result) {
					result = Lists.newArrayList();
				}
				FormatRestriction item = new FormatRestriction();
				item.setCode(trimmed(cursor.getString(kColumnFormatRestrctionCode)));
				result.add(item);
			}
		}
		finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}
	
	private Batch getBatchFor(String volumeIssueId) throws SQLException {
		Batch result = new Batch();
		ResultSet cursor = null;
		try {
			batchStatement.setString(1, volumeIssueId);
			cursor = batchStatement.executeQuery();
			while (cursor.next()) {
				result.setDBTypeCode(required(cursor.getString(kColumnBatchTypeCode)));
				result.setDBTypeDesc(required(cursor.getString(kColumnBatchDescription)));
				result.setVolumeIssue(required(cursor.getString(kColumnVolumeIssue)));
				result.setDAISectionCode(trimmed(cursor.getString(kColumnDiaSectionCode)));
			}
		}
		finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}
	
	private List<AlternateTitle> getAlternateTitlesFor(String itemId) throws SQLException {
		List<AlternateTitle> result = null;
		ResultSet cursor = null;
		try {
			alternateTitlesStatement.setString(1, itemId);
			cursor = alternateTitlesStatement.executeQuery();
			while (cursor.next()) {
				if (null == result) {
					result = Lists.newArrayList();
				}
				AlternateTitle item = new AlternateTitle();
				item.setAltTitle(cursor.getString(kColumnAlternateTitle));
				item.setLanguage(trimmed(cursor.getString(kColumnAlternateTitleLanguage)));
				result.add(item);
			}
		}
		finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}
	
	private Advisors getAdvisorsFor(String itemId, String delimitedAdvisorStr) throws SQLException {
		Advisors result = null;
		if (null != delimitedAdvisorStr && ! delimitedAdvisorStr.isEmpty()) {
			result = new Advisors();
			result.setAdvisorsExodusStr(delimitedAdvisorStr);
			List<Advisor> advisors = Lists.newArrayList();
			List<String> advisorNames = SplitAdvisors.split(delimitedAdvisorStr); 
			List<String> altAdvisorNames = getAlternateAdvisorsFor(itemId);
			for (int i=0; i<advisorNames.size(); ++i) {
				Advisor item = new Advisor();
				item.setAdvisorFullName(advisorNames.get(i));
				if (altAdvisorNames.size() >= i+1) {
					item.setAltAdvisorFullName(altAdvisorNames.get(i));
				}
				advisors.add(item);
			}
			result.setAdvisor(advisors);
		} 
		return result; 
	}
	
	private PdfStatus getPdfStatusFor(String itemId) throws SQLException {
		PdfStatus result = new PdfStatus();
		pdfStatusStatement.setString(1, itemId);
		ResultSet cursor = pdfStatusStatement.executeQuery();
		if (cursor.next()) {
			result.setPdfAvailableDate(cursor.getString(kColumnPdfAvailableDate));
			result.setPdfAvailableStatus(true);
		} else {
			result.setPdfAvailableStatus(false);
		}
		
		return result; 
	}
	

	private List<String> getAlternateAdvisorsFor(String itemId) throws SQLException {
		List<String> result = Lists.newArrayList();
		ResultSet cursor = null;
		try {
			alternateAdvisorsStatement.setString(1, itemId);
			cursor = alternateAdvisorsStatement.executeQuery();
			while (cursor.next()) {
				result.add(cursor.getString(kColumnAlternateAdvisorName));
			}
		}
		finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result; 
	}
	
	private Title makeTitleFrom(ResultSet cursor) throws SQLException {
		Title result = new Title();
		result.setElectronicTitle(cursor.getString(kColumnElectronicTitle));
		result.setMasterTitle(cursor.getString(kColumnMasterTitle));
		result.setEnglishOverwriteTitle(cursor.getString(kColumnEngOverwriteTitle));
        result.setForeignTitle(cursor.getString(kColumnForeignTitle)); 
		return result;
	}
	
	private School getSchoolFor(String schooldId) throws SQLException {
		School result = new School();
		if (null != schooldId) {
			ResultSet cursor = null;
			try {
				schoolStatement.setString(1, schooldId);
				cursor = schoolStatement.executeQuery();
				if (cursor.next()) {
					result.setSchoolCode(required(cursor.getString(kColumnSchoolCode)));
					result.setSchoolName(required(cursor.getString(kColumnSchoolName)));
					result.setSchoolCountry(required(cursor.getString(kColumnSchoolCountry)));
					result.setSchoolState(trimmed(cursor.getString(kColumnSchoolState)));
				}
			}
			finally {
				if (null != cursor) {
					cursor.close();
				}
			}
		}
		return result;
	}

	public void close() throws SQLException {
		closeStatement(authorsStatement);
		closeStatement(mainPubDataStatement);
		closeStatement(languageStatement);
		closeStatement(degreeStatement);
		closeStatement(abstractStatement);
		closeStatement(subjectsStatement);
		closeStatement(committeeMembersStatement);
		closeStatement(supplementalFilesStatement);
		closeStatement(departmentsStatement);
		closeStatement(keywordsStatement);
		closeStatement(batchStatement);
		closeStatement(alternateTitlesStatement);
		closeStatement(alternateAdvisorsStatement);
		closeStatement(schoolStatement);
	}
	
	private void closeStatement(PreparedStatement statment) throws SQLException {
		if (null != statment) {
			statment.close();
		}
	}
	
	private static BigInteger getExtractionDateAsInt() {
		Date now = new Date(System.currentTimeMillis());
		return new BigInteger(kDateFormat.format(now));
	}
	
	public static String trimmed(String x) {
		if (null != x) {
			x = x.trim();
		}
		return x;
	}
	
	public static String required(String x) {
		x = trimmed(x);
		if (null == x || x.isEmpty()) {
			x = kEmptyValue;
		}
		return x;
	}
}

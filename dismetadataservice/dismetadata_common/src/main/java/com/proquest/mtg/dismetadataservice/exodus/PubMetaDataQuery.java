package com.proquest.mtg.dismetadataservice.exodus;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisor;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisors;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.AlternateTitle;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Batch;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.CmteMember;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.DissLanguage;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.DissLOCLanguage;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.FormatRestriction;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Keyword;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.ManuscriptMedia;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.PdfAvailableDateStatus;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SalesRestriction;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.School;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Subject;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SuppFile;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Title;
import com.proquest.mtg.dismetadataservice.metadata.Author;
import com.proquest.mtg.dismetadataservice.metadata.Author.Claimant;
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
	private static final String kManuscriptYear = "ManuScriptYear";
	private static final String kColumnSource = "Source";
	private static final String kColumnExternalUrl = "ExternalUrl";
	private static final String kColumnLanguageDescription = "LanguageDescription";
	private static final String kColumnLanguageCode = "LanguageCode";
	private static final String kColumnLOCLanguageDescription = "LOCLanguageDescription";
	private static final String kColumnLOCLanguageCode = "LOCLanguageCode";
	private static final String kColumnMasterTitle = "MasterTitle";
	private static final String kColumnElectronicTitle = "ElectronicTitle";
	private static final String kColumnEngOverwriteTitle = "EnglishOevrwriteTitle";
	private static final String kColumnForeignTitle = "ForeignTitle";
	private static final String kColumnCRCTitle = "CRCOverrideTitle";
	private static final String kColumnDwosReceiveDate = "dwosReceiveDate";
	
	private static final String kColumnAuthorId = "AuthorId"; 
	private static final String kColumnAuthorSequenceNumber = "AuthorSequenceNumber"; 
	private static final String kColumnAuthorFullName = "AuthorFullName";
	private static final String kColumnAuthorAlternateFullName = "AuthorAlternateFullName";
	private static final String kColumnClaimantAddFlag = "ClaimantAddFlag";
	private static final String kColumnAuthorCitizenship = "AuthorCitizenship"; 
	private static final String kColumnAuthorOrcId = "AuthorOrcId";
	
	private static final String kColumnClaimantId = "AuthorId"; 
	private static final String kColumnClaimantSequenceNumber = "AuthorSequenceNumber"; 
	private static final String kColumnClaimantFullName = "AuthorFullName";
	
	private static final String kColumnDegreeCode = "DegreeCode";
	private static final String kColumnDegreeDescription = "DegreeDescription";
	private static final String kColumnDegreeYear = "DegreeYear";
	private static final String kColumnDegreeSequenceNumber = "DegreeSequenceNumber";
	
	private static final String kColumnAbstract = "Abstract";
	private static final String kColumnAlternateAbstract = "AlternateAbstract";
	
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
	
	private static final String kColumnFormatRestrictionCode = "FormatRestrictionCode";
	private static final String kColumnFormatRestrictionDesc  = "FormatRestrictionDesc";
	private static final String kColumnFormatRestrictionStartDt = "FormatRestrictionCodeStartDt";
	private static final String kColumnFormatRestrictionEndDt = "FormatRestrictionCodeEndDt";
	
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
	
	private static final String kColumnPdfAvailableFlag = "PdfAvailableFlag";
	private static final String kColumnPdfAvailableDate = "PdfAvailableDate";
	private static final String kColumnExternalId = "ExternalId";
	private static final String kColumnOpenAccessFlag = "OpenAccessFlag";
	
	private static final String kColumnPubDate = "PubDate";
	private static final String kColumnManuscriptMediaCode = "ManuscriptMediaCode";
	private static final String kColumnManuscriptMediaDesc = "ManuscriptMediaDesc";
	
	private static final String kColumnSchoolLocCountry = "SchoolLocCountry";
	private static final String kColumnAuthorLocCitizenship = "LocAuthorCitizenship";
	
	private static final String kColumnDCIRefFlag = "DCIRefsFlag";
	
	private static final String kColumnDisValidSource = "DisValidSource"; 
	private static final String kColumnDisAvailableFormat = "DisAvailableFormat"; 
	private static final String kColumnFOPFormatCode = "FOPFormatCode"; 
	private static final String kColumnFOPFormatQuantity = "FormatQuantity"; 
	
	
	private static final String kSelectDisValidSource = 
			"SELECT " +
				"dvs.dvs_description " + kColumnDisValidSource + " " +
			"FROM " + 
				"dis_valid_sources dvs " +
			"WHERE " + 
				"dvs.dvs_code = ?  " ;
	
	
	private static final String kSelectDisAvailableFormat = "SELECT " + "dvf.dvf_code " + kColumnDisAvailableFormat
			+ " " + "FROM " + "dis_item_available_formats dvf " + "WHERE " + "dvf.ditm_id = ?  ";
	
	
	private static final String kSelectFOPFormatQuantity = 
			"SELECT " +
				"dfu.dvf_code " + kColumnFOPFormatCode + ", " +
				"doi.doi_quantity " + kColumnFOPFormatQuantity + " " +
			"FROM " + 
				"dis_order_items doi, " + " " +
				"dis_format_uses dfu " +
			"WHERE " +
				"doi.ditm_id = ? " +
				"AND dfu.dfu_id = doi.dfu_id " +
				"AND dfu.dvf_code in ('MP', 'MN', 'RFP', 'RFN', 'MFL', 'MFC')" +
				"AND dfu.dot_code = 'ST'";
	
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
                  "ditm_manuscript_year " + kManuscriptYear + ", " +
                  "ditm_bl_no " + kColumnBritishLibraryNumber + ", " +
                  "ditm_source " + kColumnSource + ", " +
                  "ditm_ext_url " + kColumnExternalUrl + ", " +
                  "( SELECT dttl_text FROM dis.dis_titles WHERE dvtl_code = 'M' AND ditm_id = ditm.ditm_id ) " + kColumnMasterTitle + ", " +
                  "( SELECT dttl_text FROM dis.dis_titles WHERE dvtl_code = 'P' AND ditm_id = ditm.ditm_id ) " + kColumnElectronicTitle + ", " +
                  "( SELECT dttl_text FROM dis.dis_titles WHERE dvtl_code = 'E' AND ditm_id = ditm.ditm_id ) " + kColumnEngOverwriteTitle + ", " +
                  "( SELECT dttl_text FROM dis.dis_titles WHERE dvtl_code = 'F' AND ditm_id = ditm.ditm_id ) " + kColumnForeignTitle + ", " +
                  "( SELECT dttl_text FROM dis.dis_titles WHERE dvtl_code = 'C' AND ditm_id = ditm.ditm_id ) " + kColumnCRCTitle + ", " +
                  "open_access.flag  " + kColumnOpenAccessFlag + ", " +
                  "ditm_external_id " + kColumnExternalId + ", " +
                  "(SELECT MAX (to_char(dwos_date_created,'dd-MON-yyyy')) " +
                  "FROM dis_work_order_stations dwos " +                 
                  "WHERE dvwo_code = 'S'" + 
                  "AND dwos.diw_id = ditm.diw_id) " + kColumnPubDate + ", " +
            //      "(SELECT  MAX (to_date(to_char(dwos_received_date,'mm/dd/yyyy'),'mm/dd/yyyy')) " +
                  "(SELECT  MAX (dwos_received_date) " +
                  "FROM dis_work_order_stations dwos, dis_work_orders dwo " +                 
                  "WHERE dwo.diw_id = dwos.diw_id and dwos.diw_id = ditm.diw_id) " + kColumnDwosReceiveDate + " " +
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
	
	private static final String kSelectLOCLanguage = 
			"SELECT " +
				"dvl.dvl_loc_code " + kColumnLOCLanguageCode + "," +
				"dvl.dvl_description " + kColumnLOCLanguageDescription   + " " + 
			"FROM " + 
				"dis_items_languages dil, " +
				"dis_valid_languages dvl " +
			"WHERE " + 
				"dil.ditm_id = ?  AND " + 
				"dil.dvl_code = dvl.dvl_code";

	private static final String kSelectAuthors = 
			"SELECT " +
				"dath.dath_id " + kColumnAuthorId + ", " +
				"dath.dath_orc_id " + kColumnAuthorOrcId + ", "+
				"dath_sequence_number " + kColumnAuthorSequenceNumber + ", " +
				"dath_fullname " + kColumnAuthorFullName + ", " + 
				"dsa_fullname " + kColumnAuthorAlternateFullName + ", " + 
				"dvc_description " + kColumnAuthorCitizenship + ", " +
				"dath_use_claimant_add_flag " + kColumnClaimantAddFlag + ", " +
				"dvc_loc_description " + kColumnAuthorLocCitizenship + " " +
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
	
	private static final String kSelectClaimants = 
			"SELECT " +
				"dclm_id " + kColumnClaimantId + ", " +
				"dclm_sequence_number " + kColumnClaimantSequenceNumber + ", " +
				"dclm_fullname " + kColumnClaimantFullName + " " + 
			"FROM " +
				"dis.dis_claimants dclm " + 
			"WHERE " +
				"dclm.dath_id = ? " + 
			"ORDER BY dclm_sequence_number asc "; 
	
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
	
	
	private static final String kSelectAlternateAbstract = "SELECT "
			+ "dst.dsab_abstract_text " + kColumnAlternateAbstract + ", " 
			+ "dvl.dvl_description " + kColumnAlternateTitleLanguage + " "
			+ "FROM dis.dis_supp_abstracts dst , " 
			+ "dis.dis_valid_languages dvl WHERE dst.ditm_id = ? "
			+ "AND dst.dvl_code = dvl.dvl_code "
			+ "ORDER BY dst.DSAB_SNO";
	
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
				"dfr.dvfr_code " + kColumnFormatRestrictionCode + ", " +
				"dvfr.dvfr_description " + kColumnFormatRestrictionDesc + ", " +
				"TO_CHAR(dfr.dfr_res_start_date, 'DD-MON-YYYY') " + kColumnFormatRestrictionStartDt + ", " +
				"TO_CHAR(dfr.dfr_res_lift_date, 'DD-MON-YYYY') " + kColumnFormatRestrictionEndDt + " " +
			"FROM " + 
				"dis.dis_format_restrictions dfr " + ", " + 
				"dis_valid_format_rstcns dvfr " +
			"WHERE " + 
				"dfr.ditm_id = ?  AND " +
			    "dfr.dvfr_code = dvfr.dvfr_code " +
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
				"dsta_name " + kColumnSchoolState + ", " +
				"dvc_loc_description " + kColumnSchoolLocCountry + " " +
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
			"PDF_IN_VAULT_FLAG " + kColumnPdfAvailableFlag + ", " +
			"TO_CHAR(DPDF_DATE_MODIFIED,'DD-MON-YYYY')  " + kColumnPdfAvailableDate + " " +
			"FROM " + 
				"dis_pub_doc_pdf dpdp, " + 
				"dis_items di " +
			"WHERE " + 
				"di.ditm_pub_number = dpdp.ditm_pub_number " + 
				"AND " + 
				"di.ditm_id = ? ";
	
	private static final String kSelectManuscriptMedia = 
			"SELECT " +
				"di.dvmm_code " +  kColumnManuscriptMediaCode + ", " +
				"dvmm_description " + kColumnManuscriptMediaDesc + " " +
			"FROM " + 
				"dis.dis_valid_manuscript_media dvmm, dis_items di " + 
			"WHERE " + 
				"di.ditm_id = ? " + 
				"AND di.dvmm_code = dvmm.dvmm_code";
	
	private static final String kCheckIfDCIRefsExistQuery = 
			"SELECT " +
				"diaf.dvf_code " +  kColumnDCIRefFlag + " " + 
			"FROM " + 
				"dis_items ditm,dis_item_available_formats diaf,dis_valid_formats dvf " +
				"WHERE " + 
				"ditm.ditm_id = ? and " + 
				"ditm.ditm_id = diaf.ditm_id and " +
				"diaf.dvf_code = dvf.dvf_code and " +
				"diaf.DVF_CODE = \'DCF\'";
	
	private PreparedStatement authorsStatement;
	private PreparedStatement claimantsStatement;
	private PreparedStatement mainPubDataStatement;
	//private PreparedStatement dwsoReceivedDataStatement;
	private PreparedStatement languageStatement;
	private PreparedStatement LOClanguageStatement;
	private PreparedStatement degreeStatement;
	private PreparedStatement abstractStatement;
	private PreparedStatement alternateAbstractStatement;
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
	private PreparedStatement manuscriptMediaStatement;
	private PreparedStatement dciRefsStatement;
	private PreparedStatement disValidSourceStatement; 
	private PreparedStatement disAvailableFormatStatement; 
	private PreparedStatement fopQuantityStatement; 
	
	private final String pqOpenUrlBase;
	
	public PubMetaDataQuery(Connection connection, 
			String pqOpenUrlBase) throws SQLException {
		this.pqOpenUrlBase = pqOpenUrlBase;
		
		this.authorsStatement = connection.prepareStatement(kSelectAuthors);
		this.claimantsStatement = connection.prepareStatement(kSelectClaimants);
		this.mainPubDataStatement = connection.prepareStatement(kSelectMainPubData);
		//this.dwsoReceivedDataStatement = connection.prepareStatement(kSelectDwosReceiveDate);
		this.languageStatement = connection.prepareStatement(kSelectLanguage);
		this.LOClanguageStatement = connection.prepareStatement(kSelectLOCLanguage);
		this.degreeStatement = connection.prepareStatement(kSelectDegree);
		this.abstractStatement = connection.prepareStatement(kSelectAbstract);
		this.alternateAbstractStatement = connection
				.prepareStatement(kSelectAlternateAbstract);
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
		this.manuscriptMediaStatement = connection.prepareStatement(kSelectManuscriptMedia);
		this.dciRefsStatement = connection.prepareStatement(kCheckIfDCIRefsExistQuery);
		this.disValidSourceStatement = connection.prepareStatement(kSelectDisValidSource); 
		this.disAvailableFormatStatement = connection.prepareStatement(kSelectDisAvailableFormat); 
		this.fopQuantityStatement = connection.prepareStatement(kSelectFOPFormatQuantity); 
	}
	
	public String getPqOpenUrlBase() {
		return pqOpenUrlBase;
	}
	
	public String makePqOpenUrlFor(String pubId) {
		return getPqOpenUrlBase() + pubId.trim();
	}
	
	public DisPubMetaData getFor(String pubId, int excludeRestriction, int excludeAbstract, int excludeAltAbstract) throws SQLException, ParseException {
		DisPubMetaData result = null;
		ResultSet cursor = null;
		try {
			mainPubDataStatement.setString(1, pubId);
			mainPubDataStatement.setString(2, pubId);
			cursor = mainPubDataStatement.executeQuery();
			if (cursor.next()) {
				result = makeDisPubMetaDataFrom(cursor, excludeRestriction, excludeAbstract, excludeAltAbstract);
				System.out.println("result:" + result);
			}
		}
		finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}
		
	private DisPubMetaData makeDisPubMetaDataFrom(ResultSet cursor, int excludeRestriction, int excludeAbstract, int excludeAltAbstract) throws SQLException, ParseException {
		DisPubMetaData result = new DisPubMetaData();
		String itemId = cursor.getString(kColumnItemId);
		String pubId = cursor.getString(kColumnPubId);
		result.setPubNumber(required(pubId));
		result.setISBN(trimmed(cursor.getString(kColumnIsbn)));
		result.setPubPageNum(trimmed(cursor.getString(kColumnPageNumber)));
		result.setPageCount(trimmed(cursor.getString(kColumnPageCount)));
		result.setPublisher(trimmed(cursor.getString(kColumnPublisher)));
		result.setManuscriptYear(trimmed(cursor.getString(kManuscriptYear)));
		result.setBLNumber(trimmed(cursor.getString(kColumnBritishLibraryNumber)));
		result.setReferenceLocation(trimmed(cursor.getString(kColumnReferenceLocation)));
		result.setTitle(makeTitleFrom(cursor));
		
		
		SimpleDateFormat dayFormat = new SimpleDateFormat("MM/dd/yyyy");
		String dwoReceiveDate = null;
		System.out.println(cursor.getString(kColumnDwosReceiveDate));

		if(null != cursor.getDate(kColumnDwosReceiveDate))
		{
			System.out.println(dayFormat.format(cursor.getDate(kColumnDwosReceiveDate)));
			dwoReceiveDate = dayFormat.format(cursor.getDate(kColumnDwosReceiveDate));
		}
		result.setFirstPublicationDate(dwoReceiveDate);
		
		
		String source = trimmed(cursor.getString(kColumnSource));
		if (null != source && source.equalsIgnoreCase("I")) {
			result.setExternalURL(trimmed(cursor.getString(kColumnExternalUrl)));
		}
		if (null != itemId) {
			result.setDissLanguages(getLanguagesFor(itemId));
			result.setDissLOCLanguages(getLOCLanguagesFor(itemId));
			result.setSubjects(getSubjectsFor(itemId));
			if (excludeAbstract == 0){
				result.setAbstract(required(getAbstractFor(itemId)));
			}
			if (excludeAltAbstract == 0) {
				result.setAlternateAbstracts(getAlternateAbstractFor(itemId));
			}
			result.setDepartments(getDepartmentsFor(itemId));
			result.setKeywords(getKeywordsFor(itemId));
			result.setSalesRestrictions(getSalesRestrictionsFor(itemId, excludeRestriction));
			result.setFormatRestrictions(getFormatRestrictionsFor(itemId));
			result.setSuppFiles(getSupplementalFilesFor(itemId));
			result.setAuthors(getAuthorsFor(itemId));
			result.setAlternateTitles(getAlternateTitlesFor(itemId));
			result.setCmteMembers(getCommitteeMembersFor(itemId));
			String delimitedAdvisorStr = cursor.getString(kColumnAdvisors);
			result.setAdvisors(getAdvisorsFor(itemId, delimitedAdvisorStr));
			result.setPdfStatus(getPdfStatusFor(itemId));
			result.setDisAvailableFormats(getDisAvailableFormats(itemId));
			result.setFOPQuantity(getFOPQuantities(itemId));
		}
		String volumeIssueId = cursor.getString(kColumnVolumeIssueId);
		if (null != volumeIssueId) {
			result.setBatch(getBatchFor(volumeIssueId));
		}
		result.setDateOfExtraction(getExtractionDateAsInt());
		result.setSchool(getSchoolFor(cursor.getString(kColumnSchoolId)));
		result.setPqOpenURL(makePqOpenUrlFor(pubId));
		result.setOpenAccessFlag(cursor.getString(kColumnOpenAccessFlag));
		result.setPubDate(cursor.getString(kColumnPubDate));
		result.setExternalId(cursor.getString(kColumnExternalId));
		result.setManuscriptMedia(getManuscriptMediaFor(itemId));
		result.setDciRefExistsFlag(getDCIRefsFor(itemId));
		
		if (null != source) {
			result.setDisValidSource(getDisValidSourceFor(source));
		}
			
		return result;
	}
	
	private String getDisValidSourceFor(String source) throws SQLException {
		String result = null;
		ResultSet cursor = null;
		
		try {
			disValidSourceStatement.setString(1, source);
			cursor = disValidSourceStatement.executeQuery();
			while (cursor.next()) {
				result = cursor.getString(kColumnDisValidSource);
			}
		}
		finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}
	
	private List<String> getDisAvailableFormats(String itemid) throws SQLException {
		List<String> result = Lists.newArrayList();
		ResultSet cursor = null;

		try {
			disAvailableFormatStatement.setString(1, itemid);
			cursor = disAvailableFormatStatement.executeQuery();
			while (cursor.next()) {
				String availableFormat = trimmed(cursor.getString(kColumnDisAvailableFormat));
				result.add(availableFormat);
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}
	
	private List<String> getFOPQuantities(String itemid) throws SQLException {
		List<String> result = Lists.newArrayList();
		ResultSet cursor = null;

		try {
			fopQuantityStatement.setString(1, itemid);
			cursor = fopQuantityStatement.executeQuery();
			while (cursor.next()) {
				String fopQuantity = trimmed(
						cursor.getString(kColumnFOPFormatCode) + "," + cursor.getString(kColumnFOPFormatQuantity));
				result.add(fopQuantity);
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
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
	
	private List<DissLOCLanguage> getLOCLanguagesFor(String itemId) throws SQLException {
		List<DissLOCLanguage> result = Lists.newArrayList();
		ResultSet cursor = null;
		try {
			LOClanguageStatement.setString(1, itemId);
			cursor = LOClanguageStatement.executeQuery();
			while (cursor.next()) {
				String LOClanguageDescription = trimmed(cursor.getString(kColumnLOCLanguageDescription));
				String LOClanguageCode = trimmed(cursor.getString(kColumnLOCLanguageCode));
				DissLOCLanguage language = new DissLOCLanguage(required(LOClanguageDescription), required(LOClanguageCode));
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
				author.setAuthorId(cursor.getString(kColumnAuthorId));
				SplitAuthorNames splitNames = new SplitAuthorNames(
						cursor.getString(kColumnAuthorFullName));
				author.setAuthorFullName(required(splitNames.getFull()));
				author.setLastName(required(splitNames.getLast()));
				author.setFirstName(splitNames.getFirst());
				author.setMiddleName(splitNames.getMiddle());
				author.setSequenceNumber(cursor
						.getInt(kColumnAuthorSequenceNumber));
				String alternateFullName = cursor
						.getString(kColumnAuthorAlternateFullName);
				if (null != alternateFullName) {
					author.setAltAuthorFullName(alternateFullName);
				}
				author.setClaimantAddFlag(cursor
						.getString(kColumnClaimantAddFlag));
				author.setDegrees(getDegreesFor(cursor
						.getString(kColumnAuthorId)));
				author.setAuthorCitizenship(cursor
						.getString(kColumnAuthorCitizenship));
				author.setClaimants(getClaimantsFor(cursor
						.getString(kColumnAuthorId)));
				if (null != cursor.getString(kColumnAuthorLocCitizenship)
						&& !cursor.getString(kColumnAuthorLocCitizenship)
								.isEmpty()) {
					author.setAuthorLocCitizenship(cursor
							.getString(kColumnAuthorLocCitizenship));
				} else {
					author.setAuthorLocCitizenship(cursor
							.getString(kColumnAuthorCitizenship));
				}
				if (null != cursor.getString(kColumnAuthorOrcId)
						&& !cursor.getString(kColumnAuthorOrcId)
								.isEmpty()) {
					author.setOrcID(cursor
							.getString(kColumnAuthorOrcId));
				} else {
					author.setOrcID(cursor
							.getString(kColumnAuthorOrcId));
				}
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

	private List<Claimant> getClaimantsFor(String authorId) throws SQLException {
		List<Claimant> result = Lists.newArrayList();
		if (null != authorId) {
			ResultSet cursor = null;
			try {
				claimantsStatement.setString(1, authorId);
				cursor = claimantsStatement.executeQuery();
				while (cursor.next()) {
					Claimant claimant = new Claimant();
					claimant.setClaimantId(cursor.getString(kColumnClaimantId));
					claimant.setSequenceNumber(cursor.getInt(kColumnClaimantSequenceNumber));
					SplitAuthorNames splitNames = new SplitAuthorNames(cursor.getString(kColumnClaimantFullName));
					claimant.setClaimantFullName(required(splitNames.getFull()));
					claimant.setLastName(required(splitNames.getLast()));
					claimant.setFirstName(splitNames.getFirst());
					claimant.setMiddleName(splitNames.getMiddle());
					result.add(claimant);
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
	
	private String getAlternateAbstractFor(String itemId) throws SQLException {
        String abs = null;
        ResultSet cursor = null;
        try {
               alternateAbstractStatement.setString(1, itemId);
               cursor = alternateAbstractStatement.executeQuery();
               while (cursor.next()) {
                     abs = cursor.getString(kColumnAlternateAbstract);
                     break;
               }
        } finally {
               if (null != cursor) {
                     cursor.close();
               }
        }
        return abs;
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

	private List<SalesRestriction> getSalesRestrictionsFor(String itemId, int excludeRestriction) throws SQLException {
		List<SalesRestriction> result = null;
		ResultSet cursor = null;
		try {
			salesRestrictionStatement.setString(1, itemId);
			cursor = salesRestrictionStatement.executeQuery();
			while (cursor.next()) {
				if (null == result) {
					result = Lists.newArrayList();
				}
				if(excludeRestriction==1){
					if(!(trimmed(cursor.getString(kColumnSalesRestrctionCode))).equals("5") && !(trimmed(cursor.getString(kColumnSalesRestrctionCode))).equals("8")){
						SalesRestriction item = new SalesRestriction();
						item.setCode(trimmed(cursor.getString(kColumnSalesRestrctionCode)));
						item.setRestrictionStartDate(cursor.getString(kColumnSalesRestrctionStartDate));
						item.setRestrictionEndDate(cursor.getString(kColumnSalesRestrctionEndDate));
						item.setDescription(trimmed(cursor.getString(kColumnSalesRestrctionDescription)));
						result.add(item);
					}
				}
				else{
					SalesRestriction item = new SalesRestriction();
					item.setCode(trimmed(cursor.getString(kColumnSalesRestrctionCode)));
					item.setRestrictionStartDate(cursor.getString(kColumnSalesRestrctionStartDate));
					item.setRestrictionEndDate(cursor.getString(kColumnSalesRestrctionEndDate));
					item.setDescription(trimmed(cursor.getString(kColumnSalesRestrctionDescription)));
					result.add(item);
				}
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
				item.setCode(trimmed(cursor.getString(kColumnFormatRestrictionCode)));
				item.setDesc(trimmed(cursor.getString(kColumnFormatRestrictionDesc)));
				item.setFormatRestrictionStartDt(cursor.getString(kColumnFormatRestrictionStartDt));
				item.setFormatRestrictionEndDt(cursor.getString(kColumnFormatRestrictionEndDt));
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
	
	private PdfAvailableDateStatus getPdfStatusFor(String itemId) throws SQLException {
		PdfAvailableDateStatus result = new PdfAvailableDateStatus();
		pdfStatusStatement.setString(1, itemId);
		ResultSet cursor = pdfStatusStatement.executeQuery();
		if (cursor.next()) {
			result.setPdfAvailableDate(cursor.getString(kColumnPdfAvailableDate));
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
        result.setCRCTitle(cursor.getString(kColumnCRCTitle));
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
					if(null != cursor.getString(kColumnSchoolLocCountry) && !cursor.getString(kColumnSchoolLocCountry).isEmpty()) {
						result.setSchoolLocCountry(trimmed(cursor.getString(kColumnSchoolLocCountry)));
					} 
					else {
						result.setSchoolLocCountry(trimmed(cursor.getString(kColumnSchoolCountry)));
					}
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
	
	private ManuscriptMedia getManuscriptMediaFor(String itemId) throws SQLException {
		ManuscriptMedia result = new ManuscriptMedia();
		if (null != itemId) {
			ResultSet cursor = null;
			try {
				manuscriptMediaStatement.setString(1, itemId);
				cursor = manuscriptMediaStatement.executeQuery();
				if (cursor.next()) {
					result.setManuscriptMediaCode(required(cursor.getString(kColumnManuscriptMediaCode)));
					result.setManuscriptMediaDesc(required(cursor.getString(kColumnManuscriptMediaDesc)));
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
	
	private String getDCIRefsFor(String itemId) throws SQLException {
		String result = "N";
		if (null != itemId) {
			ResultSet cursor = null;
			try {
				dciRefsStatement.setString(1, itemId);
				cursor = dciRefsStatement.executeQuery();
				if (cursor.next()) {
					if (cursor.getString(kColumnDCIRefFlag).equalsIgnoreCase("DCF"));
					 result = "Y";
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
		closeStatement(claimantsStatement);
		closeStatement(mainPubDataStatement);
		closeStatement(languageStatement);
		closeStatement(LOClanguageStatement);
		closeStatement(degreeStatement);
		closeStatement(abstractStatement);
		closeStatement(alternateAbstractStatement);
		closeStatement(subjectsStatement);
		closeStatement(committeeMembersStatement);
		closeStatement(supplementalFilesStatement);
		closeStatement(departmentsStatement);
		closeStatement(keywordsStatement);
		closeStatement(salesRestrictionStatement);
		closeStatement(formatRestrictionStatement);
		closeStatement(batchStatement);
		closeStatement(alternateTitlesStatement);
		closeStatement(alternateAdvisorsStatement);
		closeStatement(schoolStatement);
		closeStatement(pdfStatusStatement);	
		closeStatement(manuscriptMediaStatement);
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

package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.metadata.SplitAuthorNames;
import com.proquest.mtg.dismetadataservice.metadata.TextNormalizer;
import com.proquest.mtg.dismetadataservice.mrngxml.Degrees;
import com.proquest.mtg.dismetadataservice.mrngxml.Degrees.Degree;
import com.proquest.mtg.dismetadataservice.mrngxml.Dissertation;
import com.proquest.mtg.dismetadataservice.mrngxml.Dissertation.Advisors.Advisor;
import com.proquest.mtg.dismetadataservice.mrngxml.Dissertation.Advisors.Advisor.AltAdvisorFullName;
import com.proquest.mtg.dismetadataservice.mrngxml.Dissertation.AlternateTitles.AlternateTitle;
import com.proquest.mtg.dismetadataservice.mrngxml.PrimaryAuthor;
import com.proquest.mtg.dismetadataservice.mrngxml.SecondaryAuthor;

import com.proquest.mtg.dismetadataservice.mrngxml.Dissertation.CmteMembers.CmteMember;
import com.proquest.mtg.dismetadataservice.mrngxml.Dissertation.Keywords.Keyword;

import com.proquest.mtg.dismetadataservice.mrngxml.Dissertation.*;

public class PubMetaDataQueryForMrngXml {
	public static final String kEmptyValue = "";
	private static final String kColumnItemId = "ItemId";
	private static final String kColumnVolumeIssueId = "VolumeIssueId";
	private static final String kColumnPubId = "PubNumber";
	private static final String kColumnAdvisors = "Advisors";
	private static final String kColumnIsbn = "Isbn";
	private static final String kColumnPageNumber = "PageNumber";
	private static final String kColumnPageCount = "PageCount";
	private static final String kColumnReferenceLocation = "ReferenceLocation";
	private static final String kColumnBritishLibraryNumber = "BritishLibraryNumber";
	private static final String kColumnSource = "Source";
	private static final String kColumnExternalUrl = "ExternalUrl";
	private static final String kColumnLanguage = "Language";
	private static final String kColumnAbstract = "Abstract";
	private static final String kColumnAlternateAbstract = "AlternateAbstract";
	private static final String kColumnMasterTitle = "MasterTitle";
	private static final String kColumnElectronicTitle = "ElectronicTitle";
	private static final String kColumnDepartment = "Department";

	private static final String kColumnAlternateTitle = "AlternateTitle";
	private static final String kColumnAlternateTitleLanguage = "AlternateTitleLanguage";

	private static final String kColumnSubjectGroup = "SubjectGroup";
	private static final String kColumnSubjectCode = "SubjectCode";
	private static final String kColumnSubjectDescription = "SubjectDescription";
	private static final String kColumnSubjectSequenceNumber = "SubjectSequenceNumber";

	private static final String kColumnBatchTypeCode = "BatchTypeCode";
	private static final String kColumnBatchDescription = "BatchDescription";
	private static final String kColumnVolumeIssue = "VolumeIssue";
	private static final String kColumnDiaSectionCode = "DiaSectionCode";

	private static final String kColumnSchoolId = "SchoolId";
	private static final String kColumnSchoolCode = "SchoolCode";
	private static final String kColumnSchoolName = "SchoolName";
	private static final String kColumnSchoolCountry = "SchoolCountry";
	private static final String kColumnSchoolState = "SchoolState";

	private static final String kColumnKeyword = "Keyword";
	private static final String kColumnKeywordSource = "KeywordSource";

	private static final String kColumnSupplementalFileName = "SupplementalFileName";
	private static final String kColumnSupplementalFileDescription = "SupplementalFileDescription";
	private static final String kColumnSupplementalFileCategory = "SupplementalFileCategory";

	private static final String kColumnCommitteeFirstName = "CommitteeFirstName";
	private static final String kColumnCommitteeMiddleName = "CommitteeMiddleName";
	private static final String kColumnCommitteeLastName = "CommitteeLastName";
	private static final String kColumnCommitteeSuffix = "CommitteeSuffix";

	private static final String kColumnAlternateAdvisorName = "AlternateAdvisorName";

	private static final String kColumnAuthorId = "AuthorId";
	private static final String kColumnAuthorSequenceNumber = "AuthorSequenceNumber";
	private static final String kColumnAuthorFullName = "AuthorFullName";
	private static final String kColumnAuthorAlternateFullName = "AuthorAlternateFullName";
	private static final String kColumnAuthorOrcId = "AuthorOrcId";

	private static final String kColumnDegreeCode = "DegreeCode";
	private static final String kColumnDegreeDescription = "DegreeDescription";
	private static final String kColumnDegreeYear = "DegreeYear";
	private static final String kColumnDegreeSequenceNumber = "DegreeSequenceNumber";

	private static final String kSelectSchoolId = "( "
			+ "SELECT dish_id FROM dis_schools WHERE dish_id = ditm.dish_id "
			+ "UNION "
			+ "SELECT dish_id FROM dis_schools WHERE dish_id = "
			+ "( "
			+ "SELECT dish_id "
			+ "FROM dis.dis_school_instructions dsin, dis.dis_shipments dshi "
			+ "WHERE dshi.dsin_id = dsin.dsin_id AND dshi.dshi_id = ditm.dshi_id "
			+ ") " + ") " + kColumnSchoolId + " ";

	private static final String kSelectMainPubData = "SELECT " + "ditm_id "
			+ kColumnItemId
			+ ", "
			+ "dvi_id "
			+ kColumnVolumeIssueId
			+ ", "
			+ kSelectSchoolId
			+ ", "
			+ "ditm_pub_number "
			+ kColumnPubId
			+ ", "
			+ "ditm_adviser "
			+ kColumnAdvisors
			+ ", "
			+ "ditm_isbn_number "
			+ kColumnIsbn
			+ ", "
			+ "ditm_publication_page_number "
			+ kColumnPageNumber
			+ ", "
			+ "ditm_page_count "
			+ kColumnPageCount
			+ ", "
			+ "ditm_reference_location "
			+ kColumnReferenceLocation
			+ ", "
			+ "ditm_bl_no "
			+ kColumnBritishLibraryNumber
			+ ", "
			+ "ditm_source "
			+ kColumnSource
			+ ", "
			+ "ditm_ext_url "
			+ kColumnExternalUrl
			+ ", "
			+ "( SELECT dttl_text FROM dis.dis_titles WHERE dvtl_code = 'M' AND ditm_id = ditm.ditm_id ) "
			+ kColumnMasterTitle
			+ ", "
			+ "( SELECT dttl_text FROM dis.dis_titles WHERE dvtl_code = 'P' AND ditm_id = ditm.ditm_id ) "
			+ kColumnElectronicTitle + ", " + "( SELECT dvl_description "
			+ "FROM dis.dis_items_languages a, dis.dis_valid_languages b "
			+ "WHERE a.ditm_id = ditm.ditm_id AND a.dvl_code = b.dvl_code ) "
			+ kColumnLanguage + " " + "FROM " + "dis.dis_items ditm "
			+ "WHERE " + "ditm.ditm_pub_number = ? " + "AND "
			+ "ditm.dvi_id IS NOT NULL ";

	private static final String kSelectSubjects = "SELECT "
			+ "dvsg_description "
			+ kColumnSubjectGroup
			+ ", "
			+ "dsub.dvsc_code "
			+ kColumnSubjectCode
			+ ", "
			+ "dvsc_description "
			+ kColumnSubjectDescription
			+ ", "
			+ "dis_sequence_number "
			+ kColumnSubjectSequenceNumber
			+ " "
			+ "FROM "
			+ "dis.dis_item_subjects dsub, "
			+ "dis.dis_valid_subjects dvsc, "
			+ "dis.dis_valid_subject_groups dvsg "
			+ "WHERE "
			+ "dsub.ditm_id = ? "
			+ "AND "
			+ "dsub.dvsc_code = dvsc.dvsc_code "
			+ "AND "
			+ "dvsc.dvsg_id = dvsg.dvsg_id "
			+ "ORDER BY dis_sequence_number ";

	private static final String kSelectBatch = "SELECT "
			+ "decode(dvi.ddt_code,'DAC','DAI',dvi.ddt_code) "
			+ kColumnBatchTypeCode
			+ ", "
			+ "ddt_description "
			+ kColumnBatchDescription
			+ ", "
			+ "decode(dvi.ddt_code,'DAC',substr(dvi_volume_issue,1,5),dvi_volume_issue) "
			+ kColumnVolumeIssue + ", "
			+ "decode(dvi.ddt_code,'DAI',dvi_dai_section_code,'DAC','C',NULL) "
			+ kColumnDiaSectionCode + " " + "FROM "
			+ "dis.dis_volume_issues dvi, " + "dis.dis_database_types ddt "
			+ "WHERE " + "dvi.dvi_id = ? " + "AND "
			+ "dvi.ddt_code = ddt.ddt_code ";

	private static final String kSelectSchool = "SELECT " + "dish_code "
			+ kColumnSchoolCode + ", " + "dish_name " + kColumnSchoolName
			+ ", " + "dvc_description " + kColumnSchoolCountry + ", "
			+ "dsta_name " + kColumnSchoolState + " " + "FROM "
			+ "dis.dis_schools dish, " + "dis.dis_valid_countries dvc, "
			+ "dis.dis_states dsta " + "WHERE " + "dish.dish_id = ? " + "AND "
			+ "dish.dvc_code = dvc.dvc_code " + "AND "
			+ "dish.dsta_code = dsta.dsta_code(+) ";

	private static final String kSelectAbstract = "SELECT "
			+ "da_abstract_text " + kColumnAbstract + " " + "FROM "
			+ "dis.dis_abstracts " + "WHERE " + "ditm_id = ? ";

	private static final String kSelectAlternateAbstract = "SELECT "
			+ "dsab_abstract_text " + kColumnAlternateAbstract + " " + "FROM "
			+ "dis.dis_supp_abstracts " + "WHERE " + "ditm_id = ? ";

	private static final String kSelectAlternateTitles = "SELECT "
			+ "dst.dst_supp_title " + kColumnAlternateTitle + ", "
			+ "dvl.dvl_description " + kColumnAlternateTitleLanguage + " "
			+ "FROM  " + "dis.dis_supp_titles dst, "
			+ "dis.dis_valid_languages dvl " + "WHERE " + "dst.ditm_id = ? "
			+ "AND " + "dst.dvl_code = dvl.dvl_code "
			+ "ORDER BY dst.dst_supp_title ";

	private static final String kSelectDepartments = "SELECT "
			+ "did_department " + kColumnDepartment + " " + "FROM "
			+ "dis.dis_item_departments " + "WHERE " + "ditm_id = ? "
			+ "ORDER BY did_department ";

	private static final String kSelectKeywords = "SELECT " + "dik_keyword "
			+ kColumnKeyword + ", " + "rv_abbreviation " + kColumnKeywordSource
			+ " " + "FROM " + "dis_keywords dik, " + "cg_ref_codes rv "
			+ "WHERE " + "dik.dik_source = rv.rv_low_value(+) " + "AND "
			+ "rv.rv_domain(+) = 'KEYWORD SOURCE' " + "AND " + "ditm_id = ? "
			+ "ORDER BY dik_keyword ";

	private static final String kSelectSupplementalFiles = "SELECT "
			+ "disf_filename " + kColumnSupplementalFileName + ", "
			+ "disf_description " + kColumnSupplementalFileDescription + ", "
			+ "vcc_description " + kColumnSupplementalFileCategory + " "
			+ "FROM " + "dis.dis_item_suppl_files disf, "
			+ "dis.valid_content_categories vcc " + "WHERE "
			+ "disf.vcc_number = vcc.vcc_number " + "AND "
			+ "disf.ditm_id = ? " + "ORDER BY disf_filename ";

	private static final String kSelectCommitteeMembers = "SELECT "
			+ "dicm_first_name " + kColumnCommitteeFirstName + ", "
			+ "dicm_middle_name " + kColumnCommitteeMiddleName + ", "
			+ "dicm_last_name " + kColumnCommitteeLastName + ", "
			+ "dicm_suffix " + kColumnCommitteeSuffix + " " + "FROM "
			+ "dis.dis_item_cmte_members " + "WHERE " + "ditm_id = ? "
			+ "ORDER BY dicm_last_name, dicm_first_name ";

	private static final String kSelectAlternateAdvisors = "SELECT "
			+ "dsad_fullname " + kColumnAlternateAdvisorName + " " + "FROM "
			+ "dis.dis_supp_advisors advis " + "WHERE " + "ditm_id = ? ";

	private static final String kSelectAuthors = "SELECT " + "dath.dath_id "
			+ kColumnAuthorId + ", " + "dath.dath_orc_id " + kColumnAuthorOrcId
			+ "," + "dath_sequence_number " + kColumnAuthorSequenceNumber
			+ ", " + "dath_fullname " + kColumnAuthorFullName + ", "
			+ "dsa_fullname " + kColumnAuthorAlternateFullName + " " + "FROM "
			+ "dis.dis_authors dath, " + "dis.dis_supp_authors dsa " + "WHERE "
			+ "dath.ditm_id = ? " + "AND " + "dath.dath_id = dsa.dath_id(+) "
			+ "AND "
			+ "dath.dath_sequence_number = dsa.dsa_sequence_number(+) "
			+ "ORDER BY dath_sequence_number ";

	private static final String kSelectDegree = "SELECT " + "dad_code "
			+ kColumnDegreeCode + ", " + "dvde_description "
			+ kColumnDegreeDescription + ", "
			+ "TO_CHAR (dad_degree_year, 'YYYY') " + kColumnDegreeYear + ", "
			+ "dad_sequence_number " + kColumnDegreeSequenceNumber + " "
			+ "FROM " + "dis.dis_authors dath, "
			+ "dis.dis_author_degrees dad, " + "dis.dis_valid_degrees dvde "
			+ "WHERE " + "dath.dath_id = ? " + "AND "
			+ "dath.dath_id = dad.dath_id " + "AND "
			+ "dad.dad_code = dvde.dvde_code "
			+ "ORDER BY dad_sequence_number ";

	private final TextNormalizer textNormalizer;
	private PreparedStatement mainPubDataStatement;
	private PreparedStatement abstractStatement;
	private PreparedStatement alternateAbstractStatement;
	private PreparedStatement authorsStatement;
	private PreparedStatement subjectsStatement;
	private PreparedStatement batchStatement;
	private PreparedStatement schoolStatement;
	private PreparedStatement alternateTitlesStatement;
	private PreparedStatement departmentsStatement;
	private PreparedStatement keywordsStatement;
	private PreparedStatement supplementalFilesStatement;
	private PreparedStatement committeeMembersStatement;
	private PreparedStatement alternateAdvisorsStatement;
	private PreparedStatement degreeStatement;

	public PubMetaDataQueryForMrngXml(Connection connection,
			String pqOpenUrlBase) throws SQLException {

		this.textNormalizer = new TextNormalizer();
		this.mainPubDataStatement = connection
				.prepareStatement(kSelectMainPubData);
		this.abstractStatement = connection.prepareStatement(kSelectAbstract);
		this.alternateAbstractStatement = connection
				.prepareStatement(kSelectAlternateAbstract);
		this.authorsStatement = connection.prepareStatement(kSelectAuthors);
		this.subjectsStatement = connection.prepareStatement(kSelectSubjects);
		this.batchStatement = connection.prepareStatement(kSelectBatch);
		this.schoolStatement = connection.prepareStatement(kSelectSchool);
		this.alternateTitlesStatement = connection
				.prepareStatement(kSelectAlternateTitles);
		this.departmentsStatement = connection
				.prepareStatement(kSelectDepartments);
		this.keywordsStatement = connection.prepareStatement(kSelectKeywords);
		this.supplementalFilesStatement = connection
				.prepareStatement(kSelectSupplementalFiles);
		this.committeeMembersStatement = connection
				.prepareStatement(kSelectCommitteeMembers);
		this.alternateAdvisorsStatement = connection
				.prepareStatement(kSelectAlternateAdvisors);
		this.degreeStatement = connection.prepareStatement(kSelectDegree);
	}

	public void close() throws SQLException {
		closeStatement(mainPubDataStatement);
		closeStatement(abstractStatement);
		closeStatement(alternateAbstractStatement);
		closeStatement(authorsStatement);
		closeStatement(degreeStatement);
		closeStatement(subjectsStatement);
		closeStatement(batchStatement);
		closeStatement(schoolStatement);
		closeStatement(alternateTitlesStatement);
		closeStatement(departmentsStatement);
		closeStatement(keywordsStatement);
		closeStatement(supplementalFilesStatement);
		closeStatement(committeeMembersStatement);
		closeStatement(alternateAdvisorsStatement);
	}

	private void closeStatement(PreparedStatement statment) throws SQLException {
		if (null != statment) {
			statment.close();
		}
	}

	public Dissertation getFor(String pubId) throws SQLException {
		Dissertation result = null;
		ResultSet cursor = null;
		try {
			mainPubDataStatement.setString(1, pubId);
			cursor = mainPubDataStatement.executeQuery();
			if (cursor.next()) {
				result = makePubMetaDataFrom(cursor);
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	private Dissertation makePubMetaDataFrom(ResultSet cursor)
			throws SQLException {
		Dissertation result = new Dissertation();
		String pubId = cursor.getString(kColumnPubId);
		result.setPubNumber(required(pubId));
		result.setISBN(trimmed(cursor.getString(kColumnIsbn)));
		result.setPageCount(cursor.getInt(kColumnPageCount));
		String language = trimmed(cursor.getString(kColumnLanguage));
		result.setDissLanguage(language);
		result.setTitle(makeTitleFrom(cursor, language));

		String itemId = cursor.getString(kColumnItemId);
		if (null != itemId) {

			result.setAbstract(getAbstractFor(itemId, language));

			AlternateAbstract altAbstract = getAlternateAbstractFor(itemId,
					language);
			if (null != altAbstract) {
				result.setAlternateAbstract(altAbstract);
			}

			result.setDepartments(getDepartmentsFor(itemId));
			result.setKeywords(getKeywordsFor(itemId));
			result.setAuthors(getAuthorsFor(itemId, language));
			result.setAlternateTitles(getAlternateTitlesFor(itemId));
			result.setCmteMembers(getCommitteeMembersFor(itemId));
			String delimitedAdvisorStr = cursor.getString(kColumnAdvisors);
			if (null != delimitedAdvisorStr) {
				result.setAdvisors(getAdvisorsFor(itemId, delimitedAdvisorStr,
						language));
			}
		}
		String volumeIssueId = cursor.getString(kColumnVolumeIssueId);
		if (null != volumeIssueId) {
			result.setBatch(getBatchFor(volumeIssueId));
		}
		result.setSchool(getSchoolFor(cursor.getString(kColumnSchoolId)));
		return result;
	}

	private Authors getAuthorsFor(String itemId, String language)
			throws SQLException {
		Authors result = new Authors();
		ResultSet cursor = null;
		int sequenceNumber = 0;

		try {
			authorsStatement.setString(1, itemId);
			cursor = authorsStatement.executeQuery();

			if (cursor.next()) {
				PrimaryAuthor primaryAuthor = new PrimaryAuthor();
				SplitAuthorNames splitNames = new SplitAuthorNames(
						cursor.getString(kColumnAuthorFullName));
				primaryAuthor.setAuthorFullName(required(splitNames.getFull()));
				primaryAuthor.setLastName(required(splitNames.getLast()));
				primaryAuthor.setFirstName(splitNames.getFirst());
				primaryAuthor.setMiddleName(splitNames.getMiddle());
				String authorOrcId = cursor.getString(kColumnAuthorOrcId);
				if (null != authorOrcId) {
					primaryAuthor.setORCID(authorOrcId);
				}
				String alternateFullName = cursor
						.getString(kColumnAuthorAlternateFullName);
				if (null != alternateFullName) {
					PrimaryAuthor.AltAuthorFullName alternateAuthor = new PrimaryAuthor.AltAuthorFullName();
					alternateAuthor.setValue(alternateFullName);
					alternateAuthor.setLanguage(language);
					primaryAuthor.setAltAuthorFullName(alternateAuthor);
				}
				primaryAuthor.getDegrees().add(
						getDegreesFor(cursor.getString(kColumnAuthorId)));
				result.setPrimaryAuthor(primaryAuthor);
			}

			while (cursor.next()) {
				sequenceNumber++;
				SecondaryAuthor secondaryAuthor = new SecondaryAuthor();
				SplitAuthorNames splitNames = new SplitAuthorNames(
						cursor.getString(kColumnAuthorFullName));
				secondaryAuthor
						.setAuthorFullName(required(splitNames.getFull()));
				secondaryAuthor.setLastName(required(splitNames.getLast()));
				secondaryAuthor.setFirstName(splitNames.getFirst());
				secondaryAuthor.setMiddleName(splitNames.getMiddle());
				String authorOrcId = cursor.getString(kColumnAuthorOrcId);
				if (null != authorOrcId) {
					secondaryAuthor.setORCID(authorOrcId);
				}
				secondaryAuthor.setSequenceNumber(sequenceNumber);
				String alternateFullName = cursor
						.getString(kColumnAuthorAlternateFullName);
				if (null != alternateFullName) {
					SecondaryAuthor.AltAuthorFullName alternateAuthor = new SecondaryAuthor.AltAuthorFullName();
					alternateAuthor.setValue(alternateFullName);
					alternateAuthor.setLanguage(language);
					secondaryAuthor.setAltAuthorFullName(alternateAuthor);
				}
				Degrees degrees = getDegreesFor(cursor
						.getString(kColumnAuthorId));
				if (!degrees.getDegree().isEmpty()) {
					secondaryAuthor.getDegrees().add(degrees);
				}
				result.getSecondaryAuthor().add(secondaryAuthor);
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	private Degrees getDegreesFor(String authorId) throws SQLException {
		Degrees result = new Degrees();
		if (null != authorId) {
			ResultSet cursor = null;
			try {
				degreeStatement.setString(1, authorId);
				cursor = degreeStatement.executeQuery();
				while (cursor.next()) {
					Degree degree = new Degree();
					degree.setDegreeCode(required(cursor
							.getString(kColumnDegreeCode)));
					degree.setDegreeDescription(required(cursor
							.getString(kColumnDegreeDescription)));
					degree.setDegreeYear(cursor.getShort(kColumnDegreeYear));
					degree.setSequenceNumber(cursor
							.getInt(kColumnDegreeSequenceNumber));
					result.getDegree().add(degree);
				}
			} finally {
				if (null != cursor) {
					cursor.close();
				}
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
				result.setDBTypeCode(required(cursor
						.getString(kColumnBatchTypeCode)));
				result.setDBTypeDesc(required(cursor
						.getString(kColumnBatchDescription)));
				result.setVolumeIssue(required(cursor
						.getString(kColumnVolumeIssue)));
				result.setDAISectionCode(trimmed(cursor
						.getString(kColumnDiaSectionCode)));
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	private School getSchoolFor(String schoolId) throws SQLException {
		School result = null;
		if (null != schoolId) {
			ResultSet cursor = null;
			try {
				schoolStatement.setString(1, schoolId);
				cursor = schoolStatement.executeQuery();
				if (cursor.next()) {
					result = new School();
					result.setSchoolCode(required(cursor
							.getString(kColumnSchoolCode)));
					result.setSchoolName(required(cursor
							.getString(kColumnSchoolName)));
					result.setSchoolCountry(required(cursor
							.getString(kColumnSchoolCountry)));
					result.setSchoolState(trimmed(cursor
							.getString(kColumnSchoolState)));
				}
			} finally {
				if (null != cursor) {
					cursor.close();
				}
			}
		}
		return result;
	}

	private Title makeTitleFrom(ResultSet cursor, String language)
			throws SQLException {
		Title result = new Title();
		String electronicTitle = processTextForPlatform(cursor
				.getString(kColumnElectronicTitle));
		String masterTitle = processTextForPlatform(cursor
				.getString(kColumnMasterTitle));
		result.setValue(required(((null != electronicTitle) ? electronicTitle
				: masterTitle)));
		result.setLanguage(language);
		return result;
	}

	private Abstract getAbstractFor(String itemId, String language)
			throws SQLException {
		Abstract result = null;

		ResultSet cursor = null;
		try {
			abstractStatement.setString(1, itemId);
			cursor = abstractStatement.executeQuery();
			if (cursor.next()) {
				result = new Abstract();
				result.setValue(processTextForPlatform((cursor
						.getString(kColumnAbstract))));
				result.setLanguage(language);
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	private AlternateAbstract getAlternateAbstractFor(String itemId,
			String language) throws SQLException {
		AlternateAbstract result = null;

		ResultSet cursor = null;
		try {
			alternateAbstractStatement.setString(1, itemId);
			cursor = alternateAbstractStatement.executeQuery();
			if (cursor.next()) {
				result = new AlternateAbstract();
				result.setValue(processTextForPlatform((cursor
						.getString(kColumnAlternateAbstract))));
				result.setLanguage(language);
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	private AlternateTitles getAlternateTitlesFor(String itemId)
			throws SQLException {
		AlternateTitles result = null;
		ResultSet cursor = null;
		try {
			alternateTitlesStatement.setString(1, itemId);
			cursor = alternateTitlesStatement.executeQuery();
			while (cursor.next()) {
				if (null == result) {
					result = new AlternateTitles();
				}
				AlternateTitle item = new AlternateTitle();
				item.setValue(processTextForPlatform(cursor
						.getString(kColumnAlternateTitle)));
				item.setLanguage(trimmed(cursor
						.getString(kColumnAlternateTitleLanguage)));
				result.setAlternateTitle(item);
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	private Departments getDepartmentsFor(String itemId) throws SQLException {
		Departments result = null;
		ResultSet cursor = null;
		try {
			departmentsStatement.setString(1, itemId);
			cursor = departmentsStatement.executeQuery();
			while (cursor.next()) {
				if (null == result) {
					result = new Departments();
				}
				result.getDepartment().add(
						trimmed(cursor.getString(kColumnDepartment)));
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	private Keywords getKeywordsFor(String itemId) throws SQLException {
		Keywords result = null;
		ResultSet cursor = null;
		try {
			keywordsStatement.setString(1, itemId);
			cursor = keywordsStatement.executeQuery();
			while (cursor.next()) {
				if (null == result) {
					result = new Keywords();
				}
				Keyword item = new Keyword();
				item.setValue(trimmed(cursor.getString(kColumnKeyword)));
				item.setSource(trimmed(cursor.getString(kColumnKeywordSource)));
				result.getKeyword().add(item);
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	private CmteMembers getCommitteeMembersFor(String itemId)
			throws SQLException {
		CmteMembers result = null;
		ResultSet cursor = null;
		try {
			committeeMembersStatement.setString(1, itemId);
			cursor = committeeMembersStatement.executeQuery();
			while (cursor.next()) {
				if (null == result) {
					result = new CmteMembers();
				}
				CmteMember item = new CmteMember();
				item.setFirstName(required(cursor
						.getString(kColumnCommitteeFirstName)));
				item.setMiddleName(trimmed(cursor
						.getString(kColumnCommitteeMiddleName)));
				item.setLastName(required(cursor
						.getString(kColumnCommitteeLastName)));
				item.setSuffix(trimmed(cursor.getString(kColumnCommitteeSuffix)));
				result.getCmteMember().add(item);
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	private Advisors getAdvisorsFor(String itemId, String delimitedAdvisorStr,
			String language) throws SQLException {
		Advisors result = null;
		List<String> advisors = SplitAdvisors.split(delimitedAdvisorStr);
		List<AltAdvisorFullName> altAdvisors = getAlternateAdvisorsFor(itemId,
				language);
		for (int i = 0; i < advisors.size(); ++i) {
			Advisor item = new Advisor();
			item.setAdvisorFullName(advisors.get(i));
			if (altAdvisors.size() >= i + 1) {
				item.setAltAdvisorFullName(altAdvisors.get(i));
			}
			if (null == result) {
				result = new Advisors();
			}
			result.getAdvisor().add(item);
		}
		return result;
	}

	private List<AltAdvisorFullName> getAlternateAdvisorsFor(String itemId,
			String language) throws SQLException {
		List<AltAdvisorFullName> result = Lists.newArrayList();
		ResultSet cursor = null;
		try {
			alternateAdvisorsStatement.setString(1, itemId);
			cursor = alternateAdvisorsStatement.executeQuery();
			while (cursor.next()) {
				AltAdvisorFullName item = new AltAdvisorFullName();
				item.setValue(cursor.getString(kColumnAlternateAdvisorName));
				item.setLanguage(language);
				result.add(item);
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
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

	private String processTextForPlatform(String x) {
		String result = x;
		if (null != x) {
			result = textNormalizer.removeAdeptTags(result);
			result = textNormalizer.addCdataTags(result);
		}
		return result;
	}

}

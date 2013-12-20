package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.CmteMember;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.DissLanguage;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Subject;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SuppFile;
import com.proquest.mtg.dismetadataservice.metadata.Author;
import com.proquest.mtg.dismetadataservice.metadata.Author.Degree;
import com.proquest.mtg.dismetadataservice.metadata.SplitAuthorNames;

public class PubMetaDataQuery {
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
	
	private static final String kSelectMainPubData =
            "SELECT " +
                  "ditm.ditm_id " + kColumnItemId + ", " +
                  "dvi_id " + kColumnVolumeIssueId + ", " +
                  "ditm_pub_number " + kColumnPubId  + ", " + 
                  "ditm_adviser " + kColumnAdvisors + ", " +
                  "ditm_isbn_number " + kColumnIsbn + ", " +
                  "ditm_publication_page_number " + kColumnPageNumber + ", " +
                  "ditm_page_count " + kColumnPageCount + ", " +
                  "ditm_reference_location " + kColumnReferenceLocation + ", " +
                  "ditm_bl_no " + kColumnBritishLibraryNumber + ", " +
                  "ditm_source " + kColumnSource + ", " +
                  "ditm_ft_url " + kColumnExternalUrl + ", " +
                  "( SELECT dttl_text FROM dis.dis_titles WHERE dvtl_code = 'M' AND ditm_id = ditm.ditm_id ) " + kColumnMasterTitle + ", " +
                  "( SELECT dttl_text FROM dis.dis_titles WHERE dvtl_code = 'P' AND ditm_id = ditm.ditm_id ) " + kColumnElectronicTitle + ", " +
                  "( SELECT dttl_text FROM dis.dis_titles WHERE dvtl_code = 'E' AND ditm_id = ditm.ditm_id ) " + kColumnEngOverwriteTitle + ", " +
                  "( SELECT dttl_text FROM dis.dis_titles WHERE dvtl_code = 'F' AND ditm_id = ditm.ditm_id ) " + kColumnForeignTitle + " " +
            "FROM " +
                  "dis.dis_items ditm " +
            "WHERE " +
                  "ditm.ditm_pub_number = ? AND " +
                  "ditm.dvi_id IS NOT NULL ";
	
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
				"dsa_fullname " + kColumnAuthorAlternateFullName + " " + 
			"FROM " +
				"dis.dis_authors dath, " + 
				"dis.dis_supp_authors dsa " + 
			"WHERE " +
				"dath.ditm_id = ? AND " +  
				"dath.dath_id = dsa.dath_id(+) AND " + 
				"dath.dath_sequence_number = dsa.dsa_sequence_number(+) " + 
			"ORDER BY dath_sequence_number "; 
	
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
	
	
	private PreparedStatement authorsStatement;
	private PreparedStatement mainPubDataStatement;
	private PreparedStatement languageStatement;
	private PreparedStatement degreeStatement;
	private PreparedStatement abstractStatement;
	private PreparedStatement subjectsStatement;
	private PreparedStatement committeeMembersStatement;
	private PreparedStatement supplementalFilesStatement;
	
	public PubMetaDataQuery(Connection connection) throws SQLException {
		this.authorsStatement = connection.prepareStatement(kSelectAuthors);
		this.mainPubDataStatement = connection.prepareStatement(kSelectMainPubData);
		this.languageStatement = connection.prepareStatement(kSelectLanguage);
		this.degreeStatement = connection.prepareStatement(kSelectDegree);
		this.abstractStatement = connection.prepareStatement(kSelectAbstract);
		this.subjectsStatement = connection.prepareStatement(kSelectSubjects);
		this.committeeMembersStatement = connection.prepareStatement(kSelectCommitteeMembers);
		this.supplementalFilesStatement = connection.prepareStatement(kSelectSupplementalFiles);
	}
	
	public DisPubMetaData getFor(String pubId) throws SQLException {
		DisPubMetaData result = null;
		ResultSet cursor = null;
		try {
			mainPubDataStatement.setString(1, pubId);
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
		result.setBLNumber(trimmed(cursor.getString(kColumnBritishLibraryNumber)));
		result.setReferenceLocation(trimmed(cursor.getString(kColumnReferenceLocation)));
//		result.setTitle(makeTitleFrom(cursor, language));
		String source = trimmed(cursor.getString(kColumnSource));
		if (null != source && source.equalsIgnoreCase("I")) {
			result.setExternalURL(trimmed(cursor.getString(kColumnExternalUrl)));
		}
//		String itemId = cursor.getString(kColumnItemId);
		if (null != itemId) {
			result.setDissLanguages(getLanguagesFor(itemId));
			result.setSubjects(getSubjectsFor(itemId));
			result.setAbstract(required(getAbstractFor(itemId)));
//			result.setDepartments(getDepartmentsFor(itemId));
//			result.setKeywords(getKeywordsFor(itemId));
			result.setSuppFiles(getSupplementalFilesFor(itemId));
			result.setAuthors(getAuthorsFor(itemId));
//			result.setAlternateTitles(getAlternateTitlesFor(itemId));
			result.setCmteMembers(getCommitteeMembersFor(itemId));
//			String delimitedAdvisorStr = cursor.getString(kColumnAdvisors);
//			if (null != delimitedAdvisorStr) {
//				result.setAdvisors(getAdvisorsFor(itemId, delimitedAdvisorStr, language));
//			}
		}
//		String volumeIssueId = cursor.getString(kColumnVolumeIssueId);
//		if (null != volumeIssueId) {
//			result.setBatch(getBatchFor(volumeIssueId));
//		}
//		result.setSchool(getSchoolFor(cursor.getString(kColumnSchoolId)));
//		result.setDateOfExtraction(getExtractionDateAsInt());
//		result.setPQOpenURL(makePqOpenUrlFor(pubId));
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
	
	public void close() throws SQLException {
		closeStatement(authorsStatement);
		closeStatement(mainPubDataStatement);
		closeStatement(degreeStatement);
		closeStatement(abstractStatement);
		closeStatement(subjectsStatement);
		closeStatement(languageStatement);
	}
	
	private void closeStatement(PreparedStatement statment) throws SQLException {
		if (null != statment) {
			statment.close();
		}
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

package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.DissLanguage;
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
	
	
	
	private static final String kColumnAuthorId = "AuthorId"; 
	private static final String kColumnAuthorSequenceNumber = "AuthorSequenceNumber"; 
	private static final String kColumnAuthorFullName = "AuthorFullName";
	private static final String kColumnAuthorAlternateFullName = "AuthorAlternateFullName";
	
	private static final String kColumnDegreeCode = "DegreeCode";
	private static final String kColumnDegreeDescription = "DegreeDescription";
	private static final String kColumnDegreeYear = "DegreeYear";
	private static final String kColumnDegreeSequenceNumber = "DegreeSequenceNumber";
	
	
	private static final String kSelectMainPubData =
			"SELECT " +
				"ditm_id " + kColumnItemId + ", " +
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
				"( SELECT b.dvl_description " + kColumnLanguageDescription + ", " +
					"a.dvl_code " +  kColumnLanguageCode + ", " +
					"FROM dis.dis_items_languages a, dis.dis_valid_languages b " +
					"WHERE a.ditm_id = ditm.ditm_id AND a.dvl_code = b.dvl_code ) " + 
			"FROM " +
				"dis.dis_items ditm " +
			"WHERE " +
				"ditm.ditm_pub_number = ? " +
				"AND " +
				"ditm.dvi_id IS NOT NULL ";
	
	private static final String kSelectAuthors = 
			"SELECT " +
				"dath.dath_id " + kColumnAuthorId + ", " +
				"dath_sequence_number " + kColumnAuthorSequenceNumber + ", " +
				"dath_fullname " + kColumnAuthorFullName + ", " + 
				"dsa_fullname " + kColumnAuthorAlternateFullName + ", " +
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
	
	
	private PreparedStatement authorsStatement;
	private PreparedStatement mainPubDataStatement;
	private PreparedStatement degreeStatement;
	
	public PubMetaDataQuery(Connection connection) throws SQLException {
		this.authorsStatement = connection.prepareStatement(kSelectAuthors);
		this.mainPubDataStatement = connection.prepareStatement(kSelectMainPubData);
		this.degreeStatement = connection.prepareStatement(kSelectDegree);
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
		String languageDescription = trimmed(cursor.getString(kColumnLanguageDescription));
		String languageCode = trimmed(cursor.getString(kColumnLanguageCode));
		DissLanguage language = new DissLanguage(required(languageDescription), required(languageCode));
		result.setDissLanguage(language);
//		result.setTitle(makeTitleFrom(cursor, language));
//		String source = trimmed(cursor.getString(kColumnSource));
//		if (null != source && source.equalsIgnoreCase("I")) {
//			result.setExternalURL(trimmed(cursor.getString(kColumnExternalUrl)));
//		}
//		String itemId = cursor.getString(kColumnItemId);
		if (null != itemId) {
//			result.setSubjects(getSubjectsFor(itemId));
//			result.setAbstract(getAbstractFor(itemId, language));
//			result.setDepartments(getDepartmentsFor(itemId));
//			result.setKeywords(getKeywordsFor(itemId));
//			result.setSuppFiles(getSupplementalFilesFor(itemId));
			result.setAuthors(getAuthorsFor(itemId));
//			result.setAlternateTitles(getAlternateTitlesFor(itemId));
//			result.setCmteMembers(getCommitteeMembersFor(itemId));
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

	
	public void close() throws SQLException {
		closeStatement(authorsStatement);
		closeStatement(mainPubDataStatement);
		closeStatement(degreeStatement);
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

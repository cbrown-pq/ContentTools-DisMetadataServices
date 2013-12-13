package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	private static final String kColumnLanguage = "Language";
	private static final String kColumnMasterTitle = "MasterTitle";
	private static final String kColumnElectronicTitle = "ElectronicTitle";
	
	
	
	private static final String kColumnAuthorId = "AuthorId"; 
	private static final String kColumnAuthorSequenceNumber = "AuthorSequenceNumber"; 
	private static final String kColumnAuthorFullName = "AuthorFullName";
	private static final String kColumnAuthorAlternateFullName = "AuthorAlternateFullName";
	
	
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
				"( SELECT dvl_description " +
					"FROM dis.dis_items_languages a, dis.dis_valid_languages b " +
					"WHERE a.ditm_id = ditm.ditm_id AND a.dvl_code = b.dvl_code ) " + kColumnLanguage + " " +
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
	
	private PreparedStatement authorsStatement;
	private PreparedStatement mainPubDataStatement;
	
	public PubMetaDataQuery(Connection connection) throws SQLException {
		this.authorsStatement = connection.prepareStatement(kSelectAuthors);
		this.mainPubDataStatement = connection.prepareStatement(kSelectMainPubData);
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
		String pubId = cursor.getString(kColumnPubId);
		result.setPubNumber(required(pubId));
		result.setISBN(trimmed(cursor.getString(kColumnIsbn)));
		result.setPubPageNum(trimmed(cursor.getString(kColumnPageNumber)));
		result.setPageCount(trimmed(cursor.getString(kColumnPageCount)));
//		result.setBLNumber(trimmed(cursor.getString(kColumnBritishLibraryNumber)));
//		result.setReferenceLocation(trimmed(cursor.getString(kColumnReferenceLocation)));
//		result.setDissLanguages(new DissLanguages());
//		String language = trimmed(cursor.getString(kColumnLanguage));
//		result.getDissLanguages().getDissLanguage().add(required(language));
//		result.setTitle(makeTitleFrom(cursor, language));
//		String source = trimmed(cursor.getString(kColumnSource));
//		if (null != source && source.equalsIgnoreCase("I")) {
//			result.setExternalURL(trimmed(cursor.getString(kColumnExternalUrl)));
//		}
//		String itemId = cursor.getString(kColumnItemId);
//		if (null != itemId) {
//			result.setSubjects(getSubjectsFor(itemId));
//			result.setAbstract(getAbstractFor(itemId, language));
//			result.setDepartments(getDepartmentsFor(itemId));
//			result.setKeywords(getKeywordsFor(itemId));
//			result.setSuppFiles(getSupplementalFilesFor(itemId));
//			result.setAuthors(getAuthorsFor(itemId, language));
//			result.setAlternateTitles(getAlternateTitlesFor(itemId));
//			result.setCmteMembers(getCommitteeMembersFor(itemId));
//			String delimitedAdvisorStr = cursor.getString(kColumnAdvisors);
//			if (null != delimitedAdvisorStr) {
//				result.setAdvisors(getAdvisorsFor(itemId, delimitedAdvisorStr, language));
//			}
//		}
//		String volumeIssueId = cursor.getString(kColumnVolumeIssueId);
//		if (null != volumeIssueId) {
//			result.setBatch(getBatchFor(volumeIssueId));
//		}
//		result.setSchool(getSchoolFor(cursor.getString(kColumnSchoolId)));
//		result.setDateOfExtraction(getExtractionDateAsInt());
//		result.setPQOpenURL(makePqOpenUrlFor(pubId));
		return result;
	}

	public void close() throws SQLException {
		closeStatement(authorsStatement);
		closeStatement(mainPubDataStatement);
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

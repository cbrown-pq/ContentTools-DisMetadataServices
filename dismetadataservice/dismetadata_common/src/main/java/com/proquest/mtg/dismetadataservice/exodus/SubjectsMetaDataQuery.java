package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proquest.mtg.dismetadataservice.subjects.xml.Subjects.Subject;

public class SubjectsMetaDataQuery {
	
	public static final String kSubjCode = "SubjCode";
	public static final String kSubjDesc = "SubjDesc";
	public static final String kSubjectPQOnlineDesc = "SubjectPQOnlineDesc";
	public static final String kSubjectStatus = "SubjectStatus";
	public static final String kSubjectStatusDate = "SubjectStatusDate";
	
	private PreparedStatement subjectsStatement;
	

	private static final String kSelectSubjects = "select dvsc_code " + kSubjCode	+ ", " +
			 "dvsc_description "	+ kSubjDesc + ", " +
			 "dvsc_pq_description "	+ kSubjectPQOnlineDesc + ", " +
			 "dvsc_status_flag "	+ kSubjectStatus + ", " +
			 "dvsc_status_date "	+ kSubjectStatusDate + " " +
			 "from dis_valid_subjects ";
	
	public SubjectsMetaDataQuery(Connection connection) throws SQLException {
		this.subjectsStatement = connection.prepareStatement(kSelectSubjects);
	}
	
	public List<Subject> getAllSubjects() throws SQLException {
		List<Subject> result = new ArrayList<Subject>();
		ResultSet cursor = null;
		try {
			cursor = subjectsStatement.executeQuery();
			while (cursor.next()) {
				Subject subject = new Subject();
				subject.setSubjectCode(cursor.getString(kSubjCode));
				subject.setSubjectDesc(cursor.getString(kSubjDesc));
				subject.setPQOnlineDescriptions( SplitPQOnlineSubjectsDescriptors.split(cursor.getString(
							kSubjectPQOnlineDesc)));
				subject.setStatus(cursor.getString(kSubjectStatus));
				subject.setStatusDate(cursor.getString(kSubjectStatusDate));
				result.add(subject);
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}
	
	public void close() throws SQLException {
		closeStatement(subjectsStatement);
	}

	private void closeStatement(PreparedStatement statement) throws SQLException {
		if (null != statement) {
			statement.close();
		}
	}
}

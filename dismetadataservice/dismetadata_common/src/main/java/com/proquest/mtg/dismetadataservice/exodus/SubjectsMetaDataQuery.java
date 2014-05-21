package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proquest.mtg.dismetadataservice.subjects.xml.Subjects;
import com.proquest.mtg.dismetadataservice.subjects.xml.Subjects.Subject;
import com.proquest.mtg.dismetadataservice.subjects.xml.Subjects.Subject.PQOnlineDescriptions;

public class SubjectsMetaDataQuery {
	
	public static final String kSubjCode = "SubjCode";
	public static final String kSubjDesc = "SubjDesc";
	public static final String kSubjectPQOnlineDesc = "SubjectPQOnlineDesc";
	public static final String kSubjectStatus = "SubjectStatus";
	public static final String kSubjectStatusDate = "SubjectStatusDate";
	
	private PreparedStatement subjectsStatement;
	
	private Connection connection;

	private static final String kSelectSubjects = "select dvsc_code " + kSubjCode	+ ", " +
			 "dvsc_description "	+ kSubjDesc + ", " +
			 "'ONLINEDESC1;ONLINEDESC2' "	+ kSubjectPQOnlineDesc + ", " +
			 "dvsc_status_flag "	+ kSubjectStatus + ", " +
			 "dvsc_status_date "	+ kSubjectStatusDate + " " +
			 "from dis_valid_subjects ";
	
	public SubjectsMetaDataQuery(Connection connection) throws SQLException {
		this.subjectsStatement = connection.prepareStatement(kSelectSubjects);
		this.connection = connection;
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
				String[] onlineDescriptionString = cursor.getString(kSubjectPQOnlineDesc)
						.split(";");
				List<String> descriptions = new ArrayList<String>();
				for (int i = 0; i < onlineDescriptionString.length; i++) {
					descriptions.add(onlineDescriptionString[i]);
				}
				PQOnlineDescriptions pqOnlineDescriptions = new PQOnlineDescriptions();
				pqOnlineDescriptions.setPQOnlineDescription(descriptions);
				subject.setPQOnlineDescriptions(pqOnlineDescriptions);
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

package com.proquest.mtg.dismetadataservice.metadata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;

public class DisGeneralMappingDAO {
	
	private final static Pattern LTRIM = Pattern.compile("^%+");
	private final static Pattern RTRIM = Pattern.compile("%+$");
	
	private static final String kDegreeValue_1 = "DGR_VALUE_1";
	private static final String kDegreeValue_2 = "DGR_VALUE_2";
			
	private static final String kSelectDisGenMapping = 
			"SELECT " +
				"dgr_value_1 " + kDegreeValue_1 + "," +
				"dgr_value_2 " + kDegreeValue_2   + " " + 
			"FROM " + 
				"dis_gen_mapping " +
			"WHERE " + 
				"dgr_system = ?";
	
	private PreparedStatement disMappingStatement;
	private Connection connection;
	
	public DisGeneralMappingDAO(Connection connection) throws SQLException {
		this.disMappingStatement = connection.prepareStatement(kSelectDisGenMapping);
	}
	
	public List<DisGeneralMapping> getAllMappingFor(String degreeSystem) throws SQLException {
		List<DisGeneralMapping> result = null;
		ResultSet cursor = null;
		try {
			disMappingStatement.setString(1, degreeSystem);
			cursor = disMappingStatement.executeQuery();
			result = makeMappingFrom(cursor);
		}
		finally {
			if (null != cursor) {
				cursor.close();
			}
			if(null != connection) {
				connection.close();
			}
		}
		return result;
	}

	private List<DisGeneralMapping> makeMappingFrom(ResultSet cursor) throws SQLException {
		List<DisGeneralMapping> result = Lists.newArrayList();
		while (cursor.next()) {
			String dgrValue_1 = cursor.getString(kDegreeValue_1);
			String dgrValue_2 = cursor.getString(kDegreeValue_2);
			DisGeneralMapping disGeneralMapping = new DisGeneralMapping(
					RTRIM.matcher(LTRIM.matcher(dgrValue_1).replaceAll("")).replaceAll(""), 
					dgrValue_2);
			result.add(disGeneralMapping);
		}
		return result;
	}

}

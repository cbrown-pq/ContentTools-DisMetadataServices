//package com.proquest.mtg.dismetadataservice.exodus;
//
//import java.sql.CallableStatement;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Types;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//
//import com.proquest.mtg.dismetadataservice.externalurl.xml.DissertationList;
//import com.proquest.mtg.dismetadataservice.externalurl.xml.DissertationList.Dissertation;
//
//
//public class ExternalUrlDataQuery {
//	
//	public static final String kPubId = "PubId";
//	public static final String kExternalUrl = "ExternalUrl";
//	private Connection connection;
//	private PreparedStatement externalUrlStatement;
//	private CallableStatement exodusStatement;
//
//	private static final String kSelectExternalUrls = "select " + 
//			 "ditm_pub_number "	+ kPubId + ", " +
//			 "ditm_ext_url "	+ kExternalUrl + " " +		
//			 "from dis_items where " +
//			 "ditm_ext_url is not null AND " +
//			 "((DITM_EXT_URL_STAT_CODE = 'INVALID' OR DITM_EXT_URL_STAT_CODE IS NULL) OR " +
//			 "(DITM_EXT_URL_STAT_CODE = 'VALID' AND DITM_EXT_URL_STAT_DATE < "
//			 + "to_date(?,'YYYYMMDD') - 365))";
//	
//	private static final String kUpdateExternalUrlStatus =
//			"{? = call dis_update_ext_url_stat_code(?,?)}";
//	
//	public ExternalUrlDataQuery(Connection inConnection) throws Exception {
//		this.connection = inConnection;
//	}
//	
//	public DissertationList getAllExternalUrls(String lastRunDate) throws Exception {
//		this.externalUrlStatement = connection.prepareStatement(kSelectExternalUrls);
//		DissertationList result = new DissertationList();
//		ResultSet cursor = null;
//		try {
//			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyymmdd");
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(dateFormatter.parse(lastRunDate));
//			externalUrlStatement.setString(1, dateFormatter.format(cal.getTime()));
//			cursor = externalUrlStatement.executeQuery();
//			while (cursor.next()) {
//				Dissertation dissertation = new Dissertation();
//				dissertation.setPubID(cursor.getString(kPubId));
//				dissertation.setExtURL(cursor.getString(kExternalUrl));
//				result.getDissertation().add(dissertation);
//			}
//		} finally {
//			if (null != cursor) {
//				cursor.close();
//			}
//		}
//		return result;
//	}
//	
//	public String updateExternalUrlStatus(String pubid, String status) throws Exception {
//		this.exodusStatement = connection.prepareCall(kUpdateExternalUrlStatus);
//		int ret = 2;
//		this.exodusStatement.registerOutParameter(1, Types.INTEGER );
//		this.exodusStatement.setString(2, pubid);
//		this.exodusStatement.setString(3, status);
//		this.exodusStatement.executeUpdate();
//		
//		ret = this.exodusStatement.getInt(1);
//		
//		if(ret == 0){
//			return "Update successful";
//		}else if (ret == 2){
//			return "Error! Pub not found";
//		}else{
//			return "Error! Unknown error";
//		}
//	}
//	
//	public void close() throws SQLException {
//		closeStatement(externalUrlStatement);
//	}
//
//	private void closeStatement(PreparedStatement statement) throws SQLException {
//		if (null != statement) {
//			statement.close();
//		}
//	}
//}

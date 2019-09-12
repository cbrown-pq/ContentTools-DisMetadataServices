package com.proquest.mtg.dismetadataservice.vms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.PreparedStatement;

public class VMSMetaDataQuery {
	Connection connection;

	public VMSMetaDataQuery(Connection connection) {
		this.connection = connection;
	}

	public String getPQDeliveryData(String startDate, String endDate,String pubList) throws Exception {
		JSONArray jsonArray = new JSONArray();
		String pqDeliveryDataQuery = "SELECT B.BATCH_ID,"+
				 "D.DOCUMENT_EXTERNAL_ID, " +
				 "DH.DOCUMENT_STATUS, "	 +
				 "DH.DOCUMENT_STATUS_DATE "	+
				 "FROM BATCH B,BATCH_TO_DELIVERY DB,DELIVERY_TO_DOCUMENTS DTD,DOCUMENTS D,DOCUMENT_HISTORY DH,DOCUMENT_TO_HISTORY DTH " +
				 "WHERE B.BATCH_ID = DB.BATCH_ID " +
				 "AND DB.DELIVERY_JOB_ID = DTD.DELIVERY_JOB_ID " +
				 "AND DTD.DOCUMENT_ID = D.DOCUMENT_ID " +
				 "AND D.DOCUMENT_ID = DTH.DOCUMENT_ID " +
				 "AND DTH.DOCUMENT_HISTORY_ID = DH.DOCUMENT_HISTORY_ID " +
				 "AND DATE(DH.DOCUMENT_STATUS_DATE) BETWEEN '" + startDate + "' AND '" + endDate + "' " ;
		
		if(pubList != null && !pubList.isEmpty())
		{
			pqDeliveryDataQuery = pqDeliveryDataQuery +	 "AND D.DOCUMENT_EXTERNAL_ID IN(" +  pubList + ") " ;
		}
		pqDeliveryDataQuery = pqDeliveryDataQuery +	 "ORDER BY B.BATCH_ID ASC";
		PreparedStatement stmt = connection.prepareStatement(pqDeliveryDataQuery); 
		ResultSet rs=stmt.executeQuery(pqDeliveryDataQuery); 
		
        while (rs.next()) {
            int total_columns = rs.getMetaData().getColumnCount();
            JSONObject obj = new JSONObject();
            for (int i = 0; i < total_columns; i++) {
                obj.put(rs.getMetaData().getColumnLabel(i + 1).toLowerCase(), rs.getObject(i + 1));
            }
          jsonArray.put(obj);
        }
		return jsonArray.toString();
	}

}

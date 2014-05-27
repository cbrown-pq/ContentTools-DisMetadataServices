package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.google.common.collect.Lists;

public class LOCPubSelectionQuery {
	
	public static final String kDitmPubNum = "DitmPubNum";
	
	private PreparedStatement locPubStatement;
	
	private static final String kSelectLocPubs = "select dis_items.ditm_pub_number " + kDitmPubNum
			   + " from dis_work_orders dwo, dis_work_order_stations dwos, dis_items "
			   + " where dwo.diw_id = dwos.diw_id "
			   + " and dwo.dvwo_code = 'V'" 
			   + " and TRUNC(dis_items.ditm_date_of_delivery) > TO_DATE(?) - 1"
			 //  + " and SYSDATE - TRUNC(dis_items.ditm_date_of_delivery) > 5 "
			   + "and dis_items.diw_id = dwo.diw_id "
			   + " and dis_items.ditm_copyright_year IS NOT NULL "
			   + " and dwos.dwos_date_created = "
			   + "(SELECT MAX (dwos_date_created) "
                 + "FROM dis_work_order_stations dwos1 "
                 + "WHERE dwos1.diw_id = dwo.diw_id AND dwos.dvwo_code = 'V') " 
             //  + "AND dis_items.ditm_lc_date_of_delivery IS NOT NULL "
               + "ORDER BY dis_items.ditm_pub_number";
                 
             


	public LOCPubSelectionQuery(Connection connection) throws SQLException {
		this.locPubStatement = connection.prepareStatement(kSelectLocPubs);
	}
	
	public List<String> getAll(String lastRunDate) throws SQLException {
		List<String> pubIds = Lists.newArrayList();
		ResultSet cursor = null;
		try {
			locPubStatement.setString(1, lastRunDate);
			cursor = locPubStatement.executeQuery();
			while(cursor.next()) {
				pubIds.add(cursor.getString(kDitmPubNum));
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return pubIds;
	}
	
	public void close() throws SQLException {
		closeStatement(locPubStatement);
	}

	private void closeStatement(PreparedStatement statment) throws SQLException {
		if (null != statment) {
			statment.close();
		}
	}
 
 
 

}

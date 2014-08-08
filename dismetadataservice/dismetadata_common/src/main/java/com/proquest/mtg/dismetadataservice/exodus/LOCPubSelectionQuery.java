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
	
	private static final String kSelectLocPubs = "select distinct ditm.ditm_pub_number " + kDitmPubNum
    + " from dis_work_orders dwo, dis_items ditm"
    + " WHERE dwo.dvwo_code = 'V'"
    + " AND ditm.diw_id = dwo.diw_id"
    + " AND ditm.ditm_copyright_year IS NOT NULL"
    //+ " AND ditm.ditm_lc_date_of_delivery IS NULL"
    + " AND ditm.ditm_date_of_delivery IS NOT NULL"
    + " AND ditm_pub_number in('3621589','3580999','3581000','3584889','3615969','3621131','3621152','3621158','3621321','3621358','3621367','3621381')"
    + " ORDER BY ditm.ditm_pub_number";


	public LOCPubSelectionQuery(Connection connection) throws SQLException {
		this.locPubStatement = connection.prepareStatement(kSelectLocPubs);
	}
	
	public List<String> getAll() throws SQLException {
		List<String> pubIds = Lists.newArrayList();
		ResultSet cursor = null;
		try {
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

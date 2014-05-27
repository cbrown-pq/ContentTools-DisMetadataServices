package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.proquest.mtg.dismetadataservice.jdbc.IJdbcConnectionPool;

public class AddressMetaDataProvider {
	private static String kAuthorAddress = "authorAddress";
	private static String kClaimantAddress = "claimantAddress";
	private static String kSchoolAddress = "schoolAddress";
	
	private IJdbcConnectionPool connectionPool;
	
	@Inject
	public AddressMetaDataProvider(
			@Named(IJdbcConnectionPool.kExodusConnectionPool) 
			IJdbcConnectionPool connectionPool) throws SQLException {
		this.connectionPool = connectionPool;
	}

	public IJdbcConnectionPool getConnectionPool() {
		return connectionPool;
	}
		

	public List<AddressMetaData> getAddressFor(String addressTypeId, String addressType) throws Exception {
		List<AddressMetaData> result = null;
		Connection connection = null;
		AddressMetaDataQuery query = null;
		try {
			connection = getConnectionPool().getConnection();
			query = new AddressMetaDataQuery(connection);
			result = getAddress(query, addressTypeId, addressType );
		}
		finally {
			if (null != query) {
				query.close();
			}
			if (null != connection) {
				connection.close();
			}
		}
		return result;
	}
	
	private List<AddressMetaData> getAddress(AddressMetaDataQuery query, 
			String addressTypeId, 
			String addressType) throws SQLException {
		List<AddressMetaData> result = null;
		if(addressType.equalsIgnoreCase(kAuthorAddress)) {
			result = query.getAuthorAddressesFor(addressTypeId);
		} else if(addressType.equalsIgnoreCase(kClaimantAddress)) {
			result = query.getClaimantAddressesFor(addressTypeId);
		} else if(addressType.equalsIgnoreCase(kSchoolAddress)){
		//	result = query.getClaimantAddressesFor(addressTypeId);
		}
		return result;
	}

}

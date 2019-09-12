package com.proquest.mtg.dismetadataservice.vms;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.inject.Inject;
import javax.inject.Named;

import com.proquest.mtg.dismetadataservice.properties.DisMetadataProperties;

public class VmsMetaDataProvider implements IVmsMetaDataProvider {
	private String vmsDbUrl;
	private String vmsDbUserName;
	private String vmsDbPassword;

	@Inject
	public VmsMetaDataProvider(
			@Named(DisMetadataProperties.VMS_DB_URL) String vmsDbUrl,
			@Named(DisMetadataProperties.VMS_DB_USER_NAME) String vmsDbUserName,
			@Named(DisMetadataProperties.VMS_DB_PASSWORD) String vmsDbPassword)
			throws Exception {
		this.vmsDbUrl = vmsDbUrl;
		this.vmsDbUserName = vmsDbUserName;
		this.vmsDbPassword = vmsDbPassword;
	}

	public String getVmsDbUrl() {
		return vmsDbUrl;
	}

	public String getVmsDbUserName() {
		return vmsDbUserName;
	}

	public String getVmsDbPassword() {
		return vmsDbPassword;
	}

	@Override
	public String getPQDeliveryData(String startDate,String endDate,String pubList) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = null;
		VMSMetaDataQuery query;
		String result;
		try {
			connection = DriverManager.getConnection(getVmsDbUrl(),
					getVmsDbUserName(), getVmsDbPassword());
			query = new VMSMetaDataQuery(connection);
			result = query.getPQDeliveryData(startDate, endDate, pubList);
		} finally {
			if (null != connection) {
				connection.close();
			}
		}
		return result;
	}
}

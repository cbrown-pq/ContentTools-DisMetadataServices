package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressMetaDataQuery {
	
	public static final String kAddressId = "AddressId";
	public static final String kAddressName = "AddressName";
	public static final String kAddressLine1 = "AddressLine1";
	public static final String kAddressLine2 = "AddressLine2";
	public static final String kAddressLine3 = "AddressLine3";
	public static final String kAddressCity = "AddressCity";
	public static final String kAddressFourDigitZip = "AddressFourDigitZip";
	public static final String kAddressZip = "AddressZip";
	public static final String kAddressPostalCode = "AddressPostalCode";
	public static final String kAddressProvince = "AddressProvince";
	public static final String kAddressCountryCode = "AddressCountryCode";
	public static final String kAddressEffectiveDate = "AddressEffectiveDate";
	public static final String kAddressEffectiveFlag = "AddressEffectiveFlag";
	public static final String kAddressSchoolPeopleId = "AddressSchoolPeopleId";
	public static final String kAddressStateCode = "AddressStateCode";
	public static final String kAddressCountryDescription = "AddressCountryDescription";
	private static final String kAddressLocCountryDescription = "AddressLocCountryDescription";;
	
	//private PreparedStatement schoolAddressStatement;
	private PreparedStatement authorAddressStatement;
	private PreparedStatement claimantAddressStatement;
	private PreparedStatement stateCodeStatement;
	
	
	
	private static final String kSelectAddresses = "select daad_id " + kAddressId + ", " 
			+ "da.daad_name " + kAddressName + ", "
			+ "da.daad_line_1 " + kAddressLine1 + ", " 
			+ "da.daad_line_2 " + kAddressLine2 + ", " 
			+ "da.daad_line_3 " + kAddressLine3 + ", "
			+ "da.daad_city " + kAddressCity + ", " 
			+ "da.daad_zip4 " + kAddressFourDigitZip + ", " 
			+ "da.dzip_zip5 " + kAddressZip + ", "
			+ "da.daad_postal_code " + kAddressPostalCode + ", "
			+ "da.daad_province " + kAddressProvince + ", " 
			+ "da.dvc_code "+ kAddressCountryCode + ", " 
			+ "to_char(da.daad_effective_date ,'dd-mon-yyyy') " + kAddressEffectiveDate + ", "
			+ "da.daad_active_flag " + kAddressEffectiveFlag + ", "
			+ "dvc.dvc_description " + kAddressCountryDescription + ", "
			+ "dvc.dvc_loc_description " + kAddressLocCountryDescription 
			+ " from dis_addresses da, dis_valid_countries dvc "
			+ "where da.dvc_code = dvc.dvc_code and ";
	
	
	
	
//	private static final String kSelectSchoolAddresses = kSelectAddresses + ", "
//			+ "dsp_id " + kAddressSchoolPeopleId + " "
//			+ "from dis_addresses ";
	
	private static final String kSelectAuthorAddresses = kSelectAddresses 
			+ " da.dath_id = ? "
			+ "ORDER BY da.daad_effective_date desc";
	
	private static final String kSelectClaimantAddresses = kSelectAddresses 
			+ " da.dclm_id = ? "
			+ "ORDER BY da.daad_effective_date desc";
	
	private static final String kSelectStateCode = "select dsta_code " + kAddressStateCode
			+ " from dis_zip_codes "
			+ "where dzip_zip5 = ?";
	
	public AddressMetaDataQuery(Connection connection) throws SQLException {
		//this.schoolAddressStatement = connection.prepareStatement(kSelectSchoolAddresses);
		this.authorAddressStatement = connection.prepareStatement(kSelectAuthorAddresses);
		this.claimantAddressStatement = connection.prepareStatement(kSelectClaimantAddresses);
		this.stateCodeStatement = connection.prepareStatement(kSelectStateCode);
	}
	
	
	public List<AddressMetaData> getAuthorAddressesFor(String addressTypeId) throws SQLException {
		List<AddressMetaData> result = null;
		ResultSet cursor = null;
		try {
			authorAddressStatement.setString(1, addressTypeId);
			cursor = authorAddressStatement.executeQuery();
			result= populateAddress(cursor);
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}
	
	public List<AddressMetaData> getClaimantAddressesFor(String addressTypeId) throws SQLException {
		List<AddressMetaData> result = null;
		ResultSet cursor = null;
		try {
			claimantAddressStatement.setString(1, addressTypeId);
			cursor = claimantAddressStatement.executeQuery();
			result= populateAddress(cursor);
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	private List<AddressMetaData> populateAddress(ResultSet cursor)
			throws SQLException {
		List<AddressMetaData> result = new ArrayList<AddressMetaData>();
		while (cursor.next()) {
			AddressMetaData addressMetaData = new AddressMetaData();
			addressMetaData.setName(cursor.getString(kAddressName));
			addressMetaData.setLine1(cursor.getString(kAddressLine1));
			addressMetaData.setLine2(cursor.getString(kAddressLine2));
			addressMetaData.setLine3(cursor.getString(kAddressLine3));
			addressMetaData.setCity(cursor.getString(kAddressCity));
			addressMetaData.setFiveDigitzip(cursor.getString(kAddressZip));
			addressMetaData.setFourDigitzip(cursor.getString(kAddressFourDigitZip));
			addressMetaData.setPostalCode(cursor.getString(kAddressPostalCode));
			addressMetaData.setStateProvince(cursor.getString(kAddressProvince));
			addressMetaData.setCountry(cursor.getString(kAddressCountryCode));
			if(addressMetaData.getCountry().equals("US")) {
				addressMetaData.setStateCode(getStateCode(addressMetaData.getFiveDigitZip()));
			}
			addressMetaData.setCountryDescription(cursor.getString(kAddressCountryDescription));
			addressMetaData.setLocCountryDescription(cursor.getString(kAddressLocCountryDescription));
			result.add(addressMetaData);
		}
		return result;
	}
	
	private String getStateCode(String zip) throws SQLException {
		String stateCode= null;
		ResultSet cursor = null;
		stateCodeStatement.setString(1, zip);
		try {
			cursor = stateCodeStatement.executeQuery();
			if(cursor.next()) {
				stateCode = cursor.getString(kAddressStateCode);
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return stateCode;
	}


	public void close() throws SQLException {
		closeStatement(authorAddressStatement);
		closeStatement(claimantAddressStatement);
		closeStatement(stateCodeStatement);
	}

	private void closeStatement(PreparedStatement statment) throws SQLException {
		if (null != statment) {
			statment.close();
		}
	}

}

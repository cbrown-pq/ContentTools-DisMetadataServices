package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.metadata.Address;
import com.proquest.mtg.dismetadataservice.metadata.SchoolPersonType;

public class SchoolMetaDataQuery {
	
public static final String kEmptyValue = "";
public static final String kSchoolId = "SchoolId";
public static final String kSchoolCode = "SchoolCode";
public static final String kSchoolName = "SchoolName";
public static final String kSchoolCountry = "SchoolCountry";
public static final String kSchoolState = "SchoolState";

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

public static final String kSchoolPersonTitle = "SchoolTitle";
public static final String kSchoolPersonTitleCategory = "SchoolPersonTitleCategory";
public static final String kSchoolPersonDepartment = "SchoolPersonDepartment";
public static final String kSchoolPersonStatus = "SchoolPersonStatus";
public static final String kSchoolPersonEmail = "SchoolPersonEmail";
public static final String kSchoolPersonStartDate = "SchoolPersonStartDate";
public static final String kSchoolPersonEndDate = "SchoolPersonEndDate";
public static final String kNameId = "NameId";

public static final String kFirstName = "FirstName";
public static final String kMiddleName = "MiddleName";
public static final String kLastName = "LastName";
public static final String kStatus = "Status";
public static final String kStatusDate = "StatusDate";

public static final String kAddressUseType = "AddressUseType";
public static final String kEbsAccount = "AddressUseType";
public static final String kDeliveryDate = "DeliveryDate";
public static final String kDateCreated = "DateCreated";
public static final String kDateModified = "DateModified";
public static final String kAddressUseId = "AddressUseId";

public static final String kContactType = "ContactType";
public static final String kContactSequenceNumber = "ContactSequenceNumber";
public static final String kContactName = "ContactName";
public static final String kContactNotes = "ContactNotes";
public static final String kContactEffectiveDate = "ContactEffectiveDate";
public static final String kContactDateCreated = "ContactDateCreated";
public static final String kContactDateModified = "ContactDateModified";

private static final SimpleDateFormat kDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
private PreparedStatement schoolsStatement;
private PreparedStatement mainSchoolMetaDataStatement;
private PreparedStatement mainSchoolAddressesStatement;
private PreparedStatement mainSchoolPersonTypesStatement;
private static final String kSelectSchoolCodes = "select dish_id "
											+ kSchoolId +
											" from dis_schools";
								
private static final String kSelectMainSchoolMetadata = "select dish.dish_id " + kSchoolId + ", "
																  + "dish.dish_code " + kSchoolCode + ", "
		       													  + "dish.dish_name " + kSchoolName + ", "
		       													  + "dvc.dvc_description " + kSchoolCountry + ", "
		       													  + "dist.dsta_name " + kSchoolState + " "
		       													  + "from dis_schools dish, "
		       													  + "dis_valid_countries dvc, "
		       													  + "dis_states dist "
		       													  + "where dish.dvc_code = dvc.dvc_code and "
		       													  + "dish.dsta_code = dist.dsta_code and "
		       													  + "dish.dish_code = ?";
	
private static final String kSelectAddresses = "select daad_id " + kAddressId + ", " 
														+  "daad_name " + kAddressName + ", "
														+ "daad_line_1 " + kAddressLine1 + ", "
			       									    + "daad_line_2 " + kAddressLine2 + ", "					 
			       									    + "daad_line_3 " + kAddressLine3 + ", "  
			       									    + "daad_city " + kAddressCity + ", "	
			       									    + "daad_zip4 " + kAddressFourDigitZip + ", "
			       									    + "dzip_zip5 " + kAddressZip + ", "
			       									    + "daad_postal_code " + kAddressPostalCode + ", "
			       									    + "daad_province " + kAddressProvince + ", "
			       									    + "dvc_code " + kAddressCountryCode + ", "
			       									    + "daad_effective_date " + kAddressEffectiveDate + ", "
			       									    + "daad_active_flag " + kAddressEffectiveFlag + " "
			       									    + "from dis_addresses "
			       									    + "where dish_id = ?";
	
private static final String kSelectSchoolPersonTypes = "select dsp.dsp_title " + kSchoolPersonTitle + ", " 
                              									+ "dvtc.dvtc_name " + kSchoolPersonTitleCategory + ", "
																+ "dsp.dsp_department " + kSchoolPersonDepartment + ", "
																+ "dsp.dsp_status " + kSchoolPersonStatus + ", "
																+ "dea.dea_description " + kSchoolPersonEmail + ", "
																+ "dsp.dsp_start_date " + kSchoolPersonStartDate + ", "
																+ "dsp.dsp_end_date " + kSchoolPersonEndDate + ", "
																+ "dsp.dpl_id " + kNameId + " "
																+ "from dis_school_people dsp, "
																+ "dis_electronic_addresses dea, "
																+ "dis_valid_title_categories dvtc "
																+ "where dsp.dsp_id = dea.dsp_id and "
																+ "dsp.dvtc_id = dvtc.dvtc_id and "
																+ "dish_id = ?";
	
	private static final String kSelectNameTypes = "select dpl_first_name " + kFirstName + ", " 
															+ "dpl_middle_name " + kMiddleName + ", "
															+ "dpl_last_name " + kLastName + ", "
															+ "dpl_status " + kStatus + ", "
															+ "dpl_status_date " + kStatusDate + ", "
															+ "dpl_date_created " + kSchoolPersonStartDate + ", "
															+ "dpl_date_modified " + kSchoolPersonEndDate + " "
															+ "from dis_people "
															+ "where dpl_id = ?";
	
	private static final String kSelectAddressUseTypes = "select dat.dat_description " + kAddressUseType + ", " 
															+ "dau.dau_external_sys_key " + kEbsAccount + ", "
															+ "dau.dau_date_of_delivery " + kDeliveryDate + ", "
															+ "dau.dau_date_created " + kDateCreated + ", "
															+ "dau.dau_date_modified " + kDateModified + ", "
															+ "dau.dau_id " + kAddressUseId + " "
															+ "from  DIS_ADDRESS_USES dau, "
															+ "DIS_ADDRESS_TYPES dat "
															+ "where dau.daad_id = ?";
		
	private static final String kSelectContactTypes = "select dcon_type " + kContactType + ", " 
														+ "dcon_sequence_number " + kContactSequenceNumber + ", "
														+ "dcon_name " + kContactName + ", "
														+ "dcon_notes " + kContactNotes + ", "
														+ "dcon_effective_date " + kContactEffectiveDate + ", "
														+ "dcon_date_created " + kContactDateCreated + ", "
														+ "dcon_date_modified " + kContactDateModified + " "
														+ "from  dis_school_contacts "
														+ "where dau_id = ?";
												
	
	public SchoolMetaDataQuery(Connection connection) throws SQLException {
		this.schoolsStatement = connection.prepareStatement(kSelectSchoolCodes);
		this.mainSchoolMetaDataStatement = connection.prepareStatement(kSelectMainSchoolMetadata);
		this.mainSchoolAddressesStatement = connection.prepareStatement(kSelectAddresses);
		this.mainSchoolPersonTypesStatement = connection.prepareStatement(kSelectSchoolPersonTypes);
	}
	
	public List<String> getAllSchoolCodes() throws SQLException {
		List<String> result = new ArrayList<String>();
		ResultSet cursor = null;
		try {
			cursor = schoolsStatement.executeQuery();
			while (cursor.next()) {
				String schoolCode = cursor.getString(kSchoolId);
				result.add(schoolCode);
			}
		}
		finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}
	
	public DisSchoolMetaData getSchoolMetadataForSchoolCode(String schoolCode) throws SQLException {
		DisSchoolMetaData result = null;
		ResultSet cursor = null;
		try {
		mainSchoolMetaDataStatement.setString(1, schoolCode);
		cursor = mainSchoolMetaDataStatement.executeQuery();
		if (cursor.next()) {
			result = makeSchoolMetaDataFrom(cursor);
		}
		}
		finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}
	
	private DisSchoolMetaData makeSchoolMetaDataFrom(ResultSet cursor) throws SQLException {
		DisSchoolMetaData result = new DisSchoolMetaData();
		String schoolId = cursor.getString(kSchoolId);
		String schoolCode = cursor.getString(kSchoolCode);
		String schoolName = cursor.getString(kSchoolName);
		String schoolCountry = cursor.getString(kSchoolCountry);
		String schoolState = cursor.getString(kSchoolState);
		result.setSchoolId(schoolId);
		result.setSchoolCode(schoolCode);
		result.setSchoolName(schoolName);
		result.setSchoolCountry(schoolCountry);
		result.setSchoolState(schoolState);
		if(null != schoolId) {
			result.setAddresses(getAddressesFor(schoolId));
		}
		if(null != schoolId) {
			result.setSchoolPersonTypes(getSchoolPersonTypesFor(schoolId));
		}
		return result;
	}


	private List<Address> getAddressesFor(String schoolId) throws SQLException {
		List<Address> result = Lists.newArrayList();
		ResultSet cursor = null;
		try {
			mainSchoolAddressesStatement.setString(1, schoolId);
			cursor = mainSchoolAddressesStatement.executeQuery();
			while(cursor.next()){
				String addressId = cursor.getString(kAddressId);
				String addressName = cursor.getString(kAddressName);
				String addressLine1 = cursor.getString(kAddressLine1);
				String addressLine2 = cursor.getString(kAddressLine2);
				String addressLine3 = cursor.getString(kAddressLine3);
				String addressCity = cursor.getString(kAddressCity);
				String addressFourDigitZip = cursor.getString(kAddressFourDigitZip);
				String addressZip = cursor.getString(kAddressZip);
				String addressPostalCode = cursor.getString(kAddressPostalCode);
				String addressProvince = cursor.getString(kAddressProvince);
				String addressCountryCode = cursor.getString(kAddressCountryCode);
				String addressEffectiveDate = cursor.getString(kAddressEffectiveDate);
				String addressEffectiveFlag = cursor.getString(kAddressEffectiveFlag);
				Address address = new Address();
				address.setAddressId(addressId);
				address.setAddressName(addressName);
				address.setLine1(addressLine1);
				address.setLine2(addressLine2);
				address.setLine3(addressLine3);
				address.setCity(addressCity);
				address.setFourdigitzip(addressFourDigitZip);
				address.setZip(addressZip);
				address.setPostalCode(addressPostalCode);
				address.setStateProvince(addressProvince);
				address.setCountry(addressCountryCode);
				address.setEffectiveDate(addressEffectiveDate);
				address.setActiveFlag(addressEffectiveFlag);
				result.add(address);
			}
		}
		finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	private List<SchoolPersonType> getSchoolPersonTypesFor(String schoolId) throws SQLException {
		List<SchoolPersonType> result = Lists.newArrayList();
		ResultSet cursor = null;
		try {
			mainSchoolPersonTypesStatement.setString(1, schoolId);
			cursor = mainSchoolPersonTypesStatement.executeQuery();
			while(cursor.next()){
				String schoolPersonTitle = cursor.getString(kSchoolPersonTitle);
				String schoolPersonTitleCategory = cursor.getString(kSchoolPersonTitleCategory);
				String schoolPersonDepartment = cursor.getString(kSchoolPersonDepartment);
				String schoolPersonStatus = cursor.getString(kSchoolPersonStatus);
				String schoolPersonEmail = cursor.getString(kSchoolPersonEmail);
				String schoolPersonStartDate = cursor.getString(kSchoolPersonStartDate);
				String schoolPersonEndDate = cursor.getString(kSchoolPersonEndDate);
				String nameId = cursor.getString(kNameId);
				SchoolPersonType schoolPersonType = new SchoolPersonType();
				schoolPersonType.setTitle(schoolPersonTitle);
				schoolPersonType.setCategory(schoolPersonTitleCategory);
				schoolPersonType.setDepartment(schoolPersonDepartment);
				schoolPersonType.setStatus(schoolPersonStatus);
				schoolPersonType.setEmail(schoolPersonEmail);
				schoolPersonType.setStartDate(schoolPersonStartDate);
				schoolPersonType.setEndDate(schoolPersonEndDate);
				result.add(schoolPersonType);
			}
		}
		finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}
	
	public List<DisSchoolMetaData> getAllSchoolMetadata() {
		List<DisSchoolMetaData> result = null;
		return result;
	}
	
	public void close() throws SQLException {
		closeStatement(schoolsStatement);
	}
	
	private void closeStatement(PreparedStatement statment) throws SQLException {
		if (null != statment) {
			statment.close();
		}
	}
}

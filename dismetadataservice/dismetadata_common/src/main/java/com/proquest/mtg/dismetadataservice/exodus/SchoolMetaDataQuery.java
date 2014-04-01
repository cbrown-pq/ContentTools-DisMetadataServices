package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.metadata.school.Address;
import com.proquest.mtg.dismetadataservice.metadata.school.AddressUse;
import com.proquest.mtg.dismetadataservice.metadata.school.NameType;
import com.proquest.mtg.dismetadataservice.metadata.school.PersonType;
import com.proquest.mtg.dismetadataservice.metadata.school.School;
import com.proquest.mtg.dismetadataservice.metadata.school.SchoolContact;

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
	public static final String kSchoolPeopleNameId = "NameId";

	public static final String kFirstName = "FirstName";
	public static final String kMiddleName = "MiddleName";
	public static final String kLastName = "LastName";
	public static final String kStatus = "Status";
	public static final String kStatusDate = "StatusDate";

	public static final String kAddressUseType = "AddressUseType";
	public static final String kEbsAccount = "EbsAccount";
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

	private PreparedStatement schoolsStatement;
	private PreparedStatement mainSchoolMetaDataStatement;
	private PreparedStatement mainSchoolAddressesStatement;
	private PreparedStatement mainSchoolPersonTypesStatement;
	private PreparedStatement mainSchoolNameTypesStatement;
	private PreparedStatement mainSchoolAddressUsesStatement;
	private PreparedStatement mainSchoolContactTypesStatement;

	private static final String kSelectSchoolCodes = "select dish_id "
			+ kSchoolId + " from dis_schools";

	private static final String kSelectMainSchoolMetadata = "select dish.dish_id " + kSchoolId	+ ", "
			+ "dish.dish_code "	+ kSchoolCode	+ ", "
			+ "dish.dish_name "	+ kSchoolName	+ ", "
			+ "dvc.dvc_description " + kSchoolCountry + ", "
			+ "dist.dsta_name "	+ kSchoolState	+ " "
			+ "from dis_schools dish, "	+ "dis_valid_countries dvc, "
			+ "dis_states dist "
			+ "where dish.dvc_code = dvc.dvc_code and "
			+ "dish.dsta_code = dist.dsta_code and " + "dish.dish_code = ?";

	private static final String kSelectAddresses = "select daad_id "
			+ kAddressId + ", " + "daad_name " + kAddressName + ", "
			+ "daad_line_1 " + kAddressLine1 + ", " + "daad_line_2 "
			+ kAddressLine2 + ", " + "daad_line_3 " + kAddressLine3 + ", "
			+ "daad_city " + kAddressCity + ", " + "daad_zip4 "
			+ kAddressFourDigitZip + ", " + "dzip_zip5 " + kAddressZip + ", "
			+ "daad_postal_code " + kAddressPostalCode + ", "
			+ "daad_province " + kAddressProvince + ", " 
			+ "dvc_code "+ kAddressCountryCode + ", " 
			+ "to_char(daad_effective_date ,'dd-mon-yyyy') " + kAddressEffectiveDate + ", "
			+ "daad_active_flag " + kAddressEffectiveFlag + " " + "from dis_addresses "
			+ "where dish_id = ?";

	private static final String kSelectSchoolPersonTypes = "select dsp.dsp_title "	+ kSchoolPersonTitle + ", "
			+ "dvtc.dvtc_name "	+ kSchoolPersonTitleCategory + ", "
			+ "dsp.dsp_department "	+ kSchoolPersonDepartment	+ ", "
			+ "dsp.dsp_status "	+ kSchoolPersonStatus + ", "
			+ "dea.dea_description " + kSchoolPersonEmail + ", "
			+ "to_char(dsp.dsp_start_date ,'dd-mon-yyyy')"	+ kSchoolPersonStartDate + ", "
			+ "to_char(dsp.dsp_end_date,'dd-mon-yyyy') " + kSchoolPersonEndDate + ", "
			+ "dsp.dpl_id "	+ kSchoolPeopleNameId + " "
			+ "from dis_school_people dsp, "
			+ "dis_electronic_addresses dea, "
			+ "dis_valid_title_categories dvtc "
			+ "where dsp.dsp_id = dea.dsp_id and "
			+ "dsp.dvtc_id = dvtc.dvtc_id and " + "dish_id = ?";

	private static final String kSelectNameTypes = "select dpl_first_name "	+ kFirstName + ", " 
			+ "dpl_middle_name " + kMiddleName + ", "
			+ "dpl_last_name " + kLastName + ", " 
			+ "dpl_status " + kStatus + ", " 
			+ "to_char(dpl_status_date,'dd-mon-yyyy') " + kStatusDate + ", "
			+ "to_char(dpl_date_created,'dd-mon-yyyy') " + kSchoolPersonStartDate + ", "
			+ "to_char(dpl_date_modified,'dd-mon-yyyy') " + kSchoolPersonEndDate + " "
			+ "from dis_people " + "where dpl_id = ?";

	private static final String kSelectAddressUses = "select dat.dat_description "	+ kAddressUseType	+ ", "
			+ "dau.dau_external_sys_key "	+ kEbsAccount + ", "
			+ "to_char(dau.dau_date_of_delivery,'dd-mon-yyyy') "	+ kDeliveryDate	+ ", "
			+ "to_char(dau.dau_date_created,'dd-mon-yyyy') " + kDateCreated + ", "
			+ "to_char(dau.dau_date_modified,'dd-mon-yyyy') "	+ kDateModified	+ ", "
			+ "dau.dau_id "	+ kAddressUseId	+ " "
			+ "from  DIS_ADDRESS_USES dau, "
			+ "DIS_ADDRESS_TYPES dat " + "where dau.dat_type = dat.dat_type and dau.daad_id = ?";

	private static final String kSelectContactTypes = "select dcon_type " + kContactType + ", " 
															+ "dcon_sequence_number " + kContactSequenceNumber + ", " 
															+ "dcon_name " + kContactName
															+ ", " + "dcon_notes " + kContactNotes + ", "
															+ "to_char(dcon_effective_date,'dd-mon-yyyy') " + kContactEffectiveDate + ", "
															+ "to_char(dcon_date_created,'dd-mon-yyyy') " + kContactDateCreated + ", "
															+ "to_char(dcon_date_modified,'dd-mon-yyyy') " + kContactDateModified + " "
															+ "from  dis_school_contacts " + "where dau_id = ?";

	public SchoolMetaDataQuery(Connection connection) throws SQLException {
		this.schoolsStatement = connection.prepareStatement(kSelectSchoolCodes);
		this.mainSchoolMetaDataStatement = connection.prepareStatement(kSelectMainSchoolMetadata);
		this.mainSchoolAddressesStatement = connection.prepareStatement(kSelectAddresses);
		this.mainSchoolPersonTypesStatement = connection.prepareStatement(kSelectSchoolPersonTypes);
		this.mainSchoolNameTypesStatement = connection.prepareStatement(kSelectNameTypes);
		this.mainSchoolAddressUsesStatement = connection.prepareStatement(kSelectAddressUses);		
		this.mainSchoolContactTypesStatement = connection.prepareStatement(kSelectContactTypes);
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
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	public School getSchoolMetadataForSchoolCode(String schoolCode)
			throws SQLException {
		School result = null;
		ResultSet cursor = null;
		try {
			mainSchoolMetaDataStatement.setString(1, schoolCode);
			cursor = mainSchoolMetaDataStatement.executeQuery();
			if (cursor.next()) {
				result = makeSchoolMetaDataFrom(cursor);
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	private School makeSchoolMetaDataFrom(ResultSet cursor)
			throws SQLException {
		School result = new School();
		String schoolId = cursor.getString(kSchoolId);
		String schoolCode = cursor.getString(kSchoolCode);
		String schoolName = cursor.getString(kSchoolName);
		String schoolCountry = cursor.getString(kSchoolCountry);
		String schoolState = cursor.getString(kSchoolState);
		result.setId(schoolId);
		result.setCode(schoolCode);
		result.setName(schoolName);
		result.setCountry(schoolCountry);
		result.setState(schoolState);
		if (null != schoolId) {
			result.setAddresses(getAddressesFor(schoolId));
		}
		if (null != schoolId) {
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
			while (cursor.next()) {
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
				address.setAddressUses(getAddressUses(addressId));
				result.add(address);
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	private List<AddressUse> getAddressUses(String addressId) throws SQLException {
		List<AddressUse> result = Lists.newArrayList();
		ResultSet cursor = null;
		try {
			mainSchoolAddressUsesStatement.setString(1, addressId);
			cursor = mainSchoolAddressUsesStatement.executeQuery();
			while (cursor.next()) {
				String addressUseId = cursor.getString(kAddressUseId);
				String addressUseType = cursor.getString(kAddressUseType);
				String ebsAccount = cursor.getString(kEbsAccount);
				String deliveryDate = cursor.getString(kDeliveryDate);
				String dateCreated = cursor.getString(kDateCreated);
				String dateModified = cursor.getString(kDateModified);
				AddressUse addressUse = new AddressUse();
				addressUse.setType(addressUseType);
				addressUse.seteBSAccount(ebsAccount);
				addressUse.setDeliveryDate(deliveryDate);
				addressUse.setDateCreated(dateCreated);
				addressUse.setDateModified(dateModified);
				addressUse.setSchoolContacts(getSchoolContactFor(addressUseId));
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	private List<SchoolContact> getSchoolContactFor(String addressUseId) throws SQLException {
		List<SchoolContact> result = Lists.newArrayList();
		ResultSet cursor = null;
		try {
			mainSchoolContactTypesStatement.setString(1, addressUseId);
			cursor = mainSchoolContactTypesStatement.executeQuery();
			while (cursor.next()) {
				String  type = cursor.getString(kContactType);
				String contactName  = cursor.getString(kContactName );
				String contactEffectiveDate  = cursor.getString(kContactEffectiveDate );
				String contactDateCreated  = cursor.getString(kContactDateCreated );
				String contactDateModified   = cursor.getString(kContactDateModified  );
				SchoolContact schoolContact = new SchoolContact();
				schoolContact.setType(type);
				schoolContact.setName(contactName);
				schoolContact.setEffectiveDate(contactEffectiveDate);
				schoolContact.setDateCreated(contactDateCreated);
				schoolContact.setDateModified(contactDateModified);
				result.add(schoolContact);
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	private List<PersonType> getSchoolPersonTypesFor(String schoolId)
			throws SQLException {
		List<PersonType> result = Lists.newArrayList();
		ResultSet cursor = null;
		try {
			mainSchoolPersonTypesStatement.setString(1, schoolId);
			cursor = mainSchoolPersonTypesStatement.executeQuery();
			while (cursor.next()) {
				String schoolPersonTitle = cursor.getString(kSchoolPersonTitle);
				String schoolPersonTitleCategory = cursor
						.getString(kSchoolPersonTitleCategory);
				String schoolPersonDepartment = cursor
						.getString(kSchoolPersonDepartment);
				String schoolPersonStatus = cursor
						.getString(kSchoolPersonStatus);
				String schoolPersonEmail = cursor.getString(kSchoolPersonEmail);
				String schoolPersonStartDate = cursor
						.getString(kSchoolPersonStartDate);
				String schoolPersonEndDate = cursor
						.getString(kSchoolPersonEndDate);
				String schoolPeopleNameId = cursor
						.getString(kSchoolPeopleNameId);
				PersonType personType = new PersonType();
				personType.setTitle(schoolPersonTitle);
				personType.setCategory(schoolPersonTitleCategory);
				personType.setDepartment(schoolPersonDepartment);
				personType.setStatus(schoolPersonStatus);
				personType.setEmail(schoolPersonEmail);
				personType.setStartDate(schoolPersonStartDate);
				personType.setEndDate(schoolPersonEndDate);
				personType.setNameId(schoolPeopleNameId);
				personType.setNameType(getNameTypeFor(schoolPeopleNameId));
				result.add(personType);
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	private NameType getNameTypeFor(String schoolPeopleNameId)
			throws SQLException {
		NameType result = new NameType();
		ResultSet cursor = null;
		try {
			mainSchoolNameTypesStatement.setString(1, schoolPeopleNameId);
			cursor = mainSchoolNameTypesStatement.executeQuery();
			while (cursor.next()) {
				String firstName = cursor.getString(kFirstName);
				String middleName = cursor.getString(kMiddleName);
				String lastName = cursor.getString(kLastName);
				String status = cursor.getString(kStatus);
				String statusDate = cursor.getString(kStatusDate);
				String schoolPersonStartDate = cursor
						.getString(kSchoolPersonStartDate);
				String schoolPersonEndDate = cursor
						.getString(kSchoolPersonEndDate);
				result.setFirstName(firstName);
				result.setMiddleName(middleName);
				result.setLastName(lastName);
				result.setStatus(status);
				result.setStatusDate(statusDate);
				result.setDateCreated(schoolPersonStartDate);
				result.setDateModified(schoolPersonEndDate);
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	public List<School> getAllSchoolMetadata() {
		List<School> result = null;
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

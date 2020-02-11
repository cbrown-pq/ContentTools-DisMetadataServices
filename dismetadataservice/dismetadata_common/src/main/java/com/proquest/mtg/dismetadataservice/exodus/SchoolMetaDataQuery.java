/*package com.proquest.mtg.dismetadataservice.exodus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.AddressType;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.AddressType.AddressUses;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.AddressUseType;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.AddressUseType.SchoolContacts;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.AssociatedSchoolType;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.ContactType;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.NameType;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.PersonType;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.Schools.School;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.Schools.School.Addresses;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.Schools.School.AssociatedSchools;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.Schools.School.SchoolPersons;


public class SchoolMetaDataQuery {
	
	public static final String kId = "Id";
	public static final String kCode = "Code";

	public static final String kEmptyValue = "";
	public static final String kSchoolId = "SchoolId";
	public static final String kSchoolCode = "SchoolCode";
	public static final String kSchoolName = "SchoolName";
	public static final String kSchoolCountry = "SchoolCountry";
	public static final String kSchoolState = "SchoolState";
	public static final String kSchoolStateAbbrev = "SchoolStateAbbrev";
	public static final String kSchoolDashClassificationCode = "SchoolDashClassCode";
    public static final String kSchoolDashClassificationSource = "SchoolDashClassSource";
    public static final String kSchoolDashEligibilityCode = "SchoolDashEligibilityCode";
    public static final String kSchoolDashEligibilityDesc = "SchoolDasEligibilityDesc";

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
	
	public static final String kAssocSchoolCode = "AssociatedSchoolCode";
	public static final String kAssocSchoolName = "AssociatedSchoolName";

	private PreparedStatement schoolCodesStatement;
	private PreparedStatement mainSchoolMetaDataStatement;
	private PreparedStatement schoolMetaDataForSchoolCodesStatement;
	private PreparedStatement schoolAddressesStatement;
	private PreparedStatement schoolPersonTypesStatement;
	private PreparedStatement schoolNameTypesStatement;
	private PreparedStatement schoolAddressUsesStatement;
	private PreparedStatement schoolContactTypesStatement;
	private PreparedStatement schoolPersonTypesForSchoolPeopleStatement;
	private PreparedStatement associatedSchoolsStatement;
	
	private Connection connection;

	private static final String kSelectSchoolCodes = "select dish_id " + kId + ", "
			+ "dish_code " + kCode
			+ " from dis_schools "
			+ "order by dish_code asc";

	private static final String kSelectMainSchoolMetadata = "select dish.dish_id " + kSchoolId	+ ", "
			+ "dish.dish_code "	+ kSchoolCode	+ ", "
			+ "dish.dish_name "	+ kSchoolName	+ ", "
			+ "dvc.dvc_description " + kSchoolCountry + ", "
			+ "dist.dsta_name "	+ kSchoolState	+ ", "
			+ "dist.dsta_code "	+ kSchoolStateAbbrev	+ ", "
			+ "dish_dash_class_code " + kSchoolDashClassificationCode + ", "
            + "dvdsc.dvdsc_code " + kSchoolDashClassificationSource + ", "
            + "dvdc.dvdc_code " + kSchoolDashEligibilityCode + ", "
            + "dvdc.dvdc_description " + kSchoolDashEligibilityDesc + " "
			+ "from dis_schools dish, "	
            + "dis_valid_countries dvc, "
			+ "dis_states dist, "
			+ "dis_valid_dashboard_codes dvdc, "
	        + "dis_valid_dash_source_codes dvdsc "
			+ "where dish.dvc_code = dvc.dvc_code(+) and "
			+ "dish.dsta_code = dist.dsta_code(+) and " 
			+ "dish.dvdc_code = dvdc.dvdc_code(+) and "
            + "dish.dvdsc_code = dvdsc.dvdsc_code(+) and "
			+ "dish.dish_code = ?";
	
	private static final String kSelectMainSchoolMetadataForSchoolCodes = "select dish.dish_id " + kSchoolId	+ ", "
			+ "dish.dish_code "	+ kSchoolCode	+ ", "
			+ "dish.dish_name "	+ kSchoolName	+ ", "
			+ "dvc.dvc_description " + kSchoolCountry + ", "
			+ "dist.dsta_name "	+ kSchoolState	+ ", "
			+ "dist.dsta_code "	+ kSchoolStateAbbrev	+ ", "
			+ "dish_dash_class_code " + kSchoolDashClassificationCode + ", "
			+ "dvdsc.dvdsc_code " + kSchoolDashClassificationSource + ", "
	        + "dvdc.dvdc_code " + kSchoolDashEligibilityCode + ", "
	        + "dvdc.dvdc_description " + kSchoolDashEligibilityDesc + " "
			+ "from dis_schools dish, "	
			+ "dis_valid_countries dvc, "
			+ "dis_states dist, "
            + "dis_valid_dashboard_codes dvdc, "
            + "dis_valid_dash_source_codes dvdsc "
			+ "where dish.dvc_code = dvc.dvc_code(+) and "
			+ "dish.dsta_code = dist.dsta_code(+) and " 
            + "dish.dvdc_code = dvdc.dvdc_code(+) and "
            + "dish.dvdsc_code = dvdsc.dvdsc_code(+) and "
			+ "dish.dish_code IN";

	private static final String kSelectAddresses = "select daad_id " + kAddressId + ", " 
														+ "daad_name " + kAddressName + ", "
														+ "daad_line_1 " + kAddressLine1 + ", " 
														+ "daad_line_2 " + kAddressLine2 + ", " 
														+ "daad_line_3 " + kAddressLine3 + ", "
														+ "daad_city " + kAddressCity + ", " 
														+ "daad_zip4 " + kAddressFourDigitZip + ", " 
														+ "dzip_zip5 " + kAddressZip + ", "
														+ "daad_postal_code " + kAddressPostalCode + ", "
														+ "daad_province " + kAddressProvince + ", " 
														+ "dvc_code "+ kAddressCountryCode + ", " 
														+ "to_char(daad_effective_date ,'dd-mon-yyyy') " + kAddressEffectiveDate + ", "
														+ "daad_active_flag " + kAddressEffectiveFlag + ", " 
														+ "dsp_id " + kAddressSchoolPeopleId + " "
														+ "from dis_addresses "
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
			+ "where dsp.dsp_id = dea.dsp_id(+) and "
			+ "dsp.dvtc_id = dvtc.dvtc_id(+) and ";
	
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
			+ "dcon_name " + kContactName + ", " 
			+ "dcon_notes " + kContactNotes + ", "
			+ "to_char(dcon_effective_date,'dd-mon-yyyy') " + kContactEffectiveDate + ", "
			+ "to_char(dcon_date_created,'dd-mon-yyyy') " + kContactDateCreated + ", "
			+ "to_char(dcon_date_modified,'dd-mon-yyyy') " + kContactDateModified + " "
			+ "from  dis_school_contacts " 
			+ "where dau_id = ?";
	
	private static final String kSelectAssociatedSchools = "select dish_code " + kAssocSchoolCode + ", " 
			+ "dish_name " + kAssocSchoolName + " "
			+ "from  dis_schools dish1, "
			+ "(select DSA_ASSOC_DISH_ID school_id from dis_schools_assoc dsa, dis_schools dish2 "
            + " where dsa.dsa_assoc_dish_id = dish2.dish_id "
            + " and dsa.dsa_parent_dish_id = ?) assoc "
			+ "where dish1.dish_id = assoc.school_id";

	public SchoolMetaDataQuery(Connection connection) throws SQLException {
		this.schoolCodesStatement = connection.prepareStatement(kSelectSchoolCodes);
		this.mainSchoolMetaDataStatement = connection.prepareStatement(kSelectMainSchoolMetadata);
		this.schoolAddressesStatement = connection.prepareStatement(kSelectAddresses);
		this.schoolPersonTypesStatement = connection.prepareStatement(kSelectSchoolPersonTypes + "dish_id = ?");
		this.schoolNameTypesStatement = connection.prepareStatement(kSelectNameTypes);
		this.schoolAddressUsesStatement = connection.prepareStatement(kSelectAddressUses);		
		this.schoolContactTypesStatement = connection.prepareStatement(kSelectContactTypes);
		this.schoolPersonTypesForSchoolPeopleStatement = connection.prepareStatement(kSelectSchoolPersonTypes + "dsp.dsp_id = ?");
		this.associatedSchoolsStatement = connection.prepareStatement(kSelectAssociatedSchools);
		this.connection = connection;
	}

	public List<String> getAllSchoolCodes() throws SQLException {
		List<String> result = new ArrayList<String>();
		ResultSet cursor = null;
		try {
			cursor = schoolCodesStatement.executeQuery();
			while (cursor.next()) {
				String schoolCode = cursor.getString(kCode);
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

	private School makeSchoolMetaDataFrom(ResultSet cursor) throws SQLException {
		School result = new School();
		String schoolId = cursor.getString(kSchoolId);
		String schoolCode = cursor.getString(kSchoolCode);
		String schoolName = cursor.getString(kSchoolName);
		String schoolCountry = cursor.getString(kSchoolCountry);
		String schoolState = cursor.getString(kSchoolState);
		String schoolStateAbbrev = cursor.getString(kSchoolStateAbbrev);
		String schoolDashClassCode = cursor
				.getString(kSchoolDashClassificationCode);
		String schoolDashClassSource = cursor
				.getString(kSchoolDashClassificationSource);
		String schoolDashEligibilityCode = cursor
				.getString(kSchoolDashEligibilityCode);
		String schoolDashEligibilityDesc = cursor
				.getString(kSchoolDashEligibilityDesc);

		result.setCode(schoolCode);
		result.setName(schoolName);
		result.setCountry(schoolCountry);
		result.setState(schoolState);
		result.setStateAbbrev(schoolStateAbbrev);
		result.setDashboardClassificationCode(schoolDashClassCode);
		result.setDashboardClassificationSource(schoolDashClassSource);
		result.setDashboardEligibilityCode(schoolDashEligibilityCode);
		result.setDashboardEligibilityDescription(schoolDashEligibilityDesc);
		if (null != schoolId && !schoolId.isEmpty()) {
			result.setAssociatedSchools(getAssociatedSchoolsFor(schoolId));
		}
		if (null != schoolId && !schoolId.isEmpty()) {
			result.setAddresses(getAddressesFor(schoolId));
		}
		if (null != schoolId && !schoolId.isEmpty()) {
			result.setSchoolPersons(getSchoolPersonTypesFor(schoolId));
		}
		return result;
	}

	private AssociatedSchools getAssociatedSchoolsFor(String schoolId)
			throws SQLException {
		AssociatedSchools result = new AssociatedSchools();
		ResultSet cursor = null;
		try {
			associatedSchoolsStatement.setString(1, schoolId);
			cursor = associatedSchoolsStatement.executeQuery();
			while (cursor.next()) {
				AssociatedSchoolType associatedSchoolType = new AssociatedSchoolType();
				associatedSchoolType.setSchoolCode(cursor
						.getString(kAssocSchoolCode));
				associatedSchoolType.setSchoolName(cursor
						.getString(kAssocSchoolName));
				result.getAssociatedSchool().add(associatedSchoolType);
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	private Addresses getAddressesFor(String schoolId) throws SQLException {
		Addresses result = new Addresses();
		ResultSet cursor = null;
		try {
			schoolAddressesStatement.setString(1, schoolId);
			cursor = schoolAddressesStatement.executeQuery();
			while (cursor.next()) {
				AddressType addressType = new AddressType();
				addressType.setName(cursor.getString(kAddressName));
				addressType.setLine1(cursor.getString(kAddressLine1));
				addressType.setLine2(cursor.getString(kAddressLine2));
				addressType.setLine3(cursor.getString(kAddressLine3));
				addressType.setCity(cursor.getString(kAddressCity));
				String fiveDigitZip = cursor.getString(kAddressZip);
				String fourDigitZip = cursor.getString(kAddressFourDigitZip);
				if (null != fiveDigitZip && !fiveDigitZip.isEmpty()) {
					String zipCode;
					if (null != fourDigitZip && !fourDigitZip.isEmpty()) {
						zipCode = fiveDigitZip + "-" + fourDigitZip;
					} else {
						zipCode = fiveDigitZip;
					}
					addressType.setZip(zipCode);
				}
				addressType.setPostalCode(cursor.getString(kAddressPostalCode));
				addressType
						.setStateProvince(cursor.getString(kAddressProvince));
				addressType.setCountry(cursor.getString(kAddressCountryCode));
				addressType.setEffectiveDate(cursor
						.getString(kAddressEffectiveDate));
				addressType.setActiveFlag(cursor
						.getString(kAddressEffectiveFlag));

				addressType.setAddressPerson(getAddressPerson(cursor
						.getString(kAddressSchoolPeopleId)));
				String addressId = cursor.getString(kAddressId);
				if (null != addressId && !addressId.isEmpty()) {
					addressType.setAddressUses(getAddressUses(addressId));
				}
				result.getAddress().add(addressType);
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	private PersonType getAddressPerson(String schoolPeopleId)
			throws SQLException {
		PersonType result = new PersonType();
		ResultSet cursor = null;
		try {
			schoolPersonTypesForSchoolPeopleStatement.setString(1,
					schoolPeopleId);
			cursor = schoolPersonTypesForSchoolPeopleStatement.executeQuery();
			while (cursor.next()) {
				result.setTitle(cursor.getString(kSchoolPersonTitle));
				result.setTitleCategory(cursor
						.getString(kSchoolPersonTitleCategory));
				result.setDepartment(cursor.getString(kSchoolPersonDepartment));
				result.setStatus(cursor.getString(kSchoolPersonStatus));
				result.setEmailAddress(cursor.getString(kSchoolPersonEmail));
				result.setStartDate(cursor.getString(kSchoolPersonStartDate));
				result.setEndDate(cursor.getString(kSchoolPersonEndDate));
				String schoolPeopleNameId = cursor
						.getString(kSchoolPeopleNameId);
				if (null != schoolPeopleNameId && !schoolPeopleNameId.isEmpty()) {
					result.setName(getNameTypeFor(schoolPeopleNameId));
				}
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	private AddressUses getAddressUses(String addressId) throws SQLException {
		AddressUses result = new AddressUses();
		ResultSet cursor = null;
		try {
			schoolAddressUsesStatement.setString(1, addressId);
			cursor = schoolAddressUsesStatement.executeQuery();
			while (cursor.next()) {
				String addressUseId = cursor.getString(kAddressUseId);

				AddressUseType addressUseType = new AddressUseType();
				addressUseType.setType(cursor.getString(kAddressUseType));
				addressUseType.setEBSAccount(cursor.getString(kEbsAccount));
				addressUseType.setDeliveryDate(cursor.getString(kDeliveryDate));
				addressUseType.setDateCreated(cursor.getString(kDateCreated));
				addressUseType.setDateModified(cursor.getString(kDateModified));
				if (null != addressUseId && !addressUseId.isEmpty()) {
					addressUseType
							.setSchoolContacts(getSchoolContactFor(addressUseId));
				}
				result.getAddressUse().add(addressUseType);
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	private SchoolContacts getSchoolContactFor(String addressUseId)
			throws SQLException {
		SchoolContacts result = new SchoolContacts();
		ResultSet cursor = null;
		try {
			schoolContactTypesStatement.setString(1, addressUseId);
			cursor = schoolContactTypesStatement.executeQuery();
			while (cursor.next()) {
				ContactType contactType = new ContactType();
				contactType.setType(cursor.getString(kContactType));
				contactType.setName(cursor.getString(kContactName));
				contactType.setEffectiveDate(cursor
						.getString(kContactEffectiveDate));
				contactType.setDateCreated(cursor
						.getString(kContactDateCreated));
				contactType.setDateModified(cursor
						.getString(kContactDateModified));
				result.getSchoolContact().add(contactType);
			}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	private SchoolPersons getSchoolPersonTypesFor(String schoolId)
			throws SQLException {
		SchoolPersons result = new SchoolPersons();
		ResultSet cursor = null;
		try {
			schoolPersonTypesStatement.setString(1, schoolId);
			cursor = schoolPersonTypesStatement.executeQuery();
			while (cursor.next()) {
				PersonType personType = new PersonType();

				personType.setTitle(cursor.getString(kSchoolPersonTitle));
				personType.setTitleCategory(cursor
						.getString(kSchoolPersonTitleCategory));
				personType.setDepartment(cursor
						.getString(kSchoolPersonDepartment));
				personType.setStatus(cursor.getString(kSchoolPersonStatus));
				personType
						.setEmailAddress(cursor.getString(kSchoolPersonEmail));
				personType.setStartDate(cursor
						.getString(kSchoolPersonStartDate));
				personType.setEndDate(cursor.getString(kSchoolPersonEndDate));
				String schoolPeopleNameId = cursor
						.getString(kSchoolPeopleNameId);
				if (null != schoolPeopleNameId && !schoolPeopleNameId.isEmpty()) {
					personType.setName(getNameTypeFor(schoolPeopleNameId));
				}
				result.getSchoolPerson().add(personType);
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
			schoolNameTypesStatement.setString(1, schoolPeopleNameId);
			cursor = schoolNameTypesStatement.executeQuery();
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

	public List<School> getAllSchoolMetadata(List<String> schoolCodes,
			int batchSize) throws SQLException {
		List<School> result = new ArrayList<School>();
		ResultSet cursor = null;
		try {
		Enumeration<List<String>> schoolCodesEnum = PartitionListBasedOnBatchSize
				.createPartitionedEnum(schoolCodes, batchSize);
		while (schoolCodesEnum.hasMoreElements()) {
			List<String> nextElement = schoolCodesEnum.nextElement();
			schoolMetaDataForSchoolCodesStatement = connection
					.prepareStatement(createQuery(nextElement.size()));
			int index = 1;
			for (String number : nextElement) {
				schoolMetaDataForSchoolCodesStatement.setString(index, number);
				index++;
			}
			cursor = schoolMetaDataForSchoolCodesStatement.executeQuery();
			while (cursor.next()) {
				School school = makeSchoolMetaDataFrom(cursor);
				result.add(school);
			}
		}
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return result;
	}

	private String createQuery(int size) {
		StringBuilder queryBuilder = new StringBuilder(
				kSelectMainSchoolMetadataForSchoolCodes + "(");
		for (int i = 0; i < size; i++) {
			queryBuilder.append(" ?");
			if (i != size - 1)
				queryBuilder.append(",");
		}
		queryBuilder.append(")");
		return queryBuilder.toString();
	}

	public void close() throws SQLException {
		closeStatement(schoolCodesStatement);
		closeStatement(mainSchoolMetaDataStatement);
		closeStatement(schoolMetaDataForSchoolCodesStatement);
		closeStatement(schoolAddressesStatement);
		closeStatement(schoolPersonTypesStatement);
		closeStatement(schoolNameTypesStatement);
		closeStatement(schoolAddressUsesStatement);
		closeStatement(schoolContactTypesStatement);
		closeStatement(schoolPersonTypesForSchoolPeopleStatement);
		closeStatement(associatedSchoolsStatement);
	}

	private void closeStatement(PreparedStatement statment) throws SQLException {
		if (null != statment) {
			statment.close();
		}
	}
}*/

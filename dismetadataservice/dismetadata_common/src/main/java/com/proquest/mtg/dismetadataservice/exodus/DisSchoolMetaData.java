package com.proquest.mtg.dismetadataservice.exodus;

import java.util.List;

import com.proquest.mtg.dismetadataservice.metadata.Address;
import com.proquest.mtg.dismetadataservice.metadata.AddressUseType;
import com.proquest.mtg.dismetadataservice.metadata.ContactType;
import com.proquest.mtg.dismetadataservice.metadata.NameType;
import com.proquest.mtg.dismetadataservice.metadata.SchoolPersonType;

public class DisSchoolMetaData {
	private String schoolId;
	private String schoolCode;
	private String schoolName;
	private String schoolCountry;
	private String schoolState;
	private String schoolCarnegieCode;
	private String schoolDashboardEligibility;
	private List<Address> addresses;
	private List<SchoolPersonType> schoolPersonTypes;
	private List<NameType> nameTypes;
	private List<AddressUseType> addressUseTypes;
	private List<ContactType> contactTypes;

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolCountry() {
		return schoolCountry;
	}

	public void setSchoolCountry(String schoolCountry) {
		this.schoolCountry = schoolCountry;
	}

	public String getSchoolState() {
		return schoolState;
	}

	public void setSchoolState(String schoolState) {
		this.schoolState = schoolState;
	}

	public String getSchoolCarnegieCode() {
		return schoolCarnegieCode;
	}

	public void setSchoolCarnegieCode(String schoolCarnegieCode) {
		this.schoolCarnegieCode = schoolCarnegieCode;
	}

	public String getSchoolDashboardEligibility() {
		return schoolDashboardEligibility;
	}

	public void setSchoolDashboardEligibility(String schoolDashboardEligibility) {
		this.schoolDashboardEligibility = schoolDashboardEligibility;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public List<SchoolPersonType> getSchoolPersonTypes() {
		return schoolPersonTypes;
	}

	public void setSchoolPersonTypes(List<SchoolPersonType> schoolPersonTypes) {
		this.schoolPersonTypes = schoolPersonTypes;
	}

	public List<NameType> getNameTypes() {
		return nameTypes;
	}

	public void setNameTypes(List<NameType> nameTypes) {
		this.nameTypes = nameTypes;
	}

	public List<AddressUseType> getAddressUseTypes() {
		return addressUseTypes;
	}

	public void setAddressUseTypes(List<AddressUseType> addressUseTypes) {
		this.addressUseTypes = addressUseTypes;
	}

	public List<ContactType> getContactTypes() {
		return contactTypes;
	}

	public void setContactTypes(List<ContactType> contactTypes) {
		this.contactTypes = contactTypes;
	}

}

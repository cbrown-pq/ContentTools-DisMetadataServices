package com.proquest.mtg.dismetadataservice.exodus;

import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.AddressType;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.ContactType;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.AddressType.AddressUses;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.AddressUseType;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.AddressUseType.SchoolContacts;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.NameType;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.PersonType;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.Schools.School.Addresses;

public class SchoolMetadaHelper {
	public static AddressUseType MakeAddressUseTypeFrom(String type, 
			String ebsAccount, String deliveryDate, String dateCreated, 
			String dateModified, SchoolContacts schoolContact) {
		AddressUseType addressUseType = new AddressUseType();
		addressUseType.setType(type);
		addressUseType.setEBSAccount(ebsAccount);
		addressUseType.setDeliveryDate(deliveryDate);
		addressUseType.setDateCreated(dateCreated);
		addressUseType.setDateModified(dateModified);
		addressUseType.setSchoolContacts(schoolContact);
		return addressUseType;
	}
	
	
	public static AddressType MakeAddressTypeFrom(String name, String line1, 
			String line2, String line3, String city, String zip, String postalCode, 
			String stateProvince, String country, String effectiveDate, String activeFlag,
			String dateCreated, String dateModified, PersonType addressPersons,
			AddressUses addressUses) {
		AddressType address = new AddressType();
		address.setName(name);
		address.setLine1(line1);
		address.setLine2(line2);
		address.setLine3(line3);
		address.setCity(city);
		address.setZip(zip);
		address.setPostalCode(postalCode);
		address.setStateProvince(stateProvince);
		address.setCountry(country);
		address.setEffectiveDate(effectiveDate);
		address.setActiveFlag(activeFlag);
		address.setDateCreated(dateCreated);
		address.setDateModified(dateModified);
		address.setAddressPerson(addressPersons);
		address.setAddressUses(addressUses);
		return address;
	}

	public static ContactType MakeSchoolContactTypeFrom(String type, String name, 
			String effectiveDate, String dateCreated, String dateModified) {
		ContactType contact = new ContactType();
		contact.setType(type);
		contact.setName(name);
		contact.setEffectiveDate(effectiveDate);
		contact.setDateCreated(dateCreated);
		contact.setDateModified(dateModified);
		return contact;
	}


	public static NameType MakeNameTypeFrom(String firstName, String middleName,
			String lastName, String status, String statusDate, String dateCreated,
			String dateModified) {
		NameType name = new NameType();
		name.setFirstName(firstName);
		name.setMiddleName(middleName);
		name.setLastName(lastName);
		name.setStatus(status);
		name.setStatusDate(statusDate);
		name.setDateCreated(dateCreated);
		name.setDateModified(dateModified);
		
		return name;
	}


	public static PersonType MakeSchoolPersonTypeFrom(String title, 
			String titleCategory, String department, String status, 
			String emailAddress, String startDate, String endDate, 
			NameType name) {
		PersonType person = new PersonType();
		person.setTitle(title);
		person.setTitleCategory(titleCategory);
		person.setDepartment(department);
		person.setStatus(status);
		person.setEmailAddress(emailAddress);
		person.setStartDate(startDate);
		person.setEndDate(endDate);
		person.setName(name);
		return person;
	}
}

package com.proquest.mtg.dismetadataservice.exodus;

import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.AddressType;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.AddressType.AddressUses;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.AddressUseType;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.AddressUseType.SchoolContacts;
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

}

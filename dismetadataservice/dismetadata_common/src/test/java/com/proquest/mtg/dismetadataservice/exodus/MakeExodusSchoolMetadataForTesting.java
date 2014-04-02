package com.proquest.mtg.dismetadataservice.exodus;

import java.util.ArrayList;
import java.util.List;

import com.proquest.mtg.dismetadataservice.metadata.school.Address;
import com.proquest.mtg.dismetadataservice.metadata.school.AddressUse;
import com.proquest.mtg.dismetadataservice.metadata.school.NameType;
import com.proquest.mtg.dismetadataservice.metadata.school.School;
import com.proquest.mtg.dismetadataservice.metadata.school.PersonType;
import com.proquest.mtg.dismetadataservice.metadata.school.SchoolContact;

public class MakeExodusSchoolMetadataForTesting {
	static final String fakeSchool = "fakeSchool";
	
	public static String school1 = "0126"; // Multiple Addresses
	
	public static String school2 = "0502"; // Multiple Addresses

	public static School makeExpectedMetaData1() {
		School x = new School();
		x.setId("123");
		x.setName("Miami University");
		
		List<Address> addresses = new ArrayList<Address>();
		Address address1 = new Address();
		address1.setAddressId("171");
		address1.setAddressName("MIAMI UNIVERSITY");
		address1.setLine1("GRADUATE SCHOOL");
		address1.setLine2("102 ROUDEBUSH HALL");
		address1.setLine3(null);
		address1.setCity("OXFORD");
		address1.setFourdigitzip(null);
		address1.setZip("45056");
		address1.setPostalCode(null);
		address1.setStateProvince(null);
		address1.setCountry("US");
		address1.setEffectiveDate(null);
		address1.setActiveFlag(null);
		
		List<AddressUse> addressUses = new ArrayList<AddressUse>();
		AddressUse addressUse1 = new AddressUse();
		addressUse1.setType("Bill to");
		addressUse1.seteBSAccount("32139");
		addressUse1.setDeliveryDate("23-mar-2005");
		addressUse1.setDateCreated("26-dec-1999");
		addressUse1.setDateModified("10-jan-2000");
		addressUse1.setSchoolContacts(null);
		addressUses.add(addressUse1);
		
		AddressUse addressUse2 = new AddressUse();
		addressUse2.setType("Ship to");
		addressUse2.seteBSAccount("124137");
		addressUse2.setDeliveryDate("23-mar-2005");
		addressUse2.setDateCreated("26-dec-1999");
		addressUse2.setDateModified("10-jan-2000");
		addressUse2.setSchoolContacts(null);
		addressUses.add(addressUse2);
		
		address1.setAddressUses(addressUses );
		addresses.add(address1);
		
		Address address2 = new Address();
		address2.setAddressId("100755");
		address2.setAddressName("Cindy Stevens");
		address2.setLine1("Miami University");
		address2.setLine2("Graduate School");
		address2.setLine3("102 Roudebush Hall");
		address2.setCity("Oxford");
		address2.setFourdigitzip(null);
		address2.setZip("45056");
		address2.setPostalCode(null);
		address2.setStateProvince(null);
		address2.setCountry("US");
		address2.setEffectiveDate("23-dec-1999");
		address2.setActiveFlag(null);
		
		
		AddressUse addressUse3 = new AddressUse();
		addressUse3.setType("Contacts");
		addressUse3.seteBSAccount(null);
		addressUse3.setDeliveryDate("26-dec-1999");
		addressUse3.setDateCreated("27-apr-1993");
		addressUse3.setDateModified(null);

		
		List<SchoolContact> schoolContacts = new ArrayList<SchoolContact>();
		SchoolContact schoolContact1 = new SchoolContact();
		schoolContact1.setType("E");
		schoolContact1.setName("Lou Ann Haines");
		schoolContact1.setEffectiveDate("23-dec-1999");
		schoolContact1.setDateCreated("27-apr-1993");
		schoolContact1.setDateModified("18-may-2011");
		schoolContacts.add(schoolContact1);
		
		
		SchoolContact schoolContact2 = new SchoolContact();
		schoolContact2.setType("I");
		schoolContact2.setName("Lou Ann Haines");
		schoolContact2.setEffectiveDate("23-dec-1999");
		schoolContact2.setDateCreated("27-apr-1993");
		schoolContact2.setDateModified("08-dec-2010");
		schoolContacts.add(schoolContact2);
		
		SchoolContact schoolContact3 = new SchoolContact();
		schoolContact3.setType("S");
		schoolContact3.setName("Lou Ann Haines");
		schoolContact3.setEffectiveDate("23-dec-1999");
		schoolContact3.setDateCreated("27-apr-1993");
		schoolContact3.setDateModified("08-dec-2010");
		schoolContacts.add(schoolContact3);
		
		SchoolContact schoolContact4 = new SchoolContact();
		schoolContact4.setType("R");
		schoolContact4.setName("Lou Ann Haines");
		schoolContact4.setEffectiveDate("23-dec-1999");
		schoolContact4.setDateCreated("27-apr-1993");
		schoolContact4.setDateModified("08-dec-2010");
		schoolContacts.add(schoolContact4);
		
		SchoolContact schoolContact5 = new SchoolContact();
		schoolContact5.setType("E");
		schoolContact5.setName("LOU HAINES");
		schoolContact5.setEffectiveDate("23-dec-1999");
		schoolContact5.setDateCreated("25-jul-1994");
		schoolContact5.setDateModified("11-sep-2007");
		schoolContacts.add(schoolContact5);
		
		SchoolContact schoolContact6 = new SchoolContact();
		schoolContact6.setType("I");
		schoolContact6.setName("LOU HAINES");
		schoolContact6.setEffectiveDate("23-dec-1999");
		schoolContact6.setDateCreated("25-jul-1994");
		schoolContact6.setDateModified("06-nov-2008");
		schoolContacts.add(schoolContact6);
		
		SchoolContact schoolContact7 = new SchoolContact();
		schoolContact7.setType("S");
		schoolContact7.setName("LOU HAINES");
		schoolContact7.setEffectiveDate("23-dec-1999");
		schoolContact7.setDateCreated("25-jul-1994");
		schoolContact7.setDateModified("11-sep-2007");
		schoolContacts.add(schoolContact7);
		
		SchoolContact schoolContact8 = new SchoolContact();
		schoolContact8.setType("R");
		schoolContact8.setName("LOU HAINES");
		schoolContact8.setEffectiveDate("23-dec-1999");
		schoolContact8.setDateCreated("25-jul-1994");
		schoolContact8.setDateModified("11-sep-2007");
		schoolContacts.add(schoolContact8);
		
		SchoolContact schoolContact9 = new SchoolContact();
		schoolContact9.setType("E");
		schoolContact9.setName("Cindy Stevens");
		schoolContact9.setEffectiveDate("23-dec-1999");
		schoolContact9.setDateCreated("26-oct-1991");
		schoolContact9.setDateModified("21-jun-2005");
		schoolContacts.add(schoolContact9);
		
		SchoolContact schoolContact10 = new SchoolContact();
		schoolContact10.setType("I");
		schoolContact10.setName("Cindy Stevens");
		schoolContact10.setEffectiveDate("23-dec-1999");
		schoolContact10.setDateCreated("26-oct-1991");
		schoolContact10.setDateModified("21-jun-2005");
		schoolContacts.add(schoolContact10);

		SchoolContact schoolContact11 = new SchoolContact();
		schoolContact11.setType("R");
		schoolContact11.setName("Cindy Stevens");
		schoolContact11.setEffectiveDate("23-dec-1999");
		schoolContact11.setDateCreated("26-oct-1991");
		schoolContact11.setDateModified("21-jun-2005");
		schoolContacts.add(schoolContact11);
		
		
		addressUse3.setSchoolContacts(schoolContacts);
		List<AddressUse> addressUses2 = new ArrayList<AddressUse>();
		addressUses2.add(addressUse3);
		address2.setAddressUses(addressUses2);
		addresses.add(address2);
		
		x.setAddresses(addresses);
		
		return x;
	}

	public static School makeExpectedMetaData2() {

		School x = new School();
		x.setId("1970");
		x.setName("Loyola Marymount University");
		
		List<PersonType> schoolPersonTypes = new ArrayList<PersonType>();
		PersonType schoolPersonTypes1 = new PersonType();
		schoolPersonTypes1.setTitle("Spec Ass to the Chief Acad Off");
		schoolPersonTypes1.setCategory("DEAN");
		schoolPersonTypes1.setDepartment(null);
		schoolPersonTypes1.setStatus("Y");
		schoolPersonTypes1.setEmail("saugust@lmu.edu");
		schoolPersonTypes1.setStartDate("03-aug-2011");
		schoolPersonTypes1.setEndDate(null);
		schoolPersonTypes1.setNameId("466");
		
		NameType nameType1 = new NameType();
		nameType1.setFirstName("Stephanie");
		nameType1.setLastName("August");
		nameType1.setMiddleName(null);
		nameType1.setStatus("Y");
		nameType1.setStatusDate("03-aug-2011");
		nameType1.setDateCreated("03-aug-2011");
		nameType1.setDateModified(null);
	
		schoolPersonTypes1.setNameType(nameType1);
		
		schoolPersonTypes.add(schoolPersonTypes1);
		
		PersonType schoolPersonTypes2 = new PersonType();
		schoolPersonTypes2.setTitle("Associate Dean");
		schoolPersonTypes2.setCategory("DEAN");
		schoolPersonTypes2.setDepartment(null);
		schoolPersonTypes2.setStatus("Y");
		schoolPersonTypes2.setEmail("gkuleck@lmu.edu");
		schoolPersonTypes2.setStartDate("03-aug-2011");
		schoolPersonTypes2.setEndDate(null);
		schoolPersonTypes2.setNameId("538");
		
		NameType nameType2 = new NameType();
		
		nameType2.setFirstName("Gary");
		nameType2.setLastName("Kuleck");
		nameType2.setMiddleName(null);
		nameType2.setStatus("Y");
		nameType2.setStatusDate("03-aug-2011");
		nameType2.setDateCreated("03-aug-2011");
		nameType2.setDateModified(null);
		schoolPersonTypes2.setNameType(nameType2);
		
		schoolPersonTypes.add(schoolPersonTypes2);	
		
		x.setPersonTypes(schoolPersonTypes);
		
		return x;
	}
	
	public static School makeExpectedMetaData3() {

		School x = new School();
		x.setId("1970");
		x.setName("Loyola Marymount University");
		
		List<PersonType> schoolPersonTypes = new ArrayList<PersonType>();
		PersonType schoolPersonTypes1 = new PersonType();
		schoolPersonTypes1.setTitle("Spec Ass to the Chief Acad Off");
		schoolPersonTypes1.setCategory("DEAN");
		schoolPersonTypes1.setDepartment(null);
		schoolPersonTypes1.setStatus("Y");
		schoolPersonTypes1.setEmail("saugust@lmu.edu");
		schoolPersonTypes1.setStartDate("03-aug-2011");
		schoolPersonTypes1.setEndDate(null);
		schoolPersonTypes1.setNameId("466");
		
		NameType nameType1 = new NameType();
		nameType1.setFirstName("Gary");
		nameType1.setLastName("Kuleck");
		nameType1.setMiddleName(null);
		nameType1.setStatus("Y");
		nameType1.setStatusDate("03-aug-2011");
		nameType1.setDateCreated("03-aug-2011");
		nameType1.setDateModified(null);
		schoolPersonTypes1.setNameType(nameType1);
		
		schoolPersonTypes.add(schoolPersonTypes1);
		
		PersonType schoolPersonTypes2 = new PersonType();
		schoolPersonTypes2.setTitle("Associate Dean");
		schoolPersonTypes2.setCategory("DEAN");
		schoolPersonTypes2.setDepartment(null);
		schoolPersonTypes2.setStatus("Y");
		schoolPersonTypes2.setEmail("gkuleck@lmu.edu");
		schoolPersonTypes2.setStartDate("03-aug-2011");
		schoolPersonTypes2.setEndDate(null);
		schoolPersonTypes2.setNameId("538");
		
		NameType nameType2 = new NameType();
		nameType2.setFirstName("Stephanie");
		nameType2.setLastName("August	");
		nameType2.setMiddleName(null);
		nameType2.setStatus("Y");
		nameType2.setStatusDate("03-aug-2011");
		nameType2.setDateCreated("03-aug-2011");
		nameType2.setDateModified(null);
		schoolPersonTypes2.setNameType(nameType2);
		schoolPersonTypes.add(schoolPersonTypes2);
		
		x.setPersonTypes(schoolPersonTypes);
		
		return x;
	}
	
}

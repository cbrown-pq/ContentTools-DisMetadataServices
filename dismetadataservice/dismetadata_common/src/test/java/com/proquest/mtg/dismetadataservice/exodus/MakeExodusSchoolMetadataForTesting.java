package com.proquest.mtg.dismetadataservice.exodus;

import java.util.ArrayList;
import java.util.List;

import com.proquest.mtg.dismetadataservice.metadata.Address;
import com.proquest.mtg.dismetadataservice.metadata.NameType;
import com.proquest.mtg.dismetadataservice.metadata.SchoolPersonType;

public class MakeExodusSchoolMetadataForTesting {
	static final String fakeSchool = "fakeSchool";
	
	public static String school1 = "0126"; // Multiple Addresses
	
	public static String school2 = "0502"; // Multiple Addresses

	public static DisSchoolMetaData makeExpectedMetaData1() {
		DisSchoolMetaData x = new DisSchoolMetaData();
		x.setSchoolId("123");
		x.setSchoolName("Miami University");
		
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
		addresses.add(address2);
		
		
		x.setAddresses(addresses);
		return x;
	}

	public static DisSchoolMetaData makeExpectedMetaData2() {

		DisSchoolMetaData x = new DisSchoolMetaData();
		x.setSchoolId("1970");
		x.setSchoolName("Loyola Marymount University");
		
		List<SchoolPersonType> schoolPersonTypes = new ArrayList<SchoolPersonType>();
		SchoolPersonType schoolPersonTypes1 = new SchoolPersonType();
		schoolPersonTypes1.setTitle("Spec Ass to the Chief Acad Off");
		schoolPersonTypes1.setCategory("DEAN");
		schoolPersonTypes1.setDepartment(null);
		schoolPersonTypes1.setStatus("Y");
		schoolPersonTypes1.setEmail("saugust@lmu.edu");
		schoolPersonTypes1.setStartDate("03-aug-2011");
		schoolPersonTypes1.setEndDate(null);
		schoolPersonTypes1.setNameId("466");
		schoolPersonTypes.add(schoolPersonTypes1);
		
		SchoolPersonType schoolPersonTypes2 = new SchoolPersonType();
		schoolPersonTypes2.setTitle("Associate Dean");
		schoolPersonTypes2.setCategory("DEAN");
		schoolPersonTypes2.setDepartment(null);
		schoolPersonTypes2.setStatus("Y");
		schoolPersonTypes2.setEmail("gkuleck@lmu.edu");
		schoolPersonTypes2.setStartDate("03-aug-2011");
		schoolPersonTypes2.setEndDate(null);
		schoolPersonTypes2.setNameId("538");
		schoolPersonTypes.add(schoolPersonTypes2);
		
		x.setSchoolPersonTypes(schoolPersonTypes);
		
		return x;
	}
	
	public static DisSchoolMetaData makeExpectedMetaData3() {

		DisSchoolMetaData x = new DisSchoolMetaData();
		x.setSchoolId("1970");
		x.setSchoolName("Loyola Marymount University");
		
		List<SchoolPersonType> schoolPersonTypes = new ArrayList<SchoolPersonType>();
		SchoolPersonType schoolPersonTypes1 = new SchoolPersonType();
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
		
		SchoolPersonType schoolPersonTypes2 = new SchoolPersonType();
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
		
		x.setSchoolPersonTypes(schoolPersonTypes);
		
		return x;
	}
	
}

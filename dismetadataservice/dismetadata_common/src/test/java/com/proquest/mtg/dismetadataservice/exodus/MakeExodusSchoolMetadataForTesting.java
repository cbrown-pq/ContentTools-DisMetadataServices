package com.proquest.mtg.dismetadataservice.exodus;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.AddressType;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.AddressType.AddressUses;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.AddressUseType;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.AddressUseType.SchoolContacts;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.ContactType;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.NameType;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.PersonType;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.Schools.School;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.Schools.School.Addresses;
import com.proquest.mtg.dismetadataservice.schoolmetadata.xml.Schools.School.SchoolPersons;

public class MakeExodusSchoolMetadataForTesting {
	//static final String kEmptyField = ""; 
	
	static final String fakeSchool = "fakeSchool";
	public static String school1 = "0126"; 
	public static String school2 = "0127"; 
	public static String school3 = "0502"; 

	public static School makeExpectedMetaData1() {
		School x = new School();
		x.setName("Miami University");
		x.setState("Ohio");
		x.setStateAbbrev("OH");
		x.setCode("0126");
		x.setCountry("UNITED STATES");
		x.setDashboardEligibilityCode("0001");
		x.setDashboardEligibilityDescription("Not eligible for Dashboard");
		
		Addresses addressses = new Addresses();
		
		AddressUseType address1_useType1 = SchoolMetadaHelper.MakeAddressUseTypeFrom("Bill to",
				"32139", "23-mar-2005", "26-dec-1999", "10-jan-2000", null);
		AddressUseType address1_useType2 = SchoolMetadaHelper.MakeAddressUseTypeFrom("Ship to",
				"124137", "23-mar-2005", "26-dec-1999", "10-jan-2000", null);
		AddressUses address1_uses = new AddressUses();
		address1_uses.getAddressUse().addAll(Lists.newArrayList(address1_useType1, address1_useType2));
		AddressType address1 = SchoolMetadaHelper.MakeAddressTypeFrom("MIAMI UNIVERSITY",
				"GRADUATE SCHOOL", "102 ROUDEBUSH HALL", null, "OXFORD", "45056", null,
				null, "US", null, null, null, null, null, address1_uses);
		
		
		AddressUseType address2_useType1 = SchoolMetadaHelper.MakeAddressUseTypeFrom("Contacts",
				null, "26-dec-1999", "27-apr-1993", null, null);
		SchoolContacts address2_schoolContacts = new SchoolContacts();
		ContactType address2_contact1 = SchoolMetadaHelper.MakeSchoolContactTypeFrom("E", "Lou Ann Haines", 
				"23-dec-1999", "27-apr-1993", "18-may-2011");
		ContactType address2_contact2 = SchoolMetadaHelper.MakeSchoolContactTypeFrom("I", "Lou Ann Haines", 
				"23-dec-1999", "27-apr-1993", "08-dec-2010");
		ContactType address2_contact3 = SchoolMetadaHelper.MakeSchoolContactTypeFrom("S", "Lou Ann Haines", 
				"23-dec-1999", "27-apr-1993", "08-dec-2010");
		ContactType address2_contact4 = SchoolMetadaHelper.MakeSchoolContactTypeFrom("R", "Lou Ann Haines", 
				"23-dec-1999", "27-apr-1993", "08-dec-2010");
		ContactType address2_contact5 = SchoolMetadaHelper.MakeSchoolContactTypeFrom("E", "LOU HAINES", 
				"23-dec-1999", "25-jul-1994", "11-sep-2007");
		ContactType address2_contact6 = SchoolMetadaHelper.MakeSchoolContactTypeFrom("I", "LOU HAINES", 
				"23-dec-1999", "25-jul-1994", "06-nov-2008");
		ContactType address2_contact7 = SchoolMetadaHelper.MakeSchoolContactTypeFrom("S", "LOU HAINES", 
				"23-dec-1999", "25-jul-1994", "11-sep-2007");
		ContactType address2_contact8 = SchoolMetadaHelper.MakeSchoolContactTypeFrom("R", "LOU HAINES", 
				"23-dec-1999", "25-jul-1994", "11-sep-2007");
		ContactType address2_contact9 = SchoolMetadaHelper.MakeSchoolContactTypeFrom("E", "Cindy Stevens", 
				"23-dec-1999", "26-oct-1991", "21-jun-2005");
		ContactType address2_contact10 = SchoolMetadaHelper.MakeSchoolContactTypeFrom("I", "Cindy Stevens", 
				"23-dec-1999", "26-oct-1991", "21-jun-2005");
		ContactType address2_contact11 = SchoolMetadaHelper.MakeSchoolContactTypeFrom("R", "Cindy Stevens", 
				"23-dec-1999", "26-oct-1991", "21-jun-2005");
		address2_schoolContacts.getSchoolContact().addAll(Lists.newArrayList(address2_contact1, address2_contact2, 
				address2_contact3, address2_contact4, address2_contact5, address2_contact6, address2_contact7,
				address2_contact8, address2_contact9, address2_contact10, address2_contact11));
		address2_useType1.setSchoolContacts(address2_schoolContacts);
		AddressUses address2_uses = new AddressUses();
		address2_uses.getAddressUse().addAll(Lists.newArrayList(address2_useType1));
		AddressType address2 = SchoolMetadaHelper.MakeAddressTypeFrom("Cindy Stevens",
				"Miami University", "Graduate School", "102 Roudebush Hall", "Oxford", "45056", null,
				null, "US", "23-dec-1999", null, null, null, null, address2_uses);
		
		
		addressses.getAddress().addAll(Lists.newArrayList(address1, address2));		
		x.setAddresses(addressses);
		
		return x;
	}

	public static School makeExpectedMetaData3() {

		School x = new School();
		
		x.setName("Loyola Marymount University");
		x.setState("California");
		x.setStateAbbrev("CA");
		x.setCode("0502");
		x.setCountry("UNITED STATES");
		x.setDashboardEligibilityCode("0001");
		x.setDashboardEligibilityDescription("Not eligible for Dashboard");
		
		NameType schoolPerson1_name = SchoolMetadaHelper.MakeNameTypeFrom("Stephanie", null, 
				"August", "Y", "03-aug-2011", "03-aug-2011", null);
		PersonType schoolPerson1 = SchoolMetadaHelper.MakeSchoolPersonTypeFrom("Spec Ass to the Chief Acad Off", "DEAN", null, "Y",
				"saugust@lmu.edu", "03-aug-2011", null, schoolPerson1_name );
		
		NameType schoolPerson2_name = SchoolMetadaHelper.MakeNameTypeFrom("Gary", null, 
				"Kuleck", "Y", "03-aug-2011", "03-aug-2011", null);
		PersonType schoolPerson2 = SchoolMetadaHelper.MakeSchoolPersonTypeFrom("Associate Dean", "DEAN", null, "Y",
				"gkuleck@lmu.edu", "03-aug-2011", null, schoolPerson2_name );
		
		
		NameType schoolPerson3_name = SchoolMetadaHelper.MakeNameTypeFrom("Stephanie", null, 
				"August", "Y", "03-aug-2011", "03-aug-2011", null);
		PersonType schoolPerson3 = SchoolMetadaHelper.MakeSchoolPersonTypeFrom(null, "DASHBOARD ADMIN", null, "Y",
				"Stephanie.August@gmail.com", "21-apr-2014", null, schoolPerson3_name );
		
		NameType schoolPerson4_name = SchoolMetadaHelper.MakeNameTypeFrom("Stephanie", null, 
				"August", "Y", "03-aug-2011", "03-aug-2011", null);
		PersonType schoolPerson4 = SchoolMetadaHelper.MakeSchoolPersonTypeFrom(null, "DASHBOARD ADMIN", null, "Y",
				"S.August@gmail.com", "29-apr-2014", null, schoolPerson4_name );
		
		
		
		SchoolPersons schoolPersons = new SchoolPersons();
		schoolPersons.getSchoolPerson().addAll(Lists.newArrayList(schoolPerson1, schoolPerson2, schoolPerson3, schoolPerson4));
		
		x.setSchoolPersons(schoolPersons);
		
		return x;
	}
	
	public static School makeExpectedMetaData2() {
		School x = new School();
		
		x.setName("University of Michigan");
		x.setState("Michigan");
		x.setStateAbbrev("MI");
		x.setCode("0127");
		x.setCountry("UNITED STATES");
		x.setDashboardEligibilityCode("0001");
		x.setDashboardEligibilityDescription("Not eligible for Dashboard");
		
		NameType schoolPerson1_name = SchoolMetadaHelper.MakeNameTypeFrom("Antonucci", null, 
				" Toni", "Y", "01-jun-2009", "01-jun-2009", null);
		PersonType schoolPerson1 = SchoolMetadaHelper.MakeSchoolPersonTypeFrom("Associate Dean", 
				"DEAN", null, "Y", "tca@umich.edu", "01-jun-2009", null, 
				schoolPerson1_name );
		
		NameType schoolPerson2_name = SchoolMetadaHelper.MakeNameTypeFrom("Bergman", null, 
				" Maia", "Y", "01-jun-2009", "01-jun-2009", null);
		PersonType schoolPerson2 = SchoolMetadaHelper.MakeSchoolPersonTypeFrom(
				"Assistant Director Institutional Research", "OTHER", null, "Y",
				"mbergman@umich.edu", "01-jun-2009", null, schoolPerson2_name );
		
		NameType schoolPerson3_name = SchoolMetadaHelper.MakeNameTypeFrom("Janet", "A.", 
				"Weiss", "Y", "04-aug-2011", "04-aug-2011", null);
		PersonType schoolPerson3 = SchoolMetadaHelper.MakeSchoolPersonTypeFrom("VP and Dean", "DEAN", null, "Y",
				"janetw@umich.edu", "04-aug-2011", null, schoolPerson3_name );
		SchoolPersons schoolPersons = new SchoolPersons();
		schoolPersons.getSchoolPerson().addAll(Lists.newArrayList(schoolPerson1, schoolPerson2, schoolPerson3));
		
		x.setSchoolPersons(schoolPersons);
		
		return x;
	}
}

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.04.29 at 11:18:39 AM EDT 
//


package com.proquest.mtg.dismetadataservice.schoolmetadata.xml;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.proquest.mtg.dismetadataservice.schoolmetadata.xml package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.proquest.mtg.dismetadataservice.schoolmetadata.xml
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Schools }
     * 
     */
    public Schools createSchools() {
        return new Schools();
    }

    /**
     * Create an instance of {@link AddressUseType }
     * 
     */
    public AddressUseType createAddressUseType() {
        return new AddressUseType();
    }

    /**
     * Create an instance of {@link AddressType }
     * 
     */
    public AddressType createAddressType() {
        return new AddressType();
    }

    /**
     * Create an instance of {@link Schools.School }
     * 
     */
    public Schools.School createSchoolsSchool() {
        return new Schools.School();
    }

    /**
     * Create an instance of {@link PersonType }
     * 
     */
    public PersonType createPersonType() {
        return new PersonType();
    }

    /**
     * Create an instance of {@link NameType }
     * 
     */
    public NameType createNameType() {
        return new NameType();
    }

    /**
     * Create an instance of {@link AssociatedSchoolType }
     * 
     */
    public AssociatedSchoolType createAssociatedSchoolType() {
        return new AssociatedSchoolType();
    }

    /**
     * Create an instance of {@link ContactType }
     * 
     */
    public ContactType createContactType() {
        return new ContactType();
    }

    /**
     * Create an instance of {@link AddressUseType.SchoolContacts }
     * 
     */
    public AddressUseType.SchoolContacts createAddressUseTypeSchoolContacts() {
        return new AddressUseType.SchoolContacts();
    }

    /**
     * Create an instance of {@link AddressType.AddressUses }
     * 
     */
    public AddressType.AddressUses createAddressTypeAddressUses() {
        return new AddressType.AddressUses();
    }

    /**
     * Create an instance of {@link Schools.School.AssociatedSchools }
     * 
     */
    public Schools.School.AssociatedSchools createSchoolsSchoolAssociatedSchools() {
        return new Schools.School.AssociatedSchools();
    }

    /**
     * Create an instance of {@link Schools.School.SchoolPersons }
     * 
     */
    public Schools.School.SchoolPersons createSchoolsSchoolSchoolPersons() {
        return new Schools.School.SchoolPersons();
    }

    /**
     * Create an instance of {@link Schools.School.Addresses }
     * 
     */
    public Schools.School.Addresses createSchoolsSchoolAddresses() {
        return new Schools.School.Addresses();
    }

}

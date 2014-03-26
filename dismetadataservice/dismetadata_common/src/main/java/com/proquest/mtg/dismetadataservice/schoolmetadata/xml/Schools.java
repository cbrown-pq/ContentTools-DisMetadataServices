//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.03.26 at 10:33:12 AM EDT 
//


package com.proquest.mtg.dismetadataservice.schoolmetadata.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="School">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="Country" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="State" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="CarnegieCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="DashboardEligibilityCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="DashboardEligibilityDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="SchoolPersons" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="SchoolPerson" type="{}PersonType" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Addresses">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Address" type="{}AddressType" maxOccurs="unbounded" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "school"
})
@XmlRootElement(name = "Schools")
public class Schools {

    @XmlElement(name = "School", required = true)
    protected Schools.School school;

    /**
     * Gets the value of the school property.
     * 
     * @return
     *     possible object is
     *     {@link Schools.School }
     *     
     */
    public Schools.School getSchool() {
        return school;
    }

    /**
     * Sets the value of the school property.
     * 
     * @param value
     *     allowed object is
     *     {@link Schools.School }
     *     
     */
    public void setSchool(Schools.School value) {
        this.school = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="Country" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="State" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="CarnegieCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="DashboardEligibilityCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="DashboardEligibilityDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="SchoolPersons" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="SchoolPerson" type="{}PersonType" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Addresses">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Address" type="{}AddressType" maxOccurs="unbounded" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "code",
        "name",
        "country",
        "state",
        "carnegieCode",
        "dashboardEligibilityCode",
        "dashboardEligibilityDescription",
        "schoolPersons",
        "addresses"
    })
    public static class School {

        @XmlElement(name = "Code", required = true)
        protected String code;
        @XmlElement(name = "Name", required = true)
        protected String name;
        @XmlElement(name = "Country", required = true)
        protected String country;
        @XmlElement(name = "State")
        protected String state;
        @XmlElement(name = "CarnegieCode")
        protected String carnegieCode;
        @XmlElement(name = "DashboardEligibilityCode")
        protected String dashboardEligibilityCode;
        @XmlElement(name = "DashboardEligibilityDescription")
        protected String dashboardEligibilityDescription;
        @XmlElement(name = "SchoolPersons")
        protected Schools.School.SchoolPersons schoolPersons;
        @XmlElement(name = "Addresses", required = true)
        protected Schools.School.Addresses addresses;

        /**
         * Gets the value of the code property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCode() {
            return code;
        }

        /**
         * Sets the value of the code property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCode(String value) {
            this.code = value;
        }

        /**
         * Gets the value of the name property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }

        /**
         * Gets the value of the country property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCountry() {
            return country;
        }

        /**
         * Sets the value of the country property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCountry(String value) {
            this.country = value;
        }

        /**
         * Gets the value of the state property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getState() {
            return state;
        }

        /**
         * Sets the value of the state property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setState(String value) {
            this.state = value;
        }

        /**
         * Gets the value of the carnegieCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCarnegieCode() {
            return carnegieCode;
        }

        /**
         * Sets the value of the carnegieCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCarnegieCode(String value) {
            this.carnegieCode = value;
        }

        /**
         * Gets the value of the dashboardEligibilityCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDashboardEligibilityCode() {
            return dashboardEligibilityCode;
        }

        /**
         * Sets the value of the dashboardEligibilityCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDashboardEligibilityCode(String value) {
            this.dashboardEligibilityCode = value;
        }

        /**
         * Gets the value of the dashboardEligibilityDescription property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDashboardEligibilityDescription() {
            return dashboardEligibilityDescription;
        }

        /**
         * Sets the value of the dashboardEligibilityDescription property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDashboardEligibilityDescription(String value) {
            this.dashboardEligibilityDescription = value;
        }

        /**
         * Gets the value of the schoolPersons property.
         * 
         * @return
         *     possible object is
         *     {@link Schools.School.SchoolPersons }
         *     
         */
        public Schools.School.SchoolPersons getSchoolPersons() {
            return schoolPersons;
        }

        /**
         * Sets the value of the schoolPersons property.
         * 
         * @param value
         *     allowed object is
         *     {@link Schools.School.SchoolPersons }
         *     
         */
        public void setSchoolPersons(Schools.School.SchoolPersons value) {
            this.schoolPersons = value;
        }

        /**
         * Gets the value of the addresses property.
         * 
         * @return
         *     possible object is
         *     {@link Schools.School.Addresses }
         *     
         */
        public Schools.School.Addresses getAddresses() {
            return addresses;
        }

        /**
         * Sets the value of the addresses property.
         * 
         * @param value
         *     allowed object is
         *     {@link Schools.School.Addresses }
         *     
         */
        public void setAddresses(Schools.School.Addresses value) {
            this.addresses = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="Address" type="{}AddressType" maxOccurs="unbounded" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "address"
        })
        public static class Addresses {

            @XmlElement(name = "Address")
            protected List<AddressType> address;

            /**
             * Gets the value of the address property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the address property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getAddress().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link AddressType }
             * 
             * 
             */
            public List<AddressType> getAddress() {
                if (address == null) {
                    address = new ArrayList<AddressType>();
                }
                return this.address;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="SchoolPerson" type="{}PersonType" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "schoolPerson"
        })
        public static class SchoolPersons {

            @XmlElement(name = "SchoolPerson")
            protected PersonType schoolPerson;

            /**
             * Gets the value of the schoolPerson property.
             * 
             * @return
             *     possible object is
             *     {@link PersonType }
             *     
             */
            public PersonType getSchoolPerson() {
                return schoolPerson;
            }

            /**
             * Sets the value of the schoolPerson property.
             * 
             * @param value
             *     allowed object is
             *     {@link PersonType }
             *     
             */
            public void setSchoolPerson(PersonType value) {
                this.schoolPerson = value;
            }

        }

    }

}

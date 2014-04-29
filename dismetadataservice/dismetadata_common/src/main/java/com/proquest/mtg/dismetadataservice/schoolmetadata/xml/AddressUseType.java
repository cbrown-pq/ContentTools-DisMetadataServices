//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.04.29 at 11:18:39 AM EDT 
//


package com.proquest.mtg.dismetadataservice.schoolmetadata.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AddressUseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AddressUseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EBSAccount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DeliveryDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DateCreated" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DateModified" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SchoolContacts" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="SchoolContact" type="{}ContactType" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "AddressUseType", propOrder = {
    "type",
    "ebsAccount",
    "deliveryDate",
    "dateCreated",
    "dateModified",
    "schoolContacts"
})
public class AddressUseType {

    @XmlElement(name = "Type")
    protected String type;
    @XmlElement(name = "EBSAccount")
    protected String ebsAccount;
    @XmlElement(name = "DeliveryDate")
    protected String deliveryDate;
    @XmlElement(name = "DateCreated")
    protected String dateCreated;
    @XmlElement(name = "DateModified")
    protected String dateModified;
    @XmlElement(name = "SchoolContacts")
    protected AddressUseType.SchoolContacts schoolContacts;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the ebsAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEBSAccount() {
        return ebsAccount;
    }

    /**
     * Sets the value of the ebsAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEBSAccount(String value) {
        this.ebsAccount = value;
    }

    /**
     * Gets the value of the deliveryDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * Sets the value of the deliveryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryDate(String value) {
        this.deliveryDate = value;
    }

    /**
     * Gets the value of the dateCreated property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateCreated() {
        return dateCreated;
    }

    /**
     * Sets the value of the dateCreated property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateCreated(String value) {
        this.dateCreated = value;
    }

    /**
     * Gets the value of the dateModified property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateModified() {
        return dateModified;
    }

    /**
     * Sets the value of the dateModified property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateModified(String value) {
        this.dateModified = value;
    }

    /**
     * Gets the value of the schoolContacts property.
     * 
     * @return
     *     possible object is
     *     {@link AddressUseType.SchoolContacts }
     *     
     */
    public AddressUseType.SchoolContacts getSchoolContacts() {
        return schoolContacts;
    }

    /**
     * Sets the value of the schoolContacts property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressUseType.SchoolContacts }
     *     
     */
    public void setSchoolContacts(AddressUseType.SchoolContacts value) {
        this.schoolContacts = value;
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
     *         &lt;element name="SchoolContact" type="{}ContactType" maxOccurs="unbounded" minOccurs="0"/>
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
        "schoolContact"
    })
    public static class SchoolContacts {

        @XmlElement(name = "SchoolContact")
        protected List<ContactType> schoolContact;

        /**
         * Gets the value of the schoolContact property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the schoolContact property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSchoolContact().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ContactType }
         * 
         * 
         */
        public List<ContactType> getSchoolContact() {
            if (schoolContact == null) {
                schoolContact = new ArrayList<ContactType>();
            }
            return this.schoolContact;
        }

    }

}

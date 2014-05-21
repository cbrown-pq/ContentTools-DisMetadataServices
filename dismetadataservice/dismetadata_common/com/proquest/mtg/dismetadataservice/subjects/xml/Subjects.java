//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.05.20 at 09:25:02 AM EDT 
//


package com.proquest.mtg.dismetadataservice.subjects.xml;

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
 *         &lt;element name="Subject" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="SubjectCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="SubjectDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="PQOnlineDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="PQOnlineDescriptions" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="PQOnlineDescription" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="StatusDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "subject"
})
@XmlRootElement(name = "Subjects")
public class Subjects {

    @XmlElement(name = "Subject", required = true)
    protected List<Subjects.Subject> subject;

    /**
     * Gets the value of the subject property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subject property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubject().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Subjects.Subject }
     * 
     * 
     */
    public List<Subjects.Subject> getSubject() {
        if (subject == null) {
            subject = new ArrayList<Subjects.Subject>();
        }
        return this.subject;
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
     *         &lt;element name="SubjectCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="SubjectDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="PQOnlineDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="PQOnlineDescriptions" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="PQOnlineDescription" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="StatusDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "subjectCode",
        "subjectDesc",
        "pqOnlineDesc",
        "pqOnlineDescriptions",
        "status",
        "statusDate"
    })
    public static class Subject {

        @XmlElement(name = "SubjectCode")
        protected String subjectCode;
        @XmlElement(name = "SubjectDesc")
        protected String subjectDesc;
        @XmlElement(name = "PQOnlineDesc")
        protected String pqOnlineDesc;
        @XmlElement(name = "PQOnlineDescriptions")
        protected Subjects.Subject.PQOnlineDescriptions pqOnlineDescriptions;
        @XmlElement(name = "Status")
        protected String status;
        @XmlElement(name = "StatusDate")
        protected String statusDate;

        /**
         * Gets the value of the subjectCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSubjectCode() {
            return subjectCode;
        }

        /**
         * Sets the value of the subjectCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSubjectCode(String value) {
            this.subjectCode = value;
        }

        /**
         * Gets the value of the subjectDesc property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSubjectDesc() {
            return subjectDesc;
        }

        /**
         * Sets the value of the subjectDesc property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSubjectDesc(String value) {
            this.subjectDesc = value;
        }

        /**
         * Gets the value of the pqOnlineDesc property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPQOnlineDesc() {
            return pqOnlineDesc;
        }

        /**
         * Sets the value of the pqOnlineDesc property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPQOnlineDesc(String value) {
            this.pqOnlineDesc = value;
        }

        /**
         * Gets the value of the pqOnlineDescriptions property.
         * 
         * @return
         *     possible object is
         *     {@link Subjects.Subject.PQOnlineDescriptions }
         *     
         */
        public Subjects.Subject.PQOnlineDescriptions getPQOnlineDescriptions() {
            return pqOnlineDescriptions;
        }

        /**
         * Sets the value of the pqOnlineDescriptions property.
         * 
         * @param value
         *     allowed object is
         *     {@link Subjects.Subject.PQOnlineDescriptions }
         *     
         */
        public void setPQOnlineDescriptions(Subjects.Subject.PQOnlineDescriptions value) {
            this.pqOnlineDescriptions = value;
        }

        /**
         * Gets the value of the status property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStatus() {
            return status;
        }

        /**
         * Sets the value of the status property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStatus(String value) {
            this.status = value;
        }

        /**
         * Gets the value of the statusDate property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStatusDate() {
            return statusDate;
        }

        /**
         * Sets the value of the statusDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStatusDate(String value) {
            this.statusDate = value;
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
         *         &lt;element name="PQOnlineDescription" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
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
            "pqOnlineDescription"
        })
        public static class PQOnlineDescriptions {

            @XmlElement(name = "PQOnlineDescription")
            protected List<String> pqOnlineDescription;

            /**
             * Gets the value of the pqOnlineDescription property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the pqOnlineDescription property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getPQOnlineDescription().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link String }
             * 
             * 
             */
            public List<String> getPQOnlineDescription() {
                if (pqOnlineDescription == null) {
                    pqOnlineDescription = new ArrayList<String>();
                }
                return this.pqOnlineDescription;
            }

        }

    }

}

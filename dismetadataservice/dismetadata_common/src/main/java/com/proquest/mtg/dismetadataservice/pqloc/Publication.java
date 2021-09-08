
package com.proquest.mtg.dismetadataservice.pqloc;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Publication complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Publication"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="YearofCompletion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PreregistrationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PublishedWork" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="NationofFirstPublication" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Dateoffirstpublication" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="StandardWorkIdentifierType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="StandardWorkIdentifierValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Titles" type="{http://www.loc.gov/BulkClaim}Titles"/&gt;
 *         &lt;element name="Authors" type="{http://www.loc.gov/BulkClaim}Authors"/&gt;
 *         &lt;element name="Claimants" type="{http://www.loc.gov/BulkClaim}Claimants"/&gt;
 *         &lt;element name="LimitationOfClaim" type="{http://www.loc.gov/BulkClaim}LimitationOfClaim"/&gt;
 *         &lt;element name="RightsAndPermissions" type="{http://www.loc.gov/BulkClaim}RightsAndPermissions"/&gt;
 *         &lt;element name="Correspondent" type="{http://www.loc.gov/BulkClaim}Correspondent"/&gt;
 *         &lt;element name="CertificateMailingAddress" type="{http://www.loc.gov/BulkClaim}CertificateMailingAddress"/&gt;
 *         &lt;element name="SpecialHandling" type="{http://www.loc.gov/BulkClaim}SpecialHandling" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Certification" type="{http://www.loc.gov/BulkClaim}Certification"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Publication", propOrder = {
    "yearofCompletion",
    "preregistrationNumber",
    "publishedWork",
    "nationofFirstPublication",
    "dateoffirstpublication",
    "standardWorkIdentifierType",
    "standardWorkIdentifierValue",
    "titles",
    "authors",
    "claimants",
    "limitationOfClaim",
    "rightsAndPermissions",
    "correspondent",
    "certificateMailingAddress",
    "specialHandling",
    "certification"
})
public class Publication {

    @XmlElement(name = "YearofCompletion", required = true)
    protected String yearofCompletion;
    @XmlElement(name = "PreregistrationNumber")
    protected String preregistrationNumber;
    @XmlElement(name = "PublishedWork", required = true)
    protected String publishedWork;
    @XmlElement(name = "NationofFirstPublication")
    protected String nationofFirstPublication;
    @XmlElement(name = "Dateoffirstpublication")
    protected String dateoffirstpublication;
    @XmlElement(name = "StandardWorkIdentifierType")
    protected String standardWorkIdentifierType;
    @XmlElement(name = "StandardWorkIdentifierValue")
    protected String standardWorkIdentifierValue;
    @XmlElement(name = "Titles", required = true)
    protected Titles titles;
    @XmlElement(name = "Authors", required = true)
    protected Authors authors;
    @XmlElement(name = "Claimants", required = true)
    protected Claimants claimants;
    @XmlElement(name = "LimitationOfClaim", required = true)
    protected LimitationOfClaim limitationOfClaim;
    @XmlElement(name = "RightsAndPermissions", required = true)
    protected RightsAndPermissions rightsAndPermissions;
    @XmlElement(name = "Correspondent", required = true)
    protected Correspondent correspondent;
    @XmlElement(name = "CertificateMailingAddress", required = true)
    protected CertificateMailingAddress certificateMailingAddress;
    @XmlElement(name = "SpecialHandling")
    protected List<SpecialHandling> specialHandling;
    @XmlElement(name = "Certification", required = true)
    protected Certification certification;

    /**
     * Gets the value of the yearofCompletion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYearofCompletion() {
        return yearofCompletion;
    }

    /**
     * Sets the value of the yearofCompletion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYearofCompletion(String value) {
        this.yearofCompletion = value;
    }

    /**
     * Gets the value of the preregistrationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreregistrationNumber() {
        return preregistrationNumber;
    }

    /**
     * Sets the value of the preregistrationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreregistrationNumber(String value) {
        this.preregistrationNumber = value;
    }

    /**
     * Gets the value of the publishedWork property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPublishedWork() {
        return publishedWork;
    }

    /**
     * Sets the value of the publishedWork property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPublishedWork(String value) {
        this.publishedWork = value;
    }

    /**
     * Gets the value of the nationofFirstPublication property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNationofFirstPublication() {
        return nationofFirstPublication;
    }

    /**
     * Sets the value of the nationofFirstPublication property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNationofFirstPublication(String value) {
        this.nationofFirstPublication = value;
    }

    /**
     * Gets the value of the dateoffirstpublication property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateoffirstpublication() {
        return dateoffirstpublication;
    }

    /**
     * Sets the value of the dateoffirstpublication property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateoffirstpublication(String value) {
        this.dateoffirstpublication = value;
    }

    /**
     * Gets the value of the standardWorkIdentifierType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStandardWorkIdentifierType() {
        return standardWorkIdentifierType;
    }

    /**
     * Sets the value of the standardWorkIdentifierType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStandardWorkIdentifierType(String value) {
        this.standardWorkIdentifierType = value;
    }

    /**
     * Gets the value of the standardWorkIdentifierValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStandardWorkIdentifierValue() {
        return standardWorkIdentifierValue;
    }

    /**
     * Sets the value of the standardWorkIdentifierValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStandardWorkIdentifierValue(String value) {
        this.standardWorkIdentifierValue = value;
    }

    /**
     * Gets the value of the titles property.
     * 
     * @return
     *     possible object is
     *     {@link Titles }
     *     
     */
    public Titles getTitles() {
        return titles;
    }

    /**
     * Sets the value of the titles property.
     * 
     * @param value
     *     allowed object is
     *     {@link Titles }
     *     
     */
    public void setTitles(Titles value) {
        this.titles = value;
    }

    /**
     * Gets the value of the authors property.
     * 
     * @return
     *     possible object is
     *     {@link Authors }
     *     
     */
    public Authors getAuthors() {
        return authors;
    }

    /**
     * Sets the value of the authors property.
     * 
     * @param value
     *     allowed object is
     *     {@link Authors }
     *     
     */
    public void setAuthors(Authors value) {
        this.authors = value;
    }

    /**
     * Gets the value of the claimants property.
     * 
     * @return
     *     possible object is
     *     {@link Claimants }
     *     
     */
    public Claimants getClaimants() {
        return claimants;
    }

    /**
     * Sets the value of the claimants property.
     * 
     * @param value
     *     allowed object is
     *     {@link Claimants }
     *     
     */
    public void setClaimants(Claimants value) {
        this.claimants = value;
    }

    /**
     * Gets the value of the limitationOfClaim property.
     * 
     * @return
     *     possible object is
     *     {@link LimitationOfClaim }
     *     
     */
    public LimitationOfClaim getLimitationOfClaim() {
        return limitationOfClaim;
    }

    /**
     * Sets the value of the limitationOfClaim property.
     * 
     * @param value
     *     allowed object is
     *     {@link LimitationOfClaim }
     *     
     */
    public void setLimitationOfClaim(LimitationOfClaim value) {
        this.limitationOfClaim = value;
    }

    /**
     * Gets the value of the rightsAndPermissions property.
     * 
     * @return
     *     possible object is
     *     {@link RightsAndPermissions }
     *     
     */
    public RightsAndPermissions getRightsAndPermissions() {
        return rightsAndPermissions;
    }

    /**
     * Sets the value of the rightsAndPermissions property.
     * 
     * @param value
     *     allowed object is
     *     {@link RightsAndPermissions }
     *     
     */
    public void setRightsAndPermissions(RightsAndPermissions value) {
        this.rightsAndPermissions = value;
    }

    /**
     * Gets the value of the correspondent property.
     * 
     * @return
     *     possible object is
     *     {@link Correspondent }
     *     
     */
    public Correspondent getCorrespondent() {
        return correspondent;
    }

    /**
     * Sets the value of the correspondent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Correspondent }
     *     
     */
    public void setCorrespondent(Correspondent value) {
        this.correspondent = value;
    }

    /**
     * Gets the value of the certificateMailingAddress property.
     * 
     * @return
     *     possible object is
     *     {@link CertificateMailingAddress }
     *     
     */
    public CertificateMailingAddress getCertificateMailingAddress() {
        return certificateMailingAddress;
    }

    /**
     * Sets the value of the certificateMailingAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link CertificateMailingAddress }
     *     
     */
    public void setCertificateMailingAddress(CertificateMailingAddress value) {
        this.certificateMailingAddress = value;
    }

    /**
     * Gets the value of the specialHandling property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the specialHandling property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSpecialHandling().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SpecialHandling }
     * 
     * 
     */
    public List<SpecialHandling> getSpecialHandling() {
        if (specialHandling == null) {
            specialHandling = new ArrayList<SpecialHandling>();
        }
        return this.specialHandling;
    }

    /**
     * Gets the value of the certification property.
     * 
     * @return
     *     possible object is
     *     {@link Certification }
     *     
     */
    public Certification getCertification() {
        return certification;
    }

    /**
     * Sets the value of the certification property.
     * 
     * @param value
     *     allowed object is
     *     {@link Certification }
     *     
     */
    public void setCertification(Certification value) {
        this.certification = value;
    }

}

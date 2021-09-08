
package com.proquest.mtg.dismetadataservice.pqloc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Certification complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Certification"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CertificationAuthority" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CertifiedBy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Comments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ApplicantInternalTrackingNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Certification", propOrder = {
    "certificationAuthority",
    "certifiedBy",
    "comments",
    "applicantInternalTrackingNumber"
})
public class Certification {

    @XmlElement(name = "CertificationAuthority", required = true)
    protected String certificationAuthority;
    @XmlElement(name = "CertifiedBy", required = true)
    protected String certifiedBy;
    @XmlElement(name = "Comments")
    protected String comments;
    @XmlElement(name = "ApplicantInternalTrackingNumber")
    protected String applicantInternalTrackingNumber;

    /**
     * Gets the value of the certificationAuthority property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertificationAuthority() {
        return certificationAuthority;
    }

    /**
     * Sets the value of the certificationAuthority property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertificationAuthority(String value) {
        this.certificationAuthority = value;
    }

    /**
     * Gets the value of the certifiedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertifiedBy() {
        return certifiedBy;
    }

    /**
     * Sets the value of the certifiedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertifiedBy(String value) {
        this.certifiedBy = value;
    }

    /**
     * Gets the value of the comments property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComments(String value) {
        this.comments = value;
    }

    /**
     * Gets the value of the applicantInternalTrackingNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicantInternalTrackingNumber() {
        return applicantInternalTrackingNumber;
    }

    /**
     * Sets the value of the applicantInternalTrackingNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicantInternalTrackingNumber(String value) {
        this.applicantInternalTrackingNumber = value;
    }

}

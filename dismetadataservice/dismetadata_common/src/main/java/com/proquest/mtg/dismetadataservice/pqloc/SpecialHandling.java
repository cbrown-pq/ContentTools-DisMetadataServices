
package com.proquest.mtg.dismetadataservice.pqloc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SpecialHandling complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SpecialHandling"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SpecialHandling" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SpecialHandlingReasonsLitigation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SpecialHandlingReasonsCustoms" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SpecialHandlingReasonsDeadlines" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SpecialHandlingReasonsCertification" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Comments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpecialHandling", propOrder = {
    "specialHandling",
    "specialHandlingReasonsLitigation",
    "specialHandlingReasonsCustoms",
    "specialHandlingReasonsDeadlines",
    "specialHandlingReasonsCertification",
    "comments"
})
public class SpecialHandling {

    @XmlElement(name = "SpecialHandling")
    protected String specialHandling;
    @XmlElement(name = "SpecialHandlingReasonsLitigation")
    protected String specialHandlingReasonsLitigation;
    @XmlElement(name = "SpecialHandlingReasonsCustoms")
    protected String specialHandlingReasonsCustoms;
    @XmlElement(name = "SpecialHandlingReasonsDeadlines")
    protected String specialHandlingReasonsDeadlines;
    @XmlElement(name = "SpecialHandlingReasonsCertification")
    protected String specialHandlingReasonsCertification;
    @XmlElement(name = "Comments")
    protected String comments;

    /**
     * Gets the value of the specialHandling property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialHandling() {
        return specialHandling;
    }

    /**
     * Sets the value of the specialHandling property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialHandling(String value) {
        this.specialHandling = value;
    }

    /**
     * Gets the value of the specialHandlingReasonsLitigation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialHandlingReasonsLitigation() {
        return specialHandlingReasonsLitigation;
    }

    /**
     * Sets the value of the specialHandlingReasonsLitigation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialHandlingReasonsLitigation(String value) {
        this.specialHandlingReasonsLitigation = value;
    }

    /**
     * Gets the value of the specialHandlingReasonsCustoms property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialHandlingReasonsCustoms() {
        return specialHandlingReasonsCustoms;
    }

    /**
     * Sets the value of the specialHandlingReasonsCustoms property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialHandlingReasonsCustoms(String value) {
        this.specialHandlingReasonsCustoms = value;
    }

    /**
     * Gets the value of the specialHandlingReasonsDeadlines property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialHandlingReasonsDeadlines() {
        return specialHandlingReasonsDeadlines;
    }

    /**
     * Sets the value of the specialHandlingReasonsDeadlines property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialHandlingReasonsDeadlines(String value) {
        this.specialHandlingReasonsDeadlines = value;
    }

    /**
     * Gets the value of the specialHandlingReasonsCertification property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialHandlingReasonsCertification() {
        return specialHandlingReasonsCertification;
    }

    /**
     * Sets the value of the specialHandlingReasonsCertification property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialHandlingReasonsCertification(String value) {
        this.specialHandlingReasonsCertification = value;
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

}

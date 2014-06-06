
package com.proquest.mtg.dismetadataservice.pqloc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Claim complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Claim">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SRNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustomerClaimId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TypeofWork" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Deposits" type="{http://www.loc.gov/BulkClaim}Deposits"/>
 *         &lt;element name="Publication" type="{http://www.loc.gov/BulkClaim}Publication"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Claim", propOrder = {
    "srNumber",
    "customerClaimId",
    "typeofWork",
    "deposits",
    "publication"
})
public class Claim {

    @XmlElement(name = "SRNumber")
    protected String srNumber;
    @XmlElement(name = "CustomerClaimId", required = true)
    protected String customerClaimId;
    @XmlElement(name = "TypeofWork", required = true)
    protected String typeofWork;
    @XmlElement(name = "Deposits", required = true)
    protected Deposits deposits;
    @XmlElement(name = "Publication", required = true)
    protected Publication publication;

    /**
     * Gets the value of the srNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSRNumber() {
        return srNumber;
    }

    /**
     * Sets the value of the srNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSRNumber(String value) {
        this.srNumber = value;
    }

    /**
     * Gets the value of the customerClaimId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerClaimId() {
        return customerClaimId;
    }

    /**
     * Sets the value of the customerClaimId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerClaimId(String value) {
        this.customerClaimId = value;
    }

    /**
     * Gets the value of the typeofWork property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeofWork() {
        return typeofWork;
    }

    /**
     * Sets the value of the typeofWork property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeofWork(String value) {
        this.typeofWork = value;
    }

    /**
     * Gets the value of the deposits property.
     * 
     * @return
     *     possible object is
     *     {@link Deposits }
     *     
     */
    public Deposits getDeposits() {
        return deposits;
    }

    /**
     * Sets the value of the deposits property.
     * 
     * @param value
     *     allowed object is
     *     {@link Deposits }
     *     
     */
    public void setDeposits(Deposits value) {
        this.deposits = value;
    }

    /**
     * Gets the value of the publication property.
     * 
     * @return
     *     possible object is
     *     {@link Publication }
     *     
     */
    public Publication getPublication() {
        return publication;
    }

    /**
     * Sets the value of the publication property.
     * 
     * @param value
     *     allowed object is
     *     {@link Publication }
     *     
     */
    public void setPublication(Publication value) {
        this.publication = value;
    }

}

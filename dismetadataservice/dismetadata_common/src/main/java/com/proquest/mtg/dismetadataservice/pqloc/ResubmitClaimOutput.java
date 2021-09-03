
package com.proquest.mtg.dismetadataservice.pqloc;

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
 *         &lt;element name="BatchNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BulkSubmissionStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Error_spcCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Error_spcMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "batchNumber",
    "bulkSubmissionStatus",
    "errorSpcCode",
    "errorSpcMessage"
})
@XmlRootElement(name = "ResubmitClaim_Output", namespace = "https://eco.copyright.gov/WebServices")
public class ResubmitClaimOutput {

    @XmlElement(name = "BatchNumber", namespace = "https://eco.copyright.gov/WebServices", required = true)
    protected String batchNumber;
    @XmlElement(name = "BulkSubmissionStatus", namespace = "https://eco.copyright.gov/WebServices", required = true)
    protected String bulkSubmissionStatus;
    @XmlElement(name = "Error_spcCode", namespace = "https://eco.copyright.gov/WebServices", required = true)
    protected String errorSpcCode;
    @XmlElement(name = "Error_spcMessage", namespace = "https://eco.copyright.gov/WebServices", required = true)
    protected String errorSpcMessage;

    /**
     * Gets the value of the batchNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBatchNumber() {
        return batchNumber;
    }

    /**
     * Sets the value of the batchNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBatchNumber(String value) {
        this.batchNumber = value;
    }

    /**
     * Gets the value of the bulkSubmissionStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBulkSubmissionStatus() {
        return bulkSubmissionStatus;
    }

    /**
     * Sets the value of the bulkSubmissionStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBulkSubmissionStatus(String value) {
        this.bulkSubmissionStatus = value;
    }

    /**
     * Gets the value of the errorSpcCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorSpcCode() {
        return errorSpcCode;
    }

    /**
     * Sets the value of the errorSpcCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorSpcCode(String value) {
        this.errorSpcCode = value;
    }

    /**
     * Gets the value of the errorSpcMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorSpcMessage() {
        return errorSpcMessage;
    }

    /**
     * Sets the value of the errorSpcMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorSpcMessage(String value) {
        this.errorSpcMessage = value;
    }

}

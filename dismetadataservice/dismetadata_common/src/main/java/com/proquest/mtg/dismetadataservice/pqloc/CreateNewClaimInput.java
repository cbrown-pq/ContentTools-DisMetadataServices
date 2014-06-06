
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
 *         &lt;element ref="{http://www.loc.gov/BulkClaim}Claims"/>
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
    "claims"
})
@XmlRootElement(name = "CreateNewClaim_Input", namespace = "https://eco.copyright.gov/WebServices")
public class CreateNewClaimInput {

    @XmlElement(name = "Claims", required = true)
    protected Claims claims;

    /**
     * Gets the value of the claims property.
     * 
     * @return
     *     possible object is
     *     {@link Claims }
     *     
     */
    public Claims getClaims() {
        return claims;
    }

    /**
     * Sets the value of the claims property.
     * 
     * @param value
     *     allowed object is
     *     {@link Claims }
     *     
     */
    public void setClaims(Claims value) {
        this.claims = value;
    }

}

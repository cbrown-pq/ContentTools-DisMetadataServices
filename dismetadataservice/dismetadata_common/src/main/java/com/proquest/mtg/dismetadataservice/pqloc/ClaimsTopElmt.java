
package com.proquest.mtg.dismetadataservice.pqloc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClaimsTopElmt complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClaimsTopElmt"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Claims" type="{http://www.loc.gov/BulkClaim}Claims"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClaimsTopElmt", propOrder = {
    "claims"
})
public class ClaimsTopElmt {

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

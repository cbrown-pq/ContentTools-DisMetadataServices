
package com.proquest.mtg.dismetadataservice.pqloc;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Claimants complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Claimants"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Claimant" type="{http://www.loc.gov/BulkClaim}Claimant" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Claimants", propOrder = {
    "claimant"
})
public class Claimants {

    @XmlElement(name = "Claimant", required = true)
    protected List<Claimant> claimant;

    /**
     * Gets the value of the claimant property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the claimant property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClaimant().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Claimant }
     * 
     * 
     */
    public List<Claimant> getClaimant() {
        if (claimant == null) {
            claimant = new ArrayList<Claimant>();
        }
        return this.claimant;
    }

}

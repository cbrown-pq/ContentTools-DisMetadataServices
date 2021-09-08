
package com.proquest.mtg.dismetadataservice.pqloc;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Deposits complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Deposits"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Deposit" type="{http://www.loc.gov/BulkClaim}Deposit" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Deposits", propOrder = {
    "deposit"
})
public class Deposits {

    @XmlElement(name = "Deposit", required = true)
    protected List<Deposit> deposit;

    /**
     * Gets the value of the deposit property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deposit property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeposit().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Deposit }
     * 
     * 
     */
    public List<Deposit> getDeposit() {
        if (deposit == null) {
            deposit = new ArrayList<Deposit>();
        }
        return this.deposit;
    }

}

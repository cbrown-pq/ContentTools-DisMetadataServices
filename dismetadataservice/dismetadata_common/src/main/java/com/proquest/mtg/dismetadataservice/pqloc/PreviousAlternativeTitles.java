
package com.proquest.mtg.dismetadataservice.pqloc;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PreviousAlternativeTitles complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PreviousAlternativeTitles">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PreviousAlternativeTitle" type="{http://www.loc.gov/BulkClaim}PreviousAlternativeTitle" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PreviousAlternativeTitles", propOrder = {
    "previousAlternativeTitle"
})
public class PreviousAlternativeTitles {

    @XmlElement(name = "PreviousAlternativeTitle")
    protected List<PreviousAlternativeTitle> previousAlternativeTitle;

    /**
     * Gets the value of the previousAlternativeTitle property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the previousAlternativeTitle property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPreviousAlternativeTitle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PreviousAlternativeTitle }
     * 
     * 
     */
    public List<PreviousAlternativeTitle> getPreviousAlternativeTitle() {
        if (previousAlternativeTitle == null) {
            previousAlternativeTitle = new ArrayList<PreviousAlternativeTitle>();
        }
        return this.previousAlternativeTitle;
    }

}

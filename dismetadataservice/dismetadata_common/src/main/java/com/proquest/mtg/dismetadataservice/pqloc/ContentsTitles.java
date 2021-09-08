
package com.proquest.mtg.dismetadataservice.pqloc;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContentsTitles complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContentsTitles"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ContentsTitle" type="{http://www.loc.gov/BulkClaim}ContentsTitle" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContentsTitles", propOrder = {
    "contentsTitle"
})
public class ContentsTitles {

    @XmlElement(name = "ContentsTitle")
    protected List<ContentsTitle> contentsTitle;

    /**
     * Gets the value of the contentsTitle property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contentsTitle property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContentsTitle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContentsTitle }
     * 
     * 
     */
    public List<ContentsTitle> getContentsTitle() {
        if (contentsTitle == null) {
            contentsTitle = new ArrayList<ContentsTitle>();
        }
        return this.contentsTitle;
    }

}

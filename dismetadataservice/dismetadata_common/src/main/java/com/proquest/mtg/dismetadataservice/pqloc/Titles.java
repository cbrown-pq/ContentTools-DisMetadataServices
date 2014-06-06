
package com.proquest.mtg.dismetadataservice.pqloc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Titles complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Titles">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TitleofthisWork" type="{http://www.loc.gov/BulkClaim}TitleofthisWork"/>
 *         &lt;element name="PreviousAlternativeTitles" type="{http://www.loc.gov/BulkClaim}PreviousAlternativeTitles" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Titles", propOrder = {
    "titleofthisWork",
    "previousAlternativeTitles"
})
public class Titles {

    @XmlElement(name = "TitleofthisWork", required = true)
    protected TitleofthisWork titleofthisWork;
    @XmlElement(name = "PreviousAlternativeTitles")
    protected PreviousAlternativeTitles previousAlternativeTitles;

    /**
     * Gets the value of the titleofthisWork property.
     * 
     * @return
     *     possible object is
     *     {@link TitleofthisWork }
     *     
     */
    public TitleofthisWork getTitleofthisWork() {
        return titleofthisWork;
    }

    /**
     * Sets the value of the titleofthisWork property.
     * 
     * @param value
     *     allowed object is
     *     {@link TitleofthisWork }
     *     
     */
    public void setTitleofthisWork(TitleofthisWork value) {
        this.titleofthisWork = value;
    }

    /**
     * Gets the value of the previousAlternativeTitles property.
     * 
     * @return
     *     possible object is
     *     {@link PreviousAlternativeTitles }
     *     
     */
    public PreviousAlternativeTitles getPreviousAlternativeTitles() {
        return previousAlternativeTitles;
    }

    /**
     * Sets the value of the previousAlternativeTitles property.
     * 
     * @param value
     *     allowed object is
     *     {@link PreviousAlternativeTitles }
     *     
     */
    public void setPreviousAlternativeTitles(PreviousAlternativeTitles value) {
        this.previousAlternativeTitles = value;
    }

}

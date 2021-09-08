
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
 * &lt;complexType name="Titles"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TitleofthisWork" type="{http://www.loc.gov/BulkClaim}TitleofthisWork"/&gt;
 *         &lt;element name="PreviousAlternativeTitles" type="{http://www.loc.gov/BulkClaim}PreviousAlternativeTitles" minOccurs="0"/&gt;
 *         &lt;element name="ContentsTitles" type="{http://www.loc.gov/BulkClaim}ContentsTitles" minOccurs="0"/&gt;
 *         &lt;element name="LargerWorkTitles" type="{http://www.loc.gov/BulkClaim}LargerWorkTitles" minOccurs="0"/&gt;
 *         &lt;element name="SeriesTitles" type="{http://www.loc.gov/BulkClaim}SeriesTitles" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Titles", propOrder = {
    "titleofthisWork",
    "previousAlternativeTitles",
    "contentsTitles",
    "largerWorkTitles",
    "seriesTitles"
})
public class Titles {

    @XmlElement(name = "TitleofthisWork", required = true)
    protected TitleofthisWork titleofthisWork;
    @XmlElement(name = "PreviousAlternativeTitles")
    protected PreviousAlternativeTitles previousAlternativeTitles;
    @XmlElement(name = "ContentsTitles")
    protected ContentsTitles contentsTitles;
    @XmlElement(name = "LargerWorkTitles")
    protected LargerWorkTitles largerWorkTitles;
    @XmlElement(name = "SeriesTitles")
    protected SeriesTitles seriesTitles;

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

    /**
     * Gets the value of the contentsTitles property.
     * 
     * @return
     *     possible object is
     *     {@link ContentsTitles }
     *     
     */
    public ContentsTitles getContentsTitles() {
        return contentsTitles;
    }

    /**
     * Sets the value of the contentsTitles property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContentsTitles }
     *     
     */
    public void setContentsTitles(ContentsTitles value) {
        this.contentsTitles = value;
    }

    /**
     * Gets the value of the largerWorkTitles property.
     * 
     * @return
     *     possible object is
     *     {@link LargerWorkTitles }
     *     
     */
    public LargerWorkTitles getLargerWorkTitles() {
        return largerWorkTitles;
    }

    /**
     * Sets the value of the largerWorkTitles property.
     * 
     * @param value
     *     allowed object is
     *     {@link LargerWorkTitles }
     *     
     */
    public void setLargerWorkTitles(LargerWorkTitles value) {
        this.largerWorkTitles = value;
    }

    /**
     * Gets the value of the seriesTitles property.
     * 
     * @return
     *     possible object is
     *     {@link SeriesTitles }
     *     
     */
    public SeriesTitles getSeriesTitles() {
        return seriesTitles;
    }

    /**
     * Sets the value of the seriesTitles property.
     * 
     * @param value
     *     allowed object is
     *     {@link SeriesTitles }
     *     
     */
    public void setSeriesTitles(SeriesTitles value) {
        this.seriesTitles = value;
    }

}

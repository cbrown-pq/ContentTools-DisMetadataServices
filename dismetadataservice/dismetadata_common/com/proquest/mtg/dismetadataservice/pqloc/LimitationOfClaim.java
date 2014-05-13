//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.05.13 at 12:02:58 PM EDT 
//


package com.proquest.mtg.dismetadataservice.pqloc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LimitationOfClaim complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LimitationOfClaim">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MaterialExcludedText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MaterialExcludedPhotographs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MaterialExcludedArtwork" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MaterialExcludedComputerProgram" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MaterialExcludedOther" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MaterialIncludedText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MaterialIncludedPhotographs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MaterialIncludedArtwork" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MaterialIncludedCompilation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MaterialIncludedComputerProgram" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MaterialIncludedOther" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FirstPreviousRegistrationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FirstPreviousRegistrationNumberYear" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SecondPreviousRegistrationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SecondPreviousRegistrationNumberYear" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LimitationOfClaim", propOrder = {
    "materialExcludedText",
    "materialExcludedPhotographs",
    "materialExcludedArtwork",
    "materialExcludedComputerProgram",
    "materialExcludedOther",
    "materialIncludedText",
    "materialIncludedPhotographs",
    "materialIncludedArtwork",
    "materialIncludedCompilation",
    "materialIncludedComputerProgram",
    "materialIncludedOther",
    "firstPreviousRegistrationNumber",
    "firstPreviousRegistrationNumberYear",
    "secondPreviousRegistrationNumber",
    "secondPreviousRegistrationNumberYear"
})
public class LimitationOfClaim {

    @XmlElement(name = "MaterialExcludedText")
    protected String materialExcludedText;
    @XmlElement(name = "MaterialExcludedPhotographs")
    protected String materialExcludedPhotographs;
    @XmlElement(name = "MaterialExcludedArtwork")
    protected String materialExcludedArtwork;
    @XmlElement(name = "MaterialExcludedComputerProgram")
    protected String materialExcludedComputerProgram;
    @XmlElement(name = "MaterialExcludedOther")
    protected String materialExcludedOther;
    @XmlElement(name = "MaterialIncludedText")
    protected String materialIncludedText;
    @XmlElement(name = "MaterialIncludedPhotographs")
    protected String materialIncludedPhotographs;
    @XmlElement(name = "MaterialIncludedArtwork")
    protected String materialIncludedArtwork;
    @XmlElement(name = "MaterialIncludedCompilation")
    protected String materialIncludedCompilation;
    @XmlElement(name = "MaterialIncludedComputerProgram")
    protected String materialIncludedComputerProgram;
    @XmlElement(name = "MaterialIncludedOther")
    protected String materialIncludedOther;
    @XmlElement(name = "FirstPreviousRegistrationNumber")
    protected String firstPreviousRegistrationNumber;
    @XmlElement(name = "FirstPreviousRegistrationNumberYear")
    protected String firstPreviousRegistrationNumberYear;
    @XmlElement(name = "SecondPreviousRegistrationNumber")
    protected String secondPreviousRegistrationNumber;
    @XmlElement(name = "SecondPreviousRegistrationNumberYear")
    protected String secondPreviousRegistrationNumberYear;

    /**
     * Gets the value of the materialExcludedText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaterialExcludedText() {
        return materialExcludedText;
    }

    /**
     * Sets the value of the materialExcludedText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaterialExcludedText(String value) {
        this.materialExcludedText = value;
    }

    /**
     * Gets the value of the materialExcludedPhotographs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaterialExcludedPhotographs() {
        return materialExcludedPhotographs;
    }

    /**
     * Sets the value of the materialExcludedPhotographs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaterialExcludedPhotographs(String value) {
        this.materialExcludedPhotographs = value;
    }

    /**
     * Gets the value of the materialExcludedArtwork property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaterialExcludedArtwork() {
        return materialExcludedArtwork;
    }

    /**
     * Sets the value of the materialExcludedArtwork property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaterialExcludedArtwork(String value) {
        this.materialExcludedArtwork = value;
    }

    /**
     * Gets the value of the materialExcludedComputerProgram property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaterialExcludedComputerProgram() {
        return materialExcludedComputerProgram;
    }

    /**
     * Sets the value of the materialExcludedComputerProgram property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaterialExcludedComputerProgram(String value) {
        this.materialExcludedComputerProgram = value;
    }

    /**
     * Gets the value of the materialExcludedOther property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaterialExcludedOther() {
        return materialExcludedOther;
    }

    /**
     * Sets the value of the materialExcludedOther property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaterialExcludedOther(String value) {
        this.materialExcludedOther = value;
    }

    /**
     * Gets the value of the materialIncludedText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaterialIncludedText() {
        return materialIncludedText;
    }

    /**
     * Sets the value of the materialIncludedText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaterialIncludedText(String value) {
        this.materialIncludedText = value;
    }

    /**
     * Gets the value of the materialIncludedPhotographs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaterialIncludedPhotographs() {
        return materialIncludedPhotographs;
    }

    /**
     * Sets the value of the materialIncludedPhotographs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaterialIncludedPhotographs(String value) {
        this.materialIncludedPhotographs = value;
    }

    /**
     * Gets the value of the materialIncludedArtwork property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaterialIncludedArtwork() {
        return materialIncludedArtwork;
    }

    /**
     * Sets the value of the materialIncludedArtwork property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaterialIncludedArtwork(String value) {
        this.materialIncludedArtwork = value;
    }

    /**
     * Gets the value of the materialIncludedCompilation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaterialIncludedCompilation() {
        return materialIncludedCompilation;
    }

    /**
     * Sets the value of the materialIncludedCompilation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaterialIncludedCompilation(String value) {
        this.materialIncludedCompilation = value;
    }

    /**
     * Gets the value of the materialIncludedComputerProgram property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaterialIncludedComputerProgram() {
        return materialIncludedComputerProgram;
    }

    /**
     * Sets the value of the materialIncludedComputerProgram property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaterialIncludedComputerProgram(String value) {
        this.materialIncludedComputerProgram = value;
    }

    /**
     * Gets the value of the materialIncludedOther property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaterialIncludedOther() {
        return materialIncludedOther;
    }

    /**
     * Sets the value of the materialIncludedOther property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaterialIncludedOther(String value) {
        this.materialIncludedOther = value;
    }

    /**
     * Gets the value of the firstPreviousRegistrationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstPreviousRegistrationNumber() {
        return firstPreviousRegistrationNumber;
    }

    /**
     * Sets the value of the firstPreviousRegistrationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstPreviousRegistrationNumber(String value) {
        this.firstPreviousRegistrationNumber = value;
    }

    /**
     * Gets the value of the firstPreviousRegistrationNumberYear property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstPreviousRegistrationNumberYear() {
        return firstPreviousRegistrationNumberYear;
    }

    /**
     * Sets the value of the firstPreviousRegistrationNumberYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstPreviousRegistrationNumberYear(String value) {
        this.firstPreviousRegistrationNumberYear = value;
    }

    /**
     * Gets the value of the secondPreviousRegistrationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondPreviousRegistrationNumber() {
        return secondPreviousRegistrationNumber;
    }

    /**
     * Sets the value of the secondPreviousRegistrationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondPreviousRegistrationNumber(String value) {
        this.secondPreviousRegistrationNumber = value;
    }

    /**
     * Gets the value of the secondPreviousRegistrationNumberYear property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondPreviousRegistrationNumberYear() {
        return secondPreviousRegistrationNumberYear;
    }

    /**
     * Sets the value of the secondPreviousRegistrationNumberYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondPreviousRegistrationNumberYear(String value) {
        this.secondPreviousRegistrationNumberYear = value;
    }

}

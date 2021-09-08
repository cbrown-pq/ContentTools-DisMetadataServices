
package com.proquest.mtg.dismetadataservice.pqloc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Author complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Author"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AuthorIntegrationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OrganizationName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FirstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="MiddleName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="WorkMadeforHire" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CitizenShip" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Domicile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="YearofBirth" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="YearofDeath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Anonymous" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Pseudonymous" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Pseudonym" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AuthorContributionText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AuthorContributionPhotographs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AuthorContributionArtwork" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AuthorContributionCompilation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AuthorContributionComputerProgram" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AuthorContributionOther" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Author", propOrder = {
    "authorIntegrationId",
    "organizationName",
    "firstName",
    "middleName",
    "lastName",
    "workMadeforHire",
    "citizenShip",
    "domicile",
    "yearofBirth",
    "yearofDeath",
    "anonymous",
    "pseudonymous",
    "pseudonym",
    "authorContributionText",
    "authorContributionPhotographs",
    "authorContributionArtwork",
    "authorContributionCompilation",
    "authorContributionComputerProgram",
    "authorContributionOther"
})
public class Author {

    @XmlElement(name = "AuthorIntegrationId")
    protected String authorIntegrationId;
    @XmlElement(name = "OrganizationName")
    protected String organizationName;
    @XmlElement(name = "FirstName")
    protected String firstName;
    @XmlElement(name = "MiddleName")
    protected String middleName;
    @XmlElement(name = "LastName")
    protected String lastName;
    @XmlElement(name = "WorkMadeforHire")
    protected String workMadeforHire;
    @XmlElement(name = "CitizenShip")
    protected String citizenShip;
    @XmlElement(name = "Domicile")
    protected String domicile;
    @XmlElement(name = "YearofBirth")
    protected String yearofBirth;
    @XmlElement(name = "YearofDeath")
    protected String yearofDeath;
    @XmlElement(name = "Anonymous")
    protected String anonymous;
    @XmlElement(name = "Pseudonymous")
    protected String pseudonymous;
    @XmlElement(name = "Pseudonym")
    protected String pseudonym;
    @XmlElement(name = "AuthorContributionText")
    protected String authorContributionText;
    @XmlElement(name = "AuthorContributionPhotographs")
    protected String authorContributionPhotographs;
    @XmlElement(name = "AuthorContributionArtwork")
    protected String authorContributionArtwork;
    @XmlElement(name = "AuthorContributionCompilation")
    protected String authorContributionCompilation;
    @XmlElement(name = "AuthorContributionComputerProgram")
    protected String authorContributionComputerProgram;
    @XmlElement(name = "AuthorContributionOther")
    protected String authorContributionOther;

    /**
     * Gets the value of the authorIntegrationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorIntegrationId() {
        return authorIntegrationId;
    }

    /**
     * Sets the value of the authorIntegrationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorIntegrationId(String value) {
        this.authorIntegrationId = value;
    }

    /**
     * Gets the value of the organizationName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * Sets the value of the organizationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganizationName(String value) {
        this.organizationName = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the middleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets the value of the middleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiddleName(String value) {
        this.middleName = value;
    }

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the workMadeforHire property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkMadeforHire() {
        return workMadeforHire;
    }

    /**
     * Sets the value of the workMadeforHire property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkMadeforHire(String value) {
        this.workMadeforHire = value;
    }

    /**
     * Gets the value of the citizenShip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCitizenShip() {
        return citizenShip;
    }

    /**
     * Sets the value of the citizenShip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCitizenShip(String value) {
        this.citizenShip = value;
    }

    /**
     * Gets the value of the domicile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomicile() {
        return domicile;
    }

    /**
     * Sets the value of the domicile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomicile(String value) {
        this.domicile = value;
    }

    /**
     * Gets the value of the yearofBirth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYearofBirth() {
        return yearofBirth;
    }

    /**
     * Sets the value of the yearofBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYearofBirth(String value) {
        this.yearofBirth = value;
    }

    /**
     * Gets the value of the yearofDeath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYearofDeath() {
        return yearofDeath;
    }

    /**
     * Sets the value of the yearofDeath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYearofDeath(String value) {
        this.yearofDeath = value;
    }

    /**
     * Gets the value of the anonymous property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnonymous() {
        return anonymous;
    }

    /**
     * Sets the value of the anonymous property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnonymous(String value) {
        this.anonymous = value;
    }

    /**
     * Gets the value of the pseudonymous property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPseudonymous() {
        return pseudonymous;
    }

    /**
     * Sets the value of the pseudonymous property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPseudonymous(String value) {
        this.pseudonymous = value;
    }

    /**
     * Gets the value of the pseudonym property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPseudonym() {
        return pseudonym;
    }

    /**
     * Sets the value of the pseudonym property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPseudonym(String value) {
        this.pseudonym = value;
    }

    /**
     * Gets the value of the authorContributionText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorContributionText() {
        return authorContributionText;
    }

    /**
     * Sets the value of the authorContributionText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorContributionText(String value) {
        this.authorContributionText = value;
    }

    /**
     * Gets the value of the authorContributionPhotographs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorContributionPhotographs() {
        return authorContributionPhotographs;
    }

    /**
     * Sets the value of the authorContributionPhotographs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorContributionPhotographs(String value) {
        this.authorContributionPhotographs = value;
    }

    /**
     * Gets the value of the authorContributionArtwork property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorContributionArtwork() {
        return authorContributionArtwork;
    }

    /**
     * Sets the value of the authorContributionArtwork property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorContributionArtwork(String value) {
        this.authorContributionArtwork = value;
    }

    /**
     * Gets the value of the authorContributionCompilation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorContributionCompilation() {
        return authorContributionCompilation;
    }

    /**
     * Sets the value of the authorContributionCompilation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorContributionCompilation(String value) {
        this.authorContributionCompilation = value;
    }

    /**
     * Gets the value of the authorContributionComputerProgram property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorContributionComputerProgram() {
        return authorContributionComputerProgram;
    }

    /**
     * Sets the value of the authorContributionComputerProgram property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorContributionComputerProgram(String value) {
        this.authorContributionComputerProgram = value;
    }

    /**
     * Gets the value of the authorContributionOther property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorContributionOther() {
        return authorContributionOther;
    }

    /**
     * Sets the value of the authorContributionOther property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorContributionOther(String value) {
        this.authorContributionOther = value;
    }

}

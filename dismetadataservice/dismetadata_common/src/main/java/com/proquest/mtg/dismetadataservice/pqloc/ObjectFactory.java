
package com.proquest.mtg.dismetadataservice.pqloc;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.proquest.mtg.disoutloc.locsei.claims package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Claims_QNAME = new QName("http://www.loc.gov/BulkClaim", "Claims");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.proquest.mtg.disoutloc.locsei.claims
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Claims }
     * 
     */
    public Claims createClaims() {
        return new Claims();
    }

    /**
     * Create an instance of {@link ClaimsTopElmt }
     * 
     */
    public ClaimsTopElmt createClaimsTopElmt() {
        return new ClaimsTopElmt();
    }

    /**
     * Create an instance of {@link Claim }
     * 
     */
    public Claim createClaim() {
        return new Claim();
    }

    /**
     * Create an instance of {@link Deposits }
     * 
     */
    public Deposits createDeposits() {
        return new Deposits();
    }

    /**
     * Create an instance of {@link Publication }
     * 
     */
    public Publication createPublication() {
        return new Publication();
    }

    /**
     * Create an instance of {@link Deposit }
     * 
     */
    public Deposit createDeposit() {
        return new Deposit();
    }

    /**
     * Create an instance of {@link Titles }
     * 
     */
    public Titles createTitles() {
        return new Titles();
    }

    /**
     * Create an instance of {@link Authors }
     * 
     */
    public Authors createAuthors() {
        return new Authors();
    }

    /**
     * Create an instance of {@link Claimants }
     * 
     */
    public Claimants createClaimants() {
        return new Claimants();
    }

    /**
     * Create an instance of {@link LimitationOfClaim }
     * 
     */
    public LimitationOfClaim createLimitationOfClaim() {
        return new LimitationOfClaim();
    }

    /**
     * Create an instance of {@link RightsAndPermissions }
     * 
     */
    public RightsAndPermissions createRightsAndPermissions() {
        return new RightsAndPermissions();
    }

    /**
     * Create an instance of {@link Correspondent }
     * 
     */
    public Correspondent createCorrespondent() {
        return new Correspondent();
    }

    /**
     * Create an instance of {@link CertificateMailingAddress }
     * 
     */
    public CertificateMailingAddress createCertificateMailingAddress() {
        return new CertificateMailingAddress();
    }

    /**
     * Create an instance of {@link SpecialHandling }
     * 
     */
    public SpecialHandling createSpecialHandling() {
        return new SpecialHandling();
    }

    /**
     * Create an instance of {@link Certification }
     * 
     */
    public Certification createCertification() {
        return new Certification();
    }

    /**
     * Create an instance of {@link TitleofthisWork }
     * 
     */
    public TitleofthisWork createTitleofthisWork() {
        return new TitleofthisWork();
    }

    /**
     * Create an instance of {@link PreviousAlternativeTitles }
     * 
     */
    public PreviousAlternativeTitles createPreviousAlternativeTitles() {
        return new PreviousAlternativeTitles();
    }

    /**
     * Create an instance of {@link ContentsTitles }
     * 
     */
    public ContentsTitles createContentsTitles() {
        return new ContentsTitles();
    }

    /**
     * Create an instance of {@link LargerWorkTitles }
     * 
     */
    public LargerWorkTitles createLargerWorkTitles() {
        return new LargerWorkTitles();
    }

    /**
     * Create an instance of {@link SeriesTitles }
     * 
     */
    public SeriesTitles createSeriesTitles() {
        return new SeriesTitles();
    }

    /**
     * Create an instance of {@link Author }
     * 
     */
    public Author createAuthor() {
        return new Author();
    }

    /**
     * Create an instance of {@link Claimant }
     * 
     */
    public Claimant createClaimant() {
        return new Claimant();
    }

    /**
     * Create an instance of {@link PreviousAlternativeTitle }
     * 
     */
    public PreviousAlternativeTitle createPreviousAlternativeTitle() {
        return new PreviousAlternativeTitle();
    }

    /**
     * Create an instance of {@link ContentsTitle }
     * 
     */
    public ContentsTitle createContentsTitle() {
        return new ContentsTitle();
    }

    /**
     * Create an instance of {@link LargerWorkTitle }
     * 
     */
    public LargerWorkTitle createLargerWorkTitle() {
        return new LargerWorkTitle();
    }

    /**
     * Create an instance of {@link SeriesTitle }
     * 
     */
    public SeriesTitle createSeriesTitle() {
        return new SeriesTitle();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Claims }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.loc.gov/BulkClaim", name = "Claims")
    public JAXBElement<Claims> createClaims(Claims value) {
        return new JAXBElement<Claims>(_Claims_QNAME, Claims.class, null, value);
    }

}

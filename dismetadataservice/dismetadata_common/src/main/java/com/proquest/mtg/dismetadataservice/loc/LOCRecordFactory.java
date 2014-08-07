package com.proquest.mtg.dismetadataservice.loc;

import java.util.List;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.exodus.AddressMetaData;
import com.proquest.mtg.dismetadataservice.exodus.AddressMetaDataProvider;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.metadata.Author.Claimant;
import com.proquest.mtg.dismetadataservice.metadata.SGMLEntitySubstitution;
import com.proquest.mtg.dismetadataservice.pqloc.Author;
import com.proquest.mtg.dismetadataservice.pqloc.Authors;
import com.proquest.mtg.dismetadataservice.pqloc.CertificateMailingAddress;
import com.proquest.mtg.dismetadataservice.pqloc.Claimants;
import com.proquest.mtg.dismetadataservice.pqloc.Claim;
import com.proquest.mtg.dismetadataservice.pqloc.Publication;
import com.proquest.mtg.dismetadataservice.pqloc.TitleofthisWork;
import com.proquest.mtg.dismetadataservice.pqloc.Titles;

public class LOCRecordFactory {
	private static String kAuthorAddress = "authorAddress";
	private static String kClaimantAddress = "claimantAddress";
	
	private final AddressMetaDataProvider addressMetaDataProvider;
	
	@Inject
	public LOCRecordFactory(AddressMetaDataProvider addressMetaDataProvider) {
		this.addressMetaDataProvider = addressMetaDataProvider;
		
	}

	public AddressMetaDataProvider getAddressMetaDataProvider() {
		return addressMetaDataProvider;
	}
	
	public Claim getLOCRecordFor(DisPubMetaData disPubMetaData) throws Exception {
		Claim claim = new Claim();
		claim.setSRNumber(null);
		claim.setCustomerClaimId(null);
		claim.setTypeofWork(null);
		claim.setDeposits(null);
		
		
		List<AddressMetaData> claimantAddresses = getClaimantAddress(disPubMetaData);
		List<AddressMetaData> authorAddresses = getAddressMetaDataProvider().getAddressFor(
					disPubMetaData.getAuthors().get(0).getAuthorId(),
					kAuthorAddress);
		
		Publication publication = new Publication();
		publication.setDateoffirstpublication(disPubMetaData.getFirstPublicationDate());
		publication.setAuthors(createAuthors(disPubMetaData));
		publication.setClaimants(
				createClaimants(disPubMetaData, claimantAddresses, authorAddresses));
		claim.setCustomerClaimId(disPubMetaData.getPubNumber());
		
		publication.setCertificateMailingAddress(createCertificateMailingAddress(
				disPubMetaData,
				claimantAddresses,
				authorAddresses));
		publication.setYearofCompletion(disPubMetaData.getManuscriptYear());
		publication.setNationofFirstPublication("United States");
		publication.setTitles(createTitles(disPubMetaData));
		claim.setPublication(publication);
		claim.setTypeofWork("TX");
		return claim;
		
	}

	private List<AddressMetaData> getClaimantAddress(
			DisPubMetaData disPubMetaData) throws Exception {
		List<AddressMetaData> claimantAddresses = null;
	
		if(disPubMetaData.getAuthors() != null && disPubMetaData.getAuthors().size() != 0) {
			com.proquest.mtg.dismetadataservice.metadata.Author author = disPubMetaData.getAuthors().get(0);
			if(author.getClaimants() != null && author.getClaimants().size() != 0 ) {
				claimantAddresses = getAddressMetaDataProvider().getAddressFor(
				author.getClaimants().get(0).getClaimantId(),
				kClaimantAddress);
			}
		}
		return claimantAddresses;
	}
	
	private CertificateMailingAddress createCertificateMailingAddress(
			DisPubMetaData disPubMetaData,
			List<AddressMetaData> claimantAddresses,
			List<AddressMetaData> authorAddresses) {
		CertificateMailingAddress certificateMailingAddress = new CertificateMailingAddress();
		com.proquest.mtg.dismetadataservice.metadata.Author author = disPubMetaData.getAuthors().get(0);
		if(author.getClaimantAddFlag() != null && author.getClaimantAddFlag().equalsIgnoreCase("Y")) {
			List<Claimant> claimants = disPubMetaData.getAuthors().get(0).getClaimants();
			if(claimants != null && claimants.size() != 0 ) {
				Claimant claimant = claimants.get(0);
				certificateMailingAddress.setFirstName(claimant.getFirstName());
				certificateMailingAddress.setMiddleName(claimant.getMiddleName());
				certificateMailingAddress.setLastName(claimant.getLastName());
				fillAddress(certificateMailingAddress, claimantAddresses);		}
		} else {
			certificateMailingAddress.setFirstName(author.getFirstName());
			certificateMailingAddress.setMiddleName(author.getMiddleName());
			certificateMailingAddress.setLastName(author.getLastName());
			fillAddress(certificateMailingAddress, authorAddresses);
		}
			
		return certificateMailingAddress;
	}

	private void fillAddress(
			CertificateMailingAddress certificateMailingAddress,
			List<AddressMetaData> claimantAddresses) {
		AddressMetaData claimantAddress = null;
		if(claimantAddresses != null && claimantAddresses.size() != 0) {
			claimantAddress = claimantAddresses.get(0);
		}
		
		if (null != claimantAddress) {
			certificateMailingAddress.setAddress1(createAddress1(claimantAddress));
			certificateMailingAddress.setCity(claimantAddress.getCity());
			if(claimantAddress.getCountry().equalsIgnoreCase("US")) {
				certificateMailingAddress.setCountry(claimantAddress.getCountry());
			} else {
				certificateMailingAddress.setCountry(claimantAddress.getLocCountryDescription());
			}
			certificateMailingAddress.setState(claimantAddress.getStateCode());
			certificateMailingAddress.setPostalCode(createPostalCode(claimantAddress));
		}
		
	}

	private String createPostalCode(AddressMetaData claimantAddress) {
		
		String postalCode = null;
		if (claimantAddress.getCountry().equalsIgnoreCase("US")) {
			String fourDigitZip = claimantAddress.getFourDigitZip();
			String fiveDigitZip = claimantAddress.getFiveDigitZip();
			if (null != fiveDigitZip && !fiveDigitZip.isEmpty()) {
				if (null != fourDigitZip && !fourDigitZip.isEmpty()) {
					postalCode = fiveDigitZip + "-" + fourDigitZip;
				} else {
					postalCode = fiveDigitZip;
				}
				
			}
		} else {
			postalCode = claimantAddress.getPostalCode();
		}
		return postalCode;
	}

	private Titles createTitles(DisPubMetaData disPubMetaData) {
		Titles titles = new Titles();
		titles.setTitleofthisWork(MakeTitleOfThisWorkFrom(disPubMetaData));
		return titles;
	}

	private  TitleofthisWork MakeTitleOfThisWorkFrom(DisPubMetaData disPubMetaData) {
		String title;
		TitleofthisWork titleOfThisWork = new TitleofthisWork();
		
		title = getTitleToInclude(disPubMetaData);
		if (null != title) {
			title = endsWithPunctuationMark(title);
			title = SGMLEntitySubstitution.applyAllTo(title);
		}
		titleOfThisWork.setTitleIntegrationId(disPubMetaData.getPubNumber());
		titleOfThisWork.setTitle(title);
		
		return titleOfThisWork;
	}

	public com.proquest.mtg.dismetadataservice.pqloc.Authors createAuthors(DisPubMetaData disPubMetaData){
		com.proquest.mtg.dismetadataservice.pqloc.Authors pqLocAuthors = new Authors();
		com.proquest.mtg.dismetadataservice.metadata.Author author = disPubMetaData.getAuthors().get(0);
		com.proquest.mtg.dismetadataservice.pqloc.Author pqLocAuthor = new Author();
		pqLocAuthor.setAuthorIntegrationId(disPubMetaData.getPubNumber());
		pqLocAuthor.setFirstName(author.getFirstName());
		pqLocAuthor.setMiddleName(author.getMiddleName());
		pqLocAuthor.setLastName(author.getLastName());
		pqLocAuthor.setCitizenShip(author.getAuthorLocCitizenship());
		pqLocAuthor.setAuthorContributionText("Y");
		pqLocAuthors.getAuthor().add(pqLocAuthor);
		return pqLocAuthors;
	
	}
	
	public com.proquest.mtg.dismetadataservice.pqloc.Claimants createClaimants(DisPubMetaData disPubMetaData, 
			List<AddressMetaData> claimantAddresses, 
			List<AddressMetaData> authorAddresses) {
		com.proquest.mtg.dismetadataservice.pqloc.Claimants pqLocClaimants = new Claimants();
		com.proquest.mtg.dismetadataservice.pqloc.Claimant pqLocClaimant = new com.proquest.mtg.dismetadataservice.pqloc.Claimant();
		
		com.proquest.mtg.dismetadataservice.metadata.Author pubMetaDataAuthor = disPubMetaData.getAuthors().get(0);
		List<Claimant> claimants = pubMetaDataAuthor.getClaimants();
		if(claimants != null && claimants.size() != 0 ) {
			Claimant claimant = claimants.get(0);
			pqLocClaimant.setClaimantIntegrationId(claimant.getClaimantId());
			pqLocClaimant.setFirstName(claimant.getFirstName());
			pqLocClaimant.setMiddleName(claimant.getMiddleName());
			pqLocClaimant.setLastName(claimant.getLastName());
		} else {
			pqLocClaimant.setClaimantIntegrationId(disPubMetaData.getPubNumber());
			pqLocClaimant.setFirstName(pubMetaDataAuthor.getFirstName());
			pqLocClaimant.setMiddleName(pubMetaDataAuthor.getMiddleName());
			pqLocClaimant.setLastName(pubMetaDataAuthor.getLastName());
			
		}
		populateAddress(pqLocClaimant, claimantAddresses, authorAddresses);
		pqLocClaimants.getClaimant().add(pqLocClaimant);
		return pqLocClaimants;
	}
	
	
	private void populateAddress(
			com.proquest.mtg.dismetadataservice.pqloc.Claimant pqLocClaimant,
			List<AddressMetaData> claimantAddresses,
			List<AddressMetaData> authorAddresses) {
		
		AddressMetaData claimantAddress = null;	
		
		if(claimantAddresses != null && !claimantAddresses.isEmpty()) {
			claimantAddress = claimantAddresses.get(0);
		} else if(authorAddresses != null && !authorAddresses.isEmpty()) {
			claimantAddress = authorAddresses.get(0);
		}
		
		if(null != claimantAddress) {
			fillAddress(pqLocClaimant, claimantAddress);
		}
	}

	private void fillAddress(
			com.proquest.mtg.dismetadataservice.pqloc.Claimant pqLocClaimant,
			AddressMetaData claimantAddress) {
		
		pqLocClaimant.setAddress1(createAddress1(claimantAddress));
		pqLocClaimant.setCity(claimantAddress.getCity());
		if(claimantAddress.getCountry().equalsIgnoreCase("US")) {
			pqLocClaimant.setCountry(claimantAddress.getCountry());
		} else {
			pqLocClaimant.setCountry(claimantAddress.getCountryDescription());
		}
		pqLocClaimant.setState(claimantAddress.getStateCode());
		pqLocClaimant.setPostalCode(createPostalCode(claimantAddress));
	}

	private String createAddress1(AddressMetaData addressMetaData) {
		String address1 = "";
		if(null != addressMetaData) {
			if(null != addressMetaData.getLine1()) {
				address1 += addressMetaData.getLine1();
			}
			
			if(null != addressMetaData.getLine2()) {
				address1 += " " + addressMetaData.getLine2();
			}
			
			if(null != addressMetaData.getLine3()) {
				address1 += " " + addressMetaData.getLine3();
			}
		}
		return address1;
	}

	protected String verifyTitle(String title) {

		String outTitle = null;
		if (null != title) {
			outTitle = title.trim();
			if (outTitle.endsWith(".")) {
				outTitle = outTitle.substring(0, outTitle.length() - 1);
			}
		}
		return outTitle;
	}
	
	
	private String getTitleToInclude(DisPubMetaData disPubMetaData) {
		String title = null;
		
		String electronicTitle = (disPubMetaData.getTitle() == null) ? 
				null : verifyTitle(disPubMetaData.getTitle().getElectronicTitle());
		if (null != electronicTitle) {
				title = electronicTitle;
		} else {
				String masterTitle = verifyTitle(disPubMetaData.getTitle()
						.getMasterTitle());
				title = masterTitle;
		}
		return title;
	}
	
	protected String endWithComma(String x) {
		return x.endsWith(",") ? x : x + ",";
	}
	
	protected String endWithPeriod(String x) {
		return x.endsWith(".") ? x : x + ".";
	}
	
	private String endsWithPunctuationMark(String x) {
		return x.matches("^.+[\\.,\\?;:!]$") ? x : x + ".";
	}
}

package com.proquest.mtg.dismetadataservice.loc;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.metadata.Author.Claimant;
import com.proquest.mtg.dismetadataservice.metadata.SGMLEntitySubstitution;
import com.proquest.mtg.dismetadataservice.metadata.SplitAuthorNames;
import com.proquest.mtg.dismetadataservice.pqloc.Author;
import com.proquest.mtg.dismetadataservice.pqloc.Authors;
import com.proquest.mtg.dismetadataservice.pqloc.CertificateMailingAddress;
import com.proquest.mtg.dismetadataservice.pqloc.Claim;
import com.proquest.mtg.dismetadataservice.pqloc.Claimants;
import com.proquest.mtg.dismetadataservice.pqloc.Publication;
import com.proquest.mtg.dismetadataservice.pqloc.TitleofthisWork;
import com.proquest.mtg.dismetadataservice.pqloc.Titles;

public class LOCRecordFactory {
	private static String kClaimantIntegrationIdConstant = "C";
	private static String kAuthorIntegrationIdConstant = "A";
	private static String kTitleIntegrationIdConstant = "T";
	private static String kUnknownLocCitizenship = "not known";

	public Claim getLOCRecordFor(DisPubMetaData disPubMetaData) throws Exception {
		Claim claim = new Claim();
		claim.setSRNumber(null);
		claim.setCustomerClaimId(null);
		claim.setTypeofWork(null);
		claim.setDeposits(null);

//		List<AddressMetaData> claimantAddresses = getClaimantAddress(disPubMetaData);
//		List<AddressMetaData> authorAddresses = getAddressMetaDataProvider()
//				.getAddressFor(disPubMetaData.getAuthors().get(0).getAuthorId(), kAuthorAddress);

		Publication publication = new Publication();
		publication.setYearofCompletion(disPubMetaData.getAuthors().get(0).getDegrees().get(0).getDegreeYear());
		publication.setDateoffirstpublication(new SimpleDateFormat("MM/dd/yyyy").format(
				new SimpleDateFormat("dd-MMM-yyyy").parse(disPubMetaData.getFirstPublicationDate())));
		publication.setAuthors(createAuthors(disPubMetaData));
		publication.setClaimants(createClaimants(disPubMetaData));
		claim.setCustomerClaimId(disPubMetaData.getPubNumber());

		publication.setCertificateMailingAddress(
				createCertificateMailingAddress(disPubMetaData));
		publication.setNationofFirstPublication("United States");
		publication.setTitles(createTitles(disPubMetaData));
		claim.setPublication(publication);
		claim.setTypeofWork("TX");
		return claim;

	}

	private CertificateMailingAddress createCertificateMailingAddress(DisPubMetaData disPubMetaData) {
		CertificateMailingAddress certificateMailingAddress = new CertificateMailingAddress();
		com.proquest.mtg.dismetadataservice.metadata.Author author = disPubMetaData.getAuthors().get(0);
		if ("Y".equalsIgnoreCase(author.getClaimantAddFlag())) {
			List<Claimant> claimants = disPubMetaData.getAuthors().get(0).getClaimants();
			if (claimants != null && !claimants.isEmpty()) {
				Claimant claimant = claimants.get(0);
				certificateMailingAddress.setFirstName(claimant.getFirstName());
				certificateMailingAddress.setMiddleName(claimant.getMiddleName());
				certificateMailingAddress.setLastName(claimant.getLastName());
//				fillAddress(certificateMailingAddress, claimantAddresses);
			}
		} else {
			certificateMailingAddress.setFirstName(author.getFirstName());
			certificateMailingAddress.setMiddleName(author.getMiddleName());
			certificateMailingAddress.setLastName(author.getLastName());
//			fillAddress(certificateMailingAddress, authorAddresses);
		}

		return certificateMailingAddress;
	}

	private Titles createTitles(DisPubMetaData disPubMetaData) {
		Titles titles = new Titles();
		titles.setTitleofthisWork(MakeTitleOfThisWorkFrom(disPubMetaData));
		return titles;
	}

	private TitleofthisWork MakeTitleOfThisWorkFrom(DisPubMetaData disPubMetaData) {
		String title;
		TitleofthisWork titleOfThisWork = new TitleofthisWork();

		title = getTitleToInclude(disPubMetaData);
		if (null != title) {
			title = endsWithPunctuationMark(title);
			title = SGMLEntitySubstitution.applyAllTo(title);
		}
		titleOfThisWork.setTitleIntegrationId(disPubMetaData.getPubNumber() + kTitleIntegrationIdConstant);
		titleOfThisWork.setTitle(title);

		return titleOfThisWork;
	}

	public com.proquest.mtg.dismetadataservice.pqloc.Authors createAuthors(DisPubMetaData disPubMetaData) {
		com.proquest.mtg.dismetadataservice.pqloc.Authors pqLocAuthors = new Authors();
		com.proquest.mtg.dismetadataservice.metadata.Author author = disPubMetaData.getAuthors().get(0);
		com.proquest.mtg.dismetadataservice.pqloc.Author pqLocAuthor = new Author();
		pqLocAuthor.setAuthorIntegrationId(disPubMetaData.getPubNumber() + kAuthorIntegrationIdConstant);
		//TODO need to add logic to handle if the author name was already split?
		SplitAuthorNames splitter = new SplitAuthorNames(author.getAuthorFullName());
		pqLocAuthor.setFirstName(splitter.getFirst());
		pqLocAuthor.setMiddleName(splitter.getMiddle());
		pqLocAuthor.setLastName(splitter.getLast());
		if (StringUtils.isNotEmpty(splitter.getSuffix())) {
			pqLocAuthor.setLastName(splitter.getLast() + " " + splitter.getSuffix());
		}
		if (null != author.getAuthorCitizenship() && !author.getAuthorCitizenship().isEmpty()) {
			pqLocAuthor.setCitizenShip(author.getAuthorLocCitizenship());
		} else {
			pqLocAuthor.setCitizenShip(kUnknownLocCitizenship);
		}
		pqLocAuthor.setAuthorContributionText("Y");
		pqLocAuthors.getAuthor().add(pqLocAuthor);
		return pqLocAuthors;

	}

	public com.proquest.mtg.dismetadataservice.pqloc.Claimants createClaimants(DisPubMetaData disPubMetaData) {
		com.proquest.mtg.dismetadataservice.pqloc.Claimants pqLocClaimants = new Claimants();
		com.proquest.mtg.dismetadataservice.pqloc.Claimant pqLocClaimant = new com.proquest.mtg.dismetadataservice.pqloc.Claimant();

		com.proquest.mtg.dismetadataservice.metadata.Author pubMetaDataAuthor = disPubMetaData.getAuthors().get(0);
		List<Claimant> claimants = pubMetaDataAuthor.getClaimants();
		if (claimants != null && !claimants.isEmpty()) {
			Claimant claimant = claimants.get(0);
			pqLocClaimant.setClaimantIntegrationId(claimant.getClaimantId() + kClaimantIntegrationIdConstant);
			pqLocClaimant.setFirstName(claimant.getFirstName());
			pqLocClaimant.setMiddleName(claimant.getMiddleName());
			pqLocClaimant.setLastName(claimant.getLastName());
		} else {
			pqLocClaimant.setClaimantIntegrationId(disPubMetaData.getPubNumber() + kClaimantIntegrationIdConstant);
			pqLocClaimant.setFirstName(pubMetaDataAuthor.getFirstName());
			pqLocClaimant.setMiddleName(pubMetaDataAuthor.getMiddleName());
			pqLocClaimant.setLastName(pubMetaDataAuthor.getLastName());

		}
//		populateAddress(pqLocClaimant, claimantAddresses, authorAddresses);
		pqLocClaimants.getClaimant().add(pqLocClaimant);
		return pqLocClaimants;
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

		String electronicTitle = (disPubMetaData.getTitle() == null) ? null
				: verifyTitle(disPubMetaData.getTitle().getElectronicTitle());
		if (null != electronicTitle) {
			title = electronicTitle;
		} else {
			String masterTitle = verifyTitle(disPubMetaData.getTitle().getMasterTitle());
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

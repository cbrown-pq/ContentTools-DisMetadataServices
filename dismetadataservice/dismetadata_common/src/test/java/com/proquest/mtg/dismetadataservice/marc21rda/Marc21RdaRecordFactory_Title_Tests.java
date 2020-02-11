package com.proquest.mtg.dismetadataservice.marc21rda;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData.Title;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;
import com.proquest.mtg.dismetadataservice.metadata.Author;
import com.proquest.mtg.dismetadataservice.metadata.DisGenMappingProvider;

public class Marc21RdaRecordFactory_Title_Tests extends
		Marc21RdaRecordFactory_Test_Helper {

	DisGenMappingProvider disGenMappingProvider;
	Author author;
	List<Author> authors;
	Title title;
	String authorFullName1 = "Okafor, Napoleon A.";
	String authorFullName2 = "Stephen, Joseph Buchanan";
	String authorFullName3 = "Coady-Leeper, Marylee T.";
	String authorFullName4 = "White, Kenneth M.";

	@Before
	public void setUp() throws Exception {
		super.setUp();
		title = new Title();
		makeAuthors();
	}
	
	public List<Author> makeAuthors(){
		String authorFullName1 = "Okafor, Napoleon A.";
		String authorFullName2 = "Stephen, Joseph Buchanan";
		String authorFullName3 = "Coady-Leeper, Marylee T.";
		String authorFullName4 = "White, Kenneth M.";
		Author author1 = new Author();
		Author author2 = new Author();
		Author author3 = new Author();
		Author author4 = new Author();
		authors = Lists.newArrayList();
		author1.setAuthorFullName(authorFullName1);
		author1.setSequenceNumber(1);
		author2.setAuthorFullName(authorFullName2);
		author2.setSequenceNumber(2);
		author3.setAuthorFullName(authorFullName3);
		author3.setSequenceNumber(3);
		author4.setAuthorFullName(authorFullName4);
		author4.setSequenceNumber(4);
		authors.add(author1);
		authors.add(author2);
		authors.add(author3);
		authors.add(author4);
		return authors;
	}

	@Test
	public void withNoTitle_ShouldNotGenerateTag() {
		title.setEnglishOverwriteTitle(null);
		String tag = MarcTags.kTitle;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setTitle(title);
		verifyMarcRecordHasEmptyField(metaData, tag);
	}

	@Test
	public void withAllTitle_ShouldUseForeignTitle() {
		title.setEnglishOverwriteTitle("English Title");
		title.setMasterTitle("Master Title");
		title.setForeignTitle("Foreign Title");
		title.setElectronicTitle("Electronic Title");

		String tag = MarcTags.kTitle;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setTitle(title);
		metaData.setAuthors(authors);
		String expectedData = "10"
				+ MarcCharSet.kSubFieldIndicator
				+ "a"
				+ "Foreign Title"
				+ " /"
				+ MarcCharSet.kSubFieldIndicator
				+ "c"
				+ "Napoleon A Okafor, Joseph Buchanan Stephen, Marylee T Coady-Leeper, Kenneth M White.";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedData, 1);
	}

	@Test
	public void exceptForeignTitle_ShouldUseMasterTitle() {
		title.setEnglishOverwriteTitle("English Title");
		title.setMasterTitle("Master Title");
		title.setElectronicTitle("Electronic Title");

		String tag = MarcTags.kTitle;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setTitle(title);
		metaData.setAuthors(authors);
		String expectedData = "10"
				+ MarcCharSet.kSubFieldIndicator
				+ "a"
				+ "Master Title"
				+ " /"
				+ MarcCharSet.kSubFieldIndicator
				+ "c"
				+ "Napoleon A Okafor, Joseph Buchanan Stephen, Marylee T Coady-Leeper, Kenneth M White.";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedData, 1);
	}

	@Test
	public void exceptEnglishTitle_ShouldUseElectronicTitle() {
		title.setMasterTitle("Master Title");
		title.setForeignTitle("Foreign Title");
		title.setElectronicTitle("Electronic Title");

		String tag = MarcTags.kTitle;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setTitle(title);
		metaData.setAuthors(authors);
		String expectedData = "10"
				+ MarcCharSet.kSubFieldIndicator
				+ "a"
				+ "Electronic Title"
				+ " /"
				+ MarcCharSet.kSubFieldIndicator
				+ "c"
				+ "Napoleon A Okafor, Joseph Buchanan Stephen, Marylee T Coady-Leeper, Kenneth M White.";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedData, 1);
	}

	@Test
	public void exceptEnglishAndElectronicTitle_ShouldUseMasterTitle() {
		title.setMasterTitle("Master Title");
		title.setForeignTitle("Foreign Title");

		String tag = MarcTags.kTitle;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setTitle(title);
		metaData.setAuthors(authors);
		String expectedData = "10"
				+ MarcCharSet.kSubFieldIndicator
				+ "a"
				+ "Master Title"
				+ " /"
				+ MarcCharSet.kSubFieldIndicator
				+ "c"
				+ "Napoleon A Okafor, Joseph Buchanan Stephen, Marylee T Coady-Leeper, Kenneth M White.";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedData, 1);
	}

	/*@Test
	public void withMatchingSecondFieldIndicatior1() {
		title.setEnglishOverwriteTitle("English Title");
		title.setMasterTitle("Master Title");
		title.setForeignTitle("A Foreign Title");
		title.setElectronicTitle("Electronic Title");

		String tag = MarcTags.kTitle;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setTitle(title);
		metaData.setAuthors(authors);
		String expectedData = "12"
				+ MarcCharSet.kSubFieldIndicator
				+ "a"
				+ "A Foreign Title"
				+ " /"
				+ MarcCharSet.kSubFieldIndicator
				+ "c"
				+ "Napoleon A Okafor, Joseph Buchanan Stephen, Marylee T Coady-Leeper, Kenneth M White.";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedData, 1);
	}

	@Test
	public void withMatchingSecondFieldIndicatior2() {
		title.setEnglishOverwriteTitle("English Title");
		title.setMasterTitle("Master Title");
		title.setForeignTitle("\"Le Foreign Title");
		title.setElectronicTitle("Electronic Title");

		String tag = MarcTags.kTitle;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setTitle(title);
		metaData.setAuthors(authors);
		String expectedData = "14"
				+ MarcCharSet.kSubFieldIndicator
				+ "a"
				+ "\"Le Foreign Title"
				+ " /"
				+ MarcCharSet.kSubFieldIndicator
				+ "c"
				+ "Napoleon A Okafor, Joseph Buchanan Stephen, Marylee T Coady-Leeper, Kenneth M White.";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedData, 1);
	}*/

	@Test
	public void withMatchingSecondFieldIndicator3() {
		String masterTitle = "THE TOTAL SYNTHESIS OF CYTOVARICIN";
		title.setMasterTitle(masterTitle);
		title.setForeignTitle("Foreign Title");

		String tag = MarcTags.kTitle;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setTitle(title);
		metaData.setAuthors(authors);
		String expectedData = "14"
				+ MarcCharSet.kSubFieldIndicator
				+ "a"
				+ masterTitle
				+ " /"
				+ MarcCharSet.kSubFieldIndicator
				+ "c"
				+ "Napoleon A Okafor, Joseph Buchanan Stephen, Marylee T Coady-Leeper, Kenneth M White.";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedData, 1);
	}

	/*@Test
	public void withMatchingSecondFieldIndicator4() {
		String masterTitle = "A problem-based learning project focused on the Missouri teacher quality standards";
		title.setMasterTitle(masterTitle);
		title.setForeignTitle("Foreign Title");

		String tag = MarcTags.kTitle;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setTitle(title);
		metaData.setAuthors(authors);
		String expectedData = "12"
				+ MarcCharSet.kSubFieldIndicator
				+ "a"
				+ masterTitle
				+ " /"
				+ MarcCharSet.kSubFieldIndicator
				+ "c"
				+ "Napoleon A Okafor, Joseph Buchanan Stephen, Marylee T Coady-Leeper, Kenneth M White.";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedData, 1);
	}*/

	/*@Test
	public void withTitleAndAdditionalAuthors() {
		String masterTitle = "A problem-based learning project focused on the Missouri teacher quality standards";
		title.setMasterTitle(masterTitle);
		title.setForeignTitle("Foreign Title");

		String tag = MarcTags.kTitle;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setTitle(title);
		metaData.setAuthors(authors);
		String expectedData = "12"
				+ MarcCharSet.kSubFieldIndicator
				+ "a"
				+ masterTitle
				+ " /"
				+ MarcCharSet.kSubFieldIndicator
				+ "c"
				+ "Napoleon A Okafor, Joseph Buchanan Stephen, Marylee T Coady-Leeper, Kenneth M White.";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedData, 1);
	}*/
	
	/*@Test
	public void withTitleAndOneAuthor() {
		String masterTitle = "A problem-based learning project focused on the Missouri teacher quality standards";
		title.setMasterTitle(masterTitle);
		title.setForeignTitle("Foreign Title");

		String tag = MarcTags.kTitle;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setTitle(title);
		Author author = new Author();
		author.setAuthorFullName("Okafor, Napoleon A.");
		authors = Lists.newArrayList(author);
		metaData.setAuthors(authors);
		String expectedData = "12"
				+ MarcCharSet.kSubFieldIndicator
				+ "a"
				+ masterTitle
				+ " /"
				+ MarcCharSet.kSubFieldIndicator
				+ "c"
				+ "Napoleon A Okafor.";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedData, 1);
	}*/
	
	/*@Test
	public void withTitleAndNameAndSuffix() {
		String masterTitle = "A problem-based learning project focused on the Missouri teacher quality standards";
		title.setMasterTitle(masterTitle);
		title.setForeignTitle("Foreign Title");

		String tag = MarcTags.kTitle;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setTitle(title);
		Author author = new Author();
		author.setAuthorFullName("Jennings, James Monroe, II.");
		authors = Lists.newArrayList(author);
		metaData.setAuthors(authors);
		String expectedData = "12"
				+ MarcCharSet.kSubFieldIndicator
				+ "a"
				+ masterTitle
				+ " /"
				+ MarcCharSet.kSubFieldIndicator
				+ "c"
				+ "James Monroe Jennings II.";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedData, 1);
	}*/
	
	/*@Test
	public void withTitleAndNameAndSuffixJR() {
		String masterTitle = "A problem-based learning project focused on the Missouri teacher quality standards";
		title.setMasterTitle(masterTitle);
		title.setForeignTitle("Foreign Title");

		String tag = MarcTags.kTitle;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setTitle(title);
		Author author = new Author();
		author.setAuthorFullName("Williams, Frank Broyles, Jr.");
		authors = Lists.newArrayList(author);
		metaData.setAuthors(authors);
		String expectedData = "12"
				+ MarcCharSet.kSubFieldIndicator
				+ "a"
				+ masterTitle
				+ " /"
				+ MarcCharSet.kSubFieldIndicator
				+ "c"
				+ "Frank Broyles Williams Jr.";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedData, 1);
	}*/

	@Test
	public void withShortTitle() {
		title.setMasterTitle("M4M");
		String tag = MarcTags.kTitle;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setTitle(title);
		metaData.setAuthors(authors);
		String expectedData = "10"
				+ MarcCharSet.kSubFieldIndicator
				+ "a"
				+ "M4M"
				+ " /"
				+ MarcCharSet.kSubFieldIndicator
				+ "c"
				+ "Napoleon A Okafor, Joseph Buchanan Stephen, Marylee T Coady-Leeper, Kenneth M White.";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedData, 1);
	}

	@Test
	public void shortTitleWithDegree2Value() {
		title.setMasterTitle("L'");
		String tag = MarcTags.kTitle;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setTitle(title);
		metaData.setAuthors(authors);
		String expectedData = "12"
				+ MarcCharSet.kSubFieldIndicator
				+ "a"
				+ "L'"
				+ " /"
				+ MarcCharSet.kSubFieldIndicator
				+ "c"
				+ "Napoleon A Okafor, Joseph Buchanan Stephen, Marylee T Coady-Leeper, Kenneth M White.";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedData, 1);
	}

	@Test
	public void shortTitleWithSpecialChar() {
		title.setMasterTitle("@#");
		String tag = MarcTags.kTitle;
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setTitle(title);
		metaData.setAuthors(authors);
		String expectedData = "10"
				+ MarcCharSet.kSubFieldIndicator
				+ "a"
				+ " #"
				+ " /"
				+ MarcCharSet.kSubFieldIndicator
				+ "c"
				+ "Napoleon A Okafor, Joseph Buchanan Stephen, Marylee T Coady-Leeper, Kenneth M White.";
		verifyMarcRecordHasCorrectField(metaData, tag, expectedData, 1);
	}
}

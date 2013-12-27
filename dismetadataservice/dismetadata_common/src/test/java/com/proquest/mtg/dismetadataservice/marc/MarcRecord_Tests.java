package com.proquest.mtg.dismetadataservice.marc;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class MarcRecord_Tests {
	
	char status;           
	char type;             
	char level;
	MarcField fieldId;
	MarcField fieldAuthor;
	MarcField fieldTitle;
	MarcField fieldAbstract1;
	MarcField fieldAbstract2;
	MarcField fieldAbstract3;
	MarcField fieldUrl;
	List<MarcField> fields;
	MarcRecord target;
	
	@Before
	public void setUp() throws Exception {
		status = 'n';
		type = 'a';
		level = 'm';
		
		fieldId = new MarcField(MarcTags.kRecId, "AAIU441720");
		fieldAuthor = new MarcField(MarcTags.kAuthor, "Wilcox, B.H.");
		fieldTitle = new MarcField(MarcTags.kTitle, "A systematic study of the Leucanthemum-Chrysanthemum complex in North Africa.");
		fieldAbstract1 = new MarcField(MarcTags.kAbstract, "Abstract not available.");
		fieldAbstract2 = new MarcField(MarcTags.kAbstract, "Abstract still not available.");
		fieldAbstract3 = new MarcField(MarcTags.kAbstract, "Can't find the abstract.");
		fieldUrl = new MarcField(MarcTags.kUrl, "http://mystuff.com/host?id:441720");fieldUrl = new MarcField(MarcTags.kUrl, "http://mystuff.com/host?id:441720");
		
		fields = Lists.newArrayList(
				fieldId,
				fieldAuthor,
				fieldTitle,
				fieldAbstract1,
				fieldAbstract2,
				fieldAbstract3,
				fieldUrl);
		
		target = new MarcRecord(status, type, level, fields);
	}
	
	@Test
	public void hasCorrect_Status() throws Exception {
		assertThat(target.getStatus(), is(status));
	}
	
	@Test
	public void hasCorrect_Type() throws Exception {
		assertThat(target.getType(), is(type));
	}
	
	@Test
	public void hasCorrect_Level() throws Exception {
		assertThat(target.getLevel(), is(level));
	}
	
	@Test
	public void hasCorrect_FieldCount() throws Exception {
		assertThat(target.getFieldCount(), is(fields.size()));
	}
	
	@Test
	public void hasCorrect_Fields() throws Exception {
		assertThat(target.getAllFields(), is(fields));
	}
	
	@Test
	public void makesCopyOfFields() throws Exception {
		assertThat(target.getAllFields(), not(sameInstance(fields)));
	}
	
	@Test
	public void getFieldsWithTag_SingleMatch() throws Exception {
		List<MarcField> matchingFields = Lists.newArrayList(fieldAuthor);
		
		assertThat(target.getFieldsMatchingTag(MarcTags.kAuthor), is(matchingFields));
	}
	
	@Test
	public void getFieldsWithTag_MultiMatch() throws Exception {
		List<MarcField> matchingFields = Lists.newArrayList(
				fieldAbstract1,
				fieldAbstract2,
				fieldAbstract3);
		
		assertThat(target.getFieldsMatchingTag(MarcTags.kAbstract), is(matchingFields));
	}
	
	@Test
	public void getFieldsWithTag_NoMatch() throws Exception {
		List<MarcField> matchingFields = Lists.newArrayList();
		
		assertThat(target.getFieldsMatchingTag(MarcTags.kEnglishTranslationOfTitle), is(matchingFields));
	}
	
	@Test
	public void getFirstFieldWithTag_NoMatch() throws Exception {
		assertThat(target.getFirstFieldMatchingTag(MarcTags.kEnglishTranslationOfTitle), nullValue());
	}
	
	@Test
	public void getFirstFieldWithTag() throws Exception {
		assertThat(target.getFirstFieldMatchingTag(MarcTags.kAbstract), is(fieldAbstract1));
	}
	
	@Test
	public void removeFields_NoMatch() throws Exception {
		target.removeFieldsWithTag(MarcTags.kHostItemEntry);
		
		assertThat(target.getAllFields(), is(fields));
	}
	
	@Test
	public void removeFields_SingleMatch() throws Exception {
		List<MarcField> fieldsWithoutUrl = fields;
		fieldsWithoutUrl.remove(fieldUrl);
		
		target.removeFieldsWithTag(MarcTags.kUrl);
		
		assertThat(target.getAllFields(), is(fieldsWithoutUrl));
	}
	
	@Test
	public void removeFields_MultieMatch() throws Exception {
		List<MarcField> fieldsWithoutAbstract = fields;
		fieldsWithoutAbstract.remove(fieldAbstract1);
		fieldsWithoutAbstract.remove(fieldAbstract2);
		fieldsWithoutAbstract.remove(fieldAbstract3);
		
		target.removeFieldsWithTag(MarcTags.kAbstract);
		
		assertThat(target.getAllFields(), is(fieldsWithoutAbstract));
	}
	
	@Test
	public void hasCorrect_tags() throws Exception {
		List<String> expectedTags = Lists.newArrayList(
				MarcTags.kRecId, 
				MarcTags.kAuthor, 
				MarcTags.kTitle, 
				MarcTags.kAbstract, 
				MarcTags.kAbstract, 
				MarcTags.kAbstract, 
				MarcTags.kUrl);
		
		assertThat(target.getAllTags(), is(expectedTags));
	}
	
	@Test
	public void canAddField() throws Exception {
		MarcField newField = new MarcField(MarcTags.kDegreeName, "Foo");
		target.addField(newField);
		
		fields.add(newField);
		
		for (MarcField expectedField : fields) {
			assertThat(target.getAllFields(), hasItem(expectedField));
		}
	}
	
	@Test
	public void canAddSeveralFields() throws Exception {
		MarcField newField1 = new MarcField(MarcTags.kDegreeName, "Foo");
		MarcField newField2 = new MarcField(MarcTags.kSubjectTerm, "Bar");
		fields.add(newField1);
		fields.add(newField2);
		
		target.addFields(Lists.newArrayList(newField1, newField2));
		
		for (MarcField expectedField : fields) {
			assertThat(target.getAllFields(), hasItem(expectedField));
		}
	}
	
	@Test
	public void sortsTags_WhenFieldsAddedOutOfOrder() throws Exception {
		List<MarcField> fieldsOutOfOrder = Lists.newArrayList(
				fieldAbstract1,
				fieldAuthor,				
				fieldAbstract2,
				fieldTitle,
				fieldAbstract3,
				fieldUrl,
				fieldId);
		
		target = new MarcRecord(fieldsOutOfOrder);
		
		List<String> expectedTags = Lists.newArrayList(
				MarcTags.kRecId, 
				MarcTags.kAuthor, 
				MarcTags.kTitle, 
				MarcTags.kAbstract, 
				MarcTags.kAbstract, 
				MarcTags.kAbstract, 
				MarcTags.kUrl);
		
		assertThat(target.getAllTags(), is(expectedTags));
	}
	
	@Test
	public void sortsFields_WhenFieldsAddedOutOfOrder() throws Exception {
		List<MarcField> fieldsOutOfOrder = Lists.newArrayList(
				fieldAbstract1,
				fieldAuthor,				
				fieldAbstract2,
				fieldTitle,
				fieldAbstract3,
				fieldUrl,
				fieldId);
		
		target = new MarcRecord(fieldsOutOfOrder);
		
		assertThat(target.getAllFields(), is(fields));
	}
	
	@Test
	public void getPubId_WhenDoesntExist_ReturnsNull() throws Exception {
		MarcRecord target = new MarcRecord();
		assertThat(target.getPubId(), nullValue());
	}
	
	@Test
	public void getPubId() throws Exception {
		assertThat(target.getPubId(), is("U441720"));
	}
	

}

package com.proquest.mtg.dismetadataservice.marc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class MarcField_Tests {
	
	String tag;
	String fieldData;
	MarcField target;
	
	@Before
	public void setUp() throws Exception {
		tag = MarcTags.kAbstract;
		fieldData = "Best dissertation ever!";
		target = new MarcField(tag, fieldData);
	}
	
	@Test
	public void hasCorrect_Tag() throws Exception {
		assertThat(target.getTag(), is(tag));
	}
	
	@Test
	public void hasCorrect_Data() throws Exception {
		assertThat(target.getData(), is(fieldData));
	}
	
	@Test
	public void hasCorrect_DataWithTerminator() throws Exception {
		assertThat(target.getDataWithTerminator(), is(fieldData + MarcField.kFieldTerminatorStr));
	}
	
	@Test
	public void hasCorrect_FieldDataWhenConstructedWithTerminator() throws Exception {
		String fieldDataWithTerminator = fieldData + MarcField.kFieldTerminatorStr;
		target = new MarcField(tag, fieldDataWithTerminator);
		
		assertThat(target.getData(), is(fieldData));
		assertThat(target.getDataWithTerminator(), is(fieldDataWithTerminator));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructWithInvalidTag_TooLong_Throws() throws Exception {
		String invalidTag = "1234";
		target = new MarcField(invalidTag, fieldData);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructWithInvalidTag_TooShort_Throws() throws Exception {
		String invalidTag = "12";
		target = new MarcField(invalidTag, fieldData);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructWithNullTag_Throws() throws Exception {
		target = new MarcField(null, fieldData);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructWithFieldDataTag_Throws() throws Exception {
		target = new MarcField(tag, null);
	}
	
	@Test
	public void coverageTests() throws Exception {
		target = new MarcField(tag, fieldData);
		assertThat(target.toString(), notNullValue());
		assertThat(target.equals(target), is(true));
	}
	
	@Test
	public void canSetTag() throws Exception {
		tag = "111";
		target.setTag(tag);
		assertThat(target.getTag(), is(tag));
		assertThat(target.getData(), is(fieldData));
	}
	
	@Test
	public void canSetFieldData() throws Exception {
		fieldData = "Foo";
		target.setData(fieldData);
		assertThat(target.getTag(), is(tag));
		assertThat(target.getData(), is(fieldData));
	}

}

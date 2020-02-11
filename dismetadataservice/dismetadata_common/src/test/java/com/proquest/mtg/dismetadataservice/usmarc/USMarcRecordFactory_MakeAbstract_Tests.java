package com.proquest.mtg.dismetadataservice.usmarc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.proquest.mtg.dismetadataservice.datasource.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.marc.MarcCharSet;
import com.proquest.mtg.dismetadataservice.marc.MarcField;
import com.proquest.mtg.dismetadataservice.marc.MarcRecord;
import com.proquest.mtg.dismetadataservice.marc.MarcTags;

public class USMarcRecordFactory_MakeAbstract_Tests extends USMarcRecordFactoryBase_Test_Helper {
	
	@Test
	public void withOnly_Abstract_WithPlainText() throws Exception {
		
		String tag = MarcTags.kAbstract;
		String abstractText = "This is my research";
		String expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "a" + abstractText + ".";
		
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setAbstract(abstractText);
		
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 1);
	}
	
	@Test
	public void withOnly_Abstract_WithCarrots() throws Exception {
		
		String abstractText = "This.^ Is my^Research?^";
		String expectedMarcFieldData1 = "  " + MarcCharSet.kSubFieldIndicator + "a" + "This.";
		String expectedMarcFieldData2 = "  " + MarcCharSet.kSubFieldIndicator + "a" + "Is my.";
		String expectedMarcFieldData3 = "  " + MarcCharSet.kSubFieldIndicator + "a" + "Research?";
		
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setAbstract(abstractText);
		
		MarcRecord marc = factory.makeFrom(metaData);
		
		assertThat(marc.getFieldCount(), is(3 + kDataIndependentFieldCount));
		
		List<MarcField> subjectTermFields = marc.getFieldsMatchingTag(MarcTags.kAbstract); 
		assertThat(subjectTermFields.size(), is(3));
		assertThat(subjectTermFields.get(0).getData(), is(expectedMarcFieldData1));
		assertThat(subjectTermFields.get(1).getData(), is(expectedMarcFieldData2));
		assertThat(subjectTermFields.get(2).getData(), is(expectedMarcFieldData3));
	}
	
	@Test
	public void withOnly_Abstract_WithLineSeparators() throws Exception {
		
		String abstractText = "This.\n Is my\nResearch!\n";
		String expectedMarcFieldData1 = "  " + MarcCharSet.kSubFieldIndicator + "a" + "This.";
		String expectedMarcFieldData2 = "  " + MarcCharSet.kSubFieldIndicator + "a" + "Is my.";
		String expectedMarcFieldData3 = "  " + MarcCharSet.kSubFieldIndicator + "a" + "Research!";
		
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setAbstract(abstractText);
		
		MarcRecord marc = factory.makeFrom(metaData);
		
		assertThat(marc.getFieldCount(), is(3 + kDataIndependentFieldCount));
		
		List<MarcField> subjectTermFields = marc.getFieldsMatchingTag(MarcTags.kAbstract); 
		assertThat(subjectTermFields.size(), is(3));
		assertThat(subjectTermFields.get(0).getData(), is(expectedMarcFieldData1));
		assertThat(subjectTermFields.get(1).getData(), is(expectedMarcFieldData2));
		assertThat(subjectTermFields.get(2).getData(), is(expectedMarcFieldData3));
	}
	
	@Test
	public void withOnly_Abstract_WithParagraphTags_p() throws Exception {
		
		String abstractText = "<p>This.</p><p>Is my<p>Research!</p>";
		String expectedMarcFieldData1 = "  " + MarcCharSet.kSubFieldIndicator + "a" + "This.";
		String expectedMarcFieldData2 = "  " + MarcCharSet.kSubFieldIndicator + "a" + "Is my.";
		String expectedMarcFieldData3 = "  " + MarcCharSet.kSubFieldIndicator + "a" + "Research!";
		
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setAbstract(abstractText);
		
		MarcRecord marc = factory.makeFrom(metaData);
		
		assertThat(marc.getFieldCount(), is(3 + kDataIndependentFieldCount));
		
		List<MarcField> subjectTermFields = marc.getFieldsMatchingTag(MarcTags.kAbstract); 
		assertThat(subjectTermFields.size(), is(3));
		assertThat(subjectTermFields.get(0).getData(), is(expectedMarcFieldData1));
		assertThat(subjectTermFields.get(1).getData(), is(expectedMarcFieldData2));
		assertThat(subjectTermFields.get(2).getData(), is(expectedMarcFieldData3));
	}
	
	@Test
	public void withOnly_Abstract_WithParagraphTags_par() throws Exception {
		
		String abstractText = "<par>This.<par>Is my</par><par>Research!</par><par>";
		String expectedMarcFieldData1 = "  " + MarcCharSet.kSubFieldIndicator + "a" + "This.";
		String expectedMarcFieldData2 = "  " + MarcCharSet.kSubFieldIndicator + "a" + "Is my.";
		String expectedMarcFieldData3 = "  " + MarcCharSet.kSubFieldIndicator + "a" + "Research!";
		
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setAbstract(abstractText);
		
		MarcRecord marc = factory.makeFrom(metaData);
		
		assertThat(marc.getFieldCount(), is(3 + kDataIndependentFieldCount));
		
		List<MarcField> subjectTermFields = marc.getFieldsMatchingTag(MarcTags.kAbstract); 
		assertThat(subjectTermFields.size(), is(3));
		assertThat(subjectTermFields.get(0).getData(), is(expectedMarcFieldData1));
		assertThat(subjectTermFields.get(1).getData(), is(expectedMarcFieldData2));
		assertThat(subjectTermFields.get(2).getData(), is(expectedMarcFieldData3));
	}
	
	@Test
	public void withOnly_Abstract_WithLessThanAndGreaterThanSgmlEntityTags() throws Exception {
		
		String abstractText = "&lt;p&gt;This.&lt;/p&gt;&lt;p&gt;Is my&lt;p&gt;Research!&lt;/p&gt;";
		String expectedMarcFieldData1 = "  " + MarcCharSet.kSubFieldIndicator + "a" + "This.";
		String expectedMarcFieldData2 = "  " + MarcCharSet.kSubFieldIndicator + "a" + "Is my.";
		String expectedMarcFieldData3 = "  " + MarcCharSet.kSubFieldIndicator + "a" + "Research!";
		
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setAbstract(abstractText);
		
		MarcRecord marc = factory.makeFrom(metaData);
		
		assertThat(marc.getFieldCount(), is(3 + kDataIndependentFieldCount));
		
		List<MarcField> subjectTermFields = marc.getFieldsMatchingTag(MarcTags.kAbstract); 
		assertThat(subjectTermFields.size(), is(3));
		assertThat(subjectTermFields.get(0).getData(), is(expectedMarcFieldData1));
		assertThat(subjectTermFields.get(1).getData(), is(expectedMarcFieldData2));
		assertThat(subjectTermFields.get(2).getData(), is(expectedMarcFieldData3));
	}
	
	@Test
	public void withOnly_Abstract_WithAdeptTag() throws Exception {
		String tag = MarcTags.kAbstract;
		String abstractText = "<?Pub Inc> This is my research";
		String expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "a" + "This is my research" + ".";
		
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setAbstract(abstractText);
		
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 1);
	}
	
	@Test
	public void withOnly_CDATA_WithAdeptTag() throws Exception {
		String tag = MarcTags.kAbstract;
		String abstractText = "<![CDATA[This is my research]]>";
		String expectedMarcFieldData = "  " + MarcCharSet.kSubFieldIndicator + "a" + "This is my research" + ".";
		
		DisPubMetaData metaData = new DisPubMetaData();
		metaData.setAbstract(abstractText);
		
		verifyMarcRecordHasCorrectField(metaData, tag, expectedMarcFieldData, 1);
	}

}

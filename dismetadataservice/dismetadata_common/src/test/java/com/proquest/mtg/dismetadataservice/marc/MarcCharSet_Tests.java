package com.proquest.mtg.dismetadataservice.marc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class MarcCharSet_Tests {
	
	private static final char illegalChar1 = (char)0x00;
	private static final char illegalChar2 = (char)0x1a;
	private static final char illegalChar3 = (char)0x99;
	
	private String concatinateAllLegalChars() {
		StringBuilder result = new StringBuilder();
		for (Character c : MarcCharSet.kAllChars) {
			result.append(c);
		}
		return result.toString();
	}

	@Test
	public void emptyString_IsLegal() throws Exception {
		String source = new String();	
		assertThat(MarcCharSet.kCharMatcher.matchesAllOf(source), is(true));
	}
	
	@Test
	public void allSpaces_IsLegal() throws Exception {
		String source = "   ";	
		assertThat(MarcCharSet.kCharMatcher.matchesAllOf(source), is(true));
	}
	
	@Test
	public void concatinateAllLegalChars_IsLegal() throws Exception {
		String source = concatinateAllLegalChars();
		assertThat(MarcCharSet.kCharMatcher.matchesAllOf(source), is(true));
	}
	
	@Test
	public void singleIllegalChar_1() throws Exception {
		String source = Character.toString(illegalChar1);
		assertThat(MarcCharSet.kCharMatcher.matchesAllOf(source), is(false));
	}
	
	@Test
	public void singleIllegalChar_2() throws Exception {
		String source = Character.toString(illegalChar2);
		assertThat(MarcCharSet.kCharMatcher.matchesAllOf(source), is(false));
	}
	
	@Test
	public void singleIllegalChar_3() throws Exception {
		String source = Character.toString(illegalChar3);
		assertThat(MarcCharSet.kCharMatcher.matchesAllOf(source), is(false));
	}
	
	@Test
	public void acceptance_Legal() throws Exception {
		String source = "The quick Red Fox jump3d @ver the lazy brown dog.";
		assertThat(MarcCharSet.kCharMatcher.matchesAllOf(source), is(true));
	}
	
	@Test
	public void acceptance_Illegal() throws Exception {
		String source = 
				"The quick Red Fox" + 
				Character.toString(illegalChar1)
				+ "jump3d @ver the" + 
				Character.toString(illegalChar3)
				+ " lazy brown dog.";
		assertThat(MarcCharSet.kCharMatcher.matchesAllOf(source), is(false));
	}

}

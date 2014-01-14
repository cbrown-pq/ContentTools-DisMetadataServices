package com.proquest.mtg.dismetadataservice.metadata;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

public class PlainTextNormalizer_Tests extends EasyMockSupport {
	
	HTMLTagRemover htmlTagRemover;
	PlainTextNormalizer target;
	
	@Before
	public void Setup(){
		
		htmlTagRemover = EasyMock.createMock(HTMLTagRemover.class);
				
		target = new PlainTextNormalizer(htmlTagRemover);
	}
	
	@Test
	public void hasCorrect_HTMLTagRemover(){
		assertThat(target.getHTMLTagRemover(), sameInstance(htmlTagRemover));	
	}
	
	@Test
	public void applyTo() throws Exception {
		String source1 = new String();
		String source2 = new String();
		
		expect(htmlTagRemover.applyTo(source1)).andReturn(source2).once();
		
		replayAll();
		target.applyTo(source1);
		verifyAll();
	}

}

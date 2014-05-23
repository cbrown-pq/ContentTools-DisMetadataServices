package com.proquest.mtg.dismetadataservice.exodus;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.subjects.xml.Subjects.Subject.PQOnlineDescriptions;

public class SplitPQonlineSubjectsDescriptors_Tests {
	
	String subjectsDescriptorsXML = "";
	PQOnlineDescriptions pqOnlineDescriptions;
	
	@Before
	public void setUp() throws Exception {	
		pqOnlineDescriptions = new PQOnlineDescriptions();
	}
	
	@Test
	public void withEmptyPQOnlineDescriptions() {
		pqOnlineDescriptions = SplitPQOnlineSubjectsDescriptors.split("");
		assertEquals(pqOnlineDescriptions.getPQOnlineDescription().size(),0);
	}

	@Test
	public void hasCorrectPQOnlineDescriptions() {
		pqOnlineDescriptions = SplitPQOnlineSubjectsDescriptors.split("0722:Science;0723:Biomedical Engineering");
		assertEquals(pqOnlineDescriptions.getPQOnlineDescription().get(0),"0722:Science");
		assertEquals(pqOnlineDescriptions.getPQOnlineDescription().get(1),"0723:Biomedical Engineering");
	}

}

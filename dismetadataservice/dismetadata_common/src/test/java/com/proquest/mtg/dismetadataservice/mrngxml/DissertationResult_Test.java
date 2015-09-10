package com.proquest.mtg.dismetadataservice.mrngxml;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import com.google.common.collect.Lists;

public class DissertationResult_Test {

	List<Dissertation> target;
	Dissertation dis1, dis2, dis3;

	@Before
	public void setUp() throws Exception {
		FakeDissertationFactory factory = new FakeDissertationFactory();
		dis1 = factory.makeFor("Pub 1");
		dis2 = factory.makeFor("Pub 2");
		dis3 = factory.makeFor("Pub 3");
		target = Lists.newArrayList(dis1, dis2, dis3);

	}

	@Test
	public void hasCorrect_MetaData() throws Exception {
		assert (target.contains(dis1));
		assert (target.contains(dis3));
		assert (target.contains(dis2));
		assertThat(target.size(), is(3));
	}

	@Test
	public void hasCorrect_PubNumber() throws Exception {
		assertThat(dis1.getPubNumber(), is("Pub 1"));
		assertThat(dis2.getPubNumber(), is("Pub 2"));
		assertThat(dis3.getPubNumber(), is("Pub 3"));

	}
}

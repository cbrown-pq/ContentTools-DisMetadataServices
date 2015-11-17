package com.proquest.mtg.dismetadataservice.mrngxml;

import java.util.List;

import com.google.common.collect.ImmutableList;

public class CharSubstitution {
	
	private final String entity;
	private final String substitution;
	
	public CharSubstitution(String entity, String substitution) {
		this.entity = entity;
		this.substitution = substitution;
	}
	
	public String getEntity() {
		return entity;
	}
	
	public String getSubstitution() {
		return substitution;
	}
	
	public String applyTo(String x) {
		return x.replaceAll(getEntity(), getSubstitution());
	}
	
	public static final List<CharSubstitution> 
			kCharSubstitutionAll = ImmutableList.of(				
                new CharSubstitution("@\\?", ""),
                new CharSubstitution("\\*@", ""),
                new CharSubstitution("@\\*", ""),
                new CharSubstitution("-@", "-"),
                new CharSubstitution("@-", "-"),
                new CharSubstitution("@/", "/"),
                new CharSubstitution(" @", " "),
				new CharSubstitution("@", " ")
				);
	
	public static final List<CharSubstitution> 
	kCharSubstitution = ImmutableList.of(        
		new CharSubstitution("@", " ")
		);
	
	public static String applyAllTo(String x) {
		if (x == null) {
			return x;
		}
		for (CharSubstitution s : kCharSubstitutionAll) {
			x = s.applyTo(x);
		}
		return x;
	}
	public static String applyForAdvisor(String x) {
		if (x == null) {
			return x;
		}
		for (CharSubstitution s : kCharSubstitution) {
			x = s.applyTo(x);
		}
		return x;
	}
}                                           
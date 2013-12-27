package com.proquest.mtg.dismetadataservice.metadata;

import java.util.List;

import com.google.common.collect.ImmutableList;

public class SGMLAngluarBracesEntitySubstitution {
	private final String entity;
	private final String substitution;
	
	public SGMLAngluarBracesEntitySubstitution(String entity, String substitution) {
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
			
	public static final List<SGMLAngluarBracesEntitySubstitution> 
		kAngularBracketEntitySubstitution = ImmutableList.of(
			new SGMLAngluarBracesEntitySubstitution("&[lL][tT];", "<"),
			new SGMLAngluarBracesEntitySubstitution("&[gG][tT];", ">")
			);
	
	public static String applyAllTo(String x) {
		for (SGMLAngluarBracesEntitySubstitution s : kAngularBracketEntitySubstitution) {
			x = s.applyTo(x);
		}
		return x;
	}

}

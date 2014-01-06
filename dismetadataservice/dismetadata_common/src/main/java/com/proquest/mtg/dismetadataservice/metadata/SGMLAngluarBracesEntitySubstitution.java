package com.proquest.mtg.dismetadataservice.metadata;

import java.util.List;

import com.google.common.collect.ImmutableList;

public class SGMLAngluarBracesEntitySubstitution extends SGMLEntitySubstitution {

	
	public SGMLAngluarBracesEntitySubstitution(String entity, String substitution) {
		super(entity, substitution);
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

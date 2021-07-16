package com.proquest.mtg.dismetadataservice.marc;

import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;

public class MarcTags {
	public static final String kRecId = "001";
	public static final String kTransactionTimestamp = "005";
	public static final String kFiexedLengthDataElements = "008";
	public static final String kIsbn = "020";
	public static final String kSystemControlNumber = "035";
	public static final String kCatalogingSource = "040";
	public static final String kLanguageCode = "041";
	public static final String kAuthor = "100";
	public static final String kEnglishTranslationOfTitle = "242";
	public static final String kTitle = "245";
	public static final String kPublisher = "260";
	public static final String kPublication = "264";
	public static final String kPageCount = "300";
	public static final String kContentType = "336";
	public static final String kMediaType = "337";
	public static final String kCarrierType = "338";
	public static final String kGeneralNote = "500";
	public static final String kDissertationNote = "502";
	public static final String kAccessRestrictionNote = "506";
	public static final String kAbstract = "520";
	public static final String kLocationOfCopy = "535";
	public static final String kLanguageNote = "546";
	public static final String kLocalNoteSchoolCode = "590";
	public static final String kSubjectTerm = "650";
	public static final String kKeyword = "653";
	public static final String kSubjectCode = "690";
	public static final String kMulitpleAuthor = "700";
	public static final String kCorporatename = "710";
	public static final String kUncontrolledName = "720";
	public static final String kVariantTitle = "740";
	public static final String kHostItemEntry = "773";
	public static final String kLocalAddedEntry = "790";
	public static final String kDegreeName = "791";
	public static final String kDegreeDate = "792";
	public static final String kDissertationLanguage = "793";
	public static final String kUrl = "856";
	
	public static final Set<String> kAllTags = ImmutableSet.of(
			kRecId,
			kTransactionTimestamp,
			kFiexedLengthDataElements,
			kIsbn,
			kSystemControlNumber,
			kCatalogingSource,
			kLanguageCode,
			kAuthor,
			kEnglishTranslationOfTitle,
			kTitle,
			kPublisher,
			kPublication,
			kPageCount,
			kContentType,
			kGeneralNote,
			kDissertationNote,
			kAccessRestrictionNote,
			kAbstract,
			kLocationOfCopy,
			kLanguageNote,
			kLocalNoteSchoolCode,
			kSubjectTerm,
			kSubjectCode,
			kKeyword,
			kMulitpleAuthor,
			kCorporatename,
			kUncontrolledName,
			kVariantTitle,
			kHostItemEntry,
			kLocalAddedEntry,
			kDegreeName,
			kDegreeDate,
			kDissertationLanguage,
			kUrl);
	
	public static final Set<String> kRequiredTags = ImmutableSet.of(kRecId);
	
	public static class UnknownTag implements Predicate<String> {
		@Override
		public boolean apply(@Nullable String x) {
			return !kAllTags.contains(x);
		}
	}
			
	public static UnknownTag kUnknownTagPredicate = new UnknownTag();

}

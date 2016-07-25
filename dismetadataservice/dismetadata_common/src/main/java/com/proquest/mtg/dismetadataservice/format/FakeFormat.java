package com.proquest.mtg.dismetadataservice.format;

public class FakeFormat implements IMetaDataFormats {

	@Override
	public String makeFor(String pubNum, int excludeRestriction, int excludeAbstract) {
		return "00927nam  2200265   4500001001100000005001700011008004100028035002000069040001300089100002700102245004800129300001000177500008400187502005900271506001600330590002300346650002400369690000900393710003700402773004500439790000900484791001000493792000900503856014900512AAI016758320130626150504.5130626s1944    ||||||||||||||||| ||eng d  a(UMI)AAI0167583  aUMIcUMI1 aMURPHY, THOMAS JOSEPH.10aHISTORY OF CATHOLIC EDUCATION IN CLEVELAND.  a93 p.  aSource: American Doctoral Dissertations, Volume: 19-44, Section: C, page: 0063.  aThesis (Ph.D.)--Case Western Reserve University, 1944.  aDo not Sell  aSchool code: 0042. 4aEducation, General.  a051520aCase Western Reserve University.0 tAmerican Doctoral Dissertationsg19-44C.  a0042  aPh.D.  a1944  uhttp://gateway.proquest.com/openurl?url_ver=Z39.88-2004&rft_val_fmt=info:ofi/fmt:kev:mtx:dissertation&res_dat=xri:pqm&rft_dat=xri:pqdiss:0167583";
	}

}

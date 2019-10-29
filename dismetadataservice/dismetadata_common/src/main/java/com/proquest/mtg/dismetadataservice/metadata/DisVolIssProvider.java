package com.proquest.mtg.dismetadataservice.metadata;

//import java.time.LocalDate;
//import java.time.Month;
//import java.time.Period;

// This class is to temporarily generate the Vol/Iss field for Metadata output.
// It should be replaced with data from ECMS at some point.
public class DisVolIssProvider {
	public static int dissBaseDate = 1938;
	
	public static String DisVolIssProvider(Integer pubYear,String pubMonth) {
	int VolIss = pubYear - dissBaseDate;
	String dissVolIss = String.format("%02d", VolIss);
	//System.out.println("DISS ISS:" +dissVolIss);
	 
	//String dissVolIss = String.valueOf(VolIss);
	//dissVolIss = String.format("%02s", dissVolIss);

	if (pubMonth.equals("JULY")) {
		dissVolIss = (dissVolIss + '-' + "01");
	}
	if (pubMonth.equals("AUGUST")) {
		dissVolIss = (dissVolIss + '-' + "02");
	}
	if (pubMonth.equals("SEPTEMBER")) {
		dissVolIss = (dissVolIss + '-' + "03");
	}
	if (pubMonth.equals("OCTOBER")) {
		dissVolIss = (dissVolIss + '-' + "04");
	}
	if (pubMonth.equals("NOVEMBER")) {
		dissVolIss = (dissVolIss + '-' + "05");
	}
	if (pubMonth.equals("DECEMBER")) {
		dissVolIss = (dissVolIss + '-' + "06");
	}
	if (pubMonth.equals("JANUARY")) {
		VolIss = VolIss -1;
		dissVolIss = String.format("%02d", VolIss);
		//dissVolIss = String.valueOf(VolIss);
		dissVolIss = (dissVolIss + '-' + "07");
	}
	if (pubMonth.equals("FEBRUARY")) {
		VolIss = VolIss -1;
		dissVolIss = String.format("%02d", VolIss);
		//dissVolIss = String.valueOf(VolIss);
		dissVolIss = (dissVolIss + '-' + "08");
	}
	if (pubMonth.equals("MARCH")) {
		VolIss = VolIss -1;
		dissVolIss = String.format("%02d", VolIss);
		//dissVolIss = String.valueOf(VolIss);
		dissVolIss = (dissVolIss + '-' + "09");
	}
	if (pubMonth.equals("APRIL")) {
		VolIss = VolIss -1;
		dissVolIss = String.format("%02d", VolIss);
		//dissVolIss = String.valueOf(VolIss);
		dissVolIss = (dissVolIss + '-' + "10");
	}
	if (pubMonth.equals("MAY")) {
		VolIss = VolIss -1;
		dissVolIss = String.format("%02d", VolIss);
		//dissVolIss = String.valueOf(VolIss);
		dissVolIss = (dissVolIss + '-' + "11");
	}
	if (pubMonth.equals("JUNE")) {
		VolIss = VolIss -1;
		dissVolIss = String.format("%02d", VolIss);
		//dissVolIss = String.valueOf(VolIss);
		dissVolIss = (dissVolIss + '-' + "12");
	}
	// If January - June, Subtract 1 from year
	// Issue is 1 - 12 starting with July as 1.
	return dissVolIss;
	}

}

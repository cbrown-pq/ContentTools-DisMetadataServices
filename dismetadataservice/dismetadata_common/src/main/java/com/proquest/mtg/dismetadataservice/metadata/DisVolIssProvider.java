package com.proquest.mtg.dismetadataservice.metadata;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;

// This class is to temporarily generate the Vol/Iss field for Metadata output.
// It should be replaced with data from ECMS at some point.
public class DisVolIssProvider {
	
	public static String DisVolIssProvider(Integer pubYear,String pubMonth) {
		// Today's date needs to be the publication date, which will be sent in
	LocalDate initialDate = LocalDate.of(1938,Month.JANUARY,1);                          //Today's date
	// Period required the month and day, but it will not be used as part of the calculation, except as required by Period.
	LocalDate creationDate = LocalDate.of(pubYear, Month.valueOf(pubMonth), 1);  //Birth date
	
	Period p = Period.between(initialDate, creationDate);
	 
	//Now access the values as below
	//System.out.println("Days :" +p.getDays());
	//System.out.println("Months :" +p.getMonths());
	//System.out.println("Years :" +p.getYears());
	int VolIss = p.getYears();
	String dissVolIss = String.valueOf(VolIss);
	//System.out.println("STRING YEARS :" +dissVolIss);
	// Since this is temporary, I did not try to make it efficient or pretty.
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
		dissVolIss = String.valueOf(VolIss);
		dissVolIss = (dissVolIss + '-' + "07");
	}
	if (pubMonth.equals("FEBRUARY")) {
		VolIss = VolIss -1;
		dissVolIss = String.valueOf(VolIss);
		dissVolIss = (dissVolIss + '-' + "08");
	}
	if (pubMonth.equals("MARCH")) {
		VolIss = VolIss -1;
		dissVolIss = String.valueOf(VolIss);
		dissVolIss = (dissVolIss + '-' + "09");
	}
	if (pubMonth.equals("APRIL")) {
		VolIss = VolIss -1;
		dissVolIss = String.valueOf(VolIss);
		dissVolIss = (dissVolIss + '-' + "10");
	}
	if (pubMonth.equals("MAY")) {
		VolIss = VolIss -1;
		dissVolIss = String.valueOf(VolIss);
		dissVolIss = (dissVolIss + '-' + "11");
	}
	if (pubMonth.equals("JUNE")) {
		VolIss = VolIss -1;
		dissVolIss = String.valueOf(VolIss);
		dissVolIss = (dissVolIss + '-' + "12");
	}
	// If January - June, Subtract 1 from year
	// Issue is 1 - 12 starting with July as 1.
	return dissVolIss;
	}

}

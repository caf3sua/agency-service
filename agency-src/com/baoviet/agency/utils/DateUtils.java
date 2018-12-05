package com.baoviet.agency.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

public class DateUtils {

	public static int countYears(Date birthDate, Date startDate) {
		// Check Birthday date must be earlier than current date
		if (birthDate.after(startDate)) {
			return -1;
		}

		// (DateTime Bday, DateTime Cday)
		Calendar calBirth = Calendar.getInstance();
		calBirth.setTime(birthDate);
		Calendar calStart = Calendar.getInstance();
		calStart.setTime(startDate);

		int birthYear = calBirth.get(Calendar.YEAR);
		int birthMonth = calBirth.get(Calendar.MONTH); // start from 0
		int birthDay = calBirth.get(Calendar.DAY_OF_MONTH);

		int startYear = calStart.get(Calendar.YEAR);
		int startMonth = calStart.get(Calendar.MONTH);
		int startDay = calStart.get(Calendar.DAY_OF_MONTH);

		if ((startYear - birthYear) > 0 || (((startYear - birthYear) == 0)
				&& ((birthMonth < startMonth) || ((birthMonth == startMonth) && (birthDay <= startDay))))) {
			if (startMonth > birthMonth) {
				return startYear - birthYear;
			} else if (startMonth == birthMonth) {
				if (startDay >= birthDay) {
					return startYear - birthYear;
				} else {
					return (startYear - 1) - birthYear;
				}
			} else {
				return (startYear - 1) - birthYear;
			}
		}

		return -1;
	}
	
	public static Date str2Date(String strDate) {
	    try {
			return new SimpleDateFormat("dd/MM/yyyy").parse(strDate);
		} catch (Exception e) {
			return null;
		}  
	}
	
	public static boolean validDateFormat(String strDate) {
	    try {
			SimpleDateFormat format =  new SimpleDateFormat("dd/MM/yyyy");
			format.setLenient(false);
			format.parse(strDate);
			return true;
		} catch (ParseException e) {
			return false;
		}  
	}
	
	public static Date str2Date(String strDate, String format) {
	    try {
			return new SimpleDateFormat(format).parse(strDate);
		} catch (ParseException e) {
			return null;
		}  
	}
	
	public static String date2Str(Date date) {
		if (date == null) {
			return null;
		}
		
	    // Create an instance of SimpleDateFormat used for formatting 
		// the string representation of date (month/day/year)
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		// representation of a date with the defined format.
		return df.format(date);  
	}
	
	public static String date2StrHHMMSS(Date date) {
		if (date == null) {
			return null;
		}
		
	    // Create an instance of SimpleDateFormat used for formatting 
		// the string representation of date (month/day/year)
		DateFormat df = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

		// representation of a date with the defined format.
		return df.format(date);  
	}
	
	public static String date2Str(Date date, String format) {
	    // Create an instance of SimpleDateFormat used for formatting 
		// the string representation of date (month/day/year)
		DateFormat df = new SimpleDateFormat(format);

		// representation of a date with the defined format.
		return df.format(date);  
	}
	
	public static int getNumberDaysBetween2DateStr(String firstDate, String secondDate) {
    	DateTimeFormatter dateStringFormat = DateTimeFormat.forPattern("dd/MM/yyyy");
        DateTime firstTime = dateStringFormat.parseDateTime(firstDate);
        DateTime secondTime = dateStringFormat.parseDateTime(secondDate);
        int days = Days.daysBetween(new LocalDate(firstTime),
                                    new LocalDate(secondTime)).getDays();
        return days;
	}
	
	public static int getNumberDaysBetween2Date(Date firstDate, Date secondDate) {
        int days = Days.daysBetween(new LocalDate(firstDate),
                                    new LocalDate(secondDate)).getDays();
        return days;
	}
	
	public static int getNumberMonthsBetween2DateStr(String firstDate, String secondDate) {
    	DateTimeFormatter dateStringFormat = DateTimeFormat.forPattern("dd/MM/yyyy");
        DateTime firstTime = dateStringFormat.parseDateTime(firstDate);
        DateTime secondTime = dateStringFormat.parseDateTime(secondDate);
        int months = Months.monthsBetween(new LocalDate(firstTime),
                                    new LocalDate(secondTime)).getMonths();
        return months;
	}
	
	public static int getNumberMonthsBetween2Date(Date firstDate, Date secondDate) {
        int months = Months.monthsBetween(new LocalDate(firstDate),
                                    new LocalDate(secondDate)).getMonths();
        return months;
	}
	
	public static boolean isValidDate(String date, String format) {
		try {
			Date dateObj = new SimpleDateFormat(format).parse(date);
			if (dateObj != null) {
				return true;
			}
		} catch (ParseException e) {
			return false;
		}
		
		return false;
	}
	
	public static String getCurrentYear() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		
		return String.valueOf(year);
	}
	
	public static String getCurrentYear2Digits() {
		return getCurrentYear().substring(2);
	}
	
	public static Date getCurrentYearPrevious() {	
		Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1); // to get previous year add 1
        cal.add(Calendar.DAY_OF_MONTH, -1); // to get previous day add -1
        Date expiryDate = cal.getTime();
		return expiryDate;
		
	}
	
	public static String getCurrentYearAfter1Day() {	
		Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        Date expiryDate = cal.getTime();
		return date2Str(expiryDate);
		
	}
	
	public static Date getCurrentYearPrevious(Date date,Integer day) {	
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
        cal.add(Calendar.YEAR, 1); // to get previous year add 1
        cal.add(Calendar.DAY_OF_MONTH, day); // to get previous day add -1
        Date expiryDate = cal.getTime();
		return expiryDate;
		
	}
	
	public static Date addYear(Date date,Integer year) {	
		Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, year); // to get previous year add 1
		return cal.getTime();
	}
	
	public static Date addMonth(Date date,Integer month) {	
		Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, month); // to get previous year add 1
		return cal.getTime();
		
	}
	
	public static Date addDay(Date date,Integer day) {	
		Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, day); // to get previous year add 1
		return cal.getTime();
		
	}
	
	public static Date getMondayOfCurrentWeek() {
		java.time.LocalDate today = java.time.LocalDate.now();

		java.time.LocalDate monday = today.with(previousOrSame(MONDAY));
		
		return Date.from(monday.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public static Date getSundayOfCurrentWeek() {
		java.time.LocalDate today = java.time.LocalDate.now();

		java.time.LocalDate sunday = today.with(nextOrSame(SUNDAY));
		
		return Date.from(sunday.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public static Date getFirstDateOfCurrentMonth() {
		Calendar c = Calendar.getInstance();   // this takes current date
	    c.set(Calendar.DAY_OF_MONTH, 1);
	    return c.getTime();  
	}
	
	public static Date getLastDateOfCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
	    int lastDate = calendar.getActualMaximum(Calendar.DATE);

	    calendar.set(Calendar.DATE, lastDate);
//	    int lastDay = calendar.get(Calendar.DAY_OF_WEEK);
	    return calendar.getTime(); 
	}
	
	public static Date getFirstDateOfCurrentYear() {
		java.time.LocalDate today = java.time.LocalDate.now();
		java.time.LocalDate firstDay = today.with(firstDayOfYear());
		
		return Date.from(firstDay.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public static Date getLastDateOfCurrentYear() {
		java.time.LocalDate today = java.time.LocalDate.now();
		java.time.LocalDate lastDay  = today.with(lastDayOfYear());
		
		return Date.from(lastDay .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public static int getMonthFromDate(String dateStr) {
		java.util.Date date = null;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
		} catch (ParseException e) {
			return 0;
		}  
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		return month;		
	}
}

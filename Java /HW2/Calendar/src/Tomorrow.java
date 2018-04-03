/**
 * Use a calendar object to figure out about tomorrow 
 * and print out the date and day of the week.
 */

import java.util.*;

public class Tomorrow {

    /**
     *  define an array of strings that can translate between integer
     *  DAY_OF_WEEK and strings. Sunday is the first day of the week for
     *  the GregorianCalendar (although this can be changed).
     *  Note: The DAY_OF_WEEK value used by Calendar objects start at 1, 
     * (and our array of strings starts at 0)!
     */
 
    final static String[] DAYS ={ "Sunday",
				  "Monday",
				  "Tuesday",
				  "Wednesday",
				  "Thursday",
				  "Friday",
				  "Saturday" };

    /**
     *  define an array of strings that can translate between integer
     *  MONTH and strings. According to a Calendar object, the first
     *  month (January) is month 0.
     */

    static final String[] MONTHS = { "January",
				     "February",
				     "March",
				     "April",
				     "May",
				     "June",
				     "July",
				     "August",
				     "September",
				     "October",
				     "November",
				     "December"};

    /**
     * main can handle a month day and year specified on the command
     * line (all as integers). If it find acceptable values onthe
     * command line this program uses today's date.
     *
     * main figures out the date for the following day, and prints it out.
     */

    public static void main(String []args) {
		// Create a calendar object holding today's date
		Calendar c = Calendar.getInstance();
		// if there are command line args, change the Calendar 
		// object to hold the date specified by the month,day and year
		try {
			int month,day,year;
			month = Integer.parseInt(args[0]);
			day = Integer.parseInt(args[1]);
			year = Integer.parseInt(args[2]);

			// January is month 0 !
			c.set(Calendar.MONTH,month-1);
			c.set(Calendar.YEAR,year);
			c.set(Calendar.DATE,day);
		} catch( Exception e) {
			// invalid or missing command line args, just
			// assume we should use the current month,year.
			// This is already in the Calendar object!
		} 

		// This version explicitly looks at the current date to
		// determine whether the following day is in a new month
		// and year. Look at BetterTomorrow.java for a better way to
		// do this (the Calendar object will take care of this for us!).

		// is this the last day of the month?
		if (c.get(Calendar.DAY_OF_MONTH)==daysInMonth(c)) {
			// yes - tomorrow is a new month
			// is tomorrow also a new year?
			if (c.get(Calendar.MONTH)==c.getActualMaximum(Calendar.MONTH)) {
				// yes - new year!
				// change the month to the minimum valid value.
				c.set(Calendar.MONTH,c.getActualMinimum(Calendar.MONTH));

				// change the year to be the current year plus 1
				c.set(Calendar.YEAR,c.get(Calendar.YEAR)+1);
			} else {
				// new month only - change to the month plus 1
				c.set(Calendar.MONTH,c.get(Calendar.MONTH)+1);
			}
			// the date needs to change to the smallest valid date
			// (should be 1!
			c.set(Calendar.DATE,c.getActualMinimum(Calendar.DATE));

		} else {
			// new day only (not a new month)
			c.set(Calendar.DATE,c.get(Calendar.DATE)+1);
		}

		// the Calendar object now has all the date fields set to
		// represent tomorrow. Print this day out.

		System.out.println("Tomorrow is " + 
				   dayName(c) + " " + 
				   monthName(c) + " " +
				   c.get(Calendar.DATE) + ", " +
				   c.get(Calendar.YEAR));
	}	

	/**
	 * daysInMonth looks up the number of days in the current month
	 * in a Calendar object. This takes care of leap year for us!
	 * @param c is the Calendar object holding the date we want to
	 *          find out about.
	 * @return the number of days in the month. 
	 */
	static int daysInMonth( Calendar c) {
//		System.out.println(c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return(c.getActualMaximum(Calendar.DAY_OF_MONTH));
	}


	/**
	 * dayName looks up the string version of the day of the week
	 * for the date in a Calendar object
	 * @param c the Calendar object holding the date
	 * @return an string holding the (english) day of the week
	 */

	static String dayName( Calendar c) {
		// Need to subtract 1, since the first DAY_OF_WEEK is 1 !
		return(DAYS[c.get(Calendar.DAY_OF_WEEK)-1]);
	}	

	/**
	 * monthName looks up the string version of the month
	 * for the date in a Calendar object
	 * @param c the Calendar object holding the date
	 * @return an string holding the (english) month
	 */

	static String monthName(Calendar c) {
		return(MONTHS[c.get(Calendar.MONTH)]);
    }
  

}


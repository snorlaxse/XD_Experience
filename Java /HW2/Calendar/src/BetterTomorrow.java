/**
 * Use a calendar object to figure out about tomorrow 
 * and print out the date and day of the week.
 * This version tells the calendar object to figure
 * out what day tomorrow is using the add() method.
 */

import java.util.*;

public class BetterTomorrow {

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
     * line (all as integers). If it find acceptable values on the
     * command line this program uses today's date.
     *
     * main figures out the date for the following day, and prints it out.
     */

    static public void main(String []args) {
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

		// Instead of looking at the current day,month to determine whether
		// tomorrow is a new month/year - just add 1 to the day to get to tomorrow!
		
		c.add(Calendar.DATE,1);

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


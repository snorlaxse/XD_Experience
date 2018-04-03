/**
 * @ author Captain
 * @ date 2018/4/2
 * @ description as bellow.
 * Calendar generating program
 */

import java.util.*;


public class cal{

    final static String[] DAYS ={ "Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" };

    static final String[] MONTHS = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};


    public static void main(String[] args) {

        Calendar c = Calendar.getInstance();

        int month,year;

        if (args.length == 0) {
            month = c.get(Calendar.MONTH);
            year = c.get(Calendar.YEAR);

        } else{
            month = Integer.parseInt(args[0]);
            year = Integer.parseInt(args[1]);

            // January is month 0 !
            c.set(Calendar.MONTH,month-1);
            c.set(Calendar.YEAR,year);

//            if ((c.get(Calendar.MONTH) > c.getActualMaximum(Calendar.MONTH)) || (c.get(Calendar.YEAR) > c.getActualMaximum(Calendar.YEAR)) || args.length > 2)
//                 c = Calendar.getInstance();

            if ((month > 12) || (year < 1970) || args.length > 2)
                 c = Calendar.getInstance();

        }

        c.set(Calendar.DATE,1);

        //print out the month and year
        System.out.println(monthName(c) + " " + c.get(Calendar.YEAR));

        // print the calendar for that month
        for (String dayName: DAYS) {
            System.out.printf("%s\t",dayName);
        }
        System.out.printf("\n");

        //System.out.println(dayName(c));   //dayName(c) 即该月1号的星期名
        int number = LinearSearch(DAYS,dayName(c));
        for (int j = 0; j < number; j++) {
            System.out.printf("\t");
        }

        int account = c.getActualMaximum(Calendar.DAY_OF_MONTH) ;
        for (int i = 1; i <=account; i++) {

            System.out.printf("%2d\t", i);
            if ((i + number) % 7 == 0) System.out.printf("\n");
        }

    }

    static String dayName(Calendar c) {
        // Need to subtract 1, since the first DAY_OF_WEEK is 1 !
        return(DAYS[c.get(Calendar.DAY_OF_WEEK)-1]);
    }

    static String monthName(Calendar c) {
        return(MONTHS[c.get(Calendar.MONTH)]);
    }

    static int LinearSearch(String[] list,String key) {
        // TODO Auto-generated method stub
        for(int i = 0;i < list.length;i++){
            if(key == list[i]){
                return i;
            }
        }
        return -1;
    }
}

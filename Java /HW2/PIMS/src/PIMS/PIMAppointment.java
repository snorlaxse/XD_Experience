package PIMS;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ author Captain
 * @ date 2018/4/1
 * @ description as bellow.
 * Appointment items must be PIMEntites defined in a class named PIMAppointment.
 * Each appointment must have a priority (a string), a date and a description (a string).
 */


public class PIMAppointment extends PIMEntity implements PIMInterface {

    private Date date;
    private  String description;

    PIMAppointment(){ super(); this.entityType = "APPOINTMENT";}

    PIMAppointment(String priority, Date date, String description){
        if (!priority.equals("") ) this.priority = priority;
        this.entityType = "APPOINTMENT";
        this.date = date;
        this.description = description;
    }

    @Override
    public void fromString(String s) {
        String[] ss = s.split(" ");
        this.priority = ss[0];

        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yy");

        try {
            this.date = dateFormat.parse(ss[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.description = ss[2];


    }

    @Override
    public String toString() {
        return getPriority() + " " + getDate() + " " + getDescription();
    }

    @Override
    public String getPriority() {
        return priority;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDate() {

        SimpleDateFormat df=new SimpleDateFormat("dd/mm/yy");

        return df.format(date);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}

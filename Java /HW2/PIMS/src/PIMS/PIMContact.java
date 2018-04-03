package PIMS;

/**
 * @ author Captain
 * @ date 2018/4/1
 * @ description as bellow.
 * Contact items must be PIMEntites defined in a class named PIMContact.
 * Each contact item must have a priority (a string), and strings for each of the following: first name, last name, email address.
 * There is one additional requirement on the implementation of the 4 item classes listed above, the 2 classes that involve a date must share an interface that you define.
 * You must formally create this interface and have both PIMAppointment and PIMTodo implement this interface.
 */


public class PIMContact extends PIMEntity{

    private String firstName;
    private String lastName;
    private String emailAddress;

    PIMContact(){
        super();
        this.entityType = "CONTACT";
    }

    PIMContact(String priority,String firstName,String lastName,String emailAddress) {
        if (!priority.equals("") ) this.priority = priority;
        this.entityType = "CONTACT";
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
    }

    @Override
    public void fromString(String s) {
        String[] ss = s.split(" ");
        this.priority = ss[0];
        this.firstName = ss[1];
        this.lastName = ss[2];
        this.emailAddress = ss[3];


    }

    @Override
    public String toString() {
        return getPriority() + " " +getFirstName() +" "+ getlastName() +" " + getEmailAddress();
    }

    @Override
    public String getPriority() {
        return priority;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setlastName(String lastName) {
        lastName = lastName;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getlastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }


}

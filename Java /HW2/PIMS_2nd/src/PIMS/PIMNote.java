package PIMS;


import java.io.Serializable;

/**
 * @ author Captain
 * @ date 2018/4/1
 * @ description as bellow.
 * Note items must be PIMEntites defined in a class named PIMNote.
 * Each note item must have a priority (a string), and a string that contains the actual text of the note.
 */


public class PIMNote extends PIMEntity {

    private  String note;

    PIMNote(){this.entityType = "NOTE";}

    PIMNote(String priority,String note){
        if (!priority.equals("") ) this.priority = priority;
        this.entityType = "NOTE";
        this.note = note;
    }

    @Override
    public void fromString(String s) {
        String[] ss = s.split(" ");
        this.priority = ss[0];
        this.note = ss[1];

    }

    @Override
    public String toString() {
        return getPriority() +  " " + getNote();
    }

    @Override
    public String getPriority() {
        return this.priority;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }


}

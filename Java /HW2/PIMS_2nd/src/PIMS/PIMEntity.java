package PIMS;

import java.io.Serializable;

/**
 * @author Captain
 * @date 2018/4/1
 * @description Each of your 4 item type classes will be derived from the following abstract class.
 */

public abstract class PIMEntity implements Serializable {


    String priority; // every kind of item has a priority

    // default constructor sets priority to "normal"
    PIMEntity() {
        priority = "normal";
    }

    // priority can be established via this constructor.
    PIMEntity(String priority) {
        priority =  priority;
    }

    // accessor method for getting the priority string
    public String getPriority() {
        return priority;
    }
    // method that changes the priority string
    public void setPriority(String p) {
        if (!p.equals(""))  {priority = p;} //...

    }

    // Each PIMEntity needs to be able to set all state information
    // (fields) from a single text string.
    abstract public void fromString(String s);

    // This is actually already defined by the super class
    // Object, but redefined here as abstract to make sure
    // that derived classes actually implement it
    abstract public String toString();


    public String entityType;

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityType() {
        return entityType;
    }
}
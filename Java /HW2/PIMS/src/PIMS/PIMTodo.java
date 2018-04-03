package PIMS;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;



/**
 * @ author Captain
 * @ date 2018/4/1
 * @ description as bellow.
 * Todo items must be PIMEntites defined in a class named PIMTodo.
 * Each todo item must have a priority (a string), a date and a string that contains the actual text of the todo item.
 */


public class PIMTodo extends PIMEntity implements PIMInterface{

    private Date date;
    private  String todoItem;


    PIMTodo(){this.entityType = "Todo";}

    PIMTodo(String priority,Date date,String todoItem){
        if (!priority.equals("") ) this.priority = priority;
        this.entityType = "TODO";
        this.date = date;
        this.todoItem = todoItem;
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

        this.todoItem = ss[2];

    }

    @Override
    public String toString() {

        return getPriority() + " " + getDate() + " " + getTodoItem();

    }

    @Override
    public String getPriority() {
        return priority;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDate() {
        //设置日期输出的格式
        SimpleDateFormat df=new SimpleDateFormat("dd/mm/yy");
        //格式化输出
        //System.out.println(df.format(date));
        return df.format(date);
    }

    public void setTodoItem(String todoItem) {
        this.todoItem = todoItem;
    }

    public String getTodoItem() {
        return todoItem;
    }

}

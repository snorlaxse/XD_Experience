package PIMS;

import java.io.*;
import java.text.*;
import java.util.*;

/**
 * @ author Captain
 * @ date 2018/4/1
 * @ description as bellow.
 * You must also create a class named PIMManager that includes a mainand provides some way of creating and managing items (from the terminal).
 * You must support the following commands (functionality):

 List: print a list of all PIM items
 Create: add a new item
 Save: save the entire list of items
 Load: read a list of items from a file

 *Note:HW2: simple version, just for printing (i.e., save in an array, Load from the array and print out);
 complex version, to a file, should be finished after I/O topic, to database, can (optional) be finished after JDBC topic,to a remote server, can (optional) be after Networking topic.

 */


public class PIMManager {
    public  static  String filePath = "out/save.txt";
    public  static  String loadfilePath = "out/load.txt";

    public  static List<PIMEntity> allItems = new ArrayList<PIMEntity>();

    public static void main(String[] args) throws ParseException, IOException {

        System.out.println("Welcome to PIM.");

        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (input != null) {

            System.out.println("---Enter a command (suported commands are List Create Save Load Quit)---");


            input = scanner.next();

            switch (input){

            case "List": case "list":
                List();
                break;

            case "Create": case "create":
                Create();
                break;

            case"Save": case "save":
                Save();
                break;

            case "Load": case"load":
                Load();
                break;

            case"Read": case "read":
                Read();
                break;

            case"Quit": case"quit":
                return;

            default:
                System.out.println("Illegal input");
                break;
            }


        }

    }

    private static void List() throws IOException {

        System.out.printf("There are %d items\n",allItems.size());

        //allItems[]非空时，依次打印item
        if(allItems.size() != 0) {
            int i =0;
            for (PIMEntity item: allItems){

                System.out.printf("Item %d: %s %s\n",++i,item.getEntityType(),item.toString());
            }
        }

    }

    private static void Create() throws ParseException {

        System.out.println("Enter an item type ( todo, note, contact or appointment )");

        Scanner scanner = new Scanner(System.in);
        String input = " ";


        while (input != null) {

            input = scanner.next();

            switch (input) {

                case "todo": case "Todo":
                    createTodo();
                    return;

                case "note": case "Note":
                    createNote();
                    return;

                case "contact": case "Contact":
                    createContact();
                    return;

                case "appointment": case "Appointment":
                    createAppointment();
                    return;

                default:
                    System.out.println("Illegal input. Please input correct args.");
                    break;

            }
        }

    }
    private static void createTodo() throws ParseException {

        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.printf("Enter date for todo item: \n");
        input = scanner.next();
        String date = input;
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yy");
        Date  d = dateFormat.parse(input);
//        System.out.println(d);
        input = scanner.nextLine();


        System.out.printf("Enter todo text:\n");
        input = scanner.nextLine();
        String text = input;
//        System.out.println(text);


        System.out.printf("Enter todo priority:\n");
        input = scanner.nextLine();
        String priority = input;
//        System.out.println(priority);


        PIMTodo item = new PIMTodo(priority,d,text);
        allItems.add(item);

    }
    private static void createNote(){
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.printf("Enter note text:\n");
        input = scanner.nextLine();
        String note = input;
//        System.out.println(text);


        System.out.printf("Enter note priority:\n");
        input = scanner.nextLine();
        String priority = input;
//        System.out.println(priority);

        PIMNote item = new PIMNote(priority,note);
        allItems.add(item);

    }
    private static void createAppointment() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.printf("Enter date for Appointment item: \n");
        input = scanner.next();
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yy");
        Date  d = dateFormat.parse(input);
//        System.out.println(d);
        input = scanner.nextLine();


        System.out.printf("Enter Appointment description:\n");
        input = scanner.nextLine();
        String description = input;
//        System.out.println(description);


        System.out.printf("Enter Appointment priority:\n");
        input = scanner.next();
        String priority = input;
//        System.out.println(priority);


        PIMAppointment item = new PIMAppointment(priority,d,description);
        allItems.add(item);

    }
    private static void createContact(){

        Scanner scanner = new Scanner(System.in);
        String input;


        System.out.printf("Enter Contact priority:\n");
        input = scanner.next();
        String  priority = input;
//        System.out.println(priority);

        System.out.printf("Enter Contact firstName:\n");
        input = scanner.next();
        String  firstName = input;
//        System.out.println(firstName);

        System.out.printf("Enter Contact lastName:\n");
        input = scanner.next();
        String  lastName = input;
//        System.out.println(lastName);

        System.out.printf("Enter Contact emailAddress:\n");
        input = scanner.next();
        String  emailAddress = input;
//        System.out.println(emailAddress);

        PIMContact contact = new PIMContact(priority,firstName,lastName,emailAddress);
        allItems.add(contact);



    }

    private static void Save(){
        // 将allItems[]保存(输出)到save.txt

        WriteStringToFile(filePath);

        System.out.println("Items have been saved.");

    }
    public static void WriteStringToFile(String filePath) {
        try {
            FileWriter fw = new FileWriter(filePath, true); // 往已有的文件上添加字符串
//            FileWriter fw = new FileWriter(filePath); // 覆盖原文件内容
            BufferedWriter bw = new BufferedWriter(fw);

            // 使用增强for循环进行遍历
            for (PIMEntity  item : allItems) {
                bw.write(item.getEntityType() +" " + item.toString() + "\r\n");
            }

            bw.close();
            fw.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        allItems.clear();
    }

    public static void Read() throws IOException{//read a list of items from a file
        FileInputStream fin = new FileInputStream(filePath);
        InputStreamReader reader = new InputStreamReader(fin);
        BufferedReader buffReader = new BufferedReader(reader);
        String strTmp = " ";

        int i =0;
        while((strTmp = buffReader.readLine())!=null){
            System.out.println("Item " + (++i) + ": " + strTmp);
        }

        buffReader.close();

    }
    public static void Load() throws IOException {

        FileInputStream fin = new FileInputStream(filePath);
        InputStreamReader reader = new InputStreamReader(fin);
        BufferedReader buffReader = new BufferedReader(reader);
        String strTmp = " ";

        int i = 0;
        while((strTmp = buffReader.readLine())!=null){

            //System.out.println("Item " + (++i) + ": " + strTmp);

            String[] ss = strTmp.split(" ");
            switch (ss[0]) {
                case"TODO":
                    PIMTodo todo = new PIMTodo();
                    todo.fromString(ss[1] +" " + ss[2]+" "+ss[3] + " ");
                    allItems.add(todo);
                    break;

                case "NOTE":
                    PIMNote note = new PIMNote();
                    note.fromString(ss[1] +" " + ss[2]);
                    allItems.add(note);
                    break;

                case "APPOINTMENT":
                    PIMAppointment app= new PIMAppointment();
                    app.fromString(ss[1] +" " + ss[2]+" "+ss[3]+ " ");
                    allItems.add(app);
                    break;

                case "CONTACT":
                    PIMContact contact = new PIMContact();
                    contact.fromString(ss[1] +" " + ss[2]+" "+ss[3] + " "+ ss[4] +" ");
                    allItems.add(contact);
                    break;


                default:
//                    System.out.println("Something error");
                    break;

            }


        }

        System.out.println("Items have been load.");


        buffReader.close();
    }

}

// get fancy and create Serializable objects and use Object streams. ---- More efficient!!!
// remember to try it!

// 数据查重
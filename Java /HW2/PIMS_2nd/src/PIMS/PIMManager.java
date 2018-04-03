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
 *
 */

 // 数据查重
 // 注意：save(保存当前allItems) - list(查看当前allItems) - load(载入save的allItems) 顺序
 // 连续load后save 会出现重复Item!;连续save后load 清空记录


public class PIMManager implements Serializable{

    public  static List<PIMEntity> allItems = new ArrayList<PIMEntity>();

    public static void main(String[] args) throws ParseException, IOException, ClassNotFoundException {

        System.out.println("Welcome to PIM.");


        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (input != null) {

            System.out.println("---Enter a command (suported commands are List Create Save Load Quit)---");

            input = scanner.next();

            switch (input){

            case "List": case "list":
                List(); break;

            case "Create": case "create":
                Create(); break;

            case"Save": case "save":
                Save(); break;

            case "Load": case"load":
                Load(); break;

            case"Quit": case"quit":
                return;

            default:
                System.out.println("Illegal input");
                break;
            }
        }

    }

    private static void List() throws IOException {     // List: print a list of all PIM items

        System.out.printf("There are %d items\n",allItems.size());

        //allItems[]非空时， print a list of all PIM items
        if(allItems.size() != 0) {
            int i =0;
            for (PIMEntity item: allItems){

                System.out.printf("Item %d: %s %s\n",++i,item.getEntityType(),item.toString());
            }
        }

    }

    private static void Create() throws ParseException {    // Create: add a new item

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

    private static void createNote(){
        Scanner scanner = new Scanner(System.in);
        PIMNote note = new PIMNote();

        System.out.printf("Enter note text:\n");
        note.setNote(scanner.nextLine());

        System.out.printf("Enter note priority:\n");
        note.setPriority(scanner.nextLine());

        allItems.add(note);
    }

    private static void createTodo() throws ParseException {

        String input;
        Scanner scanner = new Scanner(System.in);
        PIMTodo item = new PIMTodo();

        System.out.printf("Enter date for todo item: \n");
        input = scanner.next();
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yy");
        item.setDate(dateFormat.parse(input));
        input = scanner.nextLine();

        System.out.printf("Enter todo text:\n");
        item.setTodoItem(scanner.nextLine());

        System.out.printf("Enter todo priority:\n");
        item.setPriority(scanner.nextLine());

        allItems.add(item);

    }

    private static void createAppointment() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        String input;
        PIMAppointment appointment = new PIMAppointment();

        System.out.printf("Enter date for Appointment item: \n");
        input = scanner.next();
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yy");
        appointment.setDate(dateFormat.parse(input));
        scanner.nextLine();

        System.out.printf("Enter Appointment description:\n");
        appointment.setDescription(scanner.nextLine());

        System.out.printf("Enter Appointment priority:\n");
        appointment.setPriority(scanner.nextLine());

        allItems.add(appointment);

    }
    private static void createContact(){

        Scanner scanner = new Scanner(System.in);
        PIMContact contact = new PIMContact();

        System.out.printf("Enter Contact priority:\n");
        contact.setPriority(scanner.nextLine());

        System.out.printf("Enter Contact firstName:\n");
        contact.setFirstName(scanner.nextLine());

        System.out.printf("Enter Contact lastName:\n");
        contact.setlastName(scanner.nextLine());

        System.out.printf("Enter Contact emailAddress:\n");
        contact.setEmailAddress(scanner.nextLine());

        allItems.add(contact);

    }

    private static void Save() throws IOException { // Save: save the entire list of items

//        System.out.println(allItems);
        // 将allItems[]保存(输出)到out.txt
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("out.txt"))) {

            // 可以先把所有对象存到集合中，再把集合对象用对象流存到文件中.
            // 读取的时候只需要读取这个集合对象，然后再遍历，就可以拿到所有的对象了，而且没有异常
            oos.writeObject(allItems);
            oos.close();
        }

        allItems.clear();

        System.out.println("Items have been saved.");

    }


    public static void Load() throws IOException, ClassNotFoundException {  // Load: read a list of items from a file
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("out.txt"))) {   //"缺省append"

            List<PIMEntity> loadItems = (List<PIMEntity>) ois.readObject();
            System.out.println("Items have been loaded.");
//            System.out.println("loadItems "+loadItems);

            for (PIMEntity item: loadItems) { allItems.add(item); }

        }

//        System.out.println("allItems "+ allItems);
    }

}

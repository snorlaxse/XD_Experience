import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;

class Book {

    private String name;
    Book(String name){
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

}

class Course {
    private String name;
    private Book[] books;

    Course(String name,Book... books){
        this.name = name;
        this.books = books;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Book[] getBooks() {
        return books;
    }
    public void setBooks(Book[] books) {
        this.books = books;
    }
}

class Student {

    private String id;
    private Course[] courses;

    Student(String id){

    }

    Student(String id, Course course){
        System.out.print(id + " select ");
        CRS_print(course);
        System.out.printf(".\n");
    }

    Student(String id, Course[] courses){

        System.out.print(id + " select ");

        for(Course course : courses){

            if (course == courses[0]) {
                CRS_print(course);}
            else{
                System.out.print("; and " );
                CRS_print(course);}
        }

        System.out.printf(".\n");
    }


    void CRS_print(Course course){

        System.out.print(course.getName() + " with ");

        if(course.getBooks().length == 1) {
            System.out.print("《"+course.getBooks()[0].getName()+"》");
        } else {
            System.out.print("books ");
            for (Book book : course.getBooks()) {
                System.out.print("《" + book.getName() + "》");
            }
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Course[] getCourses() {
        return courses;
    }

    public void setCourses(Course[] courses) {
        this.courses = courses;
    }
}

public class CRS {

    public static void main(String[] args) {
        Book ThinkingInJava = new Book("Thinking in Java");
        Book Java8 = new Book("Java 8");
        Course Java = new Course("Java",ThinkingInJava,Java8);

        Book ML = new Book("Machine Learning By Zhou ZhiHua");
        Book PS = new Book("Probability and Statistics");
        Book SPR = new Book("Statistical Pattern Recognition");
        Course MachineLearning = new Course("MachineLearning",ML,PS,SPR);

        Book webEngineering = new Book("Web Engineering");
        Course WebEngineering = new Course("WebEngineering",webEngineering);

        Course[] allcourse= {Java,WebEngineering,MachineLearning};

        Course[] course = new Course[0];
        List<Course> courses = new ArrayList<>();

        for (int i = 1; i < args.length;i++) {
            try {
                courses.add(requireNonNull(getCourse(args[i], allcourse)));
            }catch (NullPointerException e)
            {
                System.out.println("Finding the wrong parameter(s)!");
            }
        }
        Course[] selectCourses =  courses.toArray(course);

        Student student = new Student(args[0],selectCourses);

    }

    private static Course getCourse(String name, Course... courses){
        for (Course course:courses){
            if (name.equals(course.getName())){
                return course;
            }
        }
        return null;
    }
}

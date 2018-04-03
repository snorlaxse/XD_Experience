package CourseSelectingSystem;

/**
 * @author Captain
 * @date 2018/3/27
 * @description Student has at least three overloaded constructors with different parameter list.
 **/

public class Student {

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

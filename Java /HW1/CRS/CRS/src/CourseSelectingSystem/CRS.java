package CourseSelectingSystem;

import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.*;

/**
 * @author Captain
 * @date 2018/3/27
 **/

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

        Course [] course = new Course[0];
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
        /* 利用正则表达式判断id格式是否满足要求*/

    }

    private static Course getCourse(String name,Course... courses){
        for (Course course:courses){
            if (name.equals(course.getName())){
                return course;
            }
        }
        return null;
    }
}

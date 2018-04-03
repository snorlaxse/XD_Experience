package CourseSelectingSystem;

/**
 * @author Captain
 * @date 2018/3/27
 * @description Student may select one or more Course, and one Course also has some members of Book.
 **/

public class Course {
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

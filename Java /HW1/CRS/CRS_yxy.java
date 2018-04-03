import  ava.util.* ;

class Book{
	private String name;
	public Book(String name){
		this.name = name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
}

//Course类，类成员有课程名字
class Course{
	private String courseName;
	public static Map<String, List<Book>> map = new HashMap<>();
	public static void initMap(){
		List<Book> list = new ArrayList<>();
		list.add(new Book("Thinking in Java"));
		list.add(new Book("Java8"));
		map.put("Java",list);
		List<Book> list1 = new ArrayList<>();
		list1.add(new Book("Web Engineering"));
		map.put("WebEngineering",list1);
	}
	public Course(String courseName){
		this.courseName = courseName;
	}
	public void setName(String courseName){
		this.courseName = courseName;
	}
	public String getName(){
		return this.courseName;
	}
}
//Class Student, whose members have studentNo,name of student,grade of student and courses student selected.
class Student{
	private String studentNo,name;
	private int grade,m=0;
	private Course[] courses = new Course[100];
	public Student(String studentNo){
		this.studentNo = studentNo;
	}
	public Student(String name, int grade){
		this.name = name;
		this.grade = grade;
	}
	public Student(String name, String studentNo, int grade){
		this.name = name;
		this.studentNo = studentNo;
		this.grade = grade;
	}
	public void setGrade(int grade){
		this.grade = grade;
	}
	public int getGrade(){
		return this.grade;
	}
	public String getStudentNo(){
		return this.studentNo;
	}
	public String getName(){
		return this.name;
	}
	public void setM(int i){
		this.m = i;
	}
	public int getM(){
		return this.m;
	}
	public void setCourse(Course course){
		courses[m] = course;
		m++;
	}
	public Course getCourse(int i){
		return courses[i];
	}
}

public class CRS{
	public static void main(String[] args){
		Student student = new Student(args[0]);
		StringBuilder sg = new StringBuilder();
		Course.initMap();
		for(int i=1; i<args.length; i++)
		{
			Course course = new Course(args[i]);
			student.setCourse(course);
		}
		sg.append(student.getStudentNo()+" select ");
		for(int i=0; i<student.getM(); i++)
		{
			Course course = student.getCourse(i);
			sg.append(course.getName()+" with books ");
			List<Book> list1 = Course.map.get(course.getName());
			for(int j=0; j<list1.size(); j++)
			{
				Book book = list1.get(j);
				if(j+1<list1.size())
				{
					sg.append(book.getName()+", and ");
				}
				else
				{
					sg.append(book.getName()+".");
				}
			}
			if(i+1<student.getM())
			{
				sg.append(" ; And ");
			}
		}
		System.out.println(sg.toString());
	}
}

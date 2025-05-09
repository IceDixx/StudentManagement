package raisetech.Student.management;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

  @Autowired
  private StudentRepository repository;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  //名前で学生を探します//
  @GetMapping("/Student/byName")
  public String studentInfo(@RequestParam String name) {
    Student student = repository.searchByName(name);
    System.out.println("Searching by Student name");
    return student.getId() + " " + student.getCourses_id() + student.getName() + " "
        + student.getAge()
        + "Years";
  }

  //IDで学生を探します　名前ダブルかもしれないため//
  @GetMapping("/Student/byID")
  public String searchById(@RequestParam int id) {
    Student student = repository.searchById(id);
    System.out.println("Searching Student by StudentID");
    return student.getId() + " " + student.getCourses_id() + student.getName() + " "
        + student.getAge()
        + "Years";
  }

  @GetMapping("/StudentCourse")
  public String searchCourse(@RequestParam String Course_name) {
    System.out.println("Out printing studentInfo");
    Student student = repository.searchCourse(Course_name);
    return student.getId() + " " + student.getName() + " " + student.getCourses_id()
        + student.getCourse_name() + " ";
  }

  @GetMapping("/students")
  public List<Student> getAllStudents() {
    return repository.getAllStudents();
  }

  @GetMapping("/CourseID")
  public String searchCourseID(@RequestParam int course_id) {
    System.out.println("Out printing studentInfo");
    Student student = repository.searchCourseID(course_id);
    return student.getId() + " " + student.getName() + " " + student.getCourses_id()
        + student.getCourse_name() + " ";
  }

  //学生を更新//
  @PostMapping("/Student")
  public void registerStudent(int id, String name, int age, String email, String address,
      String nickname, String gender) {
    System.out.println("Updating StudentInfo");
    repository.registerStudent(id, name, age, email, address, nickname, gender);
  }

  //授業を追加//
  @PostMapping("/Student_courses")
  public void registerStudentCourses(@RequestParam int student_id,
      @RequestParam String course_name,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date start_date,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date end_date) {
    repository.registerStudentCourses(student_id, course_name, start_date, end_date);
  }


  //学生が受けてる授業を更新//
  @PatchMapping("/StudentUpdate")
  public void updateStudent(int id, String name) {
    repository.updateStudent(id, name);
  }

  //IDで学生を消す//
  @DeleteMapping("/Student")
  public void deleteStudent(@RequestParam int id) {
    repository.deleteStudent(id);
  }

  //名前で授業を消す//
  @DeleteMapping("/DeleteCourse")
  public void deleteCourse(@RequestParam String course_name) {
    repository.deleteCourse(course_name);
  }

}



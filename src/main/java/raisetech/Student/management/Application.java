package raisetech.Student.management;

import java.time.LocalDate;
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
import raisetech.Student.management.data.Student;
import raisetech.Student.management.repository.StudentRepository;

@SpringBootApplication
@RestController
public class Application {

  @Autowired
  private StudentRepository repository;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  //名前で学生を探します//
  @GetMapping("/student/byName")
  public String studentInfo(@RequestParam String name) {
    Student student = repository.searchByName(name);
    System.out.println("Searching by Student name");
    return student.getId() + " " + student.getCoursesId() + student.getName() + " "
        + student.getAge()
        + "Years";
  }

  //IDで学生を探します　名前ダブルかもしれないため//
  @GetMapping("/student/byID")
  public String searchById(@RequestParam int id) {
    Student student = repository.searchById(id);
    System.out.println("Searching Student by StudentID");
    return student.getId() + " " + student.getCoursesId() + student.getName() + " "
        + student.getAge()
        + "Years";
  }


  @GetMapping("/courseID")
  public String searchCourseID(@RequestParam int courseId) {
    System.out.println("Out printing studentInfo");
    Student student = repository.searchCourseID(courseId);
    return student.getId() + " " + student.getName() + " " + student.getCoursesId()
        + student.getCoursesName() + " ";
  }

//was//

  //授業を追加//
  @PostMapping("/studentCourses")
  public void registerStudentCourses(@RequestParam(required = false) int studentId,
      @RequestParam String courseName,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
    repository.registerStudentCourses(studentId, courseName, startDate, endDate);
  }

  //
  //学生が受けてる授業を更新//
  @PatchMapping("/studentUpdate")
  public void updateStudent(int id, String name) {
    repository.updateStudent(id, name);
  }

  //IDで学生を消す//
  @DeleteMapping("/deleteStudent")
  public void deleteStudent(@RequestParam int id) {
    repository.deleteStudent(id);
  }

  //名前で授業を消す//
  @DeleteMapping("/deleteCourse")
  public void deleteCourse(@RequestParam String courseName) {
    repository.deleteCourse(courseName);
  }

}



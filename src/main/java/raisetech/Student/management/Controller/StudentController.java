package raisetech.Student.management.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import raisetech.Student.management.data.Student;
import raisetech.Student.management.data.studentCourses;
import raisetech.Student.management.service.StudentService;


@RestController

public class StudentController {

  private StudentService service;

  public StudentController(StudentService service) {
    this.service = service;
  }

  @Autowired

  @GetMapping("/students")
  public List<Student> getAllStudents() {
    return service.getAllStudents();
  }

  @GetMapping("/SearchStudentByYears")
  public List<Student> getYearsStudent(@RequestParam int age) {
    return service.getYearsStudent(age);
  }

  @GetMapping("/studentCourses")
  public List<studentCourses> getstudentCourses() {
    return service.getstudentCourses();
  }

  @GetMapping("/SelectCoursesByName")
  public List<studentCourses> getSelectCourses(@RequestParam String CourseName) {
    return service.getSelectCourses(CourseName);
  }
}

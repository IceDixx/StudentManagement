package raisetech.Student.management.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import raisetech.Student.management.Controller.coverter.StudentConverter;
import raisetech.Student.management.data.Student;
import raisetech.Student.management.data.StudentCourses;
import raisetech.Student.management.domain.StudentDetail;
import raisetech.Student.management.service.StudentService;


@RestController

public class StudentController {

  private StudentConverter converter;
  private StudentService service;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter
  ) {
    this.service = service;
    this.converter = converter;
  }


  @GetMapping("/students")
  public List<StudentDetail> getAllStudents() {
    List<Student> students = service.getAllStudents();
    List<StudentCourses> studentCourses = service.getStudentCourses();

    return converter.convertStudentDetails(students, studentCourses);
  }

  @PatchMapping("/voidStudent")
  public void vacantStudent(@RequestParam String id) {
    service.vacantStudent(id);
  }

  @GetMapping("/searchStudentByYears")
  public List<Student> getYearsStudent(@RequestParam int age) {
    return service.getYearsStudent(age);
  }

  @GetMapping("/studentCourses")
  public List<StudentCourses> getStudentCourses() {
    return service.getStudentCourses();
  }

  @GetMapping("/SelectCoursesByName")
  public List<StudentCourses> getSelectCourses(@RequestParam String CourseName) {
    return service.getSelectCourses(CourseName);
  }
}

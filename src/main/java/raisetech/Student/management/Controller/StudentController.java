package raisetech.Student.management.Controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import raisetech.Student.management.Controller.coverter.StudentConverter;
import raisetech.Student.management.data.Student;
import raisetech.Student.management.data.StudentsCourses;
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


  @GetMapping("/studentList")
  public List<StudentDetail> getAllStudents() {
    List<Student> students = service.getAllStudents();
    List<StudentsCourses> studentsCourse = service.getStudentCourses();
    return converter.convertStudentDetails(students, studentsCourse);
  }

  @PostMapping("/voidStudent")
  public String vacantStudent(@RequestParam int id) {
    service.vacantStudent(id);
    return "redirect:/studentList";

  }

  @GetMapping("/studentCourses")
  public List<StudentsCourses> getStudentCourses() {
    return service.getStudentCourses();
  }


  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudentsCourses(Arrays.asList(new StudentsCourses()));
    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent";
  }

  @PostMapping("/registerStudent")
  public ResponseEntity<String> registerStudent(@RequestBody StudentDetail studentDetail) {
    service.registerStudent(studentDetail);
    return ResponseEntity.ok("GOOD");

  }

  @GetMapping("/studentEdit/{id}")
  public String searchById(@PathVariable int id, Model model) {
    StudentDetail studentDetail = service.searchStudent(id);
    model.addAttribute("studentDetail", studentDetail);
    return "updateStudent";
  }

  @PostMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("GOOD");
  }


}




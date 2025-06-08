package raisetech.Student.management.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import raisetech.Student.management.Controller.coverter.StudentConverter;
import raisetech.Student.management.data.Student;
import raisetech.Student.management.data.StudentCourses;
import raisetech.Student.management.domain.StudentDetail;
import raisetech.Student.management.service.StudentService;


@Controller

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
  public String getAllStudents(Model model) {
    List<Student> students = service.getAllStudents();
    List<StudentCourses> studentCourses = service.getStudentCourses();
    model.addAttribute("studentList", converter.convertStudentDetails(students, studentCourses));
    return "studentList";
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

  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    model.addAttribute("studentDetail", new StudentDetail());
    return "registerStudent";
  }

  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if (!result.hasErrors()) {
      Student student = studentDetail.getStudent();
      service.registerStudent(student);
    }
    return "redirect:/studentList";
  }

  @GetMapping("/coursesTop")
  public String newCourses(Model model) {
    model.addAttribute("studentCourses", new StudentCourses());
    return "registerStudentCourses";
  }

  @PostMapping("/registerStudentCourses")
  public String registerStudentCourses(@ModelAttribute StudentCourses studentCourses,
      BindingResult result) {
    if (result.hasErrors()) {

      return "registerStudentCourses";
    }
    service.registerStudentCourses(studentCourses);
    return "redirect:/studentList";
  }
}



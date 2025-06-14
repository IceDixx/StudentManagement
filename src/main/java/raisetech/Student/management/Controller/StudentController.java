package raisetech.Student.management.Controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import raisetech.Student.management.Controller.coverter.StudentConverter;
import raisetech.Student.management.data.Student;
import raisetech.Student.management.data.StudentsCourses;
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
    List<StudentsCourses> studentsCourse = service.getStudentCourses();
    model.addAttribute("studentList", converter.convertStudentDetails(students, studentsCourse));
    return "studentList";
  }

  @PatchMapping("/voidStudent")
  public void vacantStudent(@RequestParam int id) {
    service.vacantStudent(id);
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
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if (!result.hasErrors()) {
      Student student = studentDetail.getStudent();
      service.registerStudent(studentDetail);
    }
    return "redirect:/studentList";
  }

  @GetMapping("/studentEdit/{id}")
  public String searchById(@PathVariable int id, Model model) {
    StudentDetail studentDetail = service.searchStudent(id);
    model.addAttribute("studentDetail", studentDetail);
    return "updateStudent";
  }

  //  @PatchMapping("/students/{id}")
  @PostMapping("/updateStudent")
  public String updateStudent(@ModelAttribute StudentDetail studentDetail,
      BindingResult result) {
    if (result.hasErrors()) {
      return "updateStudent";
    }
    service.updateStudent(studentDetail);
    return "redirect:/studentList";
  }


}




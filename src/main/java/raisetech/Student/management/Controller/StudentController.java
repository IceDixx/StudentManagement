package raisetech.Student.management.Controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import raisetech.Student.management.Controller.coverter.StudentConverter;
import raisetech.Student.management.data.CourseStatus;
import raisetech.Student.management.domain.StudentDetail;
import raisetech.Student.management.exception.TestException;
import raisetech.Student.management.service.StudentService;

/**
 * 受講生の検索や登録,更新などを行うREST API として実行されるController
 */

@Validated
@RestController

public class StudentController {


  private StudentConverter converter;
  private StudentService service;

  @Autowired
  public StudentController(StudentService service
  ) {
    this.service = service;
    this.converter = converter;
  }

  /**
   * 受講生詳細一覧検索 全件検索行う、条件してはなし
   *
   * @return　受講生詳細一覧（全件）
   */
  @Operation(summary = "Search", description = "SearchFromStudent")
  @GetMapping("/studentList")
  public List<StudentDetail> getAllStudents() {
    return service.searchStudentList();
  }

  @GetMapping("/student/search")
  public List<StudentDetail> searchStudents(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String email,
      @RequestParam(required = false) Integer age) {
    return service.searchStudentsByCondition(name, email, age);
  }


  @Operation(summary = "VoidStudent", description = "VoidStudent")
  @PostMapping("/voidStudent")
  public String vacantStudent(@RequestParam @Min(1) @Max(999) int id) {
    service.vacantStudent(id);
    return "redirect:/studentList";

  }

  /**
   * 受講生詳細の登録を行います。
   *
   * @param studentDetail
   * @return
   */
  @Operation(summary = "RegisterStudent", description = "To RegisterStudent")
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(
      @RequestBody @Valid StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);

  }

  /**
   * 受講生検索 IDに紐づくの受講生の情報を取得
   *
   * @param id 受講生ID
   * @return 受講生ID
   */
  @Operation(summary = "SearchStudent&Course", description = "SearchFromStudent&Courses")
  @GetMapping("/student/{id}")
  public StudentDetail searchById(
      @PathVariable @Min(1) @Max(999) int id) {
    return service.searchStudent(id);

  }

  @Operation(summary = "受講生を検索", description = "IDで受講生を検索する")
  @GetMapping("/studentEdit/{id}")
  public String searchById(@PathVariable int id, Model model) {
    StudentDetail studentDetail = service.searchStudent(id);
    model.addAttribute("studentDetail", studentDetail);
    return "updateStudent";
  }

  /**
   * 受講生詳細の更新を行います。 キャンセルフラグの更新もここで行います。（論理削除）
   *
   * @param studentDetail 受講生詳細
   * @return　実行結果
   */
  @Operation(summary = "UpdateStudent", description = "UpdateStudent論理削除もいける")
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("GOOD");
  }

  @Operation(summary = "例外処理", description = "例外処理")
  @GetMapping("/Find/{id}")
  public StudentDetail FindId(
      @PathVariable int id) throws TestException {
    throw new TestException("Please give me number");

  }

  @GetMapping("/courseStatuses")
  public List<CourseStatus> getCourseStatuses() {
    return service.getAllCourseStatuses();
  }

  @PutMapping("/course/{courseId}/status")
  public ResponseEntity<String> updateCourseStatus(
      @PathVariable int courseId,
      @RequestParam String status) {
    service.updateCourseStatus(courseId, status);
    return ResponseEntity.ok("Status up date to" + status);
  }
}







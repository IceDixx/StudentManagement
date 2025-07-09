package raisetech.Student.management.Controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
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

  @GetMapping("/studentList")
  public List<StudentDetail> getAllStudents() {
    return getAllStudents();
  }

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
  @GetMapping("/student/{id}")
  public StudentDetail searchById(
      @PathVariable @Min(1) @Max(999) @Pattern(regexp = "^\\d+$") int id) {
    return service.searchStudent(id);

  }

  @GetMapping("/studentEdit/{id}")
  public String searchById(@PathVariable int id, Model model) {
    StudentDetail studentDetail = service.searchStudent(id);
    model.addAttribute("studentDetail", studentDetail);
    return "updateStudent";
  }

  /**
   * 受講生詳細の更新を行います。 キャンセルフラグの更新もここで行います。（論理削除）
   *
   * @param studentDetail 　受講生詳細
   * @return　実行結果
   */
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("GOOD");
  }

  @GetMapping("/Find/{id}")
  public StudentDetail FindId(
      @PathVariable int id) throws TestException {
    throw new TestException("Please give me number");

  }
}







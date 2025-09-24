package raisetech.Student.management.Controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.Student.management.data.Student;
import raisetech.Student.management.domain.StudentDetail;
import raisetech.Student.management.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {


  @Autowired
  MockMvc mockMvc;

  @MockBean
  private StudentService service;
  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  LocalDateTime startDate = LocalDateTime.now();
  LocalDateTime endDate = LocalDateTime.now().plusYears(1);

  @Test
  void 受講生一覧の検索実行されて＿空のリストが返ってきます() throws Exception {
    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk());
    verify(service, times(1)).searchStudentList();
  }

  @Test
  void 受講生詳細の受講生で適性な値を入力した時に入力チェックに異常が発生しないこと() {
    Student student = new Student(
        "TEST",
        12,
        "TEST@TEST.com",
        "T",
        "Japan",
        -1,
        "T",

        1, "JAVA", startDate, endDate, "", false);
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  void 受講生詳細の受講生でIDに数字以外を用いた時に入力チェックに掛かること() {
    Student student = new Student(
        "TEST",
        12,
        "TEST@TEST.com",
        "T",
        "Japan",
        -1,
        "T",

        1, "JAVA", startDate, endDate, "", false);
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("ID must be greater than 0");
  }

  @Test
  void 受講生を理論削除をします＿成功したらリダイレクトすること() throws Exception {
    Student student = new Student();
    student.setId(2);
    mockMvc.perform(post("/voidStudent")
            .param("id", "2"))
        .andExpect(status().isOk())
        .andExpect(content().string("redirect:/studentList"));

    verify(service, times(1)).vacantStudent(2);
  }

  @Test
  void 受講生を理論削除をします＿入力チェック掛けること() throws Exception {
    mockMvc.perform(post("/voidStudent")
            .param("id", "wawead"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void idで受講生を検査する＿受講コース情報も一緒に検索できること() throws Exception {
    int testId = 2;
    StudentDetail mockStudentDetail = new StudentDetail();
    Student student = new Student();
    student.setId(testId);
    mockStudentDetail.setStudent(student);

    when(service.searchStudent(testId)).thenReturn(mockStudentDetail);

    student.setId(2);
    mockMvc.perform(get("/student/{id}", testId))
        .andExpect(status().isOk());
    verify(service, times(1)).searchStudent(testId);
  }

  @Test
  void 受講生を正確に登録できて＿からで返ってくること() throws Exception {
    mockMvc.perform(post("/registerStudent").contentType(MediaType.APPLICATION_JSON).content("""
          "student":{
            "name": "山田太郎",
            "email": "yamada@example.com",
            "nickname": "ヤマ",
            "age": 20,
            "address": "東京都",
            "gender": "男"
          },
          "studentCourseList" : [{"courseName" : "JAVA"
          }]}
        """)).andExpect(status().isOk());
    verify(service, times(1)).registerStudent(any());

  }

  @Test
  void 受講生の例外処理が実行できて400で返ってくること() throws Exception {
    mockMvc.perform(get("/find/{id}"))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string("Please give me number"));

  }

  @Test
  void 受講生の情報を更新し＿からで返ってくること() throws Exception {
    mockMvc.perform(put("/updateStudent").contentType(MediaType.APPLICATION_JSON).content(
            """
                           {
                          "student":{
                        "id":33,
                        "name": "山田太郎",
                        "email": "yamada@example.com",
                        "nickname": "ヤマ",
                        "age": 20,
                        "address": "東京都",
                        "gender": "男"
                  },
                      "studentCourseList" : [{"courseName" : "JAVA"
                
                   }]
                
                }
                """
        ))
        .andExpect(status().isOk());
    verify(service, times(1)).updateStudent(any(StudentDetail.class));
  }
}
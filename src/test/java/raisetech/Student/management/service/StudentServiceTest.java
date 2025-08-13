package raisetech.Student.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.Student.management.Controller.coverter.StudentConverter;
import raisetech.Student.management.data.Student;
import raisetech.Student.management.data.StudentCourse;
import raisetech.Student.management.domain.StudentDetail;
import raisetech.Student.management.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;
  @Mock
  private StudentConverter converter;
  private StudentService sut;

  private

  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);
  }


  @Test
  void 受講生一覧検索機能_リポジトリとコンバーターの処理が適性に呼び出せていること() {
    StudentService sut = new StudentService(repository, converter);
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    when(repository.search()).thenReturn(studentList);
    when(repository.searchStudentCourses()).thenReturn(studentCourseList);
    List<StudentDetail> actual = sut.searchStudentList();
    verify(repository, times(1)).search();
    verify(repository, times(1)).searchStudentCourses();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);
  }

  @Test
  void 受講生IDを指定して詳細情報を取得ができること() {
    int id = 999;
    Student student = new Student();
    student.setId(id);

    when(repository.searchById(id)).thenReturn(student);
    when(repository.searchStudentCourseList(id)).thenReturn(new ArrayList<>());

    StudentDetail expected = new StudentDetail(student, new ArrayList<>());
    StudentDetail actual = sut.searchStudent(id);

    verify(repository, times(1)).searchById(id);
    verify(repository, times(1)).searchStudentCourseList(id);
    Assertions.assertEquals(expected.getStudent().getId(), actual.getStudent().getId());
  }

  @Test
  void 受講生情報と受講コース情報を一緒に更新ができること() {
    StudentService sut = new StudentService(repository, converter);
    Student student = new Student();
    student.setId(1);
    student.setName("ICE");
    StudentCourse course1 = new StudentCourse();
    List<StudentCourse> courseList = new ArrayList<>();
    courseList.add(course1);
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentsCourseList(courseList);
    sut.updateStudent(studentDetail);
    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(1)).updateStudentCourse(course1);

    StudentDetail expected = new StudentDetail();
    expected.setStudent(student);
    expected.setStudentsCourseList(courseList);
    assertEquals(expected, studentDetail);

  }

  @Test
  void 受講生詳細の登録ができること() {
    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);
    sut.registerStudent(studentDetail);
    verify(repository, times(1)).registerStudent(student);
    verify(repository, times(1)).registerStudentCourses(studentCourse);
  }


  @Test
  void コースに受講生IDと開始_終了日の設定ができること() {
    int id = 999;
    Student student = new Student();
    student.setId(id);
    StudentCourse studentCourse = new StudentCourse();
    LocalDateTime before = LocalDateTime.now();
    sut.initStudentsCourse(studentCourse, student.getId());
    LocalDateTime after = LocalDateTime.now();
    assertEquals(id, studentCourse.getStudentId());
    assertTrue(!studentCourse.getStartDate().isBefore(before));
    assertTrue(!studentCourse.getStartDate().isAfter(after));
    assertEquals(studentCourse.getStartDate().plusYears(1), studentCourse.getEndDate());
  }

  @Test
  void 受講生コース情報の初期値が正しくの設定ができること() {
    int id = 999;
    Student student = new Student();
    student.setId(id);
    StudentCourse studentCourse = new StudentCourse();
    sut.initStudentsCourse(studentCourse, student.getId());
    assertEquals(id, studentCourse.getStudentId());
    assertEquals(LocalDateTime.now().getHour(), studentCourse.getStartDate().getHour());
    assertEquals(LocalDateTime.now().plusYears(1).getYear(), studentCourse.getEndDate().getYear());
  }
}
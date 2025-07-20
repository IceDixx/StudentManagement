package raisetech.Student.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);
    List<StudentDetail> actual = sut.searchStudentList();
    verify(repository, times(1)).search();
    verify(repository, times(1)).searchStudentCourseList();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);
  }

  @Test
  void 受講生詳細検索です() {
    int id = 999;
    Student student = new Student();
    student.setId(id);
    when(repository.searchById(id)).thenReturn(student);
    when(repository.searchStudentCourseList()).thenReturn(new ArrayList<>());
    StudentDetail expected = new StudentDetail(student, new ArrayList<>());
    StudentDetail actual = sut.searchStudent(id);
    verify(repository, times(1)).searchById(id);
    verify(repository, times(1)).searchStudentCourseList();
    Assertions.assertEquals(expected.getStudent().getId(), actual.getStudent().getId());
  }

  @Test
  void 受講生と受講コース情報をそれぞれ更新します() {
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

    assertEquals(1, course1.getStudentId());

  }

  @Test
  void 受講生詳細の登録を行います() {
    StudentService sut = new StudentService(repository, converter);
    Student student = new Student();
    student.setId(1);
    student.setName("ICE");
    StudentCourse course = new StudentCourse();
    List<StudentCourse> courseList = new ArrayList<>();
    courseList.add(course);
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentsCourseList(courseList);
    StudentDetail result = sut.registerStudent(studentDetail);
    verify(repository, times(1)).registerStudent(student);
    assertEquals(1, course.getStudentId());
    verify(repository, times(1)).registerStudentCourses(course);
    assertEquals(studentDetail, result);
  }

  @Test
  void 受講生コース情報を登録する際の初期情報を設定() {
    LocalDate now = LocalDate.now();
    Student student = new Student();
    student.setId(1);
    StudentCourse course = new StudentCourse();
    StudentService.initStudentsCourse(course, student);
    assertEquals(1, course.getStudentId());
    assertEquals(now, course.getStartDate());
    assertEquals(now.plusYears(1), course.getEndDate());
  }
}
package raisetech.Student.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.reset;
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
import raisetech.Student.management.data.CourseStatus;
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
    List<CourseStatus> courseStatuses = new ArrayList<>();
    when(repository.search()).thenReturn(studentList);
    when(repository.searchStudentCourses()).thenReturn(studentCourseList);
    when(repository.searchCourseStatuses()).thenReturn(courseStatuses);
    List<StudentDetail> actual = sut.searchStudentList();
    verify(repository, times(1)).search();
    verify(repository, times(1)).searchStudentCourses();
    verify(repository, times(1)).searchCourseStatuses();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList,
        courseStatuses);
  }

  @Test
  void 受講生IDを指定して詳細情報を取得ができること() {
    int id = 999;

    Student student = new Student();
    student.setId(id);
    List<StudentCourse> studentCourseList = new ArrayList<>();
    List<CourseStatus> courseStatusList = new ArrayList<>();

    when(repository.searchById(id)).thenReturn(student);
    when(repository.searchStudentCourseList(id)).thenReturn(studentCourseList);
    when(repository.searchCourseStatusesList(id)).thenReturn(courseStatusList);

    StudentDetail expected = new StudentDetail(student, studentCourseList, courseStatusList);
    StudentDetail actual = sut.searchStudent(id);

    verify(repository, times(1)).searchById(id);
    verify(repository, times(1)).searchStudentCourseList(id);
    verify(repository, times(1)).searchCourseStatusesList(id);

    Assertions.assertEquals(expected.getStudent().getId(), actual.getStudent().getId());
    Assertions.assertEquals(expected.getStudentsCourseList(), actual.getStudentsCourseList());
    Assertions.assertEquals(expected.getCourseStatusList(), actual.getCourseStatusList());
  }

  @Test
  void 受講生情報と受講コース情報を一緒に更新ができること() {
    StudentService sut = new StudentService(repository, converter);
    Student student = new Student();
    student.setId(1);
    student.setName("ICE");

    StudentCourse course1 = new StudentCourse();
    course1.setCourseId(1);
    List<StudentCourse> courseList = new ArrayList<>();
    courseList.add(course1);

    CourseStatus courseStatus = new CourseStatus();
    courseStatus.setCourseId(1);
    courseStatus.setStatus("本申込");
    List<CourseStatus> courseStatusList = new ArrayList<>();
    courseStatusList.add(courseStatus);

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentsCourseList(courseList);
    studentDetail.setCourseStatusList(courseStatusList);

    sut.updateStudent(studentDetail);

    studentDetail.setCourseStatusList(courseStatusList);
    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(1)).updateStudentCourse(course1);
    verify(repository, times(1)).updateCourseStatus(1, "本申込");

    StudentDetail expected = new StudentDetail();
    expected.setStudent(student);
    expected.setStudentsCourseList(courseList);
    expected.setCourseStatusList(courseStatusList);
    assertEquals(expected.getStudent().getId(), studentDetail.getStudent().getId());
    assertEquals(expected.getStudentsCourseList(), studentDetail.getStudentsCourseList());
    assertEquals(expected.getCourseStatusList(), studentDetail.getCourseStatusList());
  }

  @Test
  void 受講生詳細の登録ができること() {
    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    List<CourseStatus> courseStatusList = new ArrayList<>();
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList, courseStatusList);
    sut.registerStudent(studentDetail);
    verify(repository, times(1)).registerStudent(student);
    verify(repository, times(1)).registerStudentCourses(studentCourse);
    verify(repository, times(1)).registerCourseStatus(any(CourseStatus.class));
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

  @Test
  void 条件指定で受講生を検索し詳細が返ってくることこと() {
    Student student = new Student();
    student.setId(1);
    List<Student> students = List.of(student);

    when(repository.searchByCondition(null, null, null)).thenReturn(students);
    when(repository.searchStudentCourses()).thenReturn(new ArrayList<>());
    when(repository.searchCourseStatusesList(1)).thenReturn(new ArrayList<>());
    when(converter.convertStudentDetails(anyList(), anyList(), anyList())).thenReturn(
        new ArrayList<>());

    sut.searchStudentsByCondition(null, null, null);

    verify(repository, times(1)).searchByCondition(null, null, null);
    verify(repository, times(1)).searchStudentCourses();
    verify(repository, times(1)).searchCourseStatusesList(1);
    verify(converter, times(1)).convertStudentDetails(students, new ArrayList<>(),
        new ArrayList<>());
  }

  @Test
  void 条件指定で受講生検索入力チェックをすること() {
    String[] names = {null, "", " "};
    String[] emails = {null, "", "invalid_email"};
    Integer[] ages = {null, -1, 200};

    for (String name : names) {
      for (String email : emails) {
        for (Integer age : ages) {
          reset(repository);
          when(repository.searchByCondition(name, email, age)).thenReturn(new ArrayList<>());
          when(repository.searchStudentCourses()).thenReturn(new ArrayList<>());

          sut.searchStudentsByCondition(name, email, age);

          verify(repository, times(1)).searchByCondition(name, email, age);
          verify(repository, times(1)).searchStudentCourses();
        }
      }
    }
  }

  @Test
  void コースステータスを更新すること() {
    sut.updateCourseStatus(1, "受講中");
    verify(repository, times(1)).updateCourseStatus(1, "受講中");
  }

  @Test
  void 受講生を論理削除すること() {
    sut.vacantStudent(1);
    verify(repository, times(1)).vacantStudent(1);
  }

  @Test
  void すべての学生の受講ステータスを確認できること() {
    List<CourseStatus> courseStatuses = new ArrayList<>();
    CourseStatus Test1 = new CourseStatus();
    Test1.setId(1);
    Test1.setStatus("受講中");
    CourseStatus Test2 = new CourseStatus();
    Test2.setId(2);
    Test2.setStatus("仮申込");
    courseStatuses.add(Test1);
    courseStatuses.add(Test2);

    when(repository.searchCourseStatuses()).thenReturn(courseStatuses);

    List<CourseStatus> result = sut.getAllCourseStatuses();

    assertEquals(2, result.size());
    assertEquals("受講中", result.get(0).getStatus());
    assertEquals("仮申込", result.get(1).getStatus());

    verify(repository, times(1)).searchCourseStatuses();
  }
}

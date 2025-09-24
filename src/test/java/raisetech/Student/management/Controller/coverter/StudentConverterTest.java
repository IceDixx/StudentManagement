package raisetech.Student.management.Controller.coverter;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.Student.management.data.CourseStatus;
import raisetech.Student.management.data.Student;
import raisetech.Student.management.data.StudentCourse;
import raisetech.Student.management.domain.StudentDetail;

class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void before() {
    sut = new StudentConverter();
  }

  @Test
  void 受講生リストと受講生コース情報のリストを渡して受講生詳細のリストが作成できること() {
    Student student = createStudent();

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setCourseId(1);
    studentCourse.setStudentId(1);
    studentCourse.setCourseName("JAVA");
    studentCourse.setStartDate(LocalDateTime.now());
    studentCourse.setEndDate(LocalDateTime.now().plusYears(1));
    CourseStatus courseStatus = new CourseStatus();

    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    List<CourseStatus> courseStatusesList = List.of(courseStatus);
    List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList,
        courseStatusesList);

    assertThat(actual.get(0).getStudent()).isEqualTo(student);
    assertThat(actual.get(0).getStudentsCourseList()).isEqualTo(studentCourseList);

  }

  @Test
  void 受講生リストと受講生リストのリストを渡した時紐づかない受講生コース情報は除外されること() {
    Student student = createStudent();

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setCourseId(1);
    studentCourse.setStudentId(2);
    studentCourse.setCourseName("JAVA");
    studentCourse.setStartDate(LocalDateTime.now());
    studentCourse.setEndDate(LocalDateTime.now().plusYears(1));

    CourseStatus courseStatus = new CourseStatus();
    courseStatus.setStatus("仮申込");

    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    List<CourseStatus> courseStatuses = List.of(courseStatus);
    List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList,
        courseStatuses);
    assertThat(actual.get(0).getStudent()).isEqualTo(student);
    assertThat(actual.get(0).getStudentsCourseList().isEmpty());
  }


  private static Student createStudent() {
    Student student = new Student();
    student.setId(1);
    student.setName("ICE_DIXX");
    student.setAge(23);
    student.setEmail("123@gmail.com");
    student.setGender("Male");
    student.setNickname("ICE");
    student.setAddress("KAWASAKI");
    student.setRemark("");
    student.setDeleted(false);
    return student;
  }
}
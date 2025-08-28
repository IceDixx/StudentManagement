package raisetech.Student.management.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.Student.management.data.Student;
import raisetech.Student.management.data.StudentCourse;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索を行えること() {
    List<Student> actual = sut.search();
    assertThat(actual.size()).isEqualTo(3);
  }

  @Test
  void 受講生の登録ができること() {
    Student student = new Student();
    student.setId(1);
    student.setName("LINKIN");
    student.setAge(23);
    student.setEmail("123@gmail.com");
    student.setGender("Male");
    student.setNickname("LINK");
    student.setAddress("KAWASAKI");
    student.setRemark("");
    student.setDeleted(false);

    sut.registerStudent(student);
    List<Student> actual = sut.search();
    assertThat(actual.size()).isEqualTo(4);
  }

  @Test
  void 受講生のIDで指定しその受講生の一覧を取れること() {
    Student actual = sut.searchById(1);
    assertThat(actual.getId()).isEqualTo(1);
  }

  @Test
  void 受講生IDに紐づく受講コース情報を検索できること() {
    Student student = new Student();
    student.setId(1);
    List<StudentCourse> actual = sut.searchStudentCourseList(student.getId());

    assertThat(actual.size()).isEqualTo(1);
  }

  @Test
  void 受講生IDを指定し受講生の詳細を更新すること() {
    Student student = new Student();
    student.setId(1);
    student.setName("LINKIN");
    student.setAge(23);
    student.setEmail("123@gmail.com");
    student.setGender("Male");
    student.setNickname("LINK");
    student.setAddress("KAWASAKI");
    student.setRemark("");
    student.setDeleted(false);
    sut.updateStudent(student);
    Student updated = sut.searchById(1);
    assertThat(updated.getId()).isEqualTo(1);
    assertThat(updated.getName()).isEqualTo("LINKIN");
    assertThat(updated.getAge()).isEqualTo(23);
    assertThat(updated.getEmail()).isEqualTo("123@gmail.com");
    assertThat(updated.getGender()).isEqualTo("Male");
    assertThat(updated.getNickname()).isEqualTo("LINK");
    assertThat(updated.getAddress()).isEqualTo("KAWASAKI");
    assertThat(updated.getRemark()).isEqualTo("");
    assertThat(updated.isDeleted()).isFalse();
  }

  @Test
  void 受講コースIDを指定し受講コース名を更新すること() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setCourseId(1);
    studentCourse.setCourseName("Python");
    sut.updateStudentCourse(studentCourse);
    StudentCourse updated = sut.searchCourseID(1);
    assertThat(updated.getCourseName()).isEqualTo("Python");
  }

  @Test
  void 受講コース情報を新規登録すること() {
    LocalDateTime startDate = LocalDateTime.now();
    LocalDateTime endDate = startDate.plusYears(1);

    Student student = new Student();
    student.setId(1);
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId(student.getId());
    studentCourse.setCourseName("Ruby");
    studentCourse.setStartDate(startDate);
    studentCourse.setEndDate(endDate);

    sut.registerStudentCourses(studentCourse);

    assertThat(student.getId()).isEqualTo(1);
    assertThat(studentCourse.getCourseName()).isEqualTo("Ruby");
    assertThat(studentCourse.getStartDate()).isEqualTo(startDate);
    assertThat(studentCourse.getEndDate()).isEqualTo(endDate);
  }
}

package raisetech.Student.management.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
  void ない学生のIDを取る時にNULLで帰った来ること() {
    Student actual = sut.searchById(9999);
    assertThat(actual).isNull();
  }

  @Test
  void 入力項目がNULLの時に失敗で帰って来ること() {
    Student student = new Student();
    student.setName(null);
    student.setAge(-1);
    student.setEmail(null);
    student.setGender(null);
    student.setNickname(null);
    student.setAddress(null);
    student.setRemark(null);
    student.setDeleted(false);
    assertThatThrownBy(() -> sut.registerStudent(student))
        .isInstanceOf(org.springframework.dao.DataIntegrityViolationException.class);
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
  void 存在しない受講生IDを更新しても件数0であること() {
    Student student = new Student();
    student.setId(9999);
    student.setName("Ghost");
    student.setAge(30);
    student.setEmail("ghost@mail.com");
    student.setGender("Other");
    student.setNickname("GHOST");
    student.setAddress("Nowhere");
    student.setRemark("");
    student.setDeleted(false);

    sut.updateStudent(student);
    Student updated = sut.searchById(student.getId());
    assertThat(updated).isNull();
  }

  @Test
  void 削除フラグを立てた受講生が検索結果に含まれないこと() {
    Student student = new Student();
    student.setId(2);
    student.setName("ToBeDeleted");
    student.setAge(25);
    student.setEmail("delete@mail.com");
    student.setGender("Male");
    student.setNickname("DEL");
    student.setAddress("Nagoya");
    student.setRemark("");
    student.setDeleted(true);

    sut.updateStudent(student);

    Student updated = sut.searchById(2);
    assertThat(updated.isDeleted()).isTrue();
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

  @Test
  void 存在しない学生のIDで登録する時にエラーが返ってくること() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId(999);
    studentCourse.setCourseName("JAVA");
    studentCourse.setStartDate(LocalDateTime.now());
    studentCourse.setEndDate(LocalDateTime.now().plusYears(1));
    assertThatThrownBy(() -> sut.registerStudentCourses(studentCourse))
        .isInstanceOf(DataIntegrityViolationException.class);
  }
}

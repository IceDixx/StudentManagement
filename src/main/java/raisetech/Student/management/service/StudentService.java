package raisetech.Student.management.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.Student.management.Controller.coverter.StudentConverter;
import raisetech.Student.management.data.Student;
import raisetech.Student.management.data.StudentCourse;
import raisetech.Student.management.domain.StudentDetail;
import raisetech.Student.management.repository.StudentRepository;

/*
 *受講生を取り払うサービスです
 * 受講生の検索や登録・更新を行う
 */
@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;


  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;

  }

  /**
   * 受講生詳細の一覧検索を行う 全件検索を行う、条件指定なし
   *
   * @return　受講生詳細一覧（全件）
   */

  public List<Student> getAllStudents() {

    return repository.getAllStudents();
  }

  public List<StudentDetail> getStudentCourses() {
    List<Student> studentList = repository.getAllStudents();
    List<StudentCourse> studentsCoursesList = repository.getStudentCourses();
    return converter.convertStudentDetails(studentList, studentsCoursesList);

  }

  /**
   * 受講生詳細検索です。 IDに紐づく受講生情報を取得した後、その受講生に紐づく受講生一覧を取得して設定します。
   *
   * @param id 受講生ID
   * @return　受講生詳細
   */
  @Transactional
  public StudentDetail searchStudent(int id) {
    Student student = repository.searchById(id);
    List<StudentCourse> studentCourse = repository.searchStudentCourse(student.getId());
    return new StudentDetail(student, studentCourse);
  }

  public void vacantStudent(int id) {
    repository.vacantStudent(id);
  }

  public Student searchById(int id) {
    return repository.searchById(id);
  }

  public void updateStudent(Student student) {
    repository.updateStudent(student);
  }

  /**
   * 受講生詳細の登録を行います。 受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づける値や日付（コース開始日、終了日を設定
   *
   * @param studentDetail
   * @return　登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    repository.registerStudent(student);
    studentDetail.getStudentsCourseList().forEach(studentCourse -> {
      initStudentsCourse(studentCourse, student);
      repository.registerStudentCourses(studentCourse);
    });
    return studentDetail;
  }

  /**
   * 受講生コース情報を登録する際の初期情報を設定
   *
   * @param studentsCourses 　受講生コース情報
   * @param student         　受講生
   */
  private static void initStudentsCourse(StudentCourse studentsCourses, Student student) {
    LocalDate now = LocalDate.now();
    studentsCourses.setStudentId(student.getId());
    studentsCourses.setStartDate(now);
    studentsCourses.setEndDate(now.plusYears(1));
  }

  /**
   * 受講生詳細の更新を行います。 受講生と受講コース情報をそれぞれ更新します。
   *
   * @param studentDetail 　受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    studentDetail.getStudentsCourseList().forEach(studentCourses -> {
      studentCourses.setStudentId(studentDetail.getStudent().getId());
      repository.updateStudentCourse(studentCourses);
    });
  }
}

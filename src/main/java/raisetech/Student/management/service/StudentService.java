package raisetech.Student.management.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.Student.management.Controller.coverter.StudentConverter;
import raisetech.Student.management.data.Student;
import raisetech.Student.management.data.StudentsCourses;
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
   * 受講生の一覧検索を行う 全件検索を行う、条件指定なし
   *
   * @return　受講生一覧（全件）
   */

  public List<Student> getAllStudents() {

    return repository.getAllStudents();
  }

  public List<StudentDetail> getStudentCourses() {
    List<Student> studentList = repository.getAllStudents();
    List<StudentsCourses> studentsCoursesList = repository.getStudentCourses();
    return converter.convertStudentDetails(studentList, studentsCoursesList);

  }

  /**
   * 受講生検索です。 IDに紐づく受講生情報を取得した後、その受講生に紐づく受講生一覧を取得して設定します。
   *
   * @param id 受講生ID
   * @return　受講生
   */
  @Transactional
  public StudentDetail searchStudent(int id) {
    Student student = repository.searchById(id);
    List<StudentsCourses> studentsCourses = repository.searchStudentCourses(student.getId());
    return new StudentDetail(student, studentsCourses);
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

  //学生を登録//
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    repository.registerStudent(studentDetail.getStudent());
    for (StudentsCourses studentsCourses : studentDetail.getStudentsCourses()) {
      studentsCourses.setStudentId(studentDetail.getStudent().getId());
      studentsCourses.setStartDate(LocalDate.now());
      studentsCourses.setEndDate(LocalDate.now().plusYears(1));
      repository.registerStudentCourses(studentsCourses);
    }
    return studentDetail;
  }

  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    for (StudentsCourses studentsCourses : studentDetail.getStudentsCourses()) {
      studentsCourses.setStudentId(studentDetail.getStudent().getId());
      repository.updateStudentCourses(studentsCourses);
    }
  }
}

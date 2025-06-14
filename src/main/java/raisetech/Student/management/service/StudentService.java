package raisetech.Student.management.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.Student.management.data.Student;
import raisetech.Student.management.data.StudentsCourses;
import raisetech.Student.management.domain.StudentDetail;
import raisetech.Student.management.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;


  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;

  }

  public List<Student> getAllStudents() {

    return repository.getAllStudents();
  }

  public List<StudentsCourses> getStudentCourses() {
    return repository.getStudentCourses();
  }

  public StudentDetail searchStudent(int id) {
    Student student = repository.searchById(id);
    List<StudentsCourses> studentsCourses = repository.searchStudentCourses(student.getId());
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentsCourses(studentsCourses);
    return studentDetail;
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
  public void registerStudent(StudentDetail studentDetail) {
    repository.registerStudent(studentDetail.getStudent());
    for (StudentsCourses studentsCourses : studentDetail.getStudentsCourses()) {
      studentsCourses.setStudentId(studentDetail.getStudent().getId());
      studentsCourses.setStartDate(LocalDate.now());
      studentsCourses.setEndDate(LocalDate.now().plusYears(1));
      repository.registerStudentCourses(studentsCourses);
    }
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

package raisetech.Student.management.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.Student.management.data.Student;
import raisetech.Student.management.data.StudentCourses;
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

  //学生を更新//
  public void registerStudent(Student student) {
    repository.registerStudent(student.getName(),
        student.getNickname(),
        student.getEmail(),
        student.getAddress(),
        student.getAge(),
        student.getGender(),
        student.getRemark());
  }

  //受講を更新//
  public void registerStudentCourses(StudentCourses studentCourses) {
    repository.registerStudentCourses(
        studentCourses.getStudentId(),
        studentCourses.getCourseName(),
        studentCourses.getStartDate(),
        studentCourses.getEndDate()
    );
  }

  public void vacantStudent(String id) {
    repository.vacantStudent(id);
  }

  public List<Student> getYearsStudent(int age) {
    return repository.getYearsStudent(age);
  }

  public List<StudentCourses> getStudentCourses() {

    return repository.getStudentCourses();
  }

  public List<StudentCourses> getSelectCourses(String CourseName) {
    return repository.getSelectCourse(CourseName);
  }
}

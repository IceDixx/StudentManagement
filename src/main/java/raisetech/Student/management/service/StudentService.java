package raisetech.Student.management.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.Student.management.data.Student;
import raisetech.Student.management.data.studentCourses;
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

  public List<Student> getYearsStudent(int age) {
    return repository.getYearsStudent(age);
  }

  public List<studentCourses> getstudentCourses() {
    return repository.getstudentCourses();
  }

  public List<studentCourses> getSelectCourses(String CourseName) {
    return repository.getSelectCourse(CourseName);
  }
}

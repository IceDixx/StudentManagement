package raisetech.Student.management.Controller.coverter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.Student.management.data.Student;
import raisetech.Student.management.data.StudentCourses;
import raisetech.Student.management.domain.StudentDetail;

@Component
public class StudentConverter {

  public List<StudentDetail> convertStudentDetails(List<Student> students,
      List<StudentCourses> studentCourses) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    students.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);
      
      List<StudentCourses> covertStudentCourses = studentCourses.stream()
          .filter(studentsCourse -> student.getId().equals(studentsCourse.getStudentId()))
          .collect(Collectors.toList());
      studentDetail.setStudentsCourses(covertStudentCourses);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }
}

package raisetech.Student.management.Controller.coverter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.Student.management.data.Student;
import raisetech.Student.management.data.StudentsCourses;
import raisetech.Student.management.domain.StudentDetail;

@Component
public class StudentConverter {

  public List<StudentDetail> convertStudentDetails(List<Student> students,
      List<StudentsCourses> studentsCourse) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    students.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      List<StudentsCourses> covertStudentsCourse = studentsCourse.stream()
          .filter(courses -> courses.getCourseId() == student.getId())
          .collect(Collectors.toList());
      studentDetail.setStudentsCourses(covertStudentsCourse);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }
}

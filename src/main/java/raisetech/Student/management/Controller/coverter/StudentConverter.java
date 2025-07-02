package raisetech.Student.management.Controller.coverter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.Student.management.data.Student;
import raisetech.Student.management.data.StudentsCourses;
import raisetech.Student.management.domain.StudentDetail;

/**
 * 受講生詳細を受講生や受講生コース情報、もしくはその逆の変換を行うコンバーターです。
 */
@Component
public class StudentConverter {

  /**
   * 受講生に紐づく受講コース情報をマッピングする 受講コース情報は受講生に対して複数存在するのでループを回して受講生詳細情報を組立る
   *
   * @param students       　受講生一覧
   * @param studentsCourse 　受講コース情報のリスト
   * @return　受講生詳細情報のリスト
   */
  public List<StudentDetail> convertStudentDetails(List<Student> students,
      List<StudentsCourses> studentsCourse) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    students.stream()
        .filter(student -> !student.isDeleted())
        .forEach(student -> {
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

package raisetech.Student.management.Controller.coverter;

import io.swagger.v3.oas.annotations.Operation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.Student.management.data.CourseStatus;
import raisetech.Student.management.data.Student;
import raisetech.Student.management.data.StudentCourse;
import raisetech.Student.management.domain.StudentDetail;

/**
 * 受講生詳細を受講生や受講生コース情報、もしくはその逆の変換を行うコンバーターです。
 */

@Component
public class StudentConverter {

  /**
   * 受講生に紐づく受講コース情報をマッピングする 受講コース情報は受講生に対して複数存在するのでループを回して受講生詳細情報を組立る
   *
   * @param studentList       受講生一覧
   * @param studentCourseList 受講コース情報のリスト
   * @return　受講生詳細情報のリスト
   */
  @Operation(summary = "Converter Student&Courses", description = "受講生に紐づく受講コース情報をマッピングする 受講コース情報は受講生に対して複数存在するのでループを回して受講生詳細情報を組立る")
  public List<StudentDetail> convertStudentDetails(
      List<Student> studentList,
      List<StudentCourse> studentCourseList,
      List<CourseStatus> courseStatusList) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    studentList.stream()
        .filter(student -> !student.isDeleted())
        .forEach(student -> {
          StudentDetail studentDetail = new StudentDetail();
          studentDetail.setStudent(student);
          List<StudentCourse> covertStudentCourseList = studentCourseList.stream()
              .filter(courses -> courses.getStudentId() == student.getId())
              .collect(Collectors.toList());
          studentDetail.setStudentsCourseList(covertStudentCourseList);

          Set<Integer> courseId = covertStudentCourseList.stream()
              .map(StudentCourse::getCourseId)
              .collect(Collectors.toSet());

          List<CourseStatus> relatedStatuses = courseStatusList.stream()
              .filter(status -> courseId.contains(status.getCourseId()))
              .collect(Collectors.toList());
          studentDetail.setCourseStatusList(relatedStatuses);

          studentDetails.add(studentDetail);
        });
    return studentDetails;
  }
}

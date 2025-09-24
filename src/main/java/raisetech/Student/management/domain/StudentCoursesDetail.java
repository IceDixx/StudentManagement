package raisetech.Student.management.domain;

import jakarta.validation.Valid;
import java.util.List;
import raisetech.Student.management.data.CourseStatus;
import raisetech.Student.management.data.Student;
import raisetech.Student.management.data.StudentCourse;

public class StudentCoursesDetail {

  private Student student;
  private List<StudentCourse> studentsCourses;
  @Valid
  private List<CourseStatus> courseStatusList;

}

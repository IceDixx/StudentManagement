package raisetech.Student.management.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.Student.management.data.CourseStatus;
import raisetech.Student.management.data.Student;
import raisetech.Student.management.data.StudentCourse;

@Schema(description = "StudentDetail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {

  @Valid
  private Student student;
  @Valid
  private List<StudentCourse> studentsCourseList;
  @Valid
  private List<CourseStatus> courseStatusList;

}

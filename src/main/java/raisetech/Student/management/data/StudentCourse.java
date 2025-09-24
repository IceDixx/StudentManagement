package raisetech.Student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "StudentCoursesDetail")
@Getter
@Setter
public class StudentCourse {

  private int courseId;
  private int studentId;
  private String courseName;
  private LocalDate startDate;
  private LocalDate endDate;

}

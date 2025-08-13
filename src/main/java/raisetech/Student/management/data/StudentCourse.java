package raisetech.Student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "StudentCoursesDetail")
@Getter
@Setter
public class StudentCourse {

  private int courseId;
  private int studentId;
  private String courseName;
  private LocalDateTime startDate;
  private LocalDateTime endDate;

}

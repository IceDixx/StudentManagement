package raisetech.Student.management.data;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourses {

  private String id;
  private String studentId;
  private String CourseName;
  private LocalDateTime StartDate;
  private LocalDateTime EndDate;

}

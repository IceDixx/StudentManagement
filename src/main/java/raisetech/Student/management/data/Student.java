package raisetech.Student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Student")
@Getter
@Setter
public class Student {

  @NotBlank
  private String name;

  @NotNull
  private int age;


  @Email(message = "onlyEmail")
  @NotBlank
  private String email;

  @NotBlank
  private String nickname;

  @NotBlank
  private String address;


  private int id;

  @NotBlank
  private String gender;

  private int coursesId;

  private String coursesName;

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  private String remark;

  private boolean isDeleted;

}
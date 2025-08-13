package raisetech.Student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

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

  @NotNull(message = "NoAvailableNumber")
  @Min(value = 1, message = "ID must be greater than 0")
  private int id;

  @NotBlank
  private String gender;

  private int coursesId;

  private String coursesName;

  private Date startDate;

  private Date endDate;

  private String remark;

  private boolean isDeleted;

}
package raisetech.Student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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
  @NotBlank
  @Email
  private String email;
  @NotBlank
  private String nickname;
  @NotBlank
  private String address;

  private int id;
  @NotBlank
  private String gender;

  private int coursesId;
  @NotBlank
  private String coursesName;
  @NotNull
  private Date startDate;
  @NotNull
  private Date endDate;
  @NotBlank
  private String remark;

  private boolean isDeleted;

}
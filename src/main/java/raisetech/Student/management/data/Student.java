package raisetech.Student.management.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  @NotBlank
  private String name;
  @NotBlank
  private int age;
  @NotBlank
  @Email
  private String email;
  @NotBlank
  private String nickname;
  @NotBlank
  private String address;
  @NotBlank
  private int id;
  @NotBlank
  private String gender;
  @NotBlank
  private int coursesId;
  @NotBlank
  private String coursesName;
  @NotBlank
  private Date startDate;
  @NotBlank
  private Date endDate;
  @NotBlank
  private String remark;
  @NotBlank
  private boolean isDeleted;

}
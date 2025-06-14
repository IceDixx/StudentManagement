package raisetech.Student.management.data;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  private String name;
  private int age;
  private String email;
  private String nickname;
  private String address;
  private int id;
  private String gender;
  private int coursesId;

  private String coursesName;
  private Date startDate;
  private Date endDate;
  private String remark;
  private boolean isDeleted;

}
package raisetech.Student.management;

import java.util.Date;

public class Student {

  private String name;
  private int age;
  private String email;
  private String NickName;
  private String address;
  private int id;
  private String gender;
  private int CoursesId;
  private int StudentId;
  private String CoursesName;
  private Date StartDate;
  private Date EndDate;

  public int getCoursesId() {
    return CoursesId;
  }

  public void setCoursesId(int CoursesId) {
    this.CoursesId = CoursesId;
  }

  public int getStudentId() {
    return StudentId;
  }

  public void setStudentId(int StudentId) {
    this.StudentId = StudentId;
  }

  public String getCourseName() {
    return CoursesName;
  }

  public void setCoursesName(String CoursesName) {
    this.CoursesName = CoursesName;
  }

  public Date getStartDate() {
    return StartDate;
  }

  public void setStartDate(Date StartDate) {
    this.StartDate = StartDate;
  }

  public Date getEndDate() {
    return EndDate;
  }

  public void setEndDate(Date EndDate) {
    this.EndDate = EndDate;
  }

  public String getNickName() {
    return NickName;
  }

  public void setNickName(String NickName) {
    this.NickName = NickName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}
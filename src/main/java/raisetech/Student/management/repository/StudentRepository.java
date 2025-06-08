package raisetech.Student.management.repository;

import java.time.LocalDate;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.Student.management.data.Student;
import raisetech.Student.management.data.StudentCourses;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM student WHERE name = #{name}")
  Student searchByName(String name);

  @Select("SELECT * FROM student WHERE age => #{age}")
  List<Student> getYearsStudent(@Param("age") int age);

  @Select("SELECT * FROM student_courses WHERE course_name = #{CourseName}")
  List<StudentCourses> getSelectCourse(@Param("CourseName") String CourseName);


  @Select("SELECT * FROM student")
  List<Student> getAllStudents();

  @Select("SELECT * FROM student_courses")
  List<StudentCourses> getStudentCourses();

  //fake delete
  @Update("UPDATE student SET is_deleted = true WHERE id = #{id}")
  void vacantStudent(@Param("id") String id);

  @Select("SELECT * FROM student WHERE id = #{id}")
  Student searchById(int id);


  @Select("SELECT * FROM student_courses WHERE course_id = #{course_id}")
  Student searchCourse(String courseName);

  @Select("SELECT * FROM student_courses WHERE course_id = #{course_id}")
  Student searchCourseID(int courseId);

  //newStudent//
  @Insert("INSERT INTO student (name, nickname, email, address, age, gender, remark) VALUES (#{name}, #{nickname}, #{email}, #{address}, #{age}, #{gender},#{remark})")
  void registerStudent(String name, String nickname, String email, String address, int age,
      String gender, String remark);

  //newStudentCourses//
  @Insert("INSERT INTO student_courses (student_id, course_name, start_date, end_date) " +
      "VALUES (#{studentId}, #{courseName}, #{startDate}, #{endDate})")
  void registerStudentCourses(int studentId, String courseName, LocalDate startDate,
      LocalDate endDate);


  @Update("UPDATE student_courses SET student_id=#{id} WHERE id = #{id}")
  void updateStudent(int id, String name);

  @Delete("DELETE FROM student WHERE id = #{id}")
  void deleteStudent(int id);


  @Delete("DELETE FROM student_courses WHERE course_name = #{course_name}")
  void deleteCourse(String courseName);
}

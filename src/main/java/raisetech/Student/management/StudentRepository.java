package raisetech.Student.management;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM student WHERE name = #{name}")
  Student searchByName(String name);


  @Select("SELECT * FROM student")
  List<Student> getAllStudents(String name);

  @Select("SELECT * FROM student WHERE id = #{id}")
  Student searchById(int id);

  @Select("SELECT * FROM student_courses WHERE course_id = #{course_id}")
  Student searchCourse(String course_name);

  @Select("SELECT * FROM student_courses WHERE course_id = #{course_id}")
  Student searchCourseID(int course_id);

  @Insert("INSERT INTO student (name, age, email, address, nickname, gender) " +
      "VALUES (#{name}, #{age}, #{email}, #{address}, #{nickname}, #{gender})")
  void registerStudent(String name, int age, String email, String address, String nickname,
      String gender);


  @Insert("INSERT INTO student_courses (student_id, course_name, start_date, end_date) " +
      "VALUES (#{student_id}, #{course_name}, #{start_date}, #{end_date})")
  void registerStudentCourses(int student_id, String course_name, Date start_date, Date end_date);


  @Update("UPDATE student_courses SET student_id=#{id} WHERE id = #{id}")
  void updateStudent(int id, String name);

  @Delete("DELETE FROM student WHERE id = #{id}")
  void deleteStudent(int id);


  @Delete("DELETE FROM student_courses WHERE course_name = #{course_name}")
  void deleteCourse(String course_name);
}

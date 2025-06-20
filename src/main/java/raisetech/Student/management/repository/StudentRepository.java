package raisetech.Student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.Student.management.data.Student;
import raisetech.Student.management.data.StudentsCourses;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM student WHERE is_deleted = false")
  List<Student> getAllStudents();

  @Select("SELECT * FROM student WHERE id = #{id}")
  Student searchById(int id);

  @Select("SELECT * FROM student_courses")
  List<StudentsCourses> getStudentCourses();

  @Select("SELECT * FROM student_courses WHERE student_id = #{studentId}")
  List<StudentsCourses> searchStudentCourses(int studentId);


  //fake delete
  @Update("UPDATE student SET is_deleted = true WHERE id = #{id}")
  void vacantStudent(@Param("id") int id);


  @Select("SELECT * FROM student_courses WHERE course_id = #{course_id}")
  Student searchCourse(String courseName);

  @Select("SELECT * FROM student_courses WHERE course_id = #{course_id}")
  Student searchCourseID(int courseId);

  @Select("SELECT * FROM student WHERE name = #{name}")
  Student searchByName(String name);

  //newStudent//
  @Insert("INSERT INTO student (name, nickname, email, address, age, gender, remark) VALUES (#{name}, #{nickname}, #{email}, #{address}, #{age}, #{gender},#{remark})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);

  //newStudentCourses//
  @Insert("INSERT INTO student_courses (student_id, course_name, start_date, end_date) " +
      "VALUES (#{studentId}, #{courseName}, #{startDate}, #{endDate})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentCourses(StudentsCourses studentsCourses);


  @Update("UPDATE student SET name=#{name}, age=#{age}, email=#{email}, nickname=#{nickname}, address=#{address}, gender=#{gender}, remark=#{remark}, is_deleted=#{isDeleted} WHERE id =#{id}")
  void updateStudent(Student student);

  @Update("UPDATE  student_courses SET course_name = #{courseName} WHERE course_ID=#{courseId}")
  void updateStudentCourses(StudentsCourses studentsCourses);


  @Delete("DELETE FROM student WHERE id = #{id}")
  void deleteStudent(int id);


  @Delete("DELETE FROM student_courses WHERE course_name = #{course_name}")
  void deleteCourse(String courseName);
}

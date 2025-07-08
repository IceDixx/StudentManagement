package raisetech.Student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import raisetech.Student.management.data.Student;
import raisetech.Student.management.data.StudentCourse;

/**
 * 受講生テーブルと受講生コース情報テーブルと紐づくrepository
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索を行います。
   *
   * @return受講生一覧（全件）
   */
  List<Student> getAllStudents();

  /**
   * 受講生の検索を行います。
   *
   * @param id 　受講生ID
   * @return　受講生
   */

  Student FindId(int id);

  Student searchById(int id);

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return　受講生のコース情報（全件）
   */
  List<StudentCourse> getStudentCourses();

  /**
   * 受講生IDに紐づく受講コース情報を検索します
   *
   * @param studentId 　受講ID
   * @return　受講生IDに紐づく受講コース情報
   */
  List<StudentCourse> searchStudentCourse(int studentId);


  //fake delete
  void vacantStudent(@Param("id") int id);


  @Select("SELECT * FROM student_courses WHERE course_id = #{course_id}")
  Student searchCourse(String courseName);

  @Select("SELECT * FROM student_courses WHERE course_id = #{course_id}")
  Student searchCourseID(int courseId);

  @Select("SELECT * FROM student WHERE name = #{name}")
  Student searchByName(String name);

  /**
   * 受講生を新規登録します IDに関しては自動採番を行う
   *
   * @param student 　受講生情報
   */
  void registerStudent(Student student);

  /**
   * 受講生コース情報を新規登録します。IDに関しては自動採番を行う
   *
   * @param studentsCourse 　受講生コース情報
   */

  void registerStudentCourses(StudentCourse studentsCourse);

  /**
   * 受講生を更新します
   *
   * @param student 　受講生
   */

  void updateStudent(Student student);

  /**
   * 受講コース名を更新します。
   *
   * @param studentsCourses 　受講コース情報
   */
  void updateStudentCourse(StudentCourse studentsCourses);


  @Delete("DELETE FROM student WHERE id = #{id}")
  void deleteStudent(int id);


  @Delete("DELETE FROM student_courses WHERE course_name = #{course_name}")
  void deleteCourse(String courseName);
}

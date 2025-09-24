  CREATE TABLE IF NOT EXISTS student(
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) ,
  nickname VARCHAR(255)NOT NULL,
  email VARCHAR(255) NOT NULL,
  address VARCHAR(255),
  age INT,
  gender VARCHAR(255),
  remark TEXT,
  is_deleted boolean DEFAULT FALSE);
  CREATE TABLE IF NOT EXISTS student_courses
  (
  course_id INT AUTO_INCREMENT PRIMARY KEY,
  student_id INT,
  course_name VARCHAR(255)NOT NULL,
  start_date DATE,
  end_date DATE,
  FOREIGN KEY (student_id) REFERENCES student(id)
  );
  CREATE TABLE IF NOT EXISTS course_status (
      course_id INT PRIMARY KEY,
      status ENUM('仮申込', '本申込', '受講中', '受講終了') NOT NULL,
      FOREIGN KEY (course_id) REFERENCES student_courses(course_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
  );
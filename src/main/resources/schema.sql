CREATE TABLE user (
  user_id INTEGER NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  national_id VARCHAR(8) NOT NULL,
  email VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  inactive TINYINT NOT NULL DEFAULT 0,
  profile_photo BLOB,
  CONSTRAINT user_100 PRIMARY KEY (user_id),
  CONSTRAINT user_301 UNIQUE (national_id),
  CONSTRAINT user_302 UNIQUE (email)
);

CREATE TABLE teacher (
  teacher_id INTEGER NOT NULL,
  CONSTRAINT teacher_100 PRIMARY KEY (teacher_id),
  CONSTRAINT teacher_201 FOREIGN KEY (teacher_id) REFERENCES user (user_id)
);

CREATE TABLE student (
  student_id INTEGER NOT NULL,
  CONSTRAINT student_100 PRIMARY KEY (student_id),
  CONSTRAINT student_201 FOREIGN KEY (student_id) REFERENCES user (user_id)
);

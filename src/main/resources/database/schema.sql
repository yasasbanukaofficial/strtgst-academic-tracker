DROP DATABASE IF EXISTS Strtgst;
CREATE DATABASE Strtgst;
USE Strtgst;

-- Table Student
CREATE TABLE student(
    stud_id INT AUTO_INCREMENT,
    stud_name VARCHAR(50),
    username VARCHAR(200) NOT NULL,
    email VARCHAR(200) NOT NULL,
    password VARCHAR(200) NOT NULL,
    profile_picture MEDIUMBLOB,
    date_of_birth DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (stud_id)
);

-- Table Subject
CREATE TABLE subject (
    sub_id VARCHAR(6) NOT NULL,
    stud_id INT AUTO_INCREMENT,
    sub_name VARCHAR(50) NOT NULL,
    total_marks INT,
    PRIMARY KEY (sub_id),
    FOREIGN KEY (stud_id) REFERENCES student(stud_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- Table Assignment
CREATE TABLE assignment (
    assignment_id INT AUTO_INCREMENT,
    sub_id VARCHAR(6) NOT NULL,
    assignment_name VARCHAR(255) NOT NULL,
    assignment_description VARCHAR(500),
    due_date DATE,
    assignment_status ENUM('pending', 'completed', 'overdue') DEFAULT 'pending',
    assignment_marks INT,
    PRIMARY KEY (assignment_id),
    FOREIGN KEY (sub_id) REFERENCES subject(sub_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- Table Grade
CREATE TABLE grade (
    grade_id INT AUTO_INCREMENT,
    grade VARCHAR(2),
    PRIMARY KEY (grade_id)
);

-- Table Lecture
CREATE TABLE lecture (
    lec_id INT AUTO_INCREMENT,
    sub_id VARCHAR(6) NOT NULL,
    date DATE NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    status ENUM('upcoming', 'ongoing', 'ended') DEFAULT 'upcoming',
    PRIMARY KEY (lec_id),
    FOREIGN KEY (sub_id) REFERENCES subject(sub_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- Table Exam
CREATE TABLE exam (
    exam_id INT AUTO_INCREMENT,
    sub_id VARCHAR(6) NOT NULL,
    date DATE NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    exam_type ENUM('written', 'practical', 'oral') DEFAULT 'written',
    PRIMARY KEY (exam_id),
    FOREIGN KEY (sub_id) REFERENCES subject(sub_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- Table TodoList
CREATE TABLE todo_list (
    todo_id INT AUTO_INCREMENT,
    stud_id INT NOT NULL,
    due_date DATE NOT NULL,
    task_name VARCHAR(255) NOT NULL,
    task_description VARCHAR(500),
    status ENUM('pending', 'completed', 'overdue') DEFAULT 'pending',
    PRIMARY KEY (todo_id),
    FOREIGN KEY (stud_id) REFERENCES student(stud_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- Table StudySession
CREATE TABLE study_session (
    ss_id INT AUTO_INCREMENT,
    ss_name VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    PRIMARY KEY (ss_id)
);

-- Table Events
CREATE TABLE event (
    event_id INT AUTO_INCREMENT,
    stud_id INT NOT NULL,
    event_name VARCHAR(255) NOT NULL,
    event_description VARCHAR(500),
    date DATE NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    status ENUM('upcoming', 'ongoing', 'ended') DEFAULT 'upcoming',
    PRIMARY KEY (event_id),
    FOREIGN KEY (stud_id) REFERENCES student(stud_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- * Associate Tables * --

-- Table Subject_Scores (Subject -> Grade) -> Grade History/Updating
CREATE TABLE subject_scores (
    score_id INT AUTO_INCREMENT,
    sub_id VARCHAR(6) NOT NULL,
    grade_id INT,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (score_id),
    FOREIGN KEY (sub_id) REFERENCES subject(sub_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (grade_id) REFERENCES grade(grade_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- Table Student Study Sessions (Student -> Study Sessions)
CREATE TABLE student_study_sessions (
    stud_ss_id INT AUTO_INCREMENT,
    stud_id INT NOT NULL,
    ss_id INT,
    PRIMARY KEY (stud_ss_id),
    FOREIGN KEY (stud_id) REFERENCES student(stud_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (ss_id) REFERENCES study_session(ss_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- Table Subject Study Sessions (Subject -> Study Sessions)
CREATE TABLE subject_study_sessions (
    subject_ss_id INT AUTO_INCREMENT,
    stud_id INT NOT NULL,
    ss_id INT,
    PRIMARY KEY (subject_ss_id),
    FOREIGN KEY (stud_id) REFERENCES student(stud_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (ss_id) REFERENCES study_session(ss_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);



/*
DROP TABLE student;
DROP TABLE subject;
DROP TABLE assignment;
DROP TABLE homework;
DROP TABLE grade;
DROP TABLE lecture;

DROP TABLE enrollment;
DROP TABLE subject_scores;
DROP TABLE subject_assignments;
DROP TABLE subject_homework;
DROP TABLE lecture_session;
DROP TABLE subject_exam;
DROP TABLE student_tasks;
DROP TABLE student_study_sessions;
DROP TABLE student_events;

*/
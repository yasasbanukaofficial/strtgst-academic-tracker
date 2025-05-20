DROP DATABASE IF EXISTS Strtgst;
CREATE DATABASE Strtgst;
USE Strtgst;

-- Table Student
CREATE TABLE student(
    stud_id VARCHAR(4) NOT NULL,
    stud_name VARCHAR(50) DEFAULT 'Student',
    username VARCHAR(200) NOT NULL,
    email VARCHAR(200) NOT NULL,
    password VARCHAR(200) NOT NULL,
    date_of_birth TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (stud_id)
);

-- Table Subject
CREATE TABLE subject (
    sub_id VARCHAR(5) NOT NULL,
    stud_id VARCHAR(4),
    sub_name VARCHAR(50) NOT NULL,
    total_marks INT,
    PRIMARY KEY (sub_id),
    FOREIGN KEY (stud_id) REFERENCES student(stud_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- Table Assignment
CREATE TABLE assignment (
    assignment_id VARCHAR(4),
    assignment_name VARCHAR(255) NOT NULL,
    assignment_description VARCHAR(500) DEFAULT NULL,
    assignment_marks INT,
    sub_name VARCHAR(50),
    due_date DATE,
    assignment_status ENUM('pending', 'completed', 'overdue') DEFAULT 'pending',
    PRIMARY KEY (assignment_id)
);

-- Table Grade
CREATE TABLE grade (
    grade_id VARCHAR(5),
    marks INT,
    grade VARCHAR(2),
    received_date DATE,
    PRIMARY KEY (grade_id)
);

-- Table Lecture
CREATE TABLE lecture (
    lec_id INT AUTO_INCREMENT,
    sub_id VARCHAR(5),
    lec_Name VARCHAR(150),
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
    sub_id VARCHAR(5),
    date DATE NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    exam_type ENUM('written', 'practical', 'oral') DEFAULT 'written',
    PRIMARY KEY (exam_id),
    FOREIGN KEY (sub_id) REFERENCES subject(sub_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- Table Tasks
CREATE TABLE tasks (
    task_id VARCHAR(5) NOT NULL ,
    task_name VARCHAR(255) NOT NULL,
    task_description VARCHAR(500),
    due_date DATE,
    status ENUM('pending', 'completed', 'overdue') DEFAULT 'pending',
    PRIMARY KEY (task_id)
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
    stud_id VARCHAR(4),
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
    sub_id VARCHAR(5),
    grade_id VARCHAR(5),
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
    stud_id VARCHAR(4),
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
    stud_id VARCHAR(4),
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
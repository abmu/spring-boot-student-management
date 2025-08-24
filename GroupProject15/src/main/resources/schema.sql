DROP TABLE IF EXISTS grade;
DROP TABLE IF EXISTS registration;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS module;

CREATE TABLE student
(
    id        INT PRIMARY KEY,
    firstName VARCHAR(30),
    lastName  VARCHAR(30),
    username  VARCHAR(30),
    email     VARCHAR(50)
);

CREATE TABLE module
(
    code VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100),
    mnc  BOOLEAN
);

CREATE TABLE grade
(
    id          SERIAL PRIMARY KEY,
    score       INT,
    student_id  INT,
    module_code VARCHAR(10),
    FOREIGN KEY (student_id)
        REFERENCES student (id),
    FOREIGN KEY (module_code)
        REFERENCES module (code)
);

CREATE TABLE registration
(
    id          SERIAL PRIMARY KEY,
    student_id  INT,
    module_code VARCHAR(10),
    FOREIGN KEY (student_id)
        REFERENCES student (id),
    FOREIGN KEY (module_code)
        REFERENCES module (code)
);

/*
INSERT INTO student (id, firstName, lastName, username, email)
VALUES (1, 'John', 'Doe', 'johndoe', 'john@example.com');

INSERT INTO module (code, name, mnc)
VALUES ('CS101', 'Computer Science 101', TRUE);

INSERT INTO module (code, name, mnc)
VALUES ('CS102', 'Computer Science 102', TRUE);

INSERT INTO grade (score, student_id, module_code)
VALUES (85, 1, 'CS101');

INSERT INTO grade (score, student_id, module_code)
VALUES (5, 1, 'CS102');

INSERT INTO registration (student_id, module_code)
VALUES (1, 'CS102');

INSERT INTO registration (student_id, module_code)
VALUES (1, 'CS101');

SELECT *
FROM student;
SELECT *
FROM module;
SELECT *
FROM grade;
SELECT *
FROM registration;*/

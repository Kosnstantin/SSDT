-- 1. Створюємо ГРУПИ
INSERT INTO groups (id, created_at, updated_at, name, year) VALUES (1, NOW(), NOW(), 'MKI-251', 2025);

-- 2. Створюємо ЛЮДЕЙ (people) - пароль для всіх '123'
INSERT INTO people (id, created_at, updated_at, first_name, last_name, email, username, password, role) 
VALUES (1, NOW(), NOW(), 'Super', 'Admin', 'admin@sms.com', 'admin', '123', 'ADMIN');
INSERT INTO admins (id) VALUES (1);

-- МЕТОДИСТ (DEAN)
INSERT INTO people (id, created_at, updated_at, first_name, last_name, email, username, password, role) 
VALUES (2, NOW(), NOW(), 'Olena', 'Pchilka', 'dean@sms.com', 'dean', '123', 'DEAN');
INSERT INTO deans (id, faculty) VALUES (2, 'Computer Science');

-- ВИКЛАДАЧ (TEACHER)
INSERT INTO people (id, created_at, updated_at, first_name, last_name, email, username, password, role) 
VALUES (3, NOW(), NOW(), 'Boris', 'Johnson', 'boris@sms.com', 'teacher', '123', 'TEACHER');
INSERT INTO teachers (id, degree) VALUES (3, 'Professor');

-- СТУДЕНТ (STUDENT)
INSERT INTO people (id, created_at, updated_at, first_name, last_name, email, username, password, role) 
VALUES (4, NOW(), NOW(), 'Bohdan', 'Student', 'bohdan@sms.com', 'student', '123', 'STUDENT');
INSERT INTO students (id, dob, group_id) VALUES (4, '2003-01-01', 1);
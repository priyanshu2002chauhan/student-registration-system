-- Sample Data for Student Course Registration System

USE student_registration_db;

-- Insert sample students
INSERT INTO students (first_name, last_name, email, phone, date_of_birth) VALUES
('John', 'Doe', 'john.doe@email.com', '+1-555-0101', '2000-01-15'),
('Jane', 'Smith', 'jane.smith@email.com', '+1-555-0102', '1999-08-22'),
('Mike', 'Johnson', 'mike.johnson@email.com', '+1-555-0103', '2001-03-10'),
('Sarah', 'Wilson', 'sarah.wilson@email.com', '+1-555-0104', '2000-12-05'),
('David', 'Brown', 'david.brown@email.com', '+1-555-0105', '1999-11-18'),
('Emily', 'Davis', 'emily.davis@email.com', '+1-555-0106', '2001-06-30'),
('Robert', 'Miller', 'robert.miller@email.com', '+1-555-0107', '2000-09-14'),
('Lisa', 'Garcia', 'lisa.garcia@email.com', '+1-555-0108', '1999-04-28');

-- Insert sample courses
INSERT INTO courses (course_code, course_name, description, credits, instructor) VALUES
('CS101', 'Introduction to Computer Science', 'Basic concepts of programming and computer science', 3, 'Dr. Alan Turing'),
('CS201', 'Data Structures and Algorithms', 'Advanced programming concepts and algorithmic thinking', 4, 'Dr. Donald Knuth'),
('CS301', 'Database Systems', 'Design and implementation of database systems', 3, 'Dr. Edgar Codd'),
('CS401', 'Software Engineering', 'Principles and practices of software development', 4, 'Dr. Fred Brooks'),
('MATH101', 'Calculus I', 'Differential and integral calculus', 4, 'Dr. Isaac Newton'),
('MATH201', 'Linear Algebra', 'Vector spaces, matrices, and linear transformations', 3, 'Dr. Emmy Noether'),
('PHYS101', 'General Physics I', 'Mechanics, waves, and thermodynamics', 4, 'Dr. Albert Einstein'),
('ENG101', 'English Composition', 'Writing and communication skills', 3, 'Dr. William Shakespeare'),
('HIST101', 'World History', 'Survey of world civilizations', 3, 'Dr. Herodotus'),
('BIO101', 'General Biology', 'Introduction to biological sciences', 4, 'Dr. Charles Darwin');

-- Insert sample registrations
INSERT INTO registrations (student_id, course_id, grade, status) VALUES
-- John Doe's registrations
(1, 1, 'A', 'COMPLETED'),
(1, 2, 'B+', 'ACTIVE'),
(1, 5, 'A-', 'COMPLETED'),
(1, 7, NULL, 'ACTIVE'),

-- Jane Smith's registrations
(2, 1, 'A-', 'COMPLETED'),
(2, 3, 'B', 'ACTIVE'),
(2, 6, 'A', 'COMPLETED'),
(2, 8, NULL, 'ACTIVE'),

-- Mike Johnson's registrations
(3, 2, NULL, 'ACTIVE'),
(3, 4, NULL, 'ACTIVE'),
(3, 5, 'B-', 'COMPLETED'),

-- Sarah Wilson's registrations
(4, 1, 'B+', 'COMPLETED'),
(4, 3, NULL, 'ACTIVE'),
(4, 9, 'A', 'COMPLETED'),
(4, 10, NULL, 'ACTIVE'),

-- David Brown's registrations
(5, 2, 'C+', 'COMPLETED'),
(5, 4, NULL, 'ACTIVE'),
(5, 7, 'B', 'COMPLETED'),

-- Emily Davis's registrations
(6, 1, NULL, 'ACTIVE'),
(6, 6, NULL, 'ACTIVE'),
(6, 8, 'A-', 'COMPLETED'),
(6, 10, NULL, 'ACTIVE'),

-- Robert Miller's registrations
(7, 3, NULL, 'ACTIVE'),
(7, 5, 'B+', 'COMPLETED'),
(7, 9, NULL, 'ACTIVE'),

-- Lisa Garcia's registrations
(8, 1, 'A', 'COMPLETED'),
(8, 4, NULL, 'ACTIVE'),
(8, 6, 'A-', 'COMPLETED'),
(8, 10, NULL, 'ACTIVE');

-- Verify data insertion
SELECT 'Students' as Table_Name, COUNT(*) as Record_Count FROM students
UNION ALL
SELECT 'Courses' as Table_Name, COUNT(*) as Record_Count FROM courses
UNION ALL
SELECT 'Registrations' as Table_Name, COUNT(*) as Record_Count FROM registrations;

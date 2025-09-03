-- Sample SQL Queries with Joins

-- 1. Get all students with their registered courses
SELECT 
    s.student_id,
    CONCAT(s.first_name, ' ', s.last_name) AS student_name,
    s.email,
    c.course_code,
    c.course_name,
    c.credits,
    r.registration_date,
    r.grade,
    r.status
FROM students s
JOIN registrations r ON s.student_id = r.student_id
JOIN courses c ON r.course_id = c.course_id
ORDER BY s.student_id, r.registration_date;

-- 2. Get all courses with enrolled students
SELECT 
    c.course_id,
    c.course_code,
    c.course_name,
    c.instructor,
    CONCAT(s.first_name, ' ', s.last_name) AS student_name,
    s.email,
    r.registration_date,
    r.status
FROM courses c
JOIN registrations r ON c.course_id = r.course_id
JOIN students s ON r.student_id = s.student_id
ORDER BY c.course_code, s.last_name;

-- 3. Get courses for a specific student
SELECT 
    c.course_code,
    c.course_name,
    c.credits,
    c.instructor,
    r.registration_date,
    r.grade,
    r.status
FROM courses c
JOIN registrations r ON c.course_id = r.course_id
WHERE r.student_id = ? -- Parameter placeholder
ORDER BY r.registration_date DESC;

-- 4. Get students enrolled in a specific course
SELECT 
    s.student_id,
    CONCAT(s.first_name, ' ', s.last_name) AS student_name,
    s.email,
    s.phone,
    r.registration_date,
    r.grade,
    r.status
FROM students s
JOIN registrations r ON s.student_id = r.student_id
WHERE r.course_id = ? -- Parameter placeholder
ORDER BY s.last_name, s.first_name;

-- 5. Count of students per course
SELECT 
    c.course_code,
    c.course_name,
    COUNT(r.student_id) AS enrolled_students
FROM courses c
LEFT JOIN registrations r ON c.course_id = r.course_id AND r.status = 'ACTIVE'
GROUP BY c.course_id, c.course_code, c.course_name
ORDER BY enrolled_students DESC;

-- 6. Count of courses per student
SELECT 
    s.student_id,
    CONCAT(s.first_name, ' ', s.last_name) AS student_name,
    COUNT(r.course_id) AS registered_courses
FROM students s
LEFT JOIN registrations r ON s.student_id = r.student_id AND r.status = 'ACTIVE'
GROUP BY s.student_id, s.first_name, s.last_name
ORDER BY registered_courses DESC;

-- 7. Students not registered for any course
SELECT 
    s.student_id,
    CONCAT(s.first_name, ' ', s.last_name) AS student_name,
    s.email
FROM students s
LEFT JOIN registrations r ON s.student_id = r.student_id
WHERE r.student_id IS NULL;

-- 8. Courses with no enrolled students
SELECT 
    c.course_id,
    c.course_code,
    c.course_name,
    c.instructor
FROM courses c
LEFT JOIN registrations r ON c.course_id = r.course_id
WHERE r.course_id IS NULL;

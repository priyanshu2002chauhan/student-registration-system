package com.studentregistration.dao;

import com.studentregistration.model.Student;
import java.util.List;

/**
 * Student Data Access Object interface
 * Defines CRUD operations for Student entity
 * Following DAO pattern for separation of concerns
 */
public interface StudentDAO {

    /**
     * Add a new student to the database
     * @param student Student object to be added
     * @return true if student is added successfully, false otherwise
     */
    boolean addStudent(Student student);

    /**
     * Get student by ID
     * @param studentId ID of the student
     * @return Student object if found, null otherwise
     */
    Student getStudentById(int studentId);

    /**
     * Get student by email
     * @param email Email of the student
     * @return Student object if found, null otherwise
     */
    Student getStudentByEmail(String email);

    /**
     * Get all students
     * @return List of all students
     */
    List<Student> getAllStudents();

    /**
     * Update existing student
     * @param student Student object with updated information
     * @return true if student is updated successfully, false otherwise
     */
    boolean updateStudent(Student student);

    /**
     * Delete student by ID
     * @param studentId ID of the student to be deleted
     * @return true if student is deleted successfully, false otherwise
     */
    boolean deleteStudent(int studentId);

    /**
     * Search students by name (first name or last name)
     * @param name Name to search for
     * @return List of students matching the name
     */
    List<Student> searchStudentsByName(String name);

    /**
     * Check if student exists by email
     * @param email Email to check
     * @return true if student exists, false otherwise
     */
    boolean studentExists(String email);
}

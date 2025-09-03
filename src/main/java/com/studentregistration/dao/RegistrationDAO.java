package com.studentregistration.dao;

import com.studentregistration.model.Registration;
import com.studentregistration.model.Student;
import com.studentregistration.model.Course;
import java.util.List;

/**
 * Registration Data Access Object interface
 * Manages many-to-many relationship between students and courses
 */
public interface RegistrationDAO {

    /**
     * Register a student for a course
     * @param studentId ID of the student
     * @param courseId ID of the course
     * @return true if registration is successful, false otherwise
     */
    boolean registerStudentToCourse(int studentId, int courseId);

    /**
     * Add a registration record
     * @param registration Registration object
     * @return true if registration is added successfully, false otherwise
     */
    boolean addRegistration(Registration registration);

    /**
     * Get registration by ID
     * @param registrationId ID of the registration
     * @return Registration object if found, null otherwise
     */
    Registration getRegistrationById(int registrationId);

    /**
     * Get all registrations
     * @return List of all registrations
     */
    List<Registration> getAllRegistrations();

    /**
     * Update registration (typically for grade or status changes)
     * @param registration Registration object with updated information
     * @return true if registration is updated successfully, false otherwise
     */
    boolean updateRegistration(Registration registration);

    /**
     * Drop a student from a course (delete registration)
     * @param studentId ID of the student
     * @param courseId ID of the course
     * @return true if registration is deleted successfully, false otherwise
     */
    boolean dropStudentFromCourse(int studentId, int courseId);

    /**
     * Get all courses for a specific student with registration details
     * @param studentId ID of the student
     * @return List of registrations with course details
     */
    List<Registration> getCoursesForStudent(int studentId);

    /**
     * Get all students enrolled in a specific course with registration details
     * @param courseId ID of the course
     * @return List of registrations with student details
     */
    List<Registration> getStudentsForCourse(int courseId);

    /**
     * Check if a student is already registered for a course
     * @param studentId ID of the student
     * @param courseId ID of the course
     * @return true if student is registered, false otherwise
     */
    boolean isStudentRegisteredForCourse(int studentId, int courseId);

    /**
     * Update grade for a student in a course
     * @param studentId ID of the student
     * @param courseId ID of the course
     * @param grade Grade to assign
     * @return true if grade is updated successfully, false otherwise
     */
    boolean updateGrade(int studentId, int courseId, String grade);

    /**
     * Update registration status
     * @param studentId ID of the student
     * @param courseId ID of the course
     * @param status New status
     * @return true if status is updated successfully, false otherwise
     */
    boolean updateRegistrationStatus(int studentId, int courseId, Registration.RegistrationStatus status);

    /**
     * Get count of students enrolled in a course
     * @param courseId ID of the course
     * @return Number of enrolled students
     */
    int getEnrollmentCount(int courseId);

    /**
     * Get count of courses a student is registered for
     * @param studentId ID of the student
     * @return Number of registered courses
     */
    int getRegistrationCount(int studentId);
}

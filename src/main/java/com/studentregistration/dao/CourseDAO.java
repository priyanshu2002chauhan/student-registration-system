package com.studentregistration.dao;

import com.studentregistration.model.Course;
import java.util.List;

/**
 * Course Data Access Object interface
 * Defines CRUD operations for Course entity
 */
public interface CourseDAO {

    /**
     * Add a new course to the database
     * @param course Course object to be added
     * @return true if course is added successfully, false otherwise
     */
    boolean addCourse(Course course);

    /**
     * Get course by ID
     * @param courseId ID of the course
     * @return Course object if found, null otherwise
     */
    Course getCourseById(int courseId);

    /**
     * Get course by course code
     * @param courseCode Course code to search for
     * @return Course object if found, null otherwise
     */
    Course getCourseByCode(String courseCode);

    /**
     * Get all courses
     * @return List of all courses
     */
    List<Course> getAllCourses();

    /**
     * Update existing course
     * @param course Course object with updated information
     * @return true if course is updated successfully, false otherwise
     */
    boolean updateCourse(Course course);

    /**
     * Delete course by ID
     * @param courseId ID of the course to be deleted
     * @return true if course is deleted successfully, false otherwise
     */
    boolean deleteCourse(int courseId);

    /**
     * Search courses by name
     * @param courseName Course name to search for
     * @return List of courses matching the name
     */
    List<Course> searchCoursesByName(String courseName);

    /**
     * Get courses by instructor
     * @param instructor Instructor name
     * @return List of courses taught by the instructor
     */
    List<Course> getCoursesByInstructor(String instructor);

    /**
     * Check if course exists by course code
     * @param courseCode Course code to check
     * @return true if course exists, false otherwise
     */
    boolean courseExists(String courseCode);
}

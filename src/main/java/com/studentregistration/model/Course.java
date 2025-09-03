package com.studentregistration.model;

import java.time.LocalDateTime;

/**
 * Course model class representing a course entity
 * Demonstrates encapsulation and proper JavaBean conventions
 */
public class Course {
    private int courseId;
    private String courseCode;
    private String courseName;
    private String description;
    private int credits;
    private String instructor;
    private LocalDateTime createdDate;

    // Default constructor
    public Course() {}

    // Constructor without ID (for new courses)
    public Course(String courseCode, String courseName, String description, int credits, String instructor) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.description = description;
        this.credits = credits;
        this.instructor = instructor;
    }

    // Full constructor
    public Course(int courseId, String courseCode, String courseName, String description, 
                 int credits, String instructor, LocalDateTime createdDate) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.description = description;
        this.credits = credits;
        this.instructor = instructor;
        this.createdDate = createdDate;
    }

    // Getters and Setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return String.format("Course{id=%d, code='%s', name='%s', credits=%d, instructor='%s'}", 
                           courseId, courseCode, courseName, credits, instructor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return courseId == course.courseId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(courseId);
    }
}

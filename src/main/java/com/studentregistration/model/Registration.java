package com.studentregistration.model;

import java.time.LocalDateTime;

/**
 * Registration model class representing the many-to-many relationship
 * between students and courses
 */
public class Registration {
    private int registrationId;
    private int studentId;
    private int courseId;
    private LocalDateTime registrationDate;
    private String grade;
    private RegistrationStatus status;

    // Additional fields for joined data
    private Student student;
    private Course course;

    // Enum for registration status
    public enum RegistrationStatus {
        ACTIVE, DROPPED, COMPLETED
    }

    // Default constructor
    public Registration() {}

    // Constructor for new registration
    public Registration(int studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.status = RegistrationStatus.ACTIVE;
    }

    // Full constructor
    public Registration(int registrationId, int studentId, int courseId, 
                       LocalDateTime registrationDate, String grade, RegistrationStatus status) {
        this.registrationId = registrationId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.registrationDate = registrationDate;
        this.grade = grade;
        this.status = status;
    }

    // Constructor with student and course objects
    public Registration(int registrationId, int studentId, int courseId, 
                       LocalDateTime registrationDate, String grade, RegistrationStatus status,
                       Student student, Course course) {
        this(registrationId, studentId, courseId, registrationDate, grade, status);
        this.student = student;
        this.course = course;
    }

    // Getters and Setters
    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public RegistrationStatus getStatus() {
        return status;
    }

    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return String.format("Registration{id=%d, studentId=%d, courseId=%d, status=%s, grade='%s'}", 
                           registrationId, studentId, courseId, status, grade);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Registration that = (Registration) obj;
        return registrationId == that.registrationId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(registrationId);
    }
}

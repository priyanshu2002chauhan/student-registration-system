package com.studentregistration.dao;

import com.studentregistration.model.Course;
import com.studentregistration.util.DatabaseConnection;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Course Data Access Object implementation
 * Implements CRUD operations for Course entity using JDBC
 */
public class CourseDAOImpl implements CourseDAO {

    private final DatabaseConnection dbConnection;

    // SQL queries as constants
    private static final String INSERT_COURSE = 
        "INSERT INTO courses (course_code, course_name, description, credits, instructor) VALUES (?, ?, ?, ?, ?)";

    private static final String SELECT_COURSE_BY_ID = 
        "SELECT * FROM courses WHERE course_id = ?";

    private static final String SELECT_COURSE_BY_CODE = 
        "SELECT * FROM courses WHERE course_code = ?";

    private static final String SELECT_ALL_COURSES = 
        "SELECT * FROM courses ORDER BY course_code";

    private static final String UPDATE_COURSE = 
        "UPDATE courses SET course_code = ?, course_name = ?, description = ?, credits = ?, instructor = ? WHERE course_id = ?";

    private static final String DELETE_COURSE = 
        "DELETE FROM courses WHERE course_id = ?";

    private static final String SEARCH_COURSES_BY_NAME = 
        "SELECT * FROM courses WHERE course_name LIKE ? ORDER BY course_code";

    private static final String SELECT_COURSES_BY_INSTRUCTOR = 
        "SELECT * FROM courses WHERE instructor LIKE ? ORDER BY course_code";

    private static final String CHECK_COURSE_EXISTS = 
        "SELECT COUNT(*) FROM courses WHERE course_code = ?";

    public CourseDAOImpl() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    @Override
    public boolean addCourse(Course course) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_COURSE, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, course.getCourseCode());
            stmt.setString(2, course.getCourseName());
            stmt.setString(3, course.getDescription());
            stmt.setInt(4, course.getCredits());
            stmt.setString(5, course.getInstructor());

            int rowsAffected = stmt.executeUpdate();

            // Get generated ID and set it to course object
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        course.setCourseId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error adding course: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Course getCourseById(int courseId) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_COURSE_BY_ID)) {

            stmt.setInt(1, courseId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCourse(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getting course by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Course getCourseByCode(String courseCode) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_COURSE_BY_CODE)) {

            stmt.setString(1, courseCode);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCourse(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getting course by code: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_COURSES);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                courses.add(mapResultSetToCourse(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting all courses: " + e.getMessage());
            e.printStackTrace();
        }

        return courses;
    }

    @Override
    public boolean updateCourse(Course course) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_COURSE)) {

            stmt.setString(1, course.getCourseCode());
            stmt.setString(2, course.getCourseName());
            stmt.setString(3, course.getDescription());
            stmt.setInt(4, course.getCredits());
            stmt.setString(5, course.getInstructor());
            stmt.setInt(6, course.getCourseId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating course: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteCourse(int courseId) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_COURSE)) {

            stmt.setInt(1, courseId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting course: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Course> searchCoursesByName(String courseName) {
        List<Course> courses = new ArrayList<>();
        String searchPattern = "%" + courseName + "%";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SEARCH_COURSES_BY_NAME)) {

            stmt.setString(1, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    courses.add(mapResultSetToCourse(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error searching courses by name: " + e.getMessage());
            e.printStackTrace();
        }

        return courses;
    }

    @Override
    public List<Course> getCoursesByInstructor(String instructor) {
        List<Course> courses = new ArrayList<>();
        String searchPattern = "%" + instructor + "%";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_COURSES_BY_INSTRUCTOR)) {

            stmt.setString(1, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    courses.add(mapResultSetToCourse(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getting courses by instructor: " + e.getMessage());
            e.printStackTrace();
        }

        return courses;
    }

    @Override
    public boolean courseExists(String courseCode) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(CHECK_COURSE_EXISTS)) {

            stmt.setString(1, courseCode);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error checking if course exists: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Helper method to map ResultSet to Course object
     * @param rs ResultSet from database query
     * @return Course object
     * @throws SQLException if there's an error accessing ResultSet
     */
    private Course mapResultSetToCourse(ResultSet rs) throws SQLException {
        Course course = new Course();

        course.setCourseId(rs.getInt("course_id"));
        course.setCourseCode(rs.getString("course_code"));
        course.setCourseName(rs.getString("course_name"));
        course.setDescription(rs.getString("description"));
        course.setCredits(rs.getInt("credits"));
        course.setInstructor(rs.getString("instructor"));

        // Handle LocalDateTime conversion
        Timestamp createdDate = rs.getTimestamp("created_date");
        if (createdDate != null) {
            course.setCreatedDate(createdDate.toLocalDateTime());
        }

        return course;
    }
}

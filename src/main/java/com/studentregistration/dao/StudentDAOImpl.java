package com.studentregistration.dao;

import com.studentregistration.model.Student;
import com.studentregistration.util.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Student Data Access Object implementation
 * Implements CRUD operations for Student entity using JDBC
 */
public class StudentDAOImpl implements StudentDAO {

    private final DatabaseConnection dbConnection;

    // SQL queries as constants for better maintainability
    private static final String INSERT_STUDENT = 
        "INSERT INTO students (first_name, last_name, email, phone, date_of_birth) VALUES (?, ?, ?, ?, ?)";

    private static final String SELECT_STUDENT_BY_ID = 
        "SELECT * FROM students WHERE student_id = ?";

    private static final String SELECT_STUDENT_BY_EMAIL = 
        "SELECT * FROM students WHERE email = ?";

    private static final String SELECT_ALL_STUDENTS = 
        "SELECT * FROM students ORDER BY last_name, first_name";

    private static final String UPDATE_STUDENT = 
        "UPDATE students SET first_name = ?, last_name = ?, email = ?, phone = ?, date_of_birth = ? WHERE student_id = ?";

    private static final String DELETE_STUDENT = 
        "DELETE FROM students WHERE student_id = ?";

    private static final String SEARCH_STUDENTS_BY_NAME = 
        "SELECT * FROM students WHERE first_name LIKE ? OR last_name LIKE ? ORDER BY last_name, first_name";

    private static final String CHECK_STUDENT_EXISTS = 
        "SELECT COUNT(*) FROM students WHERE email = ?";

    public StudentDAOImpl() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    @Override
    public boolean addStudent(Student student) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_STUDENT, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setString(3, student.getEmail());
            stmt.setString(4, student.getPhone());

            // Handle LocalDate conversion
            if (student.getDateOfBirth() != null) {
                stmt.setDate(5, Date.valueOf(student.getDateOfBirth()));
            } else {
                stmt.setNull(5, Types.DATE);
            }

            int rowsAffected = stmt.executeUpdate();

            // Get generated ID and set it to student object
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        student.setStudentId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Student getStudentById(int studentId) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_STUDENT_BY_ID)) {

            stmt.setInt(1, studentId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToStudent(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getting student by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Student getStudentByEmail(String email) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_STUDENT_BY_EMAIL)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToStudent(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getting student by email: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_STUDENTS);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                students.add(mapResultSetToStudent(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting all students: " + e.getMessage());
            e.printStackTrace();
        }

        return students;
    }

    @Override
    public boolean updateStudent(Student student) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_STUDENT)) {

            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setString(3, student.getEmail());
            stmt.setString(4, student.getPhone());

            if (student.getDateOfBirth() != null) {
                stmt.setDate(5, Date.valueOf(student.getDateOfBirth()));
            } else {
                stmt.setNull(5, Types.DATE);
            }

            stmt.setInt(6, student.getStudentId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteStudent(int studentId) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_STUDENT)) {

            stmt.setInt(1, studentId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Student> searchStudentsByName(String name) {
        List<Student> students = new ArrayList<>();
        String searchPattern = "%" + name + "%";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SEARCH_STUDENTS_BY_NAME)) {

            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    students.add(mapResultSetToStudent(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error searching students by name: " + e.getMessage());
            e.printStackTrace();
        }

        return students;
    }

    @Override
    public boolean studentExists(String email) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(CHECK_STUDENT_EXISTS)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error checking if student exists: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Helper method to map ResultSet to Student object
     * @param rs ResultSet from database query
     * @return Student object
     * @throws SQLException if there's an error accessing ResultSet
     */
    private Student mapResultSetToStudent(ResultSet rs) throws SQLException {
        Student student = new Student();

        student.setStudentId(rs.getInt("student_id"));
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        student.setEmail(rs.getString("email"));
        student.setPhone(rs.getString("phone"));

        // Handle LocalDate conversion
        Date dateOfBirth = rs.getDate("date_of_birth");
        if (dateOfBirth != null) {
            student.setDateOfBirth(dateOfBirth.toLocalDate());
        }

        // Handle LocalDateTime conversion
        Timestamp enrollmentDate = rs.getTimestamp("enrollment_date");
        if (enrollmentDate != null) {
            student.setEnrollmentDate(enrollmentDate.toLocalDateTime());
        }

        return student;
    }
}

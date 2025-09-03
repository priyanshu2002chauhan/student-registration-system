package com.studentregistration.dao;

import com.studentregistration.model.Registration;
import com.studentregistration.model.Student;
import com.studentregistration.model.Course;
import com.studentregistration.util.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Registration Data Access Object implementation
 * Manages many-to-many relationship between students and courses using JDBC
 */
public class RegistrationDAOImpl implements RegistrationDAO {

    private final DatabaseConnection dbConnection;

    // SQL queries as constants
    private static final String INSERT_REGISTRATION = 
        "INSERT INTO registrations (student_id, course_id, status) VALUES (?, ?, ?)";

    private static final String SELECT_REGISTRATION_BY_ID = 
        "SELECT * FROM registrations WHERE registration_id = ?";

    private static final String SELECT_ALL_REGISTRATIONS = 
        "SELECT r.*, s.first_name, s.last_name, s.email, c.course_code, c.course_name " +
        "FROM registrations r " +
        "JOIN students s ON r.student_id = s.student_id " +
        "JOIN courses c ON r.course_id = c.course_id " +
        "ORDER BY r.registration_date DESC";

    private static final String UPDATE_REGISTRATION = 
        "UPDATE registrations SET grade = ?, status = ? WHERE registration_id = ?";

    private static final String DELETE_REGISTRATION = 
        "DELETE FROM registrations WHERE student_id = ? AND course_id = ?";

    private static final String SELECT_COURSES_FOR_STUDENT = 
        "SELECT r.*, c.course_id, c.course_code, c.course_name, c.description, " +
        "c.credits, c.instructor, c.created_date " +
        "FROM registrations r " +
        "JOIN courses c ON r.course_id = c.course_id " +
        "WHERE r.student_id = ? " +
        "ORDER BY r.registration_date DESC";

    private static final String SELECT_STUDENTS_FOR_COURSE = 
        "SELECT r.*, s.student_id, s.first_name, s.last_name, s.email, " +
        "s.phone, s.date_of_birth, s.enrollment_date " +
        "FROM registrations r " +
        "JOIN students s ON r.student_id = s.student_id " +
        "WHERE r.course_id = ? " +
        "ORDER BY s.last_name, s.first_name";

    private static final String CHECK_REGISTRATION_EXISTS = 
        "SELECT COUNT(*) FROM registrations WHERE student_id = ? AND course_id = ?";

    private static final String UPDATE_GRADE = 
        "UPDATE registrations SET grade = ? WHERE student_id = ? AND course_id = ?";

    private static final String UPDATE_STATUS = 
        "UPDATE registrations SET status = ? WHERE student_id = ? AND course_id = ?";

    private static final String COUNT_ENROLLMENTS = 
        "SELECT COUNT(*) FROM registrations WHERE course_id = ? AND status = 'ACTIVE'";

    private static final String COUNT_REGISTRATIONS = 
        "SELECT COUNT(*) FROM registrations WHERE student_id = ? AND status = 'ACTIVE'";

    public RegistrationDAOImpl() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    @Override
    public boolean registerStudentToCourse(int studentId, int courseId) {
        // Check if already registered
        if (isStudentRegisteredForCourse(studentId, courseId)) {
            System.out.println("Student is already registered for this course.");
            return false;
        }

        Registration registration = new Registration(studentId, courseId);
        return addRegistration(registration);
    }

    @Override
    public boolean addRegistration(Registration registration) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_REGISTRATION, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, registration.getStudentId());
            stmt.setInt(2, registration.getCourseId());
            stmt.setString(3, registration.getStatus().name());

            int rowsAffected = stmt.executeUpdate();

            // Get generated ID and set it to registration object
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        registration.setRegistrationId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error adding registration: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Registration getRegistrationById(int registrationId) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_REGISTRATION_BY_ID)) {

            stmt.setInt(1, registrationId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRegistration(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getting registration by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public List<Registration> getAllRegistrations() {
        List<Registration> registrations = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_REGISTRATIONS);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Registration registration = mapResultSetToRegistration(rs);

                // Create and set student info
                Student student = new Student();
                student.setStudentId(registration.getStudentId());
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setEmail(rs.getString("email"));
                registration.setStudent(student);

                // Create and set course info
                Course course = new Course();
                course.setCourseId(registration.getCourseId());
                course.setCourseCode(rs.getString("course_code"));
                course.setCourseName(rs.getString("course_name"));
                registration.setCourse(course);

                registrations.add(registration);
            }

        } catch (SQLException e) {
            System.err.println("Error getting all registrations: " + e.getMessage());
            e.printStackTrace();
        }

        return registrations;
    }

    @Override
    public boolean updateRegistration(Registration registration) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_REGISTRATION)) {

            stmt.setString(1, registration.getGrade());
            stmt.setString(2, registration.getStatus().name());
            stmt.setInt(3, registration.getRegistrationId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating registration: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean dropStudentFromCourse(int studentId, int courseId) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_REGISTRATION)) {

            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error dropping student from course: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Registration> getCoursesForStudent(int studentId) {
        List<Registration> registrations = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_COURSES_FOR_STUDENT)) {

            stmt.setInt(1, studentId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Registration registration = mapResultSetToRegistration(rs);

                    // Create and set course info
                    Course course = new Course();
                    course.setCourseId(rs.getInt("course_id"));
                    course.setCourseCode(rs.getString("course_code"));
                    course.setCourseName(rs.getString("course_name"));
                    course.setDescription(rs.getString("description"));
                    course.setCredits(rs.getInt("credits"));
                    course.setInstructor(rs.getString("instructor"));

                    Timestamp createdDate = rs.getTimestamp("created_date");
                    if (createdDate != null) {
                        course.setCreatedDate(createdDate.toLocalDateTime());
                    }

                    registration.setCourse(course);
                    registrations.add(registration);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getting courses for student: " + e.getMessage());
            e.printStackTrace();
        }

        return registrations;
    }

    @Override
    public List<Registration> getStudentsForCourse(int courseId) {
        List<Registration> registrations = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_STUDENTS_FOR_COURSE)) {

            stmt.setInt(1, courseId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Registration registration = mapResultSetToRegistration(rs);

                    // Create and set student info
                    Student student = new Student();
                    student.setStudentId(rs.getInt("student_id"));
                    student.setFirstName(rs.getString("first_name"));
                    student.setLastName(rs.getString("last_name"));
                    student.setEmail(rs.getString("email"));
                    student.setPhone(rs.getString("phone"));

                    Date dateOfBirth = rs.getDate("date_of_birth");
                    if (dateOfBirth != null) {
                        student.setDateOfBirth(dateOfBirth.toLocalDate());
                    }

                    Timestamp enrollmentDate = rs.getTimestamp("enrollment_date");
                    if (enrollmentDate != null) {
                        student.setEnrollmentDate(enrollmentDate.toLocalDateTime());
                    }

                    registration.setStudent(student);
                    registrations.add(registration);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getting students for course: " + e.getMessage());
            e.printStackTrace();
        }

        return registrations;
    }

    @Override
    public boolean isStudentRegisteredForCourse(int studentId, int courseId) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(CHECK_REGISTRATION_EXISTS)) {

            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error checking registration existence: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateGrade(int studentId, int courseId, String grade) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_GRADE)) {

            stmt.setString(1, grade);
            stmt.setInt(2, studentId);
            stmt.setInt(3, courseId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating grade: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateRegistrationStatus(int studentId, int courseId, Registration.RegistrationStatus status) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_STATUS)) {

            stmt.setString(1, status.name());
            stmt.setInt(2, studentId);
            stmt.setInt(3, courseId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating registration status: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getEnrollmentCount(int courseId) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(COUNT_ENROLLMENTS)) {

            stmt.setInt(1, courseId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getting enrollment count: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getRegistrationCount(int studentId) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(COUNT_REGISTRATIONS)) {

            stmt.setInt(1, studentId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getting registration count: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Helper method to map ResultSet to Registration object
     * @param rs ResultSet from database query
     * @return Registration object
     * @throws SQLException if there's an error accessing ResultSet
     */
    private Registration mapResultSetToRegistration(ResultSet rs) throws SQLException {
        Registration registration = new Registration();

        registration.setRegistrationId(rs.getInt("registration_id"));
        registration.setStudentId(rs.getInt("student_id"));
        registration.setCourseId(rs.getInt("course_id"));
        registration.setGrade(rs.getString("grade"));

        // Handle enum conversion
        String statusStr = rs.getString("status");
        if (statusStr != null) {
            registration.setStatus(Registration.RegistrationStatus.valueOf(statusStr));
        }

        // Handle LocalDateTime conversion
        Timestamp registrationDate = rs.getTimestamp("registration_date");
        if (registrationDate != null) {
            registration.setRegistrationDate(registrationDate.toLocalDateTime());
        }

        return registration;
    }
}

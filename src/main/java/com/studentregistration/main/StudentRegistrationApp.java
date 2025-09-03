package com.studentregistration.main;

import com.studentregistration.dao.*;
import com.studentregistration.model.*;
import com.studentregistration.util.DatabaseConnection;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Main application class with console-based menu system
 * Demonstrates the complete Student Course Registration System functionality
 */
public class StudentRegistrationApp {

    private final StudentDAO studentDAO;
    private final CourseDAO courseDAO;
    private final RegistrationDAO registrationDAO;
    private final Scanner scanner;
    private final DateTimeFormatter dateFormatter;

    public StudentRegistrationApp() {
        this.studentDAO = new StudentDAOImpl();
        this.courseDAO = new CourseDAOImpl();
        this.registrationDAO = new RegistrationDAOImpl();
        this.scanner = new Scanner(System.in);
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public static void main(String[] args) {
        StudentRegistrationApp app = new StudentRegistrationApp();
        app.run();
    }

    public void run() {
        System.out.println("=== Welcome to Student Course Registration System ===");
        System.out.println("Testing database connection...");

        DatabaseConnection dbConn = DatabaseConnection.getInstance();
        if (dbConn.testConnection()) {
            System.out.println("✓ Database connection successful!");
            System.out.println("Connection Info: " + dbConn.getConnectionInfo());
        } else {
            System.out.println("✗ Database connection failed! Please check your database configuration.");
            System.out.println("Make sure MySQL is running and the database 'student_registration_db' exists.");
            return;
        }

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    studentManagementMenu();
                    break;
                case 2:
                    courseManagementMenu();
                    break;
                case 3:
                    registrationManagementMenu();
                    break;
                case 4:
                    reportsMenu();
                    break;
                case 0:
                    running = false;
                    System.out.println("Thank you for using Student Registration System!");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }

        scanner.close();
        DatabaseConnection.getInstance().closeConnection();
    }

    private void displayMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("            MAIN MENU");
        System.out.println("=".repeat(50));
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Registration Management");
        System.out.println("4. Reports");
        System.out.println("0. Exit");
        System.out.println("=".repeat(50));
    }

    private void studentManagementMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("        STUDENT MANAGEMENT");
            System.out.println("=".repeat(40));
            System.out.println("1. Add New Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Search Student by Email");
            System.out.println("5. Search Students by Name");
            System.out.println("6. Update Student");
            System.out.println("7. Delete Student");
            System.out.println("0. Back to Main Menu");
            System.out.println("=".repeat(40));

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    searchStudentById();
                    break;
                case 4:
                    searchStudentByEmail();
                    break;
                case 5:
                    searchStudentsByName();
                    break;
                case 6:
                    updateStudent();
                    break;
                case 7:
                    deleteStudent();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private void courseManagementMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("        COURSE MANAGEMENT");
            System.out.println("=".repeat(40));
            System.out.println("1. Add New Course");
            System.out.println("2. View All Courses");
            System.out.println("3. Search Course by ID");
            System.out.println("4. Search Course by Code");
            System.out.println("5. Search Courses by Name");
            System.out.println("6. Search Courses by Instructor");
            System.out.println("7. Update Course");
            System.out.println("8. Delete Course");
            System.out.println("0. Back to Main Menu");
            System.out.println("=".repeat(40));

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    viewAllCourses();
                    break;
                case 3:
                    searchCourseById();
                    break;
                case 4:
                    searchCourseByCode();
                    break;
                case 5:
                    searchCoursesByName();
                    break;
                case 6:
                    searchCoursesByInstructor();
                    break;
                case 7:
                    updateCourse();
                    break;
                case 8:
                    deleteCourse();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private void registrationManagementMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + "=".repeat(45));
            System.out.println("       REGISTRATION MANAGEMENT");
            System.out.println("=".repeat(45));
            System.out.println("1. Register Student for Course");
            System.out.println("2. Drop Student from Course");
            System.out.println("3. Update Grade");
            System.out.println("4. Update Registration Status");
            System.out.println("5. View All Registrations");
            System.out.println("0. Back to Main Menu");
            System.out.println("=".repeat(45));

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    registerStudentForCourse();
                    break;
                case 2:
                    dropStudentFromCourse();
                    break;
                case 3:
                    updateGrade();
                    break;
                case 4:
                    updateRegistrationStatus();
                    break;
                case 5:
                    viewAllRegistrations();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private void reportsMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("           REPORTS");
            System.out.println("=".repeat(40));
            System.out.println("1. View Courses for Student");
            System.out.println("2. View Students in Course");
            System.out.println("3. Course Enrollment Statistics");
            System.out.println("4. Student Registration Statistics");
            System.out.println("0. Back to Main Menu");
            System.out.println("=".repeat(40));

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    viewCoursesForStudent();
                    break;
                case 2:
                    viewStudentsInCourse();
                    break;
                case 3:
                    courseEnrollmentStatistics();
                    break;
                case 4:
                    studentRegistrationStatistics();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // ================== STUDENT MANAGEMENT METHODS ==================

    private void addStudent() {
        System.out.println("\n--- Add New Student ---");

        System.out.print("First Name: ");
        String firstName = scanner.nextLine().trim();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        // Check if student already exists
        if (studentDAO.studentExists(email)) {
            System.out.println("✗ Student with this email already exists!");
            return;
        }

        System.out.print("Phone: ");
        String phone = scanner.nextLine().trim();

        System.out.print("Date of Birth (yyyy-MM-dd): ");
        String dobStr = scanner.nextLine().trim();

        LocalDate dateOfBirth = null;
        if (!dobStr.isEmpty()) {
            try {
                dateOfBirth = LocalDate.parse(dobStr, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("⚠ Invalid date format. Proceeding without date of birth.");
            }
        }

        Student student = new Student(firstName, lastName, email, phone, dateOfBirth);

        if (studentDAO.addStudent(student)) {
            System.out.println("✓ Student added successfully! ID: " + student.getStudentId());
        } else {
            System.out.println("✗ Failed to add student!");
        }
    }

    private void viewAllStudents() {
        System.out.println("\n--- All Students ---");
        List<Student> students = studentDAO.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.printf("%-5s %-15s %-15s %-25s %-15s %-12s%n", 
                         "ID", "First Name", "Last Name", "Email", "Phone", "Birth Date");
        System.out.println("-".repeat(90));

        for (Student student : students) {
            System.out.printf("%-5d %-15s %-15s %-25s %-15s %-12s%n",
                            student.getStudentId(),
                            student.getFirstName(),
                            student.getLastName(),
                            student.getEmail(),
                            student.getPhone() != null ? student.getPhone() : "N/A",
                            student.getDateOfBirth() != null ? student.getDateOfBirth().toString() : "N/A");
        }
    }

    private void searchStudentById() {
        System.out.println("\n--- Search Student by ID ---");
        int studentId = getIntInput("Enter Student ID: ");

        Student student = studentDAO.getStudentById(studentId);
        if (student != null) {
            displayStudentDetails(student);
        } else {
            System.out.println("✗ Student not found!");
        }
    }

    private void searchStudentByEmail() {
        System.out.println("\n--- Search Student by Email ---");
        System.out.print("Enter Email: ");
        String email = scanner.nextLine().trim();

        Student student = studentDAO.getStudentByEmail(email);
        if (student != null) {
            displayStudentDetails(student);
        } else {
            System.out.println("✗ Student not found!");
        }
    }

    private void searchStudentsByName() {
        System.out.println("\n--- Search Students by Name ---");
        System.out.print("Enter Name (first or last): ");
        String name = scanner.nextLine().trim();

        List<Student> students = studentDAO.searchStudentsByName(name);
        if (students.isEmpty()) {
            System.out.println("✗ No students found!");
        } else {
            System.out.println("Found " + students.size() + " student(s):");
            for (Student student : students) {
                displayStudentDetails(student);
                System.out.println();
            }
        }
    }

    private void updateStudent() {
        System.out.println("\n--- Update Student ---");
        int studentId = getIntInput("Enter Student ID to update: ");

        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            System.out.println("✗ Student not found!");
            return;
        }

        System.out.println("Current details:");
        displayStudentDetails(student);

        System.out.println("\nEnter new details (press Enter to keep current value):");

        System.out.print("First Name [" + student.getFirstName() + "]: ");
        String firstName = scanner.nextLine().trim();
        if (!firstName.isEmpty()) {
            student.setFirstName(firstName);
        }

        System.out.print("Last Name [" + student.getLastName() + "]: ");
        String lastName = scanner.nextLine().trim();
        if (!lastName.isEmpty()) {
            student.setLastName(lastName);
        }

        System.out.print("Email [" + student.getEmail() + "]: ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) {
            student.setEmail(email);
        }

        System.out.print("Phone [" + (student.getPhone() != null ? student.getPhone() : "N/A") + "]: ");
        String phone = scanner.nextLine().trim();
        if (!phone.isEmpty()) {
            student.setPhone(phone);
        }

        if (studentDAO.updateStudent(student)) {
            System.out.println("✓ Student updated successfully!");
        } else {
            System.out.println("✗ Failed to update student!");
        }
    }

    private void deleteStudent() {
        System.out.println("\n--- Delete Student ---");
        int studentId = getIntInput("Enter Student ID to delete: ");

        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            System.out.println("✗ Student not found!");
            return;
        }

        System.out.println("Student to delete:");
        displayStudentDetails(student);

        System.out.print("Are you sure? (y/N): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("y") || confirm.equals("yes")) {
            if (studentDAO.deleteStudent(studentId)) {
                System.out.println("✓ Student deleted successfully!");
            } else {
                System.out.println("✗ Failed to delete student!");
            }
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    // ================== COURSE MANAGEMENT METHODS ==================

    private void addCourse() {
        System.out.println("\n--- Add New Course ---");

        System.out.print("Course Code: ");
        String courseCode = scanner.nextLine().trim().toUpperCase();

        // Check if course already exists
        if (courseDAO.courseExists(courseCode)) {
            System.out.println("✗ Course with this code already exists!");
            return;
        }

        System.out.print("Course Name: ");
        String courseName = scanner.nextLine().trim();

        System.out.print("Description: ");
        String description = scanner.nextLine().trim();

        int credits = getIntInput("Credits: ");

        System.out.print("Instructor: ");
        String instructor = scanner.nextLine().trim();

        Course course = new Course(courseCode, courseName, description, credits, instructor);

        if (courseDAO.addCourse(course)) {
            System.out.println("✓ Course added successfully! ID: " + course.getCourseId());
        } else {
            System.out.println("✗ Failed to add course!");
        }
    }

    private void viewAllCourses() {
        System.out.println("\n--- All Courses ---");
        List<Course> courses = courseDAO.getAllCourses();

        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }

        System.out.printf("%-5s %-10s %-25s %-8s %-20s%n", 
                         "ID", "Code", "Name", "Credits", "Instructor");
        System.out.println("-".repeat(75));

        for (Course course : courses) {
            System.out.printf("%-5d %-10s %-25s %-8d %-20s%n",
                            course.getCourseId(),
                            course.getCourseCode(),
                            course.getCourseName().length() > 25 ? 
                                course.getCourseName().substring(0, 22) + "..." : course.getCourseName(),
                            course.getCredits(),
                            course.getInstructor() != null ? course.getInstructor() : "TBA");
        }
    }

    private void searchCourseById() {
        System.out.println("\n--- Search Course by ID ---");
        int courseId = getIntInput("Enter Course ID: ");

        Course course = courseDAO.getCourseById(courseId);
        if (course != null) {
            displayCourseDetails(course);
        } else {
            System.out.println("✗ Course not found!");
        }
    }

    private void searchCourseByCode() {
        System.out.println("\n--- Search Course by Code ---");
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine().trim().toUpperCase();

        Course course = courseDAO.getCourseByCode(courseCode);
        if (course != null) {
            displayCourseDetails(course);
        } else {
            System.out.println("✗ Course not found!");
        }
    }

    private void searchCoursesByName() {
        System.out.println("\n--- Search Courses by Name ---");
        System.out.print("Enter Course Name: ");
        String courseName = scanner.nextLine().trim();

        List<Course> courses = courseDAO.searchCoursesByName(courseName);
        if (courses.isEmpty()) {
            System.out.println("✗ No courses found!");
        } else {
            System.out.println("Found " + courses.size() + " course(s):");
            for (Course course : courses) {
                displayCourseDetails(course);
                System.out.println();
            }
        }
    }

    private void searchCoursesByInstructor() {
        System.out.println("\n--- Search Courses by Instructor ---");
        System.out.print("Enter Instructor Name: ");
        String instructor = scanner.nextLine().trim();

        List<Course> courses = courseDAO.getCoursesByInstructor(instructor);
        if (courses.isEmpty()) {
            System.out.println("✗ No courses found!");
        } else {
            System.out.println("Found " + courses.size() + " course(s):");
            for (Course course : courses) {
                displayCourseDetails(course);
                System.out.println();
            }
        }
    }

    private void updateCourse() {
        System.out.println("\n--- Update Course ---");
        int courseId = getIntInput("Enter Course ID to update: ");

        Course course = courseDAO.getCourseById(courseId);
        if (course == null) {
            System.out.println("✗ Course not found!");
            return;
        }

        System.out.println("Current details:");
        displayCourseDetails(course);

        System.out.println("\nEnter new details (press Enter to keep current value):");

        System.out.print("Course Code [" + course.getCourseCode() + "]: ");
        String courseCode = scanner.nextLine().trim().toUpperCase();
        if (!courseCode.isEmpty()) {
            course.setCourseCode(courseCode);
        }

        System.out.print("Course Name [" + course.getCourseName() + "]: ");
        String courseName = scanner.nextLine().trim();
        if (!courseName.isEmpty()) {
            course.setCourseName(courseName);
        }

        System.out.print("Description [" + (course.getDescription() != null ? course.getDescription() : "N/A") + "]: ");
        String description = scanner.nextLine().trim();
        if (!description.isEmpty()) {
            course.setDescription(description);
        }

        System.out.print("Credits [" + course.getCredits() + "]: ");
        String creditsStr = scanner.nextLine().trim();
        if (!creditsStr.isEmpty()) {
            try {
                int credits = Integer.parseInt(creditsStr);
                course.setCredits(credits);
            } catch (NumberFormatException e) {
                System.out.println("⚠ Invalid credits value. Keeping current value.");
            }
        }

        System.out.print("Instructor [" + (course.getInstructor() != null ? course.getInstructor() : "N/A") + "]: ");
        String instructor = scanner.nextLine().trim();
        if (!instructor.isEmpty()) {
            course.setInstructor(instructor);
        }

        if (courseDAO.updateCourse(course)) {
            System.out.println("✓ Course updated successfully!");
        } else {
            System.out.println("✗ Failed to update course!");
        }
    }

    private void deleteCourse() {
        System.out.println("\n--- Delete Course ---");
        int courseId = getIntInput("Enter Course ID to delete: ");

        Course course = courseDAO.getCourseById(courseId);
        if (course == null) {
            System.out.println("✗ Course not found!");
            return;
        }

        System.out.println("Course to delete:");
        displayCourseDetails(course);

        // Check enrollment count
        int enrollmentCount = registrationDAO.getEnrollmentCount(courseId);
        if (enrollmentCount > 0) {
            System.out.println("⚠ Warning: This course has " + enrollmentCount + " enrolled students!");
        }

        System.out.print("Are you sure? (y/N): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("y") || confirm.equals("yes")) {
            if (courseDAO.deleteCourse(courseId)) {
                System.out.println("✓ Course deleted successfully!");
            } else {
                System.out.println("✗ Failed to delete course!");
            }
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    // ================== REGISTRATION MANAGEMENT METHODS ==================

    private void registerStudentForCourse() {
        System.out.println("\n--- Register Student for Course ---");

        int studentId = getIntInput("Enter Student ID: ");
        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            System.out.println("✗ Student not found!");
            return;
        }

        int courseId = getIntInput("Enter Course ID: ");
        Course course = courseDAO.getCourseById(courseId);
        if (course == null) {
            System.out.println("✗ Course not found!");
            return;
        }

        System.out.println("\nRegistering:");
        System.out.println("Student: " + student.getFullName() + " (" + student.getEmail() + ")");
        System.out.println("Course: " + course.getCourseCode() + " - " + course.getCourseName());

        if (registrationDAO.registerStudentToCourse(studentId, courseId)) {
            System.out.println("✓ Student registered successfully!");
        } else {
            System.out.println("✗ Registration failed!");
        }
    }

    private void dropStudentFromCourse() {
        System.out.println("\n--- Drop Student from Course ---");

        int studentId = getIntInput("Enter Student ID: ");
        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            System.out.println("✗ Student not found!");
            return;
        }

        int courseId = getIntInput("Enter Course ID: ");
        Course course = courseDAO.getCourseById(courseId);
        if (course == null) {
            System.out.println("✗ Course not found!");
            return;
        }

        if (!registrationDAO.isStudentRegisteredForCourse(studentId, courseId)) {
            System.out.println("✗ Student is not registered for this course!");
            return;
        }

        System.out.println("\nDropping:");
        System.out.println("Student: " + student.getFullName() + " (" + student.getEmail() + ")");
        System.out.println("Course: " + course.getCourseCode() + " - " + course.getCourseName());

        System.out.print("Are you sure? (y/N): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("y") || confirm.equals("yes")) {
            if (registrationDAO.dropStudentFromCourse(studentId, courseId)) {
                System.out.println("✓ Student dropped successfully!");
            } else {
                System.out.println("✗ Drop failed!");
            }
        } else {
            System.out.println("Drop cancelled.");
        }
    }

    private void updateGrade() {
        System.out.println("\n--- Update Grade ---");

        int studentId = getIntInput("Enter Student ID: ");
        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            System.out.println("✗ Student not found!");
            return;
        }

        int courseId = getIntInput("Enter Course ID: ");
        Course course = courseDAO.getCourseById(courseId);
        if (course == null) {
            System.out.println("✗ Course not found!");
            return;
        }

        if (!registrationDAO.isStudentRegisteredForCourse(studentId, courseId)) {
            System.out.println("✗ Student is not registered for this course!");
            return;
        }

        System.out.println("\nUpdating grade for:");
        System.out.println("Student: " + student.getFullName());
        System.out.println("Course: " + course.getCourseCode() + " - " + course.getCourseName());

        System.out.print("Enter Grade (A, B, C, D, F): ");
        String grade = scanner.nextLine().trim().toUpperCase();

        if (registrationDAO.updateGrade(studentId, courseId, grade)) {
            System.out.println("✓ Grade updated successfully!");
        } else {
            System.out.println("✗ Grade update failed!");
        }
    }

    private void updateRegistrationStatus() {
        System.out.println("\n--- Update Registration Status ---");

        int studentId = getIntInput("Enter Student ID: ");
        int courseId = getIntInput("Enter Course ID: ");

        if (!registrationDAO.isStudentRegisteredForCourse(studentId, courseId)) {
            System.out.println("✗ Registration not found!");
            return;
        }

        System.out.println("\nSelect new status:");
        System.out.println("1. ACTIVE");
        System.out.println("2. DROPPED");
        System.out.println("3. COMPLETED");

        int statusChoice = getIntInput("Enter choice: ");
        Registration.RegistrationStatus status;

        switch (statusChoice) {
            case 1:
                status = Registration.RegistrationStatus.ACTIVE;
                break;
            case 2:
                status = Registration.RegistrationStatus.DROPPED;
                break;
            case 3:
                status = Registration.RegistrationStatus.COMPLETED;
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }

        if (registrationDAO.updateRegistrationStatus(studentId, courseId, status)) {
            System.out.println("✓ Registration status updated successfully!");
        } else {
            System.out.println("✗ Status update failed!");
        }
    }

    private void viewAllRegistrations() {
        System.out.println("\n--- All Registrations ---");
        List<Registration> registrations = registrationDAO.getAllRegistrations();

        if (registrations.isEmpty()) {
            System.out.println("No registrations found.");
            return;
        }

        System.out.printf("%-5s %-20s %-15s %-10s %-6s %-10s%n", 
                         "ID", "Student", "Course", "Status", "Grade", "Date");
        System.out.println("-".repeat(75));

        for (Registration registration : registrations) {
            String studentName = registration.getStudent() != null ? 
                registration.getStudent().getFullName() : "N/A";
            String courseCode = registration.getCourse() != null ? 
                registration.getCourse().getCourseCode() : "N/A";
            String grade = registration.getGrade() != null ? registration.getGrade() : "N/A";
            String date = registration.getRegistrationDate() != null ? 
                registration.getRegistrationDate().toLocalDate().toString() : "N/A";

            System.out.printf("%-5d %-20s %-15s %-10s %-6s %-10s%n",
                            registration.getRegistrationId(),
                            studentName.length() > 20 ? studentName.substring(0, 17) + "..." : studentName,
                            courseCode,
                            registration.getStatus(),
                            grade,
                            date);
        }
    }

    // ================== REPORTS METHODS ==================

    private void viewCoursesForStudent() {
        System.out.println("\n--- View Courses for Student ---");
        int studentId = getIntInput("Enter Student ID: ");

        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            System.out.println("✗ Student not found!");
            return;
        }

        System.out.println("\nCourses for: " + student.getFullName() + " (" + student.getEmail() + ")");

        List<Registration> registrations = registrationDAO.getCoursesForStudent(studentId);
        if (registrations.isEmpty()) {
            System.out.println("No courses found for this student.");
            return;
        }

        System.out.printf("%-10s %-25s %-8s %-6s %-10s %-12s%n", 
                         "Code", "Course Name", "Credits", "Grade", "Status", "Reg. Date");
        System.out.println("-".repeat(80));

        for (Registration registration : registrations) {
            Course course = registration.getCourse();
            if (course != null) {
                String grade = registration.getGrade() != null ? registration.getGrade() : "N/A";
                String date = registration.getRegistrationDate() != null ? 
                    registration.getRegistrationDate().toLocalDate().toString() : "N/A";

                System.out.printf("%-10s %-25s %-8d %-6s %-10s %-12s%n",
                                course.getCourseCode(),
                                course.getCourseName().length() > 25 ? 
                                    course.getCourseName().substring(0, 22) + "..." : course.getCourseName(),
                                course.getCredits(),
                                grade,
                                registration.getStatus(),
                                date);
            }
        }

        System.out.println("\nTotal registered courses: " + registrations.size());
    }

    private void viewStudentsInCourse() {
        System.out.println("\n--- View Students in Course ---");
        int courseId = getIntInput("Enter Course ID: ");

        Course course = courseDAO.getCourseById(courseId);
        if (course == null) {
            System.out.println("✗ Course not found!");
            return;
        }

        System.out.println("\nStudents in: " + course.getCourseCode() + " - " + course.getCourseName());

        List<Registration> registrations = registrationDAO.getStudentsForCourse(courseId);
        if (registrations.isEmpty()) {
            System.out.println("No students enrolled in this course.");
            return;
        }

        System.out.printf("%-5s %-20s %-25s %-6s %-10s %-12s%n", 
                         "ID", "Student Name", "Email", "Grade", "Status", "Reg. Date");
        System.out.println("-".repeat(85));

        for (Registration registration : registrations) {
            Student student = registration.getStudent();
            if (student != null) {
                String grade = registration.getGrade() != null ? registration.getGrade() : "N/A";
                String date = registration.getRegistrationDate() != null ? 
                    registration.getRegistrationDate().toLocalDate().toString() : "N/A";

                System.out.printf("%-5d %-20s %-25s %-6s %-10s %-12s%n",
                                student.getStudentId(),
                                student.getFullName().length() > 20 ? 
                                    student.getFullName().substring(0, 17) + "..." : student.getFullName(),
                                student.getEmail().length() > 25 ? 
                                    student.getEmail().substring(0, 22) + "..." : student.getEmail(),
                                grade,
                                registration.getStatus(),
                                date);
            }
        }

        System.out.println("\nTotal enrolled students: " + registrations.size());
    }

    private void courseEnrollmentStatistics() {
        System.out.println("\n--- Course Enrollment Statistics ---");
        List<Course> courses = courseDAO.getAllCourses();

        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }

        System.out.printf("%-10s %-25s %-10s %-15s%n", 
                         "Code", "Course Name", "Credits", "Enrolled");
        System.out.println("-".repeat(65));

        for (Course course : courses) {
            int enrollmentCount = registrationDAO.getEnrollmentCount(course.getCourseId());

            System.out.printf("%-10s %-25s %-10d %-15d%n",
                            course.getCourseCode(),
                            course.getCourseName().length() > 25 ? 
                                course.getCourseName().substring(0, 22) + "..." : course.getCourseName(),
                            course.getCredits(),
                            enrollmentCount);
        }
    }

    private void studentRegistrationStatistics() {
        System.out.println("\n--- Student Registration Statistics ---");
        List<Student> students = studentDAO.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.printf("%-5s %-20s %-25s %-15s%n", 
                         "ID", "Student Name", "Email", "Courses");
        System.out.println("-".repeat(70));

        for (Student student : students) {
            int registrationCount = registrationDAO.getRegistrationCount(student.getStudentId());

            System.out.printf("%-5d %-20s %-25s %-15d%n",
                            student.getStudentId(),
                            student.getFullName().length() > 20 ? 
                                student.getFullName().substring(0, 17) + "..." : student.getFullName(),
                            student.getEmail().length() > 25 ? 
                                student.getEmail().substring(0, 22) + "..." : student.getEmail(),
                            registrationCount);
        }
    }

    // ================== UTILITY METHODS ==================

    private void displayStudentDetails(Student student) {
        System.out.println("\n--- Student Details ---");
        System.out.println("ID: " + student.getStudentId());
        System.out.println("Name: " + student.getFullName());
        System.out.println("Email: " + student.getEmail());
        System.out.println("Phone: " + (student.getPhone() != null ? student.getPhone() : "N/A"));
        System.out.println("Date of Birth: " + (student.getDateOfBirth() != null ? student.getDateOfBirth() : "N/A"));
        System.out.println("Enrollment Date: " + (student.getEnrollmentDate() != null ? student.getEnrollmentDate() : "N/A"));
    }

    private void displayCourseDetails(Course course) {
        System.out.println("\n--- Course Details ---");
        System.out.println("ID: " + course.getCourseId());
        System.out.println("Code: " + course.getCourseCode());
        System.out.println("Name: " + course.getCourseName());
        System.out.println("Description: " + (course.getDescription() != null ? course.getDescription() : "N/A"));
        System.out.println("Credits: " + course.getCredits());
        System.out.println("Instructor: " + (course.getInstructor() != null ? course.getInstructor() : "TBA"));
        System.out.println("Created Date: " + (course.getCreatedDate() != null ? course.getCreatedDate() : "N/A"));
    }

    private int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}

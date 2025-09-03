# Student Course Registration System

## Overview
A comprehensive Java application implementing a Student Course Registration System using **Java**, **JDBC**, and **MySQL**. This project demonstrates CRUD operations, many-to-many relationships, and proper OOP design patterns including the **Data Access Object (DAO) pattern**.

## Features
- **Student Management**: Add, view, update, delete, and search students
- **Course Management**: Add, view, update, delete, and search courses  
- **Registration Management**: Register students for courses, drop students, update grades
- **Many-to-Many Relationship**: Students can register for multiple courses, courses can have multiple students
- **Advanced Reporting**: View courses by student, students by course, enrollment statistics
- **Console-based Menu**: Interactive command-line interface
- **Data Validation**: Email uniqueness, course code uniqueness, duplicate registration prevention

## Technology Stack
- **Programming Language**: Java 8+
- **Database**: MySQL 8.0+
- **JDBC Driver**: MySQL Connector/J
- **Design Patterns**: DAO Pattern, Singleton Pattern
- **Architecture**: Layered Architecture (Model, DAO, Utility, Main)

## Project Structure
```
student-registration-system/
├── src/main/java/com/studentregistration/
│   ├── model/                     # Entity classes
│   │   ├── Student.java          # Student model
│   │   ├── Course.java           # Course model
│   │   └── Registration.java     # Registration model
│   ├── dao/                      # Data Access Layer
│   │   ├── StudentDAO.java       # Student DAO interface
│   │   ├── StudentDAOImpl.java   # Student DAO implementation
│   │   ├── CourseDAO.java        # Course DAO interface
│   │   ├── CourseDAOImpl.java    # Course DAO implementation
│   │   ├── RegistrationDAO.java  # Registration DAO interface
│   │   └── RegistrationDAOImpl.java # Registration DAO implementation
│   ├── util/                     # Utility classes
│   │   └── DatabaseConnection.java # Database connection manager
│   └── main/                     # Main application
│       └── StudentRegistrationApp.java # Console application
├── sql/                          # Database scripts
│   ├── schema.sql               # Database schema
│   ├── sample_data.sql          # Sample data insertion
│   └── sample_queries.sql       # Sample SQL queries with joins
└── lib/                         # External libraries (MySQL Connector)
```

## Database Schema
The system uses three main tables with proper relationships:

### Tables
1. **students** - Stores student information
2. **courses** - Stores course information  
3. **registrations** - Junction table for many-to-many relationship

### Key Features
- **Foreign Key Constraints**: Ensures data integrity
- **Unique Constraints**: Prevents duplicate emails and course codes
- **Indexes**: Optimized for performance
- **Enum Types**: Registration status management
- **Timestamps**: Automatic date tracking

## Prerequisites
- **Java Development Kit (JDK) 8 or higher**
- **MySQL Server 8.0 or higher**
- **MySQL Connector/J (JDBC Driver)**
- **IDE**: IntelliJ IDEA, Eclipse, or VS Code (optional)

## Installation & Setup

### 1. Database Setup
1. **Install MySQL Server** on your system
2. **Start MySQL service**
3. **Create database** by running:
   ```sql
   CREATE DATABASE student_registration_db;
   ```
4. **Execute schema script**:
   ```bash
   mysql -u root -p student_registration_db < sql/schema.sql
   ```
5. **Insert sample data** (optional):
   ```bash
   mysql -u root -p student_registration_db < sql/sample_data.sql
   ```

### 2. Java Application Setup
1. **Download MySQL Connector/J**:
   - Download from [MySQL Official Site](https://dev.mysql.com/downloads/connector/j/)
   - Place `mysql-connector-java-x.x.x.jar` in the `lib/` directory

2. **Configure Database Connection**:
   - Open `src/main/java/com/studentregistration/util/DatabaseConnection.java`
   - Update the following constants:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/student_registration_db";
   private static final String USERNAME = "your_mysql_username";
   private static final String PASSWORD = "your_mysql_password";
   ```

3. **Compile the Project**:
   ```bash
   # Navigate to project root
   cd student-registration-system

   # Compile with MySQL Connector in classpath
   javac -cp "lib/mysql-connector-java-8.0.33.jar" -d bin src/main/java/com/studentregistration/*/*.java src/main/java/com/studentregistration/*/*/*.java
   ```

4. **Run the Application**:
   ```bash
   # Run with MySQL Connector in classpath
   java -cp "bin:lib/mysql-connector-java-8.0.33.jar" com.studentregistration.main.StudentRegistrationApp
   ```

### Alternative: Using IDE
1. **Import project** into your IDE
2. **Add MySQL Connector JAR** to build path/classpath
3. **Update database credentials** in `DatabaseConnection.java`
4. **Run** `StudentRegistrationApp.java`

## Usage Guide

### Main Menu Options
1. **Student Management**
   - Add, view, search, update, delete students
   - Search by ID, email, or name

2. **Course Management**
   - Add, view, search, update, delete courses
   - Search by ID, code, name, or instructor

3. **Registration Management**
   - Register students for courses
   - Drop students from courses
   - Update grades and registration status

4. **Reports**
   - View courses for specific student
   - View students enrolled in specific course
   - Enrollment and registration statistics

### Sample Operations
- **Register Student**: Enter student ID and course ID to create registration
- **Update Grade**: Assign grades (A, B, C, D, F) to student-course combinations
- **View Reports**: Get detailed enrollment information using SQL joins

## Key Design Patterns

### 1. Data Access Object (DAO) Pattern
- **Interfaces**: Define contracts for data operations
- **Implementations**: Concrete classes with JDBC code
- **Benefits**: Separation of data access logic from business logic

### 2. Singleton Pattern
- **DatabaseConnection**: Ensures single database connection instance
- **Benefits**: Resource management and connection pooling concept

### 3. Model-View-Controller (MVC) Concept
- **Model**: Entity classes (Student, Course, Registration)
- **View**: Console-based user interface
- **Controller**: Main application class handling user interactions

## SQL Joins Implementation
The system extensively uses SQL joins for data retrieval:

### Join Examples
1. **Students with Courses**:
   ```sql
   SELECT s.first_name, s.last_name, c.course_code, c.course_name
   FROM students s
   JOIN registrations r ON s.student_id = r.student_id
   JOIN courses c ON r.course_id = c.course_id
   ```

2. **Course Enrollment Count**:
   ```sql
   SELECT c.course_name, COUNT(r.student_id) as enrolled_students
   FROM courses c
   LEFT JOIN registrations r ON c.course_id = r.course_id
   GROUP BY c.course_id
   ```

## Error Handling
- **Database Connection Errors**: Graceful connection failure handling
- **Data Validation**: Email uniqueness, course code validation
- **User Input Validation**: Number format validation, empty input handling
- **SQL Exceptions**: Comprehensive exception catching and logging

## Testing
- **Sample Data**: Pre-loaded test data for immediate testing
- **Edge Cases**: Duplicate registrations, non-existent records
- **Console Testing**: Interactive menu for all operations

## Interview Preparation Features
This project demonstrates key concepts for technical interviews:

### Database Concepts
- **Normalization**: Proper 3NF database design
- **Relationships**: One-to-many and many-to-many implementations
- **Constraints**: Foreign keys, unique constraints, indexes
- **Joins**: Inner joins, left joins, aggregation queries

### Java Concepts
- **OOP**: Encapsulation, inheritance, polymorphism
- **Design Patterns**: DAO, Singleton, MVC concepts
- **Exception Handling**: Try-catch blocks, resource management
- **Collections**: List, ArrayList usage
- **Date/Time**: LocalDate, LocalDateTime handling

### Software Engineering
- **Layered Architecture**: Separation of concerns
- **CRUD Operations**: Complete data management
- **Input Validation**: Data integrity and user experience
- **Resource Management**: Database connection handling

## Troubleshooting

### Common Issues
1. **ClassNotFoundException**: Ensure MySQL Connector JAR is in classpath
2. **SQLException**: Check database credentials and MySQL service status
3. **Connection Refused**: Verify MySQL is running on correct port (3306)
4. **Access Denied**: Check MySQL user permissions

### Debug Tips
- Enable MySQL general log to see executed queries
- Use try-catch blocks to capture detailed error messages
- Test database connection independently before running application

## Future Enhancements
- **Web Interface**: REST API with Spring Boot
- **Connection Pooling**: HikariCP or Apache DBCP
- **Unit Testing**: JUnit test cases for DAO methods
- **Logging**: Log4j integration for better debugging
- **Security**: Password encryption, SQL injection prevention
- **Batch Operations**: Bulk data import/export features

## Contributing
This is an educational project. Feel free to fork and enhance with additional features like:
- Prerequisites management
- Waitlist functionality
- Grade point average calculation
- Semester/term management
- Student transcript generation

## License
This project is created for educational purposes and interview preparation.

---

**Author**: Priyanshu Kumar Chauhan  
**Purpose**: Technical Interview Preparation  
**Focus**: Java, JDBC, MySQL, Design Patterns, Database Design

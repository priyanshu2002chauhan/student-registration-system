#!/bin/bash

echo "Starting Student Registration System..."
echo

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "ERROR: Java is not installed or not in PATH"
    echo "Please install Java JDK 8 or higher"
    exit 1
fi

# Check if MySQL Connector JAR exists
if ! ls lib/mysql-connector-java-*.jar 1> /dev/null 2>&1; then
    echo "ERROR: MySQL Connector JAR not found in lib directory"
    echo "Please download MySQL Connector/J and place it in the lib folder"
    exit 1
fi

# Compile Java files
echo "Compiling Java source files..."
mkdir -p bin
javac -cp "lib/*" -d bin src/main/java/com/studentregistration/*/*.java src/main/java/com/studentregistration/*/*/*.java

if [ $? -ne 0 ]; then
    echo "ERROR: Compilation failed"
    exit 1
fi

echo "Compilation successful!"
echo

# Run the application
echo "Starting application..."
java -cp "bin:lib/*" com.studentregistration.main.StudentRegistrationApp

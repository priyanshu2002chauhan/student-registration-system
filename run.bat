@echo off
echo Starting Student Registration System...
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java JDK 8 or higher
    pause
    exit /b 1
)

REM Check if MySQL Connector JAR exists
if not exist "lib\mysql-connector-java-*.jar" (
    echo ERROR: MySQL Connector JAR not found in lib directory
    echo Please download MySQL Connector/J and place it in the lib folder
    pause
    exit /b 1
)

REM Compile Java files
echo Compiling Java source files...
mkdir bin 2>nul
javac -cp "lib\*" -d bin src\main\java\com\studentregistration\*\*.java src\main\java\com\studentregistration\*\*\*.java

if %errorlevel% neq 0 (
    echo ERROR: Compilation failed
    pause
    exit /b 1
)

echo Compilation successful!
echo.

REM Run the application
echo Starting application...
java -cp "bin;lib\*" com.studentregistration.main.StudentRegistrationApp

pause

@echo off
REM Eight Queens Problem - Run Script for Windows
REM Lab 4 - CCS3104

echo ========================================
echo   Eight Queens Problem Solver
echo   Lab 4 - Backtracking Algorithm
echo ========================================
echo.

REM Set JAVA_HOME (adjust path if needed)
set JAVA_HOME=C:\Program Files\Java\jdk-21

echo Setting up Java environment...
echo JAVA_HOME: %JAVA_HOME%
echo.

echo Compiling project...
call mvnw.cmd clean compile

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: Compilation failed!
    echo Please check your Java installation and JAVA_HOME path.
    pause
    exit /b 1
)

echo.
echo Compilation successful!
echo.
echo Running Eight Queens application...
echo.

REM Run the application
java -cp "target/classes;target/lib/*" --module-path "target/lib" --add-modules javafx.controls,javafx.fxml com.csc3402.lab.ccs3104_lab.LAB4.EightQueens

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Alternative method: Using Maven JavaFX plugin...
    call mvnw.cmd javafx:run -f pom.xml -Djavafx.mainClass=com.csc3402.lab.ccs3104_lab.LAB4.EightQueens
)

echo.
echo Application closed.
pause

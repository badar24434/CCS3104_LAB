# Eight Queens Problem - Run Script for PowerShell
# Lab 4 - CCS3104

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Eight Queens Problem Solver" -ForegroundColor Cyan
Write-Host "  Lab 4 - Backtracking Algorithm" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Set JAVA_HOME (adjust path if needed)
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"

Write-Host "Setting up Java environment..." -ForegroundColor Yellow
Write-Host "JAVA_HOME: $env:JAVA_HOME" -ForegroundColor Gray
Write-Host ""

# Check if Java is available
try {
    $javaVersion = java -version 2>&1
    Write-Host "Java found: $($javaVersion[0])" -ForegroundColor Green
} catch {
    Write-Host "ERROR: Java not found!" -ForegroundColor Red
    Write-Host "Please install Java 21 or set JAVA_HOME correctly." -ForegroundColor Red
    pause
    exit 1
}

Write-Host ""
Write-Host "Compiling project..." -ForegroundColor Yellow
& .\mvnw.cmd clean compile

if ($LASTEXITCODE -ne 0) {
    Write-Host ""
    Write-Host "ERROR: Compilation failed!" -ForegroundColor Red
    Write-Host "Please check your Java installation and JAVA_HOME path." -ForegroundColor Red
    pause
    exit 1
}

Write-Host ""
Write-Host "Compilation successful!" -ForegroundColor Green
Write-Host ""
Write-Host "Running Eight Queens application..." -ForegroundColor Yellow
Write-Host ""

# Method 1: Direct Java execution
Write-Host "Starting application..." -ForegroundColor Cyan
$classpath = "target/classes"

# Get JavaFX jars from Maven dependencies
$javafxPath = "$env:USERPROFILE\.m2\repository\org\openjfx"

try {
    java --module-path "$javafxPath\javafx-controls\21;$javafxPath\javafx-fxml\21;$javafxPath\javafx-graphics\21;$javafxPath\javafx-base\21" `
         --add-modules javafx.controls,javafx.fxml `
         -cp $classpath `
         com.csc3402.lab.ccs3104_lab.LAB4.EightQueens
} catch {
    Write-Host ""
    Write-Host "Alternative method: Using Maven JavaFX plugin..." -ForegroundColor Yellow
    & .\mvnw.cmd javafx:run -f pom.xml "-Djavafx.mainClass=com.csc3402.lab.ccs3104_lab.LAB4.EightQueens"
}

Write-Host ""
Write-Host "Application closed." -ForegroundColor Green
Write-Host ""
Write-Host "Press any key to exit..." -ForegroundColor Gray
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")

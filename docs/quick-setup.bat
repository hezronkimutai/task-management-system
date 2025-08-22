@echo off
REM 🚀 Task Management System - Quick Setup Script for Windows
REM This script will help you set up the project structure quickly

echo 🚀 Setting up Task Management System...

REM Create main project directories
echo 📁 Creating project structure...

REM Backend structure
mkdir backend\src\main\java\com\taskmanagement\controller 2>nul
mkdir backend\src\main\java\com\taskmanagement\service 2>nul
mkdir backend\src\main\java\com\taskmanagement\repository 2>nul
mkdir backend\src\main\java\com\taskmanagement\entity 2>nul
mkdir backend\src\main\java\com\taskmanagement\dto 2>nul
mkdir backend\src\main\java\com\taskmanagement\security 2>nul
mkdir backend\src\main\java\com\taskmanagement\exception 2>nul
mkdir backend\src\main\java\com\taskmanagement\config 2>nul
mkdir backend\src\main\resources 2>nul
mkdir backend\src\test\java\com\taskmanagement 2>nul

REM Frontend structure  
mkdir frontend\src\components\auth 2>nul
mkdir frontend\src\components\tasks 2>nul
mkdir frontend\src\components\layout 2>nul
mkdir frontend\src\components\common 2>nul
mkdir frontend\src\components\forms 2>nul
mkdir frontend\src\pages\auth 2>nul
mkdir frontend\src\pages\tasks 2>nul
mkdir frontend\src\pages\dashboard 2>nul
mkdir frontend\src\hooks 2>nul
mkdir frontend\src\services 2>nul
mkdir frontend\src\contexts 2>nul
mkdir frontend\src\types 2>nul
mkdir frontend\src\utils 2>nul
mkdir frontend\src\constants 2>nul
mkdir frontend\src\styles 2>nul
mkdir frontend\public 2>nul
mkdir frontend\src\__tests__\components 2>nul
mkdir frontend\src\__tests__\services 2>nul
mkdir frontend\src\__tests__\hooks 2>nul

REM Documentation structure
mkdir docs\api 2>nul
mkdir docs\deployment 2>nul
mkdir docs\development 2>nul

echo ✅ Project structure created!

REM Create basic files
echo 📄 Creating basic configuration files...

REM Backend pom.xml
(
echo ^<?xml version="1.0" encoding="UTF-8"?^>
echo ^<project xmlns="http://maven.apache.org/POM/4.0.0"
echo          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
echo          xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd"^>
echo     ^<modelVersion^>4.0.0^</modelVersion^>
echo.    
echo     ^<groupId^>com.taskmanagement^</groupId^>
echo     ^<artifactId^>task-management-backend^</artifactId^>
echo     ^<version^>1.0.0^</version^>
echo     ^<packaging^>jar^</packaging^>
echo.    
echo     ^<name^>Task Management Backend^</name^>
echo     ^<description^>Backend API for Task Management System^</description^>
echo.    
echo     ^<parent^>
echo         ^<groupId^>org.springframework.boot^</groupId^>
echo         ^<artifactId^>spring-boot-starter-parent^</artifactId^>
echo         ^<version^>3.2.5^</version^>
echo         ^<relativePath/^>
echo     ^</parent^>
echo.    
echo     ^<properties^>
echo         ^<java.version^>17^</java.version^>
echo         ^<jwt.version^>0.12.3^</jwt.version^>
echo     ^</properties^>
echo.    
echo     ^<dependencies^>
echo         ^<!-- Spring Boot Starters --^>
echo         ^<dependency^>
echo             ^<groupId^>org.springframework.boot^</groupId^>
echo             ^<artifactId^>spring-boot-starter-web^</artifactId^>
echo         ^</dependency^>
echo.        
echo         ^<dependency^>
echo             ^<groupId^>org.springframework.boot^</groupId^>
echo             ^<artifactId^>spring-boot-starter-data-jpa^</artifactId^>
echo         ^</dependency^>
echo.        
echo         ^<dependency^>
echo             ^<groupId^>org.springframework.boot^</groupId^>
echo             ^<artifactId^>spring-boot-starter-security^</artifactId^>
echo         ^</dependency^>
echo.        
echo         ^<dependency^>
echo             ^<groupId^>org.springframework.boot^</groupId^>
echo             ^<artifactId^>spring-boot-starter-validation^</artifactId^>
echo         ^</dependency^>
echo.        
echo         ^<!-- Database --^>
echo         ^<dependency^>
echo             ^<groupId^>com.h2database^</groupId^>
echo             ^<artifactId^>h2^</artifactId^>
echo             ^<scope^>runtime^</scope^>
echo         ^</dependency^>
echo.        
echo         ^<!-- JWT --^>
echo         ^<dependency^>
echo             ^<groupId^>io.jsonwebtoken^</groupId^>
echo             ^<artifactId^>jjwt-api^</artifactId^>
echo             ^<version^>${jwt.version}^</version^>
echo         ^</dependency^>
echo.        
echo         ^<dependency^>
echo             ^<groupId^>io.jsonwebtoken^</groupId^>
echo             ^<artifactId^>jjwt-impl^</artifactId^>
echo             ^<version^>${jwt.version}^</version^>
echo             ^<scope^>runtime^</scope^>
echo         ^</dependency^>
echo.        
echo         ^<dependency^>
echo             ^<groupId^>io.jsonwebtoken^</groupId^>
echo             ^<artifactId^>jjwt-jackson^</artifactId^>
echo             ^<version^>${jwt.version}^</version^>
echo             ^<scope^>runtime^</scope^>
echo         ^</dependency^>
echo.        
echo         ^<!-- Testing --^>
echo         ^<dependency^>
echo             ^<groupId^>org.springframework.boot^</groupId^>
echo             ^<artifactId^>spring-boot-starter-test^</artifactId^>
echo             ^<scope^>test^</scope^>
echo         ^</dependency^>
echo.        
echo         ^<dependency^>
echo             ^<groupId^>org.springframework.security^</groupId^>
echo             ^<artifactId^>spring-security-test^</artifactId^>
echo             ^<scope^>test^</scope^>
echo         ^</dependency^>
echo     ^</dependencies^>
echo.    
echo     ^<build^>
echo         ^<plugins^>
echo             ^<plugin^>
echo                 ^<groupId^>org.springframework.boot^</groupId^>
echo                 ^<artifactId^>spring-boot-maven-plugin^</artifactId^>
echo             ^</plugin^>
echo         ^</plugins^>
echo     ^</build^>
echo ^</project^>
) > backend\pom.xml

echo ✅ Configuration files created!

echo.
echo 🎉 Project setup complete!
echo.
echo 📋 Next steps:
echo 1. Navigate to backend\ and run: mvnw.cmd spring-boot:run
echo 2. Navigate to frontend\ and run: npm install ^&^& npm start
echo 3. Start implementing according to GITHUB_ISSUES.md
echo.
echo 🔗 Useful URLs:
echo - Backend API: http://localhost:8081/api
echo - Frontend App: http://localhost:3000
echo - H2 Console: http://localhost:8081/api/h2-console
echo.
echo Happy coding! 🚀

pause

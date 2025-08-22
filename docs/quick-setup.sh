#!/bin/bash

# 🚀 Task Management System - Quick Setup Script
# This script will help you set up the project structure quickly

echo "🚀 Setting up Task Management System..."

# Create main project directories
echo "📁 Creating project structure..."

# Backend structure
mkdir -p backend/src/main/java/com/taskmanagement/{controller,service,repository,entity,dto,security,exception,config}
mkdir -p backend/src/main/resources
mkdir -p backend/src/test/java/com/taskmanagement

# Frontend structure  
mkdir -p frontend/src/{components/{auth,tasks,layout,common,forms},pages/{auth,tasks,dashboard},hooks,services,contexts,types,utils,constants,styles}
mkdir -p frontend/public
mkdir -p frontend/src/__tests__/{components,services,hooks}

# Documentation structure
mkdir -p docs/{api,deployment,development}

echo "✅ Project structure created!"

# Create basic files
echo "📄 Creating basic configuration files..."

# Backend pom.xml
cat > backend/pom.xml << 'EOF'
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>com.taskmanagement</groupId>
    <artifactId>task-management-backend</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>
    
    <name>Task Management Backend</name>
    <description>Backend API for Task Management System</description>
    
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.5</version>
        <relativePath/>
    </parent>
    
    <properties>
        <java.version>17</java.version>
        <jwt.version>0.12.3</jwt.version>
    </properties>
    
    <dependencies>
        <!-- Spring Boot Starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        
        <!-- Database -->
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>
        
        <!-- JWT -->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-api</artifactId>
            <version>${jwt.version}</version>
        </dependency>
        
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-impl</artifactId>
            <version>${jwt.version}</version>
            <scope>runtime</scope>
        </dependency>
        
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-jackson</artifactId>
            <version>${jwt.version}</version>
            <scope>runtime</scope>
        </dependency>
        
        <!-- Testing -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
EOF

# Backend application.properties
cat > backend/src/main/resources/application.properties << 'EOF'
# Server Configuration
server.port=8081
server.servlet.context-path=/api

# Database Configuration
spring.datasource.url=jdbc:h2:mem:taskdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# H2 Console (Development only)
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JWT Configuration
app.jwt.secret=mySecretKey
app.jwt.expiration=86400000

# CORS Configuration
app.cors.allowed-origins=http://localhost:3000

# Logging
logging.level.com.taskmanagement=INFO
logging.level.org.springframework.security=INFO
logging.level.org.springframework.web=INFO
EOF

# Main Application Class
cat > backend/src/main/java/com/taskmanagement/TaskManagementApplication.java << 'EOF'
package com.taskmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskManagementApplication.class, args);
    }
}
EOF

# Frontend package.json
cat > frontend/package.json << 'EOF'
{
  "name": "task-management-frontend",
  "version": "1.0.0",
  "private": true,
  "description": "React frontend for Task Management System",
  "dependencies": {
    "@heroicons/react": "^2.0.18",
    "@types/node": "^16.7.13",
    "@types/react": "^18.0.0",
    "@types/react-dom": "^18.0.0",
    "axios": "^1.6.0",
    "react": "^18.2.0",
    "react-dom": "^18.2.0",
    "react-hook-form": "^7.48.0",
    "react-hot-toast": "^2.4.1",
    "react-router-dom": "^6.8.0",
    "react-scripts": "^5.0.1",
    "typescript": "^4.4.2"
  },
  "scripts": {
    "start": "react-scripts start",
    "build": "react-scripts build",
    "test": "react-scripts test",
    "eject": "react-scripts eject"
  },
  "eslintConfig": {
    "extends": [
      "react-app",
      "react-app/jest"
    ]
  },
  "browserslist": {
    "production": [
      ">0.2%",
      "not dead",
      "not op_mini all"
    ],
    "development": [
      "last 1 chrome version",
      "last 1 firefox version",
      "last 1 safari version"
    ]
  },
  "devDependencies": {
    "@types/jest": "^27.0.1",
    "autoprefixer": "^10.4.16",
    "postcss": "^8.4.32",
    "tailwindcss": "^3.3.6"
  }
}
EOF

# Frontend Tailwind config
cat > frontend/tailwind.config.js << 'EOF'
/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          50: '#eff6ff',
          500: '#3b82f6',
          600: '#2563eb',
          700: '#1d4ed8',
        },
        gray: {
          50: '#f9fafb',
          100: '#f3f4f6',
          200: '#e5e7eb',
          300: '#d1d5db',
          400: '#9ca3af',
          500: '#6b7280',
          600: '#4b5563',
          700: '#374151',
          800: '#1f2937',
          900: '#111827',
        },
      },
    },
  },
  plugins: [],
}
EOF

# PostCSS config
cat > frontend/postcss.config.js << 'EOF'
module.exports = {
  plugins: {
    tailwindcss: {},
    autoprefixer: {},
  },
}
EOF

# Frontend environment variables
cat > frontend/.env << 'EOF'
REACT_APP_API_BASE_URL=http://localhost:8081/api
REACT_APP_JWT_EXPIRATION=86400000
REACT_APP_VERSION=1.0.0
EOF

# Create .gitignore for root
cat > .gitignore << 'EOF'
# Compiled output
/dist
/tmp
/out-tsc

# Dependencies
node_modules/
*/node_modules/

# IDE
.vscode/
.idea/
*.swp
*.swo

# OS
.DS_Store
Thumbs.db

# Logs
logs
*.log
npm-debug.log*
yarn-debug.log*
yarn-error.log*

# Environment variables
.env
.env.local
.env.development.local
.env.test.local
.env.production.local

# Coverage directory used by tools like istanbul
coverage/
*.lcov

# Build outputs
build/
dist/

# Backend specific
backend/target/
backend/.mvn/
backend/mvnw
backend/mvnw.cmd

# Frontend specific
frontend/build/
frontend/.eslintcache

# Temporary files
*.tmp
*.temp
EOF

# Create backend .gitignore
cat > backend/.gitignore << 'EOF'
HELP.md
target/
!.mvn/wrapper/maven-wrapper.jar
!**/src/main/**/target/
!**/src/test/**/target/

### STS ###
.apt_generated
.classpath
.factorypath
.project
.settings
.springBeans
.sts4-cache

### IntelliJ IDEA ###
.idea
*.iws
*.iml
*.ipr

### NetBeans ###
/nbproject/private/
/nbbuild/
/dist/
/nbdist/
/.nb-gradle/
build/
!**/src/main/**/build/
!**/src/test/**/build/

### VS Code ###
.vscode/

### OS ###
.DS_Store
Thumbs.db
EOF

echo "✅ Configuration files created!"

# Create basic TypeScript types
cat > frontend/src/types/index.ts << 'EOF'
// User types
export interface User {
  id: number;
  username: string;
  email: string;
  role: UserRole;
  createdAt: string;
  updatedAt: string;
}

export enum UserRole {
  USER = 'USER',
  ADMIN = 'ADMIN'
}

// Task types
export interface Task {
  id: number;
  title: string;
  description?: string;
  status: TaskStatus;
  priority: TaskPriority;
  dueDate?: string;
  assignedUser?: User;
  createdBy: User;
  createdAt: string;
  updatedAt: string;
}

export enum TaskStatus {
  TODO = 'TODO',
  IN_PROGRESS = 'IN_PROGRESS',
  DONE = 'DONE'
}

export enum TaskPriority {
  LOW = 'LOW',
  MEDIUM = 'MEDIUM',
  HIGH = 'HIGH'
}

// Auth types
export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
}

export interface AuthResponse {
  token: string;
  type: string;
  user: User;
}

// API types
export interface ApiResponse<T> {
  data: T;
  message?: string;
  success: boolean;
}

export interface ApiError {
  message: string;
  status: number;
  timestamp: string;
}

// Form types
export interface TaskFormData {
  title: string;
  description?: string;
  priority: TaskPriority;
  dueDate?: string;
  assignedUserId?: number;
}
EOF

echo "✅ TypeScript types created!"

echo ""
echo "🎉 Project setup complete!"
echo ""
echo "📋 Next steps:"
echo "1. Navigate to backend/ and run: ./mvnw spring-boot:run"
echo "2. Navigate to frontend/ and run: npm install && npm start"
echo "3. Start implementing according to GITHUB_ISSUES.md"
echo ""
echo "🔗 Useful URLs:"
echo "- Backend API: http://localhost:8081/api"
echo "- Frontend App: http://localhost:3000"
echo "- H2 Console: http://localhost:8081/api/h2-console"
echo ""
echo "Happy coding! 🚀"

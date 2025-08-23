# Task Management System

A full-stack task management application built with Spring Boot (backend) and React with TypeScript (frontend).

## Build Status

![CI Status](https://github.com/hezronkimutai/task-management-system/workflows/Continuous%20Integration/badge.svg)

## Technology Stack & Versions

### Backend
- **Java**: 17+ (JDK 17 or higher)
- **Spring Boot**: 3.4.1
- **Maven**: 3.9+ (wrapper included)
- **JWT**: 0.12.6 (JJWT)
- **Database**: H2 (in-memory)
- **API Documentation**: SpringDoc OpenAPI 2.7.0
- **Testing**: JUnit 5, Spring Boot Test

### Frontend
- **Node.js**: 18+ (LTS recommended)
- **npm**: 8+ 
- **React**: 19.1.1
- **TypeScript**: 4.9.5
- **Testing**: Jest, React Testing Library

## Project Structure

```
task-management-system/
├── backend/          # Spring Boot REST API
├── frontend/         # React TypeScript application
└── README.md        # Project documentation
```

## Prerequisites

Before running this application, make sure you have the following installed:

- **Java 17** or higher (OpenJDK recommended)
- **Node.js** 18+ (LTS recommended)
- **npm** 8+ (comes with Node.js)
- **Maven** (included via wrapper)

## Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/hezronkimutai/task-management-system.git
cd task-management-system
```

### 2. Backend Setup
```bash
cd backend
./mvnw spring-boot:run
```
Backend runs on **http://localhost:8080**

### 3. Frontend Setup (in new terminal)
```bash
cd frontend
npm install
npm start
```
Frontend runs on **http://localhost:3000**

## Backend Setup and Run

1. **Navigate to the backend directory:**
   ```bash
   cd backend
   ```

2. **Install dependencies and run:**
   ```bash
   # Using Maven wrapper (recommended)
   ./mvnw spring-boot:run
   
   # Or using Maven directly
   mvn spring-boot:run
   ```

3. **Alternative: Build and run JAR file:**
   ```bash
   ./mvnw clean package
   java -jar target/task-management-backend-0.0.1-SNAPSHOT.jar
   ```

The backend server will start on **http://localhost:8080**

### Backend Features:
- Spring Boot 3.4.1 with Java 24
- REST API endpoints
- H2 in-memory database
- JWT authentication (JJWT 0.12.6)
- Interactive API documentation with Swagger UI (SpringDoc OpenAPI 2.7.0)
- CORS configuration for frontend integration
- Database console available at: http://localhost:8080/h2-console
- Latest security features with Spring Security 6.4.2

### Database Configuration:
- **URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** `password`

## Frontend Setup and Run

1. **Navigate to the frontend directory:**
   ```bash
   cd frontend
   ```

2. **Install dependencies:**
   ```bash
   npm install
   ```

3. **Start the development server:**
   ```bash
   npm start
   ```

The frontend application will start on **http://localhost:3000**

### Frontend Features:
- React 19.1.1 with TypeScript 4.9.5
- Modern React with latest features
- Responsive design
- Hot reload during development
- Configured to work with backend API
- Web Vitals 5.1.0 for performance monitoring
- Router support with react-router-dom 7.1.7
- HTTP client with axios 1.7.9

## Development Workflow

### Running Both Services Simultaneously

1. **Terminal 1 - Backend:**
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```

2. **Terminal 2 - Frontend:**
   ```bash
   cd frontend
   npm start
   ```

### Building for Production

**Backend:**
```bash
cd backend
./mvnw clean package
```

**Frontend:**
```bash
cd frontend
npm run build
```

## API Endpoints & Documentation

The backend provides REST API endpoints with comprehensive Swagger documentation:

- **API Base URL**: `http://localhost:8080/api`
- **Swagger UI (Interactive Docs)**: `http://localhost:8080/swagger-ui/index.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`
- **H2 Database Console**: `http://localhost:8080/h2-console`

### Database Configuration
- **URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: `password`

### Key API Features
- ✅ **Task CRUD Operations**: Create, Read, Update, Delete tasks
- ✅ **User Management**: User registration and authentication
- ✅ **JWT Authentication**: Secure API access with Bearer tokens
- ✅ **Task Assignment**: Assign tasks to users
- ✅ **Status Management**: TODO, IN_PROGRESS, DONE
- ✅ **Priority Levels**: LOW, MEDIUM, HIGH

### Using Swagger UI
1. Start the backend application
2. Navigate to `http://localhost:8080/swagger-ui/index.html`
3. Click "Authorize" to enter your JWT token
4. Test endpoints directly from the interface

## Testing

Run tests to ensure everything works correctly:

**Backend Tests (JUnit 5):**
```bash
cd backend
./mvnw test
```

**Frontend Tests (Jest):**
```bash
cd frontend
npm test
```

**Build Verification:**
```bash
# Backend
cd backend && ./mvnw clean package

# Frontend
cd frontend && npm run build
```

## Security & Best Practices

### Security Features
- ✅ **JWT Authentication**: Secure token-based authentication
- ✅ **Password Hashing**: BCrypt password security
- ✅ **CORS Protection**: Configured for frontend integration
- ✅ **Input Validation**: Server-side request validation
- ✅ **Error Handling**: Secure error responses

### Development Best Practices
- ✅ **Test-Driven Development**: Comprehensive unit test coverage
- ✅ **Clean Architecture**: Separation of concerns (Entity → Repository → Service → Controller)
- ✅ **API Documentation**: Interactive Swagger documentation
- ✅ **Type Safety**: TypeScript for frontend development
- ✅ **Dependency Management**: Maven for backend, npm for frontend

## Troubleshooting

### Common Issues

1. **Port already in use:**
   - Backend (8080): Change `server.port` in `application.properties`
   - Frontend (3000): Use `PORT=3001 npm start`

2. **CORS errors:**
   - Ensure backend is running on port 8080
   - Check CORS configuration in Spring Boot

3. **Database issues:**
   - H2 runs in-memory, data resets on restart
   - Access H2 console to verify data

4. **Build failures:**
   - Backend: Ensure Java 17+ is installed
   - Frontend: Try `rm -rf node_modules && npm install`

## CI/CD & Quality Assurance

![CI Status](https://github.com/hezronkimutai/task-management-system/workflows/Continuous%20Integration/badge.svg)

### Automated Testing
- ✅ **Backend**: JUnit 5 tests with Spring Boot Test
- ✅ **Frontend**: Jest tests with React Testing Library  
- ✅ **Build Verification**: Compilation checks
- ✅ **Pull Request Protection**: Tests must pass before merge

### Branch Protection
The `main` branch requires:
- ✅ Passing status checks
- ✅ Pull request reviews
- ✅ Up-to-date branches

## Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Make your changes
4. **Run tests locally**: `./mvnw test` (backend) and `npm test` (frontend)
5. Commit your changes: `git commit -m 'Add amazing feature'`
6. Push to branch: `git push origin feature/amazing-feature`
7. Submit a pull request

**Requirements:**
- All tests must pass ✅
- Code follows project conventions ✅
- Include tests for new features ✅

## License

This project is licensed under the MIT License.

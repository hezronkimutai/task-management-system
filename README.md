# Task Management System

A full-stack task management application built with Spring Boot (backend) and React with TypeScript (frontend).

## Build Status

![CI Status](https://github.com/hezronkimutai/task-management-system/workflows/Continuous%20Integration/badge.svg)
![Security](https://github.com/hezronkimutai/task-management-system/workflows/Security%20and%20Code%20Quality/badge.svg)

## Technology Stack & Versions

### Backend
- **Java**: 24.0.2 (Oracle JDK 24)
- **Spring Boot**: 3.4.1
- **Maven**: 3.9.11 (wrapper)
- **JWT**: 0.12.6 (JJWT)
- **Database**: H2 (in-memory)
- **API Documentation**: SpringDoc OpenAPI 2.7.0
- **Build Tool**: Maven

### Frontend
- **Node.js**: 24.6.0
- **npm**: 11.5.1
- **React**: 19.1.1
- **TypeScript**: 4.9.5
- **React Scripts**: 5.0.1
- **Testing Library**: @testing-library/react 16.3.0
- **Web Vitals**: 5.1.0
- **Additional Dependencies**:
  - axios: 1.7.9 (HTTP client)
  - react-router-dom: 7.1.7 (routing)

## Project Structure

```
task-management-system/
├── backend/          # Spring Boot REST API
├── frontend/         # React TypeScript application
└── docs/            # Documentation files
```

## Prerequisites

Before running this application, make sure you have the following installed:

- **Java 24** or higher (Oracle JDK or OpenJDK)
- **Node.js** 24.6.0 or higher (recommended to use NVM)
- **npm** 11.5.1 or higher
- **Maven** (included via wrapper)

## Quick Start

### Version Verification

Before getting started, verify you have the correct versions installed:

```bash
# Check Java version (should be 24 or higher)
java -version

# Check Node.js version (should be 24.6.0 or higher)
node --version

# Check npm version (should be 11.5.1 or higher)
npm --version
```

### Installing Required Versions

**Java 24:**
- Download from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or use [OpenJDK](https://openjdk.org/)

**Node.js 24.6.0 (using NVM - recommended):**
```bash
# Install NVM if not already installed
# Windows: Download from https://github.com/coreybutler/nvm-windows
# Linux/Mac: curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.0/install.sh | bash

# Install and use Node.js 24.6.0
nvm install 24.6.0
nvm use 24.6.0
```

### Option 1: Using Quick Setup Scripts

For Windows:
```bash
./docs/quick-setup.bat
```

For Linux/Mac:
```bash
./docs/quick-setup.sh
```

### Option 2: Manual Setup

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

## API Endpoints

The backend provides REST API endpoints. Once running, you can access:

- API Base URL: `http://localhost:8080`
- **Swagger UI (API Documentation)**: `http://localhost:8080/swagger-ui/index.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`
- H2 Database Console: `http://localhost:8080/h2-console`
- Health Check: `http://localhost:8080/actuator/health` (if actuator is enabled)

### API Documentation

The application includes comprehensive API documentation powered by **SpringDoc OpenAPI 2.7.0**:

- **Interactive API Testing**: Use Swagger UI to test API endpoints directly in your browser
- **Complete API Specification**: View all available endpoints, request/response schemas, and authentication requirements
- **Authentication Support**: Swagger UI supports JWT token authentication for testing secured endpoints
- **OpenAPI 3.0 Compliant**: Industry-standard API documentation format

#### How to Use Swagger UI:
1. Start the backend application
2. Navigate to `http://localhost:8080/swagger-ui/index.html`
3. Explore available endpoints and their documentation
4. For authenticated endpoints, click "Authorize" and enter your JWT token
5. Test endpoints directly from the interface

## Testing

**Backend Tests:**
```bash
cd backend
./mvnw test
```

**Frontend Tests:**
```bash
cd frontend
npm test
```

## Configuration

### Backend Configuration
The backend configuration can be found in `backend/src/main/resources/application.properties`:
- Database settings
- JWT configuration
- CORS settings
- Server port (default: 8080)

### Frontend Configuration
The frontend is configured to connect to the backend at `http://localhost:8080`. This can be modified in the frontend source code if needed.

## Security

### Vulnerability Management
This project implements comprehensive security scanning and vulnerability management:

- ✅ **Zero npm audit vulnerabilities** - All frontend dependencies are secure
- ✅ **Automated security scanning** - CI/CD pipeline includes security checks
- ✅ **Dependency monitoring** - Regular vulnerability scanning and updates
- ✅ **Package overrides** - Strategic dependency version management

### Security Features
- **Authentication**: JWT-based secure authentication
- **CORS Protection**: Configured Cross-Origin Resource Sharing
- **Input Validation**: Server-side request validation
- **Error Handling**: Secure error responses without information leakage

### Security Monitoring
The project includes automated security monitoring through:
- Daily npm audit scans
- Weekly comprehensive security reports
- Pull request dependency reviews
- CodeQL analysis for code security

### Security Documentation
- [Security Assessment](docs/SECURITY_ASSESSMENT.md) - Comprehensive security strategy
- [Security Resolution Report](docs/SECURITY_RESOLUTION_REPORT.md) - Vulnerability resolution details

For security concerns, please follow the responsible disclosure process outlined in our security documentation.

## Troubleshooting

### Common Issues:

1. **Port already in use:**
   - Backend (8080): Change `server.port` in `application.properties`
   - Frontend (3000): Use `PORT=3001 npm start` to run on different port

2. **CORS errors:**
   - Ensure backend CORS configuration includes your frontend URL
   - Check `cors.allowed-origins` in `application.properties`

3. **Database connection issues:**
   - H2 database runs in-memory, data is lost on restart
   - Access H2 console to verify database state

4. **Version compatibility issues:**
   - **Java**: Ensure Java 24 is installed and JAVA_HOME is set correctly
   - **Node.js**: Use Node.js 24.6.0 (recommended via NVM)
   - **TypeScript**: Project uses TypeScript 4.9.5 (compatible with React Scripts 5.0.1)

5. **Maven/Node issues:**
   - Backend: `./mvnw clean install` (Java 24 required)
   - Frontend: `rm -rf node_modules && npm install` (clean install)

6. **Web Vitals API errors:**
   - Updated to Web Vitals 5.1.0 with new API (onCLS, onINP, onFCP, onLCP, onTTFB)
   - FID metric replaced with INP (Interaction to Next Paint)

### Development Environment Setup:
- Use latest IDEs with Java 24 and Node.js 24 support
- VS Code extensions: Java Extension Pack, ES7+ React/Redux/React-Native snippets
- IntelliJ IDEA: Enable Java 24 preview features if needed

## Recent Updates (August 2025)

### Backend Updates:
- ✅ **Java**: Upgraded to Java 24.0.2 (latest stable)
- ✅ **Spring Boot**: Updated to 3.4.1 (latest stable)
- ✅ **JWT**: Updated to JJWT 0.12.6 (latest)
- ✅ **SpringDoc OpenAPI**: Updated to 2.7.0 (latest, compatible with Spring Boot 3.4.1)
- ✅ **Maven**: Using latest wrapper 3.9.11
- ✅ **Dependencies**: All dependencies updated to latest compatible versions

### Frontend Updates:
- ✅ **Node.js**: Upgraded to v24.6.0 (latest stable)
- ✅ **npm**: Updated to v11.5.1 (latest)
- ✅ **React**: Already on v19.1.1 (latest)
- ✅ **TypeScript**: Using v4.9.5 (latest compatible with React Scripts)
- ✅ **Web Vitals**: Updated to v5.1.0 with new API
- ✅ **Testing Libraries**: All updated to latest versions
- ✅ **Dependencies**: Clean installation without legacy peer deps

### Key Improvements:
- Modern development stack with latest stable versions
- Enhanced performance monitoring with Web Vitals 5.1.0
- Improved security with latest JWT implementation
- Better type safety with modern TypeScript
- Clean dependency resolution without legacy workarounds

## CI/CD and Quality Assurance

This project uses **GitHub Actions** for continuous integration and automated testing. All pull requests must pass automated tests before being merged.

### Automated Testing Pipeline

![CI Status](https://github.com/hezronkimutai/task-management-system/workflows/Continuous%20Integration/badge.svg)
![Security](https://github.com/hezronkimutai/task-management-system/workflows/Security%20and%20Code%20Quality/badge.svg)

**✅ What gets tested automatically:**
- **Backend Tests**: JUnit 5 tests with Spring Boot Test (`./mvnw test`)
- **Frontend Tests**: Jest tests with React Testing Library (`npm test`)
- **Build Verification**: Both frontend and backend compilation
- **Security Scanning**: Dependency vulnerabilities with Trivy and OWASP
- **Code Quality**: Automated dependency audits

**🚫 Pull Request Requirements:**
- All tests must pass ✅
- Security scans must pass ✅
- Code builds successfully ✅
- At least one approving review ✅ (when branch protection is enabled)

### Running Tests Locally

Before submitting a pull request, ensure all tests pass locally:

```bash
# Backend tests
cd backend
./mvnw test

# Frontend tests  
cd frontend
npm test

# Frontend tests with coverage
npm test -- --coverage --watchAll=false

# Build verification
cd backend && ./mvnw clean compile
cd frontend && npm run build
```

### Branch Protection

The `main` branch is protected with the following rules:
- ✅ Require status checks to pass before merging
- ✅ Require pull request reviews before merging  
- ✅ Require branches to be up to date before merging
- ✅ Include administrators in restrictions

### Workflows

1. **Continuous Integration** (`.github/workflows/ci.yml`)
   - Runs on every pull request and push to `main`/`develop`
   - Parallel execution of backend and frontend tests
   - Integration verification and build checks

2. **Security and Code Quality** (`.github/workflows/security.yml`)
   - Vulnerability scanning with Trivy
   - Dependency audits for both npm and Maven
   - Results uploaded to GitHub Security tab

For detailed contributing guidelines, see [CONTRIBUTING.md](CONTRIBUTING.md).

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. **Ensure all tests pass locally** (see CI/CD section above)
5. Submit a pull request

**Development Requirements:**
- Java 24+ for backend development
- Node.js 24.6.0+ for frontend development
- Follow the version requirements specified above
- All automated tests must pass before merge

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Additional Documentation

For more detailed information, check the `docs/` directory:
- Implementation guides
- Setup instructions
- GitHub configuration

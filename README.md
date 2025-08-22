# Task Management System

A full-stack task management application built with Spring Boot (backend) and React with TypeScript (frontend).

## Project Structure

```
task-management-system/
├── backend/          # Spring Boot REST API
├── frontend/         # React TypeScript application
└── docs/            # Documentation files
```

## Prerequisites

Before running this application, make sure you have the following installed:

- **Java 17** or higher
- **Node.js** (version 16 or higher)
- **npm** or **yarn**
- **Maven** (for backend)

## Quick Start

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
- REST API endpoints
- H2 in-memory database
- JWT authentication
- CORS configuration for frontend integration
- Database console available at: http://localhost:8080/h2-console

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
- React 19 with TypeScript
- Responsive design
- Hot reload during development
- Configured to work with backend API

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
- H2 Database Console: `http://localhost:8080/h2-console`
- Health Check: `http://localhost:8080/actuator/health` (if actuator is enabled)

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

4. **Maven/Node issues:**
   - Ensure Java 17+ and Node.js 16+ are installed
   - Clear caches: `./mvnw clean` (backend) or `npm install --force` (frontend)

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test both backend and frontend
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Additional Documentation

For more detailed information, check the `docs/` directory:
- Implementation guides
- Setup instructions
- GitHub configuration

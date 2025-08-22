# 📋 GitHub Issues Breakdown - 2-Hour Coding Assignment

This document provides a detailed breakdown of GitHub issues for implementing the Task Management System within the 2-hour time constraint of the coding assignment. Issues are prioritized to deliver a working full-stack application with authentication and task CRUD operations.

## 🏷️ Issue Labels

Before creating issues, set up these labels in your GitHub repository:

- `phase-1-setup` - Project setup and basic structure (0-30 min)
- `phase-2-backend` - Backend API implementation (30-75 min)
- `phase-3-frontend` - Frontend development (75-105 min)
- `phase-4-integration` - Integration and testing (105-120 min)
- `backend` - Backend related tasks
- `frontend` - Frontend related tasks
- `authentication` - Authentication related
- `task-management` - Task CRUD operations
- `critical` - Must complete for working app
- `important` - Should complete if time permits
- `nice-to-have` - Optional enhancements

---

## ⏱️ PHASE 1: PROJECT SETUP (0-30 minutes)

### Issue #1: Quick Project Initialization and Structure
**Labels:** `phase-1-setup`, `critical`
**Time Estimate:** 15 minutes

**Description:**
Rapidly set up the complete project structure for both backend and frontend applications using Spring Initializr and Create React App.

**Acceptance Criteria:**
- [ ] Create root project directory with backend/ and frontend/ folders
- [ ] Initialize Spring Boot backend with required dependencies via Spring Initializr
- [ ] Initialize React frontend with TypeScript
- [ ] Configure basic .gitignore files
- [ ] Test both applications start successfully

**Quick Setup Commands:**
```bash
# Backend (Spring Initializr)
# Dependencies: Web, JPA, Security, H2, Validation
# Frontend
npx create-react-app frontend --template typescript
```

---

### Issue #2: Essential Dependencies and Database Setup
**Labels:** `phase-1-setup`, `backend`, `critical`
**Time Estimate:** 15 minutes

**Description:**
Configure essential dependencies and H2 database for rapid development.

**Acceptance Criteria:**
- [ ] Add JWT dependencies to pom.xml
- [ ] Configure application.properties for H2 database
- [ ] Enable H2 console for development
- [ ] Configure basic CORS settings
- [ ] Test database connectivity

**Key Dependencies:**
```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>
```

---

## ⚡ PHASE 2: BACKEND API (30-75 minutes)

### Issue #3: User Entity and Security Configuration
**Labels:** `phase-2-backend`, `authentication`, `critical`
**Time Estimate:** 20 minutes

**Description:**
Create User entity and basic JWT security configuration in one go for speed.

**Acceptance Criteria:**
- [ ] Create User entity with required fields (id, username, email, password, role, createdAt)
- [ ] Create Role enum (USER, ADMIN)
- [ ] Set up UserRepository
- [ ] Configure basic Spring Security with JWT
- [ ] Create JwtUtils class for token generation
- [ ] Test user creation and JWT generation

**Speed Implementation Note:**
Combine entity creation with basic security setup to save time.

---

### Issue #4: Authentication REST API
**Labels:** `phase-2-backend`, `authentication`, `critical`
**Time Estimate:** 15 minutes

**Description:**
Implement essential authentication endpoints with minimal validation.

**Acceptance Criteria:**
- [ ] Create AuthController with signup and login endpoints
- [ ] Implement basic request/response DTOs
- [ ] Add password hashing with BCrypt
- [ ] Return JWT tokens on successful authentication
- [ ] Test endpoints with simple curl commands

**Essential Endpoints:**
```
POST /api/auth/register
POST /api/auth/login
```

---

### Issue #5: Task Entity and CRUD API
**Labels:** `phase-2-backend`, `task-management`, `critical`
**Time Estimate:** 20 minutes

**Description:**
Create Task entity and complete CRUD operations in minimal time.

**Acceptance Criteria:**
- [ ] Create Task entity (id, title, description, status, priority, assigneeId, creatorId, createdAt, updatedAt)
- [ ] Create TaskStatus enum (TODO, IN_PROGRESS, DONE)
- [ ] Create Priority enum (LOW, MEDIUM, HIGH)
- [ ] Implement TaskRepository with basic queries
- [ ] Create TaskController with all CRUD endpoints
- [ ] Add basic authorization checks

**Essential Endpoints:**
```
GET /api/tasks
POST /api/tasks
PUT /api/tasks/{id}
DELETE /api/tasks/{id}
GET /api/users (for assignment dropdown)
```

---

## 🎨 PHASE 3: FRONTEND DEVELOPMENT (75-105 minutes)

### Issue #6: Frontend Dependencies and Basic Setup
**Labels:** `phase-3-frontend`, `critical`
**Time Estimate:** 10 minutes

**Description:**
Install essential frontend dependencies and configure basic routing.

**Acceptance Criteria:**
- [ ] Install React Router, Axios, and Tailwind CSS
- [ ] Configure Tailwind CSS
- [ ] Set up basic routing structure
- [ ] Create environment configuration for API URL
- [ ] Test development server

**Quick Dependencies:**
```bash
npm install react-router-dom axios
npm install -D tailwindcss postcss autoprefixer
```

---

### Issue #7: Authentication Context and API Service
**Labels:** `phase-3-frontend`, `authentication`, `critical`
**Time Estimate:** 15 minutes

**Description:**
Create authentication context and centralized API service for rapid development.

**Acceptance Criteria:**
- [ ] Create AuthContext with login/logout functions
- [ ] Set up Axios instance with JWT interceptors
- [ ] Create authentication service functions
- [ ] Implement token storage in localStorage
- [ ] Add protected route wrapper

**Files to Create:**
- `src/contexts/AuthContext.tsx`
- `src/services/api.ts`
- `src/components/ProtectedRoute.tsx`

---

### Issue #8: Login and Registration Forms
**Labels:** `phase-3-frontend`, `authentication`, `critical`
**Time Estimate:** 15 minutes

**Description:**
Create simple but functional authentication forms with basic styling.

**Acceptance Criteria:**
- [ ] Create Login component with form validation
- [ ] Create Register component with form validation
- [ ] Apply basic Tailwind CSS styling
- [ ] Implement form submission to backend API
- [ ] Add error handling and loading states
- [ ] Test authentication flow

**Components:**
- `src/components/auth/LoginForm.tsx`
- `src/components/auth/RegisterForm.tsx`

---

### Issue #9: Task Management Dashboard
**Labels:** `phase-3-frontend`, `task-management`, `critical`
**Time Estimate:** 20 minutes

**Description:**
Create a functional task dashboard with CRUD operations and status columns.

**Acceptance Criteria:**
- [ ] Create TaskDashboard with three columns (TODO, IN_PROGRESS, DONE)
- [ ] Implement TaskCard component for individual tasks
- [ ] Create TaskForm modal for create/edit operations
- [ ] Add task status update functionality
- [ ] Implement task filtering by status and assignee
- [ ] Connect to backend API with proper error handling

**Components:**
- `src/components/tasks/TaskDashboard.tsx`
- `src/components/tasks/TaskCard.tsx`
- `src/components/tasks/TaskForm.tsx`

---

## 🔧 PHASE 4: INTEGRATION & TESTING (105-120 minutes)

### Issue #10: End-to-End Integration and Basic Testing
**Labels:** `phase-4-integration`, `critical`
**Time Estimate:** 10 minutes

**Description:**
Ensure complete integration between frontend and backend with basic functionality testing.

**Acceptance Criteria:**
- [ ] Test complete authentication flow (register → login → dashboard)
- [ ] Test all task CRUD operations
- [ ] Verify task status transitions work correctly
- [ ] Test task assignment to different users
- [ ] Ensure proper error handling for invalid operations
- [ ] Test responsive design on mobile and desktop

---

### Issue #11: Seed Data and Final Polish
**Labels:** `phase-4-integration`, `important`
**Time Estimate:** 5 minutes

**Description:**
Add sample data and final touches for demonstration.

**Acceptance Criteria:**
- [ ] Create seed data with 2 users (admin@example.com, user@example.com)
- [ ] Add 5-10 sample tasks with different statuses and assignments
- [ ] Test application with sample data
- [ ] Add basic navigation and logout functionality
- [ ] Ensure application is production-ready

---

## 🏆 BONUS FEATURES (If Time Remaining)

### Issue #12: Advanced UI Features
**Labels:** `nice-to-have`, `frontend`
**Time Estimate:** Variable

**Description:**
Implement bonus features if time permits.

**Possible Enhancements:**
- [ ] Drag and drop task reordering
- [ ] Task comments/activity log
- [ ] Real-time updates with WebSocket
- [ ] Advanced filtering and search
- [ ] Dark mode toggle
- [ ] Task due dates and notifications

---

## 📊 Time Management Summary

**Critical Path (Must Complete):**
- **Phase 1 (0-30 min):** Issues #1, #2
- **Phase 2 (30-75 min):** Issues #3, #4, #5
- **Phase 3 (75-105 min):** Issues #6, #7, #8, #9
- **Phase 4 (105-120 min):** Issues #10, #11

**Total Critical Issues:** 11
**Estimated Time:** 115 minutes
**Buffer Time:** 5 minutes

**Success Criteria:**
- ✅ Working authentication (register, login, logout)
- ✅ Complete task CRUD operations
- ✅ Task status management (TODO → IN_PROGRESS → DONE)
- ✅ User assignment functionality
- ✅ Responsive UI with proper error handling
- ✅ JWT-based security implementation

**Architecture Delivered:**
- Spring Boot backend with H2 database
- React TypeScript frontend
- JWT authentication
- RESTful API design
- Responsive UI with Tailwind CSS
- Proper error handling and validation

This streamlined approach focuses on delivering a working full-stack application within the 2-hour constraint while maintaining code quality and demonstrating full-stack development skills.
This streamlined approach focuses on delivering a working full-stack application within the 2-hour constraint while maintaining code quality and demonstrating full-stack development skills.

---

## 📋 Detailed Implementation Guidelines

### Backend Implementation Tips:
1. **Use Spring Initializr** for rapid project setup with dependencies
2. **Minimal Configuration** - focus on application.properties essentials
3. **Combined Approach** - implement related features together (Entity + Repository + Controller)
4. **H2 Database** - use in-memory database for speed
5. **Basic Validation** - use Bean Validation annotations sparingly

### Frontend Implementation Tips:
1. **Create React App** with TypeScript template for quick setup
2. **Tailwind CSS** for rapid styling without custom CSS
3. **Context API** instead of Redux for simpler state management
4. **Functional Components** with hooks throughout
5. **Minimal Dependencies** - only install what's absolutely necessary

### Time-Saving Strategies:
1. **Skip Complex Validation** - implement basic validation only
2. **Use Default Styling** - leverage Tailwind's utility classes
3. **Minimal Error Handling** - basic try-catch and user feedback
4. **No Complex Testing** - focus on manual testing during development
5. **Seed Data** - create sample data programmatically

### Quality Assurance:
- Test each phase completion before moving to next phase
- Ensure authentication works before implementing tasks
- Verify API endpoints with browser/Postman quickly
- Test responsive design throughout development

This issue breakdown ensures successful delivery of a working Task Management System within the 2-hour coding assignment timeframe.

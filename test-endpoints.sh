#!/bin/bash

# Task Management System - API Endpoint Testing Script
# This script provides examples for testing all available endpoints

BASE_URL="http://localhost:8080"
TOKEN=""

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}=== Task Management System - API Endpoint Testing ===${NC}"
echo
echo -e "${YELLOW}Note: Make sure the Spring Boot application is running on port 8080${NC}"
echo -e "${YELLOW}Start with: cd backend && ./mvnw spring-boot:run${NC}"
echo

# Function to show section header
show_section() {
    echo
    echo -e "${GREEN}=== $1 ===${NC}"
    echo
}

# Function to show test case
show_test() {
    echo -e "${BLUE}$1${NC}"
    echo -e "${YELLOW}$2${NC}"
    echo "$3"
    echo
}

show_section "AUTHENTICATION ENDPOINTS"

show_test "1. User Registration" \
    "POST /api/auth/register" \
    "curl -X POST $BASE_URL/api/auth/register \\
  -H \"Content-Type: application/json\" \\
  -d '{\"username\":\"testuser\",\"email\":\"test@example.com\",\"password\":\"password123\"}'"

show_test "2. User Login" \
    "POST /api/auth/login" \
    "curl -X POST $BASE_URL/api/auth/login \\
  -H \"Content-Type: application/json\" \\
  -d '{\"username\":\"testuser\",\"password\":\"password123\"}'"

show_test "3. Validation Error Test" \
    "POST /api/auth/register (Invalid Data)" \
    "curl -X POST $BASE_URL/api/auth/register \\
  -H \"Content-Type: application/json\" \\
  -d '{\"username\":\"\",\"email\":\"invalid-email\",\"password\":\"123\"}'"

show_test "4. Duplicate User Test" \
    "POST /api/auth/register (Same Username)" \
    "curl -X POST $BASE_URL/api/auth/register \\
  -H \"Content-Type: application/json\" \\
  -d '{\"username\":\"testuser\",\"email\":\"another@example.com\",\"password\":\"password123\"}'"

show_section "TEST ENDPOINTS (Development)"

show_test "5. Basic Connectivity Test" \
    "GET /api/test" \
    "curl -X GET $BASE_URL/api/test"

show_test "6. Public Endpoint Test" \
    "GET /api/test/public" \
    "curl -X GET $BASE_URL/api/test/public"

show_test "7. Create Test User" \
    "POST /api/test/create-user" \
    "curl -X POST $BASE_URL/api/test/create-user"

show_test "8. Create Admin User" \
    "POST /api/test/create-admin" \
    "curl -X POST $BASE_URL/api/test/create-admin"

show_test "9. Generate JWT Token" \
    "POST /api/test/generate-jwt" \
    "curl -X POST $BASE_URL/api/test/generate-jwt \\
  -d \"username=testuser&password=password123\""

show_test "10. List All Users" \
    "GET /api/test/users" \
    "curl -X GET $BASE_URL/api/test/users"

show_section "PROTECTED ENDPOINTS (Require JWT Token)"

echo -e "${RED}Note: Replace YOUR_JWT_TOKEN with actual token from login/register response${NC}"
echo

show_test "11. Authenticated Test Endpoint" \
    "GET /api/test/authenticated" \
    "curl -X GET $BASE_URL/api/test/authenticated \\
  -H \"Authorization: Bearer YOUR_JWT_TOKEN\""

show_section "FUTURE ENDPOINTS (To be implemented)"

echo -e "${YELLOW}These endpoints will be available as the system develops:${NC}"
echo
echo "• POST /api/tasks - Create new task"
echo "• GET /api/tasks - List all tasks"
echo "• GET /api/tasks/{id} - Get specific task"
echo "• PUT /api/tasks/{id} - Update task"
echo "• DELETE /api/tasks/{id} - Delete task"
echo "• GET /api/users/profile - Get user profile"
echo "• PUT /api/users/profile - Update user profile"
echo "• GET /api/admin/users - Admin: List all users"
echo "• DELETE /api/admin/users/{id} - Admin: Delete user"

show_section "TESTING WORKFLOW"

echo "1. Start the backend application:"
echo "   cd backend && ./mvnw spring-boot:run"
echo
echo "2. Test basic connectivity:"
echo "   curl $BASE_URL/api/test/public"
echo
echo "3. Register a new user and save the token:"
echo "   curl -X POST $BASE_URL/api/auth/register -H \"Content-Type: application/json\" -d '{\"username\":\"myuser\",\"email\":\"user@example.com\",\"password\":\"password123\"}'"
echo
echo "4. Use the token for authenticated requests:"
echo "   curl -X GET $BASE_URL/api/test/authenticated -H \"Authorization: Bearer YOUR_TOKEN_HERE\""
echo
echo "5. Test error handling with invalid data"
echo

show_section "RESPONSE FORMATS"

echo -e "${GREEN}Successful Authentication Response:${NC}"
echo '{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "id": 1,
  "username": "testuser",
  "email": "test@example.com",
  "role": "USER"
}'
echo

echo -e "${RED}Error Response:${NC}"
echo '{
  "timestamp": "2025-08-23T07:46:32.123",
  "status": 400,
  "error": "Bad Request",
  "message": "Username is already taken!"
}'

#!/bin/bash

# Test script for Task Management API
BASE_URL="http://localhost:8080/api"

echo "=== Task Management API Test ==="
echo "Base URL: $BASE_URL"
echo

# Test 1: Register a new user
echo "1. Registering a new user..."
REGISTER_RESPONSE=$(curl -s -X POST "$BASE_URL/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "taskuser",
    "email": "taskuser@example.com",
    "password": "password123"
  }')

echo "Register Response: $REGISTER_RESPONSE"
echo

# Test 2: Login to get JWT token
echo "2. Logging in to get JWT token..."
LOGIN_RESPONSE=$(curl -s -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "taskuser",
    "password": "password123"
  }')

echo "Login Response: $LOGIN_RESPONSE"

# Extract JWT token from response
JWT_TOKEN=$(echo $LOGIN_RESPONSE | sed -n 's/.*"token":"\([^"]*\)".*/\1/p')
echo "JWT Token: $JWT_TOKEN"
echo

# Test 3: Get all users (for assignment dropdown)
echo "3. Getting all users..."
USERS_RESPONSE=$(curl -s -X GET "$BASE_URL/users" \
  -H "Authorization: Bearer $JWT_TOKEN")

echo "Users Response: $USERS_RESPONSE"
echo

# Test 4: Create a new task
echo "4. Creating a new task..."
CREATE_TASK_RESPONSE=$(curl -s -X POST "$BASE_URL/tasks" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $JWT_TOKEN" \
  -d '{
    "title": "Test Task",
    "description": "This is a test task created via API",
    "status": "TODO",
    "priority": "HIGH"
  }')

echo "Create Task Response: $CREATE_TASK_RESPONSE"

# Extract task ID
TASK_ID=$(echo $CREATE_TASK_RESPONSE | sed -n 's/.*"id":\([0-9]*\).*/\1/p')
echo "Created Task ID: $TASK_ID"
echo

# Test 5: Get all tasks
echo "5. Getting all tasks..."
TASKS_RESPONSE=$(curl -s -X GET "$BASE_URL/tasks" \
  -H "Authorization: Bearer $JWT_TOKEN")

echo "Tasks Response: $TASKS_RESPONSE"
echo

# Test 6: Get specific task by ID
if [ ! -z "$TASK_ID" ]; then
  echo "6. Getting task by ID ($TASK_ID)..."
  TASK_BY_ID_RESPONSE=$(curl -s -X GET "$BASE_URL/tasks/$TASK_ID" \
    -H "Authorization: Bearer $JWT_TOKEN")
  
  echo "Task by ID Response: $TASK_BY_ID_RESPONSE"
  echo
fi

# Test 7: Update the task
if [ ! -z "$TASK_ID" ]; then
  echo "7. Updating task ($TASK_ID)..."
  UPDATE_TASK_RESPONSE=$(curl -s -X PUT "$BASE_URL/tasks/$TASK_ID" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer $JWT_TOKEN" \
    -d '{
      "title": "Updated Test Task",
      "description": "This task has been updated via API",
      "status": "IN_PROGRESS",
      "priority": "MEDIUM"
    }')
  
  echo "Update Task Response: $UPDATE_TASK_RESPONSE"
  echo
fi

# Test 8: Delete the task
if [ ! -z "$TASK_ID" ]; then
  echo "8. Deleting task ($TASK_ID)..."
  DELETE_RESPONSE=$(curl -s -X DELETE "$BASE_URL/tasks/$TASK_ID" \
    -H "Authorization: Bearer $JWT_TOKEN")
  
  echo "Delete Response: $DELETE_RESPONSE"
  echo
fi

echo "=== API Test Complete ==="

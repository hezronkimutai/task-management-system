# 🚀 GitHub Repository and Issues Creation Guide

This guide will help you create a new public GitHub repository and automatically add all the implementation issues for your Task Management System project.

## Prerequisites

### 1. Install GitHub CLI
**Windows:**
```bash
winget install --id GitHub.cli
```

**Mac:**
```bash
brew install gh
```

**Linux (Ubuntu/Debian):**
```bash
curl -fsSL https://cli.github.com/packages/githubcli-archive-keyring.gpg | sudo dd of=/usr/share/keyrings/githubcli-archive-keyring.gpg
echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/githubcli-archive-keyring.gpg] https://cli.github.com/packages stable main" | sudo tee /etc/apt/sources.list.d/github-cli.list > /dev/null
sudo apt update
sudo apt install gh
```

### 2. Authenticate with GitHub
```bash
gh auth login
```
- Select "GitHub.com"
- Choose "HTTPS" as the protocol
- Authenticate via web browser or with a token

## 🎯 Option 1: Quick Setup (Recommended)

### Step 1: Run the Repository Creation Script

**On Windows:**
```bash
# Make sure you're in your project directory
cd "c:\Users\Hezron Kimutai\projects\spring"

# Run the script
.\create-github-repo.bat
```

**On Linux/Mac:**
```bash
# Make the script executable
chmod +x create-github-repo.sh

# Run the script
./create-github-repo.sh
```

### Step 2: What the Script Does
1. ✅ Creates a new public repository named `task-management-system`
2. ✅ Sets up 13 custom labels for issue organization
3. ✅ Creates 20 detailed GitHub issues with:
   - Acceptance criteria
   - Time estimates
   - Priority levels
   - Technical specifications
4. ✅ Clones the repository to your local machine

### Step 3: Copy Your Files
After the script runs, copy your existing project files to the new repository:

```bash
# Navigate to the new repository
cd task-management-system

# Copy your implementation guides and setup files
cp ../IMPLEMENTATION_GUIDE.md .
cp ../GITHUB_ISSUES.md .
cp ../IMPLEMENTATION_CHECKLIST.md .
cp ../quick-setup.sh .
cp ../quick-setup.bat .

# Add and commit files
git add .
git commit -m "Add implementation guides and setup scripts"
git push origin main
```

## 🛠️ Option 2: Manual Setup

If you prefer to create the repository manually:

### Step 1: Create Repository
```bash
gh repo create task-management-system \
    --description "A full-stack Task Management System built with Spring Boot and React TypeScript" \
    --public \
    --clone \
    --gitignore Java \
    --license MIT
```

### Step 2: Create Labels
```bash
cd task-management-system

# Create custom labels
gh label create "day-1" --description "Tasks for Day 1" --color "0052cc"
gh label create "day-2" --description "Tasks for Day 2" --color "5319e7"
gh label create "backend" --description "Backend related tasks" --color "d73a4a"
gh label create "frontend" --description "Frontend related tasks" --color "0075ca"
gh label create "setup" --description "Project setup tasks" --color "a2eeef"
gh label create "authentication" --description "Authentication related" --color "7057ff"
gh label create "task-management" --description "Task CRUD operations" --color "008672"
gh label create "ui/ux" --description "User interface tasks" --color "e99695"
gh label create "testing" --description "Testing related" --color "f9d0c4"
gh label create "documentation" --description "Documentation tasks" --color "fef2c0"
gh label create "priority-high" --description "High priority tasks" --color "b60205"
gh label create "priority-medium" --description "Medium priority tasks" --color "fbca04"
gh label create "priority-low" --description "Low priority tasks" --color "0e8a16"
```

### Step 3: Create Issues Manually
Use the content from `GITHUB_ISSUES.md` to create each issue individually using:
```bash
gh issue create --title "Issue Title" --body "Issue content from GITHUB_ISSUES.md" --label "appropriate,labels"
```

## 📋 What You'll Get

After running the script, your new repository will have:

### 🏷️ Labels (13 total)
- **Day Labels:** `day-1`, `day-2`
- **Component Labels:** `backend`, `frontend`, `setup`, `authentication`, `task-management`, `ui/ux`, `testing`, `documentation`
- **Priority Labels:** `priority-high`, `priority-medium`, `priority-low`

### 📝 Issues (20 total)

**Day 1 Issues (8 - Backend Focus):**
1. Project Setup and Folder Structure
2. Backend Dependencies and Configuration  
3. User Entity and Repository Setup
4. JWT Security Configuration
5. Authentication REST Controllers
6. Task Entity and Repository
7. Task Service Layer Implementation
8. Task REST Controllers

**Day 2 Issues (12 - Frontend Focus):**
9. Frontend Project Setup and Dependencies
10. TypeScript Type Definitions
11. Authentication Context and Hooks
12. API Service Layer
13. Authentication UI Components
14. Task Management UI Components
15. Navigation and Layout Components
16. State Management and Custom Hooks
17. Error Handling and Loading States
18. Responsive Design and Mobile Optimization
19. Testing Implementation
20. Documentation and Deployment Preparation

## 🎯 Next Steps After Repository Creation

1. **Review Issues:** Go through each issue and understand the requirements
2. **Set Milestones:** Create milestones for Day 1 and Day 2
3. **Assign Issues:** Assign issues to yourself or team members
4. **Start Development:** Begin with Day 1, Issue #1
5. **Track Progress:** Use the IMPLEMENTATION_CHECKLIST.md to track your progress

## 🔗 Useful Commands After Setup

```bash
# View all issues
gh issue list

# View issues for Day 1
gh issue list --label "day-1"

# View high priority issues
gh issue list --label "priority-high"

# Close an issue when completed
gh issue close [issue-number]

# Add a comment to an issue
gh issue comment [issue-number] --body "Progress update"
```

## 🎉 Repository URLs

After creation, your repository will be available at:
- **GitHub URL:** `https://github.com/[your-username]/task-management-system`
- **Clone URL:** `https://github.com/[your-username]/task-management-system.git`
- **Issues URL:** `https://github.com/[your-username]/task-management-system/issues`

## 🆘 Troubleshooting

### Authentication Issues
```bash
# Check authentication status
gh auth status

# Re-authenticate if needed
gh auth logout
gh auth login
```

### Permission Issues
```bash
# Check if you have permission to create repositories
gh repo list

# If issues persist, check your GitHub token permissions
```

### Script Issues
- Make sure GitHub CLI is properly installed and in your PATH
- Ensure you're authenticated with GitHub
- Check that you have permission to create public repositories
- Verify your internet connection

Happy coding! 🚀

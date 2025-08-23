# Contributing to Task Management System

## Overview
This repository uses automated testing and continuous integration to ensure code quality and stability. All contributions must pass automated tests before being merged into the main branch.

## Development Workflow

### Pull Request Requirements
Before your pull request can be merged, it must:

1. ✅ **Pass all automated tests** (Backend and Frontend)
2. ✅ **Pass security scans** (Dependencies and vulnerabilities)
3. ✅ **Pass code quality checks**
4. ✅ **Have at least one approving review** (when branch protection is enabled)

### Automated Testing

Our CI/CD pipeline runs the following tests automatically on every pull request:

#### Backend Tests (Spring Boot)
- **Framework**: JUnit 5 with Spring Boot Test
- **Command**: `./mvnw test`
- **Location**: `backend/src/test/java/`
- **Reports**: Available in PR checks and GitHub Actions

#### Frontend Tests (React)
- **Framework**: Jest with React Testing Library
- **Command**: `npm test -- --coverage --ci --watchAll=false`
- **Location**: `frontend/src/`
- **Coverage**: Uploaded to Codecov (when configured)

#### Integration Tests
- **Build verification** for both frontend and backend
- **Dependency compatibility** checks
- **Cross-service integration** validation

### Code Quality

We maintain code quality through:

- **Automated testing**: Comprehensive test coverage for both frontend and backend
- **Type safety**: TypeScript for frontend development
- **Build verification**: Ensuring code compiles successfully

## Running Tests Locally

Before submitting a pull request, you can run tests locally to catch issues early:

### Backend Tests
```bash
cd backend
./mvnw test
```

### Frontend Tests
```bash
cd frontend
npm test
```

### Frontend Tests with Coverage
```bash
cd frontend
npm test -- --coverage --watchAll=false
```

### Build Verification
```bash
# Backend build
cd backend
./mvnw clean compile

# Frontend build
cd frontend
npm run build
```

## GitHub Actions Workflows

### Main CI Workflow (`.github/workflows/ci.yml`)
- **Triggers**: Pull requests and pushes to `main` and `develop` branches
- **Jobs**:
  - `backend-tests`: Runs Spring Boot tests with Maven
  - `frontend-tests`: Runs React tests with npm
  - `integration-check`: Verifies builds and summarizes results

## Branch Protection Rules

To enforce these requirements, configure the following branch protection rules for the `main` branch:

1. **Require status checks to pass before merging**
   - ✅ `Backend Tests (Spring Boot)`
   - ✅ `Frontend Tests (React)`
   - ✅ `Integration Check`
   - ✅ `Security Scan`
   - ✅ `Dependency Check`

2. **Require pull request reviews before merging**
   - ✅ Require at least 1 approving review
   - ✅ Dismiss stale reviews when new commits are pushed

3. **Additional settings**
   - ✅ Require branches to be up to date before merging
   - ✅ Include administrators in restrictions
   - ✅ Allow force pushes: **Disabled**
   - ✅ Allow deletions: **Disabled**

## Setting Up Branch Protection

To configure branch protection rules:

1. Go to your repository on GitHub
2. Navigate to **Settings** → **Branches**
3. Click **Add rule** for the `main` branch
4. Configure the protection settings as described above
5. Save the protection rule

## Troubleshooting

### Common Test Failures

#### Backend Tests Failing
- Check Java version compatibility (requires JDK 17)
- Verify Maven dependencies are properly resolved
- Review test database configuration in `application-test.properties`

#### Frontend Tests Failing
- Ensure Node.js version compatibility (requires Node 18+)
- Clear npm cache: `npm cache clean --force`
- Update snapshots if UI components changed: `npm test -- --updateSnapshot`

#### Integration Issues
- Verify both frontend and backend build successfully
- Check for port conflicts in test configurations
- Review application configuration files

### Getting Help

If you encounter issues with the CI/CD pipeline:

1. Check the **Actions** tab in the GitHub repository for detailed logs
2. Review the specific job that failed for error details
3. Ensure your local environment matches the CI environment (Java 17, Node 18)
4. Contact the maintainers if you need assistance

## Performance Optimization

The CI pipeline includes several optimizations:

- **Maven dependency caching** reduces build times
- **npm dependency caching** speeds up frontend builds
- **Parallel job execution** runs tests concurrently
- **Conditional uploads** only upload reports when needed

## Monitoring and Metrics

- **Test reports** are generated and uploaded for each run
- **Coverage reports** track code coverage trends
- **Security scan results** are uploaded to GitHub Security tab
- **Build duration** and **success rates** can be monitored in Actions

---

By following these guidelines, you help maintain the stability and security of the Task Management System. Thank you for contributing! 🚀

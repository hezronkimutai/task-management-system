# Environment Variables Setup

This document explains how to set up environment variables for the Task Management System backend.

## Quick Setup

1. Copy the example environment file:
   ```bash
   cp .env.example .env
   ```

2. Edit the `.env` file with your actual values:
   ```bash
   nano .env
   ```

## Environment Variables

### Database Configuration
- `DB_URL`: Database connection URL
- `DB_DRIVER`: Database driver class
- `DB_USERNAME`: Database username
- `DB_PASSWORD`: Database password

### JWT Configuration
- `JWT_SECRET`: Secret key for JWT token signing (MUST be at least 32 characters)
- `JWT_EXPIRATION`: Token expiration time in seconds (default: 86400 = 24 hours)

### CORS Configuration
- `CORS_ALLOWED_ORIGINS`: Comma-separated list of allowed origins
- `CORS_ALLOWED_METHODS`: Comma-separated list of allowed HTTP methods
- `CORS_ALLOWED_HEADERS`: Allowed headers for CORS requests
- `CORS_ALLOW_CREDENTIALS`: Whether to allow credentials in CORS requests

### H2 Console Configuration (Development Only)
- `H2_CONSOLE_ENABLED`: Enable H2 database console
- `H2_CONSOLE_PATH`: Path to H2 console
- `H2_CONSOLE_WEB_ALLOW_OTHERS`: Allow remote connections to H2 console

### JPA Configuration
- `JPA_DATABASE_PLATFORM`: JPA database platform/dialect
- `JPA_HIBERNATE_DDL_AUTO`: Hibernate DDL auto mode
- `JPA_SHOW_SQL`: Show SQL queries in logs
- `JPA_FORMAT_SQL`: Format SQL queries in logs

### Logging Configuration
- `LOG_LEVEL_ROOT`: Root logging level
- `LOG_LEVEL_TASKMANAGEMENT`: Application-specific logging level
- `LOG_LEVEL_SECURITY`: Spring Security logging level
- `LOG_LEVEL_HIBERNATE_SQL`: Hibernate SQL logging level

## Security Best Practices

1. **Never commit the `.env` file** to version control
2. **Use strong, unique JWT secrets** in production
3. **Disable H2 console** in production environments
4. **Use environment-specific** `.env` files for different stages
5. **Restrict CORS origins** to only necessary domains in production

## Production Considerations

For production deployments:

1. Use a production database (PostgreSQL, MySQL, etc.)
2. Set `H2_CONSOLE_ENABLED=false`
3. Use a strong, randomly generated `JWT_SECRET`
4. Set appropriate logging levels
5. Configure proper CORS origins
6. Use encrypted connections for database

## Example Production `.env`

```env
# Production Database (PostgreSQL)
DB_URL=jdbc:postgresql://your-db-host:5432/taskmanagement
DB_DRIVER=org.postgresql.Driver
DB_USERNAME=your_username
DB_PASSWORD=your_secure_password

# Strong JWT Secret
JWT_SECRET=your_super_secure_jwt_secret_key_that_is_at_least_32_characters_long

# Production CORS
CORS_ALLOWED_ORIGINS=https://yourdomain.com
CORS_ALLOW_CREDENTIALS=false

# Disable H2 Console
H2_CONSOLE_ENABLED=false

# Production JPA
JPA_HIBERNATE_DDL_AUTO=validate
JPA_SHOW_SQL=false
JPA_FORMAT_SQL=false

# Production Logging
LOG_LEVEL_ROOT=WARN
LOG_LEVEL_TASKMANAGEMENT=INFO
LOG_LEVEL_SECURITY=WARN
LOG_LEVEL_HIBERNATE_SQL=WARN
```

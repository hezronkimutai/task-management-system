package com.taskmanagement.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Database connectivity test component
 * Runs on application startup to verify database connection
 */
@Component
public class DatabaseConnectionTest implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnectionTest.class);

    private final DataSource dataSource;

    public DatabaseConnectionTest(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            logger.info("Database connection successful");
            logger.info("Database URL: {}", connection.getMetaData().getURL());
            logger.info("Database Name: {}", connection.getMetaData().getDatabaseProductName());
            logger.info("Database Version: {}", connection.getMetaData().getDatabaseProductVersion());
            // H2 console and JDBC URL information is only relevant for local development
            logger.debug("H2 Console available at: http://localhost:8080/h2-console");
            logger.debug("JDBC URL: jdbc:h2:mem:testdb");
        } catch (Exception e) {
            logger.error("Database connection failed: {}", e.getMessage());
            throw e;
        }
    }
}

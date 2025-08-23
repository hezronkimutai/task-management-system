package com.taskmanagement.config;

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

    private final DataSource dataSource;

    public DatabaseConnectionTest(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("✅ Database connection successful!");
            System.out.println("📊 Database URL: " + connection.getMetaData().getURL());
            System.out.println("🏷️  Database Name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("📱 Database Version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("🌐 H2 Console available at: http://localhost:8080/h2-console");
            System.out.println("🔗 Use JDBC URL: jdbc:h2:mem:testdb");
        } catch (Exception e) {
            System.err.println("❌ Database connection failed: " + e.getMessage());
            throw e;
        }
    }
}

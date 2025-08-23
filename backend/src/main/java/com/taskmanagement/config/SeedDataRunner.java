package com.taskmanagement.config;

import com.taskmanagement.entity.Role;
import com.taskmanagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Seeds a few test users when the backend is started in CI/E2E mode.
 * Controlled by the environment variable SEED_TEST_USERS=true to avoid accidental seeding in other environments.
 */
@Component
public class SeedDataRunner implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(SeedDataRunner.class);

    @Autowired
    private Environment env;

    @Autowired
    private UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Accept the flag from Spring Environment, system property (-D) or OS env var for robustness
        boolean seed = "true".equalsIgnoreCase(env.getProperty("SEED_TEST_USERS", "false"))
                || "true".equalsIgnoreCase(System.getProperty("SEED_TEST_USERS", "false"))
                || "true".equalsIgnoreCase(System.getenv("SEED_TEST_USERS"));
        if (!seed) return;

        // Create stable seeded accounts used by Playwright E2E in CI
        try {
            if (!userService.existsByUsername("e2e-admin")) {
                userService.createUser("e2e-admin", "e2e-admin@example.com", "Password123!", Role.ADMIN);
            }
            if (!userService.existsByUsername("e2e-user")) {
                userService.createUser("e2e-user", "e2e-user@example.com", "Password123!", Role.USER);
            }
            if (!userService.existsByUsername("e2e-guest")) {
                userService.createUser("e2e-guest", "e2e-guest@example.com", "Password123!", Role.USER);
            }
            log.info("SeedDataRunner: seeded test users (e2e-admin, e2e-user, e2e-guest)");
        } catch (Exception ex) {
            log.error("SeedDataRunner: failed to seed users", ex);
        }
    }
}

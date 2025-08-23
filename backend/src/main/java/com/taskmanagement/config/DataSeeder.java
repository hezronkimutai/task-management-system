package com.taskmanagement.config;

import com.taskmanagement.entity.Priority;
import com.taskmanagement.entity.Task;
import com.taskmanagement.entity.TaskStatus;
import com.taskmanagement.entity.User;
import com.taskmanagement.entity.Role;
import com.taskmanagement.repository.TaskRepository;
import com.taskmanagement.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Seed initial users and tasks for demo / development.
 * Runs on application startup when the active profile is not 'test'.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataSeeder.class);

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, TaskRepository taskRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Don't reseed if users exist
        long existing = userRepository.count();
        if (existing > 0) {
            logger.info("DataSeeder skipped: {} existing user(s) found", existing);
            return;
        }

        logger.info("DataSeeder starting: creating demo users and tasks");

        User admin = new User("admin", "admin@example.com", passwordEncoder.encode("password123"), Role.ADMIN);
        User user = new User("user", "user@example.com", passwordEncoder.encode("password123"), Role.USER);

    userRepository.save(admin);
    userRepository.save(user);
    logger.info("Created users: {}, {}", admin.getEmail(), user.getEmail());

        Long adminId = admin.getId();
        Long userId = user.getId();

        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Setup project README", "Create project README and documentation.", TaskStatus.DONE, Priority.LOW, adminId, adminId));
        tasks.add(new Task("Implement auth endpoints", "Add JWT authentication and user registration.", TaskStatus.IN_PROGRESS, Priority.HIGH, adminId, adminId));
        tasks.add(new Task("Design database schema", "Finalize entity relationships and migrations.", TaskStatus.TODO, Priority.MEDIUM, null, adminId));
        tasks.add(new Task("Create frontend layout", "Implement top nav and basic pages.", TaskStatus.IN_PROGRESS, Priority.MEDIUM, userId, adminId));
        tasks.add(new Task("Write E2E tests", "Add Playwright tests for login and tasks flows.", TaskStatus.TODO, Priority.HIGH, userId, userId));
    tasks.add(new Task("Fix bug in task update", "Correct status update logic.", TaskStatus.TODO, Priority.MEDIUM, null, userId));
        tasks.add(new Task("Prepare release", "Bump version and prepare changelog.", TaskStatus.TODO, Priority.LOW, adminId, adminId));

    taskRepository.saveAll(tasks);
    logger.info("Saved {} demo tasks", tasks.size());
    }
}

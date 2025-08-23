package com.taskmanagement.service;

import com.taskmanagement.entity.Role;
import com.taskmanagement.entity.User;
import com.taskmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for User entity operations.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Create a new user.
     *
     * @param user the user to create
     * @return the created user
     */
    public User createUser(User user) {
        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Create a new user with username, email, and password.
     *
     * @param username the username
     * @param email the email
     * @param password the password
     * @return the created user
     */
    public User createUser(String username, String email, String password) {
        User user = new User(username, email, password);
        return createUser(user);
    }

    /**
     * Create a new user with username, email, password, and role.
     *
     * @param username the username
     * @param email the email
     * @param password the password
     * @param role the role
     * @return the created user
     */
    public User createUser(String username, String email, String password, Role role) {
        User user = new User(username, email, password, role);
        return createUser(user);
    }

    /**
     * Find user by username.
     *
     * @param username the username
     * @return Optional containing the user if found
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Find user by email.
     *
     * @param email the email
     * @return Optional containing the user if found
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Find user by ID.
     *
     * @param id the user ID
     * @return Optional containing the user if found
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Get all users.
     *
     * @return list of all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Check if username exists.
     *
     * @param username the username to check
     * @return true if exists, false otherwise
     */
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Check if email exists.
     *
     * @param email the email to check
     * @return true if exists, false otherwise
     */
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Update user.
     *
     * @param user the user to update
     * @return the updated user
     */
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Delete user by ID.
     *
     * @param id the user ID
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Validate password.
     *
     * @param rawPassword the raw password
     * @param encodedPassword the encoded password
     * @return true if passwords match, false otherwise
     */
    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}

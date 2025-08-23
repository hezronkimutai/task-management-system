package com.taskmanagement.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Configuration class to load environment variables from .env file.
 */
public class EnvironmentConfig implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        ConfigurableEnvironment environment = context.getEnvironment();
        
        try {
            Dotenv dotenv = Dotenv.configure()
                .directory("./")
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();

            Map<String, Object> envVars = dotenv.entries()
                .stream()
                .collect(Collectors.toMap(
                    entry -> entry.getKey(),
                    entry -> entry.getValue()
                ));

            environment.getPropertySources()
                .addFirst(new MapPropertySource("dotenvProperties", envVars));
                
        } catch (Exception e) {
            // If .env file is not found or has issues, continue with default properties
            System.out.println("Warning: Could not load .env file. Using default configuration.");
        }
    }
}

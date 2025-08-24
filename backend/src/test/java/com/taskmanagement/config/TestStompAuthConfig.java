package com.taskmanagement.config;

import com.taskmanagement.security.StompAuthChannelInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

@TestConfiguration
public class TestStompAuthConfig {

    @Bean
    @Primary
    public StompAuthChannelInterceptor stompAuthChannelInterceptor() {
        // Return a no-op interceptor for tests that need an open STOMP connection
        return new StompAuthChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                return message;
            }
        };
    }
}

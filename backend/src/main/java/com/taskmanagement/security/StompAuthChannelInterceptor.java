package com.taskmanagement.security;

import com.taskmanagement.util.JwtUtils;
import com.taskmanagement.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;

@Component
public class StompAuthChannelInterceptor implements ChannelInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor == null) return message;

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            List<String> auth = accessor.getNativeHeader("Authorization");
            String token = null;
            if (auth != null && !auth.isEmpty()) {
                String header = auth.get(0);
                if (header != null && header.startsWith("Bearer ")) token = header.substring(7);
            }
            // also support token as "token" header (some clients send query param or token header)
            if (token == null) {
                List<String> tokenHeader = accessor.getNativeHeader("token");
                if (tokenHeader != null && !tokenHeader.isEmpty()) token = tokenHeader.get(0);
            }

            // Require token for CONNECT and validate. If invalid or missing, reject connection.
            if (token == null || !jwtUtils.validateJwtToken(token)) {
                throw new org.springframework.messaging.MessagingException("Unauthorized: missing or invalid JWT token");
            }

            String username = jwtUtils.getUsernameFromJwtToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            accessor.setUser((Principal) authToken);
        }

        return message;
    }
}

package com.taskmanagement.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Utility class for JWT token operations.
 */
@Component
public class JwtUtils {
    
    private static final Logger logger = Logger.getLogger(JwtUtils.class.getName());

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    /**
     * Generate JWT token from authentication object.
     *
     * @param authentication the authentication object
     * @return JWT token string
     */
    public String generateJwtToken(Authentication authentication) {
        String username = authentication.getName();
        return generateJwtToken(username);
    }

    /**
     * Generate JWT token from username.
     *
     * @param username the username
     * @return JWT token string
     */
    public String generateJwtToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs * 1000L);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Get signing key for JWT.
     *
     * @return SecretKey for signing
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Get username from JWT token.
     *
     * @param token the JWT token
     * @return username
     */
    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     * Validate JWT token.
     *
     * @param authToken the JWT token to validate
     * @return true if valid, false otherwise
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.severe("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.severe("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.severe("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.severe("JWT claims string is empty: " + e.getMessage());
        } catch (Exception e) {
            logger.severe("JWT validation error: " + e.getMessage());
        }

        return false;
    }

    /**
     * Get expiration date from JWT token.
     *
     * @param token the JWT token
     * @return expiration date
     */
    public Date getExpirationDateFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }

    /**
     * Check if JWT token is expired.
     *
     * @param token the JWT token
     * @return true if expired, false otherwise
     */
    public boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromJwtToken(token);
        return expiration.before(new Date());
    }
}

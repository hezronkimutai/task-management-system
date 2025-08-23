import api from './api';
import config from '../config/config';

/**
 * User interface matching backend User entity
 */
export interface User {
  id: number;
  username: string;
  email: string;
  role: 'USER' | 'ADMIN';
  createdAt: string;
}

/**
 * Login request payload
 */
export interface LoginRequest {
  username: string;
  password: string;
}

/**
 * Registration request payload
 */
export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
  role?: 'USER' | 'ADMIN';
}

/**
 * Authentication response from backend
 */
export interface AuthResponse {
  token: string;
  type: string;
  user: User;
}

/**
 * Authentication service for user login, registration, and session management
 */
class AuthService {
  /**
   * Login user with username and password
   */
  async login(credentials: LoginRequest): Promise<AuthResponse> {
    try {
  const response = await api.post<AuthResponse>('/api/auth/login', credentials).then(r => r.data);
      
      // Store token in localStorage
      localStorage.setItem(config.auth.tokenKey, response.token);
      
      if (config.features.enableDebug) {
        console.log('Login successful:', response.user);
      }
      
      return response;
    } catch (error) {
      if (config.features.enableDebug) {
        console.error('Login error:', error);
      }
      throw error;
    }
  }

  /**
   * Register new user
   */
  async register(userData: RegisterRequest): Promise<AuthResponse> {
    try {
  const response = await api.post<AuthResponse>('/api/auth/register', userData).then(r => r.data);
      
      // Store token in localStorage
      localStorage.setItem(config.auth.tokenKey, response.token);
      
      if (config.features.enableDebug) {
        console.log('Registration successful:', response.user);
      }
      
      return response;
    } catch (error) {
      if (config.features.enableDebug) {
        console.error('Registration error:', error);
      }
      throw error;
    }
  }

  /**
   * Logout user by removing token
   */
  logout(): void {
    localStorage.removeItem(config.auth.tokenKey);
    
    if (config.features.enableDebug) {
      console.log('User logged out');
    }
  }

  /**
   * Get current user information
   */
  async getCurrentUser(): Promise<User> {
    try {
  const response = await api.get<User>('/api/auth/me').then(r => r.data);
  return response;
    } catch (error) {
      if (config.features.enableDebug) {
        console.error('Get current user error:', error);
      }
      throw error;
    }
  }

  /**
   * Check if user is authenticated
   */
  isAuthenticated(): boolean {
    const token = localStorage.getItem(config.auth.tokenKey);
    return !!token;
  }

  /**
   * Get stored JWT token
   */
  getToken(): string | null {
    return localStorage.getItem(config.auth.tokenKey);
  }

  /**
   * Validate token by making a request to protected endpoint
   */
  async validateToken(): Promise<boolean> {
    try {
      await this.getCurrentUser();
      return true;
    } catch (error) {
      this.logout(); // Remove invalid token
      return false;
    }
  }

  /**
   * Refresh token (if backend supports it)
   */
  async refreshToken(): Promise<string | null> {
    try {
  const response = await api.post<{ token: string }>('/api/auth/refresh').then(r => r.data);
  localStorage.setItem(config.auth.tokenKey, response.token);
  return response.token;
    } catch (error) {
      if (config.features.enableDebug) {
        console.error('Token refresh error:', error);
      }
      this.logout();
      return null;
    }
  }
}

// Create and export a singleton instance
const authService = new AuthService();
export default authService;

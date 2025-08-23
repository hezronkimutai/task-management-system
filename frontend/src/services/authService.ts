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
   * Normalize token string by removing any leading 'Bearer ' prefix.
   * Ensures we store and return the raw JWT only.
   */
  private normalizeToken(token: string | null): string | null {
    if (!token) return null;
    return token.startsWith('Bearer ') ? token.slice(7) : token;
  }
  /**
   * Login user with username and password
   */
  async login(credentials: LoginRequest): Promise<AuthResponse> {
    try {
      const response = await api.post<AuthResponse>('/api/auth/login', credentials).then(r => r.data);

     // Normalize and store token in localStorage (store raw JWT without 'Bearer ')
      const raw = this.normalizeToken(response.token);
      if (raw) localStorage.setItem(config.auth.tokenKey, raw);

      return response;

    } catch (error) {
      // error handling intentionally suppressed from console to reduce noise
      throw error;
    }
  }

  /**
   * Register new user
   */
  async register(userData: RegisterRequest): Promise<AuthResponse> {
    try {
      const response = await api.post<AuthResponse>('/api/auth/register', userData).then(r => r.data);

      // Normalize and store token in localStorage (store raw JWT without 'Bearer ')
      const raw = this.normalizeToken(response.token);
      if (raw) localStorage.setItem(config.auth.tokenKey, raw);

      // debug logging removed

      return response;
    } catch (error) {
      // error handling intentionally suppressed from console to reduce noise
      throw error;
    }
  }

  /**
   * Logout user by removing token
   */
  logout(): void {
  localStorage.removeItem(config.auth.tokenKey);

    // debug logging removed
  }

  /**
   * Get current user information
   */
  async getCurrentUser(): Promise<User> {
    try {
      const response = await api.get<User>('/api/auth/me').then(r => r.data);
      return response;
    } catch (error) {
      // debug logging removed
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
    const stored = localStorage.getItem(config.auth.tokenKey);
    const norm = this.normalizeToken(stored);
    // If token was stored with a prefix, normalize it back into storage for consistency
    if (stored && norm && stored !== norm) {
      localStorage.setItem(config.auth.tokenKey, norm);
    }
    return norm;
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
      const raw = this.normalizeToken(response.token);
      if (raw) localStorage.setItem(config.auth.tokenKey, raw);
      return raw;
    } catch (error) {
      // debug logging removed
      this.logout();
      return null;
    }
  }
}

// Create and export a singleton instance
const authService = new AuthService();
export default authService;

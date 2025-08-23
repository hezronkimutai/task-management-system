import React, { createContext, useContext, useEffect, useState } from 'react';
import authService, { User, LoginRequest, RegisterRequest } from '../services/authService';

interface AuthContextType {
  user: User | null;
  token: string | null;
  loading: boolean;
  isAuthenticated: boolean;
  login: (credentials: LoginRequest) => Promise<User>;
  register: (data: RegisterRequest) => Promise<User>;
  logout: () => void;
  refreshUser: () => Promise<void>;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState<string | null>(authService.getToken());
  const [loading, setLoading] = useState<boolean>(!!token);

  useEffect(() => {
    let mounted = true;

    async function init() {
      if (!token) {
        setLoading(false);
        return;
      }

      try {
        const current = await authService.getCurrentUser();
        if (!mounted) return;
        setUser(current);
      } catch (error) {
        // token invalid or request failed - ensure logout
        authService.logout();
        setUser(null);
        setToken(null);
      } finally {
        if (mounted) setLoading(false);
      }
    }

    init();

    return () => {
      mounted = false;
    };
  }, [token]);

  const login = async (credentials: LoginRequest) => {
    const resp = await authService.login(credentials);
    setToken(resp.token);
    setUser(resp.user);
    return resp.user;
  };

  const register = async (data: RegisterRequest) => {
    const resp = await authService.register(data);
    setToken(resp.token);
    setUser(resp.user);
    return resp.user;
  };

  const logout = () => {
    authService.logout();
    setUser(null);
    setToken(null);
  };

  const refreshUser = async () => {
    setLoading(true);
    try {
      const valid = await authService.validateToken();
      if (valid) {
        const current = await authService.getCurrentUser();
        setUser(current);
      } else {
        setUser(null);
      }
    } finally {
      setLoading(false);
    }
  };

  const value: AuthContextType = {
    user,
    token,
    loading,
    isAuthenticated: !!user && !!token,
    login,
    register,
    logout,
    refreshUser,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = (): AuthContextType => {
  const ctx = useContext(AuthContext);
  if (!ctx) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return ctx;
};

export default AuthContext;

import { jest } from '@jest/globals';

const mockAuthService = {
  login: jest.fn(),
  register: jest.fn(),
  logout: jest.fn(),
  getCurrentUser: jest.fn(),
  isAuthenticated: jest.fn(),
};

// Mock the authService module
jest.mock('../src/services/authService', () => ({
  __esModule: true,
  default: mockAuthService
}));

describe('authService', () => {
  it('should be properly mocked', () => {
    const authService = require('../src/services/authService').default;
    expect(authService).toBeDefined();
    expect(authService.login).toBeDefined();
    expect(authService.register).toBeDefined();
    expect(authService.logout).toBeDefined();
    expect(authService.getCurrentUser).toBeDefined();
    expect(authService.isAuthenticated).toBeDefined();
  });
});

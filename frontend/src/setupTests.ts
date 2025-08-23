// jest-dom adds custom jest matchers for asserting on DOM nodes.
// allows you to do things like:
// expect(element).toHaveTextContent(/react/i)
// learn more: https://github.com/testing-library/jest-dom
import '@testing-library/jest-dom';

// Set up environment variables for testing
process.env.REACT_APP_API_BASE_URL = 'http://localhost:8081';
process.env.REACT_APP_APP_NAME = 'Task Management System Test';
process.env.REACT_APP_APP_VERSION = '1.0.0';
process.env.REACT_APP_ENVIRONMENT = 'test';
process.env.REACT_APP_JWT_TOKEN_KEY = 'test_token';
process.env.REACT_APP_TOKEN_EXPIRY_HOURS = '24';
process.env.REACT_APP_ENABLE_DEBUG = 'false';
process.env.REACT_APP_ENABLE_ANALYTICS = 'false';
process.env.REACT_APP_MOCK_API = 'true';
process.env.REACT_APP_DEFAULT_THEME = 'light';
process.env.REACT_APP_ENABLE_DARK_MODE = 'true';
process.env.REACT_APP_LOG_LEVEL = 'error';

// Mock axios for tests
jest.mock('axios', () => ({
  __esModule: true,
  default: {
    create: jest.fn(() => ({
      interceptors: {
        request: { use: jest.fn() },
        response: { use: jest.fn() }
      },
      get: jest.fn(),
      post: jest.fn(),
      put: jest.fn(),
      delete: jest.fn()
    }))
  }
}));

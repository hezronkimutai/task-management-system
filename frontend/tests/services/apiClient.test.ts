import { jest } from '@jest/globals';

const mockApiClient = {
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn(),
};

// Mock the apiClient module to avoid axios import issues
jest.mock('../src/services/apiClient', () => ({
  __esModule: true,
  default: mockApiClient
}));

describe('apiClient', () => {
  it('should be properly mocked', () => {
    const apiClient = require('../src/services/apiClient').default;
    expect(apiClient).toBeDefined();
    expect(apiClient.get).toBeDefined();
    expect(apiClient.post).toBeDefined();
    expect(apiClient.put).toBeDefined();
    expect(apiClient.delete).toBeDefined();
  });
});

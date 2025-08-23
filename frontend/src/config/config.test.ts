import config from './config';

describe('config', () => {
  it('should have default configuration values', () => {
    expect(config.api.baseUrl).toBe('http://localhost:8081');
    expect(config.app.name).toBe('Task Management System Test');
    expect(config.app.environment).toBe('test');
    expect(config.auth.tokenKey).toBe('test_token');
    expect(config.features.mockApi).toBe(true);
  });

  it('should have valid API timeout', () => {
    expect(config.api.timeout).toBeGreaterThan(0);
    expect(typeof config.api.timeout).toBe('number');
  });

  it('should have valid token expiry hours', () => {
    expect(config.auth.tokenExpiryHours).toBeGreaterThan(0);
    expect(typeof config.auth.tokenExpiryHours).toBe('number');
  });

  it('should have theme configuration', () => {
    expect(config.theme.defaultTheme).toBeDefined();
    expect(typeof config.theme.enableDarkMode).toBe('boolean');
  });

  it('should have logging configuration', () => {
    expect(config.logging.level).toBeDefined();
    expect(typeof config.logging.level).toBe('string');
  });
});

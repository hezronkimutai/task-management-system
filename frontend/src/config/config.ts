/**
 * Application configuration interface
 */
export interface AppConfig {
  api: {
    baseUrl: string;
    timeout: number;
  };
  auth: {
    tokenKey: string;
    tokenExpiryHours: number;
  };
  app: {
    name: string;
    version: string;
    environment: string;
  };
  features: {
    enableDebug: boolean;
    enableAnalytics: boolean;
    mockApi: boolean;
  };
  theme: {
    defaultTheme: string;
    enableDarkMode: boolean;
  };
  logging: {
    level: string;
  };
}

/**
 * Get environment variable with type safety and default values
 */
function getEnvVar(key: string, defaultValue: string): string {
  return process.env[key] || defaultValue;
}

/**
 * Get boolean environment variable
 */
function getEnvBool(key: string, defaultValue: boolean): boolean {
  const value = process.env[key];
  if (value === undefined) return defaultValue;
  return value.toLowerCase() === 'true';
}

/**
 * Get number environment variable
 */
function getEnvNumber(key: string, defaultValue: number): number {
  const value = process.env[key];
  if (value === undefined) return defaultValue;
  const parsed = parseInt(value, 10);
  return isNaN(parsed) ? defaultValue : parsed;
}

/**
 * Application configuration loaded from environment variables
 */
export const config: AppConfig = {
  api: {
    baseUrl: getEnvVar('REACT_APP_API_BASE_URL', 'http://localhost:8081'),
    timeout: getEnvNumber('REACT_APP_API_TIMEOUT', 10000),
  },
  auth: {
    tokenKey: getEnvVar('REACT_APP_JWT_TOKEN_KEY', 'taskmanagement_token'),
    tokenExpiryHours: getEnvNumber('REACT_APP_TOKEN_EXPIRY_HOURS', 24),
  },
  app: {
    name: getEnvVar('REACT_APP_APP_NAME', 'Task Management System'),
    version: getEnvVar('REACT_APP_APP_VERSION', '1.0.0'),
    environment: getEnvVar('REACT_APP_ENVIRONMENT', 'development'),
  },
  features: {
    enableDebug: getEnvBool('REACT_APP_ENABLE_DEBUG', false),
    enableAnalytics: getEnvBool('REACT_APP_ENABLE_ANALYTICS', false),
    mockApi: getEnvBool('REACT_APP_MOCK_API', false),
  },
  theme: {
    defaultTheme: getEnvVar('REACT_APP_DEFAULT_THEME', 'light'),
    enableDarkMode: getEnvBool('REACT_APP_ENABLE_DARK_MODE', true),
  },
  logging: {
    level: getEnvVar('REACT_APP_LOG_LEVEL', 'info'),
  },
};

/**
 * Validate configuration on load
 */
function validateConfig(): void {
  const requiredEnvVars = [
    'REACT_APP_API_BASE_URL',
    'REACT_APP_APP_NAME',
  ];

  const missing = requiredEnvVars.filter(key => !process.env[key]);
  
  if (missing.length > 0) {
    console.warn('Missing environment variables:', missing);
  }

  // Validate API URL format
  try {
    new URL(config.api.baseUrl);
  } catch (error) {
    console.error('Invalid API base URL:', config.api.baseUrl);
  }

  if (config.features.enableDebug) {
    console.log('Application configuration:', config);
  }
}

// Validate configuration on module load
validateConfig();

export default config;

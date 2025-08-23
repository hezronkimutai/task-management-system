import React, { useEffect, useState } from 'react';
import config from '../config/config';
import authService from '../services/authService';

/**
 * Component to test environment variables and API connectivity
 */
const EnvironmentTest: React.FC = () => {
  const [envStatus, setEnvStatus] = useState<'loading' | 'success' | 'error'>('loading');
  const [apiStatus, setApiStatus] = useState<'loading' | 'success' | 'error'>('loading');
  const [errorMessage, setErrorMessage] = useState<string>('');

  useEffect(() => {
    testEnvironmentVariables();
    testApiConnectivity();
  }, []);

  const testEnvironmentVariables = () => {
    try {
      // Test if all required config values are loaded
      const requiredConfigs = [
        config.api.baseUrl,
        config.auth.tokenKey,
        config.app.name,
        config.app.version
      ];

      const missingConfigs = requiredConfigs.filter(value => !value);
      
      if (missingConfigs.length > 0) {
        throw new Error('Missing required environment variables');
      }

      setEnvStatus('success');
    } catch (error) {
      setEnvStatus('error');
      setErrorMessage(error instanceof Error ? error.message : 'Environment test failed');
    }
  };

  const testApiConnectivity = async () => {
    try {
      // Test API connectivity by checking if the backend is responding
      // Using the Swagger docs endpoint as it's always available
      const response = await fetch(config.api.baseUrl + '/v3/api-docs', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (response.ok) {
        setApiStatus('success');
      } else {
        throw new Error(`API returned status: ${response.status}`);
      }
    } catch (error) {
      setApiStatus('error');
      console.warn('API connectivity test failed (this is expected if backend is not running):', error);
    }
  };

  const getStatusIcon = (status: 'loading' | 'success' | 'error') => {
    switch (status) {
      case 'loading':
        return '⏳';
      case 'success':
        return '✅';
      case 'error':
        return '❌';
    }
  };

  const getStatusColor = (status: 'loading' | 'success' | 'error') => {
    switch (status) {
      case 'loading':
        return '#ffa500';
      case 'success':
        return '#28a745';
      case 'error':
        return '#dc3545';
    }
  };

  return (
    <div style={{ padding: '20px', fontFamily: 'Arial, sans-serif' }}>
      <h2>Environment Variables Test</h2>
      
      <div style={{ marginBottom: '20px' }}>
        <h3>Configuration Status</h3>
        <div style={{ display: 'flex', alignItems: 'center', marginBottom: '10px' }}>
          <span style={{ marginRight: '10px', fontSize: '20px' }}>
            {getStatusIcon(envStatus)}
          </span>
          <span style={{ color: getStatusColor(envStatus), fontWeight: 'bold' }}>
            Environment Variables: {envStatus.toUpperCase()}
          </span>
        </div>
        
        <div style={{ display: 'flex', alignItems: 'center', marginBottom: '10px' }}>
          <span style={{ marginRight: '10px', fontSize: '20px' }}>
            {getStatusIcon(apiStatus)}
          </span>
          <span style={{ color: getStatusColor(apiStatus), fontWeight: 'bold' }}>
            API Connectivity: {apiStatus.toUpperCase()}
          </span>
        </div>

        {errorMessage && (
          <div style={{ color: '#dc3545', marginTop: '10px', padding: '10px', backgroundColor: '#f8d7da', borderRadius: '4px' }}>
            Error: {errorMessage}
          </div>
        )}
      </div>

      <div style={{ marginBottom: '20px' }}>
        <h3>Loaded Configuration</h3>
        <div style={{ backgroundColor: '#f8f9fa', padding: '15px', borderRadius: '4px', fontFamily: 'monospace' }}>
          <div><strong>Environment:</strong> {process.env.NODE_ENV}</div>
          <div><strong>App Name:</strong> {config.app.name}</div>
          <div><strong>App Version:</strong> {config.app.version}</div>
          <div><strong>API Base URL:</strong> {config.api.baseUrl}</div>
          <div><strong>API Timeout:</strong> {config.api.timeout}ms</div>
          <div><strong>Token Key:</strong> {config.auth.tokenKey}</div>
          <div><strong>Debug Mode:</strong> {config.features.enableDebug ? 'Enabled' : 'Disabled'}</div>
          <div><strong>Default Theme:</strong> {config.theme.defaultTheme}</div>
          <div><strong>Dark Mode:</strong> {config.theme.enableDarkMode ? 'Enabled' : 'Disabled'}</div>
        </div>
      </div>

      <div style={{ marginBottom: '20px' }}>
        <h3>Authentication Test</h3>
        <div style={{ display: 'flex', gap: '10px', marginBottom: '10px' }}>
          <button 
            onClick={() => {
              const token = 'test-token-123';
              localStorage.setItem(config.auth.tokenKey, token);
              alert('Test token stored');
            }}
            style={{ padding: '8px 16px', backgroundColor: '#007bff', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' }}
          >
            Store Test Token
          </button>
          
          <button 
            onClick={() => {
              const isAuth = authService.isAuthenticated();
              alert(`Authenticated: ${isAuth}`);
            }}
            style={{ padding: '8px 16px', backgroundColor: '#28a745', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' }}
          >
            Check Auth Status
          </button>
          
          <button 
            onClick={() => {
              authService.logout();
              alert('Logged out (token removed)');
            }}
            style={{ padding: '8px 16px', backgroundColor: '#dc3545', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' }}
          >
            Logout
          </button>
        </div>
      </div>

      <div style={{ marginTop: '20px', padding: '15px', backgroundColor: '#d1ecf1', borderRadius: '4px' }}>
        <h4>Instructions:</h4>
        <ol>
          <li>Check that Environment Variables status is ✅ (green)</li>
          <li>API Connectivity may be ❌ (red) if backend is not running - this is normal</li>
          <li>Verify that all configuration values are loaded correctly</li>
          <li>Test the authentication buttons to verify localStorage integration</li>
          <li>To test full functionality, start the backend server first</li>
        </ol>
      </div>
    </div>
  );
};

export default EnvironmentTest;

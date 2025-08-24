import React from 'react';
import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import App from '../src/App';
import { CssBaseline } from '@mui/material';
import { ThemeProviderCustom } from '../src/contexts/ThemeContext';
import { AuthProvider } from '../src/contexts/AuthContext';

test('renders task management system heading', () => {
  render(
    <ThemeProviderCustom>
      <CssBaseline />
      <MemoryRouter>
        <AuthProvider>
          <App />
        </AuthProvider>
      </MemoryRouter>
    </ThemeProviderCustom>
  );
  const headingElement = screen.getByRole('heading', { level: 1, name: /Task Management System/i });
  expect(headingElement).toBeInTheDocument();
});

test('renders environment setup text', () => {
  render(
    <ThemeProviderCustom>
      <CssBaseline />
      <MemoryRouter>
        <AuthProvider>
          <App />
        </AuthProvider>
      </MemoryRouter>
    </ThemeProviderCustom>
  );
  const textElement = screen.getByText(/Welcome to the Task Management System frontend\./i);
  expect(textElement).toBeInTheDocument();
});

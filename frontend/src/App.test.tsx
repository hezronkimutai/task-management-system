import React from 'react';
import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import App from './App';
import { ThemeProvider, CssBaseline } from '@mui/material';
import theme from './theme';
import { AuthProvider } from './contexts/AuthContext';

test('renders task management system heading', () => {
  render(
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <MemoryRouter>
        <AuthProvider>
          <App />
        </AuthProvider>
      </MemoryRouter>
    </ThemeProvider>
  );
  const headingElement = screen.getByRole('heading', { level: 1, name: /Task Management System/i });
  expect(headingElement).toBeInTheDocument();
});

test('renders environment setup text', () => {
  render(
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <MemoryRouter>
        <AuthProvider>
          <App />
        </AuthProvider>
      </MemoryRouter>
    </ThemeProvider>
  );
  const textElement = screen.getByText(/Welcome to the Task Management System frontend\./i);
  expect(textElement).toBeInTheDocument();
});

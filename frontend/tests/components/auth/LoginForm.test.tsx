import React from 'react';
import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import LoginForm from '../../../src/components/auth/LoginForm';
import { AuthProvider } from '../../../src/contexts/AuthContext';

test('renders login form', () => {
  render(
    <MemoryRouter>
      <AuthProvider>
        <LoginForm />
      </AuthProvider>
    </MemoryRouter>
  );
  expect(screen.getByLabelText(/Username/i)).toBeInTheDocument();
});

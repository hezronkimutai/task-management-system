import React from 'react';
import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import RegisterForm from '../../../src/components/auth/RegisterForm';
import { AuthProvider } from '../../../src/contexts/AuthContext';

test('renders register form', () => {
  render(
    <MemoryRouter>
      <AuthProvider>
        <RegisterForm />
      </AuthProvider>
    </MemoryRouter>
  );
  expect(screen.getByLabelText(/Username/i)).toBeInTheDocument();
});

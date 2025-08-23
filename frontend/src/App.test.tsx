import React from 'react';
import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import App from './App';

test('renders task management system heading', () => {
  render(
    <MemoryRouter>
      <App />
    </MemoryRouter>
  );
  const headingElement = screen.getByRole('heading', { level: 1, name: /Task Management System/i });
  expect(headingElement).toBeInTheDocument();
});

test('renders environment setup text', () => {
  render(
    <MemoryRouter>
      <App />
    </MemoryRouter>
  );
  const textElement = screen.getByText(/Welcome to the Task Management System frontend\./i);
  expect(textElement).toBeInTheDocument();
});

import React from 'react';
import { render, screen } from '@testing-library/react';
import App from './App';

test('renders task management system heading', () => {
  render(<App />);
  const headingElement = screen.getByRole('heading', { level: 1, name: /Task Management System/i });
  expect(headingElement).toBeInTheDocument();
});

test('renders environment setup text', () => {
  render(<App />);
  const textElement = screen.getByText(/Environment Variables & JWT Authentication Setup/i);
  expect(textElement).toBeInTheDocument();
});

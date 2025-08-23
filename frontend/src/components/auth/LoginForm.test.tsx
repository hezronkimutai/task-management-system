import React from 'react';
import { render, screen } from '@testing-library/react';
import LoginForm from './LoginForm';
import { BrowserRouter } from 'react-router-dom';

test('renders login form', () => {
  render(<BrowserRouter><LoginForm /></BrowserRouter>);
  expect(screen.getByLabelText(/username/i)).toBeInTheDocument();
  expect(screen.getByLabelText(/password/i)).toBeInTheDocument();
});

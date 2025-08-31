// File: frontend/src/pages/LoginPage.test.jsx
import React from 'react';
import { render, screen } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import LoginPage from './LoginPage';
import { AuthProvider } from '../context/AuthContext';

// We wrap the component in the necessary providers (Router and our AuthContext)
const renderWithProviders = (component) => {
  return render(
    <BrowserRouter>
      <AuthProvider>{component}</AuthProvider>
    </BrowserRouter>
  );
};

test('renders login page with username and password fields', () => {
  // Arrange: Render the component
  renderWithProviders(<LoginPage />);

  // Act & Assert: Check if the important elements are on the screen
  const heading = screen.getByRole('heading', { name: /login/i });
  expect(heading).toBeInTheDocument();

  const usernameInput = screen.getByLabelText(/username/i);
  expect(usernameInput).toBeInTheDocument();

  const passwordInput = screen.getByLabelText(/password/i);
  expect(passwordInput).toBeInTheDocument();

  const loginButton = screen.getByRole('button', { name: /login/i });
  expect(loginButton).toBeInTheDocument();
});
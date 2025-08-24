import React from 'react';
import { render, screen, waitFor, act } from '@testing-library/react';
import EnvironmentTest from '../src/components/EnvironmentTest';

// Mock fetch
global.fetch = jest.fn();

describe('EnvironmentTest', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('renders without crashing', async () => {
    (fetch as jest.Mock).mockResolvedValueOnce({
      ok: true,
      json: () => Promise.resolve({}),
    });
    
    await act(async () => {
      render(<EnvironmentTest />);
    });
    
    expect(screen.getByText(/Environment Variables Test/i)).toBeInTheDocument();
  });

  it('shows loading state initially', async () => {
    (fetch as jest.Mock).mockImplementationOnce(() => new Promise(() => {})); // Never resolves
    
    await act(async () => {
      render(<EnvironmentTest />);
    });
    
    expect(screen.getByText(/LOADING/i)).toBeInTheDocument();
  });

  it('displays configuration status', async () => {
    (fetch as jest.Mock).mockResolvedValueOnce({
      ok: true,
      json: () => Promise.resolve({}),
    });

    await act(async () => {
      render(<EnvironmentTest />);
    });
    
    await waitFor(() => {
      expect(screen.getByText(/Configuration Status/i)).toBeInTheDocument();
    });
    
    expect(screen.getByText(/API Base URL/i)).toBeInTheDocument();
    expect(screen.getByText(/App Name/i)).toBeInTheDocument();
  });

  it('handles API connectivity test success', async () => {
    (fetch as jest.Mock).mockResolvedValueOnce({
      ok: true,
      json: () => Promise.resolve({}),
    });

    await act(async () => {
      render(<EnvironmentTest />);
    });
    
    await waitFor(() => {
      expect(screen.getByText(/Environment Variables:\s*SUCCESS/i)).toBeInTheDocument();
    });
  });

  it('handles API connectivity test failure', async () => {
    (fetch as jest.Mock).mockRejectedValueOnce(new Error('Network error'));

    await act(async () => {
      render(<EnvironmentTest />);
    });
    
    await waitFor(() => {
      expect(screen.getByText(/API Connectivity:\s*ERROR/i)).toBeInTheDocument();
    });
  });
});

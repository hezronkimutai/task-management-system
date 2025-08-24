import { createTheme } from '@mui/material/styles';

const primary = {
  main: '#7b61ff',
  contrastText: '#fff',
};

const secondary = {
  main: '#4bd3ff',
  contrastText: '#021124',
};

const baseShape = {
  borderRadius: 12,
};

const baseTypography = {
  h1: {
    fontSize: '1.8rem',
    fontWeight: 700,
  },
  body1: {
    // Keep palette-driven color; MUI will handle contrast for components.
  },
};

export const createAppTheme = (mode: 'light' | 'dark') =>
  createTheme({
    palette: {
      mode,
      primary,
      secondary,
      background: mode === 'dark'
        ? { default: '#071428', paper: 'rgba(255,255,255,0.03)' }
        : { default: '#f6f7fb', paper: '#ffffff' },
      text: mode === 'dark'
        ? { primary: 'rgba(255,255,255,0.92)', secondary: 'rgba(255,255,255,0.72)' }
        : { primary: '#061421', secondary: '#32414a' },
    },
    shape: baseShape,
    typography: baseTypography,
  });

// default export kept for existing code/tests that import default
const defaultTheme = createAppTheme('dark');
export default defaultTheme;

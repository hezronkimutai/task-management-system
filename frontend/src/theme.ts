import { createTheme } from '@mui/material/styles';

const theme = createTheme({
  palette: {
    primary: {
      main: '#1976d2',
    },
    secondary: {
      main: '#9c27b0',
    },
    mode: 'light',
  },
  typography: {
    h1: {
      fontSize: '1.8rem',
    },
  },
});

export default theme;

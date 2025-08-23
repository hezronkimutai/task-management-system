import { createTheme } from '@mui/material/styles';

const theme = createTheme({
  palette: {
    primary: {
      main: '#7b61ff',
      contrastText: '#fff',
    },
    secondary: {
      main: '#4bd3ff',
      contrastText: '#021124',
    },
    background: {
      default: '#071428',
      paper: 'rgba(255,255,255,0.03)'
    },
    mode: 'dark',
  },
  shape: {
    borderRadius: 12,
  },
  typography: {
    h1: {
      fontSize: '1.8rem',
      fontWeight: 700,
    },
    body1: {
      color: 'rgba(255,255,255,0.9)'
    }
  },
});

export default theme;

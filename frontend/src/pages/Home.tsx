import React from 'react';
import { Container, Typography, Box } from '@mui/material';
import config from '../config/config';

const Home: React.FC = () => {
  return (
    <Container>
      <Box className="hero" sx={{ my: 4 }}>
        <Box>
          <Typography variant="h1">{config.app.name}</Typography>
          <Typography variant="body1">Welcome to the Task Management System frontend.</Typography>
        </Box>
        <Box>
          <img src="/logo192.png" alt="logo" style={{ width: 84, opacity: 0.95 }} />
        </Box>
      </Box>
    </Container>
  );
};

export default Home;

import React from 'react';
import { Container, Typography, Box } from '@mui/material';
import config from '../config/config';

const Home: React.FC = () => {
  return (
    <Container>
      <Box sx={{ my: 4 }}>
        <Typography variant="h1">{config.app.name}</Typography>
        <Typography variant="body1">Welcome to the Task Management System frontend.</Typography>
      </Box>
    </Container>
  );
};

export default Home;

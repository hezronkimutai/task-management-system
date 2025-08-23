import React from 'react';
import { Container, Typography, Box } from '@mui/material';

const NotFound: React.FC = () => (
  <Container>
    <Box sx={{ my: 4 }}>
      <Typography variant="h4">404 - Not Found</Typography>
      <Typography>Sorry, the page you requested does not exist.</Typography>
    </Box>
  </Container>
);

export default NotFound;

import React from 'react';
import './App.css';
import { Routes, Route, Link, useNavigate } from 'react-router-dom';
import { AppBar, Toolbar, Typography, Container, Box, Button } from '@mui/material';
import Home from './pages/Home';
import NotFound from './pages/NotFound';
import LoginForm from './components/auth/LoginForm';
import RegisterForm from './components/auth/RegisterForm';
import TaskDashboard from './components/tasks/TaskDashboard';
import { useAuth } from './contexts/AuthContext';

function App() {
  const { isAuthenticated, user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  return (
    <div className="App">
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" sx={{ flexGrow: 1 }}>
            Task Management
          </Typography>
          <Button color="inherit" component={Link} to="/">Home</Button>
          <Button color="inherit" component={Link} to="/tasks">Tasks</Button>

          {!isAuthenticated ? (
            <>
              <Button color="inherit" component={Link} to="/login">Login</Button>
              <Button color="inherit" component={Link} to="/register">Sign up</Button>
            </>
          ) : (
            <>
              <Typography variant="subtitle1" sx={{ mx: 2 }}>{user?.username}</Typography>
              <Button color="inherit" onClick={handleLogout}>Logout</Button>
            </>
          )}
        </Toolbar>
      </AppBar>

      <Container>
        <Box sx={{ mt: 4 }}>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<LoginForm />} />
            <Route path="/register" element={<RegisterForm />} />
            <Route path="/tasks" element={<TaskDashboard />} />
            <Route path="*" element={<NotFound />} />
          </Routes>
        </Box>
      </Container>
    </div>
  );
}

export default App;

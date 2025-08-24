import React from 'react';
import './App.css';
import { Routes, Route, Link, useNavigate } from 'react-router-dom';
import { AppBar, Toolbar, Typography, Container, Box, Button, IconButton, Badge, Menu, MenuItem } from '@mui/material';
import NotificationsIcon from '@mui/icons-material/Notifications';
import { useThemeMode } from './contexts/ThemeContext';
import Home from './pages/Home';
import NotFound from './pages/NotFound';
import LoginForm from './components/auth/LoginForm';
import RegisterForm from './components/auth/RegisterForm';
import TaskDashboard from './components/tasks/TaskDashboard';
import TaskOverview from './pages/TaskOverview';
import { useAuth } from './contexts/AuthContext';

function App() {
  const { isAuthenticated, user, logout } = useAuth();
  const navigate = useNavigate();
  const { mode, toggleMode } = useThemeMode();
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
  const [dueTasks, setDueTasks] = React.useState<Array<{ id: number; title: string; dueDate?: string | null }>>([]);

  const open = Boolean(anchorEl);

  const refreshDue = async () => {
    try {
      const notificationService = await import('./services/notificationService');
      const tasks = await notificationService.default.getDueSoon(30);
      setDueTasks(tasks);
      // show browser notifications for new tasks
      if (tasks.length && 'Notification' in window && Notification.permission === 'granted') {
        tasks.forEach((t: any) => {
          try {
            new Notification('Task due soon', { body: `${t.title} — due ${t.dueDate ? new Date(t.dueDate).toLocaleString() : ''}` });
          } catch (e) {}
        });
      }
    } catch (e) {
      // ignore
    }
  };

  React.useEffect(() => {
    // request permission once
    if ('Notification' in window && Notification.permission === 'default') {
      try { Notification.requestPermission(); } catch (e) {}
    }
    // initial load
    refreshDue();
    const id = setInterval(refreshDue, 60 * 1000); // poll every minute
    return () => clearInterval(id);
  }, []);

  const handleOpenNotifications = (ev: React.MouseEvent<HTMLElement>) => setAnchorEl(ev.currentTarget);
  const handleCloseNotifications = () => setAnchorEl(null);

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

          <IconButton color="inherit" onClick={toggleMode} sx={{ ml: 1 }} aria-label="toggle theme">
            {mode === 'dark' ? '🌞' : '🌙'}
          </IconButton>

          {isAuthenticated && (
            <>
              <IconButton color="inherit" onClick={handleOpenNotifications} aria-label="notifications">
                <Badge badgeContent={dueTasks.length} color="error">
                  <NotificationsIcon />
                </Badge>
              </IconButton>
              <Menu anchorEl={anchorEl} open={open} onClose={handleCloseNotifications}>
                {dueTasks.length === 0 && <MenuItem disabled>No upcoming tasks</MenuItem>}
                {dueTasks.map((t) => (
                  <MenuItem key={t.id} onClick={handleCloseNotifications}>{t.title} {t.dueDate ? ` — ${new Date(t.dueDate).toLocaleString()}` : ''}</MenuItem>
                ))}
              </Menu>
            </>
          )}

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
            <Route path="/tasks/:id" element={<TaskOverview />} />
            <Route path="*" element={<NotFound />} />
          </Routes>
        </Box>
      </Container>
    </div>
  );
}

export default App;

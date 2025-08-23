import React from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import { TextField, Button, Box, CircularProgress, Alert } from '@mui/material';
import { LoginRequest } from '../../services/authService';
import { useAuth } from '../../contexts/AuthContext';
import { useNavigate } from 'react-router-dom';

type FormValues = LoginRequest;

const LoginForm: React.FC = () => {
  const { register, handleSubmit, formState: { errors } } = useForm<FormValues>();
  const [loading, setLoading] = React.useState(false);
  const [error, setError] = React.useState<string | null>(null);
  const navigate = useNavigate();

  const { login } = useAuth();

  const onSubmit: SubmitHandler<FormValues> = async (data) => {
    setError(null);
    setLoading(true);
    try {
  await login(data);
  navigate('/tasks');
    } catch (err: any) {
      setError(err?.response?.data?.message || err?.message || 'Login failed');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box component="form" onSubmit={handleSubmit(onSubmit)} sx={{ width: 360, mx: 'auto', mt: 4 }}>
      {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}
      <TextField
        label="Username"
        fullWidth
        margin="normal"
        {...register('username', { required: 'Username is required' })}
        error={!!errors.username}
        helperText={errors.username?.message}
      />
      <TextField
        label="Password"
        type="password"
        fullWidth
        margin="normal"
        {...register('password', { required: 'Password is required', minLength: { value: 6, message: 'Minimum 6 characters' } })}
        error={!!errors.password}
        helperText={errors.password?.message}
      />

      <Box sx={{ position: 'relative', mt: 2 }}>
        <Button type="submit" variant="contained" color="primary" fullWidth disabled={loading}>
          Sign in
        </Button>
        {loading && <CircularProgress size={24} sx={{ position: 'absolute', top: '50%', left: '50%', mt: '-12px', ml: '-12px' }} />}
      </Box>
    </Box>
  );
};

export default LoginForm;

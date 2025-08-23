import React from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import { TextField, Button, Box, CircularProgress, Alert } from '@mui/material';
import authService, { RegisterRequest } from '../../services/authService';
import { useNavigate } from 'react-router-dom';

type FormValues = RegisterRequest;

const RegisterForm: React.FC = () => {
  const { register, handleSubmit, watch, formState: { errors } } = useForm<FormValues>();
  const [loading, setLoading] = React.useState(false);
  const [error, setError] = React.useState<string | null>(null);
  const navigate = useNavigate();

  const password = React.useRef({});
  password.current = watch('password', '');

  const onSubmit: SubmitHandler<FormValues> = async (data) => {
    setError(null);
    setLoading(true);
    try {
      await authService.register(data);
      navigate('/');
    } catch (err: any) {
      setError(err?.response?.data?.message || err?.message || 'Registration failed');
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
        label="Email"
        type="email"
        fullWidth
        margin="normal"
        {...register('email', { required: 'Email is required', pattern: { value: /^[^@\s]+@[^@\s]+\.[^@\s]+$/, message: 'Invalid email' } })}
        error={!!errors.email}
        helperText={errors.email?.message}
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
          Register
        </Button>
        {loading && <CircularProgress size={24} sx={{ position: 'absolute', top: '50%', left: '50%', mt: '-12px', ml: '-12px' }} />}
      </Box>
    </Box>
  );
};

export default RegisterForm;

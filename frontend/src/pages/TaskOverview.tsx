import React from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Box, Paper, Typography, CircularProgress, Button, Grid, Chip, Avatar, Stack, IconButton, Divider, FormControl, InputLabel, Select, MenuItem, Snackbar, Alert, useMediaQuery, useTheme } from '@mui/material';
import apiClient from '../services/apiClient';
import CommentList from '../components/tasks/CommentList';
import CommentForm from '../components/tasks/CommentForm';
import ActivityList from '../components/tasks/ActivityList';
import TaskForm from '../components/tasks/TaskForm';
import activityService from '../services/activityService';

const TaskOverview: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [task, setTask] = React.useState<any | null>(null);
  const [loading, setLoading] = React.useState(true);
  const [openEdit, setOpenEdit] = React.useState(false);
  const [error, setError] = React.useState<string | null>(null);
  const [users, setUsers] = React.useState<Array<any>>([]);
  const theme = useTheme();
  const isSmall = useMediaQuery(theme.breakpoints.down('md'));

  React.useEffect(() => {
    if (!id) return;
    setLoading(true);
    apiClient.get<any>(`/api/tasks/${id}`)
      .then((res) => setTask(res))
      .catch(() => setTask(null))
      .finally(() => setLoading(false));
  }, [id]);

  React.useEffect(() => {
    // fetch users for assignee select in edit form
    let mounted = true;
    apiClient.get<any[]>('/api/users')
      .then((res) => { if (mounted) setUsers(res); })
      .catch(() => {});
    return () => { mounted = false; };
  }, []);

  const handleCommentCreated = () => {
    // notify comment lists to refresh
    window.dispatchEvent(new Event('comments:refresh'));
    // notify dashboard to refresh tasks if needed
    window.dispatchEvent(new Event('tasks:refresh'));
  };

  const handleStatusChange = async (status: string) => {
    if (!task) return;
    try {
      const payload = { ...task, status };
  const updated = await apiClient.put<any>(`/api/tasks/${task.id}`, payload);
      setTask(updated);
      try { await activityService.create({ taskId: Number(task.id), type: 'STATUS_CHANGED', actorId: undefined, actorName: undefined, detail: `Status: ${task.status} → ${status}`, createdAt: new Date().toISOString() }); } catch (e) {}
      window.dispatchEvent(new Event('tasks:refresh'));
    } catch (err: any) {
      setError(err?.response?.data?.message || err?.message || 'Failed to update status');
    }
  };

  const handleDelete = async () => {
    if (!task) return;
    try {
      await apiClient.delete(`/api/tasks/${task.id}`);
      window.dispatchEvent(new Event('tasks:refresh'));
      navigate('/tasks');
    } catch (err: any) {
      setError(err?.response?.data?.message || err?.message || 'Failed to delete task');
    }
  };

  const handleSave = async (payload: any) => {
    try {
      if (payload.id) {
  const updated = await apiClient.put<any>(`/api/tasks/${payload.id}`, payload);
        setTask(updated);
        try { await activityService.create({ taskId: Number(payload.id), type: 'UPDATED', actorId: undefined, actorName: undefined, detail: 'Task updated', createdAt: new Date().toISOString() }); } catch (e) {}
      } else {
  const created = await apiClient.post<any>('/api/tasks', payload);
  setTask(created);
        try { await activityService.create({ taskId: Number(created.id), type: 'CREATED', actorId: undefined, actorName: undefined, detail: 'Task created', createdAt: new Date().toISOString() }); } catch (e) {}
      }
      setOpenEdit(false);
      window.dispatchEvent(new Event('tasks:refresh'));
    } catch (err: any) {
      setError(err?.response?.data?.message || err?.message || 'Failed to save task');
    }
  };

  if (loading) return <Box display="flex" justifyContent="center"><CircularProgress /></Box>;

  if (!task) return (
    <Box>
      <Typography variant="h6">Task not found</Typography>
      <Button onClick={() => navigate('/tasks')}>Back to tasks</Button>
    </Box>
  );

  const assigneeName = task.assignee?.username || task.assigneeUsername || 'Unassigned';
  const priorityColor: Record<string, string> = {
    LOW: '#6EE7B7',
    MEDIUM: '#FFD166',
    HIGH: '#FF6B6B',
  };

  return (
    <Box>
      <Paper className="task-overview-hero" sx={{ p: { xs: 2, md: 3 }, mb: 2 }}>
        <Box sx={{ display: 'flex', flexDirection: isSmall ? 'column' : 'row', alignItems: isSmall ? 'flex-start' : 'center', justifyContent: 'space-between', gap: isSmall ? 1 : 2 }}>
          <Box sx={{ display: 'flex', alignItems: 'center', gap: 2, width: '100%' }}>
            <IconButton onClick={() => navigate('/tasks')} size="large" sx={{ color: 'var(--muted)' }}>
              <span style={{ fontSize: 20, fontWeight: 700 }}>←</span>
            </IconButton>
            <Box>
              <Typography variant="h4" className="overview-title">{task.title}</Typography>
              <Typography variant="body2" className="overview-desc" sx={{ mt: 0.5 }}>{task.description}</Typography>
            </Box>
          </Box>

          <Stack direction={isSmall ? 'column' : 'row'} spacing={1} alignItems={isSmall ? 'flex-start' : 'center'} sx={{ mt: isSmall ? 1 : 0 }}>
            <Chip label={task.priority || 'MEDIUM'} className="overview-meta-chip" sx={{ bgcolor: priorityColor[task.priority] || priorityColor.MEDIUM, color: '#021124', fontWeight: 700 }} />
            <Chip avatar={<Avatar>{assigneeName.charAt(0).toUpperCase()}</Avatar>} label={assigneeName} variant="outlined" sx={{ color: 'var(--muted)', borderColor: 'rgba(0,0,0,0.06)' }} />
                    <Typography variant="caption" className="small-muted">{task.createdAt ? new Date(task.createdAt).toLocaleString() : ''}</Typography>
                    {task.dueDate && <Typography variant="caption" className="small-muted">Due: {new Date(task.dueDate).toLocaleString()}</Typography>}

            <FormControl size="small" sx={{ minWidth: 140 }}>
              <InputLabel id="overview-status-label">Status</InputLabel>
              <Select
                labelId="overview-status-label"
                value={task.status}
                label="Status"
                onChange={(e) => handleStatusChange(String(e.target.value))}
              >
                <MenuItem value="TODO">To Do</MenuItem>
                <MenuItem value="IN_PROGRESS">In Progress</MenuItem>
                <MenuItem value="DONE">Done</MenuItem>
              </Select>
            </FormControl>

            <Box sx={{ display: 'flex', gap: 8, flexDirection: isSmall ? 'row' : 'row' }}>
              <Button size={isSmall ? 'small' : 'small'} variant={isSmall ? 'contained' : 'outlined'} sx={{ ml: isSmall ? 0 : 1 }} onClick={() => setOpenEdit(true)}>Edit</Button>
              <Button size={isSmall ? 'small' : 'small'} color="error" variant="outlined" sx={{ ml: isSmall ? 0 : 1 }} onClick={handleDelete}>Delete</Button>
            </Box>
          </Stack>
        </Box>
      </Paper>

      <Grid container spacing={2} className="task-overview-grid">
        <Grid item xs={12} md={7}>
          <Paper className="fancy-section" sx={{ p: 2 }}>
            <Typography variant="h6" sx={{ mb: 1 }}>Description</Typography>
            <Divider sx={{ mb: 1 }} />

            <Typography variant="body1" sx={{ whiteSpace: 'pre-line' }}>{task.description || 'No description provided.'}</Typography>

            <Box mt={2} display="flex" gap={1}>
              <Button variant="contained" className="btn-accent" onClick={() => window.location.href = `/tasks/${task.id}`}>
                Open in page
              </Button>
              <Button variant="outlined" onClick={() => { window.dispatchEvent(new Event('tasks:refresh')); }}>
                Refresh
              </Button>
            </Box>
          </Paper>

          <Box mt={2}>
            <Paper className="fancy-section" sx={{ p: 2 }}>
              <Typography variant="h6">Comments</Typography>
              <Divider sx={{ mb: 1 }} />
              <CommentForm taskId={task.id} onCreated={handleCommentCreated} />
              <Box mt={1}>
                <CommentList taskId={task.id} />
              </Box>
            </Paper>
          </Box>
        </Grid>

        <Grid item xs={12} md={5}>
          <Paper className="fancy-section" sx={{ p: 2 }}>
            <Typography variant="h6">Activity</Typography>
            <Divider sx={{ mb: 1 }} />
            <ActivityList taskId={task.id} />
          </Paper>
        </Grid>
      </Grid>
      <TaskForm open={openEdit} onClose={() => setOpenEdit(false)} onSubmit={handleSave} initial={task} users={users} />
      <Snackbar open={!!error} autoHideDuration={6000} onClose={() => setError(null)}>
        <Alert severity="error" onClose={() => setError(null)}>
          {error}
        </Alert>
      </Snackbar>
    </Box>
  );
};

export default TaskOverview;

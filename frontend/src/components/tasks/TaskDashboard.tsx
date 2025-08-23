import React, { useEffect } from 'react';
import { Box, Button, Grid, Paper, Typography, Select, MenuItem, FormControl, InputLabel, Snackbar, Alert } from '@mui/material';
import apiClient from '../../services/apiClient';
import TaskCard, { Task as TaskType } from './TaskCard';
import TaskForm from './TaskForm';
import { useAuth } from '../../contexts/AuthContext';

type User = { id: number; username: string };

const TaskDashboard: React.FC = () => {
  const { user } = useAuth();
  const [tasks, setTasks] = React.useState<TaskType[]>([]);
  const [users, setUsers] = React.useState<User[]>([]);
  const [filterStatus, setFilterStatus] = React.useState<string>('ALL');
  const [filterAssignee, setFilterAssignee] = React.useState<string>('ALL');
  const [openForm, setOpenForm] = React.useState(false);
  const [editing, setEditing] = React.useState<TaskType | undefined>(undefined);
  const [error, setError] = React.useState<string | null>(null);

  const fetchTasks = async (status?: string, assignee?: string, unassigned?: boolean) => {
    try {
      const params: Record<string, any> = {};
      if (status && status !== 'ALL') params.status = status;
      if (assignee && assignee !== 'ALL' && assignee !== 'UNASSIGNED') params.assigneeId = assignee;
      if (assignee === 'UNASSIGNED' || unassigned) params.unassigned = true;

      // build query string
      const query = new URLSearchParams(params).toString();
      const url = query ? `/api/tasks?${query}` : '/api/tasks';
      const data = await apiClient.get<TaskType[]>(url);
      setTasks(data);
    } catch (err: any) {
      setError(err?.message || 'Failed to load tasks');
    }
  };

  const fetchUsers = async () => {
    try {
      const data = await apiClient.get<User[]>('/api/users');
      setUsers(data);
    } catch (err: any) {
      setError(err?.message || 'Failed to load users');
    }
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  // Refetch tasks whenever filters change
  useEffect(() => {
    fetchTasks(filterStatus, filterAssignee, filterAssignee === 'UNASSIGNED');
  }, [filterStatus, filterAssignee]);

  const openCreate = () => {
    setEditing(undefined);
    setOpenForm(true);
  };

  const handleCreateOrUpdate = async (payload: any) => {
    try {
      if (payload.id) {
        const updated = await apiClient.put<TaskType>(`/api/tasks/${payload.id}`, payload);
        setTasks((t) => t.map((x) => (x.id === updated.id ? updated : x)));
      } else {
        const created = await apiClient.post<TaskType>('/api/tasks', payload);
        setTasks((t) => [created, ...t]);
      }
      setOpenForm(false);
    } catch (err: any) {
      setError(err?.response?.data?.message || err?.message || 'Failed to save task');
    }
  };

  const handleDelete = async (taskId: number) => {
    try {
      await apiClient.delete(`/api/tasks/${taskId}`);
      setTasks((t) => t.filter((x) => x.id !== taskId));
    } catch (err: any) {
      setError(err?.response?.data?.message || err?.message || 'Failed to delete task');
    }
  };

  const handleStatusChange = async (task: TaskType, status: any) => {
    try {
      const payload = { ...task, status };
      const updated = await apiClient.put<TaskType>(`/api/tasks/${task.id}`, payload);
      setTasks((t) => t.map((x) => (x.id === updated.id ? updated : x)));
    } catch (err: any) {
      setError(err?.response?.data?.message || err?.message || 'Failed to update status');
    }
  };

  const columns: Array<{ key: string; title: string }> = [
    { key: 'TODO', title: 'To Do' },
    { key: 'IN_PROGRESS', title: 'In Progress' },
    { key: 'DONE', title: 'Done' },
  ];

  return (
    <Box>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
        <Typography variant="h5">Task Dashboard</Typography>
        <Box>
          <FormControl sx={{ mr: 1 }} size="small">
            <InputLabel id="filter-status">Status</InputLabel>
            <Select labelId="filter-status" value={filterStatus} label="Status" onChange={(e) => setFilterStatus(String(e.target.value))}>
              <MenuItem value="ALL">All</MenuItem>
              <MenuItem value="TODO">TODO</MenuItem>
              <MenuItem value="IN_PROGRESS">IN_PROGRESS</MenuItem>
              <MenuItem value="DONE">DONE</MenuItem>
            </Select>
          </FormControl>
          <FormControl sx={{ mr: 1 }} size="small">
            <InputLabel id="filter-assignee">Assignee</InputLabel>
            <Select labelId="filter-assignee" value={filterAssignee} label="Assignee" onChange={(e) => setFilterAssignee(String(e.target.value))}>
              <MenuItem value="ALL">All</MenuItem>
              <MenuItem value="UNASSIGNED">Unassigned</MenuItem>
              {users.map((u) => (
                <MenuItem key={u.id} value={String(u.id)}>
                  {u.username}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
          <Button variant="contained" onClick={openCreate}>
            New Task
          </Button>
        </Box>
      </Box>

      <Grid container spacing={2}>
        {columns.map((col) => (
          <Grid item xs={12} md={4} key={col.key}>
            <Paper sx={{ p: 1, minHeight: 300 }}>
              <Typography variant="h6" sx={{ mb: 1 }}>
                {col.title}
              </Typography>
              {tasks
                .filter((t) => t.status === col.key)
                .map((task) => (
                  <TaskCard key={task.id} task={task} users={users} onEdit={(t) => { setEditing(t); setOpenForm(true); }} onDelete={handleDelete} onStatusChange={handleStatusChange} />
                ))}
            </Paper>
          </Grid>
        ))}
      </Grid>

      <TaskForm open={openForm} onClose={() => setOpenForm(false)} onSubmit={handleCreateOrUpdate} initial={editing || { title: '', status: 'TODO', priority: 'MEDIUM' }} users={users} />

      <Snackbar open={!!error} autoHideDuration={6000} onClose={() => setError(null)}>
        <Alert severity="error" onClose={() => setError(null)}>
          {error}
        </Alert>
      </Snackbar>
    </Box>
  );
};

export default TaskDashboard;

import React from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Box, Paper, Typography, CircularProgress, Button } from '@mui/material';
import apiClient from '../services/apiClient';
import CommentList from '../components/tasks/CommentList';
import CommentForm from '../components/tasks/CommentForm';

const TaskOverview: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [task, setTask] = React.useState<any | null>(null);
  const [loading, setLoading] = React.useState(true);

  React.useEffect(() => {
    if (!id) return;
    setLoading(true);
    apiClient.get(`/api/tasks/${id}`)
      .then((res) => setTask(res))
      .catch(() => setTask(null))
      .finally(() => setLoading(false));
  }, [id]);

  const handleCommentCreated = () => {
    // notify comment lists to refresh
    window.dispatchEvent(new Event('comments:refresh'));
    // notify dashboard to refresh tasks if needed
    window.dispatchEvent(new Event('tasks:refresh'));
  };

  if (loading) return <Box display="flex" justifyContent="center"><CircularProgress /></Box>;
  if (!task) return (
    <Box>
      <Typography variant="h6">Task not found</Typography>
      <Button onClick={() => navigate('/tasks')}>Back to tasks</Button>
    </Box>
  );

  return (
    <Box>
      <Paper sx={{ p: 2, mb: 2 }}>
        <Typography variant="h5">{task.title}</Typography>
        <Typography variant="body1" sx={{ mt: 1 }}>{task.description}</Typography>
      </Paper>

      <Paper sx={{ p: 2 }}>
  <CommentForm taskId={task.id} onCreated={handleCommentCreated} />
        <Box mt={1}>
          <CommentList taskId={task.id} />
        </Box>
      </Paper>
    </Box>
  );
};

export default TaskOverview;

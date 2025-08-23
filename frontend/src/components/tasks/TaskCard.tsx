import React from 'react';
import { Box, Paper, Typography, IconButton, Menu, MenuItem, Tooltip, Chip, Avatar } from '@mui/material';

type TaskStatus = 'TODO' | 'IN_PROGRESS' | 'DONE';
type Priority = 'LOW' | 'MEDIUM' | 'HIGH';

export type Task = {
  id: number;
  title: string;
  description?: string | null;
  status: TaskStatus;
  priority: Priority;
  assigneeId?: number | null;
  creatorId: number;
  createdAt: string;
  updatedAt: string;
};

type User = {
  id: number;
  username: string;
};

interface Props {
  task: Task;
  users: User[];
  onEdit: (task: Task) => void;
  onDelete: (taskId: number) => void;
  onStatusChange: (task: Task, status: TaskStatus) => void;
}

const statusOptions: TaskStatus[] = ['TODO', 'IN_PROGRESS', 'DONE'];

const TaskCard: React.FC<Props> = ({ task, users, onEdit, onDelete, onStatusChange }) => {
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);

  const assignee = users.find((u) => u.id === task.assigneeId);

  const openMenu = (e: React.MouseEvent<HTMLElement>) => setAnchorEl(e.currentTarget);
  const closeMenu = () => setAnchorEl(null);

  const handleStatus = (status: TaskStatus) => {
    closeMenu();
    if (status !== task.status) onStatusChange(task, status);
  };

  const priorityColor: Record<Priority, string> = {
    LOW: '#6EE7B7',
    MEDIUM: '#FFD166',
    HIGH: '#FF6B6B',
  };

  return (
    <Paper className="task-card" sx={{ p: 1, mb: 1, borderLeft: `6px solid ${priorityColor[task.priority]}` }} elevation={1}>
      <Box display="flex" justifyContent="space-between" alignItems="flex-start">
        <Box>
          <Typography variant="subtitle1" className="task-title">{task.title}</Typography>
          <Typography variant="body2" className="task-meta">
            {task.description}
          </Typography>
          <Box mt={1} display="flex" alignItems="center" gap={1}>
            <Chip label={task.priority} size="small" sx={{ bgcolor: priorityColor[task.priority], color: '#021124', fontWeight: 700 }} />
            {assignee && (
              <Chip
                size="small"
                avatar={<Avatar sx={{ width: 20, height: 20, fontSize: 12 }}>{assignee.username.charAt(0).toUpperCase()}</Avatar>}
                label={assignee.username}
                variant="outlined"
                sx={{ color: 'rgba(255,255,255,0.9)', borderColor: 'rgba(255,255,255,0.06)' }}
              />
            )}
          </Box>
        </Box>

        <Box>
          <Tooltip title="Actions">
            <IconButton size="small" onClick={openMenu}>
              <span aria-hidden style={{ fontWeight: 700, fontSize: 18 }}>⋯</span>
            </IconButton>
          </Tooltip>
          <Menu anchorEl={anchorEl} open={Boolean(anchorEl)} onClose={closeMenu}>
            {statusOptions.map((s) => (
              <MenuItem key={s} onClick={() => handleStatus(s as TaskStatus)}>
                Move to {s}
              </MenuItem>
            ))}
            <MenuItem
              onClick={() => {
                closeMenu();
                onEdit(task);
              }}
            >
              Edit
            </MenuItem>
            <MenuItem
              onClick={() => {
                closeMenu();
                onDelete(task.id);
              }}
            >
              Delete
            </MenuItem>
          </Menu>
        </Box>
      </Box>
    </Paper>
  );
};

export default TaskCard;

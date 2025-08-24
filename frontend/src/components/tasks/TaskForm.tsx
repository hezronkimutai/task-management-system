import React, { useEffect } from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  TextField,
  MenuItem,
  FormControl,
  InputLabel,
  Select,
  SelectChangeEvent,
} from '@mui/material';

type TaskStatus = 'TODO' | 'IN_PROGRESS' | 'DONE';
type Priority = 'LOW' | 'MEDIUM' | 'HIGH';

export type Task = {
  id?: number;
  title: string;
  description?: string | null;
  status: TaskStatus;
  priority: Priority;
  assigneeId?: number | null;
  dueDate?: string | null; // ISO string
};

type User = { id: number; username: string };

interface Props {
  open: boolean;
  onClose: () => void;
  onSubmit: (payload: Task) => void;
  initial?: Partial<Task>;
  users: User[];
}

const TaskForm: React.FC<Props> = ({ open, onClose, onSubmit, initial = {}, users }) => {
  const [title, setTitle] = React.useState(initial.title || '');
  const [description, setDescription] = React.useState(initial.description || '');
  const [status, setStatus] = React.useState<TaskStatus>((initial.status as TaskStatus) || 'TODO');
  const [priority, setPriority] = React.useState<Priority>((initial.priority as Priority) || 'MEDIUM');
  const [assigneeId, setAssigneeId] = React.useState<number | string>(initial.assigneeId ?? '');
  const [dueDate, setDueDate] = React.useState<string | null>(initial.dueDate ?? null);

  useEffect(() => {
    setTitle(initial.title || '');
    setDescription(initial.description || '');
    setStatus((initial.status as TaskStatus) || 'TODO');
    setPriority((initial.priority as Priority) || 'MEDIUM');
    setAssigneeId(initial.assigneeId ?? '');
  setDueDate(initial.dueDate ?? null);
  }, [initial, open]);

  const handleSubmit = () => {
    if (!title.trim()) return;
  onSubmit({ ...initial, title: title.trim(), description, status, priority, assigneeId: assigneeId === '' ? undefined : (assigneeId as number), dueDate: dueDate || undefined });
  };

  return (
    <Dialog open={open} onClose={onClose} fullWidth maxWidth="sm">
      <DialogTitle>{initial?.id ? 'Edit Task' : 'Create Task'}</DialogTitle>
      <DialogContent>
        <TextField autoFocus margin="dense" label="Title" fullWidth value={title} onChange={(e) => setTitle(e.target.value)} />
        <TextField margin="dense" label="Description" fullWidth multiline minRows={3} value={description} onChange={(e) => setDescription(e.target.value)} />

        <FormControl fullWidth margin="dense">
          <InputLabel id="status-label">Status</InputLabel>
          <Select labelId="status-label" value={status} label="Status" onChange={(e: SelectChangeEvent) => setStatus(e.target.value as TaskStatus)}>
            <MenuItem value="TODO">To Do</MenuItem>
            <MenuItem value="IN_PROGRESS">In Progress</MenuItem>
            <MenuItem value="DONE">Done</MenuItem>
          </Select>
        </FormControl>

        <FormControl fullWidth margin="dense">
          <InputLabel id="priority-label">Priority</InputLabel>
          <Select labelId="priority-label" value={priority} label="Priority" onChange={(e: SelectChangeEvent) => setPriority(e.target.value as Priority)}>
            <MenuItem value="LOW">LOW</MenuItem>
            <MenuItem value="MEDIUM">MEDIUM</MenuItem>
            <MenuItem value="HIGH">HIGH</MenuItem>
          </Select>
        </FormControl>

        <FormControl fullWidth margin="dense">
          <InputLabel id="assignee-label">Assignee</InputLabel>
          <Select
            labelId="assignee-label"
            value={assigneeId === undefined ? '' : String(assigneeId)}
            label="Assignee"
            onChange={(e: SelectChangeEvent) => setAssigneeId(e.target.value === '' ? '' : e.target.value)}
          >
            <MenuItem value="">Unassigned</MenuItem>
            {users.map((u) => (
              <MenuItem key={u.id} value={String(u.id)}>
                {u.username}
              </MenuItem>
            ))}
          </Select>
        </FormControl>

        <TextField
          margin="dense"
          label="Due date"
          type="datetime-local"
          fullWidth
          value={dueDate ?? ''}
          onChange={(e) => setDueDate(e.target.value === '' ? null : e.target.value)}
          InputLabelProps={{ shrink: true }}
        />
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>Cancel</Button>
        <Button onClick={handleSubmit} variant="contained">
          {initial?.id ? 'Save' : 'Create'}
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default TaskForm;

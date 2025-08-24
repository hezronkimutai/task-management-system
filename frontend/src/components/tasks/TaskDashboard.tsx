import React, { useEffect } from 'react';
import { Box, Button, Grid, Paper, Typography, Select, MenuItem, FormControl, InputLabel, Snackbar, Alert, Tabs, Tab, useMediaQuery, useTheme, IconButton, Badge, Menu, MenuItem as MItem } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { DragDropContext, Droppable, DropResult } from '@hello-pangea/dnd';
import apiClient from '../../services/apiClient';
import wsClient from '../../services/wsClient';
import TaskCard, { Task as TaskType } from './TaskCard';
import TaskForm from './TaskForm';
import { useAuth } from '../../contexts/AuthContext';
import activityService from '../../services/activityService';

type User = { id: number; username: string };

const TaskDashboard: React.FC = () => {
  const [tasks, setTasks] = React.useState<TaskType[]>([]);
  const [users, setUsers] = React.useState<User[]>([]);
  const [filterStatus, setFilterStatus] = React.useState<string>('ALL');
  const [filterAssignee, setFilterAssignee] = React.useState<string>('ALL');
  const [openForm, setOpenForm] = React.useState(false);
  const [editing, setEditing] = React.useState<TaskType | undefined>(undefined);
  const [error, setError] = React.useState<string | null>(null);
  const [notifications, setNotifications] = React.useState<any[]>([]);
  const [notifAnchor, setNotifAnchor] = React.useState<null | HTMLElement>(null);

  const fetchTasks = async (status?: string, assignee?: string, unassigned?: boolean) => {
    try {
      const params: Record<string, any> = {};
      if (status && status !== 'ALL') params.status = status;
      if (assignee && assignee !== 'ALL' && assignee !== 'UNASSIGNED') params.assigneeId = assignee;
      if (unassigned) params.unassigned = true;
      const data = await apiClient.get<TaskType[]>('/api/tasks', { params });
      setTasks(data || []);
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

  // connect to websocket for real-time updates
  const { user } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    console.log('[ui] wsClient.connect() called');
    wsClient.connect((event: any) => {
      if (!event) return;
      console.log('[ui] ws event received', event);

      // task events
      if (event.action) {
        const act = event.action;
        if (act === 'CREATED' && event.task) {
          setTasks((t) => [event.task, ...t]);
          return;
        }
        if (act === 'UPDATED' && event.task) {
          setTasks((t) => t.map((x) => (x.id === event.task.id ? event.task : x)));
          return;
        }
        if (act === 'DELETED') {
          const id = event.taskId || (event.task && event.task.id);
          if (id) setTasks((t) => t.filter((x) => x.id !== id));
          return;
        }
      }

      // notification events forwarded by wsClient as { type: 'notification', payload }
      if (event.type === 'notification' && event.payload) {
        const n = event.payload;
        // if notification has an assigneeId, only show to that user
        if (n.assigneeId && user && Number(n.assigneeId) !== Number(user.id)) return;
        setNotifications((s) => [n, ...s].slice(0, 20));
      }
    });

    return () => { wsClient.disconnect(); };
  }, [/* run once */, user]);

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
        await apiClient.put<TaskType>(`/api/tasks/${payload.id}`, payload);
        // refresh tasks from server to ensure consistent view
        await fetchTasks(filterStatus, filterAssignee, filterAssignee === 'UNASSIGNED');
  // create activity entry for update
  try { await activityService.create({ taskId: payload.id, type: 'UPDATED', actorId: undefined, actorName: undefined, detail: 'Task updated', createdAt: new Date().toISOString() }); } catch (e) {}
      } else {
        const created = await apiClient.post<TaskType>('/api/tasks', payload);
        // fetch latest tasks immediately so UI reflects server state (and respects filters)
        await fetchTasks(filterStatus, filterAssignee, filterAssignee === 'UNASSIGNED');
        // create activity entry for creation using server-provided id
        try { await activityService.create({ taskId: Number(created.id), type: 'CREATED', actorId: undefined, actorName: undefined, detail: 'Task created', createdAt: new Date().toISOString() }); } catch (e) {}
      }
      setOpenForm(false);
    } catch (err: any) {
      setError(err?.response?.data?.message || err?.message || 'Failed to save task');
    }
  };

  React.useEffect(() => {
    const onTasksRefresh = () => fetchTasks(filterStatus, filterAssignee, filterAssignee === 'UNASSIGNED');
    window.addEventListener('tasks:refresh', onTasksRefresh);
    return () => window.removeEventListener('tasks:refresh', onTasksRefresh);
  }, [filterStatus, filterAssignee]);

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
  // record activity for status change
  try { await activityService.create({ taskId: Number(task.id), type: 'STATUS_CHANGED', actorId: undefined, actorName: undefined, detail: `Status: ${task.status} → ${status}`, createdAt: new Date().toISOString() }); } catch (e) {}
    } catch (err: any) {
      setError(err?.response?.data?.message || err?.message || 'Failed to update status');
    }
  };

  const columns: Array<{ key: string; title: string }> = [
    { key: 'TODO', title: 'To Do' },
    { key: 'IN_PROGRESS', title: 'In Progress' },
    { key: 'DONE', title: 'Done' },
  ];

  const theme = useTheme();
  const isSmall = useMediaQuery(theme.breakpoints.down('md'));
  const [activeTab, setActiveTab] = React.useState<string>('TODO');

  // keep activeTab in sync with filterStatus when filters are changed externally
  useEffect(() => {
    if (filterStatus && filterStatus !== 'ALL') setActiveTab(filterStatus);
  }, [filterStatus]);

  return (
    <Box>
      <DragDropContext
        onDragStart={(start: any) => {
          console.log('dnd:start', start);
        }}
        onDragEnd={async (result: DropResult) => {
          console.log('dnd:end', result);
          const { destination, source, draggableId } = result;
          if (!destination) return;
          // dropped in same place
          if (destination.droppableId === source.droppableId && destination.index === source.index) return;

          const taskId = Number(draggableId.replace('task-', ''));
          const task = tasks.find((t) => t.id === taskId);
          if (!task) return;

          const newStatus = destination.droppableId;
          if (task.status !== newStatus) {
            await handleStatusChange(task, newStatus);
          }
        }}
      >
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
        <Typography variant="h5" className="accent">Task Dashboard</Typography>
        <Box>
          <FormControl sx={{ mr: 1 }} size="small">
            <InputLabel id="filter-status">Status</InputLabel>
            <Select labelId="filter-status" value={filterStatus} label="Status" onChange={(e) => setFilterStatus(String(e.target.value))}>
              <MenuItem value="ALL">ALL</MenuItem>
              <MenuItem value="TODO">To Do</MenuItem>
              <MenuItem value="IN_PROGRESS">In Progress</MenuItem>
              <MenuItem value="DONE">Done</MenuItem>
            </Select>
          </FormControl>
          <FormControl sx={{ mr: 1 }} size="small">
            <InputLabel id="filter-assignee">Assignee</InputLabel>
            <Select labelId="filter-assignee" value={filterAssignee} label="Assignee" onChange={(e) => setFilterAssignee(String(e.target.value))}>
              <MenuItem value="ALL">ALL</MenuItem>
              <MenuItem value="UNASSIGNED">Unassigned</MenuItem>
              {users.map((u) => (
                <MenuItem key={u.id} value={String(u.id)}>
                  {u.username}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
          <IconButton aria-label="notifications" color="inherit" onClick={(e) => setNotifAnchor(e.currentTarget)} sx={{ mr: 1 }}>
            <Badge badgeContent={notifications.length} color="error">
              <span className="material-icons">notifications</span>
            </Badge>
          </IconButton>
          <Menu anchorEl={notifAnchor} open={Boolean(notifAnchor)} onClose={() => setNotifAnchor(null)}>
            {notifications.length === 0 && (
              <MItem onClick={() => setNotifAnchor(null)}>No notifications</MItem>
            )}
            {notifications.map((n, idx) => (
              <MItem key={idx} onClick={() => {
                setNotifAnchor(null);
                setNotifications((s) => s.filter((_, i) => i !== idx));
                // if the notification relates to a task, navigate to its overview page
                if (n && (n.taskId || n.task?.id)) {
                  const id = n.taskId || (n.task && n.task.id);
                  try { navigate(`/tasks/${id}`); } catch (e) {}
                }
              }}>
                <Box sx={{ display: 'flex', flexDirection: 'column' }}>
                  <strong>{n.type}</strong>
                  <span>{n.title}</span>
                  {n.dueDate && <small>Due: {new Date(n.dueDate).toLocaleString()}</small>}
                </Box>
              </MItem>
            ))}
          </Menu>
          <Button variant="contained" onClick={openCreate} className="btn-accent">
            New Task
          </Button>
        </Box>
      </Box>

      {isSmall ? (
        <Box>
          <Tabs value={activeTab} onChange={(e, v) => { setActiveTab(String(v)); setFilterAssignee('ALL'); setFilterStatus(String(v)); }} variant="fullWidth" textColor="inherit" indicatorColor="primary">
            {columns.map((c) => (
              <Tab key={c.key} label={c.title} value={c.key} />
            ))}
          </Tabs>

          <Box mt={2}>
            <Droppable
              droppableId={activeTab}
              type="TASKS"
              renderClone={(provided: any, snapshot: any, rubric: any) => {
                const id = Number(String(rubric.draggableId).replace('task-', ''));
                const task = tasks.find((t) => t.id === id);
                if (!task) return null;
                return (
                  <div ref={provided.innerRef} {...provided.draggableProps} {...provided.dragHandleProps}>
                    <TaskCard task={task} users={users} onEdit={() => {}} onDelete={() => {}} onStatusChange={() => {}} draggableId={undefined} index={undefined} />
                  </div>
                );
              }}
            >
              {(provided: any, snapshot: any) => (
                <Paper
                  className="task-column fancy-card"
                  sx={{ p: 1, minHeight: 300, backgroundColor: snapshot.isDraggingOver ? 'rgba(25,118,210,0.06)' : undefined }}
                  ref={provided.innerRef}
                  {...provided.droppableProps}
                >
                  <Typography variant="h6" sx={{ mb: 1 }} className="small-muted">
                    {columns.find((c) => c.key === activeTab)?.title}
                  </Typography>
                  {tasks
                    .filter((t) => t.status === activeTab)
                    .map((task, idx) => (
                      <TaskCard draggableId={`task-${task.id}`} index={idx} key={task.id} task={task} users={users} onEdit={(t) => { setEditing(t); setOpenForm(true); }} onDelete={handleDelete} onStatusChange={handleStatusChange} />
                    ))}
                  {provided.placeholder}
                </Paper>
              )}
            </Droppable>
          </Box>
        </Box>
      ) : (
        <Grid container spacing={2} className="dashboard-grid">
          {columns.map((col) => (
            <Grid item xs={12} md={4} key={col.key}>
              <Droppable
                droppableId={col.key}
                type="TASKS"
                renderClone={(provided: any, snapshot: any, rubric: any) => {
                  const id = Number(String(rubric.draggableId).replace('task-', ''));
                  const task = tasks.find((t) => t.id === id);
                  if (!task) return null;
                  return (
                    <div ref={provided.innerRef} {...provided.draggableProps} {...provided.dragHandleProps}>
                      <TaskCard task={task} users={users} onEdit={() => {}} onDelete={() => {}} onStatusChange={() => {}} draggableId={undefined} index={undefined} />
                    </div>
                  );
                }}
              >
                {(provided: any, snapshot: any) => (
                  <Paper
                    className="task-column fancy-card"
                    sx={{ p: 1, minHeight: 300, backgroundColor: snapshot.isDraggingOver ? 'rgba(25,118,210,0.06)' : undefined }}
                    ref={provided.innerRef}
                    {...provided.droppableProps}
                  >
                    <Typography variant="h6" sx={{ mb: 1 }} className="small-muted">
                      {col.title}
                    </Typography>
                    {tasks
                      .filter((t) => t.status === col.key)
                      .map((task, idx) => (
                        <TaskCard draggableId={`task-${task.id}`} index={idx} key={task.id} task={task} users={users} onEdit={(t) => { setEditing(t); setOpenForm(true); }} onDelete={handleDelete} onStatusChange={handleStatusChange} />
                      ))}
                    {provided.placeholder}
                  </Paper>
                )}
              </Droppable>
            </Grid>
          ))}
        </Grid>
      )}

  </DragDropContext>

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

import React from 'react';
import { Box, Typography, List, ListItem, ListItemText, Divider } from '@mui/material';
import activityService, { Activity } from '../../services/activityService';

const ActivityList: React.FC<{ taskId: number }> = ({ taskId }) => {
  const [activities, setActivities] = React.useState<Activity[]>([]);

  const load = React.useCallback(async () => {
    try {
      const data = await activityService.listByTask(taskId);
      setActivities(data);
    } catch (e) {
      setActivities([]);
    }
  }, [taskId]);

  React.useEffect(() => {
    let mounted = true;
    load();
    const onRefresh = () => { if (mounted) load(); };
    window.addEventListener('activities:refresh', onRefresh);
    return () => { mounted = false; window.removeEventListener('activities:refresh', onRefresh); };
  }, [load]);

  if (!activities.length) return (
    <Box mt={2}>
      <Typography variant="subtitle2">Activity</Typography>
      <Typography variant="body2" className="small-muted">No activity yet</Typography>
    </Box>
  );

  return (
    <Box mt={2}>
      <Typography variant="subtitle2">Activity</Typography>
      <List dense>
        {activities.map((a) => (
          <React.Fragment key={String(a.id) + a.createdAt}>
            <ListItem>
              <ListItemText primary={a.detail ?? a.type} secondary={`${a.actorName ?? (a.actorId ? `User ${a.actorId}` : 'System')} · ${new Date(a.createdAt).toLocaleString()}`} />
            </ListItem>
            <Divider component="li" />
          </React.Fragment>
        ))}
      </List>
    </Box>
  );
};

export default ActivityList;

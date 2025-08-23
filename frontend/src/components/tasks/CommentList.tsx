import React from 'react';
import { Box, Typography, IconButton, List, ListItem, ListItemText, ListItemSecondaryAction } from '@mui/material';
import commentService, { Comment } from '../../services/commentService';
import { useAuth } from '../../contexts/AuthContext';

interface Props {
  taskId: number;
}

const CommentList: React.FC<Props> = ({ taskId }) => {
  const [comments, setComments] = React.useState<Comment[]>([]);
  const { user } = useAuth();

  const fetch = async () => {
    try {
      const data = await commentService.listByTask(taskId);
      setComments(data);
    } catch (e) {
      // ignore for now
    }
  };

  React.useEffect(() => {
    let mounted = true;
    const doFetch = async () => {
      try {
        const data = await commentService.listByTask(taskId);
        if (mounted) setComments(data);
      } catch (e) {
        // ignore
      }
    };

    doFetch();

    const onRefresh = () => { doFetch(); };
    window.addEventListener('comments:refresh', onRefresh);
    return () => { mounted = false; window.removeEventListener('comments:refresh', onRefresh); };
  }, [taskId]);

  const handleDelete = async (id: number) => {
    try {
      await commentService.delete(id);
      setComments((c) => c.filter((x) => x.id !== id));
    } catch (e) {
      // ignore
    }
  };

  return (
    <Box mt={1}>
      <Typography variant="subtitle2">Comments</Typography>
      <List dense>
        {comments.map((c) => (
          <ListItem key={c.id}>
            <ListItemText
              primary={c.content}
              secondary={`by ${c.authorUsername ?? c.authorId} · ${new Date(c.createdAt).toLocaleString()}`}
            />
            <ListItemSecondaryAction>
              {user && user.id === c.authorId && (
                <IconButton edge="end" size="small" onClick={() => handleDelete(c.id)}>
                  ✖
                </IconButton>
              )}
            </ListItemSecondaryAction>
          </ListItem>
        ))}
      </List>
    </Box>
  );
};

export default CommentList;

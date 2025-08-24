import React from 'react';
import { Box, TextField, Button } from '@mui/material';
import commentService from '../../services/commentService';
import activityService from '../../services/activityService';

interface Props {
  taskId: number;
  onCreated?: () => void;
}

const CommentForm: React.FC<Props> = ({ taskId, onCreated }) => {
  const [text, setText] = React.useState('');
  const [loading, setLoading] = React.useState(false);

  const submit = async () => {
    if (!text.trim()) return;
    setLoading(true);
    try {
      await commentService.create({ taskId, content: text.trim() });
  try { await activityService.create({ taskId, type: 'COMMENT', actorId: undefined, actorName: undefined, detail: text.trim(), createdAt: new Date().toISOString() }); } catch (e) {}
      setText('');
      onCreated && onCreated();
    } catch (e) {
      // ignore
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box mt={1} display="flex" gap={1}>
      <TextField size="small" fullWidth value={text} onChange={(e) => setText(e.target.value)} placeholder="Add a comment" />
      <Button variant="contained" size="small" onClick={submit} disabled={loading || !text.trim()}>Add</Button>
    </Box>
  );
};

export default CommentForm;

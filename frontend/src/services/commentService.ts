import apiClient from './apiClient';

export type CommentCreatePayload = {
  taskId: number;
  content: string;
};

export type Comment = {
  id: number;
  content: string;
  taskId: number;
  authorId: number;
  authorUsername?: string;
  createdAt: string;
};

const commentService = {
  create: async (payload: CommentCreatePayload): Promise<Comment> => {
    return apiClient.post('/api/comments', payload);
  },
  listByTask: async (taskId: number): Promise<Comment[]> => {
    return apiClient.get(`/api/comments/task/${taskId}`);
  },
  update: async (id: number, content: string): Promise<Comment> => {
    return apiClient.put(`/api/comments/${id}`, { content, taskId: undefined });
  },
  delete: async (id: number): Promise<void> => {
    return apiClient.delete(`/api/comments/${id}`);
  },
};

export default commentService;

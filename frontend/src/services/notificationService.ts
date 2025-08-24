import api from './api';

export type DueTask = {
  id: number;
  title: string;
  dueDate?: string | null;
};

const getDueSoon = async (minutes = 30): Promise<DueTask[]> => {
  const resp = await api.get<DueTask[]>(`/api/tasks/due-soon?minutes=${minutes}`);
  return resp.data || [];
};

const notificationService = { getDueSoon };
export default notificationService;

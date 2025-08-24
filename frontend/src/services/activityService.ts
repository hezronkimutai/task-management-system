import apiClient from './apiClient';

export type ActivityType = 'CREATED' | 'UPDATED' | 'STATUS_CHANGED' | 'COMMENT';

export type Activity = {
  id?: number;
  taskId: number;
  type: ActivityType;
  actorId?: number | null;
  actorName?: string | null;
  detail?: string | null;
  createdAt: string; // ISO
};

const LOCAL_KEY = 'tms_activity_log_v1';

const tryParse = (s: string | null) => {
  try {
    return s ? JSON.parse(s) : [];
  } catch (e) {
    return [];
  }
};

const readLocal = (): Activity[] => tryParse(localStorage.getItem(LOCAL_KEY));

const writeLocal = (arr: Activity[]) => localStorage.setItem(LOCAL_KEY, JSON.stringify(arr));

const activityService = {
  async listByTask(taskId: number): Promise<Activity[]> {
    // Try backend endpoint first (if implemented)
    try {
      const res = await apiClient.get<Activity[]>(`/api/activities/task/${taskId}`);
      return res;
    } catch (err) {
      // fallback to local + build-from-comments handled by caller
      const all = readLocal();
      return all.filter((a) => a.taskId === taskId).sort((a, b) => new Date(a.createdAt).getTime() - new Date(b.createdAt).getTime());
    }
  },

  async create(activity: Activity): Promise<Activity> {
    // try backend create
    try {
      const res = await apiClient.post<Activity>('/api/activities', activity);
      // notify listeners
      window.dispatchEvent(new Event('activities:refresh'));
      return res;
    } catch (err) {
      // fallback: store locally
      const all = readLocal();
      const entry: Activity = { ...activity, id: Date.now() };
      all.push(entry);
      writeLocal(all);
      window.dispatchEvent(new Event('activities:refresh'));
      return entry;
    }
  },

  // utility to seed a creation event when we only have task data
  buildFromTask(task: any, creatorName?: string | null): Activity[] {
    if (!task) return [];
    const createdAt = task.createdAt || new Date().toISOString();
    const updatedAt = task.updatedAt || createdAt;
    const list: Activity[] = [];
    list.push({ taskId: Number(task.id), type: 'CREATED', actorId: task.creatorId ?? null, actorName: creatorName ?? null, detail: `Task created`, createdAt });
    if (updatedAt && updatedAt !== createdAt) {
      list.push({ taskId: Number(task.id), type: 'UPDATED', actorId: null, actorName: null, detail: `Task updated`, createdAt: updatedAt });
    }
    return list;
  }
};

export default activityService;

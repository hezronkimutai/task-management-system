import axios, { AxiosInstance } from 'axios';
import config from '../config/config';
import authService from './authService';

const api: AxiosInstance = axios.create({
  baseURL: config.api.baseUrl,
  timeout: config.api.timeout,
  headers: {
    'Content-Type': 'application/json',
  },
});

let isRefreshing = false;
let failedQueue: Array<{ resolve: (token: string) => void; reject: (err: any) => void }> = [];

const processQueue = (error: any, token: string | null = null) => {
  failedQueue.forEach((prom) => {
    if (error) prom.reject(error);
    else prom.resolve(token as string);
  });
  failedQueue = [];
};

api.interceptors.request.use(
  (configReq) => {
    const token = authService.getToken();
    if (token) {
      configReq.headers = configReq.headers || {};
      configReq.headers.Authorization = `Bearer ${token}`;
    }
  // debug logging removed
    return configReq;
  },
  (error) => Promise.reject(error)
);

api.interceptors.response.use(
  (resp) => {
  // debug logging removed
    return resp;
  },
  async (error) => {
    const originalRequest = error.config;

  // debug logging removed

    if (error.response?.status === 401 && !originalRequest._retry) {
      // Attempt refresh once
      originalRequest._retry = true;

      if (isRefreshing) {
        return new Promise(function (resolve, reject) {
          failedQueue.push({
            resolve: (token: string) => {
              originalRequest.headers['Authorization'] = 'Bearer ' + token;
              resolve(api(originalRequest));
            },
            reject,
          });
        });
      }

      isRefreshing = true;
      try {
        const newToken = await authService.refreshToken();
        if (newToken) {
          originalRequest.headers['Authorization'] = 'Bearer ' + newToken;
          processQueue(null, newToken);
          return api(originalRequest);
        }
      } catch (err) {
        processQueue(err, null);
        authService.logout();
        window.location.href = '/login';
        return Promise.reject(err);
      } finally {
        isRefreshing = false;
      }
    }

    return Promise.reject(error);
  }
);

export default api;

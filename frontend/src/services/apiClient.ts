import axios, { AxiosInstance, InternalAxiosRequestConfig, AxiosResponse } from 'axios';
import config from '../config/config';

/**
 * API client configuration and setup
 */
class ApiClient {
  private client: AxiosInstance;

  constructor() {
    this.client = axios.create({
      baseURL: config.api.baseUrl,
      timeout: config.api.timeout,
      headers: {
        'Content-Type': 'application/json',
      },
    });

    this.setupInterceptors();
  }

  /**
   * Setup request and response interceptors
   */
  private setupInterceptors(): void {
    // Request interceptor - add auth token
    this.client.interceptors.request.use(
      (requestConfig: InternalAxiosRequestConfig) => {
        // Ensure requests to /api/* use an absolute backend base URL. We set this here
        // (at request time) to avoid referencing window at module load time.
        try {
          const urlCheck = String(requestConfig.url || '');
          if ((requestConfig.baseURL == null || requestConfig.baseURL === '') && urlCheck.startsWith('/api/')) {
            (requestConfig as any).baseURL = config.api.baseUrl && config.api.baseUrl.length ? config.api.baseUrl : window.location.origin;
          }
        } catch (_) {
          // ignore when window is not available or other errors
        }

        // Read token directly from localStorage (use the same key your login console.log shows)
        try {
          const stored = localStorage.getItem(config.auth.tokenKey);
          const token = stored ? (stored.startsWith('Bearer ') ? stored.slice(7) : stored) : null;
          if (token) {
            requestConfig.headers = requestConfig.headers || {};
            // cast headers to any to avoid strict typing issues when assigning Authorization
            (requestConfig.headers as any).Authorization = `Bearer ${token}`;
          }

          // Optional debug: warn when calling protected API without a token
          const url = String(requestConfig.url || '');
          const method = String(requestConfig.method || '').toUpperCase();
          const isApi = url.startsWith('/api/') || url.startsWith(config.api.baseUrl + '/api/');
          if (config.features?.enableDebug && isApi && !token) {
            // eslint-disable-next-line no-console
            console.warn(`[apiClient] No auth token attached for ${method} ${url}`);
          }
        } catch (e) {
          // ignore any localStorage access errors
        }

        return requestConfig;
      },
      (error) => Promise.reject(error)
    );

    // Response interceptor - handle common errors
    this.client.interceptors.response.use(
      (response: AxiosResponse) => {
  // debug logging removed
        return response;
      },
      (error) => {
        // Handle 401 Unauthorized - token expired
        if (error.response?.status === 401) {
          localStorage.removeItem(config.auth.tokenKey);
          window.location.href = '/login';
        }

        return Promise.reject(error);
      }
    );
  }

  /**
   * GET request
   */
  async get<T>(url: string, params?: any): Promise<T> {
    const response = await this.client.get<T>(url, { params });
    return response.data;
  }

  /**
   * POST request
   */
  async post<T>(url: string, data?: any): Promise<T> {
    const response = await this.client.post<T>(url, data);
    return response.data;
  }

  /**
   * PUT request
   */
  async put<T>(url: string, data?: any): Promise<T> {
    const response = await this.client.put<T>(url, data);
    return response.data;
  }

  /**
   * DELETE request
   */
  async delete<T>(url: string): Promise<T> {
    const response = await this.client.delete<T>(url);
    return response.data;
  }

  /**
   * Get the base URL
   */
  getBaseUrl(): string {
    return config.api.baseUrl;
  }
}

// Create and export a singleton instance
const apiClient = new ApiClient();
export default apiClient;

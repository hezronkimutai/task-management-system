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
        const token = localStorage.getItem(config.auth.tokenKey);
        if (token) {
          requestConfig.headers = requestConfig.headers || {};
          requestConfig.headers.Authorization = `Bearer ${token}`;
        }

  // debug logging removed

        return requestConfig;
      },
      (error) => {
  // debug logging removed
        return Promise.reject(error);
      }
    );

    // Response interceptor - handle common errors
    this.client.interceptors.response.use(
      (response: AxiosResponse) => {
  // debug logging removed
        return response;
      },
      (error) => {
  // debug logging removed

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

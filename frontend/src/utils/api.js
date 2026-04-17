import axios from 'axios';

const API_BASE_URL = 'https://kiri-ai-backend.onrender.com/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// AUTH_INTERCEPTOR: Automatically attach JWT token to all requests
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('kiri_auth_token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
}, (error) => {
  return Promise.reject(error);
});

// RESPONSE_INTERCEPTOR: Handle 401 Unauthorized globally
api.interceptors.response.use((response) => response, (error) => {
  if (error.response && error.response.status === 401) {
    localStorage.removeItem('kiri_auth_token');
    // Optional: window.location.href = '/login';
  }
  return Promise.reject(error);
});

export default api;

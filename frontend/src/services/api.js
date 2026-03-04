import axios from 'axios';

const API_BASE = process.env.REACT_APP_API_URL || 'http://localhost:5000/api';

const api = axios.create({
  baseURL: API_BASE,
  timeout: 30000,
  headers: { 'Content-Type': 'application/json' }
});

// Attach JWT token to all requests
api.interceptors.request.use(config => {
  const token = localStorage.getItem('kiri_token');
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
}, error => Promise.reject(error));

// Handle auth errors globally
api.interceptors.response.use(
  response => response,
  error => {
    if (error.response?.status === 401) {
      localStorage.removeItem('kiri_token');
      localStorage.removeItem('kiri_user');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

// Auth API
export const authAPI = {
  register: (data) => api.post('/auth/register', data),
  login: (data) => api.post('/auth/login', data),
  getMe: () => api.get('/auth/me'),
  updateProfile: (data) => api.put('/auth/profile', data),
  changePassword: (data) => api.put('/auth/change-password', data),
  deleteAccount: () => api.delete('/auth/account')
};

// Chat API
export const chatAPI = {
  sendMessage: (data) => api.post('/chat/message', data),
  getConversations: (params) => api.get('/chat/conversations', { params }),
  getConversation: (id) => api.get(`/chat/conversations/${id}`),
  deleteConversation: (id) => api.delete(`/chat/conversations/${id}`),
  deleteAllConversations: () => api.delete('/chat/conversations'),
  pinConversation: (id) => api.put(`/chat/conversations/${id}/pin`)
};

// Subscription API
export const subscriptionAPI = {
  getPlans: () => api.get('/subscription/plans'),
  createCheckout: (planId) => api.post('/subscription/create-checkout', { planId }),
  cancelSubscription: () => api.post('/subscription/cancel'),
  getStatus: () => api.get('/subscription/status')
};

export default api;

import api from './api';

export const categoryService = {
  getCategories: () => api.get('/categories').then((r) => r.data),
  getCategoryById: (id) => api.get(`/categories/${id}`).then((r) => r.data),
  createCategory: (data) => api.post('/categories', data).then((r) => r.data),
  updateCategory: (id, data) => api.put(`/categories/${id}`, data).then((r) => r.data),
  deleteCategory: (id) => api.delete(`/categories/${id}`),
};

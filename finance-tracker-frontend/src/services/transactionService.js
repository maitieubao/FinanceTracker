import api from './api';

const cleanParams = (params = {}) => {
  const cleaned = {};
  if (params.type && params.type.trim() !== '') cleaned.type = params.type;
  if (params.categoryId) cleaned.categoryId = params.categoryId;
  if (params.query && params.query.trim() !== '') cleaned.query = params.query;

  const from = params.from || params.fromDate;
  const to = params.to || params.toDate;
  if (from && String(from).trim() !== '') cleaned.fromDate = from;
  if (to && String(to).trim() !== '') cleaned.toDate = to;

  if (params.page !== undefined && params.page !== null) cleaned.page = params.page;
  if (params.size !== undefined && params.size !== null) cleaned.size = params.size;
  return cleaned;
};

export const transactionService = {
  getTransactions: (params) =>
    api
      .get('/transactions', { params: cleanParams(params) })
      .then((r) => r.data),
  getTransactionById: (id) => api.get(`/transactions/${id}`).then((r) => r.data),
  createTransaction: (data) => api.post('/transactions', data).then((r) => r.data),
  updateTransaction: (id, data) => api.put(`/transactions/${id}`, data).then((r) => r.data),
  deleteTransaction: (id) => api.delete(`/transactions/${id}`),
  getSummary: (fromDate, toDate) => {
    const params = {};
    if (fromDate && String(fromDate).trim() !== '') params.fromDate = fromDate;
    if (toDate && String(toDate).trim() !== '') params.toDate = toDate;
    return api.get('/transactions/summary', { params }).then((r) => r.data);
  },
};

// src/composables/useTransactionsCalc.js
import { reactive, computed } from 'vue';

export const useTransactionsCalc = (store) => {
  const typeOptions = [
    { value: '', label: 'All Types' },
    { value: 'INCOME', label: 'INCOME Only' },
    { value: 'EXPENSE', label: 'EXPENSE Only' },
  ];

  const categoryOptions = computed(() => {
    const cats = store.categories || [];
    return cats.map((c) => ({ value: c.id, label: `${c.name} (${c.type})` }));
  });

  const localFilters = reactive({
    query: '',
    type: '',
    categoryId: '',
    from: '',
    to: '',
  });

  const applyFilters = () => {
    if (store.setFilters) store.setFilters(localFilters);
  };

  const resetLocalFilters = () => {
    localFilters.query = '';
    localFilters.type = '';
    localFilters.categoryId = '';
    localFilters.from = '';
    localFilters.to = '';
    if (store.resetFilters) store.resetFilters();
  };

  // 🔹 Lấy dữ liệu danh sách giao dịch trang hiện tại từ Store
  const paginatedTransactions = computed(() => store.transactions || []);
  const totalItems = computed(() => store.pagination?.totalElements || 0);
  const totalPages = computed(() => store.pagination?.totalPages || 1);

  // 🔹 Chuyển đổi giữa 1-based (UI) và 0-based (Backend API)
  const currentPage = computed({
    get: () => (store.pagination?.currentPage ?? 0) + 1,
    set: (pageOneBased) => {
      const pageZeroBased = pageOneBased - 1;
      if (store.fetchTransactions) {
        store.fetchTransactions(pageZeroBased);
      }
    },
  });

  const confirmDelete = async (id) => {
    if (confirm(`Are you sure you want to delete transaction #${id}?`)) {
      if (store.deleteTransaction) await store.deleteTransaction(id);
    }
  };

  return {
    currentPage,
    typeOptions,
    categoryOptions,
    localFilters,
    applyFilters,
    resetLocalFilters,
    totalItems,
    paginatedTransactions,
    totalPages,
    confirmDelete,
  };
};

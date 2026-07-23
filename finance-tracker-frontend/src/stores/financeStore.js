import { defineStore } from 'pinia';
import { categoryService } from '../services/categoryService';
import { transactionService } from '../services/transactionService';

export const useFinanceStore = defineStore('finance', {
  state: () => ({
    categories: [],

    transactions: [],

    pagination: {
      currentPage: 0,
      pageSize: 10,
      totalPages: 0,
      totalElements: 0,
    },

    summary: {
      totalIncome: 0,
      totalExpense: 0,
      balance: 0,
      savingsRate: 0,
    },

    filters: {
      query: '',
      type: '',
      categoryId: '',
      from: '',
      to: '',
    },

    loading: false,
    error: null,

    isTransactionModalOpen: false,
    editingTransaction: null,
    isCategoryModalOpen: false,
    editingCategory: null,
  }),

  getters: {
    incomeCategories: (state) => (state.categories || []).filter((c) => c.type === 'INCOME'),

    expenseCategories: (state) => (state.categories || []).filter((c) => c.type === 'EXPENSE'),

    recentTransactions: (state) => [...(state.transactions || [])].slice(0, 5),

    categoryStats: (state) => {
      const map = {};
      (state.transactions || []).forEach((tx) => {
        const categoryName = tx.categoryName || 'Uncategorized';
        if (!map[categoryName]) {
          map[categoryName] = { name: categoryName, type: tx.type, total: 0, count: 0 };
        }
        map[categoryName].total += Number(tx.amount);
        map[categoryName].count += 1;
      });
      return Object.values(map).sort((a, b) => b.total - a.total);
    },
  },

  actions: {
    async fetchCategories() {
      try {
        this.categories = await categoryService.getCategories();
      } catch (err) {
        this.error = err.message;
      }
    },

    async fetchTransactions(page = 0) {
      this.loading = true;
      this.error = null;
      try {
        const params = {
          ...this.filters,
          page: page,
          size: this.pagination.pageSize,
        }

        const data = await transactionService.getTransactions(params);

        if (data && data.content) {
          this.transactions = data.content;
          this.pagination.currentPage = data.number;
          this.pagination.totalPages = data.totalPages;
          this.pagination.totalElements = data.totalElements;
        } else if (Array.isArray(data)) {
          this.transactions = data;
        }
      } catch (err) {
        this.error = err.message;
      } finally {
        this.loading = false;
      }
    },

    async fetchSummary(fromDate, toDate) {
      try {
        const from = fromDate !== undefined ? fromDate : this.filters.from;
        const to = toDate !== undefined ? toDate : this.filters.to;
        this.summary = await transactionService.getSummary(from, to);
      } catch (err) {
        this.error = err.message;
      }
    },

    async loadAll() {
      this.loading = true;
      await Promise.all([
        this.fetchCategories(),
        this.fetchTransactions(this.pagination.currentPage),
        this.fetchSummary(this.filters.from, this.filters.to),
      ]);
      this.loading = false;
    },

    openAddTransactionModal() {
      this.editingTransaction = null;
      this.isTransactionModalOpen = true;
    },

    openEditTransactionModal(tx) {
      this.editingTransaction = { ...tx };
      this.isTransactionModalOpen = true;
    },

    closeTransactionModal() {
      this.isTransactionModalOpen = false;
      this.editingTransaction = null;
    },

    async saveTransaction(payload) {
      this.loading = true;
      try {
        if (this.editingTransaction && this.editingTransaction.id) {
          await transactionService.updateTransaction(this.editingTransaction.id, payload);
        } else {
          await transactionService.createTransaction(payload);
        }
        await this.loadAll();
        this.closeTransactionModal();
      } catch (err) {
        this.error = err.message;
        throw err;
      } finally {
        this.loading = false;
      }
    },

    async deleteTransaction(id) {
      this.loading = true;
      try {
        await transactionService.deleteTransaction(id);
        await this.loadAll();
      } catch (err) {
        this.error = err.message;
      } finally {
        this.loading = false;
      }
    },

    openAddCategoryModal() {
      this.editingCategory = null;
      this.isCategoryModalOpen = true;
    },

    openEditCategoryModal(cat) {
      this.editingCategory = { ...cat };
      this.isCategoryModalOpen = true;
    },

    closeCategoryModal() {
      this.isCategoryModalOpen = false;
      this.editingCategory = null;
    },

    async saveCategory(payload) {
      this.loading = true;
      try {
        if (this.editingCategory && this.editingCategory.id) {
          await categoryService.updateCategory(this.editingCategory.id, payload);
        } else {
          await categoryService.createCategory(payload);
        }
        await this.loadAll();
        this.closeCategoryModal();
      } catch (err) {
        this.error = err.message;
        throw err;
      } finally {
        this.loading = false;
      }
    },

    async deleteCategory(id) {
      this.loading = true;
      try {
        await categoryService.deleteCategory(id);
        await this.loadAll();
      } catch (err) {
        this.error = err.message;
      } finally {
        this.loading = false;
      }
    },

    setFilters(newFilters) {
      this.filters = { ...this.filters, ...newFilters };
      this.fetchTransactions(0);
      this.fetchSummary();
    },

    resetFilters() {
      this.filters = {
        query: '',
        type: '',
        categoryId: '',
        from: '',
        to: '',
      };
      this.fetchTransactions(0);
      this.fetchSummary();
    },
  },
});

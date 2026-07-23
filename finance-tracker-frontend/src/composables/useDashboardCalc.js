import { computed, reactive, ref, onMounted } from 'vue';
import { formatCurrency } from '../utils/format';

const formatDateString = (date) => {
  const d = new Date(date);
  const year = d.getFullYear();
  const month = String(d.getMonth() + 1).padStart(2, '0');
  const day = String(d.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
};

const getPresetDates = (presetKey) => {
  const now = new Date();
  let from = '';
  let to = '';

  switch (presetKey) {
    case 'today': {
      const todayStr = formatDateString(now);
      from = todayStr;
      to = todayStr;
      break;
    }
    case '7days': {
      const past = new Date();
      past.setDate(now.getDate() - 6);
      from = formatDateString(past);
      to = formatDateString(now);
      break;
    }
    case 'thisMonth': {
      const firstDay = new Date(now.getFullYear(), now.getMonth(), 1);
      const lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0);
      from = formatDateString(firstDay);
      to = formatDateString(lastDay);
      break;
    }
    case 'lastMonth': {
      const firstDay = new Date(now.getFullYear(), now.getMonth() - 1, 1);
      const lastDay = new Date(now.getFullYear(), now.getMonth(), 0);
      from = formatDateString(firstDay);
      to = formatDateString(lastDay);
      break;
    }
    case 'allTime':
    default:
      from = '';
      to = '';
      break;
  }

  return { from, to };
};

export const useDashboardCalc = (store) => {
  const selectedPreset = ref('thisMonth');

  const initialDates = getPresetDates('thisMonth');

  const dashboardFilter = reactive({
    from: initialDates.from,
    to: initialDates.to,
    type: '',
    categoryId: '',
  });

  const summary = computed(() => store.summary || {
    totalIncome: 0,
    totalExpense: 0,
    balance: 0,
    savingsRate: 0,
  });

  const applyFilters = () => {
    if (store.setFilters) {
      store.setFilters({ ...dashboardFilter });
    }
  };

  const updateFilters = (newFilters) => {
    Object.assign(dashboardFilter, newFilters);
    applyFilters();
  };

  const selectPreset = (presetKey) => {
    selectedPreset.value = presetKey;
    if (presetKey !== 'custom') {
      const { from, to } = getPresetDates(presetKey);
      dashboardFilter.from = from;
      dashboardFilter.to = to;
    }
    applyFilters();
  };

  const resetFilters = () => {
    selectedPreset.value = 'allTime';
    dashboardFilter.from = '';
    dashboardFilter.to = '';
    dashboardFilter.type = '';
    dashboardFilter.categoryId = '';
    if (store.resetFilters) {
      store.resetFilters();
    } else {
      applyFilters();
    }
  };

  onMounted(() => {
    applyFilters();
  });

  const recentTransactions = computed(() => store.recentTransactions || []);
  const incomeCategories = computed(() => store.incomeCategories || []);
  const expenseCategories = computed(() => store.expenseCategories || []);

  const incomePercentage = computed(() => {
    const total = summary.value.totalIncome + summary.value.totalExpense;
    return total === 0 ? 50 : Math.round((summary.value.totalIncome / total) * 100);
  });

  const expensePercentage = computed(() => {
    const total = summary.value.totalIncome + summary.value.totalExpense;
    return total === 0 ? 50 : Math.round((summary.value.totalExpense / total) * 100);
  });

  const topExpenseCategories = computed(() => {
    return (store.categoryStats || [])
      .filter((c) => c.type === 'EXPENSE')
      .slice(0, 4);
  });

  return {
    formatCurrency,
    summary,
    dashboardFilter,
    selectedPreset,
    applyFilters,
    updateFilters,
    selectPreset,
    resetFilters,
    recentTransactions,
    incomeCategories,
    expenseCategories,
    incomePercentage,
    expensePercentage,
    topExpenseCategories,
  };
};

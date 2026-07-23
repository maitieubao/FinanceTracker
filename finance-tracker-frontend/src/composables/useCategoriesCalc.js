import { ref, computed } from 'vue';

/**
 * Composable for Categories view filtering and actions
 */
export const useCategoriesCalc = (store) => {
  const activeTab = ref('ALL');

  const filteredCategories = computed(() => {
    const categories = store.categories || [];
    if (activeTab.value === 'INCOME') return store.incomeCategories || categories.filter((c) => c.type === 'INCOME');
    if (activeTab.value === 'EXPENSE') return store.expenseCategories || categories.filter((c) => c.type === 'EXPENSE');
    return categories;
  });

  const confirmDeleteCategory = async (id) => {
    if (confirm('Are you sure you want to delete this category?')) {
      if (store.deleteCategory) await store.deleteCategory(id);
    }
  };

  return {
    activeTab,
    filteredCategories,
    confirmDeleteCategory,
  };
};

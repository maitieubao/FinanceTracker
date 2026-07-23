import { ref, reactive, computed, watch } from 'vue';

/**
 * Composable for TransactionModal form state and validation
 */
export const useTransactionForm = (store) => {
  const formError = ref('');
  const submitting = ref(false);

  const form = reactive({
    amount: '',
    type: 'EXPENSE',
    categoryId: '',
    transactionDate: new Date().toISOString().split('T')[0],
    description: '',
  });

  const isEditing = computed(() => !!store.editingTransaction);

  const availableCategories = computed(() => {
    const categories = store.categories || [];
    if (!form.type) return categories;
    return categories.filter((c) => c.type === form.type);
  });

  watch(
    () => store.isTransactionModalOpen,
    (isOpen) => {
      if (isOpen) {
        formError.value = '';
        if (store.editingTransaction) {
          const tx = store.editingTransaction;
          form.amount = tx.amount;
          form.type = tx.type;
          form.categoryId = tx.categoryId;
          form.transactionDate = tx.transactionDate;
          form.description = tx.description || '';
        } else {
          form.amount = '';
          form.type = 'EXPENSE';
          const cats = store.categories || [];
          form.categoryId = cats.length > 0 ? cats[0].id : '';
          form.transactionDate = new Date().toISOString().split('T')[0];
          form.description = '';
        }
      }
    }
  );

  const handleSubmit = async () => {
    formError.value = '';
    if (!form.amount || Number(form.amount) <= 0) {
      formError.value = 'Amount must be greater than 0';
      return;
    }
    if (!form.categoryId) {
      formError.value = 'Please select a valid category';
      return;
    }

    submitting.value = true;
    try {
      if (store.saveTransaction) {
        await store.saveTransaction({
          amount: Number(form.amount),
          type: form.type,
          categoryId: Number(form.categoryId),
          transactionDate: form.transactionDate,
          description: form.description,
        });
      }
    } catch (err) {
      formError.value = err.message || 'Failed to save transaction';
    } finally {
      submitting.value = false;
    }
  };

  return {
    form,
    formError,
    submitting,
    isEditing,
    availableCategories,
    handleSubmit,
  };
};

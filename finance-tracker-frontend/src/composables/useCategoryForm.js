import { ref, reactive, computed, watch } from 'vue';

export const useCategoryForm = (store) => {
  const formError = ref('');
  const submitting = ref(false);

  const form = reactive({ name: '', type: 'EXPENSE' });

  const isEditing = computed(() => !!store.editingCategory);

  watch(
    () => store.isCategoryModalOpen,
    (isOpen) => {
      if (isOpen) {
        formError.value = '';
        if (store.editingCategory) {
          form.name = store.editingCategory.name;
          form.type = store.editingCategory.type;
        } else {
          form.name = '';
          form.type = 'EXPENSE';
        }
      }
    }
  );

  const handleSubmit = async () => {
    formError.value = '';
    if (!form.name.trim()) {
      formError.value = 'Category name must not be empty';
      return;
    }

    submitting.value = true;
    try {
      if (store.saveCategory) {
        await store.saveCategory({ name: form.name.trim(), type: form.type });
      }
    } catch (err) {
      formError.value = err.message || 'Failed to save category';
    } finally {
      submitting.value = false;
    }
  };

  return {
    form,
    formError,
    submitting,
    isEditing,
    handleSubmit,
  };
};

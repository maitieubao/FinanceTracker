<template>
  <BaseModal
    :isOpen="!!store.isCategoryModalOpen"
    :title="isEditing ? 'Edit Category' : 'Create Category'"
    icon="category"
    maxWidth="max-w-md"
    @close="store.closeCategoryModal?.()"
  >
    <form id="categoryForm" @submit.prevent="handleSubmit" class="space-y-4">
      <BaseAlert v-if="formError" variant="error" :message="formError" />
      <BaseInput v-model="form.name" label="Category Name" placeholder="E.g., Groceries..." required />
      <TransactionTypeToggle v-model="form.type" label="Category Type" required />
    </form>

    <template #footer>
      <BaseButton variant="outline" @click="store.closeCategoryModal?.()">Cancel</BaseButton>
      <BaseButton
        type="submit"
        form="categoryForm"
        variant="primary"
        :loading="submitting"
        :icon="isEditing ? 'save' : 'add'"
      >
        {{ isEditing ? 'Update Category' : 'Save Category' }}
      </BaseButton>
    </template>
  </BaseModal>
</template>

<script setup>
import { useFinanceStore } from '../../stores/financeStore';
import { useCategoryForm } from '../../composables/useCategoryForm';

import BaseModal from '../common/BaseModal.vue';
import BaseInput from '../common/BaseInput.vue';
import BaseButton from '../common/BaseButton.vue';
import BaseAlert from '../common/BaseAlert.vue';
import TransactionTypeToggle from '../transactions/TransactionTypeToggle.vue';

const store = useFinanceStore();
const {
  form,
  formError,
  submitting,
  isEditing,
  handleSubmit,
} = useCategoryForm(store);
</script>

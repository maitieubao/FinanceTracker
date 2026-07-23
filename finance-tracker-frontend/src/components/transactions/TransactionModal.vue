<template>
  <BaseModal
    :isOpen="!!store.isTransactionModalOpen"
    :title="isEditing ? 'Edit Transaction' : 'Record New Transaction'"
    :icon="isEditing ? 'edit_note' : 'add_circle'"
    @close="store.closeTransactionModal?.()"
  >
    <form id="transactionForm" @submit.prevent="handleSubmit" class="space-y-4">
      <BaseAlert v-if="formError" variant="error" :message="formError" />
      <TransactionTypeToggle v-model="form.type" required />
      <BaseInput
        v-model="form.amount"
        type="number"
        step="1000"
        min="1000"
        label="Amount"
        placeholder="50000"
        suffixText="VND"
        required
      />
      <BaseSelect
        v-model="form.categoryId"
        label="Category"
        placeholder="Select category..."
        :options="availableCategories"
        required
      />
      <BaseInput
        v-model="form.transactionDate"
        type="date"
        label="Date"
        required
      />
      <BaseTextarea
        v-model="form.description"
        label="Description / Note"
        placeholder="E.g., Monthly groceries..."
        rows="3"
      />
    </form>

    <template #footer>
      <BaseButton variant="outline" @click="store.closeTransactionModal?.()">Cancel</BaseButton>
      <BaseButton
        type="submit"
        form="transactionForm"
        variant="primary"
        :loading="submitting"
        :icon="isEditing ? 'save' : 'add'"
      >
        {{ isEditing ? 'Update Entry' : 'Save Transaction' }}
      </BaseButton>
    </template>
  </BaseModal>
</template>

<script setup>
import { useFinanceStore } from '../../stores/financeStore';
import { useTransactionForm } from '../../composables/useTransactionForm';

import BaseModal from '../common/BaseModal.vue';
import BaseInput from '../common/BaseInput.vue';
import BaseSelect from '../common/BaseSelect.vue';
import BaseTextarea from '../common/BaseTextarea.vue';
import BaseButton from '../common/BaseButton.vue';
import BaseAlert from '../common/BaseAlert.vue';
import TransactionTypeToggle from './TransactionTypeToggle.vue';

const store = useFinanceStore();
const {
  form,
  formError,
  submitting,
  isEditing,
  availableCategories,
  handleSubmit,
} = useTransactionForm(store);
</script>

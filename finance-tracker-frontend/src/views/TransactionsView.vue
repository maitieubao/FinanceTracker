<template>
  <div class="space-y-6">
    <div class="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
      <div>
        <h1 class="text-2xl font-extrabold text-white tracking-tight flex items-center gap-2">
          Transactions Management
        </h1>
        <p class="text-xs text-on-muted mt-0.5">
          View, filter, edit and manage all recorded financial entries.
        </p>
      </div>

      <BaseButton variant="primary" icon="add_circle" class="pb-2" @click="store.openAddTransactionModal?.()">
        Add Transaction
      </BaseButton>
    </div>

    <BaseCard padding="p-4">
      <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-5 gap-3">
        <BaseInput
          v-model="localFilters.query"
          label="Search"
          placeholder="Description"
          prefixIcon=""
          @update:modelValue="applyFilters"
        />

        <BaseSelect
          v-model="localFilters.type"
          label="Type"
          :options="typeOptions"
          @update:modelValue="applyFilters"
        />

        <BaseSelect
          v-model="localFilters.categoryId"
          label="Category"
          placeholder="All Categories"
          :options="categoryOptions"
          @update:modelValue="applyFilters"
        />

        <BaseInput
          v-model="localFilters.from"
          type="date"
          label="From Date"
          @update:modelValue="applyFilters"
        />

        <BaseInput
          v-model="localFilters.to"
          type="date"
          label="To Date"
          @update:modelValue="applyFilters"
        />
      </div>

      <div class="flex items-center justify-between border-t border-outline/50 pt-3 mt-4 text-xs">
        <span class="text-on-muted">
          Showing <strong class="text-white">{{ paginatedTransactions.length }}</strong> of <strong class="text-white">{{ totalItems }}</strong> transactions
        </span>
        <BaseButton variant="ghost" size="sm" icon="restart_alt" @click="resetLocalFilters">
          Reset Filters
        </BaseButton>
      </div>
    </BaseCard>

    <BaseCard padding="p-0">
      <div class="overflow-x-auto">
        <table class="w-full text-left border-collapse">
          <thead>
            <tr class="bg-surface-low border-b border-outline text-[11px] text-on-muted uppercase tracking-wider">
              <th class="py-3 px-4">Date</th>
              <th class="py-3 px-4">Type</th>
              <th class="py-3 px-4">Category</th>
              <th class="py-3 px-4">Description</th>
              <th class="py-3 px-4 text-right">Amount</th>
              <th class="py-3 px-4 text-right">Actions</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-outline/30 text-xs">
            <TransactionTableRow
              v-for="tx in paginatedTransactions"
              :key="tx.id"
              :transaction="tx"
              :categoryName="tx.categoryName || 'Uncategorized'"
              @edit="store.openEditTransactionModal?.($event)"
              @delete="confirmDelete"
            />

            <tr v-if="paginatedTransactions.length === 0">
              <td colspan="6" class="py-12 text-center text-on-muted">
                <span class="material-symbols-outlined text-4xl block mb-2 opacity-50">search_off</span>
                <p>No transactions match your current filters.</p>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <template v-if="totalPages > 1" #footer>
        <div class="flex items-center justify-between text-xs">
          <span class="text-on-muted">
            Page {{ currentPage }} of {{ totalPages }}
          </span>
          <div class="flex items-center space-x-2">
            <BaseButton variant="outline" size="sm" :disabled="currentPage === 1" @click="currentPage--">
              Previous
            </BaseButton>
            <BaseButton variant="outline" size="sm" :disabled="currentPage === totalPages" @click="currentPage++">
              Next
            </BaseButton>
          </div>
        </div>
      </template>
    </BaseCard>
  </div>
</template>

<script setup>
import { useFinanceStore } from '../stores/financeStore';
import { useTransactionsCalc } from '../composables/useTransactionsCalc';

import BaseButton from '../components/common/BaseButton.vue';
import BaseInput from '../components/common/BaseInput.vue';
import BaseSelect from '../components/common/BaseSelect.vue';
import BaseCard from '../components/common/BaseCard.vue';
import TransactionTableRow from '../components/transactions/TransactionTableRow.vue';

const store = useFinanceStore();
const {
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
} = useTransactionsCalc(store);
</script>

<template>
  <div class="space-y-6">
    <div class="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
      <div>
        <h1 class="text-2xl font-extrabold text-white tracking-tight flex items-center gap-2">
          Categories Management
        </h1>
        <p class="text-xs text-on-muted mt-0.5">
          Organize income sources and expense categories.
        </p>
      </div>

      <BaseButton variant="primary" icon="add_circle" @click="store.openAddCategoryModal?.()">
        Create Category
      </BaseButton>
    </div>

    <div class="flex items-center space-x-2 border-b border-outline pb-2 text-xs">
      <BaseButton
        :variant="activeTab === 'ALL' ? 'secondary' : 'ghost'"
        size="sm"
        @click="activeTab = 'ALL'"
      >
        All Categories ({{ (store.categories || []).length }})
      </BaseButton>

      <BaseButton
        :variant="activeTab === 'INCOME' ? 'income' : 'ghost'"
        size="sm"
        @click="activeTab = 'INCOME'"
      >
        Income Categories ({{ (store.incomeCategories || []).length }})
      </BaseButton>

      <BaseButton
        :variant="activeTab === 'EXPENSE' ? 'expense' : 'ghost'"
        size="sm"
        @click="activeTab = 'EXPENSE'"
      >
        Expense Categories ({{ (store.expenseCategories || []).length }})
      </BaseButton>
    </div>

    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
      <CategoryCard
        v-for="cat in filteredCategories"
        :key="cat.id"
        :category="cat"
        @edit="store.openEditCategoryModal?.($event)"
        @delete="confirmDeleteCategory"
      />
    </div>

    <p v-if="filteredCategories.length === 0" class="text-center py-12 text-xs text-on-muted">
      No categories available. Click "Create Category" to add one.
    </p>

    <CategoryModal />
  </div>
</template>

<script setup>
import { useFinanceStore } from '../stores/financeStore';
import { useCategoriesCalc } from '../composables/useCategoriesCalc';

import BaseButton from '../components/common/BaseButton.vue';
import CategoryCard from '../components/categories/CategoryCard.vue';
import CategoryModal from '../components/categories/CategoryModal.vue';

const store = useFinanceStore();
const { activeTab, filteredCategories, confirmDeleteCategory } = useCategoriesCalc(store);
</script>

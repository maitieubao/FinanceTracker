<template>
  <header class="h-16 border-b border-outline bg-background/80 backdrop-blur-md sticky top-0 z-10 flex items-center justify-between px-8">
    <div class="flex items-center space-x-4">
      <h2 class="text-xl font-bold text-white tracking-tight flex items-center gap-2">
        {{ currentRouteTitle }}
      </h2>
      <span class="px-2.5 py-0.5 rounded-full text-xs font-mono bg-surface-high text-on-muted border border-outline">
        Personal Finance Tracker
      </span>
    </div>

    <div class="flex items-center space-x-4">
      <div class="w-64">
        <BaseInput
          v-model="searchQuery"
          placeholder="Search transactions..."
          prefixIcon="search"
          @update:modelValue="onSearch"
        />
      </div>

      <BaseButton
        variant="primary"
        icon="add"
        @click="store.openAddTransactionModal?.()"
      >
        New Entry
      </BaseButton>
    </div>
  </header>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRoute } from 'vue-router';
import { useFinanceStore } from '../../stores/financeStore';
import BaseButton from '../common/BaseButton.vue';
import BaseInput from '../common/BaseInput.vue';

const route = useRoute();
const store = useFinanceStore();

const searchQuery = ref('');
const currentRouteTitle = computed(() => route.meta.title || 'Dashboard');

const onSearch = () => {
  if (store.setFilters) {
    store.setFilters({ query: searchQuery.value });
  }
};
</script>

<template>
  <div class="min-h-screen bg-background text-on-surface flex">
    <AppSidebar />

    <div class="flex-1 ml-64 flex flex-col min-h-screen">
      <AppHeader />

      <main class="flex-1 p-8">
        <div v-if="store.error" class="mb-6 p-4 bg-expense/10 border border-expense/30 rounded-lg text-expense text-xs flex items-center justify-between">
          <div class="flex items-center space-x-2">
            <span class="material-symbols-outlined text-lg">error</span>
            <span>{{ store.error }}</span>
          </div>
          <BaseButton
            variant="ghost"
            size="sm"
            icon="close"
            @click="store.error = null"
          />
        </div>

        <router-view />
      </main>

      
      <footer class="py-4 px-8 border-t border-outline/50 text-xs text-on-muted flex items-center justify-between bg-surface-lowest">
        <span>Personal Finance Tracker</span>
        <span class="font-mono text-[11px]"></span>
      </footer>
    </div>

    <TransactionModal />
  </div>
</template>

<script setup>
import { onMounted } from 'vue';
import AppSidebar from './components/layout/AppSidebar.vue';
import AppHeader from './components/layout/AppHeader.vue';
import TransactionModal from './components/transactions/TransactionModal.vue';
import BaseButton from './components/common/BaseButton.vue';
import { useFinanceStore } from './stores/financeStore';

const store = useFinanceStore();

onMounted(() => {
  if (store.loadAll) store.loadAll();
});
</script>
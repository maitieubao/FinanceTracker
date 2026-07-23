<template>
  <aside class="w-64 fixed left-0 top-0 h-screen bg-surface-low border-r border-outline flex flex-col z-20 transition-all duration-300">
    <div class="px-6 py-6 flex items-center space-x-3 border-b border-outline/50">
      <div class="w-10 h-10 rounded-lg bg-primary/20 flex items-center justify-center border border-primary/40 text-primary-light shadow-lg shadow-primary/10">
        <span class="material-symbols-outlined text-2xl">finance</span>
      </div>
      <div>
        <h1 class="font-bold text-base tracking-tight text-white flex items-center gap-1.5">
          Luminal<span class="text-primary font-extrabold">Finance</span>
        </h1>
        <p class="text-xs text-on-muted">Personal Tracker</p>
      </div>
    </div>

    <nav class="flex-1 py-6 px-3 space-y-1">
      <router-link to="/" v-slot="{ isActive }">
        <div
          class="flex items-center px-4 py-3 rounded-md transition-all duration-200 group text-sm font-medium cursor-pointer"
          :class="isActive ? 'bg-surface-high text-primary font-semibold border-r-6 border-primary shadow-sm' : 'text-on-variant hover:bg-surface-high/60 hover:text-white'"
        >
          <span class="material-symbols-outlined mr-3 text-xl">dashboard</span>
          <span>Dashboard</span>
        </div>
      </router-link>

      <router-link to="/transactions" v-slot="{ isActive }">
        <div
          class="flex items-center px-4 py-3 rounded-md transition-all duration-200 group text-sm font-medium cursor-pointer"
          :class="isActive ? 'bg-surface-high text-primary font-semibold border-r-6 border-primary shadow-sm' : 'text-on-variant hover:bg-surface-high/60 hover:text-white'"
        >
          <span class="material-symbols-outlined mr-3 text-xl">receipt_long</span>
          <span>Transactions</span>
        </div>
      </router-link>

      <router-link to="/categories" v-slot="{ isActive }">
        <div
          class="flex items-center px-4 py-3 rounded-md transition-all duration-200 group text-sm font-medium cursor-pointer"
          :class="isActive ? 'bg-surface-high text-primary font-semibold border-r-6 border-primary shadow-sm' : 'text-on-variant hover:bg-surface-high/60 hover:text-white'"
        >
          <span class="material-symbols-outlined mr-3 text-xl">category</span>
          <span>Categories</span>
        </div>
      </router-link>
    </nav>

    <div class="px-4 py-4 border-t border-outline/50">
      <BaseButton
        variant="primary"
        icon="add_circle"
        fullWidth
        @click="store.openAddTransactionModal?.()"
      >
        Add Transaction
      </BaseButton>
    </div>
  </aside>
</template>

<script setup>
import { useFinanceStore } from '../../stores/financeStore';
import BaseButton from '../common/BaseButton.vue';

const store = useFinanceStore();
</script>
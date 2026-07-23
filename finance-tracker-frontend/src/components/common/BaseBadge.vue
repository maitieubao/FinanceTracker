<template>
  <span
    :class="[
      'inline-flex items-center gap-1 font-semibold rounded-full px-2.5 py-0.5 text-[11px] tracking-wide uppercase',
      isIncome ? 'bg-income/10 text-income border border-income/30' : isExpense ? 'bg-expense/10 text-expense border border-expense/30' : 'bg-surface-high text-on-muted border border-outline'
    ]"
  >
    <span v-if="isIncome" class="material-symbols-outlined text-xs">arrow_downward</span>
    <span v-else-if="isExpense" class="material-symbols-outlined text-xs">arrow_upward</span>
    <span>{{ label || type }}</span>
  </span>
</template>

<script setup>
import { computed } from 'vue';

const props = defineProps({
  type: { type: String, default: 'INCOME' },
  label: { type: String, default: '' },
});

const isIncome = computed(() => props.type?.toUpperCase() === 'INCOME');
const isExpense = computed(() => props.type?.toUpperCase() === 'EXPENSE');
</script>

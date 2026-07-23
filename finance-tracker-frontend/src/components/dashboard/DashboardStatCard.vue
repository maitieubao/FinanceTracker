<template>
  <div class="bg-surface-card border border-outline rounded-xl p-5 shadow-lg flex items-center justify-between">
    <div>
      <p class="text-xs font-semibold uppercase tracking-wider  mb-1">{{ title }}</p>
      <h3 class="text-2xl font-extrabold font-helvetica tracking-normal text-white">{{ formattedValue }} VND</h3>
      <p v-if="subtitle" class="text-[11px] text-on-muted mt-1">{{ subtitle }}</p>
    </div>
    <!-- <div
      class="w-12 h-12 rounded-xl flex items-center justify-center text-xl shadow-inner"
      :class="colorClasses[color] || colorClasses.primary"
    >
      <span class="material-symbols-outlined">{{ icon }}</span>
    </div> -->
  </div>
</template>

<script setup>
import { computed } from 'vue';

const props = defineProps({
  title: { type: String, required: true },
  value: { type: [Number, String], default: 0 },
  icon: { type: String, default: '' },
  color: { type: String, default: 'primary' },
  subtitle: { type: String, default: '' },
});

const formattedValue = computed(() => {
  const num = Number(props.value) || 0;
  return num.toLocaleString('en-US', { minimumFractionDigits: 0, maximumFractionDigits: 3 });
});

const colorClasses = {
  primary: 'bg-primary/10 text-primary border border-primary/20',
  income: 'bg-income/10 text-income border border-income/20',
  expense: 'bg-expense/10 text-expense border border-expense/20',
  info: 'bg-blue-500/10 text-blue-400 border border-blue-500/20',
};
</script>
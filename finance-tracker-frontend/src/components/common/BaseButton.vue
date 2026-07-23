<template>
  <button
    :type="type"
    :disabled="disabled || loading"
    :class="[
      'inline-flex items-center justify-center font-semibold rounded-md transition-all focus:outline-none disabled:opacity-50 disabled:cursor-not-allowed cursor-pointer select-none',
      variantClasses[variant] || variantClasses.primary,
      sizeClasses[size] || sizeClasses.md,
      fullWidth ? 'w-full' : '',
    ]"
    @click="$emit('click', $event)"
  >
    <span v-if="loading" class="material-symbols-outlined animate-spin text-base pointer-events-none">progress_activity</span>
    <span v-else-if="icon" class="material-symbols-outlined text-base pointer-events-none">{{ icon }}</span>
    <slot></slot>
  </button>
</template>

<script setup>
defineProps({
  type: { type: String, default: 'button' },
  variant: { type: String, default: 'primary' },
  size: { type: String, default: 'md' },
  disabled: { type: Boolean, default: false },
  loading: { type: Boolean, default: false },
  icon: { type: String, default: '' },
  fullWidth: { type: Boolean, default: false },
});

defineEmits(['click']);

const variantClasses = {
  primary: 'bg-primary hover:bg-primary-container text-white shadow-md shadow-primary/20',
  secondary: 'bg-surface-high hover:bg-surface-highest text-white border border-outline',
  outline: 'border border-outline bg-transparent text-on-muted hover:text-white hover:bg-surface-high',
  ghost: 'bg-transparent text-on-muted hover:text-white hover:bg-surface-high/50',
  danger: 'bg-expense hover:bg-expense/80 text-white',
  income: 'bg-income/10 border border-income text-income font-bold',
  expense: 'bg-expense/10 border border-expense text-expense font-bold',
};

const sizeClasses = {
  sm: 'px-2.5 py-1.5 text-xs gap-1.5',
  md: 'px-4 py-2 text-xs gap-2',
  lg: 'px-5 py-2.5 text-sm gap-2',
};
</script>

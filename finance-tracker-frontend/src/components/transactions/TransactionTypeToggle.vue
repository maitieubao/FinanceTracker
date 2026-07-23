<template>
  <div>
    <label v-if="label" class="block text-xs font-semibold uppercase tracking-wider text-on-muted mb-2">
      {{ label }} <span v-if="required" class="text-expense">*</span>
    </label>
    <div class="grid grid-cols-2 gap-3">
      <BaseButton
        type="button"
        :variant="modelValue === 'INCOME' ? 'income' : 'outline'"
        icon="arrow_downward"
        fullWidth
        @click="$emit('update:modelValue', 'INCOME')"
      >
        INCOME
      </BaseButton>
      <BaseButton
        type="button"
        :variant="modelValue === 'EXPENSE' ? 'expense' : 'outline'"
        icon="arrow_upward"
        fullWidth
        @click="$emit('update:modelValue', 'EXPENSE')"
      >
        EXPENSE
      </BaseButton>
    </div>
  </div>
</template>

<script setup>
import BaseButton from '../common/BaseButton.vue';

defineProps({
  modelValue: { type: String, default: 'EXPENSE' },
  label: { type: String, default: 'Transaction Type' },
  required: { type: Boolean, default: false },
});

defineEmits(['update:modelValue']);
</script>

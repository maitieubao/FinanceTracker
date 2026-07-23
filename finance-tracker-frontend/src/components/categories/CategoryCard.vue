<template>
  <div class="p-4 bg-surface-card border border-outline rounded-lg flex items-center justify-between hover:border-outline-variant transition-all shadow-sm">
    <div class="flex items-center space-x-3">
      <div
        class="w-10 h-10 rounded-lg flex items-center justify-center font-bold text-sm"
        :class="category.type === 'INCOME' ? 'bg-income/10 text-income' : 'bg-expense/10 text-expense'"
      >
        <span class="material-symbols-outlined">
          {{ category.type === 'INCOME' ? 'payments' : 'shopping_bag' }}
        </span>
      </div>
      <div>
        <h4 class="text-sm font-semibold text-white">{{ category.name }}</h4>
        <BaseBadge :type="category.type" class="mt-1" />
      </div>
    </div>
    <div class="flex items-center space-x-1">
      <BaseButton variant="ghost" size="sm" icon="edit" @click="$emit('edit', category)" />
      <BaseButton variant="ghost" size="sm" icon="delete" class="hover:text-expense" @click="$emit('delete', category.id)" />
    </div>
  </div>
</template>

<script setup>
import BaseBadge from '../common/BaseBadge.vue';
import BaseButton from '../common/BaseButton.vue';

defineProps({
  category: { type: Object, required: true },
});

defineEmits(['edit', 'delete']);
</script>

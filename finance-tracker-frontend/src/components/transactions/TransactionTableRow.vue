<template>
  <tr class="border-b border-outline/50 hover:bg-surface-low/50 transition-colors text-xs">
    <td class="px-4 py-3 font-helvetica text-on-muted">{{ transaction.transactionDate }}</td>
    <td class="px-4 py-3"><BaseBadge :type="transaction.type" /></td>
    <td class="px-4 py-3 font-medium text-white">{{ categoryName }}</td>
    <td class="px-4 py-3 text-on-muted max-w-xs truncate">{{ transaction.description || '-' }}</td>
    <td
      class="px-4 py-3 text-right font-mono font-bold"
      :class="transaction.type === 'INCOME' ? 'text-income' : 'text-expense'"
    >
      {{ transaction.type === 'INCOME' ? '+' : '-' }}{{ Number(transaction.amount || 0).toLocaleString('en-US', { minimumFractionDigits: 0, maximumFractionDigits: 3 }) }} VND
    </td>
    <td class="px-4 py-3 text-right">
      <div class="flex items-center justify-end space-x-1">
        <BaseButton variant="ghost" size="sm" icon="edit" @click="$emit('edit', transaction)" />
        <BaseButton variant="ghost" size="sm" icon="delete" class="hover:text-expense" @click="$emit('delete', transaction.id)" />
      </div>
    </td>
  </tr>
</template>

<script setup>
import BaseBadge from '../common/BaseBadge.vue';
import BaseButton from '../common/BaseButton.vue';

defineProps({
  transaction: { type: Object, required: true },
  categoryName: { type: String, default: 'Uncategorized' },
});

defineEmits(['edit', 'delete']);
</script>

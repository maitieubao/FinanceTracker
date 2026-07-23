<template>
  <div v-if="message || $slots.default" class="p-3 border rounded-md text-xs flex items-center gap-2 bg-expense/10 border-expense/30 text-expense">
    <span class="material-symbols-outlined text-base">error</span>
    <span><slot>{{ message }}</slot></span>
  </div>
</template>

<script setup>
defineProps({
  message: { type: String, default: '' },
});
</script>

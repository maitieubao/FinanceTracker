<template>
  <div class="bg-surface-card border border-outline rounded-xl shadow-lg overflow-hidden">
    <div v-if="title || $slots.header" class="px-5 py-4 border-b border-outline flex items-center justify-between bg-surface-low">
      <slot name="header">
        <h3 class="text-sm font-bold text-white flex items-center gap-2">
          <span v-if="icon" class="material-symbols-outlined text-primary text-lg">{{ icon }}</span>
          <span>{{ title }}</span>
        </h3>
      </slot>
    </div>
    <div :class="padding">
      <slot></slot>
    </div>
    <div v-if="$slots.footer" class="px-5 py-3 border-t border-outline bg-surface-low/50">
      <slot name="footer"></slot>
    </div>
  </div>
</template>

<script setup>
defineProps({
  title: { type: String, default: '' },
  icon: { type: String, default: '' },
  padding: { type: String, default: 'p-5' },
});
</script>

<template>
  <Teleport to="body">
    <div
      v-if="isOpen"
      class="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/70 backdrop-blur-sm transition-opacity"
      @click.self="$emit('close')"
    >
      <div :class="['bg-surface-card border border-outline rounded-lg w-full shadow-2xl overflow-hidden', maxWidth]">
        <div class="px-6 py-4 border-b border-outline flex items-center justify-between bg-surface-low">
          <h3 class="text-base font-bold text-white flex items-center gap-2">
            <span v-if="icon" class="material-symbols-outlined text-primary text-xl">{{ icon }}</span>
            <slot name="title"><span>{{ title }}</span></slot>
          </h3>
          <BaseButton
            variant="ghost"
            size="sm"
            icon="close"
            @click="$emit('close')"
          />
        </div>

        <div class="p-6">
          <slot></slot>
        </div>

        <div v-if="$slots.footer" class="px-6 py-4 border-t border-outline flex items-center justify-end space-x-3 bg-surface-low/50">
          <slot name="footer"></slot>
        </div>
      </div>
    </div>
  </Teleport>
</template>

<script setup>
import BaseButton from './BaseButton.vue';

defineProps({
  isOpen: { type: Boolean, default: false },
  title: { type: String, default: '' },
  icon: { type: String, default: '' },
  maxWidth: { type: String, default: 'max-w-lg' },
});

defineEmits(['close']);
</script>

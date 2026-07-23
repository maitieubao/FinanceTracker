<template>
  <div class="space-y-1">
    <label v-if="label" class="block text-xs font-semibold uppercase tracking-wider text-on-muted">
      {{ label }} <span v-if="required" class="text-expense">*</span>
    </label>
    <select
      :value="modelValue"
      :required="required"
      :disabled="disabled"
      class="w-full bg-background border border-outline rounded-md px-3 py-2 text-sm text-white focus:outline-none focus:border-primary transition-colors"
      @change="$emit('update:modelValue', $event.target.value)"
    >
      <option v-if="placeholder" value="" disabled>{{ placeholder }}</option>
      <option v-for="opt in normalizedOptions" :key="opt.value" :value="opt.value">
        {{ opt.label }}
      </option>
    </select>
  </div>
</template>

<script setup>
import { computed } from 'vue';

const props = defineProps({
  modelValue: { type: [String, Number], default: '' },
  label: { type: String, default: '' },
  required: { type: Boolean, default: false },
  disabled: { type: Boolean, default: false },
  placeholder: { type: String, default: '' },
  options: { type: Array, default: () => [] },
});

defineEmits(['update:modelValue']);

const normalizedOptions = computed(() => {
  return props.options.map((opt) => {
    if (typeof opt === 'object' && opt !== null) {
      return { value: opt.value ?? opt.id, label: opt.label ?? opt.name ?? opt.id };
    }
    return { value: opt, label: opt };
  });
});
</script>

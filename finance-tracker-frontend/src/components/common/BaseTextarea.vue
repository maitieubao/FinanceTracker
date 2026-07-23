<template>
  <div class="space-y-1">
    <label v-if="label" class="block text-xs font-semibold uppercase tracking-wider text-on-muted">
      {{ label }} <span v-if="required" class="text-expense">*</span>
    </label>
    <textarea
      :value="modelValue"
      :placeholder="placeholder"
      :required="required"
      :disabled="disabled"
      :rows="rows"
      class="w-full bg-background border border-outline rounded-md px-3 py-2 text-sm text-white focus:outline-none focus:border-primary transition-colors"
      @input="$emit('update:modelValue', $event.target.value)"
    ></textarea>
  </div>
</template>

<script setup>
defineProps({
  modelValue: { type: String, default: '' },
  label: { type: String, default: '' },
  placeholder: { type: String, default: '' },
  required: { type: Boolean, default: false },
  disabled: { type: Boolean, default: false },
  rows: { type: [String, Number], default: 3 },
});

defineEmits(['update:modelValue']);
</script>

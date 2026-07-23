<template>
  <div class="space-y-1">
    <label v-if="label" class="block text-xs font-semibold uppercase tracking-wider text-on-muted">
      {{ label }} <span v-if="required" class="text-expense">*</span>
    </label>
    <div class="relative">
      <span 
        v-if="prefixIcon" 
        class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-muted text-base leading-none pointer-events-none select-none"
        style="font-variation-settings: 'FILL' 0, 'wght' 400, 'GRAD' 0, 'opsz' 20;"
      >
        {{ prefixIcon }}
      </span>
      <span 
        v-else-if="prefixText" 
        class="absolute left-3 top-1/2 -translate-y-1/2 text-on-muted text-sm leading-none pointer-events-none select-none"
      >
        {{ prefixText }}
      </span>
      <input
        :type="type"
        :value="modelValue"
        :placeholder="placeholder"
        :required="required"
        :disabled="disabled"
        :step="step"
        :min="min"
        :max="max"
        :class="[
          'w-full bg-background border rounded-md py-2 text-sm text-white focus:outline-none transition-colors',
          prefixIcon || prefixText ? 'pl-10' : 'pl-3',
          suffixIcon || suffixText ? 'pr-12' : 'pr-3',
          error ? 'border-expense focus:border-expense' : 'border-outline focus:border-primary',
          disabled ? 'opacity-50 cursor-not-allowed' : '',
        ]"
        @input="$emit('update:modelValue', $event.target.value)"
      />
      <span 
        v-if="suffixIcon" 
        class="material-symbols-outlined absolute right-3 top-1/2 -translate-y-1/2 text-on-muted text-base leading-none pointer-events-none select-none"
        style="font-variation-settings: 'FILL' 0, 'wght' 400, 'GRAD' 0, 'opsz' 20;"
      >
        {{ suffixIcon }}
      </span>
      <span 
        v-else-if="suffixText" 
        class="absolute right-3 top-1/2 -translate-y-1/2 text-on-muted text-xs font-semibold leading-none pointer-events-none select-none uppercase"
      >
        {{ suffixText }}
      </span>
    </div>
    <p v-if="error" class="text-[11px] text-expense">{{ error }}</p>
    <p v-else-if="helpText" class="text-[11px] text-on-muted">{{ helpText }}</p>
  </div>
</template>

<script setup>
defineProps({
  modelValue: { type: [String, Number], default: '' },
  type: { type: String, default: 'text' },
  label: { type: String, default: '' },
  placeholder: { type: String, default: '' },
  required: { type: Boolean, default: false },
  disabled: { type: Boolean, default: false },
  prefixIcon: { type: String, default: '' },
  prefixText: { type: String, default: '' },
  suffixIcon: { type: String, default: '' },
  suffixText: { type: String, default: '' },
  error: { type: String, default: '' },
  helpText: { type: String, default: '' },
  step: { type: [String, Number], default: undefined },
  min: { type: [String, Number], default: undefined },
  max: { type: [String, Number], default: undefined },
});

defineEmits(['update:modelValue']);
</script>

<style>
  /* 1. Ẩn icon lịch mặc định của trình duyệt */
input[type="date"]::-webkit-calendar-picker-indicator {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  width: 100%;
  height: 100%;
  opacity: 0;
  cursor: pointer;
}

/* 2. Đảm bảo ô input có position relative để chứa icon tùy chỉnh */
input[type="date"] {
  position: relative;
}
</style>
<template>
  <div class="bg-surface-card border border-outline p-4 md:p-5 rounded-xl space-y-4 shadow-sm">
    <!-- Header row: Presets & Controls -->
    <div class="flex flex-col lg:flex-row lg:items-center justify-between gap-4">
      <!-- Quick Date Presets -->
      <div class="flex items-center flex-wrap gap-1.5">
        <span class="text-xs font-semibold uppercase tracking-wider text-on-muted mr-2 flex items-center gap-1">
          <span class="material-symbols-outlined text-sm text-primary">filter_alt</span>
          Thời gian:
        </span>
        <button
          v-for="preset in presets"
          :key="preset.key"
          type="button"
          class="px-3 py-1.5 rounded-lg text-xs font-medium transition-all duration-200 cursor-pointer flex items-center gap-1"
          :class="
            selectedPreset === preset.key
              ? 'bg-primary text-background font-bold shadow-sm shadow-primary/20 scale-[1.02]'
              : 'bg-surface-high/60 text-on-surface hover:bg-surface-high hover:text-white border border-outline/40'
          "
          @click="selectPreset(preset.key)"
        >
          <span>{{ preset.label }}</span>
        </button>
      </div>

      <!-- Action Buttons -->
      <div class="flex items-center space-x-2">
        <BaseButton
          v-if="hasActiveFilters"
          variant="ghost"
          size="sm"
          icon="restart_alt"
          class="text-xs text-on-muted hover:text-expense"
          @click="resetAllFilters"
        >
          Xóa bộ lọc
        </BaseButton>

        <BaseButton
          variant="secondary"
          size="sm"
          icon="refresh"
          :loading="loading"
          @click="$emit('sync')"
        >
          Đồng bộ
        </BaseButton>
      </div>
    </div>

    <!-- Secondary row: Filters (Date Inputs, Type & Category) -->
    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-3 pt-3 border-t border-outline/40">
      <!-- From Date -->
      <div>
        <label class="block text-[11px] font-semibold uppercase tracking-wider text-on-muted mb-1">
          Từ ngày
        </label>
        <input
          :value="filters.from"
          type="date"
          class="w-full bg-background border border-outline rounded-lg px-3 py-1.5 text-xs text-white focus:outline-none focus:border-primary transition-colors"
          @input="onDateInput('from', $event.target.value)"
        />
      </div>

      <!-- To Date -->
      <div>
        <label class="block text-[11px] font-semibold uppercase tracking-wider text-on-muted mb-1">
          Đến ngày
        </label>
        <input
          :value="filters.to"
          type="date"
          class="w-full bg-background border border-outline rounded-lg px-3 py-1.5 text-xs text-white focus:outline-none focus:border-primary transition-colors"
          @input="onDateInput('to', $event.target.value)"
        />
      </div>

      <!-- Transaction Type -->
      <div>
        <label class="block text-[11px] font-semibold uppercase tracking-wider text-on-muted mb-1">
          Loại giao dịch
        </label>
        <select
          :value="filters.type"
          class="w-full bg-background border border-outline rounded-lg px-3 py-1.5 text-xs text-white focus:outline-none focus:border-primary transition-colors"
          @change="onFilterChange('type', $event.target.value)"
        >
          <option value="">Tất cả loại</option>
          <option value="INCOME">Thu nhập (+)</option>
          <option value="EXPENSE">Chi tiêu (-)</option>
        </select>
      </div>

      <!-- Category -->
      <div>
        <label class="block text-[11px] font-semibold uppercase tracking-wider text-on-muted mb-1">
          Danh mục
        </label>
        <select
          :value="filters.categoryId"
          class="w-full bg-background border border-outline rounded-lg px-3 py-1.5 text-xs text-white focus:outline-none focus:border-primary transition-colors"
          @change="onFilterChange('categoryId', $event.target.value)"
        >
          <option value="">Tất cả danh mục</option>
          <option v-for="cat in categories" :key="cat.id" :value="cat.id">
            {{ cat.name }} ({{ cat.type === 'INCOME' ? 'Thu' : 'Chi' }})
          </option>
        </select>
      </div>
    </div>

    <!-- Active Filters Summary Badge Bar -->
    <div v-if="hasActiveFilters" class="flex items-center flex-wrap gap-2 pt-2">
      <span class="text-[11px] text-on-muted font-medium">Đang lọc theo:</span>
      
      <span v-if="selectedPresetLabel" class="inline-flex items-center gap-1 px-2.5 py-0.5 rounded-full text-[11px] font-medium bg-primary/10 text-primary border border-primary/20">
        <span class="material-symbols-outlined text-[12px]">calendar_today</span>
        {{ selectedPresetLabel }}
      </span>

      <span v-if="filters.from || filters.to" class="inline-flex items-center gap-1 px-2.5 py-0.5 rounded-full text-[11px] font-medium bg-surface-high text-white border border-outline">
        <span>Khoảng: {{ filters.from || 'Đầu' }} &rarr; {{ filters.to || 'Nay' }}</span>
      </span>

      <span v-if="filters.type" class="inline-flex items-center gap-1 px-2.5 py-0.5 rounded-full text-[11px] font-medium" :class="filters.type === 'INCOME' ? 'bg-income/10 text-income border border-income/20' : 'bg-expense/10 text-expense border border-expense/20'">
        <span>{{ filters.type === 'INCOME' ? 'Thu nhập' : 'Chi tiêu' }}</span>
      </span>

      <span v-if="activeCategoryName" class="inline-flex items-center gap-1 px-2.5 py-0.5 rounded-full text-[11px] font-medium bg-info/10 text-info border border-info/20">
        <span class="material-symbols-outlined text-[12px]">category</span>
        {{ activeCategoryName }}
      </span>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue';
import BaseButton from '../common/BaseButton.vue';

const props = defineProps({
  filters: {
    type: Object,
    required: true,
  },
  selectedPreset: {
    type: String,
    default: 'thisMonth',
  },
  categories: {
    type: Array,
    default: () => [],
  },
  loading: {
    type: Boolean,
    default: false,
  },
});

const emit = defineEmits(['update:filters', 'select-preset', 'reset', 'sync']);

const presets = [
  { key: 'allTime', label: 'Tất cả' },
  { key: 'today', label: 'Hôm nay' },
  { key: '7days', label: '7 ngày qua' },
  { key: 'thisMonth', label: 'Tháng này' },
  { key: 'lastMonth', label: 'Tháng trước' },
  { key: 'custom', label: 'Tùy chỉnh' },
];

const selectedPresetLabel = computed(() => {
  const p = presets.find((item) => item.key === props.selectedPreset);
  return p ? p.label : '';
});

const activeCategoryName = computed(() => {
  if (!props.filters.categoryId) return '';
  const found = props.categories.find((c) => String(c.id) === String(props.filters.categoryId));
  return found ? found.name : '';
});

const hasActiveFilters = computed(() => {
  return (
    props.selectedPreset !== 'allTime' ||
    !!props.filters.from ||
    !!props.filters.to ||
    !!props.filters.type ||
    !!props.filters.categoryId
  );
});

const selectPreset = (presetKey) => {
  emit('select-preset', presetKey);
};

const onDateInput = (field, value) => {
  emit('update:filters', {
    ...props.filters,
    [field]: value,
  });
  if (props.selectedPreset !== 'custom') {
    emit('select-preset', 'custom');
  }
};

const onFilterChange = (field, value) => {
  emit('update:filters', {
    ...props.filters,
    [field]: value,
  });
};

const resetAllFilters = () => {
  emit('reset');
};
</script>

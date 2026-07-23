<template>
  <div class="space-y-6">
    <!-- Top Title & Main Actions Banner -->
    <div class="flex flex-col md:flex-row md:items-center justify-between gap-4 bg-surface-card border border-outline p-6 rounded-xl relative overflow-hidden">
      <div class="absolute -right-10 -bottom-10 w-48 h-48 bg-primary/10 rounded-full blur-3xl pointer-events-none"></div>
      <div>
        <h1 class="text-2xl font-extrabold text-white tracking-tight flex items-center gap-2">
          Tổng quan tài chính
          <span class="text-xs font-normal text-on-muted px-2.5 py-0.5 rounded-full bg-surface-high border border-outline">
            Dashboard
          </span>
        </h1>
        <p class="text-xs text-on-muted mt-1">
          Theo dõi các dòng thu nhập, kiểm soát chi tiêu và phân tích sức khỏe tài chính theo thời gian thực.
        </p>
      </div>

      <div class="flex items-center space-x-3">
        <BaseButton variant="primary" icon="add" @click="store.openAddTransactionModal?.()">
          Thêm giao dịch
        </BaseButton>
      </div>
    </div>

    <!-- Enhanced Dashboard Filter Bar -->
    <DashboardFilterBar
      :filters="dashboardFilter"
      :selected-preset="selectedPreset"
      :categories="store.categories || []"
      :loading="!!store.loading"
      @update:filters="updateFilters"
      @select-preset="selectPreset"
      @reset="resetFilters"
      @sync="store.loadAll?.()"
    />

    <!-- Summary Stat Cards -->
    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
      <DashboardStatCard
        title="Tổng số dư ròng"
        :value="summary.balance"
        color="primary"
        subtitle="Hiệu số Thu - Chi"
      />

      <DashboardStatCard
        title="Tổng Thu Nhập"
        :value="summary.totalIncome"
        color="income"
        :subtitle="`${incomeCategories.length} danh mục thu nhập`"
      />

      <DashboardStatCard
        title="Tổng Chi Tiêu"
        :value="summary.totalExpense"
        color="expense"
        :subtitle="`${expenseCategories.length} danh mục chi tiêu`"
      />
    </div>

    <!-- Charts and Breakdown -->
    <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
      <BaseCard title="Phân bổ dòng tiền" icon="insights" padding="p-6" class="lg:col-span-1 flex flex-col justify-between">
        <div class="space-y-4">
          <div>
            <div class="flex justify-between text-xs font-medium mb-1">
              <span class="text-income flex items-center gap-1">
                <span class="w-2 h-2 rounded-full bg-income inline-block"></span> Thu nhập
              </span>
              <span class="font-mono text-white">{{ formatNumber(summary.totalIncome) }} VND</span>
            </div>
            <div class="w-full bg-surface-lowest h-3 rounded-full overflow-hidden">
              <div
                class="bg-income h-full rounded-full transition-all duration-500"
                :style="{ width: `${incomePercentage}%` }"
              ></div>
            </div>
          </div>

          <div>
            <div class="flex justify-between text-xs font-medium mb-1">
              <span class="text-expense flex items-center gap-1">
                <span class="w-2 h-2 rounded-full bg-expense inline-block"></span> Chi tiêu
              </span>
              <span class="font-mono text-white">{{ formatNumber(summary.totalExpense) }} VND</span>
            </div>
            <div class="w-full bg-surface-lowest h-3 rounded-full overflow-hidden">
              <div
                class="bg-expense h-full rounded-full transition-all duration-500"
                :style="{ width: `${expensePercentage}%` }"
              ></div>
            </div>
          </div>
        </div>

        <div class="border-t border-outline/50 pt-4 mt-6 space-y-3">
          <h4 class="text-xs font-semibold text-on-muted uppercase tracking-wider">Top Chi Tiêu Theo Danh Mục</h4>
          <div class="space-y-2">
            <div
              v-for="cat in topExpenseCategories"
              :key="cat.name"
              class="flex items-center justify-between text-xs"
            >
              <span class="text-on-surface truncate max-w-35">{{ cat.name }}</span>
              <span class="font-mono font-semibold text-white">{{ formatNumber(cat.total) }} VND</span>
            </div>
            <p v-if="topExpenseCategories.length === 0" class="text-xs text-on-muted">Chưa có giao dịch chi tiêu trong khoảng thời gian này.</p>
          </div>
        </div>
      </BaseCard>

      <!-- Recent Transactions Table -->
      <BaseCard padding="p-6" class="lg:col-span-2">
        <template #header>
          <div class="flex items-center justify-between w-full">
            <h3 class="text-sm font-bold text-white uppercase tracking-wider flex items-center gap-2">
              <span class="material-symbols-outlined text-primary text-lg">history</span>
              <span>Giao dịch gần đây</span>
            </h3>
            <router-link
              to="/transactions"
              class="text-xs text-primary hover:text-primary-light font-medium flex items-center gap-1 transition-colors"
            >
              <span>Xem tất cả</span>
              <span class="material-symbols-outlined text-sm">arrow_forward</span>
            </router-link>
          </div>
        </template>

        <div class="overflow-x-auto">
          <table class="w-full text-left border-collapse">
            <thead>
              <tr class="border-b border-outline/50 text-[11px] text-on-muted uppercase tracking-wider">
                <th class="py-2.5 px-3">Ngày</th>
                <th class="py-2.5 px-3">Danh mục</th>
                <th class="py-2.5 px-3">Mô tả</th>
                <th class="py-2.5 px-3 text-right">Số tiền</th>
                <th class="py-2.5 px-3 text-center">Thao tác</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-outline/30 text-xs">
              <tr
                v-for="tx in recentTransactions"
                :key="tx.id"
                class="hover:bg-surface-high/40 transition-colors group"
              >
                <td class="py-3 px-3 text-on-muted font-mono whitespace-nowrap">{{ tx.transactionDate }}</td>
                <td class="py-3 px-3 whitespace-nowrap">
                  <BaseBadge :type="tx.type" :label="tx.categoryName || 'Chưa phân loại'" />
                </td>
                <td class="py-3 px-3 text-white max-w-50 truncate">{{ tx.description || '—' }}</td>
                <td
                  class="py-3 px-3 text-right font-mono font-bold whitespace-nowrap"
                  :class="tx.type === 'INCOME' ? 'text-income' : 'text-expense'"
                >
                  {{ tx.type === 'INCOME' ? '+' : '-' }}{{ formatNumber(tx.amount) }} VND
                </td>
                <td class="py-3 px-3 text-center whitespace-nowrap">
                  <div class="flex items-center justify-center space-x-1 opacity-80 group-hover:opacity-100">
                    <BaseButton variant="ghost" size="sm" icon="edit" @click="store.openEditTransactionModal?.(tx)" />
                    <BaseButton variant="ghost" size="sm" icon="delete" class="hover:text-expense" @click="store.deleteTransaction?.(tx.id)" />
                  </div>
                </td>
              </tr>

              <tr v-if="recentTransactions.length === 0">
                <td colspan="5" class="py-8 text-center text-on-muted">
                  Không tìm thấy giao dịch phù hợp với bộ lọc.
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </BaseCard>
    </div>
  </div>
</template>

<script setup>
import { useFinanceStore } from '../stores/financeStore';
import { useDashboardCalc } from '../composables/useDashboardCalc';

import BaseButton from '../components/common/BaseButton.vue';
import BaseCard from '../components/common/BaseCard.vue';
import BaseBadge from '../components/common/BaseBadge.vue';
import DashboardStatCard from '../components/dashboard/DashboardStatCard.vue';
import DashboardFilterBar from '../components/dashboard/DashboardFilterBar.vue';

const store = useFinanceStore();
const {
  formatCurrency: formatNumber,
  summary,
  dashboardFilter,
  selectedPreset,
  updateFilters,
  selectPreset,
  resetFilters,
  recentTransactions,
  incomeCategories,
  expenseCategories,
  incomePercentage,
  expensePercentage,
  topExpenseCategories,
} = useDashboardCalc(store);
</script>
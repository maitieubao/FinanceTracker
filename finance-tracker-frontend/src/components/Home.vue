<script setup>
import { onMounted, ref } from 'vue'
import { getSummary, getTransactions } from '../service/transactionService'

const transactions = ref([])
const error = ref('')
const summary = ref(null)

async function loadData() {
  try {
    const [transactionResponse, summaryResponse] = await Promise.all([
      getTransactions(),
      getSummary(),
    ])

    transactions.value = transactionResponse.data
    summary.value = summaryResponse.data
  } catch (err) {
    error.value = err.response?.data?.message || 'Could not load data.'
  }
}

onMounted(loadData)
</script>

<template>
  <main class="">
    <p v-if="error">{{ error }}</p>
    <p v-if="summary">Total Income: ${{ summary.totalIncome }}</p>
    <p v-if="summary">Total Expenses: ${{ summary.totalExpenses }}</p>

    <ul>
      <li v-for></li>
    </ul>
  </main>
</template>

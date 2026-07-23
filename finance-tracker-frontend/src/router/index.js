import { createRouter, createWebHistory } from 'vue-router';
import DashboardView from '../views/DashboardView.vue';
import TransactionsView from '../views/TransactionsView.vue';
import CategoriesView from '../views/CategoriesView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'dashboard',
      component: DashboardView,
      meta: { title: 'Dashboard' }
    },
    {
      path: '/transactions',
      name: 'transactions',
      component: TransactionsView,
      meta: { title: 'Transactions' }
    },
    {
      path: '/categories',
      name: 'categories',
      component: CategoriesView,
      meta: { title: 'Categories' }
    },
    {
      path: '/:pathMatch(.*)*',
      redirect: '/'
    }
  ]
});

router.afterEach((to) => {
  document.title = to.meta.title ? `${to.meta.title} - Luminal Finance` : 'Personal Finance Tracker';
});

export default router;

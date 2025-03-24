import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/introduce',
      name: 'introduce',
      component: () => import('../views/IntroduceView.vue')
    },
    {
      path: '/free',
      name: 'free',
      component: () => import('../views/FreeView.vue')
    },
    {
      path: '/review',
      name: 'review',
      component: () => import('../views/ReviewView.vue')
    },
    {
      path: '/qna',
      name: 'qna',
      component: () => import('../views/QnaView.vue')
    },
    {
      path: '/notice',
      name: 'notice',
      component: () => import('../views/NoticeView.vue')
    }
  ]
})

export default router

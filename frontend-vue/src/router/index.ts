import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import NoticeView from '../views/posts/NoticeView.vue'
import FreeView from '../views/posts/FreeView.vue'
import ReviewView from '../views/posts/ReviewView.vue'
import QnaView from '../views/posts/QnaView.vue'
import IntroduceView from '../views/posts/IntroduceView.vue'
import LoginView from '../views/LoginView.vue'
import SignupView from '../views/SignupView.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/login',
    name: 'Login',
    component: LoginView
  },
  {
    path: '/signup',
    name: 'Signup',
    component: SignupView
  },
  {
    path: '/introduce',
    name: 'introduce',
    component: IntroduceView
  },
  {
    path: '/free',
    name: 'free',
    component: FreeView
  },
  {
    path: '/review',
    name: 'review',
    component: ReviewView
  },
  {
    path: '/qna',
    name: 'qna',
    component: QnaView
  },
  {
    path: '/notice',
    name: 'notice',
    component: NoticeView
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
  scrollBehavior() {
    return { top: 0 };
  },
})

export default router;

<template>
  <div class="v-app bg-white">
    <!-- 네비게이션 바 -->
    <div class="v-app-bar app white elevation-1">
      <div class="d-flex align-center justify-space-between w-100 nav-container">
        <!-- 좌측 로고 -->
        <div class="d-flex align-center logo-container">
          <router-link to="/" class="logo-wrapper">
            <img src="../assets/image/gamersicon.png" alt="Logo" style="max-height: 45px; max-width: 45px;"
              class="mr-2 logo-image">
            <div class="brand-name-container">
              <span class="brand-name-gamers">Gamer's</span>
              <span class="brand-name-ranc">RanC</span>
            </div>
          </router-link>
        </div>

        <!-- 중앙 검색창 -->
        <div class="search-container">
          <div class="search-wrapper">
            <span class="search-icon">🔍</span>
            <input v-model="searchQuery" placeholder="검색어를 입력하세요" class="search-input">
          </div>
        </div>

        <!-- 우측 로그인/가입 버튼 또는 사용자 정보 -->
        <div class="auth-buttons">
          <!-- 로그인 상태가 아닐 때 -->
          <template v-if="!isAuthenticated">
            <router-link to="/signup" class="v-btn signup-btn">회원가입</router-link>
            <router-link to="/login" class="v-btn login-btn">로그인</router-link>
          </template>

          <!-- 로그인 상태일 때 -->
          <template v-else>
            <div class="welcome-message">{{ userNickname }}님, 환영합니다!</div>
            <button @click="logout" class="v-btn logout-btn">로그아웃</button>
          </template>
        </div>
      </div>
    </div>

    <div class="page-layout">
      <!-- 좌측 사이드바 -->
      <div class="sidebar">
        <div class="sidebar-container">
          <ul class="sidebar-list">
            <li class="sidebar-item" v-for="(item, i) in categories" :key="i" :class="{ active: item.active }">
              <router-link :to="item.route" class="sidebar-item-content">
                <div class="sidebar-icon-container">
                  <component :is="item.iconComponent" class="sidebar-icon-component" v-if="item.iconComponent" />
                  <span class="sidebar-icon-fallback" v-else>{{ item.icon }}</span>
                </div>
                <div class="sidebar-title">{{ item.title }}</div>
              </router-link>
            </li>
          </ul>
        </div>
      </div>

      <!-- 메인 콘텐츠 영역 -->
      <div class="main-content">
        <slot></slot>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import IconDocumentation from './icons/IconDocumentation.vue';
import IconCommunity from './icons/IconCommunity.vue';
import IconSupport from './icons/IconSupport.vue';
import IconTooling from './icons/IconTooling.vue';
import IconEcosystem from './icons/IconEcosystem.vue';

const router = useRouter();
const isAuthenticated = ref(false);
const searchQuery = ref('');
const userNickname = ref('');

const categories = [
  { title: '홈', icon: 'mdi-home', iconComponent: IconDocumentation, route: '/', active: true },
  { title: '게임 소개', icon: 'mdi-gamepad-variant', iconComponent: IconEcosystem, route: '/introduce' },
  { title: '자유 게시판', icon: 'mdi-forum', iconComponent: IconCommunity, route: '/free' },
  { title: '리뷰', icon: 'mdi-star', iconComponent: IconTooling, route: '/review' },
  { title: 'Q&A', icon: 'mdi-help-circle', iconComponent: IconSupport, route: '/qna' },
  { title: '공지사항', icon: 'mdi-bell', iconComponent: IconTooling, route: '/notice' },
];

// 로그인 상태 확인 및 사용자 정보 가져오기
onMounted(async () => {
  const token = localStorage.getItem('token');
  if (token) {
    isAuthenticated.value = true;
    // localStorage에서 닉네임 가져오기 (로그인 시 저장된 경우)
    userNickname.value = localStorage.getItem('nickname') || '사용자';

    // 또는 API 호출로 사용자 정보 가져오기
    try {
      const response = await fetch('/api/users/me', {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });

      if (response.ok) {
        const userData = await response.json();
        userNickname.value = userData.nickname;
        // 필요하면 localStorage에 닉네임 저장
        localStorage.setItem('nickname', userData.nickname);
      }
    } catch (error) {
      console.error('사용자 정보를 가져오는 중 오류 발생:', error);
    }
  }
});

// 로그아웃 함수
const logout = () => {
  localStorage.removeItem('token');
  localStorage.removeItem('nickname');
  localStorage.removeItem('email');
  isAuthenticated.value = false;
  router.push('/');
};

defineExpose({ searchQuery, categories });
</script>

<script>
export default {
  name: 'Header'
}
</script>

<style>
/* 전체 앱 스타일 오버라이드 */
.v-application {
  width: 100% !important;
  max-width: 100% !important;
  overflow-x: hidden;
}

body,
html {
  overflow-x: hidden !important;
  max-width: 100% !important;
  margin: 0;
  padding: 0;
  background-color: white !important;
  color: rgba(0, 0, 0, 0.87) !important;
  font-weight: 400;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.v-theme--light {
  --v-theme-background: white !important;
  --v-theme-surface: white !important;
  --v-theme-on-surface: rgba(0, 0, 0, 0.87) !important;
  --v-theme-primary: #1976d2 !important;
}

/* 네비게이션 바 스타일 */
.v-app-bar {
  height: 70px !important;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05) !important;
  border-bottom: 1px solid rgba(0, 0, 0, 0.08);
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 100;
  background-color: white !important;
  /* 배경색 강제 적용 */
}

.nav-container {
  width: 100%;
  max-width: 2000px;
  /* 더 넓은 최대 너비 */
  margin: 0 auto;
  padding: 0 24px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 13px;
}

/* 로고 스타일 */
.logo-container {
  transition: transform 0.2s;
  flex: 0 0 auto;
  margin-right: 20px;
}

.logo-container:hover {
  transform: scale(1.05);
}

.logo-wrapper {
  display: flex;
  align-items: center;
}

.logo-wrapper:hover {
  background-color: transparent !important;
  /* 호버 시 배경색 제거 */
}

.logo-image {
  filter: drop-shadow(0 2px 4px rgba(0, 0, 0, 0.1));
}

.brand-name-container {
  display: flex;
  align-items: baseline;
  margin-left: 8px;
}

.brand-name-gamers {
  font-size: 1.5rem;
  font-weight: 700 !important;
  color: #1976d2 !important;
  letter-spacing: -0.5px;
}

.brand-name-ranc {
  font-size: 1.5rem;
  font-weight: 700 !important;
  color: #ff5252 !important;
  margin-left: 4px;
  letter-spacing: -0.5px;
}

/* 검색창 스타일 개선 */
.search-container {
  flex: 1;
  max-width: 600px;
  /* 검색창 넓게 */
  margin: 0 auto;
  /* 중앙 정렬 */
}

.search-wrapper {
  position: relative;
  display: flex;
  align-items: center;
  width: 100%;
  border-radius: 24px;
  border: 1px solid rgba(0, 0, 0, 0.15);
  background-color: #f5f7fa;
  overflow: hidden;
  transition: all 0.2s;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.03);
}

.search-wrapper:focus-within {
  box-shadow: 0 2px 10px rgba(25, 118, 210, 0.15);
  border-color: rgba(25, 118, 210, 0.5);
  background-color: white;
}

.search-icon {
  margin: 0 12px;
  color: rgba(0, 0, 0, 0.5);
  font-size: 1rem;
}

.search-input {
  flex: 1;
  border: none !important;
  outline: none !important;
  padding: 12px 12px 12px 0;
  color: rgba(0, 0, 0, 0.87) !important;
  font-weight: 500 !important;
  font-size: 0.95rem;
  background-color: transparent;
}

/* 로그인/회원가입 버튼 스타일 */
.auth-buttons {
  display: flex;
  gap: 12px;
  flex: 0 0 auto;
  margin-left: 20px;
  align-items: center;
}

.login-btn {
  color: #1976d2 !important;
  font-weight: 600 !important;
  padding: 0 20px !important;
  height: 40px !important;
  border-radius: 20px !important;
  border: 2px solid #1976d2 !important;
  background-color: transparent !important;
  transition: all 0.2s !important;
  display: flex !important;
  align-items: center !important;
  justify-content: center !important;
}

.login-btn:hover {
  background-color: rgba(25, 118, 210, 0.05) !important;
  transform: translateY(-2px);
}

.signup-btn {
  background-color: #1976d2 !important;
  color: white !important;
  font-weight: 600 !important;
  padding: 0 20px !important;
  height: 40px !important;
  border-radius: 20px !important;
  box-shadow: 0 3px 5px rgba(0, 0, 0, 0.2) !important;
  transition: all 0.2s !important;
  border: 2px solid #1976d2 !important;
  display: flex !important;
  align-items: center !important;
  justify-content: center !important;
}

.signup-btn:hover {
  background-color: #1565c0 !important;
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.25) !important;
  transform: translateY(-2px);
}

/* 로그인 상태 환영 메시지 */
.welcome-message {
  font-weight: 600;
  color: #1976d2;
  margin-right: 10px;
  font-size: 0.95rem;
}

/* 로그아웃 버튼 스타일 */
.logout-btn {
  background-color: #ff5252 !important;
  color: white !important;
  font-weight: 600 !important;
  padding: 0 20px !important;
  height: 40px !important;
  border-radius: 20px !important;
  box-shadow: 0 3px 5px rgba(0, 0, 0, 0.2) !important;
  transition: all 0.2s !important;
  border: 2px solid #ff5252 !important;
  display: flex !important;
  align-items: center !important;
  justify-content: center !important;
}

.logout-btn:hover {
  background-color: #f44336 !important;
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.25) !important;
  transform: translateY(-2px);
}

/* 페이지 레이아웃 */
.page-layout {
  display: flex;
  width: 100%;
  margin-top: 70px;
  /* 네비게이션 바 높이 */
}

/* 사이드바 스타일 */
.sidebar {
  width: 80px;
  /* 더 좁은 사이드바 */
  flex-shrink: 0;
  height: calc(100vh - 70px);
  position: fixed;
  top: 70px;
  left: 0;
  overflow-y: auto;
  z-index: 10;
}

.sidebar-container {
  background-color: white;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.05);
  border-right: 1px solid rgba(0, 0, 0, 0.08);
  height: 100%;
}

.sidebar-list {
  padding: 16px 0;
  list-style-type: none;
  margin: 0;
}

.sidebar-item {
  position: relative;
  margin: 4px 8px;
  border-radius: 8px;
  overflow: hidden;
  transition: all 0.2s;
  list-style-type: none;
}

.sidebar-item-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 12px 4px;
  text-align: center;
}

.sidebar-icon-container {
  width: 24px;
  height: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 0;
  margin-bottom: 6px;
  flex-shrink: 0;
}

.sidebar-icon-component {
  width: 100%;
  height: 100%;
  color: rgba(0, 0, 0, 0.6);
  transition: color 0.2s;
}

.sidebar-icon-fallback {
  color: rgba(0, 0, 0, 0.6);
  font-size: 1.25rem;
  transition: color 0.2s;
}

.sidebar-title {
  color: rgba(0, 0, 0, 0.75) !important;
  font-weight: 500 !important;
  font-size: 0.7rem;
  transition: color 0.2s;
}

.sidebar-item:hover {
  background-color: rgba(25, 118, 210, 0.05) !important;
}

.sidebar-item:hover .sidebar-icon-component,
.sidebar-item:hover .sidebar-icon-fallback,
.sidebar-item:hover .sidebar-title {
  color: #1976d2 !important;
}

.sidebar-item.active {
  /* background-color: rgba(25, 118, 210, 0.08) !important; */
  /* 기존 배경색 제거 */
}

.sidebar-item.active .sidebar-icon-component,
.sidebar-item.active .sidebar-icon-fallback,
.sidebar-item.active .sidebar-title {
  color: rgba(0, 0, 0, 0.75) !important;
  /* 파란색에서 검정색으로 변경 */
  font-weight: 600 !important;
}

.sidebar-item.active::before {
  /* content: ''; */
  /* position: absolute; */
  /* left: 0; */
  /* top: 0; */
  /* bottom: 0; */
  /* width: 4px; */
  /* background-color: #1976d2; */
  /* border-radius: 0 4px 4px 0; */
  /* 파란색 선 제거 */
}

/* 메인 콘텐츠 영역 */
.main-content {
  flex: 1;
  min-height: calc(100vh - 70px);
  margin-left: 80px;
  /* 사이드바 너비만큼 마진 추가 */
  width: calc(100% - 80px);
  /* 사이드바 너비만큼 너비 조정 */
  background-color: #f5f7fa;
  padding: 0;
  overflow-x: hidden;
}

/* 미디어 쿼리 - 반응형 */
@media (max-width: 1366px) {

  /* 브레이크포인트 확대 */
  .nav-container {
    padding: 0 16px;
  }

  .search-container {
    max-width: 400px;
  }
}

/* 미디어 쿼리 - 태블릿 */
@media (max-width: 1024px) {

  /* 브레이크포인트 확대 */
  .nav-container {
    flex-wrap: wrap;
    padding: 8px 16px;
  }

  .search-container {
    order: 3;
    max-width: 100%;
    margin: 8px 0 0;
    width: 100%;
  }

  .v-app-bar {
    height: auto !important;
    position: relative;
  }

  .page-layout {
    flex-direction: column;
    margin-top: 0;
  }

  .sidebar {
    width: 100%;
    height: auto;
    position: relative;
    top: 0;
  }

  .sidebar-container {
    border-right: none;
    border-bottom: 1px solid rgba(0, 0, 0, 0.08);
  }

  .sidebar-list {
    display: flex;
    overflow-x: auto;
    padding: 8px;
  }

  .sidebar-item {
    margin: 0 4px;
    flex: 0 0 auto;
  }

  .sidebar-item-content {
    padding: 8px 16px;
  }

  .main-content {
    margin-left: 0;
    width: 100%;
  }
}

@media (max-width: 600px) {

  .brand-name-gamers,
  .brand-name-ranc {
    font-size: 1.25rem;
  }

  .auth-buttons {
    gap: 8px;
  }

  .login-btn,
  .signup-btn {
    padding: 0 12px !important;
    font-size: 0.875rem !important;
  }
}
</style>

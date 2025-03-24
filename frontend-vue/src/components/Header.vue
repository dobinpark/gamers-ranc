<template>
  <v-app class="bg-white">
    <!-- 네비게이션 바 -->
    <v-app-bar app color="white" elevation="1">
      <div class="d-flex align-center justify-space-between w-100">
        <!-- 좌측 로고 -->
        <div class="d-flex align-center">
          <v-img
            src="@/assets/logo.png"
            alt="Logo"
            max-height="40"
            max-width="40"
            class="mr-2"
          ></v-img>
          <span class="text-h6 font-weight-bold">Gamers RanC</span>
        </div>

        <!-- 중앙 검색창 -->
        <v-text-field
          v-model="searchQuery"
          prepend-inner-icon="mdi-magnify"
          placeholder="검색어를 입력하세요"
          hide-details
          density="compact"
          variant="outlined"
          class="mx-4"
          style="max-width: 500px"
        ></v-text-field>

        <!-- 우측 로그인/가입 버튼 -->
        <div>
          <v-btn variant="text" class="mr-2">로그인</v-btn>
          <v-btn color="primary">회원가입</v-btn>
        </div>
      </div>
    </v-app-bar>

    <v-main style="background-color: white; width: 100vw; max-width: 100%;">
      <v-container fluid class="pa-0 ma-0" style="max-width: 100%; width: 100%;">
        <v-row no-gutters class="ma-0">
          <!-- 좌측 사이드바 - 너비 축소하고 좌측으로 붙임 -->
          <v-col cols="12" md="2" class="pa-0" style="position: fixed; left: 0; top: 64px; bottom: 0; width: 180px; z-index: 1;">
            <v-card flat class="ma-0 pa-0 h-100" style="width: 100%;">
              <v-list density="compact">
                <v-list-item
                  v-for="(item, i) in categories"
                  :key="i"
                  :to="item.route"
                  :active="item.active"
                  class="py-2"
                >
                  <template v-slot:prepend>
                    <v-icon :icon="item.icon"></v-icon>
                  </template>
                  <v-list-item-title>{{ item.title }}</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-card>
          </v-col>

          <!-- 메인 콘텐츠 영역 - 좌측에 여백 추가하고 확장 -->
          <v-col cols="12" class="pa-0" style="margin-left: 180px;">
            <v-sheet min-height="70vh" rounded="0" class="pa-4" style="width: 100%;">
              <slot></slot>
            </v-sheet>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script setup>
import { ref } from 'vue';

const searchQuery = ref('');

const categories = [
  { title: '홈', icon: 'mdi-home', route: '/', active: true },
  { title: '게임 소개', icon: 'mdi-gamepad-variant', route: '/introduce' },
  { title: '자유 게시판', icon: 'mdi-forum', route: '/free' },
  { title: '리뷰', icon: 'mdi-star', route: '/review' },
  { title: 'Q&A', icon: 'mdi-help-circle', route: '/qna' },
  { title: '공지사항', icon: 'mdi-bell', route: '/notice' },
];

defineExpose({ searchQuery, categories });
</script>

<style>
/* 전체 앱 스타일 오버라이드 */
.v-application {
  width: 100% !important;
  max-width: 100% !important;
  overflow-x: hidden;
}

body, html {
  overflow-x: hidden !important;
  max-width: 100% !important;
  margin: 0;
  padding: 0;
  background-color: white !important;
}

.v-theme--light {
  --v-theme-background: white !important;
  --v-theme-surface: white !important;
}

/* 사이드바 아이콘 메뉴 스타일 */
@media (max-width: 960px) {
  .v-col[style*="margin-left: 180px"] {
    margin-left: 0 !important;
  }
  
  .v-col[style*="position: fixed"] {
    position: relative !important;
    top: auto !important;
    width: 100% !important;
  }
}
</style>

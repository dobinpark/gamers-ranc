<script setup lang="ts">
import { ref } from 'vue'
import Header from '../components/Header.vue'

const popularPosts = ref([
  {
    title: '신작 게임 리뷰: 엘든 링',
    author: 'gamer123',
    views: 1542,
    category: { color: 'green', icon: 'mdi-star' }
  },
  {
    title: 'RPG 게임 추천 해주세요',
    author: 'rpglover',
    views: 982,
    category: { color: 'blue', icon: 'mdi-forum' }
  },
  {
    title: '게임 성능 최적화 팁',
    author: 'techguru',
    views: 765,
    category: { color: 'amber', icon: 'mdi-help-circle' }
  },
  {
    title: '블록체인 게임 개발자 모집',
    author: 'admin',
    views: 431,
    category: { color: 'red', icon: 'mdi-bell' }
  },
])

const noticePosts = ref([
  {
    title: '금주 서버 점검 안내 (10/26)',
    author: 'admin',
    views: 1200,
    category: { color: 'red', icon: 'mdi-bell' }
  },
  {
    title: '개인정보처리방침 변경 공지',
    author: 'admin',
    views: 850,
    category: { color: 'red', icon: 'mdi-bell' }
  },
])
</script>

<template>
  <Header>
    <div class="home">
      <h1>게이머스 랭크</h1>
      <p>게임 커뮤니티에 오신 것을 환영합니다!</p>

      <!-- 인기 게시글 목록 예시 -->
      <v-row>
        <v-col cols="12" md="6">
          <v-card class="mt-4" height="300">
            <v-card-title>인기 게시글</v-card-title>
            <v-list class="overflow-y-auto" max-height="230">
              <v-list-item v-for="(post, index) in popularPosts" :key="index" :title="post.title"
                :subtitle="`작성자: ${post.author} | 조회수: ${post.views}`">
                <template v-slot:prepend>
                  <v-icon :color="post.category.color" :icon="post.category.icon"></v-icon>
                </template>
              </v-list-item>
            </v-list>
          </v-card>
        </v-col>

        <!-- 공지사항 -->
        <v-col cols="12" md="6">
          <v-card class="mt-4 mb-4" height="300">
            <v-card-title>공지사항</v-card-title>
            <v-list class="overflow-y-auto" max-height="230">
              <v-list-item v-for="(post, index) in noticePosts" :key="index" :title="post.title"
                :subtitle="`작성자: ${post.author} | 조회수: ${post.views}`">
                <template v-slot:prepend>
                  <v-icon :color="post.category.color" :icon="post.category.icon"></v-icon>
                </template>
              </v-list-item>
            </v-list>
          </v-card>
        </v-col>
      </v-row>
    </div>
  </Header>
</template>

<style scoped>
.home {
  padding: 16px;
  max-width: 1200px;
  margin: 0 auto;
}

.v-card {
  display: flex;
  flex-direction: column;
}

.v-list {
  flex: 1;
}

@media (max-width: 960px) {
  .v-row {
    flex-direction: column;
  }
  
  .v-card {
    margin-bottom: 16px;
  }
}
</style>

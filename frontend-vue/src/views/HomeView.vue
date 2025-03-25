<script setup lang="ts">
import { ref } from 'vue';
import Header from '../components/Header.vue'

const popularPosts = ref([
  {
    title: '신작 게임 리뷰: 엘든 링',
    author: 'gamer123',
    views: 1542,
    category: { color: '#4CAF50', icon: 'mdi-star' }
  },
  {
    title: 'RPG 게임 추천 해주세요',
    author: 'rpglover',
    views: 982,
    category: { color: '#2196F3', icon: 'mdi-forum' }
  },
  {
    title: '게임 성능 최적화 팁',
    author: 'techguru',
    views: 765,
    category: { color: '#FFC107', icon: 'mdi-help-circle' }
  },
  {
    title: '블록체인 게임 개발자 모집',
    author: 'admin',
    views: 431,
    category: { color: '#F44336', icon: 'mdi-bell' }
  },
])

const noticePosts = ref([
  {
    title: '금주 서버 점검 안내 (10/26)',
    author: 'admin',
    views: 1200,
    category: { color: '#F44336', icon: 'mdi-bell' }
  },
  {
    title: '개인정보처리방침 변경 공지',
    author: 'admin',
    views: 850,
    category: { color: '#F44336', icon: 'mdi-bell' }
  },
])
</script>

<template>
  <Header>
    <!-- 히어로 섹션 -->
    <div class="hero-section">
      <div class="hero-content">
        <img src="../assets/image/banner.png" alt="Gamer's RanC 게임 커뮤니티에 오신 것을 환영합니다!" class="banner-image">
      </div>
    </div>

    <div class="home">
      <!-- 콘텐츠 섹션 -->
      <div class="content-section">
        <!-- 카드 섹션 -->
        <div class="cards-row">
          <!-- 인기 게시글 -->
          <div class="card-col">
            <div class="v-card card-container">
              <div class="card-header">
                <div class="v-card-title card-title">인기 게시글</div>
                <div class="card-action">더보기</div>
              </div>
              <ul class="v-list overflow-y-auto post-list">
                <li class="v-list-item post-item" v-for="(post, index) in popularPosts" :key="index">
                  <div class="post-category-icon" :style="{ backgroundColor: post.category.color }">
                    <span class="post-icon">{{ post.category.icon }}</span>
                  </div>
                  <div class="post-content">
                    <div class="v-list-item-title post-title">{{ post.title }}</div>
                    <div class="v-list-item-subtitle post-subtitle">
                      <span class="author">작성자: {{ post.author }}</span>
                      <span class="divider">|</span>
                      <span class="views">조회수: {{ post.views }}</span>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
          </div>

          <!-- 공지사항 -->
          <div class="card-col">
            <div class="v-card card-container">
              <div class="card-header">
                <div class="v-card-title card-title">공지사항</div>
                <div class="card-action">더보기</div>
              </div>
              <ul class="v-list overflow-y-auto post-list">
                <li class="v-list-item post-item" v-for="(post, index) in noticePosts" :key="index">
                  <div class="post-category-icon" :style="{ backgroundColor: post.category.color }">
                    <span class="post-icon">{{ post.category.icon }}</span>
                  </div>
                  <div class="post-content">
                    <div class="v-list-item-title post-title">{{ post.title }}</div>
                    <div class="v-list-item-subtitle post-subtitle">
                      <span class="author">작성자: {{ post.author }}</span>
                      <span class="divider">|</span>
                      <span class="views">조회수: {{ post.views }}</span>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </div>

        <!-- 추천 게임 섹션 -->
        <div class="recommended-section">
          <h2 class="section-title">추천 게임</h2>
          <div class="game-cards">
            <div class="game-card">
              <div class="game-image">
                <div class="game-category">RPG</div>
              </div>
              <div class="game-info">
                <h3 class="game-title">엘든 링</h3>
                <p class="game-desc">프롬 소프트웨어의 오픈 월드 액션 RPG</p>
                <div class="game-meta">
                  <span class="game-rating">★★★★★</span>
                  <span class="game-players">1만명 플레이 중</span>
                </div>
              </div>
            </div>
            <div class="game-card">
              <div class="game-image game-image-2">
                <div class="game-category">액션</div>
              </div>
              <div class="game-info">
                <h3 class="game-title">데빌 메이 크라이 5</h3>
                <p class="game-desc">캡콤의 스타일리쉬 액션 게임</p>
                <div class="game-meta">
                  <span class="game-rating">★★★★☆</span>
                  <span class="game-players">5천명 플레이 중</span>
                </div>
              </div>
            </div>
            <div class="game-card">
              <div class="game-image game-image-3">
                <div class="game-category">어드벤처</div>
              </div>
              <div class="game-info">
                <h3 class="game-title">젤다의 전설</h3>
                <p class="game-desc">닌텐도의 대표 오픈월드 어드벤처</p>
                <div class="game-meta">
                  <span class="game-rating">★★★★★</span>
                  <span class="game-players">8천명 플레이 중</span>
                </div>
              </div>
            </div>
            <div class="game-card">
              <div class="game-image game-image-4">
                <div class="game-category">슈팅</div>
              </div>
              <div class="game-info">
                <h3 class="game-title">발로란트</h3>
                <p class="game-desc">라이엇 게임즈의 전술적 슈팅 게임</p>
                <div class="game-meta">
                  <span class="game-rating">★★★★☆</span>
                  <span class="game-players">12만명 플레이 중</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 커뮤니티 활동 섹션 -->
        <div class="community-section">
          <h2 class="section-title">커뮤니티 활동</h2>
          <div class="community-stats">
            <div class="stat-card">
              <div class="stat-icon">mdi-account-group</div>
              <div class="stat-value">15,423</div>
              <div class="stat-label">활성 사용자</div>
            </div>
            <div class="stat-card">
              <div class="stat-icon">mdi-message-text</div>
              <div class="stat-value">32,105</div>
              <div class="stat-label">게시글</div>
            </div>
            <div class="stat-card">
              <div class="stat-icon">mdi-comment</div>
              <div class="stat-value">87,964</div>
              <div class="stat-label">댓글</div>
            </div>
            <div class="stat-card">
              <div class="stat-icon">mdi-star</div>
              <div class="stat-value">45,789</div>
              <div class="stat-label">평가</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </Header>
</template>

<style scoped>
/* 히어로 섹션 */
.hero-section {
  background: linear-gradient(135deg, #1976d2, #2196f3);
  color: white;
  padding: 0; /* 패딩 제거 */
  text-align: center;
  margin-bottom: 40px;
  width: 100%;
  position: relative;
  height: 300px; /* 고정 높이 설정 - 필요에 따라 조정 가능 */
  overflow: hidden;
}

.hero-content {
  max-width: 100%;
  margin: 0 auto;
  padding: 0;
  width: 100%;
  height: 100%;
}

.banner-image {
  width: 100%;
  height: 100%;
  display: block;
  object-fit: cover; /* 이미지가 컨테이너를 채우도록 설정 */
  object-position: center; /* 이미지 중앙 정렬 */
}

/* 기존 제목 스타일은 유지 (이미지가 로드되지 않을 경우를 대비) */
.main-title {
  display: none;
  /* 숨김 처리 */
}

.subtitle {
  display: none;
  /* 숨김 처리 */
}

/* 홈 콘테이너 */
.home {
  background-color: #f5f7fa;
  width: 100%;
  max-width: 100%;
  min-height: calc(100vh - 70px - 190px);
  margin: 0;
  padding: 0 5px;
  /* 좌우 패딩 최소화 */
}

/* 콘텐츠 섹션 */
.content-section {
  padding: 0 5px;
  /* 좌우 패딩 최소화 */
  max-width: 100%;
  margin: 0;
}

/* 카드 섹션 */
.cards-row {
  display: flex;
  gap: 30px;
  /* 갭 조정 */
  margin-bottom: 40px;
}

.card-col {
  flex: 1;
  min-width: 0;
  /* flex 항목이 내용에 의해 너무 확장되는 것을 방지 */
}

/* 카드 스타일 강화 */
.card-container {
  display: flex;
  flex-direction: column;
  border: none;
  border-radius: 12px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
  background-color: white;
  overflow: hidden;
  height: 420px;
  /* 더 높은 카드 */
  transition: transform 0.2s, box-shadow 0.2s;
}

.card-container:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  /* 패딩 조정 */
  border-bottom: 1px solid rgba(0, 0, 0, 0.05);
}

.card-title {
  font-size: 1.35rem;
  /* 더 큰 제목 */
  font-weight: 700;
  color: #1976d2;
  padding: 0;
}

.card-action {
  font-size: 0.95rem;
  /* 더 큰 텍스트 */
  font-weight: 600;
  color: #1976d2;
  cursor: pointer;
}

.card-action:hover {
  text-decoration: underline;
}

/* 게시글 목록 스타일 */
.post-list {
  flex: 1;
  padding: 0;
  overflow-y: auto;
  height: calc(100% - 74px);
  /* 카드 헤더 높이 고려 */
}

.post-item {
  display: flex;
  align-items: flex-start;
  padding: 18px 20px;
  /* 패딩 조정 */
  border-bottom: 1px solid rgba(0, 0, 0, 0.04);
  transition: background-color 0.2s;
}

.post-item:hover {
  background-color: rgba(25, 118, 210, 0.03);
}

.post-category-icon {
  width: 40px;
  /* 더 큰 아이콘 */
  height: 40px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 18px;
  flex-shrink: 0;
}

.post-icon {
  color: white;
  font-size: 1.3rem;
  /* 더 큰 아이콘 */
}

.post-content {
  flex: 1;
}

.post-title {
  font-size: 1.1rem;
  /* 더 큰 제목 */
  font-weight: 600;
  color: rgba(0, 0, 0, 0.87);
  margin-bottom: 8px;
  line-height: 1.4;
}

.post-subtitle {
  font-size: 0.875rem;
  /* 더 큰 부제목 */
  color: rgba(0, 0, 0, 0.6);
  display: flex;
  align-items: center;
}

.author,
.views {
  color: rgba(0, 0, 0, 0.6);
}

.divider {
  margin: 0 8px;
  color: rgba(0, 0, 0, 0.4);
}

/* 추천 게임 섹션 */
.recommended-section {
  margin-top: 60px;
  /* 더 넓은 마진 */
  margin-bottom: 60px;
}

.section-title {
  font-size: 2rem;
  /* 더 큰 제목 */
  font-weight: 700;
  color: #1976d2;
  margin-bottom: 32px;
  text-align: center;
}

.game-cards {
  display: flex;
  gap: 15px;
  /* 갭 더 줄임 */
  overflow-x: visible;
  padding: 8px 2px;
  flex-wrap: wrap;
  justify-content: space-between;
}

.game-card {
  flex: 0 0 calc(20% - 12px);
  /* 5개 카드가 한 줄에 들어갈 수 있도록 조정 */
  max-width: calc(20% - 12px);
  border-radius: 12px;
  overflow: hidden;
  background-color: white;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
  transition: transform 0.2s, box-shadow 0.2s;
  margin-bottom: 15px;
  /* 하단 여백 조정 */
}

.game-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 12px 24px rgba(0, 0, 0, 0.1);
}

.game-image {
  height: 160px;
  /* 이미지 높이 조정 */
  background: linear-gradient(rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.1)),
    url('https://via.placeholder.com/300x180/1976d2/ffffff?text=Elden+Ring') center/cover no-repeat;
  position: relative;
}

.game-image-2 {
  background: linear-gradient(rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.1)),
    url('https://via.placeholder.com/300x180/f44336/ffffff?text=Devil+May+Cry+5') center/cover no-repeat;
}

.game-image-3 {
  background: linear-gradient(rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.1)),
    url('https://via.placeholder.com/300x180/4caf50/ffffff?text=Zelda') center/cover no-repeat;
}

.game-image-4 {
  background: linear-gradient(rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.1)),
    url('https://via.placeholder.com/300x180/9c27b0/ffffff?text=Valorant') center/cover no-repeat;
}

.game-category {
  position: absolute;
  top: 12px;
  right: 12px;
  background-color: rgba(0, 0, 0, 0.6);
  color: white;
  font-size: 0.8rem;
  /* 더 큰 텍스트 */
  font-weight: 600;
  padding: 5px 10px;
  border-radius: 4px;
}

.game-info {
  padding: 16px;
  /* 패딩 조정 */
}

.game-title {
  font-size: 1.2rem;
  /* 제목 크기 조정 */
  font-weight: 700;
  margin-bottom: 8px;
  color: rgba(0, 0, 0, 0.87);
}

.game-desc {
  font-size: 0.9rem;
  /* 설명 크기 조정 */
  color: rgba(0, 0, 0, 0.6);
  margin-bottom: 12px;
  line-height: 1.4;
}

.game-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 0.85rem;
  /* 더 큰 메타 정보 */
}

.game-rating {
  color: #ffc107;
  font-weight: 600;
}

.game-players {
  color: rgba(0, 0, 0, 0.6);
}

/* 커뮤니티 활동 섹션 */
.community-section {
  margin-top: 60px;
  /* 더 넓은 마진 */
  margin-bottom: 60px;
  padding: 40px 0;
  /* 더 넓은 패딩 */
  background-color: white;
  border-radius: 12px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
}

.community-stats {
  display: flex;
  justify-content: space-between;
  /* 공간 균등 분배 */
  flex-wrap: wrap;
  gap: 20px;
  /* 갭 조정 */
  margin-top: 32px;
  padding: 0 30px;
  /* 좌우 패딩 조정 */
}

.stat-card {
  text-align: center;
  padding: 32px 20px;
  /* 패딩 조정 */
  flex: 1;
  min-width: 180px;
  /* 카드 너비 조정 */
}

.stat-icon {
  font-size: 2.5rem;
  /* 더 큰 아이콘 */
  color: #1976d2;
  margin-bottom: 12px;
}

.stat-value {
  font-size: 2.5rem;
  /* 더 큰 값 */
  font-weight: 700;
  color: #1976d2;
  margin-bottom: 8px;
}

.stat-label {
  font-size: 1rem;
  /* 더 큰 레이블 */
  color: rgba(0, 0, 0, 0.6);
}

/* 미디어 쿼리 (타블렛) */
@media (max-width: 1366px) {
  .game-card {
    flex: 0 0 calc(25% - 15px);
    max-width: calc(25% - 15px);
  }
}

@media (max-width: 1024px) {
  .cards-row {
    flex-direction: column;
    gap: 20px;
  }

  .card-container {
    height: 360px;
    margin-bottom: 20px;
  }

  .game-cards {
    flex-wrap: nowrap;
    overflow-x: auto;
    padding-bottom: 20px;
    justify-content: flex-start;
  }

  .game-card {
    flex: 0 0 260px;
    max-width: 260px;
    margin-bottom: 0;
  }
}

/* 미디어 쿼리 (모바일) */
@media (max-width: 600px) {
  .hero-section {
    padding: 40px 16px;
  }

  .main-title {
    font-size: 2rem;
  }

  .subtitle {
    font-size: 1.25rem;
  }

  .content-section {
    padding: 0 16px;
  }

  .section-title {
    font-size: 1.5rem;
  }

  .card-container {
    height: 320px;
  }

  .stat-card {
    flex-basis: 100%;
    padding: 24px 16px;
  }

  .stat-value {
    font-size: 2rem;
  }
}
</style>

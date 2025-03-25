<template>
  <div class="review-container">
    <Header>
      <div class="board-container">
        <div class="board-header">
          <h1 class="board-title">리뷰 게시판</h1>
          <p class="board-description">다양한 게임에 대한 유저들의 솔직한 리뷰를 확인하세요.</p>
        </div>

        <div class="board-actions">
          <div class="search-filter-container">
            <div class="filter-container">
              <select v-model="sortOption" class="sort-select">
                <option value="latest">최신순</option>
                <option value="rating">평점순</option>
                <option value="popular">인기순</option>
              </select>
              <select v-model="genreOption" class="genre-select">
                <option value="all">전체 장르</option>
                <option value="rpg">RPG</option>
                <option value="fps">FPS</option>
                <option value="action">액션</option>
                <option value="strategy">전략</option>
                <option value="simulation">시뮬레이션</option>
              </select>
            </div>
            <div class="search-container">
              <input type="text" v-model="searchQuery" placeholder="게임 이름 검색" class="search-input">
              <button class="search-button">검색</button>
            </div>
          </div>
          <button class="write-button">리뷰 작성</button>
        </div>

        <div class="review-list">
          <div class="review-card" v-for="review in reviews" :key="review.id">
            <div class="review-game-image" :style="{ backgroundImage: `url(${review.gameImage})` }">
              <div class="review-genre">{{ review.genre }}</div>
            </div>
            <div class="review-content">
              <div class="review-header">
                <h3 class="review-game-title">{{ review.gameTitle }}</h3>
                <div class="review-rating">
                  <div class="stars">
                    <span v-for="i in 5" :key="i" class="star" :class="{ 'filled': i <= review.rating }">★</span>
                  </div>
                  <span class="rating-value">{{ review.rating.toFixed(1) }}</span>
                </div>
              </div>
              <p class="review-summary">{{ review.summary }}</p>
              <div class="review-meta">
                <div class="reviewer-info">
                  <span class="reviewer-avatar" :style="{ backgroundColor: getAvatarColor(review.author) }">
                    {{ review.author.charAt(0).toUpperCase() }}
                  </span>
                  <span class="reviewer-name">{{ review.author }}</span>
                </div>
                <div class="review-info">
                  <span class="review-date">{{ formatDate(review.date) }}</span>
                  <span class="review-views">조회 {{ review.views }}</span>
                  <span class="review-comments">댓글 {{ review.comments }}</span>
                  <span class="review-likes">좋아요 {{ review.likes }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="pagination">
          <button class="page-button prev">이전</button>
          <div class="page-numbers">
            <button class="page-number active">1</button>
            <button class="page-number">2</button>
            <button class="page-number">3</button>
            <button class="page-number">4</button>
            <button class="page-number">5</button>
          </div>
          <button class="page-button next">다음</button>
        </div>
      </div>
    </Header>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import Header from '../../components/Header.vue';

const searchQuery = ref('');
const sortOption = ref('latest');
const genreOption = ref('all');

// 샘플 리뷰 데이터
const reviews = ref([
  {
    id: 1,
    gameTitle: '엘든 링',
    gameImage: 'https://via.placeholder.com/300x180/1976d2/ffffff?text=Elden+Ring',
    genre: 'RPG',
    rating: 4.8,
    summary: '프롬소프트웨어의 최고 걸작. 광활한 오픈월드와 깊이 있는 전투 시스템이 매력적인 액션 RPG. 도전적인 난이도지만 그만큼 클리어 시 성취감이 높습니다.',
    author: 'soulsplayer',
    date: new Date('2023-03-15T14:30:00'),
    views: 3542,
    comments: 78,
    likes: 325
  },
  {
    id: 2,
    gameTitle: '발로란트',
    gameImage: 'https://via.placeholder.com/300x180/9c27b0/ffffff?text=Valorant',
    genre: 'FPS',
    rating: 4.5,
    summary: '라이엇 게임즈의 전술적 5v5 슈팅 게임. 각 요원의 고유한 능력과 깔끔한 타격감이 인상적입니다. 팀워크가 중요하며 친구들과 함께 플레이하기 좋습니다.',
    author: 'tacticalshooter',
    date: new Date('2023-03-14T10:15:00'),
    views: 2201,
    comments: 42,
    likes: 187
  },
  {
    id: 3,
    gameTitle: '데빌 메이 크라이 5',
    gameImage: 'https://via.placeholder.com/300x180/f44336/ffffff?text=DMC5',
    genre: '액션',
    rating: 4.6,
    summary: '끝없는 콤보와 화려한 액션의 향연. 캡콤의 인기 시리즈 최신작으로, 세 명의 캐릭터를 번갈아가며 플레이할 수 있어 다양한 전투 스타일을 경험할 수 있습니다.',
    author: 'stylishslayer',
    date: new Date('2023-03-13T18:45:00'),
    views: 1887,
    comments: 35,
    likes: 142
  }
]);

// 날짜 포맷 함수
const formatDate = (date) => {
  const now = new Date();
  const diff = now - date;
  
  // 24시간 이내
  if (diff < 86400000) {
    const hours = Math.floor(diff / 3600000);
    if (hours < 1) return '방금 전';
    return `${hours}시간 전`;
  }
  
  // 7일 이내
  if (diff < 604800000) {
    const days = Math.floor(diff / 86400000);
    return `${days}일 전`;
  }
  
  // 그 외
  return `${date.getFullYear()}.${date.getMonth() + 1}.${date.getDate()}`;
};

// 아바타 색상 생성 함수
const getAvatarColor = (name) => {
  let hash = 0;
  for (let i = 0; i < name.length; i++) {
    hash = name.charCodeAt(i) + ((hash << 5) - hash);
  }
  let color = '#';
  for (let i = 0; i < 3; i++) {
    const value = (hash >> (i * 8)) & 0xFF;
    color += ('00' + value.toString(16)).substr(-2);
  }
  return color;
};
</script>

<style scoped>
.review-container {
  width: 100%;
  min-height: 100vh;
}

.board-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 40px 20px;
}

.board-header {
  margin-bottom: 30px;
  text-align: center;
}

.board-title {
  font-size: 2.5rem;
  font-weight: 700;
  color: #1976d2;
  margin-bottom: 10px;
}

.board-description {
  font-size: 1.1rem;
  color: rgba(0, 0, 0, 0.6);
}

.board-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.search-filter-container {
  display: flex;
  gap: 15px;
  align-items: center;
}

.filter-container {
  display: flex;
  gap: 10px;
}

.sort-select, .genre-select {
  padding: 10px 15px;
  border-radius: 8px;
  border: 1px solid rgba(0, 0, 0, 0.1);
  background-color: white;
  font-size: 0.9rem;
  color: rgba(0, 0, 0, 0.7);
  cursor: pointer;
}

.search-container {
  display: flex;
  max-width: 400px;
}

.search-input {
  padding: 10px 15px;
  border-radius: 8px 0 0 8px;
  border: 1px solid rgba(0, 0, 0, 0.1);
  border-right: none;
  background-color: white;
  font-size: 0.9rem;
  color: rgba(0, 0, 0, 0.7);
  width: 300px;
}

.search-button {
  padding: 10px 15px;
  border-radius: 0 8px 8px 0;
  border: 1px solid #1976d2;
  background-color: #1976d2;
  color: white;
  font-size: 0.9rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
}

.search-button:hover {
  background-color: #1565c0;
}

.write-button {
  padding: 10px 20px;
  border-radius: 8px;
  border: none;
  background-color: #1976d2;
  color: white;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s;
  box-shadow: 0 3px 5px rgba(0, 0, 0, 0.1);
}

.write-button:hover {
  background-color: #1565c0;
  transform: translateY(-2px);
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.15);
}

.review-list {
  display: grid;
  grid-template-columns: repeat(1, 1fr);
  gap: 20px;
  margin-bottom: 30px;
}

.review-card {
  display: flex;
  background-color: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.05);
  transition: all 0.2s;
}

.review-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
}

.review-game-image {
  width: 180px;
  flex-shrink: 0;
  background-size: cover;
  background-position: center;
  position: relative;
}

.review-genre {
  position: absolute;
  top: 10px;
  right: 10px;
  background-color: rgba(0, 0, 0, 0.6);
  color: white;
  font-size: 0.8rem;
  font-weight: 600;
  padding: 5px 10px;
  border-radius: 4px;
}

.review-content {
  flex: 1;
  padding: 20px;
  display: flex;
  flex-direction: column;
}

.review-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 15px;
}

.review-game-title {
  font-size: 1.4rem;
  font-weight: 700;
  color: rgba(0, 0, 0, 0.85);
  margin: 0;
}

.review-rating {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.stars {
  display: flex;
  margin-bottom: 3px;
}

.star {
  color: #d4d4d4;
  font-size: 1.2rem;
}

.star.filled {
  color: #ffc107;
}

.rating-value {
  font-size: 1rem;
  font-weight: 600;
  color: #ffc107;
}

.review-summary {
  flex: 1;
  margin: 0 0 15px 0;
  color: rgba(0, 0, 0, 0.7);
  line-height: 1.6;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}

.review-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.reviewer-info {
  display: flex;
  align-items: center;
}

.reviewer-avatar {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-weight: 600;
  margin-right: 10px;
}

.reviewer-name {
  font-size: 0.95rem;
  font-weight: 500;
  color: rgba(0, 0, 0, 0.7);
}

.review-info {
  display: flex;
  gap: 15px;
}

.review-date, .review-views, .review-comments, .review-likes {
  font-size: 0.85rem;
  color: rgba(0, 0, 0, 0.5);
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 40px;
}

.page-button {
  padding: 8px 15px;
  border: 1px solid rgba(0, 0, 0, 0.1);
  background-color: white;
  color: rgba(0, 0, 0, 0.6);
  font-size: 0.9rem;
  cursor: pointer;
  transition: all 0.2s;
}

.page-button.prev {
  border-radius: 8px 0 0 8px;
}

.page-button.next {
  border-radius: 0 8px 8px 0;
}

.page-numbers {
  display: flex;
}

.page-number {
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid rgba(0, 0, 0, 0.1);
  border-left: none;
  background-color: white;
  color: rgba(0, 0, 0, 0.6);
  font-size: 0.9rem;
  cursor: pointer;
  transition: all 0.2s;
}

.page-number.active {
  background-color: #1976d2;
  color: white;
  font-weight: 600;
}

@media (max-width: 768px) {
  .review-card {
    flex-direction: column;
  }
  
  .review-game-image {
    width: 100%;
    height: 180px;
  }
  
  .review-header {
    flex-direction: column;
  }
  
  .review-rating {
    align-items: flex-start;
    margin-top: 10px;
  }
  
  .review-meta {
    flex-direction: column;
    align-items: flex-start;
  }
  
  .reviewer-info {
    margin-bottom: 10px;
  }
}
</style>

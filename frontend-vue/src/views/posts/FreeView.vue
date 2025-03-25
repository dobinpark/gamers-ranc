<template>
  <div class="post-container">
    <Header>
      <div class="board-container">
        <div class="board-header">
          <h1 class="board-title">자유 게시판</h1>
          <p class="board-description">다양한 주제로 자유롭게 소통하는 공간입니다.</p>
        </div>

        <div class="board-actions">
          <div class="search-filter-container">
            <div class="filter-container">
              <select v-model="sortOption" class="sort-select">
                <option value="latest">최신순</option>
                <option value="popular">인기순</option>
                <option value="comments">댓글순</option>
              </select>
              <select v-model="categoryOption" class="category-select">
                <option value="all">전체</option>
                <option value="discussion">토론</option>
                <option value="humor">유머</option>
                <option value="question">질문</option>
                <option value="etc">기타</option>
              </select>
            </div>
            <div class="search-container">
              <input type="text" v-model="searchQuery" placeholder="검색어를 입력하세요" class="search-input">
              <button class="search-button">검색</button>
            </div>
          </div>
          <button class="write-button">글쓰기</button>
        </div>

        <div class="post-list">
          <div class="post-item" v-for="post in posts" :key="post.id">
            <div class="post-header">
              <div class="post-category" :class="post.category">{{ getCategoryName(post.category) }}</div>
              <div class="post-title">{{ post.title }}</div>
            </div>
            <div class="post-meta">
              <div class="post-author">
                <span class="author-avatar" :style="{ backgroundColor: getAvatarColor(post.author) }">
                  {{ post.author.charAt(0).toUpperCase() }}
                </span>
                <span class="author-name">{{ post.author }}</span>
              </div>
              <div class="post-info">
                <span class="post-date">{{ formatDate(post.date) }}</span>
                <span class="post-views">조회 {{ post.views }}</span>
                <span class="post-comments">댓글 {{ post.comments }}</span>
                <span class="post-likes">좋아요 {{ post.likes }}</span>
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
const categoryOption = ref('all');

// 샘플 게시글 데이터
const posts = ref([
  {
    id: 1,
    title: '신작 게임 추천해주세요!',
    author: 'gamer123',
    date: new Date('2023-03-15T14:30:00'),
    views: 1542,
    comments: 23,
    likes: 45,
    category: 'discussion'
  },
  {
    id: 2,
    title: 'PC방에서 자주 플레이하는 게임 TOP 10',
    author: 'pcmaster',
    date: new Date('2023-03-14T10:15:00'),
    views: 3201,
    comments: 42,
    likes: 87,
    category: 'etc'
  },
  {
    id: 3,
    title: '이 게임 버그 제보합니다 (스크린샷 포함)',
    author: 'bugfinder',
    date: new Date('2023-03-13T18:45:00'),
    views: 987,
    comments: 15,
    likes: 32,
    category: 'question'
  },
  {
    id: 4,
    title: '게임 개발자로 취업하는 방법',
    author: 'devhopeful',
    date: new Date('2023-03-12T09:20:00'),
    views: 2154,
    comments: 28,
    likes: 63,
    category: 'discussion'
  },
  {
    id: 5,
    title: '웃긴 게임 플레이 상황 모음',
    author: 'funplayer',
    date: new Date('2023-03-11T21:10:00'),
    views: 4321,
    comments: 51,
    likes: 128,
    category: 'humor'
  }
]);

// 카테고리 이름 변환 함수
const getCategoryName = (category) => {
  const categories = {
    'discussion': '토론',
    'humor': '유머',
    'question': '질문',
    'etc': '기타'
  };
  return categories[category] || '';
};

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
.post-container {
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

.sort-select, .category-select {
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

.post-list {
  margin-bottom: 30px;
}

.post-item {
  background-color: white;
  border-radius: 12px;
  padding: 20px;
  margin-bottom: 15px;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.05);
  transition: all 0.2s;
}

.post-item:hover {
  transform: translateY(-3px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
}

.post-header {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.post-category {
  padding: 5px 10px;
  border-radius: 20px;
  font-size: 0.8rem;
  font-weight: 600;
  margin-right: 15px;
}

.post-category.discussion {
  background-color: #e3f2fd;
  color: #1976d2;
}

.post-category.humor {
  background-color: #f8bbd0;
  color: #c2185b;
}

.post-category.question {
  background-color: #e8f5e9;
  color: #388e3c;
}

.post-category.etc {
  background-color: #f5f5f5;
  color: #616161;
}

.post-title {
  font-size: 1.2rem;
  font-weight: 600;
  color: rgba(0, 0, 0, 0.85);
}

.post-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.post-author {
  display: flex;
  align-items: center;
}

.author-avatar {
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

.author-name {
  font-size: 0.95rem;
  font-weight: 500;
  color: rgba(0, 0, 0, 0.7);
}

.post-info {
  display: flex;
  gap: 15px;
}

.post-date, .post-views, .post-comments, .post-likes {
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
</style>

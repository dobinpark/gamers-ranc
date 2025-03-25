<template>
  <div class="qna-container">
    <Header>
      <div class="board-container">
        <div class="board-header">
          <h1 class="board-title">Q&A 게시판</h1>
          <p class="board-description">게임 관련 궁금한 점을 질문하고 답변을 받는 공간입니다.</p>
        </div>

        <div class="board-actions">
          <div class="search-filter-container">
            <div class="filter-container">
              <select v-model="sortOption" class="sort-select">
                <option value="latest">최신순</option>
                <option value="unsolved">미해결 먼저</option>
                <option value="popular">인기순</option>
              </select>
              <select v-model="topicOption" class="topic-select">
                <option value="all">전체 주제</option>
                <option value="bug">버그/오류</option>
                <option value="gameplay">게임플레이</option>
                <option value="technical">기술 지원</option>
                <option value="other">기타</option>
              </select>
            </div>
            <div class="search-container">
              <input type="text" v-model="searchQuery" placeholder="검색어를 입력하세요" class="search-input">
              <button class="search-button">검색</button>
            </div>
          </div>
          <button class="write-button">질문하기</button>
        </div>

        <div class="qna-list">
          <div class="qna-item" v-for="qna in qnas" :key="qna.id">
            <div class="qna-left">
              <div class="qna-status" :class="{ 'solved': qna.solved, 'unsolved': !qna.solved }">
                {{ qna.solved ? '해결됨' : '미해결' }}
              </div>
              <div class="qna-votes">
                <div class="vote-count">{{ qna.votes }}</div>
                <div class="vote-label">추천</div>
              </div>
              <div class="answer-count" :class="{ 'has-answers': qna.answers > 0 }">
                <div class="count">{{ qna.answers }}</div>
                <div class="label">답변</div>
              </div>
            </div>
            <div class="qna-right">
              <div class="qna-header">
                <div class="qna-topic" :class="qna.topic">{{ getTopicName(qna.topic) }}</div>
                <h3 class="qna-title">{{ qna.title }}</h3>
              </div>
              <p class="qna-summary">{{ qna.summary }}</p>
              <div class="qna-meta">
                <div class="qna-tags">
                  <span class="tag" v-for="(tag, index) in qna.tags" :key="index">{{ tag }}</span>
                </div>
                <div class="qna-info">
                  <div class="author-info">
                    <span class="author-avatar" :style="{ backgroundColor: getAvatarColor(qna.author) }">
                      {{ qna.author.charAt(0).toUpperCase() }}
                    </span>
                    <span class="author-name">{{ qna.author }}</span>
                  </div>
                  <span class="qna-date">{{ formatDate(qna.date) }}</span>
                  <span class="qna-views">조회 {{ qna.views }}</span>
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
const topicOption = ref('all');

// 샘플 Q&A 데이터
const qnas = ref([
  {
    id: 1,
    title: '엘든 링에서 초반에 쉽게 얻을 수 있는 좋은 무기가 있을까요?',
    summary: '엘든 링을 처음 시작했는데 초반에 얻을 수 있는 좋은 무기 추천 부탁드립니다. 현재 기본 무기로 플레이 중인데 너무 어려워요.',
    author: 'newgamer',
    date: new Date('2023-03-15T14:30:00'),
    views: 542,
    votes: 15,
    answers: 8,
    solved: true,
    topic: 'gameplay',
    tags: ['엘든링', '초보자', '무기추천']
  },
  {
    id: 2,
    title: '발로란트 게임이 갑자기 튕기는 현상이 발생합니다.',
    summary: '발로란트 게임 중 갑자기 튕기는 현상이 자주 발생합니다. 그래픽 드라이버는 최신 버전으로 업데이트했고 PC 사양도 권장 사양 이상입니다. 해결 방법 아시는 분 있을까요?',
    author: 'valorantfan',
    date: new Date('2023-03-14T10:15:00'),
    views: 421,
    votes: 7,
    answers: 3,
    solved: false,
    topic: 'bug',
    tags: ['발로란트', '버그', '튕김현상']
  },
  {
    id: 3,
    title: '게임 개발을 시작하고 싶은데 어떤 엔진이 좋을까요?',
    summary: '게임 개발에 입문하려고 합니다. Unity와 Unreal Engine 중 초보자에게 더 적합한 엔진은 무엇일까요? 간단한 2D 게임부터 시작하려고 합니다.',
    author: 'wannabedev',
    date: new Date('2023-03-13T18:45:00'),
    views: 887,
    votes: 25,
    answers: 12,
    solved: true,
    topic: 'technical',
    tags: ['게임개발', 'Unity', 'Unreal', '입문']
  },
  {
    id: 4,
    title: '옵시디언에서 다크소울 같은 전투 시스템을 구현했나요?',
    summary: '옵시디언 게임을 구매할까 고민 중인데, 다크소울 같은 전투 시스템이 있는지 궁금합니다. 영상으로만 봐서는 정확히 알기 어렵네요.',
    author: 'gamer123',
    date: new Date('2023-03-12T09:20:00'),
    views: 354,
    votes: 5,
    answers: 2,
    solved: false,
    topic: 'other',
    tags: ['옵시디언', '다크소울', '전투시스템']
  }
]);

// 토픽 이름 변환 함수
const getTopicName = (topic: string): string => {
  const topics: Record<string, string> = {
    'bug': '버그/오류',
    'gameplay': '게임플레이',
    'technical': '기술 지원',
    'other': '기타'
  };
  return topics[topic] || '';
};

// 날짜 포맷 함수
const formatDate = (date: Date): string => {
  const now = new Date();
  const diff = now.getTime() - date.getTime();
  
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
const getAvatarColor = (name: string): string => {
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
.qna-container {
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

.sort-select, .topic-select {
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

.qna-list {
  margin-bottom: 30px;
}

.qna-item {
  display: flex;
  background-color: white;
  border-radius: 12px;
  padding: 20px;
  margin-bottom: 15px;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.05);
  transition: all 0.2s;
}

.qna-item:hover {
  transform: translateY(-3px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
}

.qna-left {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-right: 20px;
  min-width: 100px;
}

.qna-status {
  font-size: 0.8rem;
  font-weight: 600;
  padding: 4px 10px;
  border-radius: 12px;
  margin-bottom: 10px;
  text-align: center;
  width: 100%;
}

.qna-status.solved {
  background-color: #e8f5e9;
  color: #388e3c;
}

.qna-status.unsolved {
  background-color: #ffebee;
  color: #d32f2f;
}

.qna-votes, .answer-count {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 10px;
}

.vote-count, .count {
  font-size: 1.2rem;
  font-weight: 700;
  color: rgba(0, 0, 0, 0.8);
}

.vote-label, .label {
  font-size: 0.8rem;
  color: rgba(0, 0, 0, 0.5);
}

.answer-count.has-answers .count {
  color: #1976d2;
}

.qna-right {
  flex: 1;
}

.qna-header {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.qna-topic {
  padding: 4px 10px;
  border-radius: 12px;
  font-size: 0.8rem;
  font-weight: 600;
  margin-right: 12px;
}

.qna-topic.bug {
  background-color: #ffebee;
  color: #d32f2f;
}

.qna-topic.gameplay {
  background-color: #e3f2fd;
  color: #1976d2;
}

.qna-topic.technical {
  background-color: #e8f5e9;
  color: #388e3c;
}

.qna-topic.other {
  background-color: #f5f5f5;
  color: #616161;
}

.qna-title {
  font-size: 1.2rem;
  font-weight: 600;
  color: rgba(0, 0, 0, 0.85);
  margin: 0;
}

.qna-summary {
  margin: 0 0 15px 0;
  color: rgba(0, 0, 0, 0.7);
  line-height: 1.6;
}

.qna-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.qna-tags {
  display: flex;
  gap: 8px;
}

.tag {
  padding: 4px 8px;
  border-radius: 4px;
  background-color: #f5f5f5;
  color: rgba(0, 0, 0, 0.6);
  font-size: 0.8rem;
}

.qna-info {
  display: flex;
  align-items: center;
  gap: 15px;
}

.author-info {
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

.qna-date, .qna-views {
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
  .qna-item {
    flex-direction: column;
  }
  
  .qna-left {
    flex-direction: row;
    margin-right: 0;
    margin-bottom: 15px;
    justify-content: space-between;
    width: 100%;
  }
  
  .qna-meta {
    flex-direction: column;
    align-items: flex-start;
    gap: 10px;
  }
}
</style>

<template>
  <div class="notice-container">
    <Header>
      <div class="board-container">
        <div class="board-header">
          <h1 class="board-title">공지사항</h1>
          <p class="board-description">게이머스 랭크의 중요 소식과 업데이트 정보를 확인하세요.</p>
        </div>

        <div class="board-actions">
          <div class="search-filter-container">
            <div class="filter-container">
              <select v-model="sortOption" class="sort-select">
                <option value="latest">최신순</option>
                <option value="views">조회순</option>
              </select>
              <select v-model="categoryOption" class="category-select">
                <option value="all">전체</option>
                <option value="update">업데이트</option>
                <option value="event">이벤트</option>
                <option value="system">시스템</option>
                <option value="important">중요</option>
              </select>
            </div>
            <div class="search-container">
              <input type="text" v-model="searchQuery" placeholder="검색어를 입력하세요" class="search-input">
              <button class="search-button">검색</button>
            </div>
          </div>
        </div>

        <div class="notice-list">
          <div class="notice-item" v-for="notice in notices" :key="notice.id" :class="{ 'important': notice.important }">
            <div class="notice-badge" v-if="notice.important">중요</div>
            <div class="notice-header">
              <div class="notice-category" :class="notice.category">{{ getCategoryName(notice.category) }}</div>
              <h3 class="notice-title">{{ notice.title }}</h3>
            </div>
            <div class="notice-content" v-if="notice.expanded">
              <div class="notice-text" v-html="notice.content"></div>
              <div class="notice-attachments" v-if="notice.attachments && notice.attachments.length > 0">
                <h4>첨부파일</h4>
                <div class="attachment-list">
                  <div class="attachment-item" v-for="(file, index) in notice.attachments" :key="index">
                    <span class="attachment-icon">📎</span>
                    <span class="attachment-name">{{ file.name }}</span>
                    <span class="attachment-size">({{ formatFileSize(file.size) }})</span>
                  </div>
                </div>
              </div>
            </div>
            <div class="notice-meta">
              <div class="notice-info">
                <span class="notice-date">{{ formatDate(notice.date) }}</span>
                <span class="notice-author">작성자: {{ notice.author }}</span>
                <span class="notice-views">조회 {{ notice.views }}</span>
              </div>
              <button class="expand-button" @click="toggleExpand(notice)">
                {{ notice.expanded ? '접기' : '자세히 보기' }}
              </button>
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

// 샘플 공지사항 데이터
const notices = ref([
  {
    id: 1,
    title: '3월 30일 서버 점검 안내',
    content: `<p>안녕하세요, 게이머스 랭크입니다.</p>
      <p>더 나은 서비스 제공을 위해 아래와 같이 서버 점검을 진행할 예정입니다.</p>
      <ul>
        <li>점검 일시: 2023년 3월 30일 오전 2시 ~ 오전 6시 (4시간)</li>
        <li>점검 내용: 시스템 안정화 및 성능 개선</li>
        <li>점검 영향: 점검 시간 동안 서비스 이용 불가</li>
      </ul>
      <p>이용에 불편을 드려 죄송합니다. 더 나은 서비스로 찾아뵙겠습니다.</p>
      <p>감사합니다.</p>`,
    author: 'admin',
    date: new Date('2023-03-29T10:00:00'),
    views: 1245,
    category: 'system',
    important: true,
    expanded: false,
    attachments: [
      { name: '서버점검_상세내용.pdf', size: 512000 }
    ]
  },
  {
    id: 2,
    title: '게이머스 랭크 봄 이벤트 안내',
    content: `<p>안녕하세요, 게이머스 랭크입니다.</p>
      <p>봄을 맞아 특별 이벤트를 진행합니다!</p>
      <h4>🌸 봄맞이 활동 이벤트 🌸</h4>
      <p>기간: 2023년 3월 20일 ~ 4월 20일</p>
      <p>내용:</p>
      <ul>
        <li>매일 로그인 시 포인트 지급</li>
        <li>게시글 작성 시 추가 포인트 지급</li>
        <li>봄 테마 프로필 아이콘 무료 제공</li>
      </ul>
      <p>많은 참여 부탁드립니다!</p>`,
    author: 'admin',
    date: new Date('2023-03-20T09:30:00'),
    views: 2874,
    category: 'event',
    important: false,
    expanded: false,
    attachments: [
      { name: '봄이벤트_포스터.jpg', size: 2048000 },
      { name: '이벤트_규정.pdf', size: 356000 }
    ]
  },
  {
    id: 3,
    title: '게이머스 랭크 v2.0 업데이트 안내',
    content: `<p>안녕하세요, 게이머스 랭크입니다.</p>
      <p>게이머스 랭크가 v2.0으로 업데이트됩니다!</p>
      <h4>주요 업데이트 내용</h4>
      <ul>
        <li>새로운 UI/UX 디자인 적용</li>
        <li>게임 리뷰 시스템 개선</li>
        <li>실시간 채팅 기능 추가</li>
        <li>게임 랭킹 시스템 도입</li>
        <li>성능 및 보안 강화</li>
      </ul>
      <p>더 나은 서비스로 찾아뵙겠습니다. 감사합니다.</p>`,
    author: 'admin',
    date: new Date('2023-03-15T11:00:00'),
    views: 4562,
    category: 'update',
    important: true,
    expanded: false,
    attachments: [
      { name: '업데이트_전체내용.pdf', size: 1024000 },
      { name: '가이드라인.pdf', size: 768000 }
    ]
  },
  {
    id: 4,
    title: '개인정보 처리방침 변경 안내',
    content: `<p>안녕하세요, 게이머스 랭크입니다.</p>
      <p>당사의 개인정보 처리방침이 2023년 4월 1일부터 변경됨을 안내드립니다.</p>
      <h4>주요 변경사항</h4>
      <ul>
        <li>개인정보 수집 항목 명확화</li>
        <li>개인정보 보관 기간 및 파기 절차 구체화</li>
        <li>개인정보 제3자 제공 관련 사항 수정</li>
        <li>이용자 권리 행사 방법 추가</li>
      </ul>
      <p>자세한 내용은 첨부파일을 확인해주세요.</p>
      <p>감사합니다.</p>`,
    author: 'admin',
    date: new Date('2023-03-10T14:00:00'),
    views: 1876,
    category: 'important',
    important: true,
    expanded: false,
    attachments: [
      { name: '개인정보처리방침_변경사항.pdf', size: 890000 },
      { name: '개인정보처리방침_전문.pdf', size: 1250000 }
    ]
  }
]);

// 공지사항 펼치기/접기 토글
const toggleExpand = (notice: any) => {
  notice.expanded = !notice.expanded;
};

// 카테고리 이름 변환 함수
const getCategoryName = (category: string): string => {
  const categories: Record<string, string> = {
    'update': '업데이트',
    'event': '이벤트',
    'system': '시스템',
    'important': '중요'
  };
  return categories[category] || '';
};

// 날짜 포맷 함수
const formatDate = (date: Date): string => {
  return `${date.getFullYear()}.${String(date.getMonth() + 1).padStart(2, '0')}.${String(date.getDate()).padStart(2, '0')}`;
};

// 파일 크기 포맷 함수
const formatFileSize = (bytes: number): string => {
  if (bytes === 0) return '0 Bytes';
  const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
  const i = Math.floor(Math.log(bytes) / Math.log(1024));
  return parseFloat((bytes / Math.pow(1024, i)).toFixed(2)) + ' ' + sizes[i];
};
</script>

<style scoped>
.notice-container {
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
  justify-content: flex-end;
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

.notice-list {
  margin-bottom: 30px;
}

.notice-item {
  position: relative;
  background-color: white;
  border-radius: 12px;
  padding: 20px;
  margin-bottom: 15px;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.05);
  transition: all 0.2s;
  overflow: hidden;
}

.notice-item:hover {
  transform: translateY(-3px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
}

.notice-item.important {
  border-left: 4px solid #f44336;
}

.notice-badge {
  position: absolute;
  top: 20px;
  right: 20px;
  background-color: #f44336;
  color: white;
  font-size: 0.8rem;
  font-weight: 600;
  padding: 4px 10px;
  border-radius: 12px;
}

.notice-header {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
  padding-right: 70px;
}

.notice-category {
  padding: 4px 10px;
  border-radius: 12px;
  font-size: 0.8rem;
  font-weight: 600;
  margin-right: 12px;
}

.notice-category.update {
  background-color: #e3f2fd;
  color: #1976d2;
}

.notice-category.event {
  background-color: #f3e5f5;
  color: #7b1fa2;
}

.notice-category.system {
  background-color: #fff3e0;
  color: #e65100;
}

.notice-category.important {
  background-color: #ffebee;
  color: #d32f2f;
}

.notice-title {
  font-size: 1.2rem;
  font-weight: 600;
  color: rgba(0, 0, 0, 0.85);
  margin: 0;
}

.notice-content {
  margin-bottom: 20px;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
}

.notice-text {
  color: rgba(0, 0, 0, 0.7);
  line-height: 1.6;
}

.notice-text p {
  margin: 10px 0;
}

.notice-text h4 {
  margin: 15px 0 10px;
  color: #1976d2;
}

.notice-text ul {
  margin: 10px 0;
  padding-left: 20px;
}

.notice-text li {
  margin-bottom: 5px;
}

.notice-attachments {
  margin-top: 20px;
  padding-top: 15px;
  border-top: 1px solid rgba(0, 0, 0, 0.1);
}

.notice-attachments h4 {
  font-size: 1rem;
  font-weight: 600;
  margin-bottom: 10px;
  color: rgba(0, 0, 0, 0.7);
}

.attachment-list {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.attachment-item {
  display: flex;
  align-items: center;
  padding: 8px 12px;
  background-color: white;
  border-radius: 6px;
  border: 1px solid rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: all 0.2s;
}

.attachment-item:hover {
  background-color: #f5f5f5;
}

.attachment-icon {
  margin-right: 8px;
}

.attachment-name {
  font-size: 0.9rem;
  color: #1976d2;
  margin-right: 8px;
}

.attachment-size {
  font-size: 0.8rem;
  color: rgba(0, 0, 0, 0.5);
}

.notice-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.notice-info {
  display: flex;
  align-items: center;
  gap: 15px;
}

.notice-date, .notice-author, .notice-views {
  font-size: 0.85rem;
  color: rgba(0, 0, 0, 0.5);
}

.expand-button {
  padding: 6px 12px;
  border-radius: 6px;
  border: 1px solid rgba(0, 0, 0, 0.1);
  background-color: white;
  color: #1976d2;
  font-size: 0.9rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
}

.expand-button:hover {
  background-color: #f5f5f5;
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
  .notice-header {
    flex-direction: column;
    align-items: flex-start;
  }
  
  .notice-category {
    margin-bottom: 8px;
  }
  
  .notice-meta {
    flex-direction: column;
    align-items: flex-start;
    gap: 10px;
  }
  
  .notice-info {
    flex-wrap: wrap;
    gap: 10px;
  }
  
  .expand-button {
    width: 100%;
  }
}
</style>

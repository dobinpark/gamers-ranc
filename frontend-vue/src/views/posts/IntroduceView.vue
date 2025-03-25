<template>
  <div class="introduce-container">
    <Header>
      <div class="intro-content">
        <!-- 히어로 섹션 -->
        <div class="hero-section">
          <div class="hero-content">
            <h1 class="hero-title">게임의 세계로 오신 것을 환영합니다</h1>
            <p class="hero-subtitle">다양한 장르의 게임을 소개하고 추천해드립니다</p>
          </div>
        </div>

        <!-- 소개 섹션 -->
        <div class="about-section">
          <h2 class="section-title">Gamer's RanC 소개</h2>
          <div class="about-content">
            <img class="about-image" src="../../assets/image/gamersRancthumbnail.png" alt="Gamer's RanC 소개 이미지">
            <div class="about-text">
              <p>Gamer's RanC는 게이머들을 위한 종합 커뮤니티 플랫폼입니다.</p>
              <p>다양한 게임 정보와 리뷰, 유저들의 솔직한 의견을 한 곳에서 만나보세요.</p>
              <p>최신 게임 트렌드부터 클래식 게임까지, 모든 게이머를 위한 공간입니다.</p>
              <div class="features">
                <div class="feature">
                  <div class="feature-icon">🎮</div>
                  <div class="feature-title">다양한 게임 소개</div>
                  <div class="feature-desc">최신 게임부터 고전 명작까지</div>
                </div>
                <div class="feature">
                  <div class="feature-icon">⭐</div>
                  <div class="feature-title">유저 리뷰</div>
                  <div class="feature-desc">실제 플레이어들의 솔직한 평가</div>
                </div>
                <div class="feature">
                  <div class="feature-icon">💬</div>
                  <div class="feature-title">활발한 커뮤니티</div>
                  <div class="feature-desc">같은 취향의 게이머들과 소통</div>
                </div>
                <div class="feature">
                  <div class="feature-icon">🏆</div>
                  <div class="feature-title">랭킹 시스템</div>
                  <div class="feature-desc">장르별 인기 게임 순위 제공</div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 추천 게임 섹션 -->
        <div class="games-section">
          <h2 class="section-title">추천 게임</h2>
          <div class="games-filter">
            <button class="filter-button active">전체</button>
            <button class="filter-button">RPG</button>
            <button class="filter-button">FPS</button>
            <button class="filter-button">액션</button>
            <button class="filter-button">어드벤처</button>
            <button class="filter-button">전략</button>
          </div>
          <div class="games-grid">
            <div class="game-card" v-for="game in featuredGames" :key="game.id">
              <div class="game-image" :style="{ backgroundImage: `url(${game.image})` }">
                <div class="game-platform">{{ game.platform }}</div>
                <div class="game-rating">★ {{ game.rating }}</div>
              </div>
              <div class="game-info">
                <h3 class="game-title">{{ game.title }}</h3>
                <p class="game-genre">{{ game.genre }}</p>
                <p class="game-desc">{{ game.description }}</p>
                <button class="game-details-btn">자세히 보기</button>
              </div>
            </div>
          </div>
          <div class="view-more">
            <button class="view-more-btn">더 많은 게임 보기</button>
          </div>
        </div>

        <!-- 개발사 소개 섹션 -->
        <div class="studios-section">
          <h2 class="section-title">주요 게임 개발사</h2>
          <div class="studios-grid">
            <div class="studio-card" v-for="studio in topStudios" :key="studio.id">
              <div class="studio-logo" :style="{ backgroundColor: studio.color }">
                {{ studio.name.charAt(0) }}
              </div>
              <h3 class="studio-name">{{ studio.name }}</h3>
              <p class="studio-specialty">{{ studio.specialty }}</p>
              <p class="studio-desc">{{ studio.description }}</p>
              <div class="studio-games">
                <span>대표작:</span>
                <span class="famous-games">{{ studio.famousGames.join(', ') }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- FAQ 섹션 -->
        <div class="faq-section">
          <h2 class="section-title">자주 묻는 질문</h2>
          <div class="faq-list">
            <div class="faq-item" v-for="(faq, index) in faqs" :key="index" :class="{ 'active': faq.expanded }">
              <div class="faq-question" @click="toggleFaq(faq)">
                <h3>{{ faq.question }}</h3>
                <div class="faq-toggle">{{ faq.expanded ? '−' : '+' }}</div>
              </div>
              <div class="faq-answer" v-if="faq.expanded">
                <p>{{ faq.answer }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </Header>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import Header from '../../components/Header.vue';

// 추천 게임 데이터
const featuredGames = ref([
  {
    id: 1,
    title: '엘든 링',
    genre: 'RPG',
    platform: 'PC/콘솔',
    rating: 4.8,
    image: 'https://via.placeholder.com/300x180/1976d2/ffffff?text=Elden+Ring',
    description: '프롬소프트웨어의 오픈월드 액션 RPG. 광활한 세계와 깊이 있는 전투 시스템을 경험해보세요.'
  },
  {
    id: 2,
    title: '발로란트',
    genre: 'FPS',
    platform: 'PC',
    rating: 4.5,
    image: 'https://via.placeholder.com/300x180/9c27b0/ffffff?text=Valorant',
    description: '라이엇 게임즈의 전술적 5v5 슈팅 게임. 각 요원의 고유한 능력과 깔끔한 타격감이 특징입니다.'
  },
  {
    id: 3,
    title: '젤다의 전설: 티어스 오브 더 킹덤',
    genre: '어드벤처',
    platform: '닌텐도 스위치',
    rating: 4.9,
    image: 'https://via.placeholder.com/300x180/4caf50/ffffff?text=Zelda',
    description: '하이랄 왕국을 넘어 하늘까지 모험을 떠나는 젤다 시리즈의 최신작. 자유로운 탐험과 다양한 능력이 특징입니다.'
  },
  {
    id: 4,
    title: '데빌 메이 크라이 5',
    genre: '액션',
    platform: 'PC/콘솔',
    rating: 4.6,
    image: 'https://via.placeholder.com/300x180/f44336/ffffff?text=DMC5',
    description: '화려한 콤보 액션과 스타일리쉬한 전투가 매력적인 캡콤의 인기 시리즈 최신작입니다.'
  },
  {
    id: 5,
    title: '스타크래프트 2',
    genre: '전략',
    platform: 'PC',
    rating: 4.7,
    image: 'https://via.placeholder.com/300x180/ff9800/ffffff?text=Starcraft+2',
    description: '블리자드의 대표적인 실시간 전략 게임. 테란, 저그, 프로토스 세 종족의 전략적 대결을 경험해보세요.'
  },
  {
    id: 6,
    title: '사이버펑크 2077',
    genre: 'RPG',
    platform: 'PC/콘솔',
    rating: 4.3,
    image: 'https://via.placeholder.com/300x180/ffeb3b/000000?text=Cyberpunk+2077',
    description: '나이트 시티를 배경으로 한 오픈월드 RPG. 자유로운 선택과 사이버펑크 세계관을 만끽할 수 있습니다.'
  }
]);

// 게임 개발사 데이터
const topStudios = ref([
  {
    id: 1,
    name: '프롬소프트웨어',
    color: '#1976d2',
    specialty: '액션 RPG',
    description: '다크소울, 엘든 링으로 유명한 일본의 게임 개발사. 도전적인 난이도와 깊이 있는 세계관이 특징입니다.',
    famousGames: ['다크소울 시리즈', '엘든 링', '세키로', '블러드본']
  },
  {
    id: 2,
    name: '라이엇 게임즈',
    color: '#f44336',
    specialty: '온라인 멀티플레이어',
    description: '리그 오브 레전드로 시작해 발로란트까지, 경쟁적인 멀티플레이어 게임을 개발하는 미국 기업입니다.',
    famousGames: ['리그 오브 레전드', '발로란트', '전략적 팀 전투', '레전드 오브 룬테라']
  },
  {
    id: 3,
    name: '닌텐도',
    color: '#4caf50',
    specialty: '콘솔 게임',
    description: '마리오, 젤다의 전설 등 수많은 인기 시리즈를 보유한 일본의 게임 개발 및 하드웨어 기업입니다.',
    famousGames: ['마리오 시리즈', '젤다의 전설 시리즈', '포켓몬스터', '동물의 숲']
  },
  {
    id: 4,
    name: '블리자드',
    color: '#9c27b0',
    specialty: 'RTS, MMORPG',
    description: '스타크래프트, 워크래프트, 디아블로 등 다양한 장르의 인기 게임을 개발한 미국 기업입니다.',
    famousGames: ['스타크래프트', '워크래프트', '디아블로', '오버워치']
  }
]);

// FAQ 데이터
const faqs = ref([
  {
    question: '게이머스 랭크에서는 어떤 게임을 소개하나요?',
    answer: '게이머스 랭크에서는 PC, 콘솔, 모바일 등 다양한 플랫폼의 게임을 소개합니다. RPG, FPS, 액션, 어드벤처, 전략 등 모든 장르를 아우르며, 최신 게임부터 클래식 명작까지 폭넓게 다룹니다.',
    expanded: true
  },
  {
    question: '게임 리뷰는 어떻게 작성되나요?',
    answer: '게임 리뷰는 실제 게임을 플레이한 유저들과 전문 에디터들에 의해 작성됩니다. 그래픽, 사운드, 게임플레이, 스토리 등 다양한 측면을 종합적으로 평가하며, 객관적인 시각을 유지하기 위해 노력하고 있습니다.',
    expanded: false
  },
  {
    question: '게이머스 랭크 커뮤니티에 어떻게 참여할 수 있나요?',
    answer: '회원가입 후 자유 게시판, 리뷰 게시판, Q&A 게시판 등에서 활동할 수 있습니다. 다른 게이머들과 의견을 나누고, 게임 팁을 공유하거나 질문에 답변할 수 있습니다. 적극적인 활동은 커뮤니티 포인트로 보상받습니다.',
    expanded: false
  },
  {
    question: '게임 랭킹은 어떤 기준으로 선정되나요?',
    answer: '게임 랭킹은 유저 평점, 리뷰 수, 인기도, 전문가 평가 등을 종합적으로 고려하여 선정됩니다. 장르별, 플랫폼별로 구분된 랭킹을 제공하여 사용자가 원하는 정보를 쉽게 찾을 수 있도록 하고 있습니다.',
    expanded: false
  },
  {
    question: '게임 소개 페이지에서 제공하는 정보는 무엇인가요?',
    answer: '게임 소개 페이지에서는 게임의 기본 정보(출시일, 개발사, 장르, 플랫폼 등), 게임플레이 설명, 스크린샷과 트레일러, 시스템 요구사항, 유저 및 전문가 리뷰, 관련 뉴스와 가이드 등을 제공합니다.',
    expanded: false
  }
]);

// FAQ 토글 함수
const toggleFaq = (faq: any) => {
  faq.expanded = !faq.expanded;
};

// 소개 이미지 URL (원하는 이미지 URL로 변경)
const aboutImage = ref('@/assets/image/gamersRancthumbnail.png');
</script>

<style scoped>
.introduce-container {
  width: 100%;
  min-height: 100vh;
}

.intro-content {
  width: 100%;
}

/* 히어로 섹션 스타일 */
.hero-section {
  background: linear-gradient(135deg, #1976d2 0%, #64b5f6 100%);
  color: white;
  padding: 80px 20px;
  text-align: center;
  margin-bottom: 60px;
}

.hero-content {
  max-width: 1000px;
  margin: 0 auto;
}

.hero-title {
  font-size: 3.5rem;
  font-weight: 800;
  margin-bottom: 20px;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.hero-subtitle {
  font-size: 1.5rem;
  font-weight: 400;
  opacity: 0.9;
}

/* 섹션 공통 스타일 */
.section-title {
  font-size: 2.5rem;
  font-weight: 700;
  color: #1976d2;
  text-align: center;
  margin-bottom: 40px;
}

/* 소개 섹션 스타일 */
.about-section {
  max-width: 1200px;
  margin: 0 auto 80px;
  padding: 0 20px;
}

.about-content {
  display: flex;
  flex-wrap: wrap;
  gap: 40px;
  align-items: center;
}

.about-image {
  flex: 1;
  min-width: 300px;
  height: 400px;
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  object-fit: cover;
  object-position: center;
}

.about-text {
  flex: 1;
  min-width: 300px;
}

.about-text p {
  font-size: 1.1rem;
  color: rgba(0, 0, 0, 0.7);
  line-height: 1.7;
  margin-bottom: 15px;
}

.features {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  margin-top: 30px;
}

.feature {
  flex: 1;
  min-width: 200px;
  padding: 20px;
  background-color: white;
  border-radius: 12px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
  text-align: center;
  transition: transform 0.3s, box-shadow 0.3s;
}

.feature:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
}

.feature-icon {
  font-size: 2.5rem;
  margin-bottom: 15px;
}

.feature-title {
  font-size: 1.1rem;
  font-weight: 600;
  color: #1976d2;
  margin-bottom: 8px;
}

.feature-desc {
  font-size: 0.9rem;
  color: rgba(0, 0, 0, 0.6);
}

/* 게임 섹션 스타일 */
.games-section {
  max-width: 1200px;
  margin: 0 auto 80px;
  padding: 0 20px;
}

.games-filter {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 30px;
}

.filter-button {
  padding: 8px 16px;
  border: 1px solid rgba(0, 0, 0, 0.1);
  border-radius: 20px;
  background-color: white;
  color: rgba(0, 0, 0, 0.7);
  font-size: 0.9rem;
  cursor: pointer;
  transition: all 0.2s;
}

.filter-button:hover,
.filter-button.active {
  background-color: #1976d2;
  color: white;
}

.games-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 30px;
  margin-bottom: 40px;
}

.game-card {
  background-color: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
  transition: transform 0.3s, box-shadow 0.3s;
}

.game-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
}

.game-image {
  height: 200px;
  background-size: cover;
  background-position: center;
  position: relative;
}

.game-platform {
  position: absolute;
  top: 15px;
  left: 15px;
  background-color: rgba(0, 0, 0, 0.6);
  color: white;
  padding: 5px 10px;
  border-radius: 5px;
  font-size: 0.8rem;
}

.game-rating {
  position: absolute;
  top: 15px;
  right: 15px;
  background-color: rgba(255, 193, 7, 0.9);
  color: rgba(0, 0, 0, 0.8);
  padding: 5px 10px;
  border-radius: 5px;
  font-size: 0.9rem;
  font-weight: 600;
}

.game-info {
  padding: 20px;
}

.game-title {
  font-size: 1.3rem;
  font-weight: 700;
  color: rgba(0, 0, 0, 0.85);
  margin-bottom: 5px;
}

.game-genre {
  font-size: 0.9rem;
  color: #1976d2;
  margin-bottom: 15px;
  font-weight: 500;
}

.game-desc {
  font-size: 0.95rem;
  color: rgba(0, 0, 0, 0.7);
  line-height: 1.5;
  margin-bottom: 20px;
  height: 60px;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.game-details-btn {
  width: 100%;
  padding: 10px;
  border: none;
  border-radius: 8px;
  background-color: #1976d2;
  color: white;
  font-size: 0.95rem;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.2s;
}

.game-details-btn:hover {
  background-color: #1565c0;
}

.view-more {
  text-align: center;
}

.view-more-btn {
  padding: 12px 30px;
  border: none;
  border-radius: 30px;
  background-color: white;
  color: #1976d2;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
}

.view-more-btn:hover {
  background-color: #1976d2;
  color: white;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
}

/* 개발사 섹션 스타일 */
.studios-section {
  background-color: #f8f9fa;
  padding: 80px 20px;
  margin-bottom: 80px;
}

.studios-grid {
  max-width: 1200px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 30px;
}

.studio-card {
  background-color: white;
  border-radius: 12px;
  padding: 30px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
  transition: transform 0.3s, box-shadow 0.3s;
}

.studio-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
}

.studio-logo {
  width: 70px;
  height: 70px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 2rem;
  font-weight: 700;
  color: white;
  margin-bottom: 20px;
}

.studio-name {
  font-size: 1.3rem;
  font-weight: 700;
  color: rgba(0, 0, 0, 0.85);
  margin-bottom: 5px;
}

.studio-specialty {
  font-size: 0.9rem;
  color: #1976d2;
  margin-bottom: 15px;
  font-weight: 500;
}

.studio-desc {
  font-size: 0.95rem;
  color: rgba(0, 0, 0, 0.7);
  margin-bottom: 20px;
  line-height: 1.5;
}

.studio-games {
  font-size: 0.9rem;
  color: rgba(0, 0, 0, 0.6);
}

.famous-games {
  color: rgba(0, 0, 0, 0.8);
  font-weight: 500;
}

/* FAQ 섹션 스타일 */
.faq-section {
  max-width: 1000px;
  margin: 0 auto 100px;
  padding: 0 20px;
}

.faq-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.faq-item {
  background-color: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.05);
  transition: box-shadow 0.3s;
}

.faq-item:hover {
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
}

.faq-item.active {
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
}

.faq-question {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 25px;
  cursor: pointer;
}

.faq-question h3 {
  font-size: 1.1rem;
  font-weight: 600;
  color: rgba(0, 0, 0, 0.8);
  margin: 0;
}

.faq-toggle {
  font-size: 1.5rem;
  color: #1976d2;
  font-weight: 600;
}

.faq-answer {
  padding: 0 25px 20px;
  border-top: 1px solid rgba(0, 0, 0, 0.05);
}

.faq-answer p {
  margin: 15px 0 0;
  font-size: 1rem;
  color: rgba(0, 0, 0, 0.7);
  line-height: 1.6;
}

/* 반응형 스타일 */
@media (max-width: 768px) {
  .hero-title {
    font-size: 2.5rem;
  }

  .hero-subtitle {
    font-size: 1.2rem;
  }

  .section-title {
    font-size: 2rem;
  }

  .games-grid {
    grid-template-columns: 1fr;
  }

  .studios-grid {
    grid-template-columns: 1fr;
  }

  .about-content {
    flex-direction: column;
  }

  .about-image {
    width: 100%;
    margin-bottom: 30px;
  }
}
</style>

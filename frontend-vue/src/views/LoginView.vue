<template>
  <div>
    <Header>
      <div class="login-container">
        <div class="login-card">
          <div class="login-header">
            <h2 class="login-title">로그인</h2>
          </div>

          <div class="login-form">
            <div class="form-group">
              <label for="email">이메일</label>
              <input type="email" id="email" v-model="email" class="form-input" placeholder="이메일을 입력하세요"
                :class="{ 'error': emailError }">
              <p v-if="emailError" class="error-message">{{ emailError }}</p>
            </div>

            <div class="form-group">
              <label for="password">비밀번호</label>
              <input type="password" id="password" v-model="password" class="form-input" placeholder="비밀번호를 입력하세요"
                :class="{ 'error': passwordError }">
              <p v-if="passwordError" class="error-message">{{ passwordError }}</p>
            </div>

            <div class="form-group">
              <button @click="login" class="login-button" :disabled="isLoading">
                {{ isLoading ? '로그인 중...' : '로그인' }}
              </button>
            </div>

            <div class="form-group text-center">
              <p>계정이 없으신가요? <router-link to="/signup" class="signup-link">회원가입</router-link></p>
            </div>
          </div>
        </div>
      </div>
    </Header>
  </div>
</template>

<script setup>
import Header from '../components/Header.vue';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const router = useRouter();
const email = ref('');
const password = ref('');
const emailError = ref('');
const passwordError = ref('');
const isLoading = ref(false);

const validateForm = () => {
  let isValid = true;

  // 이메일 검증
  if (!email.value) {
    emailError.value = '이메일을 입력해주세요.';
    isValid = false;
  } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email.value)) {
    emailError.value = '올바른 이메일 형식이 아닙니다.';
    isValid = false;
  } else {
    emailError.value = '';
  }

  // 비밀번호 검증
  if (!password.value) {
    passwordError.value = '비밀번호를 입력해주세요.';
    isValid = false;
  } else {
    passwordError.value = '';
  }

  return isValid;
};

// 로그인 요청
const login = async () => {
  if (!validateForm()) return;

  isLoading.value = true;

  try {
    const response = await axios.post('/auth/login', {
      email: email.value,
      password: password.value
    });

    // 토큰 저장
    localStorage.setItem('token', response.data.token);
    localStorage.setItem('email', response.data.email);

    // 로그인 성공 후 홈으로 이동
    router.push('/');
  } catch (error) {
    if (error.response) {
      // 서버 응답 오류 처리
      if (error.response.status === 401) {
        passwordError.value = '이메일 또는 비밀번호가 일치하지 않습니다.';
      } else {
        passwordError.value = '로그인 중 오류가 발생했습니다. 다시 시도해주세요.';
      }
    } else {
      // 네트워크 오류 등 처리
      passwordError.value = '서버에 연결할 수 없습니다. 네트워크 연결을 확인해주세요.';
    }
  } finally {
    isLoading.value = false;
  }
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: calc(100vh - 70px);
  padding: 40px 20px;
  background-color: #f5f7fa;
}

.login-card {
  width: 100%;
  max-width: 450px;
  background-color: white;
  border-radius: 12px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
  overflow: hidden;
  transition: transform 0.2s, box-shadow 0.2s;
}

.login-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
}

.login-header {
  padding: 25px 30px;
  background-color: #1976d2;
  color: white;
  text-align: center;
}

.login-title {
  font-size: 1.8rem;
  font-weight: 600;
  margin: 0;
}

.login-form {
  padding: 30px;
}

.form-group {
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
  color: rgba(0, 0, 0, 0.7);
}

.form-input {
  width: 100%;
  padding: 12px 15px;
  border: 1px solid rgba(0, 0, 0, 0.15);
  border-radius: 8px;
  font-size: 1rem;
  transition: all 0.2s;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.03);
}

.form-input:focus {
  outline: none;
  border-color: #1976d2;
  box-shadow: 0 2px 10px rgba(25, 118, 210, 0.1);
}

.form-input.error {
  border-color: #ff5252;
  background-color: rgba(255, 82, 82, 0.05);
}

.error-message {
  color: #ff5252;
  font-size: 0.85rem;
  margin-top: 5px;
}

.login-button {
  width: 100%;
  padding: 12px;
  background-color: #1976d2;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s;
  box-shadow: 0 3px 5px rgba(0, 0, 0, 0.1);
}

.login-button:hover {
  background-color: #1565c0;
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.15);
}

.login-button:disabled {
  background-color: #90caf9;
  cursor: not-allowed;
}

.text-center {
  text-align: center;
}

.signup-link {
  color: #1976d2;
  font-weight: 600;
  text-decoration: none;
  transition: color 0.2s;
}

.signup-link:hover {
  color: #1565c0;
  text-decoration: underline;
}
</style>

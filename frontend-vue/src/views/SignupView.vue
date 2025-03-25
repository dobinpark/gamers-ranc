<template>
  <div>
    <Header>
      <div class="signup-container">
        <div class="signup-card">
          <div class="signup-header">
            <h2 class="signup-title">회원가입</h2>
          </div>

          <div class="signup-form">
            <div class="form-group">
              <label for="email">이메일</label>
              <input type="email" id="email" v-model="email" class="form-input" placeholder="이메일을 입력하세요"
                :class="{ 'error': emailError }">
              <p v-if="emailError" class="error-message">{{ emailError }}</p>
            </div>

            <div class="form-group">
              <label for="password">비밀번호</label>
              <input type="password" id="password" v-model="password" class="form-input"
                placeholder="비밀번호를 입력하세요 (8자 이상, 영문자, 숫자, 특수문자 포함)" :class="{ 'error': passwordError }">
              <p v-if="passwordError" class="error-message">{{ passwordError }}</p>
              <p class="help-message">특수문자: ! @ # $ % ^ & * ( ) 만 사용 가능</p>
            </div>

            <div class="form-group">
              <label for="passwordConfirm">비밀번호 확인</label>
              <input type="password" id="passwordConfirm" v-model="passwordConfirm" class="form-input"
                placeholder="비밀번호를 다시 입력하세요" :class="{ 'error': passwordConfirmError }">
              <p v-if="passwordConfirmError" class="error-message">{{ passwordConfirmError }}</p>
            </div>

            <div class="form-group">
              <label for="nickname">닉네임</label>
              <input type="text" id="nickname" v-model="nickname" class="form-input" placeholder="닉네임을 입력하세요 (2-20자)"
                :class="{ 'error': nicknameError }">
              <p v-if="nicknameError" class="error-message">{{ nicknameError }}</p>
            </div>

            <div class="form-group">
              <label for="phoneNumber">전화번호</label>
              <input type="text" id="phoneNumber" v-model="phoneNumber" class="form-input"
                placeholder="전화번호를 입력하세요 (- 없이 11자리)" :class="{ 'error': phoneNumberError }">
              <p v-if="phoneNumberError" class="error-message">{{ phoneNumberError }}</p>
            </div>

            <div class="form-group">
              <button @click="signup" class="signup-button" :disabled="isLoading">
                {{ isLoading ? '가입 중...' : '회원가입' }}
              </button>
            </div>

            <div class="form-group text-center">
              <p>이미 계정이 있으신가요? <router-link to="/login" class="login-link">로그인</router-link></p>
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
const passwordConfirm = ref('');
const nickname = ref('');
const phoneNumber = ref('');
const emailError = ref('');
const passwordError = ref('');
const passwordConfirmError = ref('');
const nicknameError = ref('');
const phoneNumberError = ref('');
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
  } else if (!/^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()])[a-zA-Z\\d!@#$%^&*()]{8,}$/.test(password.value)) {
    passwordError.value = '비밀번호는 8자 이상, 영문자, 숫자, 특수문자를 포함해야 합니다.';
    isValid = false;
  } else {
    passwordError.value = '';
  }

  // 비밀번호 확인 검증
  if (!passwordConfirm.value) {
    passwordConfirmError.value = '비밀번호 확인을 입력해주세요.';
    isValid = false;
  } else if (password.value !== passwordConfirm.value) {
    passwordConfirmError.value = '비밀번호와 비밀번호 확인이 일치하지 않습니다.';
    isValid = false;
  } else {
    passwordConfirmError.value = '';
  }

  // 닉네임 검증
  if (!nickname.value) {
    nicknameError.value = '닉네임을 입력해주세요.';
    isValid = false;
  } else if (nickname.value.length < 2 || nickname.value.length > 20) {
    nicknameError.value = '닉네임은 2자 이상 20자 이하여야 합니다.';
    isValid = false;
  } else {
    nicknameError.value = '';
  }

  // 전화번호 검증
  if (!phoneNumber.value) {
    phoneNumberError.value = '전화번호를 입력해주세요.';
    isValid = false;
  } else if (!/^\d{11}$/.test(phoneNumber.value)) {
    phoneNumberError.value = '전화번호는 11자리 숫자여야 합니다. (- 없이 입력)';
    isValid = false;
  } else {
    phoneNumberError.value = '';
  }

  return isValid;
};

const signup = async () => {
  if (!validateForm()) return;

  isLoading.value = true;

  try {
    await axios.post('/auth/signup', {
      email: email.value,
      password: password.value,
      passwordConfirm: passwordConfirm.value,
      nickname: nickname.value,
      phoneNumber: phoneNumber.value
    });

    // 회원가입 성공 시 로그인 페이지로 이동
    router.push('/login');
  } catch (error) {
    if (error.response) {
      // 서버 응답 오류 처리
      const status = error.response.status;
      const data = error.response.data;

      if (status === 400) {
        // 필드별 오류 메시지 설정
        if (data.message) {
          if (data.message.includes('이메일')) {
            emailError.value = data.message;
          } else if (data.message.includes('닉네임')) {
            nicknameError.value = data.message;
          } else if (data.message.includes('전화번호')) {
            phoneNumberError.value = data.message;
          } else if (data.message.includes('비밀번호')) {
            passwordError.value = data.message;
          } else {
            passwordError.value = data.message;
          }
        } else {
          passwordError.value = '회원가입 중 오류가 발생했습니다. 입력 정보를 확인해주세요.';
        }
      } else {
        passwordError.value = '회원가입 중 오류가 발생했습니다. 다시 시도해주세요.';
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
.signup-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: calc(100vh - 70px);
  padding: 40px 20px;
  background-color: #f5f7fa;
}

.signup-card {
  width: 100%;
  max-width: 500px;
  background-color: white;
  border-radius: 12px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
  overflow: hidden;
  transition: transform 0.2s, box-shadow 0.2s;
}

.signup-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
}

.signup-header {
  padding: 25px 30px;
  background-color: #1976d2;
  color: white;
  text-align: center;
}

.signup-title {
  font-size: 1.8rem;
  font-weight: 600;
  margin: 0;
}

.signup-form {
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

.help-message {
  color: rgba(0, 0, 0, 0.6);
  font-size: 0.8rem;
  margin-top: 3px;
}

.signup-button {
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

.signup-button:hover {
  background-color: #1565c0;
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.15);
}

.signup-button:disabled {
  background-color: #90caf9;
  cursor: not-allowed;
}

.text-center {
  text-align: center;
}

.login-link {
  color: #1976d2;
  font-weight: 600;
  text-decoration: none;
  transition: color 0.2s;
}

.login-link:hover {
  color: #1565c0;
  text-decoration: underline;
}
</style>

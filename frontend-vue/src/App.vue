<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <router-view />
</template>

<style>
/* 전역 스타일: 폰트 렌더링과 텍스트 선명도 향상 */
:root {
  --color-text: rgba(0, 0, 0, 0.87);
  --color-text-secondary: rgba(0, 0, 0, 0.6);
  --color-border: rgba(0, 0, 0, 0.12);
}

html, body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: var(--color-text);
  font-weight: 400;
  line-height: 1.5;
}

h1, h2, h3, h4, h5, h6 {
  font-weight: 700;
  color: var(--color-text);
}

p, div, span, button, a {
  color: var(--color-text);
}

/* Material 디자인 아이콘 선명도 개선 */
.mdi-star, .mdi-forum, .mdi-bell, .mdi-help-circle {
  font-weight: normal !important;
  font-size: 1.25rem !important;
}

.v-card, .v-btn, .v-list-item, button {
  font-weight: 500 !important;
}

.v-btn--contained {
  box-shadow: 0 3px 5px rgba(0, 0, 0, 0.2) !important;
}

.v-list-item-title {
  font-weight: 500 !important;
  color: var(--color-text) !important;
}

.v-list-item-subtitle {
  font-weight: 400 !important;
  color: var(--color-text-secondary) !important;
}

/* 기존 스타일은 유지 */
header {
  line-height: 1.5;
  max-height: 100vh;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

nav {
  width: 100%;
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;
  }
}
</style>

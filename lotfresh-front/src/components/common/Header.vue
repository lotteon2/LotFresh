<template>
  <div>
    <div class="header">
      <div class="header_wrap">
        <router-link to="/">
          <img class="logo_img" src="../../assets/logo.png" />
        </router-link>
        <div class="search_wrap">
          <input class="input_bar" placeholder="검색어를 입력해주세요." />
          <button id="submit" class="search_btn"></button>
        </div>
        <div class="header_icon_wrap">
          <div class="header_icon_1"></div>
          <div class="header_icon_2"></div>
        </div>
      </div>
    </div>
    <Menu />
  </div>
</template>

<script setup lang="ts">
import Menu from "./Menu.vue";
</script>

<style scoped>
.header {
  padding: 10px;
  display: flex;
  justify-content: space-between;
  font-weight: 900;
}

.header_wrap {
  padding-top: 2vh;
  display: flex;
  height: 55px;
  width: 100%;
  position: relative;
}
.search_wrap {
  padding-right: 10px;
  display: flex;
  align-items: center;
  position: relative;
  left: 340px;
  height: 45px;
  border: 1px solid red;
  border-radius: 6px;
  background-color: rgb(255, 255, 255);
  box-shadow: rgb(247, 247, 247) 0px 0px 0px 1px inset;
}

.input_bar {
  padding-top: 5px;
  padding-left: 15px;
  width: 350px;
  background-color: inherit;
  border: none;
  outline: none;
  font-size: 16px;
  letter-spacing: -0.35px;
}

.logo_img {
  left: 30px;
  width: 150px;
  height: auto;
  position: absolute;
}
.search_btn {
  top: 8px;
  right: 15px;
  opacity: 1;
  width: 26px;
  height: 26px;
  background-image: none;
  background: url(https://static.lotteon.com/p/common/foCommon/assets/img/icon_search_mask.png)
    no-repeat;
  border: none;
  background-color: #ef2a23;
  background-size: 26px auto;
  cursor: pointer;
}

.header_icon_wrap {
  top: 5px;
  left: 550px;
  display: flex;
  align-items: center;
  position: relative;
}

.header_icon_1 {
  width: 36px;
  height: 36px;
  margin-right: 10px;
  background: url(data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMzYiIGhlaWdodD0iMzYiIHZpZXdCb3g9IjAgMCAzNiAzNiIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KICAgIDxnIGZpbGw9Im5vbmUiIGZpbGwtcnVsZT0iZXZlbm9kZCI+CiAgICAgICAgPHBhdGggZD0iTTM2IDM2SDBWMGgzNnoiLz4KICAgICAgICA8ZyB0cmFuc2Zvcm09InRyYW5zbGF0ZSg4LjcgNikiIHN0cm9rZT0iIzMzMyIgc3Ryb2tlLWxpbmVqb2luPSJyb3VuZCIgc3Ryb2tlLXdpZHRoPSIxLjciPgogICAgICAgICAgICA8cGF0aCBkPSJNOS4zMDYgMjRTMCAxNi41NDQgMCA5LjMwNmE5LjMwNiA5LjMwNiAwIDAgMSAxOC42MTIgMEMxOC42MTIgMTYuNTQ0IDkuMzA2IDI0IDkuMzA2IDI0eiIgc3Ryb2tlLWxpbmVjYXA9InJvdW5kIi8+CiAgICAgICAgICAgIDxjaXJjbGUgc3Ryb2tlLWxpbmVjYXA9InNxdWFyZSIgY3g9IjkuMjEyIiBjeT0iOS4xMjMiIHI9IjIuNzg0Ii8+CiAgICAgICAgPC9nPgogICAgPC9nPgo8L3N2Zz4K)
    50% 50% no-repeat;
  cursor: pointer;
}

.header_icon_2 {
  width: 36px;
  height: 36px;
  background: url(data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMzYiIGhlaWdodD0iMzYiIHZpZXdCb3g9IjAgMCAzNiAzNiIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KICAgIDxnIGZpbGw9Im5vbmUiIGZpbGwtcnVsZT0iZXZlbm9kZCI+CiAgICAgICAgPHBhdGggZD0iTTM2IDM2SDBWMGgzNnoiLz4KICAgICAgICA8ZyB0cmFuc2Zvcm09InRyYW5zbGF0ZSg1LjE2NCA2LjU0NykiIHN0cm9rZT0iIzMzMyIgc3Ryb2tlLWxpbmVjYXA9InNxdWFyZSIgc3Ryb2tlLWxpbmVqb2luPSJyb3VuZCIgc3Ryb2tlLXdpZHRoPSIxLjciPgogICAgICAgICAgICA8cGF0aCBkPSJtMjUuNjggMy42Ni0yLjcyIDExLjU3SDcuMzdMNC42NiAzLjY2eiIvPgogICAgICAgICAgICA8Y2lyY2xlIGN4PSIyMC41MiIgY3k9IjIwLjc4IiByPSIyLjE0Ii8+CiAgICAgICAgICAgIDxjaXJjbGUgY3g9IjkuODEiIGN5PSIyMC43OCIgcj0iMi4xNCIvPgogICAgICAgICAgICA8cGF0aCBkPSJNMCAwaDMuOGwxLjc2IDcuNSIvPgogICAgICAgIDwvZz4KICAgIDwvZz4KPC9zdmc+Cg==);
  cursor: pointer;
}

/* 시작 */
</style>

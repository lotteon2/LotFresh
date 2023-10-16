<template>
  <div class="header">
    <div class="header_wrap">
      <img class="logo_img" src="../assets/logo.png" />
      <div class="search_wrap">
        <input class="input_bar" placeholder="검색어를 입력해주세요." />
      </div>

      <img class="logo_img" src="../assets/logo.png" />
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
body {
  margin: 0;
  padding: 0;
  font-family: Arial, sans-serif;
}

.header {
  padding: 20px;
  display: flex;
  justify-content: baseline;
}

.header_wrap {
  padding-left: 350px;
  display: flex;
  width: 800px;
  height: 55px;
  justify-content: space-between;
}
.search_wrap {
  display: flex;
  /* -webkit-box-align: center; */
  align-items: center;
  -webkit-box-pack: justify;
  justify-content: space-between;
  position: absolute;
  left: 730px;
  top: 50px;
  width: 450px;
  height: 5%;
  /* padding-left: 14px; */
  border: 1px solid red;
  border-radius: 6px;
  background-color: rgb(255, 255, 255);
  box-shadow: rgb(247, 247, 247) 0px 0px 0px 1px inset;
}

.input_bar {
  padding-left: 15px;
  width: 370px;
  background-color: inherit;
  border: none;
  outline: none;
  font-size: 16px;
  letter-spacing: -0.33px;
}

.logo_img {
  top: 50px;
  width: 150px;
  height: auto;
  position: absolute;
}
</style>

<template>
  <div>
    <div class="title">
      <div class="item">주문자 정보</div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
    </div>
    <hr />
  </div>
  <div class="container">
    <div class="item">보내는 분</div>
    <div class="item">홍길동</div>
    <div class="item"></div>
    <div class="item"></div>
    <div class="item"></div>
    <div class="item"></div>
  </div>
  <div class="container">
    <div class="item">휴대폰</div>
    <div class="item">010-1234-5678</div>
    <div class="item"></div>
    <div class="item"></div>
    <div class="item"></div>
    <div class="item"></div>
  </div>
  <div class="container">
    <div class="item">이메일</div>
    <div class="item">qwerty1434@naver.com</div>
    <div class="item"></div>
    <div class="item"></div>
    <div class="item"></div>
    <div class="item"></div>
  </div>
</template>

<script lang="ts">
export default {};
</script>

<style scoped>
.title {
  display: grid;
  grid: ". . . . . . . . . . . .";
}
.container {
  display: grid;
  grid: ". . . . . .";
}
</style>

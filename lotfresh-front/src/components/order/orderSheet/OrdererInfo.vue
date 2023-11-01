<template>
  <div class="outer">
    <div>
      <div class="title">
        <div class=""><h3>주문자 정보</h3></div>
      </div>
    </div>
    <div class="orderer-info-container">
      <div class="container">
        <div class="item">보내는 분</div>
        <div class="item">홍길동</div>
        <div class="item"></div>
        <div class="item"></div>
        <div class="item"></div>
        <div class="item"></div>
      </div>
      <!-- <div class="container">
        <div class="item">휴대폰</div>
        <div class="item">010-1234-5678</div>
        <div class="item"></div>
        <div class="item"></div>
        <div class="item"></div>
        <div class="item"></div>
      </div> -->
      <div class="container">
        <div class="item">이메일</div>
        <div class="item">qwerty1434@naver.com</div>
        <div class="item"></div>
        <div class="item"></div>
        <div class="item"></div>
        <div class="item"></div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { storeToRefs } from "pinia";
export default {};
</script>

<style scoped>
.title {
  display: grid;
  grid: ". . . . . . . . . . . .";
  border-bottom: 2px solid rgb(0, 0, 0);
  margin-top: 30px;
  padding-bottom: 10px;
}

.container {
  display: grid;
  grid: ". . . . . .";
}

.orderer-info-container {
  margin-top: 40px;
}
.outer {
  margin-right: auto;
  margin-left: auto;
  font-size: 20px;
  margin-bottom: 10vh;
}

.item {
  margin-bottom: 20px;
}
</style>

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
        <div class="item">{{ nickname }}</div>
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
        <div class="item">{{ email }}</div>
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
import { useMemberStore } from "@/stores/member";

export default {
  setup() {
    const { memberInfo } = storeToRefs(useMemberStore()); // useMemberStore를 사용해 memberInfo를 가져옴.

    return {
      nickname: memberInfo.value?.nickname, // nickname을 setup의 리턴 객체에 추가.
      email: memberInfo.value?.email, // email을 setup의 리턴 객체에 추가.
    };
  },
};
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

<template>
  <div class="delivery-info-container">
    <div>
      <div class="title">
        <div class="item"><h3>배송 정보</h3></div>
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
        <div class="item text-right gray-text small-text v-center">
          배송지변경 안내
        </div>
      </div>
    </div>
    <div class="container">
      <div class="item">배송지</div>
      <div class="item">
        서우울특별시 서초구 서초동 어쩌구번지 비트컴퓨터 3층
      </div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item text-right"><button>주소 변경</button></div>
    </div>
  </div>
</template>

<script lang="ts">
export default {};
</script>

<style scoped>
.title {
  display: grid;
  grid: ". . . . . . . . . . . .";
  margin-top: 30px;
}
.container {
  display: grid;
  grid: ". . . . . .";
  margin-top: 40px;
}
.text-right {
  text-align: right;
}
.gray-text {
  color: gray;
}
.small-text {
  font-size: 13px;
}
.delivery-info-container {
  margin-right: auto;
  margin-left: auto;
}
.title {
  padding-bottom: 10px;
  border-bottom: 2px solid rgb(0, 0, 0);
}
.v-center {
  margin-top: auto;
  margin-bottom: auto;
}
</style>

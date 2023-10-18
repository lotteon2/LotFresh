<template>
  <div>
    <div class="title">
      <div class="item">배송 정보</div>
      <div class="item">a</div>
      <div class="item">b</div>
      <div class="item">c</div>
      <div class="item">d</div>
      <div class="item">e</div>
      <div class="item">f</div>
      <div class="item">g</div>
      <div class="item">h</div>
      <div class="item">i</div>
      <div class="item">j</div>
      <div class="item text-right gray-text small-text">배송지변경 안내</div>
    </div>
    <hr />
  </div>
  <div class="container">
    <div class="item">배송지</div>
    <div class="item">서우울특별시 서초구 서초동 어쩌구번지 비트컴퓨터 3층</div>
    <div class="item">c</div>
    <div class="item">d</div>
    <div class="item">e</div>
    <div class="item"><button>주소 변경</button></div>
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
.text-right {
  text-align: right;
}
.gray-text {
  color: gray;
}
.small-text {
  font-size: 13px;
}
</style>

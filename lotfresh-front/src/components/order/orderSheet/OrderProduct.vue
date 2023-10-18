<template>
  <div>
    <div class="title">
      <div class="item">주문 상품</div>
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
      <div class="item text-right">위로 화살표 아이콘</div>
    </div>
    <hr />
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
.text-right {
  text-align: right;
}
</style>

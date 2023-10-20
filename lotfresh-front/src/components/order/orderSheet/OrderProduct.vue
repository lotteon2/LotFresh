<template>
  <div class="order-product-container">
    <div class="title">
      <div class="item"><h3>주문 상품</h3></div>
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
      <div class="item text-right">위로 화살표 아이콘</div>
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
  padding-bottom: 10px;
  border-bottom: 2px solid rgb(0, 0, 0);
}
.text-right {
  text-align: right;
}
.order-product-container {
  margin-right: auto;
  margin-left: auto;
}
</style>

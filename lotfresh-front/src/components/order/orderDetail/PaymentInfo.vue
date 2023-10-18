<template>
  <div class="outer">
    <div class="text-left">
      <h3>결제정보</h3>
    </div>
    <div class="payment-container">
      <div class="item">
        <div class="item-title">결제 수단</div>
        <div class="item-content">내용</div>
      </div>
      <div class="item">
        <div class="item-title">총 상품가격</div>
        <div class="item-content">내용</div>
      </div>
      <div class="item"></div>
      <div class="item">
        <div class="item-title">할인금액</div>
        <div class="item-content">내용</div>
      </div>
      <div class="item"></div>
      <div class="item">
        <div class="item-title">총 결제금액</div>
        <div class="item-content">내용</div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
export default {};
</script>

<style scoped>
.payment-container {
  display: grid;
  grid: ". .";
  margin-left: auto;
  margin-right: auto;
}
.outer {
  margin-left: 100px;
}

.item {
  margin-bottom: 15px;
}
.item-title {
  text-align: left;
  font-weight: bold;
  margin-bottom: 5px;
}

.item-content {
  text-align: left;
  color: gray;
  margin-left: 20px;
}

.text-left {
  text-align: left;
}
</style>

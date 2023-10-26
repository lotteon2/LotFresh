<template>
  <div class="outer">
    <div class="text-left">
      <h3>결제정보</h3>
    </div>
    <div class="payment-container">
      <div class="item">
        <div class="item-title bold-text">결제 수단</div>
        <div class="item-content">내용</div>
      </div>
      <div class="item">
        <div class="item-title bold-text">총 상품가격</div>
        <div class="item-content">내용</div>
      </div>
      <div class="item"></div>
      <div class="item">
        <div class="item-title bold-text">할인금액</div>
        <div class="item-content">내용</div>
      </div>
      <div class="item"></div>
      <div class="item">
        <div class="item-title bold-text">총 결제금액</div>
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
  margin-right: auto;
  margin-left: auto;
  font-size: 25px;
}

.item {
  margin-bottom: 15px;
}
.item-title {
  text-align: left;
  margin-bottom: 5px;
}
.bold-text {
  font-weight: bold;
}

.item-content {
  text-align: left;
  color: gray;
  margin-left: 20px;
}

.text-left {
  text-align: left;
}

.popup-view {
  opacity: 0;
  display: none;
  visibility: hidden;
}
.active {
  opacity: 1;
  display: block;
  visibility: visible;
}
</style>

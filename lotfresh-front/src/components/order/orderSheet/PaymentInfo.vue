<template>
  <div class="payment-info-container">
    <div class="container">
      <div class="bill">
        <div class="title"><h3>결제금액</h3></div>
        <div class="section">
          <div class="item">주문 금액</div>
          <div class="item"></div>
          <div class="item text-right">4,800원</div>
        </div>
        <div class="section">
          <div class="item gray-text small-text">상품금액</div>
          <div class="item"></div>
          <div class="item gray-text small-text text-right">4,800원</div>
        </div>
        <div class="section">
          <div class="item gray-text small-text">상품할인금액</div>
          <div class="item"></div>
          <div class="item gray-text small-text text-right">0원</div>
        </div>
        <div class="section">
          <div class="item">적립금사용</div>
          <div class="item"></div>
          <div class="item text-right">0원</div>
        </div>
        <div class="section">
          <div class="item">최종결제금액</div>
          <div class="item"></div>
          <div class="item text-right">7,800원</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
export default {};
</script>

<style scoped>
.container {
  display: grid;
  grid: ". . .";
  grid-template-columns: 1fr 1fr 1fr;
}
.text-left {
  text-align: right;
}
.text-right {
  text-align: right;
}

.bill {
  border: 1px solid black;
}
.section {
  display: grid;
  grid: ". . .";
  grid-template-columns: 1fr 1fr 1fr;
}
.item {
  border: 1px solid black;
}
.gray-text {
  color: gray;
}
.small-text {
  font-size: 13px;
}
.payment-info-container {
  width: 1000px;
  margin-right: auto;
  margin-left: auto;
  margin-top: 100px;
  margin-bottom: 70px;
}
.title {
  margin-bottom: 20px;
}
</style>

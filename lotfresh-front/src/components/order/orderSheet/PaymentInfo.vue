<template>
  <div>
    <div class="container">
      <div class="bill">
        결제금액
        <div class="section">
          <div class="item">주문 금액</div>
          <div class="item"></div>
          <div class="item">4,800원</div>
        </div>
        <div class="section">
          <div class="item gray-text small-text">상품금액</div>
          <div class="item"></div>
          <div class="item gray-text small-text">4,800원</div>
        </div>
        <div class="section">
          <div class="item gray-text small-text">상품할인금액</div>
          <div class="item"></div>
          <div class="item gray-text small-text">0원</div>
        </div>
        <div class="section">
          <div class="item">적립금사용</div>
          <div class="item"></div>
          <div class="item">0원</div>
        </div>
        <div class="section">
          <div class="item">최종결제금액</div>
          <div class="item"></div>
          <div class="item">7,800원</div>
        </div>
      </div>
      <div class="bill">a</div>
      <div class="bill">b</div>
    </div>
    <hr />
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
.bill {
  border-radius: 25px;
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
</style>

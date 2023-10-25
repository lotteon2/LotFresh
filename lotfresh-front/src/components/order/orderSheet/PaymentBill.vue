<template>
  <div class="payment-info-container">
    <div class="container">
      <div class="bill">
        <div class="content">
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

          <div class="emptySpace"></div>

          <div class="section">
            <div class="item"></div>
            <div class="item"></div>
            <div class="item text-right"></div>
          </div>
          <div class="section">
            <div class="item grid-col">최종결제금액</div>
            <div class="item text-right">7,800원</div>
          </div>
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
.section {
  display: grid;
  grid: ". . .";
  grid-template-columns: 1fr 1fr 1fr;
  margin-bottom: 15px;
}
.grid-col {
  grid-column: 1/3;
}
.gray-text {
  color: gray;
}
.small-text {
  font-size: 20px;
}
.payment-info-container {
  margin-right: auto;
  margin-left: auto;
  margin-top: 100px;
  margin-bottom: 70px;
  font-size: 20px;
}
.emptySpace {
  width: 90%;
  height: 180px;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 20px;
  border-bottom: 2px solid rgb(204, 194, 194);
}
.title {
  margin-bottom: 20px;
}
</style>

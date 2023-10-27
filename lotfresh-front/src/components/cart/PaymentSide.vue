!
<template>
  <div>
    <div class="section">
      <div class="item">상품금액</div>
      <div class="item text-right">{{ totalPrice }}</div>
    </div>
    <div class="section">
      <div class="item">상품할인금액</div>
      <div class="item text-right">{{ discountPrice }}</div>
    </div>
  </div>
  <div class="emptySpace"></div>

  <div class="section">
    <div class="item grid-col">결제예정금액</div>
    <div class="item text-right">{{ totalPrice - discountPrice }}</div>
  </div>

  <div class="order_button">주문하기</div>
</template>

<script lang="ts">
export default {
  props: {
    totalPrice: Number,
    discountPrice: Number,
    items: Object,
  },
};
</script>

<style scoped>
.emptySpace {
  width: 95%;
  height: 30px;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 20px;
}
.section {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-bottom: 10px;
}

.order_button {
  width: 11vw;
  padding: 1vh 1vw 1vh 1vw;
  color: white;
  background-color: #ef2a23;
  border-radius: 5px;
  text-align: center;
  font-size: 1rem;
  margin: 20px auto;
  text-align: center;
  width: 80%;
}

.order_button:hover {
  background-color: #d8241f;
  cursor: pointer;
}

.order_button:active {
  background-color: #c01e1b;
}
</style>

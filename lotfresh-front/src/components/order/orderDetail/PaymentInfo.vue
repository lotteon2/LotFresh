<template>
  <div class="outer">
    <div class="text-left">
      <h3>결제정보</h3>
    </div>
    <div class="payment-container">
      <div class="item">
        <div class="item-title bold-text">결제 수단</div>
        <div class="item-content">
          <div v-if="paymentData.paymentMethod">
            {{ paymentData.paymentMethod }}
          </div>
          <div v-else>-</div>
        </div>
      </div>
      <div class="item">
        <div class="item-title bold-text">총 상품가격</div>
        <div class="item-content">
          <div v-if="paymentData.originalAmount">
            {{ paymentData.originalAmount }}
          </div>
          <div v-else>-</div>
        </div>
      </div>
      <div class="item"></div>
      <div class="item">
        <div class="item-title bold-text">할인금액</div>
        <div class="item-content">
          <div v-if="paymentData.discountedAmount">
            {{ paymentData.discountedAmount }}
          </div>
          <div v-else>-</div>
        </div>
      </div>
      <div class="item"></div>
      <div class="item">
        <div class="item-title bold-text">총 결제금액</div>
        <div class="item-content">
          <div v-if="paymentData.transactionAmount">
            {{ paymentData.transactionAmount }}
          </div>
          <div v-else>-</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
export default {
  props: {
    paymentData: {
      type: Object,
      required: true,
    },
  },
  // ...
};
</script>

<style scoped>
.payment-container {
  display: grid;
  grid: ". .";
  margin-left: auto;
  margin-right: auto;
  border: 4px solid #c8c8c8;
  border-radius: 5px;
  padding: 2vh 1vw 0vh 1vw;
  /* box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19); */
  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.2);
  margin-bottom: 15vh;
}
.outer {
  margin-right: auto;
  margin-left: auto;
  font-size: 1.3rem;
}

.item {
  margin-bottom: 5vh;
  display: flex;
  flex-direction: row;
}
.item-title {
  text-align: left;
  margin-bottom: 5px;
  width: 10vw;
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

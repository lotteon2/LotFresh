<template>
  <div class="payment-info-container">
    <div class="container">
      <div class="bill">
        <div class="content">
          <div class="title"><h3>결제금액</h3></div>
          <div class="section">
            <div class="item">주문 금액</div>
            <div class="item"></div>
            <div class="item text-right">{{ sumOfOriginalPrice }}원</div>
          </div>
          <div class="section">
            <div class="item gray-text small-text">상품금액</div>
            <div class="item"></div>
            <div class="item gray-text small-text text-right">
              {{ sumOfOriginalPrice }}원
            </div>
          </div>
          <div class="section">
            <div class="item gray-text small-text">상품할인금액</div>
            <div class="item"></div>
            <div class="item gray-text small-text text-right">
              {{ sumOfOriginalPrice - sumOfDiscountedPrice }}원
            </div>
          </div>

          <div class="emptySpace"></div>

          <div class="section">
            <div class="item"></div>
            <div class="item"></div>
            <div class="item text-right"></div>
          </div>
          <div class="section">
            <div class="item grid-col">최종결제금액</div>
            <div class="item text-right">{{ sumOfDiscountedPrice }}원</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import type { OrderSheetItem } from "../../../interface/orderInterface";

export default defineComponent({
  name: "PaymentBill",
  props: {
    orderSheetItems: {
      type: Array as () => OrderSheetItem[],
      required: false,
      default: () => [],
    },
  },
  data() {
    return {
      sumOfOriginalPrice: 0,
      sumOfDiscountedPrice: 0,
    };
  },
  mounted() {
    console.log("PaymentBill로 들어온 prop은 이렇게 생겼다.");
    console.log(this.orderSheetItems);
    let sumOfOriginalPrice = 0;
    let sumOfDiscountedPrice = 0;

    this.orderSheetItems.forEach((item: OrderSheetItem) => {
      sumOfOriginalPrice += item.originalPrice * item.productStock;
      if (item.discountedPrice !== null) {
        sumOfDiscountedPrice += item.discountedPrice * item.productStock;
      } else {
        sumOfDiscountedPrice += item.originalPrice * item.productStock;
      }
    });

    console.log("금액계산하면 이렇게 된다.");
    console.log("sumOfOriginalPrice: ", sumOfOriginalPrice);
    console.log("sumOfDiscountedPrice: ", sumOfDiscountedPrice);
  },
});
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

<template>
  <div>
    <div class="pageTitle">
      <div class="title">
        <h3>주문서</h3>
      </div>
    </div>

    <order-product />
    <orderer-info />
    <delivery-info />
    <payment-info />
    <div class="pay-button">
      <button>카카오페이로 결제하기</button>
    </div>
  </div>
</template>

<script lang="ts">
import DeliveryInfo from "../components/order/orderSheet/DeliveryInfo.vue";
import OrdererInfo from "../components/order/orderSheet/OrdererInfo.vue";
import OrderProduct from "../components/order/orderSheet/OrderProduct.vue";
import PaymentInfo from "../components/order/orderSheet/PaymentInfo.vue";
export default {
  components: { DeliveryInfo, OrdererInfo, OrderProduct, PaymentInfo },
};
</script>

<style scoped>
.title {
  text-align: center;
  margin-top: 150px;
  margin-bottom: 50px;
}
.pay-button {
  text-align: center;
  margin-bottom: 200px;
}
</style>

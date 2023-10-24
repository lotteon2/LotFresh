<template>
  <div>
    <div class="pageTitle">
      <div class="title">
        <h2>주문서</h2>
      </div>
    </div>

    <order-product />
    <orderer-info />
    <delivery-info />
    <payment-bill />
    <div class="pay-button">
      <button>카카오페이로 결제하기</button>
    </div>
  </div>
</template>

<script lang="ts">
import DeliveryInfo from "../components/order/orderSheet/DeliveryInfo.vue";
import OrdererInfo from "../components/order/orderSheet/OrdererInfo.vue";
import OrderProduct from "../components/order/orderSheet/OrderProduct.vue";
import PaymentBill from "../components/order/orderSheet/PaymentBill.vue";
export default {
  components: { DeliveryInfo, OrdererInfo, OrderProduct, PaymentBill },
};
</script>

<style scoped>
.title {
  text-align: center;
  margin-top: 150px;
  margin-bottom: 50px;
  font-size: 25px;
}
.pay-button {
  text-align: center;
  margin-bottom: 200px;
}
</style>

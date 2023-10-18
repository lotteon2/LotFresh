<template>
  <div>
    <div class="pageTitle">
      <h3>주문서</h3>
    </div>

    <br />
    <br />
    <br />

    <order-product />

    <br />
    <br />
    <br />

    <orderer-info />

    <br />
    <br />
    <br />

    <delivery-info />

    <br />
    <br />
    <br />

    <payment-info />

    <br />
    <br />
    <br />

    <button>카카오페이로 결제하기</button>
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
.pageTitle {
  text-align: center;
}
</style>

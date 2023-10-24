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
      <button @click="callAPI">카카오페이로 결제하기</button>
    </div>
  </div>
</template>

<script lang="ts">
import { orderInstance } from "@/api/utils";
import DeliveryInfo from "../components/order/orderSheet/DeliveryInfo.vue";
import OrdererInfo from "../components/order/orderSheet/OrdererInfo.vue";
import OrderProduct from "../components/order/orderSheet/OrderProduct.vue";
import PaymentBill from "../components/order/orderSheet/PaymentBill.vue";

interface OrderCreateRequest {
  productRequests: ProductRequest[];
  isFromCart: boolean;
}

interface ProductRequest {
  productId: number;
  productPrice: number;
  productStock: number;
  productName: string;
  productThumbnail: string;
}

export default {
  components: { DeliveryInfo, OrdererInfo, OrderProduct, PaymentBill },
  data() {
    return {
      orderCreateRequest: {
        type: Object as () => OrderCreateRequest,
        required: true,
      },
    };
  },
  methods: {
    callAPI() {
      orderInstance
        .post("/order", this.orderCreateRequest)
        .then((res) => {
          console.log("주문 요청 성공");
        })
        .catch((res) => {
          console.log("주문 요청 실패");
          console.log(res);
        });
    },
  },
};
</script>

<style scoped>
.title {
  text-align: center;
  margin-top: 150px;
  margin-bottom: 50px;
  font-size: 20px;
}
.pay-button {
  text-align: center;
  margin-bottom: 200px;
}
</style>

<template>
  <div>
    <div>
      <div class="title">
        <h2>주문 상세정보</h2>
      </div>
      <!-- <OrderDetailInfo :orderData="order" /> -->

      <delivery />
      <payment />
    </div>
  </div>
</template>

<script lang="ts">
import { orderInstance } from "@/api/utils";
import OrderDetailInfo from "../components/order/orderDetail/OrderDetailInfo.vue";
import Delivery from "../components/order/orderDetail/ProductDeliveryInfo.vue";
import Payment from "../components/order/orderDetail/PaymentInfo.vue";
import type { OrderResponse } from "../interface/orderInterface";
import { getOrderDetail } from "../api/order/order";
import { getPaymentDetailInfo } from "../api/payment/payment";
import type { PaymentInfo } from "../api/payment/payment";

import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
export default {
  components: { OrderDetailInfo, Delivery, Payment },
  data() {
    return {
      order: {} as OrderResponse,
      payment: {} as PaymentInfo,
      orderId: "0",
    };
  },
  setup() {
    const { accessToken } = storeToRefs(useMemberStore());
    return {
      accessToken,
    };
  },
  methods: {
    callAPI(accessToken: string | null) {
      const orderId = Array.isArray(this.$route.params.orderId)
        ? this.$route.params.orderId[0]
        : this.$route.params.orderId;
      getOrderDetail(orderId, accessToken)
        .then((res) => {
          console.log("주문상세 가져오기 성공");
          console.log(res);
          this.order = res;
        })
        .catch((err) => {
          console.log("주문상세 가져오기 실패");
          console.log(err);
        });
    },
    callPaymentInfo(accessToken: string | null) {
      const orderId = Array.isArray(this.$route.params.orderId)
        ? this.$route.params.orderId[0]
        : this.$route.params.orderId;
      getPaymentDetailInfo(orderId, accessToken)
        .then((res) => {
          console.log("결제정보 가져오기 성공");
          console.log(res);
          this.payment = res;
        })
        .catch((err) => {
          console.log("결제정보 가져오기 실패");
          console.log(err);
        });
    },
  },
  created() {
    this.callAPI(this.accessToken);
    this.callPaymentInfo(this.accessToken);
  },
};
</script>

<style scoped>
.title {
  text-align: center;
  margin-top: 15vh;
  margin-bottom: 3vh;
  padding-bottom: 3vh;
  margin-left: auto;
  margin-right: auto;
  border-bottom: 2px solid #ef2a23;
  font-size: 1.3rem;
}
</style>

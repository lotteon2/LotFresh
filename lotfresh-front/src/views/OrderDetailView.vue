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
export default {
  components: { OrderDetailInfo, Delivery, Payment },
  data() {
    return {
      order: Object,
      orderId: 0,
    };
  },
  methods: {
    callAPI() {
      console.log(this.$route.params);
      orderInstance
        .get("/order/" + this.$route.params.orderId)
        .then((res) => {
          console.log("주문상세 가져오기 성공");
          console.log(res.data);
          this.order = res.data;
        })
        .catch((res) => {
          console.log("주문상세 가져오기 실패");
          console.log(res);
        });
    },
  },
  created() {
    this.callAPI();
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

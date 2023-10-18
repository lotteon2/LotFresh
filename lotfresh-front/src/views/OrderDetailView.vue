<template>
  <div>
    <div class="pageTitle">
      <h3>주문 상세정보</h3>
      <order :orderData="order" />

      <br />
      <br />
      <br />

      <delivery />

      <br />
      <br />
      <br />

      <payment />
    </div>
  </div>
</template>

<script lang="ts">
import { orderInstance } from "@/api/utils";
import Order from "../components/order/Order.vue";
import Delivery from "../components/order/orderDetail/ProductDeliveryInfo.vue";
import Payment from "../components/order/orderDetail/PaymentInfo.vue";
export default {
  components: { Order, Delivery, Payment },
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
          console.log("전체 주문 가져오기 성공");
          console.log(res.data);
          this.order = res.data;
        })
        .catch((res) => {
          console.log("전체 주문 가져오기 실패");
          console.log(res);
        });
    },
  },
  mounted() {
    this.callAPI();
  },
};
</script>

<style scoped>
.pageTitle {
  text-align: center;
}
</style>

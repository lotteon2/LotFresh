<template>
  <div>
    <div class="pageTitle">
      <div class="title">
        <h2>주문 상세정보</h2>
      </div>
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
  created() {
    this.callAPI();
  },
};
</script>

<style scoped>
.title {
  text-align: center;
  margin-top: 150px;
  margin-bottom: 30px;
  padding-bottom: 30px;
  margin-left: auto;
  margin-right: auto;
  border-bottom: 2px solid rgb(245, 199, 199);
  font-size: 25px;
}
</style>

<template>
  <div v-if="orderData" class="order-container">
    <div class="order-info">
      <div class="item">{{ formattedData }}</div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item">
        <router-link :to="'/orderdetail/' + orderData.orderId"
          >주문상세보기</router-link
        >
      </div>
    </div>
    <div v-for="orderDetail in orderData.orderDetailResponses">
      <order-detail :orderDetailData="orderDetail" />
    </div>
  </div>
</template>

<script lang="ts">
import OrderDetail from "./OrderDetail.vue";

export default {
  components: { OrderDetail },
  props: {
    orderData: Object,
  },
  computed: {
    formattedData() {
      let date: Date = new Date(this.orderData.orderCreatedTime);

      return (
        date.getFullYear() +
        "." +
        date.getMonth() +
        "." +
        date.getDay() +
        " 주문"
      );
    },
  },
};
</script>

<style scoped>
.order-container {
  border: 1px solid gray;
  border-radius: 20px;
  width: 800px;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 50px;
}

.order-info {
  display: grid;
  grid: ". . . . . . . .";
}

.item {
  margin-top: 15px;
  margin-bottom: 15px;
}
</style>

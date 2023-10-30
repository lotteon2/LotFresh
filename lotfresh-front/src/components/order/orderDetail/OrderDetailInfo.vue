<template>
  <div class="order-container">
    <div class="order-info">
      <div class="item bold-text margin-left-text">{{ formattedData }}</div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item text-align-center">주문 목록으로 이동</div>
    </div>
    <div
      v-for="orderDetail in orderData.orderDetailResponses"
      :key="orderDetail.orderDetailId"
    >
      <order-detail :orderDetailData="orderDetail" />
    </div>
    <div class="empty-bottom"></div>
  </div>

  <div v-if="orderData" class="order-container">
    <div class="order-info">
      <div class="item bold-text margin-left-text">{{ formattedData }}</div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item text-align-center"></div>
    </div>
    <div
      v-for="orderDetail in orderData.orderDetailResponses"
      :key="orderDetail.orderDetailId"
    >
      <order-detail :orderDetailData="orderDetail" />
    </div>
    <div class="empty-bottom"></div>
  </div>
</template>

<script lang="ts">
import OrderDetail from "../OrderDetail.vue";
interface Order {
  orderId: number;
  orderCreatedTime: Date;
  orderDetailResponses: OrderDetail[];
}
interface OrderDetail {
  orderDetailId: number;
  price: number;
  stock: number;
  status: string;
  productName: string;
  productThumbnail: string;
}

export default {
  components: { OrderDetail },
  props: {
    orderData: {
      type: Object as () => Order,
      required: true,
    },
  },
  setup(props) {
    return {};
  },
  computed: {
    formattedData() {
      let date: Date = new Date(this.orderData?.orderCreatedTime);

      return !isNaN(date.getTime())
        ? date.getFullYear() +
            "." +
            (date.getMonth() + 1) +
            "." +
            date.getDate() +
            " 주문"
        : "- 주문";
    },
  },
};
</script>

<style scoped>
.order-container {
  border: 4px solid #c8c8c8;
  border-radius: 5px;
  margin-left: auto;
  margin-right: auto;
  margin-top: 7.5vh;
  margin-bottom: 10vh;
  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.2);
}

.order-info {
  display: grid;
  grid: ". . . . . . . .";
  font-size: 20px;
}

.order-date {
  margin-top: 15px;
  margin-bottom: 15px;
}

.item {
  margin-top: 15px;
  margin-bottom: 15px;
}

.empty-bottom {
  margin-bottom: 50px;
}

.bold-text {
  font-weight: bold;
}

.margin-left-text {
  margin-left: 40px;
}

.text-align-center {
  text-align: center;
}
/*router-link 속성*/
a {
  text-decoration: none;
  color: black;
}
</style>

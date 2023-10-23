<template>
  <div>
    <div class="pageTitle">
      <div class="title">
        <h2>전체 주문 내역</h2>
      </div>

      <div v-for="order in orders" :key="order.orderId">
        <order :orderData="order" />
      </div>
      <div class="paging-box">
        <el-pagination
          :page-size="size"
          :pager-count="11"
          layout="prev, pager, next"
          :total="totalPage * size"
          v-model:currentPage="page"
        />
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { orderInstance } from "@/api/utils";
import Order from "../components/order/Order.vue";
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
  components: { Order },
  data() {
    return {
      orders: [] as Order[],
      page: 1,
      size: 5,
      totalPage: 0,
    };
  },
  watch: {
    page(newPage) {
      this.callAPI(this.page);
    },
  },
  methods: {
    callAPI(page: number) {
      orderInstance
        .get(`/order/myOrders` + "?page=" + (page - 1) + "&size=" + this.size)
        .then((res) => {
          console.log("전체 주문 가져오기 성공");
          console.log(res.data);
          this.orders = res.data.contents;
          this.totalPage = res.data.totalPage;
        })
        .catch((res) => {
          console.log("전체 주문 가져오기 실패");
          console.log(res);
        });
    },
  },
  created() {
    this.callAPI(this.page);
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

.paging-box {
  display: flex;
  justify-content: center;
}
</style>

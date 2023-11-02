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
import { getOrders } from "../api/order/order";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import type {
  OrderResponse,
  OrderDetailResponse,
} from "../interface/orderInterface";

export default {
  components: { Order },
  data() {
    return {
      orders: [] as OrderResponse[],
      page: 1,
      size: 5,
      totalPage: 0,
    };
  },
  setup() {
    const { accessToken } = storeToRefs(useMemberStore());
    return {
      accessToken,
    };
  },
  watch: {
    page(newPage, accessToken) {
      this.callAPI(this.page, accessToken);
      window.scrollTo(0, 0);
    },
  },
  methods: {
    callAPI(page: number, accessToken: string | null) {
      getOrders(page - 1, accessToken)
        .then((res) => {
          console.log("전체 주문 가져오기 성공");
          console.log(res);
          this.orders = res.contents;
        })
        .catch((res) => {
          console.log("전체 주문 가져오기 실패");
          console.log(res);
        });
    },
  },
  created() {
    this.callAPI(this.page, this.accessToken);
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
  font-size: 20px;
}

.paging-box {
  display: flex;
  justify-content: center;
}
</style>

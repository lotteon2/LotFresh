<template>
  <div>
    <div class="pageTitle">
      <div class="title">
        <h3>전체 주문 내역</h3>
      </div>

      <div v-for="order in orders" :key="order?.orderId">
        <order :orderData="order" />
      </div>
      <div class="paging-box">
        <div class="text-center">
          <v-container>
            <v-row justify="center">
              <v-col cols="8">
                <v-container class="max-width">
                  <v-pagination
                    v-model="page"
                    class="my-4"
                    :length="totalPage"
                  ></v-pagination>
                </v-container>
              </v-col>
            </v-row>
          </v-container>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { orderInstance } from "@/api/utils";
import Order from "../components/order/Order.vue";

export default {
  components: { Order },
  data() {
    return {
      orders: [],
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
  width: 900px;
  margin-left: auto;
  margin-right: auto;
  border-bottom: 2px solid rgb(245, 199, 199);
}
</style>

<template>
  <div>
    <div class="pageTitle">
      <h3>전체 주문 내역</h3>

      <div v-for="order in orders" :key="order.orderId">
        <order :orderData="order" />
      </div>
      <div class="paging-box">
        <template>
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
        </template>
      </div>
      페이징 처리하기 {{ totalPage }}
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
      pageable: {
        page: 0,
      },
      page: 1,
      totalPage: 0,
    };
  },
  methods: {
    callAPI() {
      orderInstance
        .get<orderType[]>(`/order/myOrders`, this.pageable)
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
  mounted() {
    this.callAPI();
  },
};
</script>

<style>
.pageTitle {
  text-align: center;
}
.paging-box {
  display: flex;
  flex-direction: row;
}
</style>

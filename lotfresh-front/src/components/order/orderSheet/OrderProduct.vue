<template>
  <div class="order-product-container">
    <div class="title">
      <div class="item"><h3>주문 상품</h3></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item"></div>
      <div class="item text-right">
        <v-btn
          :icon="show ? 'mdi-chevron-up' : 'mdi-chevron-down'"
          @click="show = !show"
        ></v-btn>
      </div>
    </div>
    <v-expand-transition>
      <div v-show="show">
        <v-divider></v-divider>
        <v-card-text>
          mounted시점에 redis로 axios통신을 해서 구매상품을 가져온다
        </v-card-text>
      </div>
    </v-expand-transition>
  </div>
</template>

<script lang="ts">
export default {
  data: () => ({
    show: false,
  }),
};
</script>

<style scoped>
.title {
  display: grid;
  grid: ". . . . . . . . . . . .";
  padding-bottom: 10px;
  border-bottom: 2px solid rgb(0, 0, 0);
}
.text-right {
  text-align: right;
}
.order-product-container {
  margin-right: auto;
  margin-left: auto;
}
</style>

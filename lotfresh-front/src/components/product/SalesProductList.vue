<template>
  <div class="container" v-if="salesProducts.length != 0">
    <product-item
      v-for="(product, index) in salesProducts"
      :key="index"
      :product="product"
      :componentHeight="componentHeight"
    />
  </div>
</template>

<script setup lang="ts">
import ProductItem from "@/components/product/item/ProductItem.vue";
import { ref } from "vue";
import { getSalesProducts } from "@/api/product/product";
import type { ProductResponse } from "@/interface/productInterface";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
const { memberInfo } = storeToRefs(useMemberStore());
const salesProducts = ref<ProductResponse[]>([]);

getSalesProducts(<string>memberInfo.value.province).then((data) => {
  salesProducts.value = data;
});

const componentHeight = ref("300px");
</script>

<style scoped>
.container {
  display: flex;
  flex-wrap: wrap;
  gap: 39px;
  padding-left: 55px;
}
</style>

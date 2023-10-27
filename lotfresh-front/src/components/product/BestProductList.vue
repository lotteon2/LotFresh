<template>
  <div class="container" v-if="bestProducts">
    <product-item
      v-for="(product, index) in bestProducts"
      :key="index"
      :product="product"
      :componentHeight="componentHeight"
    />
  </div>
</template>

<script setup lang="ts">
import ProductItem from "@/components/product/item/ProductItem.vue";
import { getBestProducts } from "@/api/product/product";
import { ref } from "vue";
import type { ProductResponse } from "@/interface/productInterface";

const bestProducts = ref<ProductResponse[]>([]);

getBestProducts().then((data) => {
  bestProducts.value = data;
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

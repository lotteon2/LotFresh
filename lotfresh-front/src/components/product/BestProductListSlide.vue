<template>
  <div v-if="bestProducts.length != 0">
    <carousel :items-to-show="4" :items-to-scroll="4">
      <slide v-for="(product, index) in bestProducts" :key="index">
        <product-item
          :key="index"
          :product="product"
          :componentHeight="componentHeight"
        />
      </slide>

      <template #addons>
        <navigation />
      </template>
    </carousel>
  </div>
</template>

<script setup lang="ts">
import ProductItem from "@/components/product/item/ProductItem.vue";
import "vue3-carousel/dist/carousel.css";
import { Carousel, Slide, Navigation } from "vue3-carousel";
import { getBestProducts } from "@/api/product/product";
import { ref } from "vue";
import type { ProductResponse } from "@/interface/productInterface";

const bestProducts = ref<ProductResponse[]>([]);

getBestProducts().then((data) => {
  bestProducts.value = data;
});
const componentHeight = ref("300px");
</script>

<style scoped></style>

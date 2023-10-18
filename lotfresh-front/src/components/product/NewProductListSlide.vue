<template>
  <div class="container" v-if="products">
    <carousel :items-to-show="4">
      <slide v-for="(product, index) in products" :key="index">
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
import { defaultInstance } from "@/api/utils";
import { ref } from "vue";

const products = ref([]);
const componentHeight = ref("300px");

const callApi = () => {
  defaultInstance
    .get(`/products/new-products`)
    .then((response) => {
      products.value = response.data;
    })
    .catch((error) => {
      console.log(error);
    });
};

callApi();
</script>

<style scoped></style>

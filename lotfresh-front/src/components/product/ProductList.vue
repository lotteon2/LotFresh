<template>
  <div v-if="products">
    <carousel :items-to-show="4">
      <slide v-for="(product, index) in products" :key="index">
        <best-product :key="index" :product="product" />
      </slide>

      <template #addons>
        <navigation />
        <pagination />
      </template>
    </carousel>
  </div>
</template>

<script setup lang="ts">
import "vue3-carousel/dist/carousel.css";
import { Carousel, Slide, Pagination, Navigation } from "vue3-carousel";
import BestProduct from "./BestProduct.vue";
import { defaultInstance } from "@/api/utils";
import { ref } from "vue";

const products = ref([]);

const callApi = () => {
  defaultInstance
    .get(`/products/best-products`)
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
<style>
.carousel__prev,
.carousel__next {
  margin-top: -50px;
  border: solid;
  border-radius: 15px;
}
</style>

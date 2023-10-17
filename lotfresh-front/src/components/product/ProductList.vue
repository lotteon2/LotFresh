<template>
  <div>
    <best-product
      v-for="(product, index) in products"
      :key="index"
      :product="product"
    />

    <!-- <VueSlickCarousel :rows="2" :slidesToShow="2">
      <div v-for="i in 10" class="pd16">
        <div class="card">{{ i }}</div>
      </div>
    </VueSlickCarousel> -->
  </div>
</template>

<script setup lang="ts">
// import VueSlickCarousel from "vue-slick-carousel";
// import "vue-slick-carousel/dist/vue-slick-carousel.css";
// import "vue-slick-carousel/dist/vue-slick-carousel-theme.css";

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

<template>
  <div>
    <VueSlickCarousel ref="carousel" :rows="3" :slidesToShow="2">
      <best-product
        v-for="(product, index) in products"
        :key="index"
        :product="product"
      />
    </VueSlickCarousel>
  </div>
</template>

<script setup lang="ts">
import { ListSlickMethods, VueSlickCarousel } from "vue-slick-ts";
import type { SlickInstance } from "vue-slick-ts";
import "vue-slick-ts/dist/css/slick.css";
import BestProduct from "./BestProduct.vue";
import { defaultInstance } from "@/api/utils";
import { ref } from "vue";

const carousel = ref<SlickInstance | null>(null);
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
.slider .im img {
  width: 310px;
  max-width: 100%;
  height: 435px;
}

.slider {
  width: 1070px;
  margin: 0px auto;
}

.slider .slick-slide {
  height: auto;
  margin: 10px;
}

.slick-prev:before,
.slick-next:before {
  color: #444444;
}
/* .slick-prev,
.slick-next {
  top: 40%;
} */
</style>

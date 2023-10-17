<template>
  <div>
    <best-product
      v-for="(product, index) in products"
      :key="index"
      :product="product"
    />
  </div>
</template>

<script setup lang="ts">
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

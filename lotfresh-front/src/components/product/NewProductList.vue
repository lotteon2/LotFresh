<template>
  <div class="container" v-if="products">
    <product-item
      v-for="(product, index) in products"
      :key="index"
      :product="product"
      :componentHeight="componentHeight"
    />
  </div>
</template>

<script setup lang="ts">
import ProductItem from "@/components/product/item/ProductItem.vue";
import { defaultInstance } from "@/api/utils";
import { ref } from "vue";

const products = ref([]);
const componentHeight = ref("250px");

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

<style scoped>
.container {
  display: flex;
  flex-wrap: wrap;
  gap: 39px;
}
</style>

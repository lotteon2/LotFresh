<template>
  <div class="content" v-if="product">
    <div class="wrap">
      <product-thumbnail :product_thumbnail="product.thumbnail" />
      <product-detail :product="product" />
    </div>
    <div class="detail_image">
      <product-detail-image :product_detail_img="product.detail" />
    </div>
  </div>
</template>

<script setup lang="ts">
import ProductThumbnail from "@/components/product/item/ProductThumbnail.vue";
import ProductDetail from "@/components/product/item/ProductDetail.vue";
import ProductDetailImage from "@/components/product/item/ProductDetailImage.vue";
import { defaultInstance } from "@/api/utils";
import { ref } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const product = ref();
const callApi = () => {
  defaultInstance
    .get(`/products/${route.params.id}`)
    .then((response) => {
      product.value = response.data;
    })
    .catch((error) => {
      console.log(error);
    });
};

callApi();
</script>

<style scoped>
.wrap {
  padding-top: 100px;
  display: flex;
  gap: 2.5vw;
  justify-content: start;
  /* height: 48vh; */
}
</style>
